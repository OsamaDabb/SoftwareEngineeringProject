import java.util.*;
import java.io.*;


public class main{
    public static void main(String args[]){
        if(loadData()){

            saveData();
        }
        else{
            return;
        }




    }
  
    public static boolean loadData(){
        //Open the CSV data file
        Scanner scan;
        try{
            scan = new Scanner(new File("catalogue.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Catalogue.csv missing");
            return false;
        }
        ArrayList<Customer> Customers = new ArrayList<Customer>();
        ArrayList<Banker> Bankers = new ArrayList<Banker>();

        scan.useDelimiter(",");
        while(scan.hasNsext()){
            int itemID = scan.nextInt();
            if(itemID == 0){
                String u = scan.next();
                String p = scan.next();
                String f = scan.next();
                String l = scan.next();
                Banker newBanker = new Banker(u, p, f, l);
            }
            else if(itemID == 1){
                String u = scan.next();
                String p = scan.next();
                String f = scan.next();
                String l = scan.next();

            }
        }

        return true;
    }

    public void saveData(){


    }
}
