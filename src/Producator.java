/**
 * Clasa Producator contine datele despre furnizorul unui produs.
 */
public class Producator {
    private int idProducator;
    private String numeProducator;
    private String email;
    private int varsta;

    public Producator(int idProducator, String numeProducator, String email, int varsta) {
        this.idProducator = idProducator;
        this.numeProducator = numeProducator;
        this.email = email;
        this.varsta = varsta;
    }

    public int getIdProducator() {
        return idProducator;
    }

    public void setIdProducator(int idProducator) {
        this.idProducator = idProducator;
    }

    public String getNumeProducator() {
        return numeProducator;
    }

    public void setNumeProducator(String numeProducator) {
        this.numeProducator = numeProducator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }
}
