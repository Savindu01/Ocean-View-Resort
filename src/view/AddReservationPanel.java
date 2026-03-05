package view;

import controller.ReservationController;
import model.Reservation;
import model.RoomType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Premium styled panel for adding new reservations.
 */
public class AddReservationPanel extends JPanel {
    private final ReservationController reservationController;

    private JTextField guestNameField;
    private JTextArea addressArea;
    private JTextField contactField;
    private JComboBox<RoomType> roomTypeCombo;
    private JSpinner checkInSpinner;
    private JSpinner checkOutSpinner;
    private JButton submitButton;
    private JButton clearButton;
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

    public AddReservationPanel(ReservationController reservationController) {
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

        // Form card
        JPanel formCard = createFormCard();
        add(formCard, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 25, 0));

        JLabel titleLabel = new JLabel("New Reservation");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_DARK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Fill in the guest details to create a new booking");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_MUTED);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(subtitleLabel);

        return panel;
    }

    private JPanel createFormCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2d.setColor(new Color(0, 0, 0, 15));
                g2d.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, 15, 15);

                // Card background
                g2d.setColor(CARD_BG);
                g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(35, 40, 35, 40));

        // Form content
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Guest Name
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(createLabel("Guest Name"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        guestNameField = createStyledTextField();
        formPanel.add(guestNameField, gbc);
        row++;

        // Address
        gbc.gridx = 0; gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(createLabel("Address"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        addressArea = new JTextArea(3, 30);
        addressArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        addressArea.setBackground(INPUT_BG);
        addressArea.setBorder(new EmptyBorder(10, 12, 10, 12));
        JScrollPane addressScroll = new JScrollPane(addressArea);
        addressScroll.setBorder(BorderFactory.createLineBorder(INPUT_BORDER, 1));
        addressScroll.setPreferredSize(new Dimension(350, 80));
        formPanel.add(addressScroll, gbc);
        row++;

        // Contact Number
        gbc.gridx = 0; gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(createLabel("Contact Number"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        contactField = createStyledTextField();
        formPanel.add(contactField, gbc);
        row++;

        // Room Type
        gbc.gridx = 0; gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("Room Type"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        roomTypeCombo = createStyledComboBox();
        formPanel.add(roomTypeCombo, gbc);
        row++;

        // Dates panel (side by side)
        gbc.gridx = 0; gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("Check-in Date"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        checkInSpinner = createStyledDateSpinner();
        formPanel.add(checkInSpinner, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("Check-out Date"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        checkOutSpinner = createStyledDateSpinner();
        SpinnerDateModel model = (SpinnerDateModel) checkOutSpinner.getModel();
        model.setValue(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        formPanel.add(checkOutSpinner, gbc);
        row++;

        // Buttons
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 15, 10, 15);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        submitButton = createStyledButton("Create Reservation", PRIMARY_BLUE);
        clearButton = createStyledButton("Clear Form", new Color(108, 117, 125));

        submitButton.addActionListener(e -> submitReservation());
        clearButton.addActionListener(e -> clearForm());

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        formPanel.add(buttonPanel, gbc);
        row++;

        // Status label
        gbc.gridy = row;
        gbc.insets = new Insets(10, 15, 10, 15);
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(statusLabel, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_DARK);
        label.setPreferredSize(new Dimension(130, 25));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(25) {
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
            new EmptyBorder(10, 12, 10, 12)
        ));
        field.setPreferredSize(new Dimension(350, 42));
        return field;
    }

    private JComboBox<RoomType> createStyledComboBox() {
        JComboBox<RoomType> combo = new JComboBox<>(RoomType.values());
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(INPUT_BG);
        combo.setPreferredSize(new Dimension(350, 42));
        combo.setBorder(BorderFactory.createLineBorder(INPUT_BORDER, 1));

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(new EmptyBorder(8, 12, 8, 12));
                if (isSelected) {
                    setBackground(PRIMARY_BLUE);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(TEXT_DARK);
                }
                return this;
            }
        });

        return combo;
    }

    private JSpinner createStyledDateSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(new Date());
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinner.setPreferredSize(new Dimension(350, 42));

        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setBackground(INPUT_BG);
            tf.setBorder(new EmptyBorder(5, 12, 5, 12));
        }

        return spinner;
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
        button.setPreferredSize(new Dimension(180, 45));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void submitReservation() {
        String guestName = guestNameField.getText().trim();
        String address = addressArea.getText().trim();
        String contact = contactField.getText().trim();
        RoomType roomType = (RoomType) roomTypeCombo.getSelectedItem();

        Date checkInDate = (Date) checkInSpinner.getValue();
        Date checkOutDate = (Date) checkOutSpinner.getValue();

        LocalDate checkIn = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOut = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (guestName.isEmpty()) {
            showError("Please enter guest name");
            guestNameField.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            showError("Please enter address");
            addressArea.requestFocus();
            return;
        }
        if (contact.isEmpty()) {
            showError("Please enter contact number");
            contactField.requestFocus();
            return;
        }

        try {
            Reservation reservation = reservationController.createReservation(
                guestName, address, contact, roomType, checkIn, checkOut
            );

            if (reservation != null) {
                showSuccess("Reservation created successfully!");
                clearForm();

                // Show premium confirmation dialog
                showConfirmationDialog(reservation);
            } else {
                showError("Failed to create reservation. Please try again.");
            }
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private void showConfirmationDialog(Reservation reservation) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Reservation Confirmed", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Success icon
        JLabel iconLabel = new JLabel("\u2713");
        iconLabel.setFont(new Font("Serif", Font.BOLD, 48));
        iconLabel.setForeground(SUCCESS_COLOR);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Booking Confirmed!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(PRIMARY_DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel resNumLabel = new JLabel(reservation.getReservationNumber());
        resNumLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        resNumLabel.setForeground(PRIMARY_BLUE);
        resNumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel detailsLabel = new JLabel("<html><center>" +
            "Guest: " + reservation.getGuestName() + "<br>" +
            "Room: " + reservation.getRoomType().getDisplayName() + "<br>" +
            "Check-in: " + reservation.getCheckInDate() + "<br>" +
            "Check-out: " + reservation.getCheckOutDate() +
            "</center></html>");
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailsLabel.setForeground(TEXT_MUTED);
        detailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okButton = createStyledButton("Done", PRIMARY_BLUE);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(e -> dialog.dispose());

        content.add(iconLabel);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(titleLabel);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        content.add(resNumLabel);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(detailsLabel);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        content.add(okButton);

        dialog.add(content);
        dialog.setVisible(true);
    }

    private void clearForm() {
        guestNameField.setText("");
        addressArea.setText("");
        contactField.setText("");
        roomTypeCombo.setSelectedIndex(0);
        checkInSpinner.setValue(new Date());
        SpinnerDateModel model = (SpinnerDateModel) checkOutSpinner.getModel();
        model.setValue(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        statusLabel.setText(" ");
        guestNameField.requestFocus();
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
