package annie470;

import annie470.dao.ElementoCollezioneDAO;
import annie470.dao.PrestitoDAO;
import annie470.dao.UtenteDAO;
import annie470.entities.*;
import annie470.exceptions.ElementoNonTrovatoException;
import annie470.exceptions.InputNonValidoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbzillapu");
    public static final String GIALLO = "\u001B[33m";
    public static final String RESET = "\u001B[0m"; // ma come mi diverto... io sola

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        ElementoCollezioneDAO eld = new ElementoCollezioneDAO(em);
        UtenteDAO ud= new UtenteDAO(em);
        PrestitoDAO prd = new PrestitoDAO(em);
        Scanner scanner = new Scanner(System.in);

        /*Libro l1 =new Libro("Fondazione",1963, 1476, "Isaac Asimov", Genere.FANTASCIENZA );
        Rivista r1 = new Rivista("Archeo II", 2008, 200, Periodicita.SEMESTRALE);
        eld.save(l1);
        eld.save(r1);

        System.out.println("TUTTO OK");*/
        System.out.print( "BENVENUTO IN"+GIALLO +" ZILLA-LIB"+ RESET+"!" ); //è solo il continuo della zillalib del frontend xD
        do {
            try {
                System.out.println("\nInserisci:\n1 -> AGGIUNGERE ELEMENTO LIBRO O RIVISTA\n2 -> RICERCARE PER ISBN\n3 -> ELIMINARE DAL CATALOGO\n4 -> AGGIUNGERE NUOVO UTENTE\n5 -> CREARE NUOVO PRESTITO\n6 -> RESTITUIRE DAL PRESTITO\n7 -> RICERCARE PER ANNO\n8 -> RICERCARE PER AUTORE\n9 -> RICERCARE PER TITOLO\n99 -> TERMINARE");
                int scelta = Integer.parseInt(scanner.nextLine());
                switch (scelta) {
                    case 1:
                        ElementoCollezione el = eld.crea(scanner);
                        if (el != null) { //gestione del null :)
                            eld.save(el);
                            System.out.println("Salvato in db : " + el);
                        } else {
                            System.out.println("OPERAZIONE ANNULLATA");
                        }
                        break;
                    case 2:
                        try {
                            System.out.println("Inserisci ISBN:");
                            String scelta2 = scanner.nextLine();
                            System.out.println(eld.getById(scelta2));
                            break;
                        } catch (ElementoNonTrovatoException ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                    case 3:
                        try {
                            System.out.println("Inserisci ISBN da eliminare:");
                            String scelta2 = scanner.nextLine();
                            eld.delete(scelta2);
                            System.out.println();
                            break;
                        } catch (ElementoNonTrovatoException ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                    case 4:
                        ud.creaSalva(scanner);
                        break;
                    case 5:
                        prd.creaSalva(scanner, eld);
                        break;
                    case 6:
                        prd.restituisciPrestito(scanner, em);
                        break;
                    case 7:
                        eld.ricercarePerAnno(scanner);
                        break;
                    case 8:
                        eld.ricercarePerAutore(scanner);
                        break;
                    case 9:
                        eld.ricercarePerTitolo(scanner);
                        break;
                    case 99:
                        System.out.println("TERMINALE CHIUSO");
                        scanner.close();
                        return;
                    default:
                        throw new InputNonValidoException("INPUT ON VALIDO");
                }

            } catch (InputNonValidoException ex) {
                System.out.println(ex.getMessage());
            };
        } while (true);



    }
}
