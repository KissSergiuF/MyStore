/**
 * Clasa Categorie reprezinta un obiect de tip categorie din baza de date.
 */
public class Categorie {
    private int idCategorie;
    private String denumire;
    public Categorie(int idCategorie, String denumire) {
        this.idCategorie = idCategorie;
        this.denumire = denumire;
    }
    public int getIdCategorie() {
        return idCategorie;
    }
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
    public String getDenumire() {
        return denumire;
    }
    public void setDenumire(String denumire) {
        this.denumire = denumire;
        ///Verificare
    }
}
