import java.util.*;

public class Customer{
  //initiating all variables for customer class
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private int age;
  private double Balance; //Customers account balance 
  private boolean hasCreditCard = false; //true if account has a card, false otherwise
  private double creditCardDue = 0;  //amount of money charged on account's credit card
  private ArrayList<HashMap<String, Double>> transactions = new ArrayList<>(); //debit transactions made by account
  
  
  public Customer(String u, String p, String fname, String lname, int a, double ba){
    username = u;
    password = p;
    firstName = fname;
    lastName = lname;
    age = a;
    balance = ba;
    
    if(balance != 0){
      newTransaction("Initial deposit", balance);       
    }
  }
  
  public void newTransaction(String transactionName, double transactionValue){
    Hashmap<String, double>  n = new HashMap<>();
    n.put(transactionName, transactionValue);
    transactions.add(n);
    balance += transactionValue;
  }
}
