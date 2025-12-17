package hr.fipu.k1_z2;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Knjiznica implements Serializable {
    List<Knjiga> listaKnjiga = new ArrayList<>();
    private static Knjiznica instance;
    private Knjiznica(){
    }

    public static synchronized Knjiznica getInstance() {
        if (instance == null) {
            instance = new Knjiznica();
        }
        return instance;
    }
    void dodajKnjigu(Knjiga knjiga){
        listaKnjiga.add(knjiga);
    }

    boolean ukloniKnjigu(String naslov){
        if(nadiKnjigu(naslov) == null) return false;
        listaKnjiga.removeIf(knjiga -> knjiga.getNaslov().equals(naslov));
        return true;
    }

    Knjiga nadiKnjigu(String naslov){
        for (Knjiga knjiga : listaKnjiga) {
            if(knjiga.getNaslov().equals(naslov)) {
                return knjiga;
            }
        }
        return null;
    }

    List<Knjiga> dohvatiSveKnjige(){
        return listaKnjiga;
    }
}
