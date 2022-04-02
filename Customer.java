import java.util.*;

public class Customer{
  //initiating all variables for customer class
  private final String username;
  private String password;
  private final String firstName;
  private final String lastName;
  private int age;
  private boolean isFrozen; //true if account has been frozen, false otherwise
  private double balance; //Customers account balance
  private boolean hasCreditCard = false; //true if account has a card, false otherwise
  private double creditCardDue = 0;  //amount of money charged on account's credit card
  private ArrayList<HashMap<String, Double>> transactions = new ArrayList<>(); //debit transactions made by account
  private ArrayList<String> queries = new ArrayList<>();
  
  
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

  public Customer(String u, String p, String fname, String lname, int a, boolean froz, double ba,
                  boolean credit, double creditDue, ArrayList<HashMap<String, Double>> transact, ArrayList<String> quer){
    username = u;
    password = p;
    firstName = fname;
    lastName = lname;
    age = a;
    isFrozen = froz;
    balance = ba;
    hasCreditCard = credit;
    creditCardDue = creditDue;
    transactions = transact;
    queries = quer;
  }

  @Override
  //Simple toString for unit test purposes, lists all Customer attributes
  public String toString() {
    return "Customer{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", age=" + age +
            ", isFrozen=" + isFrozen +
            ", balance=" + balance +
            ", hasCreditCard=" + hasCreditCard +
            ", creditCardDue=" + creditCardDue +
            ", transactions=" + transactions +
            ", queries=" + queries +
            '}';
  }

  public String[] createData(){
    ArrayList<String> transactionArray = new ArrayList<>();
    for(HashMap map: transactions) {
      for (Object val : map.keySet()) {
        transactionArray.add(String.valueOf(val));
        transactionArray.add(String.valueOf(map.get(val)));
      }
    }
    String transactionString = String.join(";",transactionArray);

    String queryString;
    if(queries.isEmpty()){
      queryString = "None";
    }
    else {
      queryString = String.join(".", queries);
    }

    return new String[] {"1",username,password,firstName,lastName,
            String.valueOf(age),String.valueOf(isFrozen),String.valueOf(balance),String.valueOf(hasCreditCard),
            String.valueOf(creditCardDue),transactionString,queryString};
  }

  //newTransaction method that changes account value, and adds new transaction to transactions ArrayList
  public void newTransaction(String transactionName, double transactionValue){
    HashMap<String, Double>  n = new HashMap<>();
    n.put(transactionName, transactionValue);
    transactions.add(n);
    balance += transactionValue;
  }
  
  
  //Getter and setter methods for all the private variables associated with the customer class
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
  
  public boolean isFrozen(){
    return isFrozen; 
  }
  
  public void setFrozen(boolean newVal){
    isFrozen = newVal;
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
    return creditCardDue;
  }
  
  public void setCreditCardDue(double newVal){
   creditCardDue = newVal;
  }
  
  public ArrayList getTransactions(){
   return transactions; 
  }
  
  public ArrayList getQueries(){
    
    return queries;
    
  }
}
