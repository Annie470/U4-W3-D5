package annie470.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Libro extends ElementoCollezione {
    private String autore;
    @Enumerated(EnumType.STRING)
    private Genere genere;

    public Libro(){};
    public Libro(String titolo, int annoPubblicazione, int nPagine, String autore, Genere genere) {
        super(titolo, annoPubblicazione, nPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public Genere getGenere() {
        return genere;
    }
    public void setGenere(Genere genere) {
        this.genere = genere;
    }
    public String getAutore() {
        return autore;
    }
    public void setAutore(String autore) {
        this.autore = autore;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", nPagine=" + nPagine +
                ", autore='" + autore + '\'' +
                ", genere=" + genere +
                '}';
    }
}
