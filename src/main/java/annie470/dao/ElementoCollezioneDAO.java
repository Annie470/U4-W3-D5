package annie470.dao;

import annie470.entities.*;
import annie470.exceptions.ElementoNonTrovatoException;
import annie470.exceptions.InputNonValidoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ElementoCollezioneDAO {
    private final EntityManager entityManager;
    public ElementoCollezioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(ElementoCollezione el) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(el);
        transaction.commit();
    }

    public ElementoCollezione getById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            ElementoCollezione elTrovato = entityManager.find(ElementoCollezione.class, uuid);
            if (elTrovato == null) {
                throw new ElementoNonTrovatoException("ELEMENTO NON PRESENTE IN DB O ID NON CORRETTO");
            }
            return elTrovato;
        }  catch (IllegalArgumentException e) {
            throw new ElementoNonTrovatoException("ELEMENTO NON PRESENTE IN DB O ID NON CORRETTO");
    }
    };

    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        ElementoCollezione elT = this.getById(String.valueOf(uuid));
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(elT);
        transaction.commit();
        System.out.println("Elemento rimosso dal catalogo: " + elT);
    }

    public ElementoCollezione crea(Scanner scanner){
        while (true) {
            int scelta;
            try {
                System.out.println("Inserisci:\n1 -> LIBRO\n2 -> RIVISTA\n9 -> TERMINA");
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                throw new InputNonValidoException("INSERISCI SOLO NUMERI");
            }
            switch (scelta) {
                case 1:
                    System.out.println("Inserisci titolo: ");
                    String titolo = scanner.nextLine();
                    int annoPubblicazione;
                    try {
                        System.out.println("Inserisci anno di pubblicazione: ");
                        annoPubblicazione = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        throw new InputNonValidoException("INSERIRE SOLO NUMERI es. 2025");
                    }
                    int nPagine;
                    try {
                        System.out.println("Inserisci numero di pagine: ");
                        nPagine = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        throw new InputNonValidoException("INSERIRE SOLO NUMERI es. 450");
                    }
                    System.out.println("Inserisci autore: ");
                    String autore = scanner.nextLine();
                    System.out.println("Inserisci genere tra SAGGISTICA, FANTASY, ROSA, FANTASCIENZA: ");
                    String genere;
                    try {
                        String scelta2 = scanner.nextLine().toUpperCase();
                        if (scelta2.equals("ROSA") || scelta2.equals("FANTASCIENZA") || scelta2.equals("FANTASY") || scelta2.equals("SAGGISTICA")) {
                            genere = scelta2;
                        } else {
                            throw new InputNonValidoException("INSERISCI SOLO GENERI INDICATI");
                        }
                    } catch (InputNonValidoException ex) {
                        throw new InputNonValidoException(ex.getMessage());
                    }
                    return new Libro(titolo, annoPubblicazione, nPagine, autore, Genere.valueOf(genere));
                case 2:
                    System.out.println("Inserisci titolo: ");
                    String titoloRivista = scanner.nextLine();
                    int annoPubblicazioneRivista;
                    try {
                        System.out.println("Inserisci anno di pubblicazione: ");
                        annoPubblicazioneRivista = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        throw new InputNonValidoException("INSERIRE SOLO NUMERI es. 2025");
                    }

                    int nPagineRivista;
                    try {
                        System.out.println("Inserisci numero di pagine: ");
                        nPagineRivista = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        throw new InputNonValidoException("INSERIRE SOLO NUMERI es. 450");
                    }
                    System.out.println("Inserisci periodicita tra SETTIMANALE, MENSILE, SEMESTRALE: ");
                    String periodicita;
                    try {
                        String scelta3 = scanner.nextLine().toUpperCase();
                        if (scelta3.equals("SETTIMANALE") || scelta3.equals("MENSILE") || scelta3.equals("SEMESTRALE")) {
                            periodicita = scelta3;
                        } else {
                            throw new InputNonValidoException("INSERISCI SOLO PERIODICITA INDICATE");
                        }
                    } catch (InputNonValidoException ex) {
                        throw new InputNonValidoException(ex.getMessage());
                    }
                    return new Rivista(titoloRivista, annoPubblicazioneRivista, nPagineRivista, Periodicita.valueOf(periodicita));
                case 9 : return null; //DEVO GESTIRE LA COSA FUORI DAL METODO
                default:
                    System.out.println("INSERISCI SOLO 1, 2 , 9");
                    break;
            }
        }
    }

    public void ricercarePerAnno(Scanner scanner) {
        try {
            System.out.println("Inserisci anno di pubblicazione:");
            int anno = Integer.parseInt(scanner.nextLine());
            TypedQuery<ElementoCollezione> query = entityManager.createQuery("SELECT e FROM ElementoCollezione e WHERE e.annoPubblicazione = :anno", ElementoCollezione.class);
            query.setParameter("anno", anno);

            List<ElementoCollezione> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun libro o rivista ha questo anno di pubblicazione");
            } else {
                System.out.println("Elementi trovati per l'anno: " + risultati.size());
                risultati.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("INSERISCI SOLO NUMERI");
        }
    }

    public void ricercarePerAutore(Scanner scanner){
        System.out.println("Inserisci autore:");
        String autore = scanner.nextLine();
        TypedQuery<ElementoCollezione> query = entityManager.createQuery("SELECT e FROM ElementoCollezione e WHERE LOWER(e.autore) LIKE LOWER(:autore)", ElementoCollezione.class).setParameter("autore", "%" + autore + "%");
        // grazie % che non sapevo dove metterti e mi hai fatto perdere mezz'ora

        List<ElementoCollezione> risultati = query.getResultList();
        if (risultati.isEmpty()) {
            System.out.println("Nessun libro ha questo autore");
        } else {
            System.out.println("Elementi trovati: " + risultati.size());
            risultati.forEach(System.out::println);
        }
    }

    public  void ricercarePerTitolo(Scanner scanner){
        System.out.println("Inserisci titolo:");
        String titolo = scanner.nextLine();
        TypedQuery<ElementoCollezione> query = entityManager.createQuery("SELECT e FROM ElementoCollezione e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)", ElementoCollezione.class).setParameter("titolo", "%" + titolo + "%");
        List<ElementoCollezione> risultati = query.getResultList();
        if (risultati.isEmpty()) {
            System.out.println("Nessun libro o rivista ha questo titolo");
        } else {
            System.out.println("Elementi trovati: " + risultati.size());
            risultati.forEach(System.out::println);
        }
    }
}
