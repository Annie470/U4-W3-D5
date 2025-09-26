package annie470.entities;

import annie470.exceptions.ElementoNonTrovatoException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Scanner;

@Entity
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "elementoCollezione_id")
    private ElementoCollezione elementoCollezione;

    @Column(name = "data_prestito")
    private LocalDate dataPrestito;
    @Column(name = "data_restituzione_prevista")
    private LocalDate dataRestituzionePrevista;
    @Column(name = "data_restituzione_effettiva", nullable = true)
    private LocalDate dataRestituzioneEffettiva;

    public Prestito(){};
    public Prestito(Utente utente, ElementoCollezione elementoCollezione, LocalDate dataPrestito){
        this.utente = utente;
        this.elementoCollezione = elementoCollezione;
        this.dataPrestito = dataPrestito;
        this.dataRestituzionePrevista = dataPrestito.plusDays(30);
        this.dataRestituzioneEffettiva = null;
    }

    public int getId() {
        return id;
    }
    public Utente getUtente() {
        return utente;
    }
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public ElementoCollezione getElementoCollezione() {
        return elementoCollezione;
    }

    public void setElementoCollezione(ElementoCollezione elementoCollezione) {
        this.elementoCollezione = elementoCollezione;
    }

    public LocalDate getDataPrestito() {
        return dataPrestito;
    }
    public void setDataPrestito(LocalDate dataPrestito) {
        this.dataPrestito = dataPrestito;
    }

    public LocalDate getDataRestituzionePrevista() {
        return dataRestituzionePrevista;
    }

    public void setDataRestituzionePrevista(LocalDate dataRestituzionePrevista) {
        this.dataRestituzionePrevista = dataRestituzionePrevista;
    }

    public LocalDate getDataRestituzioneEffettiva() {
        return dataRestituzioneEffettiva;
    }

    public void setDataRestituzioneEffettiva(LocalDate dataRestituzioneEffettiva) {
        this.dataRestituzioneEffettiva = dataRestituzioneEffettiva;
    }


    @Override
    public String toString() {
        return "Prestito{" +
                "id=" + id +
                ", utente=" + utente +
                ", elementoCollezione=" + elementoCollezione +
                ", dataPrestito=" + dataPrestito +
                ", dataRestituzionePrevista=" + dataRestituzionePrevista +
                ", dataRestituzioneEffettiva=" + dataRestituzioneEffettiva +
                '}';
    }
}

