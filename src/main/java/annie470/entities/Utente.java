package annie470.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tessera_id;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;

    public Utente(){};
    public  Utente ( String nome, String cognome, LocalDate dataNascita){
        this.nome= nome;
        this.cognome = cognome;
        this.dataNascita= dataNascita;
    }


    public UUID getTessera_id() {
        return tessera_id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public LocalDate getDataNascita() {
        return dataNascita;
    }
    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "tessera_id=" + tessera_id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}
