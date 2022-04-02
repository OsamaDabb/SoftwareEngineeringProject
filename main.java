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
    }
  
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

        scan.useDelimiter(",");
        while(scan.hasNext()){
            int itemID = scan.nextInt();
            if(itemID == 0){
                String u = scan.next();
                String p = scan.next();
                String f = scan.next();
                String l = scan.next();
                Banker newBanker = new Banker(u, p, f, l);
                Bankers.add(newBanker);
            }
            else if(itemID == 1){
                String u = scan.next();
                String p = scan.next();
                String f = scan.next();
                String l = scan.next();
                int a = scan.nextInt();
                boolean froz = scan.nextBoolean();
                double b = scan.nextDouble();
                boolean credit = scan.nextBoolean();
                double creditDue = scan.nextDouble();
                String[] transactionList = scan.next().split(".");
                ArrayList<HashMap<String,Double>> transHash = new ArrayList<>();
                for(int i = 0;i < transactionList.length/2;i += 2){
                    String transName = scan.next();
                    double transValue = scan.nextDouble();
                    HashMap<String,Double> n = new HashMap<>();
                    n.put(transName, transValue);
                    transHash.add(n);
                }
                String[] queryList = scan.next().split(".");
                ArrayList<String> queries = new ArrayList<String>(List.of(queryList));
                Customer newCustomer = new Customer(u,p,f,l,a,froz,b,credit,creditDue,transHash, queries);
                Customers.add(newCustomer);
            }
        }


        return new ArrayList[] {Bankers, Customers};
    }

    public void saveData(){


    }
}
