package view;

import controller.ReservationController;
import model.Reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Premium styled panel for displaying reservation details.
 */
public class DisplayReservationPanel extends JPanel {
    private final ReservationController reservationController;

    private JTextField searchField;
    private JButton searchButton;
    private JPanel detailsCard;
    private JLabel statusLabel;

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

    public DisplayReservationPanel(ReservationController reservationController) {
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

        // Details card
        detailsCard = createDetailsCard();
        detailsCard.setVisible(false);
        contentPanel.add(detailsCard);

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

        JLabel titleLabel = new JLabel("View Reservation");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_DARK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Search for a reservation by its confirmation number");
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

        searchButton = createStyledButton("Search", PRIMARY_BLUE);
        searchButton.setPreferredSize(new Dimension(120, 45));

        searchButton.addActionListener(e -> searchReservation());
        searchField.addActionListener(e -> searchReservation());

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

    private JPanel createDetailsCard() {
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
        card.setBorder(new EmptyBorder(30, 35, 30, 35));
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
                if (getModel().isPressed()) {
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

    private void searchReservation() {
        String reservationNumber = searchField.getText().trim();

        if (reservationNumber.isEmpty()) {
            showError("Please enter a reservation number");
            detailsCard.setVisible(false);
            return;
        }

        Reservation reservation = reservationController.findReservation(reservationNumber);

        if (reservation != null) {
            displayReservationDetails(reservation);
            statusLabel.setText(" ");
        } else {
            showError("Reservation not found: " + reservationNumber);
            detailsCard.setVisible(false);
        }
    }

    private void displayReservationDetails(Reservation reservation) {
        detailsCard.removeAll();

        // Header with reservation number
        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel titleLabel = new JLabel("Reservation Details");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setForeground(PRIMARY_DARK);

        JLabel resNumBadge = new JLabel(reservation.getReservationNumber()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(232, 244, 253));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        resNumBadge.setFont(new Font("Consolas", Font.BOLD, 14));
        resNumBadge.setForeground(PRIMARY_BLUE);
        resNumBadge.setBorder(new EmptyBorder(8, 15, 8, 15));
        resNumBadge.setOpaque(false);

        headerRow.add(titleLabel, BorderLayout.WEST);
        headerRow.add(resNumBadge, BorderLayout.EAST);

        detailsCard.add(headerRow);
        detailsCard.add(Box.createRigidArea(new Dimension(0, 25)));

        // Details sections
        JPanel sectionsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        sectionsPanel.setOpaque(false);
        sectionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Guest Information
        JPanel guestSection = createSection("Guest Information",
            new String[][]{
                {"Name", reservation.getGuestName()},
                {"Address", reservation.getAddress()},
                {"Contact", reservation.getContactNumber()}
            }
        );

        // Room Information
        JPanel roomSection = createSection("Room Information",
            new String[][]{
                {"Room Type", reservation.getRoomType().getDisplayName()},
                {"Rate/Night", String.format("LKR %,d", reservation.getRoomType().getPricePerNight())}
            }
        );

        // Stay Information
        JPanel staySection = createSection("Stay Information",
            new String[][]{
                {"Check-in", reservation.getCheckInDate().toString()},
                {"Check-out", reservation.getCheckOutDate().toString()},
                {"Nights", String.valueOf(reservation.getNumberOfNights())}
            }
        );

        sectionsPanel.add(guestSection);
        sectionsPanel.add(roomSection);
        sectionsPanel.add(staySection);

        detailsCard.add(sectionsPanel);
        detailsCard.add(Box.createRigidArea(new Dimension(0, 25)));

        // Divider
        JPanel divider = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(230, 230, 230));
                g.fillRect(0, getHeight() / 2, getWidth(), 1);
            }
        };
        divider.setOpaque(false);
        divider.setPreferredSize(new Dimension(0, 20));
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsCard.add(divider);
        detailsCard.add(Box.createRigidArea(new Dimension(0, 15)));

        // Total amount
        JPanel totalRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        totalRow.setOpaque(false);
        totalRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel totalLabel = new JLabel("Total Amount: ");
        totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        totalLabel.setForeground(TEXT_MUTED);

        JLabel totalValue = new JLabel(String.format("LKR %,d", reservation.getTotalAmount()));
        totalValue.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalValue.setForeground(SUCCESS_COLOR);

        totalRow.add(totalLabel);
        totalRow.add(totalValue);

        detailsCard.add(totalRow);

        // Booking timestamp
        if (reservation.getCreatedAt() != null) {
            detailsCard.add(Box.createRigidArea(new Dimension(0, 15)));
            JLabel timestampLabel = new JLabel("Booked on: " + reservation.getCreatedAt().toString());
            timestampLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            timestampLabel.setForeground(TEXT_MUTED);
            timestampLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsCard.add(timestampLabel);
        }

        detailsCard.setVisible(true);
        detailsCard.revalidate();
        detailsCard.repaint();
    }

    private JPanel createSection(String title, String[][] data) {
        JPanel section = new JPanel();
        section.setOpaque(false);
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(PRIMARY_BLUE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        section.add(titleLabel);

        for (String[] row : data) {
            JPanel rowPanel = new JPanel();
            rowPanel.setOpaque(false);
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
            rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            rowPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

            JLabel labelComponent = new JLabel(row[0]);
            labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            labelComponent.setForeground(TEXT_MUTED);

            JLabel valueComponent = new JLabel(row[1]);
            valueComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            valueComponent.setForeground(TEXT_DARK);

            rowPanel.add(labelComponent);
            rowPanel.add(valueComponent);
            section.add(rowPanel);
        }

        return section;
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(ERROR_COLOR);
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
