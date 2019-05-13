public class Team {
    //Attributs
    private String teamName;
    private Coach coach;

    //Methods

    //Getters
    public String getTeamName(){
        return teamName;
    }
    public Coach getCoach(){
        return coach;
    }

    //Setters
    public void setTeamName(String teamName){
        this.teamName = teamName;
    }
    public void setCoach(Coach coach){
        this.coach = coach;
    }

    public String toString(){
        return teamName;
    }
}