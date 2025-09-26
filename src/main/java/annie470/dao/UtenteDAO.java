package annie470.dao;

import annie470.entities.Utente;
import annie470.exceptions.ElementoNonTrovatoException;
import annie470.exceptions.InputNonValidoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class UtenteDAO {
    private final EntityManager entityManager;
    public UtenteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void creaSalva(Scanner scanner){

            try {
                System.out.println("Inserisci nome");
                String nome = scanner.nextLine();
                System.out.println("Inserisci cognome");
                String cognome = scanner.nextLine();
                LocalDate dataNascita;
                try {
                    System.out.println("Inserisci data di nascita anno-mese-giorno es.1994-04-11");
                    dataNascita = LocalDate.parse(scanner.nextLine());
                } catch (DateTimeParseException ex) {
                    throw new InputNonValidoException("INSERISCI FORMATO ANNO-MESE-GIORNO ES.2021-10-04");
                }
                Utente u = new Utente(nome, cognome, dataNascita);
                this.save(u);
                System.out.println("Utente salvato\nTessera ID: " + u.getTessera_id());
            } catch (InputNonValidoException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public void save(Utente u) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(u);
        transaction.commit();
    }

    public Utente getById(String id) {
        UUID uuid = UUID.fromString(id);
        Utente uTrovato = entityManager.find(Utente.class, uuid);
        if (uTrovato == null) {
            throw new ElementoNonTrovatoException("ELEMENTO NON PRESENTE IN DB O ID NON CORRETTO");
        }
        return  uTrovato;
    };

    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        Utente uT = this.getById(String.valueOf(uuid));
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(uT);
        transaction.commit();
        System.out.println("Elemento rimosso dal catalogo: " + uT);
    }

}
