import java.util.*;

public class Banker{

    private String username;
    private String password;
    private String firstName; // first name
    private String lastName; // last name
    private ArrayList<HashMap<String, String>> record= new ArrayList<>();

    public Banker(String u, String p, String f, String l){ // constructor for the Banker

        username = u;
        password = p;
        firstName = f;
        lastName = l;

    }

    @Override
    public String toString() {
        return "Banker{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", record=" + record +
                '}';
    }

    public String[] createData(){
        return new String[] {"0", username, password, firstName, lastName};
    }

    public void createEntry(Customer C, String method){ // adds an entry to the record array list: method is the name of the method

        HashMap<String, String> rec = new HashMap<>();
        rec.put(C.getUsername(), method);
        record.add(rec);

    }

    public void createCard(Customer C){

        if(C.hasCreditCard() == 1 || C.hasCreditCard() == 2) // If they already have a credit card
            throw new IllegalArgumentException("Already has a credit card");
        if(C.getAge() < 18) // Minors can't have cards
            throw new IllegalArgumentException("Underage");
        C.setCreditCard(2);

        createEntry(C, "createCard");

    }

    public void deleteCard(Customer C){

        if(C.hasCreditCard() == 0 || C.hasCreditCard() == -1) // They don't have one anyway
            throw new IllegalArgumentException("No Card Found");
        if(C.getCreditCardDue()!=0) // They need to settle all their bills before closing it
            throw new IllegalArgumentException("Bills not paid");
        C.setCreditCard(-1);

        createEntry(C, "deleteCard");

    }

    public void freeze(Customer C){

        if(C.isFrozen()) // The accoutn is already frozen
            throw new IllegalArgumentException("Account already frozen");
        C.setFrozen(true);
        createEntry(C, "freeze");

    }

    public void defreeze(Customer C){

        if(!C.isFrozen()) // The account was already defrozen
            throw new IllegalArgumentException("Account already defrozen");
        C.setFrozen(false);
        createEntry(C, "defreeze");

    }

    public void transfer(Customer C1, Customer C2, double money){ // transfer money between accounts

        if(C1.getBalance() < money) // If the balance isn't enough to transfer
            throw new IllegalArgumentException("Not enough balance");
        C2.newTransaction("Account transfer from " + C1.getUsername(), money); // Put money into C2
        C1.newTransaction("Account transfer to " + C1.getUsername(), -1 * money); // Take money out of C1
        createEntry(C1, "transferout");
        createEntry(C2, "transferin");


    }

    public String getPassword(){
        return password;
    }

    public String getName(){

        return firstName + " " + lastName;

    }

    public String getUsername(){
        return username;
    }

}
