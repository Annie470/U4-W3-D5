package annie470.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Rivista extends ElementoCollezione {
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    public Rivista(){};
    public  Rivista(String titolo, int annoPubblicazione, int nPagine, Periodicita periodicita ) {
        super( titolo, annoPubblicazione, nPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }
    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", nPagine=" + nPagine +
                ", periodicita=" + periodicita +
                '}';
    }
}
