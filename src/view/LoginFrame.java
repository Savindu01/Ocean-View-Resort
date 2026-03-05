package view;

import controller.AuthController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Premium styled login window for user authentication.
 */
public class LoginFrame extends JFrame {
    private final AuthController authController;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    // Premium color scheme
    private static final Color PRIMARY_DARK = new Color(15, 32, 55);
    private static final Color PRIMARY_BLUE = new Color(37, 99, 170);
    private static final Color ACCENT_GOLD = new Color(212, 175, 55);
    private static final Color ACCENT_GOLD_LIGHT = new Color(241, 213, 146);
    private static final Color TEXT_LIGHT = new Color(248, 249, 250);
    private static final Color TEXT_MUTED = new Color(173, 181, 189);
    private static final Color CARD_BG = new Color(255, 255, 255);
    private static final Color INPUT_BG = new Color(248, 249, 250);
    private static final Color INPUT_BORDER = new Color(206, 212, 218);

    public LoginFrame(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Ocean View Resort");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(false);

        // Main container with split layout
        JPanel mainContainer = new JPanel(new GridLayout(1, 2));

        // Left panel - branding
        JPanel brandPanel = createBrandPanel();
        mainContainer.add(brandPanel);

        // Right panel - login form
        JPanel loginPanel = createLoginPanel();
        mainContainer.add(loginPanel);

        setContentPane(mainContainer);
    }

    private JPanel createBrandPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_DARK, getWidth(), getHeight(), PRIMARY_BLUE);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Decorative circles
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
                g2d.setColor(Color.WHITE);
                g2d.fillOval(-100, -100, 400, 400);
                g2d.fillOval(getWidth() - 200, getHeight() - 200, 350, 350);
            }
        };
        panel.setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Resort icon/logo placeholder
        JLabel iconLabel = new JLabel("\u2605");
        iconLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        iconLabel.setForeground(ACCENT_GOLD);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("OCEAN VIEW");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 42));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("R E S O R T");
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        subtitleLabel.setForeground(ACCENT_GOLD);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
                // Diamond in center
                int cx = getWidth() / 2;
                g2d.fillPolygon(new int[]{cx, cx + 8, cx, cx - 8}, new int[]{y - 8, y, y + 8, y}, 4);
            }
        };
        line.setOpaque(false);
        line.setPreferredSize(new Dimension(200, 30));
        line.setMaximumSize(new Dimension(200, 30));
        line.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel locationLabel = new JLabel("Galle, Sri Lanka");
        locationLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        locationLabel.setForeground(TEXT_MUTED);
        locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel taglineLabel = new JLabel("<html><center>Experience Luxury<br>By The Sea</center></html>");
        taglineLabel.setFont(new Font("Serif", Font.ITALIC, 14));
        taglineLabel.setForeground(new Color(200, 200, 200));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(iconLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(line);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(locationLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(taglineLabel);

        panel.add(contentPanel);
        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BG);
        panel.setLayout(new GridBagLayout());

        JPanel formContainer = new JPanel();
        formContainer.setOpaque(false);
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBorder(new EmptyBorder(40, 60, 40, 60));

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome Back");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(PRIMARY_DARK);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Sign in to continue to the management system");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(TEXT_MUTED);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formContainer.add(welcomeLabel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        formContainer.add(instructionLabel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 40)));

        // Username field
        formContainer.add(createLabel("USERNAME"));
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        usernameField = createStyledTextField();
        formContainer.add(usernameField);
        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password field
        formContainer.add(createLabel("PASSWORD"));
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        passwordField = createStyledPasswordField();
        formContainer.add(passwordField);
        formContainer.add(Box.createRigidArea(new Dimension(0, 30)));

        // Login button
        loginButton = createStyledButton("Sign In");
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(loginButton);
        formContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(220, 53, 69));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(statusLabel);

        formContainer.add(Box.createVerticalGlue());

        // Footer hint
        JLabel hintLabel = new JLabel("<html><center>Default credentials: <b>admin</b> / <b>admin123</b></center></html>");
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hintLabel.setForeground(TEXT_MUTED);
        hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(hintLabel);

        panel.add(formContainer);

        // Action listeners
        loginButton.addActionListener(e -> performLogin());

        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(new Color(108, 117, 125));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(INPUT_BG);
        field.setForeground(PRIMARY_DARK);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, INPUT_BORDER),
            new EmptyBorder(12, 15, 12, 15)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(INPUT_BG);
        field.setForeground(PRIMARY_DARK);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, INPUT_BORDER),
            new EmptyBorder(12, 15, 12, 15)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(PRIMARY_DARK);
                } else if (getModel().isRollover()) {
                    g2d.setColor(PRIMARY_BLUE.brighter());
                } else {
                    GradientPaint gp = new GradientPaint(0, 0, PRIMARY_BLUE, 0, getHeight(), PRIMARY_DARK);
                    g2d.setPaint(gp);
                }
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
        button.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password");
            return;
        }

        loginButton.setEnabled(false);
        statusLabel.setText("Authenticating...");
        statusLabel.setForeground(PRIMARY_BLUE);

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                return authController.login(username, password);
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        dispose();
                        MainFrame mainFrame = new MainFrame(authController);
                        mainFrame.setVisible(true);
                    } else {
                        statusLabel.setText("Invalid username or password");
                        statusLabel.setForeground(new Color(220, 53, 69));
                        passwordField.setText("");
                        loginButton.setEnabled(true);
                    }
                } catch (Exception ex) {
                    statusLabel.setText("Login error: " + ex.getMessage());
                    statusLabel.setForeground(new Color(220, 53, 69));
                    loginButton.setEnabled(true);
                }
            }
        };
        worker.execute();
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
