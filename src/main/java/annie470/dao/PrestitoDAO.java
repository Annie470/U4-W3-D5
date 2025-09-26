package annie470.dao;

import annie470.entities.ElementoCollezione;
import annie470.entities.Prestito;
import annie470.entities.Utente;
import annie470.exceptions.ElementoNonTrovatoException;
import annie470.exceptions.InputNonValidoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class PrestitoDAO {
    private final EntityManager entityManager;

    public PrestitoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void creaSalva(Scanner scanner, ElementoCollezioneDAO eld) {
        try {
            System.out.println("Inserisci nome utente");
            String nome = scanner.nextLine();
            System.out.println("Inserisci cognome utente");
            String cognome = scanner.nextLine();
            //recupera id con query jpa su utenti where nome e cognome corrispondono
            TypedQuery<Utente> query = entityManager.createQuery("SELECT u FROM Utente u WHERE LOWER(u.nome) = LOWER(:nome) AND LOWER(u.cognome) = LOWER(:cognome)", Utente.class);
            query.setParameter("nome", nome.trim());
            query.setParameter("cognome", cognome.trim());
            Utente u = query.getSingleResultOrNull();
            if (u == null) {
                throw new ElementoNonTrovatoException("UTENTE ASSENTE IN DB O CREDENZIALI SCORRETTE");
            }
            System.out.println("Inserisci ISBN");
            String isbn = scanner.nextLine();
            ElementoCollezione el = eld.getById(isbn);

            LocalDate dataPrestito = LocalDate.now();
            Prestito p = new Prestito(u, el, dataPrestito);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(p);
            transaction.commit();
            System.out.println("Prestito salvato! Restuire entro: " + p.getDataRestituzionePrevista());
        } catch (ElementoNonTrovatoException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void restituisciPrestito(Scanner scanner, EntityManager em) {
        try {
            System.out.println("Inserisci nome utente:");
            String nome = scanner.nextLine();
            System.out.println("Inserisci cognome utente:");
            String cognome = scanner.nextLine();
            TypedQuery<Utente> query = em.createQuery(
                    "SELECT u FROM Utente u WHERE LOWER(u.nome) = LOWER(:nome) AND LOWER(u.cognome) = LOWER(:cognome)", //AVREI DOVUTO FAREEE UNA NAMEEEEDQUERY DAMN
                    Utente.class
            );
            query.setParameter("nome", nome.trim());
            query.setParameter("cognome", cognome.trim());
            Utente utente = query.getSingleResult();
            if (utente == null) {
                throw new ElementoNonTrovatoException("UTENTE NON TROVATO");
            }
            System.out.println("Inserisci ISBN del libro o rivista da restituire:");
            String isbn = scanner.nextLine();
            UUID elId=  UUID.fromString(isbn);
            TypedQuery<Prestito> query2 = em.createQuery(
                    "SELECT p FROM Prestito p WHERE p.utente = :utente AND p.elementoCollezione.id = :elId AND p.dataRestituzioneEffettiva IS NULL",
                    Prestito.class
            );
            query2.setParameter("utente", utente);
            query2.setParameter("elId", elId);
            Prestito prestito = query2.getSingleResult();
            if (prestito == null) {
                throw new ElementoNonTrovatoException("PRESTITO ATTIVO NON TROVATO PER QUESTO UTENTE E LIBRO");
            }
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            prestito.setDataRestituzioneEffettiva(LocalDate.now());
            transaction.commit();

            System.out.println("Restituzione effettuata con successo!");

        } catch (ElementoNonTrovatoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}