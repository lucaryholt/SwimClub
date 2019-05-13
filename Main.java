public class Main{
    public static void main(String[] args){
        ArrayListHandler aLH = new ArrayListHandler();
        SubscriptionHandler sH = new SubscriptionHandler(aLH);
        MemberHandler mH = new MemberHandler(aLH);
        ResultHandler rH = new ResultHandler(aLH);
        MenuHandler menuH = new MenuHandler(sH, mH, rH);

        menuH.loginMenu();
    }
}