public class Banker{
  
    private String firstName; // first name
    private String lastName; // last name
    private String username;
    private String password;

    public Banker(String f, String l, String u, String p){ // constructor for the Banker

      firstName = f;
      lastName = l;
      username = u;
      password = p;
      
    }
  
    public void createCard(Customer C){ 
   
      if(C.hasCreditCard()) // If they already have a credit card
        throw new IllegalArgumentException("Already has a credit card");
      if(C.getAge() < 18) // Minors can't have cards
        throw new IllegalArgumentException("Underage");
      C.setCreditCard(1);
    
    }
  
    public void deleteCard(Customer C){
     
      if(!C.hasCreditCard()) // They don't have one anyway
        throw new IllegalArgumentException("No Card Found");
      if(C.getCreditCardDue()!=0) // They need to settle all their bills before closing it
        throw new IllegalArgumentException("Bills not paid");
      C.setCreditCard(0);

    }
  
    public void freeze(Customer C){
     
      if(C.getIsFrozen()==1) // The accoutn is already frozen
        throw new IllegalArgumentException("Account already frozen");
      C.setIsFrozen(1);
      
    }
  
    public void defreeze(Customer C){

        if(C.getIsFrozen()==0) // The account was already defrozen
          throw new IllegalArgumentException("Account already defrozen");
        C.setIsFrozen(0);

     }
  
    public void transfer(Customer C1, Customer C2, double money){ // transfer money between accounts
     
      if(C1.getBalance() < money) // If the balance isn't enough to transfer
        throw new IllegalArgumentException("Not enough balance");
      C2.newTransaction("Account transfer from" + C1.getUsername(), money); // Put money into C2
      C1.newTransaction("Account transfer to" + C1.getUsername(), money); // Take money out of C1
      
    }
    
}
