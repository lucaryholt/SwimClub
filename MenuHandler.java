import java.io.*;
import java.util.*;

public class MenuHandler{

    private SubscriptionHandler sH;
    private MemberHandler mH;
    private ResultHandler rH;
    boolean loggedIn = false;

    //In the constructor we make sure that we get the handlers.
    public MenuHandler(SubscriptionHandler sH, MemberHandler mH, ResultHandler rH){
        this.sH = sH;
        this.mH = mH;
        this.rH = rH;

        printArt("DolphinArt","\u001B[34m");
    }

    //The loginMenu just listens for two strings and if they are equal to one of the three
    //logins, the user gets redirected to the correct menu. Otherwise the user is prompted to try again.
    //This method would not work in a real life scenario, as it offers little to none security.
    public void loginMenu(){
        Scanner input = new Scanner(System.in);

        //A while loop is used in this method, and the 'submenu' methods, to keep the system running until a user shuts the system down.
        while(!loggedIn){
            System.out.println("Velkommen til Svømmeklubben\u001B[34m Delfinens\u001B[0m System. " +
                    "Indtast Q for at lukke programmet."
            );

            System.out.println("Indtast brugernavn:");
            String userName = input.nextLine();

            if(userName.equalsIgnoreCase("Q")){
                System.out.println("Systemet lukker ned nu.");
                System.exit(1);
            }

            System.out.println("Indtast kode:");
            String passWord = input.nextLine();


            if(userName.equals("coach") && passWord.equals("coach")){
                loggedIn = true;
                coachMenu();
            } else if(userName.equals("chairman") && passWord.equals("chairman")){
                loggedIn = true;
                chairmanMenu();
            } else if(userName.equals("cashier") && passWord.equals("cashier")){
                loggedIn = true;
                cashierMenu();
            } else {
                System.out.println("Kunne ikke finde dit brugernavn og adgangskode. Prøv igen.");
            }
        }
    }

    //In the three different menu methods following include a switchstatement to redirect the user, to
    //the wanted method, depending on the user input.
    public void coachMenu(){
        System.out.println("Du er logget ind som træner.");

        while(loggedIn){
            Scanner input = new Scanner(System.in);
            System.out.println(
                    "(1) Tilføj resultat" +
                    "\n(2) Hent top 5" +
                    "\n(3) Hent resultater"+
                    "\n(4) Skift motionistsvømmer til konkurrencesvømmer" +
                    "\n(5) Skift konkurrencesvømmer til motionistsvømmer" +
                    "\n(Q) Log ud"
            );
            String menuChoice = input.nextLine();
            switch(menuChoice){
                case "1" :  rH.addResult();
                            break;
                case "2" :  rH.getTop5();
                            break;
                case "3" :  rH.getResults();
                            break;
                case "4" :  mH.changeActivityToCompSwimmer();
                            break;
                case "5" :  mH.changeActivityToSwimmer();
                            break;
                case "q" :
                case "Q" :  System.out.println("Du er nu logget ud.\n");
                            loggedIn = false;
                            break;
                default  :  System.out.println("Du skal vælge en gyldig valgmulighed.");
            }
        }
    }

    public void chairmanMenu() {
        System.out.println("Du er logget ind som formand.");

        while(loggedIn){
            Scanner input = new Scanner(System.in);
            System.out.println(
                    "(1) Tilføj svømmer" +
                    "\n(2) Liste over aktive motionistsvømmere" +
                    "\n(3) Liste over aktive konkurrencesvømmere" +
                    "\n(4) Sæt medlem til aktiv eller passiv" +
                    "\n(5) Slet en motionistsvømmer" +
                    "\n(6) Slet en konkurrencesvømmer" +
                    "\n(Q) Log ud"
            );
            String menuChoice = input.nextLine();
            switch(menuChoice){
                case "1" :  mH.addSwimmer();
                            break;
                case "2" :  mH.listSwimmers();
                            break;
                case "3" :  mH.listCompetitiveSwimmers();
                            break;
                case "4" :  mH.changeActive();
                            break;
                case "5" :  mH.deleteSwimmer();
                            break;
                case "6" :  mH.deleteCompetitiveSwimmer();
                            break;
                case "q" :
                case "Q" :  System.out.println("Du er nu logget ud.\n");
                            loggedIn = false;
                            break;
                default  :  System.out.println("Du skal vælge en gyldig valgmulighed.");
            }
        }
    }

    public void cashierMenu(){
        System.out.println("Du er logget ind som kasserer.");
        while(loggedIn){
            Scanner input = new Scanner(System.in);
            System.out.println(
                    "(1) List manglende betalinger" +
                    "\n(2) Ændrer betalingsstatus" +
                    "\n(3) Sæt medlem til aktiv eller passiv" +
                    "\n(Q) Log ud"
            );
            String menuChoice = input.nextLine();
            switch(menuChoice){
                case "1" :  sH.listMissingPayments();
                            break;
                case "2" :  sH.setPaymentStatus();
                            break;
                case "3" :  mH.changeActive();
                    break;
                case "q" :
                case "Q" :  System.out.println("Du er nu logget ud.\n");
                            loggedIn = false;
                            break;
                default  :  System.out.println("Du skal vælge en gyldig valgmulighed.");
            }
        }
    }

    //This method takes a textfile (and a colour code) and prints it to the console.
    //In this case it's ASCII art of a Dolphin (very fitting).
    public void printArt(String filename, String colour) {
        try{
            Scanner reader = new Scanner(new File(filename + ".txt"));
            System.out.println(colour);
            while(reader.hasNextLine()){
                System.out.println(reader.nextLine());
            }
            System.out.println("\u001B[0m");
        } catch (FileNotFoundException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101.");
        }
    }
}