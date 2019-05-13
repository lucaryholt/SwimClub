public class CompetitiveSwimmer extends Swimmer{
    //CompetitiveSwimmer extends Swimmer, because it needs the same attributes and methods as Swimmer, as well as some additional ones

    //Atributs
    private String disciplin;
    private Team team;

    //Methods

    //getters
    public String getDisciplin(){
        return disciplin;
    }
    public Team getTeam(){
        return team;
    }
    //setters
    public void setDisciplin(String disciplin) {
        this.disciplin = disciplin;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
}