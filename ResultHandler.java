import java.io.*;
import java.util.*;

public class ResultHandler {

    Scanner input = new Scanner (System.in);

    ArrayListHandler aLH;


    public ResultHandler(ArrayListHandler aLH){
        this.aLH = aLH;
    }

    //'addResult()' asks whether the user wants to add a training or competitions result and calls the needed method
    public void addResult(){
        try{
            boolean addMore = true;
            while(addMore){
                System.out.println("Vil du tilføje et (1)trænings- eller (2)konkurrenceresultat?");
                String choice = input.nextLine();
                while(1 == 1){
                    if(choice.equals("1") || choice.equals("2")){
                        break;
                    }
                    System.out.println("Du skal vælge et gyldigt input.");
                    choice = input.nextLine();
                }
                switch (choice){
                    case "1"    :   addTrainingResult(aLH.getTrainingResultArray());
                        break;
                    case "2"    :   addCompetitionResult(aLH.getCompetitionResultArray());
                        break;
                }
                System.out.println("Vil du tilføje flere resultater? Y/n");
                String choice0 = input.nextLine();
                while(!choice0.equals("Y") && !choice0.equals("n")){
                    System.out.println("Du skal vælge 'Y' eller 'n'.");
                    choice0 = input.nextLine();
                }
                if(choice0.equals("n")){
                    addMore = false;
                }
            }
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x06.\n");
        }
    }

    //'addTrainingResult()' prompts the user to type in the needed information and adds a Result object to the array
    //Then calls the 'writeToFileResult()' method
    //'addCompetitionResult()' does the same thing
    public void addTrainingResult(ArrayList<Result> aL) {
        try{
            aL.add(new Result());

            System.out.println("Indtast nyt træningsresultat:\nNavn: ");
            aL.get(aL.size()-1).setName(aLH.searchCompSwimmer(input.nextLine()));

            aL.get(aL.size()-1).setSwimMeet("Træning");
            System.out.println("Tid (minuter/Sekunder/hundredelesekunder): ");
            int temp1 = input.nextInt();
            int temp2 = input.nextInt();
            int temp3 = input.nextInt(); input.nextLine();
            int temp4 = (temp1*6000) + (temp2*100) + temp3;

            aL.get(aL.size()-1).setTime(temp4);
            aL.get(aL.size()-1).setPlacement(0);
            System.out.println("Disciplin: ");
            aL.get(aL.size()-1).setDisciplin(aLH.searchDisciplin(input.nextLine()));
            //Write Array List object to file
            System.out.println("Resultat gemt i arkivet");
            writeToFileResult(aLH.getTrainingResults(),aLH.getTrainingResultArray());
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x07.\n");
        }
    }

    public void addCompetitionResult(ArrayList<Result> aL) {
        try{
            aL.add(new Result());

            System.out.println("Indtast nyt Stævne resultat:\nNavn: ");
            aL.get(aL.size()-1).setName(aLH.searchCompSwimmer(input.nextLine()));
            System.out.println("Angiv stævne navn: ");
            aL.get(aL.size()-1).setSwimMeet(input.nextLine());
            System.out.println("Tid (minuter og Sekunder/hundredelesekunder): ");
            int temp1 = input.nextInt();
            int temp2 = input.nextInt();
            int temp3 = input.nextInt(); input.nextLine();
            int temp4 = (temp1*6000) + (temp2*100) + temp3;

            aL.get(aL.size()-1).setTime(temp4);
            System.out.println("Angiv placering: ");
            aL.get(aL.size()-1).setPlacement(input.nextInt()); input.nextLine();
            System.out.println("Angiv disciplin: ");
            aL.get(aL.size()-1).setDisciplin(aLH.searchDisciplin(input.nextLine()));

            //Write Array List object to file
            System.out.println("Resultat gemt i arkivet");
            writeToFileResult(aLH.getCompetitionResults(),aLH.getCompetitionResultArray());
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x08.\n");
        }
    }

    //'getTop5()' prompts the user to specify which disciplin to find a top5 results in
    //Then it loops through the arrayList containing training results and adds them to a new arrayList
    //We then use Collections.sort method to sort the Result objects in the new arrayList
    //After that we print the first 5 results (or less if there aren't enough results) to the console
    public void getTop5() {
        try{
            System.out.println("I hvilken disciplin vil du se top 5?: ");
            String search = input.nextLine();

            ArrayList<Result> choosenResults = new ArrayList<>();

            for(int i = 0; i < aLH.getTrainingResultArray().size(); i++){
                if(aLH.getTrainingResultArray().get(i).getDisciplin().equals(search)){
                    choosenResults.add(aLH.getTrainingResultArray().get(i));
                }
            }
            // Sorting
            Collections.sort(choosenResults, new Comparator<Result>() {
                @Override
                public int compare(Result r1, Result r2)
                {

                    return  r1.compareTo(r2);
                }
            });

            int numberOfResults = 5;
            if(choosenResults.size() < 5){
                numberOfResults = choosenResults.size();
            }

            for(int i = 0; i < numberOfResults; i++){
                System.out.println("(" + (i + 1) + ") " + choosenResults.get(i).getName() + ": " + choosenResults.get(i).getFullTime());
            }
            System.out.println(" ");
        } catch (InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x09.\n");
        }
    }

    //'getResults()' works in a similar way to 'getTop5'
    //It prompts the user to specify which results the user wants to see, it then adds those results to a new arrayList
    //and then printing them to the console
    public void getResults(){
        try{
            ArrayList<Result> regOrComp = aLH.getTrainingResultArray();

            System.out.println("Vil du se (1)trænings- eller (2)konkurrenceresultater?");
            String arrayChoice = input.nextLine();
            while(1 == 1){
                if(arrayChoice.equals("1") || arrayChoice.equals("2")){
                    break;
                }
                System.out.println("Du skal vælge '1' eller '2'.");
                arrayChoice = input.nextLine();
            }
            switch (arrayChoice){
                case "1" : regOrComp = aLH.getTrainingResultArray();
                    break;
                case "2" : regOrComp = aLH.getCompetitionResultArray();
                    break;
            }
            ArrayList<Result> choosenResults = new ArrayList<>();

            if(arrayChoice.equals("1")){
                System.out.println(
                        "Hvad vil du sortere resultaterne efter?" +
                                "\n(1) Efter navn" +
                                "\n(2) Efter disciplin"
                );
            } else{
                System.out.println(
                        "Hvad vil du sortere resultaterne efter?" +
                                "\n(1) Efter navn" +
                                "\n(2) Efter disciplin" +
                                "\n(3) Efter stævne"
                );
            }

            String choice = input.nextLine();
            while(1 == 1){
                if(choice.equals("1") || choice.equals("2")){
                    break;
                } else if(choice.equals("3") && arrayChoice.equals("2")){
                    break;
                }
                System.out.println("Du skal vælge '1', '2' eller '3'.");
                choice = input.nextLine();
            }
            System.out.println("Hvad vil du søge efter?");
            String search = input.nextLine();
            switch (choice){
                case "1" :  for(int i = 0; i < regOrComp.size(); i++){
                    if(regOrComp.get(i).getName().equalsIgnoreCase(search)){
                        choosenResults.add(regOrComp.get(i));
                    }
                }
                    break;
                case "2" :  aLH.searchDisciplin(search);
                    for(int i = 0; i < regOrComp.size(); i++){
                        if(regOrComp.get(i).getDisciplin().equalsIgnoreCase(search)){
                            choosenResults.add(regOrComp.get(i));
                        }
                    }
                    break;
                case "3" :  for(int i = 0; i < regOrComp.size(); i++){
                    if(regOrComp.get(i).getSwimMeet().equalsIgnoreCase(search)){
                        choosenResults.add(regOrComp.get(i));
                    }
                }
                    break;
            }
            if(choosenResults.size() == 0){
                System.out.println("Der fandtes ikke nogen resultater der passede til de kriterier.");
            } else{
                for(int i = 0; i < choosenResults.size(); i++){
                    System.out.println(choosenResults.get(i).printResult());
                }
                System.out.println(" ");
            }
            choosenResults.clear();
        }catch(InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x15.");
        }
    }

    //'writeToFileResult()'  works by accepting a File object and a arrayList containing Result objects
    //It then loops through every object in the arrayList and prints the info in a specified order in a single line
    //to the specified file
    public void writeToFileResult(File f, ArrayList<Result> aL) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            PrintStream pS = new PrintStream(fos);

            for (int i = 0; i < aL.size(); i++) {
                pS.println(
                        aLH.realNameToUnderscore(aL.get(i).getName()) + " " +
                        aL.get(i).getSwimMeet() + " " +
                        aL.get(i).getTime() + " " +
                        aL.get(i).getPlacement() + " " +
                        aL.get(i).getDisciplin());
            }
        }catch(FileNotFoundException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x06.");
        }
    }
}