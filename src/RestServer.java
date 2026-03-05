import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.AuthController;
import controller.ReservationController;
import dao.DatabaseConnection;
import model.Reservation;
import model.RoomType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Minimal REST adapter that reuses the existing controllers.
 * - Runs on port 8080
 * - Provides JSON endpoints for login and reservations
 * - Adds permissive CORS headers for local development
 */
public class RestServer {
    private static final int PORT = 8080;

    private final AuthController authController;
    private final ReservationController reservationController;

    public RestServer() {
        // Ensure DB is initialized
        DatabaseConnection.getInstance();
        this.authController = new AuthController();
        this.reservationController = new ReservationController();
        this.authController.initializeDefaultUser();
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/api/login", new LoginHandler());
        server.createContext("/api/reservations", new ReservationsHandler());
        server.createContext("/api/reports", new ReportsHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("REST server started on http://localhost:" + PORT);
    }

    private void addCorsHeaders(Headers headers) {
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }

    private class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange.getResponseHeaders());
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            String body = readBody(exchange.getRequestBody());
            String username = extractJsonField(body, "username");
            String password = extractJsonField(body, "password");

            boolean ok = authController.login(username, password);
            String response = "{\"success\":" + ok + "}";
            writeJsonResponse(exchange, response, ok ? 200 : 401);
        }
    }

    private class ReservationsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange.getResponseHeaders());
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            if ("OPTIONS".equalsIgnoreCase(method)) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            try {
                // Handle search with query parameters
                if ("GET".equalsIgnoreCase(method) && query != null && query.contains("search")) {
                    handleSearchReservations(exchange, query);
                    return;
                }

                // Handle specific reservation operations (GET, PUT, DELETE)
                if (path.matches(".*/api/reservations/[A-Z0-9-]+.*")) {
                    String resNum = path.replaceAll(".*/api/reservations/([A-Z0-9-]+).*", "$1");
                    if ("GET".equalsIgnoreCase(method)) {
                        Reservation r = reservationController.findReservation(resNum);
                        if (r != null) {
                            writeJsonResponse(exchange, reservationToJson(r), 200);
                        } else {
                            writeJsonResponse(exchange, "{\"error\":\"not found\"}", 404);
                        }
                    } else if ("PUT".equalsIgnoreCase(method)) {
                        handleUpdateReservation(exchange, resNum);
                    } else if ("DELETE".equalsIgnoreCase(method)) {
                        handleDeleteReservation(exchange, resNum);
                    } else {
                        exchange.sendResponseHeaders(405, -1);
                    }
                    return;
                }

                // Handle collection operations (GET all, POST create)
                if ("GET".equalsIgnoreCase(method)) {
                    List<Reservation> list = reservationController.getAllReservations();
                    String json = listToJson(list);
                    writeJsonResponse(exchange, json, 200);
                } else if ("POST".equalsIgnoreCase(method)) {
                    handleCreateReservation(exchange);
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                writeJsonResponse(exchange, "{\"error\":\"" + escapeJson(e.getMessage()) + "\",\"type\":\"" + e.getClass().getSimpleName() + "\"}", 500);
            }
        }

        private void handleCreateReservation(HttpExchange exchange) throws IOException {
            String body = readBody(exchange.getRequestBody());
            String guestName = extractJsonField(body, "guestName");
            String address = extractJsonField(body, "address");
            String contact = extractJsonField(body, "contactNumber");
            String roomTypeStr = extractJsonField(body, "roomType");
            String checkIn = extractJsonField(body, "checkInDate");
            String checkOut = extractJsonField(body, "checkOutDate");

            RoomType roomType = RoomType.fromString(roomTypeStr);
            Reservation r = reservationController.createReservation(
                    guestName, address, contact, roomType,
                    LocalDate.parse(checkIn), LocalDate.parse(checkOut)
            );
            if (r != null) {
                writeJsonResponse(exchange, reservationToJson(r), 201);
            } else {
                writeJsonResponse(exchange, "{\"error\":\"failed to create\"}", 400);
            }
        }

        private void handleUpdateReservation(HttpExchange exchange, String resNum) throws IOException {
            String body = readBody(exchange.getRequestBody());
            Reservation existing = reservationController.findReservation(resNum);
            if (existing == null) {
                writeJsonResponse(exchange, "{\"error\":\"not found\"}", 404);
                return;
            }

            // Update allowed fields
            String guestName = extractJsonField(body, "guestName");
            String address = extractJsonField(body, "address");
            String contact = extractJsonField(body, "contactNumber");
            String roomTypeStr = extractJsonField(body, "roomType");
            String checkIn = extractJsonField(body, "checkInDate");
            String checkOut = extractJsonField(body, "checkOutDate");

            if (guestName != null) existing.setGuestName(guestName);
            if (address != null) existing.setAddress(address);
            if (contact != null) existing.setContactNumber(contact);
            if (roomTypeStr != null) existing.setRoomType(RoomType.fromString(roomTypeStr));
            if (checkIn != null) existing.setCheckInDate(LocalDate.parse(checkIn));
            if (checkOut != null) existing.setCheckOutDate(LocalDate.parse(checkOut));

            if (reservationController.updateReservation(existing)) {
                writeJsonResponse(exchange, reservationToJson(existing), 200);
            } else {
                writeJsonResponse(exchange, "{\"error\":\"failed to update\"}", 400);
            }
        }

        private void handleDeleteReservation(HttpExchange exchange, String resNum) throws IOException {
            if (reservationController.deleteReservation(resNum)) {
                writeJsonResponse(exchange, "{\"success\":true}", 200);
            } else {
                writeJsonResponse(exchange, "{\"error\":\"not found\"}", 404);
            }
        }

        private void handleSearchReservations(HttpExchange exchange, String query) throws IOException {
            String guestName = extractQueryParam(query, "guestName");
            String fromDate = extractQueryParam(query, "fromDate");
            String toDate = extractQueryParam(query, "toDate");
            String roomType = extractQueryParam(query, "roomType");

            List<Reservation> results = null;

            if (guestName != null && !guestName.isEmpty()) {
                results = reservationController.searchByGuestName(guestName);
            } else if (fromDate != null && toDate != null) {
                results = reservationController.searchByDateRange(LocalDate.parse(fromDate), LocalDate.parse(toDate));
            } else if (roomType != null && !roomType.isEmpty()) {
                results = reservationController.searchByRoomType(RoomType.fromString(roomType));
            } else {
                results = reservationController.getAllReservations();
            }

            writeJsonResponse(exchange, listToJson(results), 200);
        }
    }

    private class ReportsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange.getResponseHeaders());
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            if ("OPTIONS".equalsIgnoreCase(method)) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            try {
                if ("GET".equalsIgnoreCase(method)) {
                    if (path.contains("/occupancy")) {
                        handleOccupancyReport(exchange, query);
                    } else if (path.contains("/revenue")) {
                        handleRevenueReport(exchange, query);
                    } else if (path.contains("/daily-checkins")) {
                        handleDailyCheckInOut(exchange, query);
                    } else {
                        exchange.sendResponseHeaders(404, -1);
                    }
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                writeJsonResponse(exchange, "{\"error\":\"" + escapeJson(e.getMessage()) + "\"}", 500);
            }
        }

        private void handleOccupancyReport(HttpExchange exchange, String query) throws IOException {
            LocalDate fromDate = LocalDate.now().minusDays(30);
            LocalDate toDate = LocalDate.now();

            String from = extractQueryParam(query, "from");
            String to = extractQueryParam(query, "to");

            if (from != null) fromDate = LocalDate.parse(from);
            if (to != null) toDate = LocalDate.parse(to);

            var report = reservationController.getOccupancyReport(fromDate, toDate);
            String json = mapToJson(report.toString());
            writeJsonResponse(exchange, json, 200);
        }

        private void handleRevenueReport(HttpExchange exchange, String query) throws IOException {
            LocalDate fromDate = LocalDate.now().minusDays(30);
            LocalDate toDate = LocalDate.now();

            String from = extractQueryParam(query, "from");
            String to = extractQueryParam(query, "to");

            if (from != null) fromDate = LocalDate.parse(from);
            if (to != null) toDate = LocalDate.parse(to);

            var report = reservationController.getRevenueReport(fromDate, toDate);
            String json = mapToJson(report.toString());
            writeJsonResponse(exchange, json, 200);
        }

        private void handleDailyCheckInOut(HttpExchange exchange, String query) throws IOException {
            LocalDate date = LocalDate.now();

            String dateStr = extractQueryParam(query, "date");
            if (dateStr != null) date = LocalDate.parse(dateStr);

            var summary = reservationController.getDailyCheckInCheckOut(date);
            String json = mapToJson(summary.toString());
            writeJsonResponse(exchange, json, 200);
        }
    }

    // Helpers
    private String listToJson(List<Reservation> list) {
        return list.stream().map(this::reservationToJson).collect(Collectors.joining(",", "[", "]"));
    }

    private String reservationToJson(Reservation r) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"reservationNumber\":\"").append(escapeJson(r.getReservationNumber())).append("\",");
        sb.append("\"guestName\":\"").append(escapeJson(r.getGuestName())).append("\",");
        sb.append("\"address\":\"").append(escapeJson(r.getAddress())).append("\",");
        sb.append("\"contactNumber\":\"").append(escapeJson(r.getContactNumber())).append("\",");
        sb.append("\"roomType\":\"").append(escapeJson(r.getRoomType().name())).append("\",");
        sb.append("\"checkInDate\":\"").append(r.getCheckInDate().toString()).append("\",");
        sb.append("\"checkOutDate\":\"").append(r.getCheckOutDate().toString()).append("\",");
        sb.append("\"totalAmount\":").append(r.getTotalAmount());
        sb.append("}");
        return sb.toString();
    }

    private void writeJsonResponse(HttpExchange exchange, String json, int status) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private String readBody(InputStream is) throws IOException {
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    private String extractJsonField(String json, String field) {
        if (json == null) return null;
        // Handle both quoted and unquoted key names: "field": or field:
        // First try quoted
        int idx = json.indexOf("\"" + field + "\"");
        if (idx == -1) {
            // Try unquoted
            idx = json.indexOf(field + ":");
            if (idx == -1) return null;
        }
        
        int colonIdx = json.indexOf(':', idx);
        if (colonIdx == -1) return null;
        int start = colonIdx + 1;
        
        // Skip whitespace
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }
        if (start >= json.length()) return null;
        
        char firstChar = json.charAt(start);
        if (firstChar == '"') {
            // Quoted string value
            start++;
            int end = start;
            while (end < json.length()) {
                if (json.charAt(end) == '"' && json.charAt(end - 1) != '\\') {
                    return json.substring(start, end);
                }
                end++;
            }
            return null;
        } else {
            // Unquoted value: read until comma or } or ]
            int end = start;
            while (end < json.length()) {
                char c = json.charAt(end);
                if (c == ',' || c == '}' || c == ']') {
                    return json.substring(start, end).trim();
                }
                end++;
            }
            return json.substring(start).trim();
        }
    }

    // escape special characters when generating JSON strings
    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r");
    }

    private String mapToJson(String mapString) {
        // Simple conversion of map toString to JSON-like format
        // For production, use a proper JSON library
        return mapString.replace("=", ":").replace(", ", ",");
    }

    private String extractQueryParam(String query, String param) {
        if (query == null || query.isEmpty()) return null;
        String[] params = query.split("&");
        for (String p : params) {
            if (p.startsWith(param + "=")) {
                return p.substring(param.length() + 1);
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        RestServer server = new RestServer();
        server.start();
    }
}
