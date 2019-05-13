import java.io.*;
import java.util.*;

public class ArrayListHandler {
    private File swimmerList = new File("SwimmerList.txt");
    private File compSwimmerList = new File("CompSwimmerList.txt");
    private File coachList = new File("CoachList.txt");
    private File teamList = new File("TeamList.txt");
    private File trainingResults = new File("TrainingResults.txt");
    private File competitionResults = new File("CompetitionResults.txt");

    private ArrayList<Swimmer> allSwimmers = new ArrayList<>();
    private ArrayList<Swimmer> swimmers = new ArrayList<>();
    private ArrayList<CompetitiveSwimmer> compSwimmers = new ArrayList<>();
    private ArrayList<Coach> coaches = new ArrayList<>();
    private ArrayList<Team> teams = new ArrayList<>();
    private ArrayList<Result> trainingResultArray = new ArrayList<>();
    private ArrayList<Result> competitionResultArray = new ArrayList<>();

    public ArrayListHandler(){
        //In the constructor we call all the methods, to generate the arrayLists needed by the system
        //We do this in the constructor, to make sure they are called, before they need to be used
        //We also call the 'checkForFileName()' method, to check that all our needed files exist or we create them.
        checkForFileName("SwimmerList.txt");
        checkForFileName("CompSwimmerList.txt");
        checkForFileName("CoachList.txt");
        checkForFileName("TeamList.txt");
        checkForFileName("TrainingResults.txt");
        checkForFileName("CompetitionResults.txt");
        checkForFileName("DolphinArt.txt");
        makeCoachArray(coachList);
        makeTeamArray(teamList);
        makeArray(swimmerList);
        makeArray(compSwimmerList);
        makeMemberArray(compSwimmerList, 1);
        makeMemberArray(swimmerList, 2);
        makeResultArray(trainingResults, trainingResultArray);
        makeResultArray(competitionResults, competitionResultArray);
    }

    //Six getter methods that return the different arrayLists, that the system use
    public ArrayList<Swimmer> getAllSwimmers() {
        return allSwimmers;
    }

    public ArrayList<CompetitiveSwimmer> getCompSwimmers() {
        return compSwimmers;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Swimmer> getSwimmers() {
        return swimmers;
    }

    public ArrayList<Result> getTrainingResultArray() {
        return trainingResultArray;
    }

    public ArrayList<Result> getCompetitionResultArray() {
        return competitionResultArray;
    }

    //Four getter methods that return the File object, that the system use
    public File getSwimmerList() {
        return swimmerList;
    }

    public File getCompSwimmerList() {
        return compSwimmerList;
    }

    public File getTrainingResults() {
        return trainingResults;
    }

    public File getCompetitionResults() {
        return competitionResults;
    }

    //Five methods that read from the text files and add that info to objects in the arrayLists
    //Because we know the exact arrangement of info in the textfiles we can just call a specific order of Scanner methods
    public void makeCoachArray(File f){
        try{
            String temp;
            Scanner read = new Scanner(f);
            do{
                temp = read.nextLine();
                Scanner stringReader = new Scanner(temp);
                coaches.add(new Coach());
                coaches.get(coaches.size() - 1).setName(underscoreToRealName(stringReader.next()));
                coaches.get(coaches.size() - 1).setAge(stringReader.nextInt());
                coaches.get(coaches.size() - 1).setEmail(stringReader.next());
                coaches.get(coaches.size() - 1).setHasPaid(stringReader.nextBoolean());
                coaches.get(coaches.size() - 1).setTitle(stringReader.next());
            } while(read.hasNextLine());
        } catch (FileNotFoundException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x01.");
        } catch (InputMismatchException e){
            System.out.println("Der er en fejl i en tekstfil. Errorcode: 105x01");
        } catch(NoSuchElementException e) {
            System.out.println("Der er ingen trænere at indlæse. Errorcode: 107x01.");
        }
    }

    public void makeTeamArray(File f){
        try{
            String temp;
            Scanner read = new Scanner(f);
            do{
                temp = read.nextLine();
                Scanner stringReader = new Scanner(temp);
                teams.add(new Team());
                teams.get(teams.size() - 1).setTeamName(underscoreToRealName(stringReader.next()));
                teams.get(teams.size() - 1).setCoach(searchCoach(underscoreToRealName(stringReader.next())));
            } while(read.hasNextLine());
        } catch(FileNotFoundException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x02.");
        } catch (InputMismatchException e){
            System.out.println("Der er en fejl i en tekstfil. Errorcode: 105x02");
        } catch(NoSuchElementException e) {
            System.out.println("Der er ingen hold at indlæse. Errorcode: 106x01.");
        }
    }

    public void makeArray(File f) {
        try{
            //Temporary String we store a member as string from MemberList
            String temp;
            //Takes input from MemberList
            Scanner read = new Scanner(f);
            try {
                //Do while loop takes every member, make them to a string and reads every token and add them as Object to ArrayList
                do {
                    temp = read.nextLine();
                    Scanner stringReader = new Scanner(temp);
                    //We use minus one, because allSwimmers.size starts at 1, but ArrayList index starts at 0
                    allSwimmers.add(new Swimmer());
                    allSwimmers.get(allSwimmers.size() - 1).setName(underscoreToRealName(stringReader.next()));
                    allSwimmers.get(allSwimmers.size() - 1).setAge(stringReader.nextInt());
                    allSwimmers.get(allSwimmers.size() - 1).setEmail(stringReader.next());
                    allSwimmers.get(allSwimmers.size() - 1).setHasPaid(stringReader.nextBoolean());
                    allSwimmers.get(allSwimmers.size() - 1).setActivity(stringReader.next());
                    allSwimmers.get(allSwimmers.size() - 1).setActive(stringReader.nextBoolean());
                } while (read.hasNextLine());
            } catch(NoSuchElementException e) {
                System.out.println("Der er ingen medlemmer at indlæse. Errorcode: 102x01.");
            }
        } catch(FileNotFoundException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x03.");
        } catch (InputMismatchException e){
            System.out.println("Der er en fejl i en tekstfil. Errorcode: 105x03");
        } catch(NoSuchElementException e) {
            System.out.println("Der er ingen medlemmer at indlæse. Errorcode: 102x03.");
        }
    }

    public void makeMemberArray(File f, int arrayList) {
        try {
            //Temporary String we store a member as string from MemberList
            String temp;
            //Takes input from MemberList
            Scanner read = new Scanner(f);
            if(arrayList == 1){
                //Do while loop takes every member, make them to a string and reads every token and add them as Object to ArrayList
                do {
                    temp = read.nextLine();
                    Scanner stringReader = new Scanner(temp);
                    //We use minus one, because swimmers.size starts at 1, but ArrayList index starts at 0
                    compSwimmers.add(new CompetitiveSwimmer());
                    compSwimmers.get(compSwimmers.size() - 1).setName(underscoreToRealName(stringReader.next()));
                    compSwimmers.get(compSwimmers.size() - 1).setAge(stringReader.nextInt());
                    compSwimmers.get(compSwimmers.size() - 1).setEmail(stringReader.next());
                    compSwimmers.get(compSwimmers.size() - 1).setHasPaid(stringReader.nextBoolean());
                    compSwimmers.get(compSwimmers.size() - 1).setActivity(stringReader.next());
                    compSwimmers.get(compSwimmers.size() - 1).setActive(stringReader.nextBoolean());
                    compSwimmers.get(compSwimmers.size() - 1).setDisciplin(stringReader.next());
                    compSwimmers.get(compSwimmers.size() - 1).setTeam(searchTeam(stringReader.next()));
                } while (read.hasNextLine());
            } else if(arrayList == 2){
                //Do while loop takes every member, make them to a string and reads every token and add them as Object to ArrayList
                do {
                    temp = read.nextLine();
                    Scanner stringReader = new Scanner(temp);
                    //We use minus one, because swimmers.size starts at 1, but ArrayList index starts at 0
                    swimmers.add(new Swimmer());
                    swimmers.get(swimmers.size() - 1).setName(underscoreToRealName(stringReader.next()));
                    swimmers.get(swimmers.size() - 1).setAge(stringReader.nextInt());
                    swimmers.get(swimmers.size() - 1).setEmail(stringReader.next());
                    swimmers.get(swimmers.size() - 1).setHasPaid(stringReader.nextBoolean());
                    swimmers.get(swimmers.size() - 1).setActivity(stringReader.next());
                    swimmers.get(swimmers.size() - 1).setActive(stringReader.nextBoolean());
                } while (read.hasNextLine());
            }
        } catch(FileNotFoundException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x04.");
        } catch (InputMismatchException e){
            System.out.println("Der er en fejl i en tekstfil. Errorcode: 105x04");
        } catch(NoSuchElementException e) {
            System.out.println("Der er ingen medlemmer at indlæse. Errorcode: 102x02.");
        }
    }

    public void makeResultArray(File f, ArrayList<Result> aL) {
        try {
            String temp = "";

            Scanner fileReader = new Scanner(f);
            do {
                temp = fileReader.nextLine();
                Scanner stringReader = new Scanner(temp);

                aL.add(new Result());
                aL.get(aL.size()-1).setName(underscoreToRealName(stringReader.next()));
                aL.get(aL.size()-1).setSwimMeet(stringReader.next());
                aL.get(aL.size()-1).setTime(stringReader.nextInt());
                aL.get(aL.size()-1).setPlacement(stringReader.nextInt());
                aL.get(aL.size()-1).setDisciplin(stringReader.next());
            } while (fileReader.hasNextLine());

        } catch (FileNotFoundException e) {
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x05.");
        } catch (InputMismatchException e){
            System.out.println("Der er en fejl i en tekstfil. Errorcode: 105x05");
        } catch(NoSuchElementException e) {
            System.out.println("Der er ingen resultater at indlæse. Errorcode: 108x01.");
        }
    }

    //Seven methods that take a string as parameter and check if the element exists. Two of them return an object, the rest of them return the name of the object
    public String searchSwimmer(String swimmerName){
        try{
            boolean swimmerFound = false;
            Scanner input = new Scanner(System.in);
            while(!swimmerFound){
                for(int i = 0; i < swimmers.size(); i++){
                    if(swimmers.get(i).getName().equals(swimmerName)){
                        return swimmers.get(i).getName();
                    }
                }
                System.out.println("Kunne ikke finde en svømmer med det navn. Prøv igen.");
                swimmerName = input.nextLine();
            }
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x01.\n");
        }
        return null;
    }

    public String searchCompSwimmer(String compSwimmerName){
        try{
            boolean compSwimmerFound = false;
            Scanner input = new Scanner(System.in);
            while(!compSwimmerFound){
                for(int i = 0; i < compSwimmers.size(); i++){
                    if(compSwimmers.get(i).getName().equals(compSwimmerName)){
                        return compSwimmers.get(i).getName();
                    }
                }
                System.out.println("Kunne ikke finde en konkurrencesvømmer med det navn. Prøv igen.");
                compSwimmerName = input.nextLine();
            }
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x02.\n");
        }
        return null;
    }

    public Coach searchCoach(String coachName){
        try{
            boolean coachFound = false;
            Scanner input = new Scanner(System.in);
            while(!coachFound){
                for(int i = 0; i < coaches.size(); i++){
                    if(coaches.get(i).getName().equals(coachName)){
                        return coaches.get(i);
                    }
                }
                System.out.println("Kunne ikke finde en træner med det navn. Prøv igen.");
                coachName = input.nextLine();
            }
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x03.\n");
        }
        return null;
    }

    public Team searchTeam(String teamName){
        try{
            boolean teamFound = false;
            Scanner input = new Scanner(System.in);
            while(!teamFound){
                for(int i = 0; i < teams.size(); i++){
                    if(teams.get(i).getTeamName().equals(teamName)){
                        return teams.get(i);
                    }
                }
                System.out.println("Kunne ikke finde et hold med det navn. Prøv igen.");
                teamName = input.nextLine();
            }
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x04.\n");
        }
        return null;
    }

    public String searchDisciplin(String disciplin){
        try{
            boolean disciplinFound = false;
            Scanner input = new Scanner(System.in);
            while(!disciplinFound){
                if(disciplin.equals("Ryg") || disciplin.equals("Bryst") || disciplin.equals("Butterfly") || disciplin.equals("Freestyle")){
                    return disciplin;
                }
                System.out.println("Kunne ikke finde en disciplin med det navn. Prøv igen.");
                disciplin = input.nextLine();
            }

        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x05.\n");
        }
        return null;
    }

    //Two methods that take a string as parameters, and respectively replace spaces with underscores and replace underscores with spaces
    //This is done, so we can write and read the name of a swimmer as a single String token, but display it with spaces (multiple String tokens)
    public String realNameToUnderscore(String realName){
        String temp = realName.replace(" ", "_");
        return temp;
    }

    public String underscoreToRealName(String underscore){
        String temp = underscore.replace("_", " ");
        return temp;
    }

    //'chechForFileName()' accepts a string as parameter and then checks if a file with that name exists.
    //If the file doesn't exits, it instanciates a printstream object with that filename. That results in a new textfile being made.
    public void checkForFileName(String fileName) {
        try{
            File f = new File(fileName);

            if (!f.canRead()){
                PrintStream ps = new PrintStream(fileName);
                System.out.println("Filen :" + fileName + " kunne ikke blive fundet. Den er nu oprettet.\n" +
                                    "Den er oprettet uden indhold. Så programmet virker muligvis ikke optimalt. \n" +
                                    "Hvis der skulle opstå fejl, venligt kontakt support.");
            }
        }catch(FileNotFoundException e){
            System.out.println("Der skete en fejl med oprettelse af filer. Errorcode: 108x01");
        }
    }
}