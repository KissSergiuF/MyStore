/**
 * Clasa Articol reprezinta un obiect de tip articol din baza de date.
 */
public class Articol {
    private int idArticol;
    private int idCategorie;
    private int idProducator;
    private String descriere;
    private int stoc;
    private double pret;
    private String denumire;

    public Articol(int idArticol, int idCategorie, int idProducator, String descriere, int stoc, double pret, String denumire) {
        this.idArticol = idArticol;
        this.idCategorie = idCategorie;
        this.idProducator = idProducator;
        this.descriere = descriere;
        this.stoc = stoc;
        this.pret = pret;
        this.denumire = denumire;
    }

    public int getIdArticol() {
        return idArticol;
    }

    public void setIdArticol(int idArticol) {
        this.idArticol = idArticol;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdProducator() {
        return idProducator;
    }

    public void setIdProducator(int idProducator) {
        this.idProducator = idProducator;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
    public String getDenumire() {
        return denumire;
    }
}
