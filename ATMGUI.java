import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMGUI extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel, loginPanel, atmPanel;

    private JPasswordField pinField;
    private JLabel balanceLabel;
    private double balance = 10000.0;
    private final int CORRECT_PIN = 1234;

    public ATMGUI() {
        setTitle("ATM Interface");
        setSize(400, 300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Confirm exit on close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createLoginPanel();
        createATMPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(atmPanel, "ATM");

        add(mainPanel);
        setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel pinLabel = new JLabel("Enter your 4-digit PIN:");
        pinField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            try {
                int enteredPin = Integer.parseInt(new String(pinField.getPassword()));
                if (enteredPin == CORRECT_PIN) {
                    JOptionPane.showMessageDialog(this, "✅ Welcome to your account!");
                    balanceLabel.setText("Current Balance: ₹" + String.format("%.2f", balance)); // Always refresh
                    cardLayout.show(mainPanel, "ATM");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Incorrect PIN", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a numeric PIN.");
            }
        });

        loginPanel.add(pinLabel);
        loginPanel.add(pinField);
        loginPanel.add(loginButton);
    }

    private void createATMPanel() {
        atmPanel = new JPanel();
        atmPanel.setLayout(new GridLayout(6, 1, 10, 10));
        atmPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        balanceLabel = new JLabel("Current Balance: ₹" + String.format("%.2f", balance));
        JButton checkButton = new JButton("Check Balance");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton logoutButton = new JButton("Logout");

        checkButton.addActionListener(e -> balanceLabel.setText("Current Balance: ₹" + String.format("%.2f", balance)));

        withdrawButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    if (amount <= 0 || amount > balance) {
                        JOptionPane.showMessageDialog(this, "❌ Invalid or Insufficient Balance.");
                    } else {
                        balance -= amount;
                        balanceLabel.setText("Current Balance: ₹" + String.format("%.2f", balance));
                        JOptionPane.showMessageDialog(this, "✅ ₹" + amount + " withdrawn.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number.");
                }
            }
        });

        depositButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(this, "❌ Enter a positive amount.");
                    } else {
                        balance += amount;
                        balanceLabel.setText("Current Balance: ₹" + String.format("%.2f", balance));
                        JOptionPane.showMessageDialog(this, "✅ ₹" + amount + " deposited.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number.");
                }
            }
        });

        logoutButton.addActionListener(e -> {
            pinField.setText("");
            balanceLabel.setText("Current Balance: ₹" + String.format("%.2f", balance)); // reset label
            cardLayout.show(mainPanel, "Login");
        });

        atmPanel.add(balanceLabel);
        atmPanel.add(checkButton);
        atmPanel.add(withdrawButton);
        atmPanel.add(depositButton);
        atmPanel.add(logoutButton);
    }

    public static void main(String[] args) {
        new ATMGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
