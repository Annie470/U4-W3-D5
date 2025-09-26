package annie470;

import annie470.dao.ElementoCollezioneDAO;
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
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        ElementoCollezioneDAO eld = new ElementoCollezioneDAO(em);
        UtenteDAO ud= new UtenteDAO(em);
        Scanner scanner = new Scanner(System.in);

        /*Libro l1 =new Libro("Fondazione",1963, 1476, "Isaac Asimov", Genere.FANTASCIENZA );
        Rivista r1 = new Rivista("Archeo II", 2008, 200, Periodicita.SEMESTRALE);
        eld.save(l1);
        eld.save(r1);

        System.out.println("TUTTO OK");*/
        do {
            try {
                System.out.println("Inserisci:\n1 -> AGGIUNGERE ELEMENTO LIBRO O RIVISTA\n2 -> RICERCARE PER ISBN\n3 -> ELIMINARE DAL CATALOGO\n4 -> AGGIUNGERE NUOVO UTENTE\n9 -> TERMINARE");
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
                    case 9:
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
