package parking_v3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationPage extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    // Variable statique pour indiquer l'état de connexion
    public static boolean isConnected = false;

    public AuthenticationPage() {
        setTitle("Authentification");
        setSize(400, 200);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        JLabel passwordLabel = new JLabel("Mot de passe:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Se connecter");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (authenticateUser(username, password)) {
                    isConnected = true; // Indiquer que l'utilisateur est connecté
                    dispose(); // Fermer la fenêtre de connexion
                    Enregistrement enregistrement = new Enregistrement();
                    enregistrement.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(AuthenticationPage.this,
                            "Nom d'utilisateur ou mot de passe incorrect",
                            "Erreur d'authentification",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(loginButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean authenticateUser(String username, String password) {
        // Méthode pour authentifier l'utilisateur
        // Elle devrait vérifier les informations dans la base de données
        // Ici, je suppose simplement que l'authentification est réussie si le nom d'utilisateur et le mot de passe ne sont pas vides
        return !username.isEmpty() && !password.isEmpty();
    }

    public static void main(String[] args) {
        AuthenticationPage login = new AuthenticationPage();
        login.setVisible(true);
    }
}
