import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;

/**
 * Clasa MyApplication conține funcționalitățile aplicației.
 */
public class MyApplication {
    private static MyApplication instance;
    private MyApplication() {
        // Constructor privat pentru a preveni crearea de instanțe multiple
    }

    /**
     * Interfața grafică a aplicației.
     */
    JTextField jtf_idArticol, jtf_idCategorie, jtf_idProducator, jtf_descriere, jtf_stoc, jtf_pret,jtf_denumire;
    JButton jb_add, jb_delete, jb_update, jb_search;
    JTable jt;
    JFrame frame;
    JLabel lbl_idArticol, lbl_idCategorie, lbl_idProducator, lbl_descriere, lbl_stoc, lbl_pret, lbl_denumire;
    /**
     * Header-ul tabelului cu articole.
     */
    String header[] = new String[]{
            "ID Articol",
            "Denumire",
            "ID Categorie",
            "ID Producator",
            "Descriere",
            "Stoc",
            "Pret",
            "Producator"
    };
    /**
     * Modelul tabelului cu articole.
     */
    DefaultTableModel dtm = new DefaultTableModel(0, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    /**
     * URL-ul bazei de date.
     */
    String url = "jdbc:sqlite:/C:/MyStore/ProiectP3/identifier.sqlite";

    /**
     * Funcția getInstance returnează instanța curentă a aplicației.
     * @return
     */
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    /**
     * Funcția showWindow afișează fereastra principală a aplicației.
     */
    public void showWindow() {
        if (frame == null) {
            frame = new JFrame("Test Window");
            mainInterface();
            afiseazaToateArticolele();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 900);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            frame.setVisible(true);
        } else {
            frame.setVisible(true);
        }
    }

    /**
     * Funcția mainInterface afișează interfața grafică a aplicației.
     */
    private void mainInterface() {
        frame = new JFrame();

        lbl_denumire = new JLabel();
        lbl_denumire.setText("Denumire");
        lbl_denumire.setBounds(10, 25, 100, 25);
        frame.add(lbl_denumire);

        jtf_denumire = new JTextField();
        jtf_denumire.setBounds(150, 25, 200, 25);
        frame.add(jtf_denumire);

        lbl_idCategorie = new JLabel();
        lbl_idCategorie.setText("ID Categorie");
        lbl_idCategorie.setBounds(10, 50, 100, 25);
        frame.add(lbl_idCategorie);

        jtf_idCategorie = new JTextField();
        jtf_idCategorie.setBounds(150, 50, 200, 25);
        frame.add(jtf_idCategorie);

        lbl_idProducator = new JLabel();
        lbl_idProducator.setText("ID Producator");
        lbl_idProducator.setBounds(10, 75, 100, 25);
        frame.add(lbl_idProducator);

        jtf_idProducator = new JTextField();
        jtf_idProducator.setBounds(150, 75, 200, 25);
        frame.add(jtf_idProducator);

        lbl_descriere = new JLabel();
        lbl_descriere.setText("Descriere");
        lbl_descriere.setBounds(10, 100, 100, 25);
        frame.add(lbl_descriere);

        jtf_descriere = new JTextField();
        jtf_descriere.setBounds(150, 100, 200, 25);
        frame.add(jtf_descriere);

        lbl_stoc = new JLabel();
        lbl_stoc.setText("Stoc");
        lbl_stoc.setBounds(10, 125, 100, 25);
        frame.add(lbl_stoc);

        jtf_stoc = new JTextField();
        jtf_stoc.setBounds(150, 125, 200, 25);
        frame.add(jtf_stoc);

        lbl_pret = new JLabel();
        lbl_pret.setText("Pret");
        lbl_pret.setBounds(10, 150, 100, 25);
        frame.add(lbl_pret);

        jtf_pret = new JTextField();
        jtf_pret.setBounds(150, 150, 200, 25);
        frame.add(jtf_pret);

        /**
         * Butonul de adăugare. La apăsarea acestuia, se apelează funcția adaugaArticol.
         */
        jb_add = new JButton();
        jb_add.setText("Add");
        jb_add.setBounds(10, 220, 150, 25);
        frame.add(jb_add);

        // Adăugăm un listener pentru butonul de adăugare
        jb_add.addActionListener(this::adaugaArticol);

        /**
         * Butonul de ștergere. La apăsarea acestuia, se apelează funcția stergeArticolDinBazaDeDate.
         */
        jb_delete = new JButton();
        jb_delete.setText("Delete");
        jb_delete.setBounds(170, 220, 150, 25);
        frame.add(jb_delete);

        // Adăugăm un listener pentru butonul de ștergere
        jb_delete.addActionListener(e -> {
            // Afișăm un pop-up pentru introducerea ID-ului articolului
            String idArticolString = JOptionPane.showInputDialog(frame, "Introduceți ID-ul articolului pentru ștergere:");

            // Verificăm dacă s-a introdus un ID valid
            if (idArticolString == null || idArticolString.trim().isEmpty()) {
                return;
            }

            try {
                // Convertim ID-ul introdus într-un număr întreg
                int idArticol = Integer.parseInt(idArticolString);

                // Verificăm dacă articolul cu ID-ul specificat există
                if (getArticolById(idArticol) == null) {
                    showMessage("Articolul cu ID-ul specificat nu există.", "Eroare");
                    return;
                }

                // Apelăm funcția pentru ștergerea articolului din baza de date
                stergeArticolDinBazaDeDate(idArticol);

                // Actualizăm tabelul cu articole
                afiseazaToateArticolele();
            } catch (NumberFormatException ex) {
                showMessage("Introduceți un ID valid (număr întreg).", "Eroare");
            }
        });

        /**
         * Butonul de actualizare. La apăsarea acestuia, se apelează funcția updateArticolInBazaDeDate.
         */

        jb_update = new JButton();
        jb_update.setText("Update");
        jb_update.setBounds(330, 220, 150, 25);
        frame.add(jb_update);
        jb_update.addActionListener(e -> {
            // Afișăm un pop-up pentru introducerea ID-ului articolului pentru actualizare
            String idArticolString = JOptionPane.showInputDialog(frame, "Introduceți ID-ul articolului pentru actualizare:");

            // Verificăm dacă s-a introdus un ID valid
            if (idArticolString == null || idArticolString.trim().isEmpty()) {
                return;
            }

            try {
                // Convertim ID-ul introdus într-un număr întreg
                int idArticol = Integer.parseInt(idArticolString);

                // Obținem datele actuale ale articolului din baza de date
                Articol articolCurent = getArticolById(idArticol);

                // Verificăm dacă articolul cu ID-ul specificat există
                if (articolCurent == null) {
                    showMessage("Articolul cu ID-ul specificat nu există.", "Eroare");
                    return;
                }
                /**
                 * Pop-up pentru actualizarea articolului.
                 */
                JTextField jtf_denumireUpdate = new JTextField(articolCurent.getDenumire());
                JTextField jtf_idCategorieUpdate = new JTextField(Integer.toString(articolCurent.getIdCategorie()));
                JTextField jtf_idProducatorUpdate = new JTextField(Integer.toString(articolCurent.getIdProducator()));
                JTextField jtf_descriereUpdate = new JTextField(articolCurent.getDescriere());
                JTextField jtf_stocUpdate = new JTextField(Integer.toString(articolCurent.getStoc()));
                JTextField jtf_pretUpdate = new JTextField(Double.toString(articolCurent.getPret()));

                Object[] message = {
                        "Denumire:", jtf_denumireUpdate,
                        "ID Categorie:", jtf_idCategorieUpdate,
                        "ID Producator:", jtf_idProducatorUpdate,
                        "Descriere:", jtf_descriereUpdate,
                        "Stoc:", jtf_stocUpdate,
                        "Pret:", jtf_pretUpdate
                };

                int option = JOptionPane.showConfirmDialog(frame, message, "Actualizare Articol", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Verificăm dacă toate câmpurile sunt completate
                    if (areCampuriCompletate(jtf_denumireUpdate, jtf_idCategorieUpdate, jtf_idProducatorUpdate, jtf_descriereUpdate, jtf_stocUpdate, jtf_pretUpdate)) {
                        // Salvăm datele actualizate
                        articolCurent.setDenumire(jtf_denumireUpdate.getText());
                        articolCurent.setIdCategorie(Integer.parseInt(jtf_idCategorieUpdate.getText()));
                        articolCurent.setIdProducator(Integer.parseInt(jtf_idProducatorUpdate.getText()));
                        articolCurent.setDescriere(jtf_descriereUpdate.getText());
                        articolCurent.setStoc(Integer.parseInt(jtf_stocUpdate.getText()));
                        articolCurent.setPret(Double.parseDouble(jtf_pretUpdate.getText()));

                        // Apelăm funcția pentru actualizarea articolului în baza de date
                        updateArticolInBazaDeDate(articolCurent);

                        // Actualizăm tabelul cu articole
                        afiseazaToateArticolele();
                    } else {
                        showMessage("Trebuie completate toate câmpurile.", "Eroare");
                    }
                }
            } catch (NumberFormatException ex) {
                showMessage("Introduceți un ID valid (număr întreg).", "Eroare");
            }
        });


        /**
         * Butonul de căutare. La apăsarea acestuia, se apelează funcția getArticolById.
         */
        jb_search = new JButton();
        jb_search.setText("Search");
        jb_search.setBounds(490, 220, 150, 25);
        frame.add(jb_search);
        jb_search.addActionListener(e -> {
            // Afișăm un pop-up pentru introducerea ID-ului articolului pentru căutare
            String idArticolString = JOptionPane.showInputDialog(frame, "Introduceți ID-ul articolului pentru căutare:");

            // Verificăm dacă s-a introdus un ID valid
            if (idArticolString == null || idArticolString.trim().isEmpty()) {
                return;
            }

            try {
                // Convertim ID-ul introdus într-un număr întreg
                int idArticol = Integer.parseInt(idArticolString);

                // Verificăm dacă articolul cu ID-ul specificat există în baza de date
                Articol articolCautat = getArticolById(idArticol);
                if (articolCautat != null) {
                    // Afișăm articolul în tabel
                    dtm.setRowCount(0); // Ștergem toate rândurile existente în tabel
                    adaugaArticolInTabela(articolCautat, getNumeProducator(articolCautat.getIdProducator()));
                } else {
                    showMessage("Nu există articol cu ID-ul specificat.", "Eroare");
                }
            } catch (NumberFormatException ex) {
                showMessage("Introduceți un ID valid (număr întreg).", "Eroare");
            }
        });

        /**
         * Tabelul cu articole.
         */
        jt = new JTable();
        jt.setModel(dtm);
        dtm.setColumnIdentifiers(header);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(10, 250, 630, 600);
        frame.add(sp);

        frame.setSize(700, 900);
        frame.setLayout(null);
        frame.setVisible(true);


    }

    /**
     * Funcția adaugaArticol adaugă un articol în baza de date.
     * @param actionEvent
     */
    /**
     * Funcția adaugaArticol adaugă un articol în baza de date.
     * @param actionEvent
     */
    public void adaugaArticol(ActionEvent actionEvent) {
        // Extragem valorile din câmpuri
        int idCategorie, idProducator, stoc;
        double pret;
        String denumire, descriere;

        try {
            idCategorie = Integer.parseInt(jtf_idCategorie.getText());
            idProducator = Integer.parseInt(jtf_idProducator.getText());
            descriere = jtf_descriere.getText();
            stoc = Integer.parseInt(jtf_stoc.getText());
            pret = Double.parseDouble(jtf_pret.getText());
            denumire = jtf_denumire.getText();
        } catch (NumberFormatException e) {
            showMessage("Trebuie completate toate campurile.", "Eroare");
            return;
        }

        // Verificăm dacă toate câmpurile sunt completate
        if (areCampuriCompletate(jtf_denumire, jtf_idCategorie, jtf_idProducator, jtf_descriere, jtf_stoc, jtf_pret)) {
            // Verificăm dacă idProducator există în tabela Producator
            if (!existaProducator(idProducator)) {
                // Afișăm un mesaj de eroare
                showMessage("Nu există producător cu ID-ul specificat.", "Eroare");
                return; // Ieșim din metoda adaugaArticol fără a continua procesul
            }

            // Verificăm dacă idCategorie există în tabela Categorie
            if (!existaCategorie(idCategorie)) {
                // Afișăm un mesaj de eroare
                showMessage("Nu există categorie cu ID-ul specificat.", "Eroare");
                return; // Ieșim din metoda adaugaArticol fără a continua procesul
            }

            // Obținem numele producătorului
            String numeProducator = getNumeProducator(idProducator);

            // Creăm un obiect Articol
            Articol articol = new Articol(0, idCategorie, idProducator, descriere, stoc, pret, denumire);

            // Adăugăm Articolul în baza de date
            insertDataArt("articole", articol);
        } else {
            showMessage("Trebuie completate toate câmpurile.", "Eroare");
        }
    }

    /**
     * Funcția insertDataArt inserează un articol în baza de date.
     * @param table_name
     * @param articol
     */
    private void insertDataArt(String table_name, Articol articol) {
        String sql = "INSERT INTO " + table_name + " (id_categorie, id_producator, descriere, stoc, pret, denumire) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, articol.getIdCategorie());
            preparedStatement.setInt(2, articol.getIdProducator());
            preparedStatement.setString(3, articol.getDescriere());
            preparedStatement.setInt(4, articol.getStoc());
            preparedStatement.setDouble(5, articol.getPret());
            preparedStatement.setString(6, articol.getDenumire());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Obținem ultimul ID introdus
                String sqlSelectLastID = "SELECT last_insert_rowid() AS last_id";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sqlSelectLastID)) {

                    if (resultSet.next()) {
                        int idArticol = resultSet.getInt("last_id");
                        articol.setIdArticol(idArticol);
                    }
                }

                // Actualizăm tabela după inserarea cu succes
                afiseazaArticoleInTabela();
                jt.validate();
                jt.repaint();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Funcția afiseazaArticoleInTabela afișează toate articolele din baza de date în tabel.
     */
    private void afiseazaArticoleInTabela() {
        // Se elimină toate rândurile existente
        dtm.setRowCount(0);

        // Se citește din nou din baza de date și se adaugă în tabel
        String sql = "SELECT * FROM articole";
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int idArticol = resultSet.getInt("id_articol");
                int idCategorie = resultSet.getInt("id_categorie");
                int idProducator = resultSet.getInt("id_producator");
                String descriere = resultSet.getString("descriere");
                int stoc = resultSet.getInt("stoc");
                double pret = resultSet.getDouble("pret");
                String denumire = resultSet.getString("denumire");

                // Obținem numele producătorului
                String numeProducator = getNumeProducator(idProducator);

                // Se creează un obiect Articol cu datele obținute din baza de date
                Articol articol = new Articol(idArticol, idCategorie, idProducator, descriere, stoc, pret, denumire);

                // Se adaugă rândul în tabel
                adaugaArticolInTabela(articol, numeProducator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Funcția afiseazaToateArticolele afișează toate articolele din baza de date în tabel.
     */
    private void afiseazaToateArticolele() {
        // Afișează toate articolele inițiale în tabel
        afiseazaArticoleInTabela();
    }

    /**
     * Funcția adaugaArticolInTabela adaugă un articol în tabel.
     * @param articol
     * @param numeProducator
     */
    private void adaugaArticolInTabela(Articol articol, String numeProducator) {
        Object[] row = new Object[]{
                articol.getIdArticol(),
                articol.getDenumire(),
                articol.getIdCategorie(),
                articol.getIdProducator(),
                articol.getDescriere(),
                articol.getStoc(),
                articol.getPret(),
                numeProducator
        };
        dtm.addRow(row);
    }

    /**
     * Funcția getNumeProducator returnează numele producătorului cu id-ul specificat.
     * @param idProducator
     * @return
     */
    private String getNumeProducator(int idProducator) {
        // Se citește numele producătorului din baza de date
        String sql = "SELECT nume FROM producator WHERE id_producator = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idProducator);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nume");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
        return ""; // Returnăm un șir gol dacă nu găsim numele producătorului
    }

    /**
     * Funcția existaProducator verifică dacă există un producător cu id-ul specificat.
     * @param idProducator
     * @return
     */
    public boolean existaProducator(int idProducator) {
        // Verificăm dacă idProducator există în tabela Producator
        String sql = "SELECT COUNT(*) AS count FROM producator WHERE id_producator = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idProducator);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
        return false; // Returnăm false dacă întâmpinăm erori sau dacă idProducator nu există
    }

    /**
     * Funcția existaCategorie verifică dacă există o categorie cu id-ul specificat.
     * @param idCategorie
     * @return
     */
    public boolean existaCategorie(int idCategorie) {
        // Verificăm dacă idCategorie există în tabela Categorie
        String sql = "SELECT COUNT(*) AS count FROM categories WHERE id_categorie = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCategorie);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
        return false; // Returnăm false dacă întâmpinăm erori sau dacă idCategorie nu există
    }

    /**
     * Funcția stergeArticolDinBazaDeDate șterge un articol din baza de date.
     * @param idArticol
     */
    public void stergeArticolDinBazaDeDate(int idArticol) {
        String sql = "DELETE FROM articole WHERE id_articol = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idArticol);
            preparedStatement.executeUpdate();
            showMessage("Articolul a fost șters cu succes.", "Succes");

        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Eroare la ștergerea articolului: " + e.getMessage(), "Eroare");
        }
    }

    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Funcția getArticolById returnează un obiect Articol cu id-ul specificat.
     * @param idArticol
     * @return
     */
    public Articol getArticolById(int idArticol) {
        // Obținem datele articolului din baza de date
        String sql = "SELECT * FROM articole WHERE id_articol = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idArticol);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idCategorie = resultSet.getInt("id_categorie");
                    int idProducator = resultSet.getInt("id_producator");
                    String descriere = resultSet.getString("descriere");
                    int stoc = resultSet.getInt("stoc");
                    double pret = resultSet.getDouble("pret");
                    String denumire = resultSet.getString("denumire");

                    return new Articol(idArticol, idCategorie, idProducator, descriere, stoc, pret, denumire);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Funcția updateArticolInBazaDeDate actualizează un articol din baza de date.
     * @param articol
     */
    public void updateArticolInBazaDeDate(Articol articol) {
        // Actualizăm datele articolului în baza de date
        String sql = "UPDATE articole SET id_categorie = ?, id_producator = ?, descriere = ?, stoc = ?, pret = ?, denumire = ? WHERE id_articol = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, articol.getIdCategorie());
            preparedStatement.setInt(2, articol.getIdProducator());
            preparedStatement.setString(3, articol.getDescriere());
            preparedStatement.setInt(4, articol.getStoc());
            preparedStatement.setDouble(5, articol.getPret());
            preparedStatement.setString(6, articol.getDenumire());
            preparedStatement.setInt(7, articol.getIdArticol());

            preparedStatement.executeUpdate();
            afiseazaToateArticolele();
            showMessage("Articolul a fost actualizat cu succes.", "Succes");

        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Eroare la actualizarea articolului: " + e.getMessage(), "Eroare");
        }
    }

    /**
     * Funcția areCampuriCompletate verifică dacă toate câmpurile sunt completate.
     * @param campuri
     * @return
     */
    public boolean areCampuriCompletate(JTextField... campuri) {
        for (JTextField camp : campuri) {
            if (camp.getText().trim().isEmpty()) {
                return false; // Dacă un câmp este necompletat, returnează false
            }
        }
        return true; // Toate câmpurile sunt completate
    }
    public static void main(String[] args) {
        MyApplication app = MyApplication.getInstance();
        app.showWindow();
    }
}
