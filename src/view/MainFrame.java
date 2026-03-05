package view;

import controller.AuthController;
import controller.ReservationController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Premium styled main dashboard frame with navigation menu.
 */
public class MainFrame extends JFrame {
    private final AuthController authController;
    private final ReservationController reservationController;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JButton activeButton;

    // Premium color scheme
    private static final Color PRIMARY_DARK = new Color(15, 32, 55);
    private static final Color PRIMARY_BLUE = new Color(37, 99, 170);
    private static final Color ACCENT_GOLD = new Color(212, 175, 55);
    private static final Color SIDEBAR_BG = new Color(22, 33, 62);
    private static final Color SIDEBAR_HOVER = new Color(32, 48, 82);
    private static final Color SIDEBAR_ACTIVE = new Color(37, 99, 170);
    private static final Color CONTENT_BG = new Color(245, 247, 250);
    private static final Color TEXT_LIGHT = new Color(248, 249, 250);
    private static final Color TEXT_MUTED = new Color(173, 181, 189);

    // Panel names
    private static final String PANEL_ADD = "ADD_RESERVATION";
    private static final String PANEL_DISPLAY = "DISPLAY_RESERVATION";
    private static final String PANEL_BILL = "BILL";
    private static final String PANEL_WELCOME = "WELCOME";

    public MainFrame(AuthController authController) {
        this.authController = authController;
        this.reservationController = new ReservationController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Ocean View Resort - Reservation Management System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 650));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        setLayout(new BorderLayout());

        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);

        // Add panels
        contentPanel.add(createWelcomePanel(), PANEL_WELCOME);
        contentPanel.add(new AddReservationPanel(reservationController), PANEL_ADD);
        contentPanel.add(new DisplayReservationPanel(reservationController), PANEL_DISPLAY);
        contentPanel.add(new BillPanel(reservationController), PANEL_BILL);

        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, PANEL_WELCOME);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Header panel with branding
        JPanel headerPanel = createHeaderPanel();
        sidebar.add(headerPanel);

        // Navigation section
        JPanel navSection = new JPanel();
        navSection.setOpaque(false);
        navSection.setLayout(new BoxLayout(navSection, BoxLayout.Y_AXIS));
        navSection.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel navLabel = new JLabel("NAVIGATION");
        navLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        navLabel.setForeground(TEXT_MUTED);
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navLabel.setBorder(new EmptyBorder(0, 10, 10, 0));
        navSection.add(navLabel);

        // Menu buttons
        JButton addButton = createMenuButton("New Reservation", "\u2795", PANEL_ADD);
        JButton displayButton = createMenuButton("View Reservation", "\uD83D\uDD0D", PANEL_DISPLAY);
        JButton billButton = createMenuButton("Generate Bill", "\uD83D\uDCB3", PANEL_BILL);

        navSection.add(addButton);
        navSection.add(Box.createRigidArea(new Dimension(0, 8)));
        navSection.add(displayButton);
        navSection.add(Box.createRigidArea(new Dimension(0, 8)));
        navSection.add(billButton);

        sidebar.add(navSection);
        sidebar.add(Box.createVerticalGlue());

        // User section
        JPanel userSection = createUserSection();
        sidebar.add(userSection);

        return sidebar;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_DARK, 0, getHeight(), SIDEBAR_BG);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setMaximumSize(new Dimension(280, 140));
        headerPanel.setPreferredSize(new Dimension(280, 140));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(25, 20, 25, 20));

        // Star icon
        JLabel iconLabel = new JLabel("\u2605");
        iconLabel.setFont(new Font("Serif", Font.PLAIN, 28));
        iconLabel.setForeground(ACCENT_GOLD);
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel("OCEAN VIEW");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("R E S O R T");
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        subtitleLabel.setForeground(ACCENT_GOLD);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createUserSection() {
        JPanel userSection = new JPanel();
        userSection.setBackground(new Color(18, 27, 50));
        userSection.setMaximumSize(new Dimension(280, 130));
        userSection.setPreferredSize(new Dimension(280, 130));
        userSection.setLayout(new BoxLayout(userSection, BoxLayout.Y_AXIS));
        userSection.setBorder(new EmptyBorder(15, 20, 15, 20));

        // User info
        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        userInfo.setOpaque(false);
        userInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Avatar circle
        JLabel avatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PRIMARY_BLUE);
                g2d.fillOval(0, 0, 40, 40);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                String initial = authController.getCurrentUser().getUsername().substring(0, 1).toUpperCase();
                FontMetrics fm = g2d.getFontMetrics();
                int x = (40 - fm.stringWidth(initial)) / 2;
                int y = (40 + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(initial, x, y);
            }
        };
        avatar.setPreferredSize(new Dimension(40, 40));

        JPanel userText = new JPanel();
        userText.setOpaque(false);
        userText.setLayout(new BoxLayout(userText, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel(authController.getCurrentUser().getUsername());
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setForeground(TEXT_LIGHT);

        JLabel roleLabel = new JLabel("Administrator");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        roleLabel.setForeground(TEXT_MUTED);

        userText.add(usernameLabel);
        userText.add(roleLabel);

        userInfo.add(avatar);
        userInfo.add(userText);

        userSection.add(userInfo);
        userSection.add(Box.createRigidArea(new Dimension(0, 15)));

        // Logout button
        JButton logoutButton = new JButton("Sign Out") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2d.setColor(new Color(192, 57, 43));
                } else {
                    g2d.setColor(new Color(231, 76, 60));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setMaximumSize(new Dimension(240, 38));
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> performLogout());

        userSection.add(logoutButton);

        return userSection;
    }

    private JButton createMenuButton(String text, String icon, String panelName) {
        JButton button = new JButton(icon + "   " + text) {
            private boolean isActive = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (this == activeButton) {
                    g2d.setColor(SIDEBAR_ACTIVE);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                } else if (getModel().isRollover()) {
                    g2d.setColor(SIDEBAR_HOVER);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }

                g2d.setColor(this == activeButton ? Color.WHITE : TEXT_MUTED);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), 15, y);

                // Active indicator
                if (this == activeButton) {
                    g2d.setColor(ACCENT_GOLD);
                    g2d.fillRoundRect(getWidth() - 4, 10, 4, getHeight() - 20, 2, 2);
                }
            }
        };

        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_MUTED);
        button.setMaximumSize(new Dimension(250, 48));
        button.setPreferredSize(new Dimension(250, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addActionListener(e -> {
            activeButton = button;
            cardLayout.show(contentPanel, panelName);
            getContentPane().repaint();
        });

        return button;
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Subtle gradient background
                GradientPaint gp = new GradientPaint(0, 0, CONTENT_BG, getWidth(), getHeight(), new Color(235, 240, 248));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Welcome header
        JLabel welcomeLabel = new JLabel("Welcome to Ocean View Resort");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 38));
        welcomeLabel.setForeground(PRIMARY_DARK);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel locationLabel = new JLabel("Galle, Sri Lanka");
        locationLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        locationLabel.setForeground(PRIMARY_BLUE);
        locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Decorative line
        JPanel line = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(ACCENT_GOLD);
                g2d.setStroke(new BasicStroke(2));
                int y = getHeight() / 2;
                g2d.drawLine(0, y, getWidth(), y);
                int cx = getWidth() / 2;
                g2d.fillPolygon(new int[]{cx, cx + 6, cx, cx - 6}, new int[]{y - 6, y, y + 6, y}, 4);
            }
        };
        line.setOpaque(false);
        line.setPreferredSize(new Dimension(150, 25));
        line.setMaximumSize(new Dimension(150, 25));
        line.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("Reservation Management System");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descLabel.setForeground(new Color(108, 117, 125));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Room rates card
        JPanel ratesCard = createRatesCard();
        ratesCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Quick actions
        JPanel actionsPanel = createQuickActions();
        actionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        centerPanel.add(locationLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(line);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(descLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        centerPanel.add(ratesCard);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(actionsPanel);

        panel.add(centerPanel);
        return panel;
    }

    private JPanel createRatesCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, 20, 20);

                // Card background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 20, 20);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(25, 30, 25, 30));
        card.setPreferredSize(new Dimension(500, 220));
        card.setMaximumSize(new Dimension(500, 220));

        // Header
        JLabel headerLabel = new JLabel("Room Rates (per night)");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(PRIMARY_DARK);
        headerLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Rates grid
        JPanel ratesGrid = new JPanel(new GridLayout(2, 2, 20, 15));
        ratesGrid.setOpaque(false);

        String[][] rates = {
            {"Single Room", "LKR 5,000", "\uD83D\uDECF"},
            {"Double Room", "LKR 8,000", "\uD83D\uDECF\uD83D\uDECF"},
            {"Family Room", "LKR 12,000", "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67"},
            {"Suite", "LKR 20,000", "\u2B50"}
        };

        for (String[] rate : rates) {
            JPanel rateItem = new JPanel();
            rateItem.setOpaque(false);
            rateItem.setLayout(new BoxLayout(rateItem, BoxLayout.Y_AXIS));

            JLabel typeLabel = new JLabel(rate[2] + " " + rate[0]);
            typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            typeLabel.setForeground(new Color(108, 117, 125));

            JLabel priceLabel = new JLabel(rate[1]);
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            priceLabel.setForeground(PRIMARY_BLUE);

            rateItem.add(typeLabel);
            rateItem.add(Box.createRigidArea(new Dimension(0, 3)));
            rateItem.add(priceLabel);
            ratesGrid.add(rateItem);
        }

        card.add(headerLabel, BorderLayout.NORTH);
        card.add(ratesGrid, BorderLayout.CENTER);

        return card;
    }

    private JPanel createQuickActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setOpaque(false);

        String[][] actions = {
            {"New Booking", PANEL_ADD},
            {"Find Reservation", PANEL_DISPLAY},
            {"Generate Bill", PANEL_BILL}
        };

        for (String[] action : actions) {
            JButton btn = new JButton(action[0]) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    if (getModel().isRollover()) {
                        GradientPaint gp = new GradientPaint(0, 0, PRIMARY_BLUE, 0, getHeight(), PRIMARY_DARK);
                        g2d.setPaint(gp);
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                        g2d.setColor(Color.WHITE);
                    } else {
                        g2d.setColor(Color.WHITE);
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                        g2d.setColor(PRIMARY_BLUE);
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                    }

                    g2d.setFont(getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = (getWidth() - fm.stringWidth(getText())) / 2;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2d.drawString(getText(), x, y);
                }
            };
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setPreferredSize(new Dimension(150, 45));
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            String panelName = action[1];
            btn.addActionListener(e -> cardLayout.show(contentPanel, panelName));

            panel.add(btn);
        }

        return panel;
    }

    private void performLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to sign out?",
            "Confirm Sign Out",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            authController.logout();
            dispose();
            LoginFrame loginFrame = new LoginFrame(authController);
            loginFrame.setVisible(true);
        }
    }
}
