import java.util.*;
import java.io.*;

public class SubscriptionHandler {

    private double juniorPrice = 1000;
    private double seniorPrice = 1600;
    private double passivePrice = 500;
    private double ageDiscount = 0.75;

    private ArrayListHandler aLH;

    public SubscriptionHandler(ArrayListHandler aLH){
        this.aLH = aLH;

        resetPaymentStatus();
    }

    //'sendInvoice()' prints to the console, that the specified name has received an email on special email address
    public void sendInvoice(String name, String email){
        System.out.println("Der er sendt en faktura til " + name + " på: " + email);
    }

    //'calculatePrice()' uses a number of if-else statements and information from the Swimmer objects to calculate the correct price
    public double calculatePrice(String name){
        for(int i = 0; i < aLH.getAllSwimmers().size(); i++){
            //When the loop 'finds' the object with a matching name, else prints that it could not find the member
            if(aLH.getAllSwimmers().get(i).getName().equalsIgnoreCase(name)){
                //If the member is active it checks the remaining parameters, else returns passivePrice
                if(aLH.getAllSwimmers().get(i).getActive()){
                    //If the member is 17 or below it returns juniorPrice, else checks remaining parameters
                    if (aLH.getAllSwimmers().get(i).getActualAge() <= 17){
                        return juniorPrice;
                    } else{
                        //If the member is 60 or above it returns the seniorPrice, multiplied with the ageDiscount, else returns seniorPrice
                        if (aLH.getAllSwimmers().get(i).getActualAge() >= 60){
                            return seniorPrice * ageDiscount;
                        } else {
                            return seniorPrice;
                        }
                    }
                } else {
                    return passivePrice;
                }
            }
        }
        System.out.println("Kunne ikke finde en svømmer med det navn.");
        return 0.0;
    }

    //'listMissingPayments()' makes a list of all the allSwimmers where the 'hasPaid' boolean is false
    //and shows how much each member has to pay
    public void listMissingPayments(){
        System.out.println("Liste over medlemmer der er i restance:");
        for(int i = 0; i < aLH.getAllSwimmers().size(); i++){
            if(!aLH.getAllSwimmers().get(i).getHasPaid()){
                System.out.println("("+ (i + 1) +") " + aLH.getAllSwimmers().get(i).getName());
                System.out.println("\t Skal betale: " + calculatePrice(aLH.getAllSwimmers().get(i).getName()) + " kr.");
            }
        }
        System.out.println(" ");
    }

    //'setPaymentStatus()' changes the 'hasPaid' boolean of the selected member
    //As this member uses the arrayList 'allMembers', where all the CompetitiveSwimmer objects have been typecasted to Swimmer objects,
    //we can't just change the value in the 'allMembers' arrayList. We therefore changes it both in allMembers and in the corresponding
    //competitiveSwimmer object in the compSwimmer arrayList
    public void setPaymentStatus(){
        try{
            boolean changeMore = true;
            while(changeMore){
                Scanner input = new Scanner(System.in);
                listMissingPayments();

                System.out.println("Hvilket medlem vil du ændre betalingsstatus på?");
                int choice = input.nextInt(); input.nextLine();

                boolean correctChoice = false;
                while(!correctChoice){
                    if(choice < aLH.getAllSwimmers().size()){
                        if(!aLH.getAllSwimmers().get(choice - 1).getHasPaid()){
                            break;
                        }
                    }
                    System.out.println("Du skal vælge et gyldigt tal.");
                    choice = input.nextInt(); input.nextLine();
                }

                //Checks if the member is "Motionist" and changes the subcription status only in SwimmerList.txt
                if(aLH.getAllSwimmers().get(choice - 1).getActivity().equals("Motionist") && !aLH.getAllSwimmers().get(choice - 1).getHasPaid()){
                    aLH.getAllSwimmers().get(choice - 1).setHasPaid(!aLH.getAllSwimmers().get(choice - 1).getHasPaid());
                    sendInvoice(aLH.getAllSwimmers().get(choice - 1).getName(), aLH.getAllSwimmers().get(choice - 1).getEmail());
                } else{
                    for (int i = 0; i < aLH.getCompSwimmers().size(); i++){
                        //Checks if the member is "Competition Swimmer" and changes subscription status in both SwimmerList.txt and CompSwimmer.txt
                        if(aLH.getAllSwimmers().get(choice - 1).getName().equals(aLH.getCompSwimmers().get(i).getName()) && !aLH.getAllSwimmers().get(choice - 1).getHasPaid()){
                            aLH.getCompSwimmers().get(i).setHasPaid(!aLH.getCompSwimmers().get(i).getHasPaid());
                            aLH.getAllSwimmers().get(choice - 1).setHasPaid(!aLH.getAllSwimmers().get(choice - 1).getHasPaid());
                            sendInvoice(aLH.getAllSwimmers().get(choice - 1).getName(), aLH.getAllSwimmers().get(choice - 1).getEmail());
                        }
                    }
                }
                writeToFile();

                System.out.println("Vil du ændre endnu en betalingsstatus? Y/n");
                String choice0 = input.nextLine();
                while(!choice0.equals("Y") && !choice0.equals("n")){
                    System.out.println("Du skal vælge 'Y' eller 'n'.");
                    choice0 = input.nextLine();
                }
                if(choice0.equals("n")){
                    changeMore = false;
                }
            }
        } catch (InputMismatchException e){
            System.out.println("Ugyldigt input. Prøv igen. Errorcode: 103x10.\n");
        }
    }

    //'resetPaymentStatus()' is called once a year, and sets the 'hasPaid' boolean of all the allSwimmers to false
    //It works by instanciating an Date object, calling the toString method and checking if a certain substring is equal to Jan 01
    //If it is, then it loops trough all members and sets their hasPaid boolean to false
    public void resetPaymentStatus(){
        Date d = new Date();

        if(d.toString().substring(4, 10).equalsIgnoreCase("Jan 01")){
            for(int i = 0; i < aLH.getAllSwimmers().size(); i++){
                aLH.getAllSwimmers().get(i).setHasPaid(false);
            }
            System.out.println("Betalingsstatus for alle medlemmer er blevet nulstillet, da der er nyt år.");

            writeToFile();
        }
    }

    //'writeToFile()' method works by checking the objects in the allMembers array, if they are casual swimmers or competitive swimmers
    //It they're casual swimmers, it simply writes the info in the object to the correct file
    //If they're competitive swimmers, if finds the corresponding competitiveSwimmer object and writes that to the correct file
    public void writeToFile(){
        try{
            //Opens a stream to our MemberList
            FileOutputStream fosM = new FileOutputStream(aLH.getSwimmerList());
            FileOutputStream fosCM = new FileOutputStream(aLH.getCompSwimmerList());
            //Loop uses size of allSwimmers ArrayList to write every single Swimmer Object to MemberList
            for(int i = 0; i < aLH.getAllSwimmers().size(); i++) {
                if(aLH.getAllSwimmers().get(i).getActivity().equals("Motionist")){
                    PrintStream pS = new PrintStream(fosM);
                    pS.println(
                            aLH.realNameToUnderscore(aLH.getAllSwimmers().get(i).getName()) + " " +
                            aLH.getAllSwimmers().get(i).getAge() + " " +
                            aLH.getAllSwimmers().get(i).getEmail() + " " +
                            aLH.getAllSwimmers().get(i).getHasPaid() + " " +
                            aLH.getAllSwimmers().get(i).getActivity() + " " +
                            aLH.getAllSwimmers().get(i).getActive()
                    );
                } else {
                    for(int i0 = 0; i0 < aLH.getCompSwimmers().size(); i0++){
                        if(aLH.getAllSwimmers().get(i).getName().equals(aLH.getCompSwimmers().get(i0).getName())){
                            PrintStream pS = new PrintStream(fosCM);
                            pS.println(
                                    aLH.realNameToUnderscore(aLH.getCompSwimmers().get(i0).getName()) + " " +
                                    aLH.getCompSwimmers().get(i0).getAge() + " " +
                                    aLH.getCompSwimmers().get(i0).getEmail() + " " +
                                    aLH.getCompSwimmers().get(i0).getHasPaid() + " " +
                                    aLH.getCompSwimmers().get(i0).getActivity() + " " +
                                    aLH.getCompSwimmers().get(i0).getActive() + " " +
                                    aLH.getCompSwimmers().get(i0).getDisciplin() + " " +
                                    aLH.getCompSwimmers().get(i0).getTeam()
                            );
                        }
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Der skete en fejl med læsning af filer. Errorcode: 101x07.");
        }
    }
}