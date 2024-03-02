import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Clasa Login este folosita pentru a autentifica utilizatorul in aplicatie
 */
public class Login extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOk;
    private JButton btnCancel;
    private JPanel loginPanel;

    /**
     * Constructorul clasei Login
     * @param parent
     */
    public Login(JFrame parent) {
        /**
         * Apelam constructorul clasei de baza
         * Setam titlul ferestrei
         * Setam continutul ferestrei
         * Setam dimensiunea minima a ferestrei
         * Setam fereastra ca fiind modala
         * Setam locatia ferestrei
         * Setam operatia de inchidere a ferestrei
         */
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        /**
         * Adaugam un ascultator pentru butonul de login
         * Adaugam un ascultator pentru butonul de cancel
         * Afisam fereastra
         */
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = new String(pfPassword.getPassword());
                user = getAuth(email, password);
                if (user != null) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Date incorecte!");
                }
            }
        });
        btnCancel.addActionListener(e -> setVisible(false));
        setVisible(true);
    }

    public User user;

    /**
     * Metoda getAuth este folosita pentru a verifica daca datele introduse de utilizator sunt corecte
     * @param email
     * @param password
     * @return
     */
    private User getAuth(String email, String password) {
        User user = null;
        final String DB_URL = "jdbc:sqlite:/C:/MyStore/ProiectP3/identifier.sqlite";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM login WHERE email=? AND parola=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.email = resultSet.getString("email");
                        user.parola = resultSet.getString("parola");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Metoda closeLoginWindow este folosita pentru a inchide fereastra de login
     */
    public void closeLoginWindow() {
        setVisible(false);
    }

    /**
     * Main-ul aplicatiei este folosit pentru a porni aplicatia si a afisa fereastra de login daca utilizatorul nu este autentificat
     * Daca utilizatorul este autentificat, se va afisa fereastra principala a aplicatiei
     * @param args
     */
    public static void main(String[] args) {
        Login login = new Login(null);
        User user = login.user;
        if (user != null) {
           MyApplication aplicatie = MyApplication.getInstance();
           aplicatie.showWindow();
           login.closeLoginWindow();



        }

    }
}
