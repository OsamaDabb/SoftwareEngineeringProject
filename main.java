import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

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
        System.out.println(Bankers);
        System.out.println(Customers);

        Bankers.add(new Banker("CamA","password","Cameron","Anderegg"));

        Customers.add(new Customer("Azam25","0704","Azamat","Khan",20,500));
        saveData(Bankers, Customers);
    }

    //method to open catalogue csv file, stores all customer and banker data into ArrayLists on memory
    public static ArrayList[] loadData() {
        //Open the CSV data file
        FileReader filereader;
        try{
            filereader = new FileReader("catalogue.csv");
        } catch (FileNotFoundException e) {
            System.out.println("Catalogue.csv missing");
            return null;
        }
        CSVReader csvReader = new CSVReader(filereader);

        ArrayList<Customer> Customers = new ArrayList<Customer>();
        ArrayList<Banker> Bankers = new ArrayList<Banker>();

        //seperate csv file by commas
        String[] item;
        try {
            while ((item = csvReader.readNext()) != null) {
                System.out.println(item[0]);
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
                    Bankers.add(newBanker);
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
                    if (quer == "None") {
                        queries = new ArrayList<>();
                    } else {
                        String[] queryList = quer.split(".");
                        queries = new ArrayList<>(List.of(queryList));
                    }

                    //adds new customer to ArrayList Customers
                    Customer newCustomer = new Customer(u, p, f, l, a, froz, b, credit, creditDue, transHash, queries);
                    Customers.add(newCustomer);
                }
            }

            csvReader.close();
            //return the new ArrayLists to the main method
            return new ArrayList[]{Bankers, Customers};
        }
        catch(Exception e) {
            System.out.println("Formatting error while loading data");
            return null;
        }
    }

    public static void saveData( ArrayList<Banker> Bankers, ArrayList<Customer> Customers){
        File file = new File("catalogue.csv");
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            //writing each banker to csv file
            for(int i = 0;i < Bankers.size();i++){
                String[] bankerData = Bankers.get(i).createData();
                writer.writeNext(bankerData);
            }

            //writing each customer to csv file
            for(int i = 0;i < Customers.size(); i++){
                String[] customerData = Customers.get(i).createData();
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
