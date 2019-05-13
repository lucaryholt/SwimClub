import java.util.Calendar;

public abstract class Member {
    //Attributs
    private String name;
    private int age;
    private String email;
    private boolean hasPaid;

    //methods

    //The getActualAge method returns the actual age of the member, instead of the parameter 'age', that contains the Members birth year
    //The method instanciates a Date object, calls the objects toString method, and a substring method on the resulting string, to extract the actual year
    public int getActualAge(){
        int year = Calendar.getInstance().get(Calendar.YEAR);

        return year - age;
    }

    //Getters
    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public String getEmail(){
        return email;
    }

    public boolean getHasPaid(){
        return hasPaid;
    }
    //setters
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }
}