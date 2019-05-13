import java.util.*;

public class Result{
    private String name = "null";
    private String swimMeet = "Træning";
    private int time = 0;
    private int placement = 0;
    private String disciplin = "";

    //As we save time in centiseconds, this method calculates those
    //into something we can actually read in the form of a string
    public String getFullTime(){
        int minutes = (int)(time/6000);
        int seconds = (int)((time%6000)/100);
        int centiSeconds = (int)((time%6000)%100);

        return minutes + "." + seconds + ":" + centiSeconds;
    }

    //This method is used when we sort the 'choosenResults' arrayList in 'getTop5()'
    //It works by comparing 'this' object with another Result object by 'time' and returning a 0, positive value or negative value
    //referencing if it's equal, greater than or less than the other Results 'time'
    public int compareTo(Result other){
        if(time < other.getTime()){
            return -1;
        } else if (time == other.getTime()){
            return 0;
        } else{
            return 1;
        }
    }

    //This method returns a string, containing the necesary information, depending on whether it's a training or competition result
    public String printResult(){
        if(swimMeet.equals("Træning")){
            return "Navn: "+name + ", tid: " + getFullTime();
        } else{
            return "Navn: "+name + ", stævne: " + swimMeet +  ", tid: " + getFullTime() +  ", placering: " + placement + ".";
        }
    }

    //getters
    public String getName(){
        return name;
    }

    public String getSwimMeet(){
        return swimMeet;
    }

    public int getTime(){
        return time;
    }

    public int getPlacement(){
        return placement;
    }

    public String getDisciplin(){
        return disciplin;
    }


    //Setters
    public void setName(String name){
        this.name = name;
    }

    public void setSwimMeet(String swimMeet){
        this.swimMeet = swimMeet;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setPlacement(int placement){
        this.placement = placement;
    }

    public void setDisciplin(String disciplin){
        this.disciplin = disciplin;
    }
}