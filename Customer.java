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
  
  public String getUsername(){
    return username;
  }
  
  public String getPassword(){
    return password; 
  }
  
  public void setPassword(String newPassword){
    password = newPassword; 
  }
  
  public String getFirstName(){
    return firstName; 
  }
  
  public String getLastName(){
    return lastName; 
  }
  
  public int getAge(){
   return age; 
  }
  
  public double getBalance(){
    return balance; 
  }
  
  public boolean hasCreditCard(){
    return hasCreditCard;
  }
  
  public void setCreditCard(boolean newCard){
   hasCreditCard = newCard;
  }
  
  public double getCreditCardDue(){
    return creditCardDue 
  }
  
  public void setCreditCardDue(double newVal){
   creditCardDue = newVal;
  }
  
  public ArrayList getTransactions(){
   return transactions; 
  }
}
