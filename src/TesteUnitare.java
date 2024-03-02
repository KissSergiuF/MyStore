import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class TesteUnitare {

    @Test
    void testAdaugaArticol() {
        MyApplication app = MyApplication.getInstance();
        app.showWindow();

        // Mock-up de date pentru testare
        app.jtf_idCategorie.setText("1");
        app.jtf_idProducator.setText("1");
        app.jtf_descriere.setText("Descriere test");
        app.jtf_stoc.setText("10");
        app.jtf_pret.setText("20.5");
        app.jtf_denumire.setText("TestArticol");

        // Obținem numărul de rânduri înainte de adăugare
        int numarRanduriInainte = app.dtm.getRowCount();

        // Simulăm evenimentul de adăugare
        ActionEvent mockEvent = new ActionEvent(app.jb_add, ActionEvent.ACTION_PERFORMED, "Add");
        app.adaugaArticol(mockEvent);

        // Verificăm dacă articolul a fost adăugat cu succes
        boolean articolAdaugat = false;
        for (int i = 0; i < app.dtm.getRowCount(); i++) {
            if (app.dtm.getValueAt(i, 1).equals("TestArticol")) {
                articolAdaugat = true;
                break;
            }
        }

        // Verificăm dacă numărul de rânduri a crescut după adăugare
        assertEquals(numarRanduriInainte + 1, app.dtm.getRowCount());

        // Verificăm dacă articolul a fost adăugat
        assertTrue(articolAdaugat);
    }

    // Al doilea test similar pentru funcționalitatea de adăugare
    @Test
    void testAdaugaArticolAltul() {
        MyApplication app = MyApplication.getInstance();
        app.showWindow();

        // Mock-up de date pentru testare
        app.jtf_idCategorie.setText("2");
        app.jtf_idProducator.setText("2");
        app.jtf_descriere.setText("Descriere test 2");
        app.jtf_stoc.setText("15");
        app.jtf_pret.setText("30.0");
        app.jtf_denumire.setText("TestArticol2");

        // Obținem numărul de rânduri înainte de adăugare
        int numarRanduriInainte = app.dtm.getRowCount();

        // Simulăm evenimentul de adăugare
        ActionEvent mockEvent = new ActionEvent(app.jb_add, ActionEvent.ACTION_PERFORMED, "Add");
        app.adaugaArticol(mockEvent);

        // Verificăm dacă articolul a fost adăugat cu succes
        boolean articolAdaugat = false;
        for (int i = 0; i < app.dtm.getRowCount(); i++) {
            if (app.dtm.getValueAt(i, 1).equals("TestArticol2")) {
                articolAdaugat = true;
                break;
            }
        }

        // Verificăm dacă numărul de rânduri a crescut după adăugare
        assertEquals(numarRanduriInainte + 1, app.dtm.getRowCount());

        // Verificăm dacă articolul a fost adăugat
        assertTrue(articolAdaugat);
    }
    @Test
    void testExistaProducator() {
        MyApplication app = MyApplication.getInstance();

        // Adaptează următoarele informații la configurația bazei tale de date de test
        int idProducatorExistent = 1;
        int idProducatorNeexistent = 999;
        assertTrue(app.existaProducator(idProducatorExistent));
        assertFalse(app.existaProducator(idProducatorNeexistent));
    }
    @Test
    void existaCategorie() {
        MyApplication myApp = MyApplication.getInstance();
        assertTrue(myApp.existaCategorie(1));
        assertFalse(myApp.existaCategorie(-1));
    }
}
