package hr.fipu.k1_z2;

public class Knjiga {
    String naslov;
    String autor;
    int godinaIzdavanja;
    String opis;

    public Knjiga(String naslov, String autor, int godinaIzdavanja, String opis) {
        this.naslov = naslov;
        this.autor = autor;
        this.godinaIzdavanja = godinaIzdavanja;
        this.opis = opis;
    }
    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getGodinaIzdavanja() {
        return godinaIzdavanja;
    }

    public void setGodinaIzdavanja(int godinaIzdavanja) {
        this.godinaIzdavanja = godinaIzdavanja;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
