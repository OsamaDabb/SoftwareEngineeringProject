import com.opencsv.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class main{
    public static void main(String args[]){
        ArrayList Data = loadData();
        if(Data == null){
            return;
        }
        HashMap<String, Banker> Bankers = (HashMap<String, Banker>) Data.get(0);
        HashMap<String, Customer> Customers = (HashMap<String, Customer>) Data.get(1);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you a [customer] or [banker]?: ");
        label:
        while(true){
            String response = scanner.nextLine().toLowerCase();

            switch (response) {
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
                                bankerLoop(currentBanker, Customers);
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
                        System.out.print("Password: ");
                        while(attempts > 0){
                            String password = scanner.nextLine();
                            //correct password given
                            if(password.equals(currentCustomer.getPassword())){
                                System.out.println("Signing in...");
                                customerLoop(currentCustomer);
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
        System.out.println(Customers.get("vish2"));
        saveData(Bankers, Customers);
    }

    private static void bankerLoop(Banker currentBanker, HashMap<String,Customer> customers) {

        Scanner in = new Scanner(System.in);
        System.out.println("Hi " + currentBanker.getName());
        int response = -1;
        while(response != 3) {

            System.out.print("1 for random queries; 2 for specific Customer; 3 to quit: ");
            response = in.nextInt();
            if(response == 1){

                for (Map.Entry mapElement : customers.entrySet()) {

                    Customer C = (Customer)mapElement.getValue();
                    ArrayList<String> queries = C.getQueries();
                    if(queries.size()==0)
                        continue;
                    String query = queries.get(0);
                    if(query.contains("?")){

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

                        case "transfer":

                            currentBanker.transfer(C, C, 0); // we ned to change this
                            break;

                        default:
                            break;
                    }

                    break;

                }

            }

            if(response == 2){

                System.out.println("What is the username of the customer you want to view?: ");
                String username = in.next();
                Customer C = customers.get(username);
                if (C == null){

                    System.out.println("User doesn't exist");
                    continue;

                }
                ArrayList<String> queries = C.getQueries();
                System.out.println("1 to address queries and 2 for independent toggling: ");
                int answer = in.nextInt();
                String query;
                if(answer == 1 )
                    query = queries.get(0);
                else if (answer == 2){

                    System.out.println("What would you like to do?: ");
                    query = in.next();

                }

                else {
                    System.out.println("Unreadable response");
                    continue;
                }

                if(query.contains("?")){

                    String[] contents = query.split("<");
                    currentBanker.transfer(C, customers.get(contents[1]), Double.parseDouble(contents[2]));
                    continue;

                }

                switch (query){

                    case "getCard":

                        currentBanker.createCard(C);
                        break;

                    case "deleteCard":
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

            if(response != 3)
                System.out.println("Unreadable response");

        }

    }

    //function for customer control flow
    public static void customerLoop(Customer customer){

        Scanner scan = new Scanner(System.in);
        ArrayList<String> queries = customer.getQueries();
        String query = "";
        while(!query.equals("quit")) {

            System.out.println("What would you like to have done?:");
            query = scan.next();
            if(query.equals("quit"))
                break;
            switch (query) {

                case "getCard":

                    queries.add(query);
                    break;

                case "deleteCard":
                    queries.add(query);
                    break;

                case "transfer":

                    System.out.println("To whom?: ");
                    String recipient = scan.next();
                    System.out.println("How much?: ");
                    double val = scan.nextDouble();
                    queries.add(query + "<" + recipient + "<" + val); // we ned to change this
                    break;

                case "deposit":

                    System.out.println("How much?: ");
                    double amount = scan.nextDouble();
                    customer.newTransaction(query, amount);
                    break;

                case "withdrawal":

                    System.out.println("How much?: ");
                    double quantity = scan.nextDouble();
                    customer.newTransaction(query, quantity);
                    break;

                default:
                    System.out.println("This operation is not supported");
                    break;
            }
        }

    }

    //method to open catalogue csv file, stores all customer and banker data into ArrayLists on memory
    public static ArrayList loadData() {
        //Open the CSV data file
        FileReader filereader;
        try{
            filereader = new FileReader("/Users/fish/Desktop/School/411/src/catalogue.csv");
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
                    boolean credit = Boolean.parseBoolean(item[8]);
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
                String[] bankerData = Bankers.get(key).createData();
                writer.writeNext((bankerData));
            }

            //writing each customer to csv file
            for(String key: Customers.keySet()){
                String[] customerData = Customers.get(key).createData();
                writer.writeNext(customerData);
            }

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
