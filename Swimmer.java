public class Swimmer extends Member{
    //Swimmer extends Member, because it needs the same attributes and methods as Swimmer, as well as some additional ones

    //Attributs
    private String activity;
    private boolean active;

    //Methods

    //getters
    public String getActivity(){
        return activity;
    }
    public boolean getActive(){
        return active;
    }
    //setters
    public void setActivity(String activity){
        this.activity = activity;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}