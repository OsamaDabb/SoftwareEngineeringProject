import java.util.*;
import java.io.*;


public class main{
    public static void main(String args[]){
        ArrayList[] Data = loadData();
        if(Data == null){
            return;
        }
        ArrayList<Banker> Bankers = Data[0];
        ArrayList<Customer> Customers = Data[1];
        System.out.println(Data[0]);
        System.out.println(Data[1]);
    }

    //method to open catalogue csv file, stores all customer and banker data into ArrayLists on memory
    public static ArrayList[] loadData(){
        //Open the CSV data file
        Scanner scan;
        try{
            scan = new Scanner(new File("catalogue.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Catalogue.csv missing");
            return null;
        }
        ArrayList<Customer> Customers = new ArrayList<Customer>();
        ArrayList<Banker> Bankers = new ArrayList<Banker>();

        //seperate csv file by commas
        scan.useDelimiter(",");
        while(scan.hasNext()){
            int itemID = scan.nextInt();
            //control branch for banker data
            if(itemID == 0){
                //simple string/int/bool scans from csv file for basic banker data
                String u = scan.next();
                String p = scan.next();
                String f = scan.next();
                String l = scan.next();
                //call constructor on new banker then add banker to array of Bankers
                Banker newBanker = new Banker(u, p, f, l);
                Bankers.add(newBanker);
            }
            //control branch for customers
            else if(itemID == 1){
                //simple string/int/bool scans from csv file for basic user data
                String u = scan.next();
                String p = scan.next();
                String f = scan.next();
                String l = scan.next();
                int a = scan.nextInt();
                boolean froz = scan.nextBoolean();
                double b = scan.nextDouble();
                boolean credit = scan.nextBoolean();
                double creditDue = scan.nextDouble();

                //seperating csv for transactions (stored as: transID;transVal;transID;transVal pairs)
                String[] transactionList = scan.next().split(";");
                ArrayList<HashMap<String,Double>> transHash = new ArrayList<>();
                //array that pairs transaction IDs and transaction values in a ArrayList of HashMaps
                for(int i = 0; i < transactionList.length;i += 2){
                    String transName = transactionList[i];
                    System.out.println(transName);
                    double transValue = Double.parseDouble(transactionList[i+1]);
                    System.out.println(transValue);
                    HashMap<String,Double> n = new HashMap<>();
                    n.put(transName, transValue);
                    transHash.add(n);
                }

                //queries which are saved as query.query.query string on csv file, are seperated by
                // .split(".") and saved as Array
                String[] queryList = scan.next().split(".");
                ArrayList<String> queries = new ArrayList<>(List.of(queryList));

                //adds new customer to ArrayList Customers
                Customer newCustomer = new Customer(u,p,f,l,a,froz,b,credit,creditDue,transHash, queries);
                Customers.add(newCustomer);
            }
        }

        //return the new ArrayLists to the main method
        return new ArrayList[] {Bankers, Customers};
    }

    public void saveData(){


    }
}
