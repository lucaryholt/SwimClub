import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MemberHandler {

    Scanner input = new Scanner(System.in);
    //Files contain member list, competitive swimmers, coachList and teams

    //ArrayLists with all swimmers, competitive, coachList and teams
    private ArrayListHandler aLH;
    private ArrayList<Swimmer> swimmers;
    private ArrayList<CompetitiveSwimmer> compSwimmers;
    private ArrayList<Team> teams;

    public MemberHandler(ArrayListHandler aLH){
        this.aLH = aLH;
        this.swimmers = aLH.getSwimmers();
        this.compSwimmers = aLH.getCompSwimmers();
        this.teams = aLH.getTeams();
    }

    //Method add Swimmer objects to Array List (swimmers)
    public void addSwimmer() {
        try{
            boolean addMore = true;
            while (addMore){
                //Adds a member to Array List
                swimmers.add(new Swimmer());

                System.out.println("Du har valgt at tilføje en svømmer." + "\nNavn: ");
                swimmers.get(swimmers.size()-1).setName(input.nextLine());
                System.out.println("Årstal: ");
                swimmers.get(swimmers.size()-1).setAge(input.nextInt());
                System.out.println("E-mail: ");
                input.nextLine();
                swimmers.get(swimmers.size()-1).setEmail(input.nextLine());
                swimmers.get(swimmers.size()-1).setHasPaid(false);
                swimmers.get(swimmers.size()-1).setActivity("Motionist");
                swimmers.get(swimmers.size()-1).setActive(true);

                //Write Array List Object to File
                System.out.println("Svømmer er blevet gemt i registret");
                writeToFile(aLH.getSwimmerList(), 0);

                System.out.println("Vil du tilføje en svømmer mere? Y/n");
                String choice = input.nextLine();
                while(!choice.equals("Y") && !choice.equals("n")){
                    System.out.println("Du skal indtaste 'Y' eller 'n'.");
                    choice = input.nextLine();
                }
                if (choice.equals("n")){
                    addMore = false;
                }
            }
        } catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x12.");
        }
    }

    //We have made 'deleteSwimmer' and 'deleteCompetitiveSwimmer()' using to different methods, to show to different ways to do the same thing
    //'deleteSwimmer' asks the user to input the name of the Swimmer they want to delete and then removes it from the arrayList
    public void deleteSwimmer() {
        try{
            listSwimmers();

            System.out.println(
                    "Du er i gang med at slette et medlem" +
                            "\nSøg på medlem: "
            );
            //Takes input from user
            Scanner input = new Scanner(System.in);
            //Local variable stores user input as search word
            String search = input.nextLine();
            //Loops through every member in MemberList searching for a match with our User Input search word
            for(int i = 0; i < swimmers.size(); i++) {
                if(swimmers.get(i).getName().equals(search)) {
                    System.out.println(swimmers.get(i).getName() + " Deleted");
                    swimmers.remove(i);
                    //Delete everything from file and rewrite all swimmers left in ArrayList
                    new FileOutputStream("SwimmerList.txt").close();
                    writeToFile(aLH.getSwimmerList(), 0);
                } else{
                    System.out.println("Svømmer findes ikke.");
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x08.");
        }catch(IOException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x09.");
        }
    }

    //'deleteCompetitiveSwimmer' calls the 'listCompetitiveSwimmers' and asks the user to input a number, that references a CompetitiveSwimmer
    //It then removes that index from the arrayList
    public void deleteCompetitiveSwimmer() {
        try{
            Scanner input = new Scanner(System.in);
            listCompetitiveSwimmers();

            System.out.println("Hvilket medlem vil du slette?");
            int choice = input.nextInt();

            compSwimmers.remove(choice - 1);

            writeToFile(aLH.getCompSwimmerList(), 1);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Den valgte konkurrencesvømmer eksistere ikke.");
        }
    }

    //'listSwimmers' and 'listCompetitiveSwimmer' works in exactly the same way, but with to different arrayLists
    //It runs a for loop, looping through the entire arrayList and printing the name of every object where the active boolean is true
    public void listSwimmers(){
        for(int i = 0; i < swimmers.size(); i++){
            if(swimmers.get(i).getActive()){
                System.out.println("(" + (i + 1) + ") " + swimmers.get(i).getName());
            }
        }
        System.out.println(" ");
    }

    public void listCompetitiveSwimmers(){
        for(int i = 0; i < compSwimmers.size(); i++){
            if(compSwimmers.get(i).getActive()){
                System.out.println("(" + (i + 1) + ") " + compSwimmers.get(i).getName());
            }
        }
        System.out.println(" ");
    }

    //'listTeams' works in almost the same way, as the to former methods, but simply prints the name of all teams in the arraylist
    //without checking anything
    public void listTeams(){
        for(int i = 0; i < teams.size(); i++){
            System.out.println("Hold: " + teams.get(i).getTeamName());
        }
        System.out.println(" ");
    }

    //'changeActivityToCompSwimmer' and 'changeActivityToSwimmer' works in basically the same way
    //It asks the user to specify which Swimmer to change, then instanciates a temp Swimmer and points that to the specific Swimmer
    //Then it copies the info from the temp to a new CompetitiveSwimmer, which is added to the compSwimmer arrayList
    public void changeActivityToCompSwimmer(){
        try{
            while(1 == 1){
                Scanner input = new Scanner(System.in);
                Swimmer temp;
                listSwimmers();

                System.out.println("Hvilken svømmer vil du ændre til konkurrencesvømmer?");
                int choice = input.nextInt(); input.nextLine();

                System.out.println("Hvilken disciplin? (Bryst, Ryg, Freestyle, Butterfly)");
                String disciplinChoice = aLH.searchDisciplin(input.nextLine());

                temp = swimmers.get(choice - 1);
                swimmers.remove(choice - 1);

                Team team;

                if(temp.getActualAge() < 18){
                    team = aLH.searchTeam("JuniorHold");
                } else{
                    team = aLH.searchTeam("SeniorHold");
                }

                compSwimmers.add(new CompetitiveSwimmer());
                compSwimmers.get(compSwimmers.size() - 1).setName(temp.getName());
                compSwimmers.get(compSwimmers.size() - 1).setAge(temp.getAge());
                compSwimmers.get(compSwimmers.size() - 1).setEmail(temp.getEmail());
                compSwimmers.get(compSwimmers.size() - 1).setHasPaid(temp.getHasPaid());
                compSwimmers.get(compSwimmers.size() - 1).setActivity("Konkurrence");
                compSwimmers.get(compSwimmers.size() - 1).setActive(temp.getActive());
                compSwimmers.get(compSwimmers.size() - 1).setDisciplin(aLH.searchDisciplin(disciplinChoice));
                compSwimmers.get(compSwimmers.size() - 1).setTeam(team);

                writeToFile(aLH.getSwimmerList(), 0);
                writeToFile(aLH.getCompSwimmerList(), 1);

                System.out.println("Vil du ændre endnu en svømmer til konkurrencesvømmer? Y/n");
                String choice0 = input.nextLine();
                while(!choice0.equals("Y") && !choice0.equals("n")){
                    System.out.println("Du skal vælge 'Y' eller 'n'");
                    choice0 = input.nextLine();
                }
                if(choice0.equals("n")){
                    break;
                }
            }
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x13.");
        }catch(IndexOutOfBoundsException e){
            System.out.println("Den valgte svømmer findes ikke. Errorcode: 104x01");
        }
    }

    public void changeActivityToSwimmer(){
        try{
            while(1 == 1){
                Scanner input = new Scanner(System.in);
                Swimmer temp;
                listCompetitiveSwimmers();

                System.out.println("Hvilken konkurrencesvømmer vil du ændre til svømmer?");
                int choice = input.nextInt(); input.nextLine();

                temp = compSwimmers.get(choice - 1);
                compSwimmers.remove(choice - 1);

                swimmers.add(new Swimmer());
                swimmers.get(swimmers.size() - 1).setName(temp.getName());
                swimmers.get(swimmers.size() - 1).setAge(temp.getAge());
                swimmers.get(swimmers.size() - 1).setEmail(temp.getEmail());
                swimmers.get(swimmers.size() - 1).setHasPaid(temp.getHasPaid());
                swimmers.get(swimmers.size() - 1).setActivity("Motionist");
                swimmers.get(swimmers.size() - 1).setActive(temp.getActive());

                writeToFile(aLH.getSwimmerList(), 0);
                writeToFile(aLH.getCompSwimmerList(), 1);

                System.out.println("Vil du ændre endnu en konkurrencesvømmer til svømmer? Y/n");
                String choice0 = input.nextLine();
                while(!choice0.equals("Y") && !choice0.equals("n")){
                    System.out.println("Du skal vælge 'Y' eller 'n'");
                    choice0 = input.nextLine();
                }
                if(choice0.equals("n")){
                    break;
                }
            }
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x14.");
        }catch(IndexOutOfBoundsException e){
            System.out.println("Den valgte svømmer findes ikke. Errorcode: 104x02");
        }
    }

    public void changeActive() {
        try{
            Scanner input= new Scanner(System.in);
            System.out.println("Er det en (1)motions- eller (2)konkurrencesvømmer?");
            String choice = input.nextLine();
            boolean correctChoice = false;
            while(!correctChoice){
                if(choice.equals("1") || choice.equals("2")){
                    break;
                }
                System.out.println("Du skal taste (1) eller (2)");
                choice = input.nextLine();
            }
            System.out.println("Indtast navn, hvis aktiv status skal ændres");
            String search = "";
            if(choice.equals("1")){
                search = aLH.searchSwimmer(input.nextLine());
            } else if(choice.equals("2")) {
                search = aLH.searchCompSwimmer(input.nextLine());
            }
            System.out.println("Er " + search + " aktiv eller passiv?");
            boolean correctActive = false;
            String active = input.nextLine();
            while(!correctActive){
                if(active.equals("aktiv") || active.equals("passiv")){
                    break;
                }
                System.out.println("Du skal skrive 'aktiv' eller 'passiv'.");
                active = input.nextLine();
            }
            boolean bool = true;
            if(active.equalsIgnoreCase("aktiv")) {
                bool = true;
            } else if(active.equalsIgnoreCase("passiv")) {
                bool = false;
            }
            if (choice.equals("1")){
                for(int i = 0; i < swimmers.size(); i++) {
                    if (swimmers.get(i).getName().equalsIgnoreCase(search)) {
                        swimmers.get(i).setActive(bool);

                    }
                }
            } else if(choice.equals("2")){
                for(int i = 0; i < compSwimmers.size(); i++){
                    if (compSwimmers.get(i).getName().equalsIgnoreCase(search)){
                        compSwimmers.get(i).setActive(bool);
                    }
                }
            }
            writeToFile(aLH.getSwimmerList(), 0);
            writeToFile(aLH.getCompSwimmerList(), 1);
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x14.");
        }
    }

    //'writeToFile' takes a File and an int (referencing which arrayList we need) and writes the info from the arrayList to the File
    //It does this by looping through each object in the arrayList, and printing the info in a specific order to a single line
    public void writeToFile(File f, int arrayList){
        try{
            //Opens a stream to our Memberlist
            FileOutputStream fos = new FileOutputStream(f);
            //Lets us write to MemberList through our Stream
            PrintStream pS = new PrintStream(fos);
            //Loop uses size of swimmers ArrayList to write every single Swimmer Object to MemberList
            if(arrayList == 0){
                for(int i = 0; i < swimmers.size(); i++) {
                    pS.println(
                            aLH.realNameToUnderscore(swimmers.get(i).getName()) + " " +
                            swimmers.get(i).getAge() + " " +
                            swimmers.get(i).getEmail() + " " +
                            swimmers.get(i).getHasPaid() + " " +
                            swimmers.get(i).getActivity() + " " +
                            swimmers.get(i).getActive()
                    );
                }
            } else if(arrayList == 1){
                for(int i = 0; i < compSwimmers.size(); i++){
                    pS.println(
                            aLH.realNameToUnderscore(compSwimmers.get(i).getName()) + " " +
                            compSwimmers.get(i).getAge() + " " +
                            compSwimmers.get(i).getEmail() + " " +
                            compSwimmers.get(i).getHasPaid() + " " +
                            compSwimmers.get(i).getActivity() + " " +
                            compSwimmers.get(i).getActive() + " " +
                            compSwimmers.get(i).getDisciplin() + " " +
                            compSwimmers.get(i).getTeam()
                    );
                }
            }
        } catch (IOException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101.x10");
        }
    }
}