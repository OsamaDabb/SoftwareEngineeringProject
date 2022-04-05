import com.opencsv.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;


public class main{
    public static void main(String[] args){
        ArrayList Data = loadData();
        if(Data == null){
            return;
        }
        HashMap<String, Banker> Bankers = (HashMap<String, Banker>) Data.get(0);
        HashMap<String, Customer> Customers = (HashMap<String, Customer>) Data.get(1);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to COVA Bank");
        System.out.print("Are you a [customer] or [banker] or creating a [new] account?: ");
        label:
        while(true){
            String response = scanner.nextLine().toLowerCase();

            switch (response) {
                //creating a new account
                case "new":
                    System.out.print("Would you like to create a [customer] or [banker] account?: ");
                    String resp = scanner.nextLine();
                    switch(resp){
                        case "customer":
                            System.out.print("First Name?: ");
                            String f = scanner.nextLine();
                            System.out.print("Last Name?: ");
                            String l = scanner.nextLine();
                            System.out.print("Username?: ");
                            String u = scanner.nextLine();
                            System.out.print("Password?: ");
                            String p = scanner.nextLine();
                            System.out.print("Age?: ");
                            int a = scanner.nextInt();
                            if(a < 13){
                                System.out.println("Sorry, you must be at least 13 to create an account.");
                            }
                            else{
                                System.out.print("How much would you like to initial deposit?: ");
                                double ba = scanner.nextDouble();
                                System.out.println("Creating account...");
                                Customer newCustomer = new Customer(u,p,f,l,a,ba);
                                Customers.put(u,newCustomer);
                                System.out.println("Welcome to CAVO bank, " + f + " " + l + "!");
                                System.out.println("Returning to login screen...");
                            }
                            break;

                        case "banker":
                            System.out.print("First Name?: ");
                            String fb = scanner.nextLine();
                            System.out.print("Last Name?: ");
                            String lb = scanner.nextLine();
                            System.out.print("Username?: ");
                            String ub = scanner.nextLine();
                            System.out.print("Password?: ");
                            String pb = scanner.nextLine();
                            System.out.print("Enter authentication code: ");
                            if(scanner.nextLine().equals("COVAcode")){
                                System.out.println("Creating account...");
                                Banker newBanker = new Banker(ub,pb,fb,lb);
                                Bankers.put(ub,newBanker);
                                System.out.println("Welcome to CAVO bank, " + fb + " " + lb + "!");
                                System.out.println("Returning to login screen...");
                            }
                            else{
                                System.out.println("Incorrect authorization code, please contact support.");
                            }
                            break;



                    }
                    break;

                //if the user is a banker, ask for username and check valid password (only three attempts) then
                //activate banker control flow
                case "banker":
                    System.out.print("Username: ");
                    String bUsername = scanner.nextLine();
                    Banker currentBanker = Bankers.get(bUsername);
                    if (currentBanker != null) {
                        int attempts = 3;
                        System.out.print("Password: ");
                        while(attempts > 0){
                            String password = scanner.nextLine();
                            //correct password
                            if(password.equals(currentBanker.getPassword())){
                                System.out.println("Signing in...");
                                try
                                {
                                    // Delay for 2 seonds
                                    Thread.sleep(2000);
                                }
                                catch(InterruptedException ex)
                                {
                                    ex.printStackTrace();
                                }
                                Bankers = bankerLoop(currentBanker, Customers, Bankers);
                                break;
                            }
                            //incorrect password
                            else{
                                attempts--;
                                System.out.println("Incorrect, you have " + attempts + " attempts left.");
                            }
                        }
                        break label;

                    }
                    //if an invalid username is given
                    else{
                        System.out.println("User does not exist");
                    }
                    break;

                //if the user is a customer, ask for username and check valid password (only three attempts) then
                //activate customer control flow
                case "customer":
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    Customer currentCustomer = Customers.get(username);
                    if (currentCustomer != null) {
                        int attempts = 3;
                        while(attempts > 0){
                            System.out.print("Password: ");
                            String password = scanner.nextLine();
                            //correct password given
                            if(password.equals(currentCustomer.getPassword())){
                                System.out.println("Signing in...");
                                try
                                {
                                    // Delay for 2 seonds
                                    Thread.sleep(2000);
                                }
                                catch(InterruptedException ex)
                                {
                                    ex.printStackTrace();
                                }
                                Customers = customerLoop(currentCustomer, Bankers, Customers);
                                break;
                            }
                            //incorrect password
                            else{
                                attempts--;
                                System.out.println("Incorrect, you have " + attempts + " attempts left.");
                            }
                        }
                        break label;

                    }
                    //if invalid username is given
                    else{
                        System.out.println("User does not exist");
                    }
                    break;

                //if user requests to quit
                case "quit":
                    break label;
                default:
                    System.out.println("Response not recognized.");
                    break;
            }
            System.out.print("would you like to [quit] or log in as a new [customer] or [banker]?: ");
        }

        //saves any changes to the csv file
        System.out.println("Saving and exiting...");
        saveData(Bankers, Customers);
    }

    private static HashMap<String, Banker> bankerLoop(Banker currentBanker,
                                                      HashMap<String,Customer> customers, HashMap<String, Banker> bankers) {

        Scanner in = new Scanner(System.in);
        System.out.println("Hi " + currentBanker.getName());
        int response = -1;
        int count =  0;
        while(response != 3 && bankers.get(currentBanker.getUsername()) != null) {

            if(count % 3 == 0){

                System.out.println("Saving");
                saveData(bankers, customers);

            }
            count++;
            System.out.print("1 for random queries; 2 for specific Customer; 3 to quit, 4 to delete account: ");
            response = in.nextInt();
            if(response == 1){

                for (Map.Entry mapElement : customers.entrySet()) {

                    Customer C = (Customer)mapElement.getValue();
                    ArrayList<String> queries = C.getQueries();
                    if(queries.size()==0)
                        continue;
                    String query = queries.get(0);
                    System.out.println(C);
                    System.out.println("You are solving " + query);
                    if(query.contains("<")){

                        String[] contents = query.split("<");
                        currentBanker.transfer(C, customers.get(contents[1]), Double.parseDouble(contents[2]));
                        break;

                    }
                    switch (query){

                        case "getCard":

                            currentBanker.createCard(C);
                            break;

                        case "deleteCard":
                            currentBanker.deleteCard(C);
                            break;

                        default:
                            break;
                    }

                    break;

                }

            }

            if(response == 2){

                System.out.print("What is the username of the customer you want to view?: ");
                String username = in.next();
                Customer C = customers.get(username);
                if (C == null){

                    System.out.println("User doesn't exist");
                    continue;

                }
                ArrayList<String> queries = C.getQueries();
                System.out.println(C);
                System.out.print("1 to address queries and 2 for independent toggling: ");
                int answer = in.nextInt();
                String query;
                if(answer == 1) {
                    query = queries.get(0);
                    queries.remove(0);
                    System.out.println("You are solving " + query);

                }
                else if (answer == 2){

                    System.out.println("[getCard],[deleteCard],[freeze],[defreeze]");
                    System.out.print("What would you like to do?: ");
                    query = in.next();

                }

                else {
                    System.out.println("Unreadable response");
                    continue;
                }

                if(query.contains("<")){

                    String[] contents = query.split("<");
                    currentBanker.transfer(C, customers.get(contents[1]), Double.parseDouble(contents[2]));
                    continue;

                }

                switch (query){

                    case "getcard":

                        currentBanker.createCard(C);
                        break;

                    case "deletecard":
                        currentBanker.deleteCard(C);
                        break;

                    case "freeze":

                        currentBanker.freeze(C);
                        break;

                    case "defreeze":
                        currentBanker.defreeze(C);
                        break;

                    default:
                        System.out.println("This operation is not supported");
                        break;
                }

            }
            if(response == 4){
                System.out.println("Are you sure you want to delete this account?[y]/[n]: ");
                if(in.nextLine().equals("y")){
                    System.out.println("Deleting account...");
                    currentBanker = null;
                    System.out.println("Thank you for working for us.");
                    break;
                }
                else{
                    System.out.println("returning...");
                }
            }

            if(response != 3 && response != 2 && response != 1 && response != 4)
                System.out.println("Unreadable response");

        }
        return bankers;
    }

    //function for customer control flow
    public static HashMap<String, Customer> customerLoop(Customer customer, HashMap<String, Banker> Bankers,  HashMap<String, Customer> customers){

        if(customer.isFrozen()){
            System.out.println("Your account has been frozen due to suspicious activity,");
            System.out.println("Please Call us at 555-555-555 to reinstate the account.");
            return customers;
        }

        if(customer.hasCreditCard() == 2){
            System.out.println("Congratulations, your credit card request has been approved!");
            customer.setCreditCard(1);
        }
        else if(customer.hasCreditCard() == -1){
            System.out.println("Your credit card has been successfully cancelled");
            customer.setCreditCard(0);
        }

        Scanner scan = new Scanner(System.in);
        ArrayList<String> queries = customer.getQueries();
        String query = "";
        int count = 0 ;
        while(customers.get(customer.getUsername()) != null) {

            
            if(count % 3 == 0){
                
                System.out.println("Saving");
                saveData(Bankers, customers);

            }
            count++;
            System.out.println("[getCard],[deleteCard],[transfer],[deposit], \n" +
                    "[withdrawal],[viewTransactions],[viewBalance],[deleteAccount],[quit]");
            System.out.print("What would you like to have done?: ");
            query = scan.next().toLowerCase();
            if(query.equals("quit"))
                break;
            switch (query) {

                case "getcard":

                    queries.add(query);
                    break;

                case "deletecard":
                    queries.add(query);
                    break;

                case "transfer":

                    System.out.print("To whom?: ");
                    String recipient = scan.next();
                    System.out.print("How much?: ");
                    double val = scan.nextDouble();
                    queries.add(query + "<" + recipient + "<" + val); // we need to change this
                    break;

                case "deposit":

                    System.out.print("How much?: ");
                    double amount = scan.nextDouble();
                    customer.newTransaction(query, amount);
                    break;

                case "withdrawal":

                    System.out.print("How much?: ");
                    double quantity = scan.nextDouble();
                    customer.newTransaction(query, -1*quantity);
                    break;

                case "viewtransactions":
                    System.out.println();
                    System.out.println("Most recent transactions: ");
                    ArrayList<HashMap<String,Double>> trans = customer.getTransactions();
                    for(int i = trans.size() - 1;
                        i >= Math.max(0,trans.size() - 11);i--){
                        for(String key: trans.get(i).keySet()){
                            System.out.println(" -" + key + ": " + trans.get(i).get(key));
                        }
                    }
                    System.out.println();
                    break;

                case "viewbalance":
                    System.out.println();
                    System.out.println(" -Your current balance is: " + customer.getBalance());
                    System.out.println();
                    break;

                case "deleteaccount":
                    if(customer.getBalance() == 0 && customer.getCreditCardDue() == 0){
                        System.out.print("Are you sure you want to delete your account? [y]/[n]");
                        String response = scan.next();
                        if(response.equals("y")){
                            System.out.println("Thank you for your business...");
                            customers.remove(customer.getUsername());
                            System.out.println("Account deleted");
                            break;
                        }
                    }
                    else{
                        System.out.println("You cannot close your account when you have outstanding charges or " +
                                "funds.");
                    }
                    break;


                default:
                    System.out.println("This operation is not supported");
                    break;
            }
        }
        return customers;
    }

    //method to open catalogue csv file, stores all customer and banker data into ArrayLists on memory
    public static ArrayList loadData() {
        //Open the CSV data file
        FileReader filereader;
        try{
            filereader = new FileReader("catalogue.csv");
        } catch (FileNotFoundException e) {
            System.out.println("Catalogue.csv missing");
            return null;
        }
        CSVReader csvReader = new CSVReader(filereader);

        HashMap<String, Object> CustomerMap = new HashMap<>();
        HashMap<String, Object> BankerMap = new HashMap<>();

        //seperate csv file by commas
        String[] item;
        try {
            while ((item = csvReader.readNext()) != null) {
                int itemID = Integer.parseInt(item[0]);
                //control branch for banker data
                //bankers expect 4 input variables
                if (itemID == 0) {
                    //simple string/int/bool scans from csv file for basic banker data
                    String u = item[1];
                    String p = item[2];
                    String f = item[3];
                    String l = item[4];
                    //call constructor on new banker then add banker to array of Bankers
                    Banker newBanker = new Banker(u, p, f, l);
                    BankerMap.put(u, newBanker);
                }
                //control branch for customers
                //customers expect 11 inputs of data
                else if (itemID == 1) {
                    //simple string/int/bool scans from csv file for basic user data
                    String u = item[1];
                    String p = item[2];
                    String f = item[3];
                    String l = item[4];
                    int a = Integer.parseInt(item[5]);
                    boolean froz = Boolean.parseBoolean(item[6]);
                    double b = Double.parseDouble(item[7]);

                    int credit = Integer.parseInt(item[8]);
                    System.out.println("reached");

                    double creditDue = Double.parseDouble(item[9]);
                    //seperating csv for transactions (stored as: transID;transVal;transID;transVal pairs)
                    String[] transactionList = item[10].split(";");
                    ArrayList<HashMap<String, Double>> transHash = new ArrayList<>();
                    //array that pairs transaction IDs and transaction values in a ArrayList of HashMaps
                    for (int i = 0; i < transactionList.length; i += 2) {
                        String transName = transactionList[i];
                        double transValue = Double.parseDouble(transactionList[i + 1]);
                        HashMap<String, Double> n = new HashMap<>();
                        n.put(transName, transValue);
                        transHash.add(n);
                    }

                    //queries which are saved as query.query.query string on csv file, are seperated by
                    // .split(".") and saved as Array
                    String quer = item[11];
                    ArrayList<String> queries;
                    //queries are saved as "None" in saveData() if a user has no queries
                    if (quer.equals("None")) {
                        queries = new ArrayList<>();
                    } else {
                        String[] queryList = quer.split(";");
                        queries = new ArrayList<>(List.of(queryList));
                    }

                    //adds new customer to ArrayList Customers
                    Customer newCustomer = new Customer(u, p, f, l, a, froz, b, credit, creditDue, transHash, queries);
                    CustomerMap.put(u,newCustomer);
                }
            }

            csvReader.close();
            //return the new ArrayLists to the main method
            ArrayList<HashMap<String,Object>> x = new ArrayList<HashMap<String,Object>>();
            x.add(BankerMap);
            x.add(CustomerMap);
            return x;
        }
        catch(Exception e) {
            System.out.println(e);
            System.out.println("Formatting error while loading data");
            return null;
        }
    }

    public static void saveData(HashMap<String, Banker> Bankers, HashMap<String, Customer> Customers){
        File file = new File("catalogue.csv");
        //saving the previous iteration of catalogue as a backup
        File backup = new File("backup.csv");
        Path path = (Path) Paths.get("catalogue.csv");
        Path out = Paths.get("backup.csv");
        try{
            Files.copy(path,out);
        }
        catch(IOException e){}
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            //writing each banker to csv file
            for(String key: Bankers.keySet()){
                if(Bankers.get(key) != null) {
                    String[] bankerData = Bankers.get(key).createData();
                    writer.writeNext((bankerData));
                }
            }

            //writing each customer to csv file
            for(String key: Customers.keySet()){
                if(Customers.get(key) != null) {
                    String[] customerData = Customers.get(key).createData();
                    writer.writeNext(customerData);
                }
            }

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
