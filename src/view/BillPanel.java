package view;

import controller.ReservationController;
import model.Reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.print.PrinterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Premium styled panel for calculating and printing bills.
 */
public class BillPanel extends JPanel {
    private final ReservationController reservationController;

    private JTextField searchField;
    private JButton searchButton;
    private JPanel billCard;
    private JButton printButton;
    private JLabel statusLabel;
    private Reservation currentReservation;

    // Premium color scheme
    private static final Color PRIMARY_DARK = new Color(15, 32, 55);
    private static final Color PRIMARY_BLUE = new Color(37, 99, 170);
    private static final Color ACCENT_GOLD = new Color(212, 175, 55);
    private static final Color CONTENT_BG = new Color(245, 247, 250);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color INPUT_BG = new Color(248, 249, 250);
    private static final Color INPUT_BORDER = new Color(206, 212, 218);
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color TEXT_MUTED = new Color(108, 117, 125);
    private static final Color SUCCESS_COLOR = new Color(25, 135, 84);
    private static final Color ERROR_COLOR = new Color(220, 53, 69);

    public BillPanel(ReservationController reservationController) {
        this.reservationController = reservationController;
        initializeUI();
    }

    private void initializeUI() {
        setBackground(CONTENT_BG);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Search card
        JPanel searchCard = createSearchCard();
        contentPanel.add(searchCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Bill card
        billCard = createBillCard();
        billCard.setVisible(false);
        contentPanel.add(billCard);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 25, 0));

        JLabel titleLabel = new JLabel("Generate Bill");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_DARK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Calculate and print the bill for a reservation");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_MUTED);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(subtitleLabel);

        return panel;
    }

    private JPanel createSearchCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 15));
                g2d.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, 15, 15);
                g2d.setColor(CARD_BG);
                g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(25, 30, 25, 30));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Search row
        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchRow.setOpaque(false);
        searchRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel searchLabel = new JLabel("Reservation Number");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchLabel.setForeground(TEXT_DARK);

        searchField = createStyledTextField();
        searchField.setPreferredSize(new Dimension(280, 45));

        searchButton = createStyledButton("Generate Bill", PRIMARY_BLUE);
        searchButton.setPreferredSize(new Dimension(140, 45));

        searchButton.addActionListener(e -> generateBill());
        searchField.addActionListener(e -> generateBill());

        searchRow.add(searchLabel);
        searchRow.add(searchField);
        searchRow.add(searchButton);

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setBorder(new EmptyBorder(10, 5, 0, 0));

        card.add(searchRow);
        card.add(statusLabel);

        return card;
    }

    private JPanel createBillCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 15));
                g2d.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, 15, 15);
                g2d.setColor(CARD_BG);
                g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(0, 0, 0, 0));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        return card;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_DARK);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, INPUT_BORDER),
            new EmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = bgColor;
                if (!isEnabled()) {
                    bg = new Color(180, 180, 180);
                } else if (getModel().isPressed()) {
                    bg = bgColor.darker();
                } else if (getModel().isRollover()) {
                    bg = bgColor.brighter();
                }

                g2d.setColor(bg);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void generateBill() {
        String reservationNumber = searchField.getText().trim();

        if (reservationNumber.isEmpty()) {
            showError("Please enter a reservation number");
            billCard.setVisible(false);
            return;
        }

        Reservation reservation = reservationController.findReservation(reservationNumber);

        if (reservation != null) {
            currentReservation = reservation;
            displayBill(reservation);
            showSuccess("Bill generated successfully");
        } else {
            showError("Reservation not found: " + reservationNumber);
            billCard.setVisible(false);
            currentReservation = null;
        }
    }

    private void displayBill(Reservation reservation) {
        billCard.removeAll();
        billCard.setBorder(new EmptyBorder(35, 40, 35, 40));

        // Bill header
        JPanel billHeader = new JPanel();
        billHeader.setOpaque(false);
        billHeader.setLayout(new BoxLayout(billHeader, BoxLayout.Y_AXIS));
        billHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Star icon
        JLabel starLabel = new JLabel("\u2605");
        starLabel.setFont(new Font("Serif", Font.PLAIN, 36));
        starLabel.setForeground(ACCENT_GOLD);
        starLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hotelName = new JLabel("OCEAN VIEW RESORT");
        hotelName.setFont(new Font("Serif", Font.BOLD, 24));
        hotelName.setForeground(PRIMARY_DARK);
        hotelName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hotelLocation = new JLabel("Galle, Sri Lanka");
        hotelLocation.setFont(new Font("Serif", Font.ITALIC, 14));
        hotelLocation.setForeground(TEXT_MUTED);
        hotelLocation.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel billTitle = new JLabel("INVOICE");
        billTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        billTitle.setForeground(PRIMARY_BLUE);
        billTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        billHeader.add(starLabel);
        billHeader.add(Box.createRigidArea(new Dimension(0, 8)));
        billHeader.add(hotelName);
        billHeader.add(Box.createRigidArea(new Dimension(0, 3)));
        billHeader.add(hotelLocation);
        billHeader.add(Box.createRigidArea(new Dimension(0, 20)));
        billHeader.add(billTitle);

        billCard.add(billHeader);
        billCard.add(Box.createRigidArea(new Dimension(0, 25)));

        // Reservation info bar
        JPanel infoBar = new JPanel(new GridLayout(1, 2, 20, 0));
        infoBar.setOpaque(false);
        infoBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel leftInfo = new JPanel();
        leftInfo.setOpaque(false);
        leftInfo.setLayout(new BoxLayout(leftInfo, BoxLayout.Y_AXIS));

        JLabel resLabel = new JLabel("Reservation #");
        resLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        resLabel.setForeground(TEXT_MUTED);
        JLabel resValue = new JLabel(reservation.getReservationNumber());
        resValue.setFont(new Font("Consolas", Font.BOLD, 14));
        resValue.setForeground(PRIMARY_BLUE);
        leftInfo.add(resLabel);
        leftInfo.add(resValue);

        JPanel rightInfo = new JPanel();
        rightInfo.setOpaque(false);
        rightInfo.setLayout(new BoxLayout(rightInfo, BoxLayout.Y_AXIS));
        rightInfo.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dateLabel.setForeground(TEXT_MUTED);
        JLabel dateValue = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        dateValue.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dateValue.setForeground(TEXT_DARK);
        rightInfo.add(dateLabel);
        rightInfo.add(dateValue);

        infoBar.add(leftInfo);
        infoBar.add(rightInfo);

        billCard.add(infoBar);
        billCard.add(Box.createRigidArea(new Dimension(0, 20)));

        // Divider
        billCard.add(createDivider());
        billCard.add(Box.createRigidArea(new Dimension(0, 20)));

        // Guest details section
        JLabel guestHeader = new JLabel("GUEST DETAILS");
        guestHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        guestHeader.setForeground(PRIMARY_BLUE);
        guestHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        billCard.add(guestHeader);
        billCard.add(Box.createRigidArea(new Dimension(0, 10)));

        billCard.add(createDetailRow("Name", reservation.getGuestName()));
        billCard.add(createDetailRow("Address", reservation.getAddress()));
        billCard.add(createDetailRow("Contact", reservation.getContactNumber()));
        billCard.add(Box.createRigidArea(new Dimension(0, 20)));

        // Room details section
        JLabel roomHeader = new JLabel("ROOM DETAILS");
        roomHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        roomHeader.setForeground(PRIMARY_BLUE);
        roomHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        billCard.add(roomHeader);
        billCard.add(Box.createRigidArea(new Dimension(0, 10)));

        billCard.add(createDetailRow("Room Type", reservation.getRoomType().getDisplayName()));
        billCard.add(createDetailRow("Check-in", reservation.getCheckInDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))));
        billCard.add(createDetailRow("Check-out", reservation.getCheckOutDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))));
        billCard.add(createDetailRow("Duration", reservation.getNumberOfNights() + " night(s)"));
        billCard.add(Box.createRigidArea(new Dimension(0, 20)));

        // Divider
        billCard.add(createDivider());
        billCard.add(Box.createRigidArea(new Dimension(0, 20)));

        // Charges section
        JLabel chargesHeader = new JLabel("CHARGES");
        chargesHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        chargesHeader.setForeground(PRIMARY_BLUE);
        chargesHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        billCard.add(chargesHeader);
        billCard.add(Box.createRigidArea(new Dimension(0, 10)));

        billCard.add(createChargeRow(
            reservation.getRoomType().getDisplayName() + " x " + reservation.getNumberOfNights() + " nights",
            String.format("LKR %,d", reservation.getRoomType().getPricePerNight()) + " x " + reservation.getNumberOfNights()
        ));
        billCard.add(Box.createRigidArea(new Dimension(0, 15)));

        // Total
        JPanel totalPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(245, 250, 255));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
        };
        totalPanel.setOpaque(false);
        totalPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        totalPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        totalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalLabel = new JLabel("TOTAL AMOUNT");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setForeground(TEXT_DARK);

        JLabel totalValue = new JLabel(String.format("LKR %,d", reservation.getTotalAmount()));
        totalValue.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalValue.setForeground(SUCCESS_COLOR);

        totalPanel.add(totalLabel, BorderLayout.WEST);
        totalPanel.add(totalValue, BorderLayout.EAST);

        billCard.add(totalPanel);
        billCard.add(Box.createRigidArea(new Dimension(0, 25)));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        printButton = createStyledButton("Print Bill", SUCCESS_COLOR);
        printButton.setPreferredSize(new Dimension(140, 45));
        printButton.addActionListener(e -> printBill());

        JButton clearButton = createStyledButton("Clear", new Color(108, 117, 125));
        clearButton.setPreferredSize(new Dimension(140, 45));
        clearButton.addActionListener(e -> clearBill());

        buttonPanel.add(printButton);
        buttonPanel.add(clearButton);

        billCard.add(buttonPanel);
        billCard.add(Box.createRigidArea(new Dimension(0, 20)));

        // Thank you message
        JLabel thankYou = new JLabel("Thank you for choosing Ocean View Resort!");
        thankYou.setFont(new Font("Serif", Font.ITALIC, 14));
        thankYou.setForeground(TEXT_MUTED);
        thankYou.setAlignmentX(Component.CENTER_ALIGNMENT);
        billCard.add(thankYou);

        billCard.setVisible(true);
        billCard.revalidate();
        billCard.repaint();
    }

    private JPanel createDivider() {
        JPanel divider = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(230, 230, 230));
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
                g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            }
        };
        divider.setOpaque(false);
        divider.setPreferredSize(new Dimension(0, 10));
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);
        return divider;
    }

    private JPanel createDetailRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelComp.setForeground(TEXT_MUTED);

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        valueComp.setForeground(TEXT_DARK);

        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.EAST);

        return row;
    }

    private JPanel createChargeRow(String description, String amount) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descComp = new JLabel(description);
        descComp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descComp.setForeground(TEXT_DARK);

        JLabel amountComp = new JLabel(amount);
        amountComp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        amountComp.setForeground(TEXT_DARK);

        row.add(descComp, BorderLayout.WEST);
        row.add(amountComp, BorderLayout.EAST);

        return row;
    }

    private void printBill() {
        if (currentReservation == null) {
            showError("No bill to print");
            return;
        }

        // Create printable text version
        String billText = reservationController.generateBill(currentReservation);

        JTextArea printArea = new JTextArea(billText);
        printArea.setFont(new Font("Consolas", Font.PLAIN, 12));

        try {
            boolean complete = printArea.print();
            if (complete) {
                showSuccess("Bill printed successfully");
            } else {
                showError("Printing cancelled");
            }
        } catch (PrinterException e) {
            showError("Error printing: " + e.getMessage());
            JOptionPane.showMessageDialog(
                this,
                "Error printing bill: " + e.getMessage(),
                "Print Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearBill() {
        searchField.setText("");
        billCard.setVisible(false);
        statusLabel.setText(" ");
        currentReservation = null;
        searchField.requestFocus();
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(ERROR_COLOR);
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(SUCCESS_COLOR);
    }

    // Custom rounded border class
    private static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }
    }
}
