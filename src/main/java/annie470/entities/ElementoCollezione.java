package annie470.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ElementoCollezione {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;
    protected String titolo;
    protected int annoPubblicazione;
    protected  int nPagine;

    protected ElementoCollezione() {};
    protected ElementoCollezione(String titolo, int annoPubblicazione, int nPagine) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.nPagine = nPagine;
    }

    public UUID getId() {
        return id;
    }
    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }
    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }
    public int getnPagine() {
        return nPagine;
    }
    public void setnPagine(int nPagine) {
        this.nPagine = nPagine;
    }
}
