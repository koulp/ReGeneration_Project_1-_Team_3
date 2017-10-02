package view;

import controller.CSV;
import controller.MySqlRequest;
import model.Owner;
import model.Vehicles;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Show {
    Scanner sc = new Scanner(System.in);
    Vehicles vehicles =new Vehicles();
    private String plate = "";
    private int ex=0;

    public void shows() throws FileNotFoundException, ParseException, SQLException, ClassNotFoundException{
        System.out.println("---Select Functionality to perform");
        System.out.println("*1 Vehicle Insurance Status");
        System.out.println("*2 Forecoming Expiries");
        System.out.println("*3 Expiries by plate");
        System.out.println("*4 Calculate the Total Fine Cost");
        System.out.println("*5 exit");
        int i=sc.nextInt();
        choice(i);
    }
    // Menu switch case
    public void choice(int i) throws ClassNotFoundException, FileNotFoundException, SQLException, ParseException {
            switch (i) {
                case 1:                     // F1 : Status
                    status();
                case 2:                     // F2 : Forecoming expired Vehicles
                    forecoming_expired();
                case 3:                     //F3 : Sorting
                    sort_table();
                case 4:                     //Owner Fine
                    cost();
                case 5:                     //Exit
                    System.exit(0);
                default:                    //Out of Range Number
                    System.out.println("This is not an available option");
                    System.out.println("Enter a number between 1 or 5");
                    try {
                        i = Integer.parseInt(sc.next());
                    } catch (NumberFormatException e) {
                        System.out.println("Please provide a valid number");
                    }
                    choice(i);
            }
        }

    // importSystem and exportSystem for validating options from user
    public int importSystem() {
        int importType=0;
        System.out.println("---Enter Import Type");
        System.out.println("*1 CSV");
        System.out.println("*2 Database");
        importType = sc.nextInt();
        while (importType < 1 || importType > 2) {
            System.out.println("This is not an available option");
            System.out.println("Enter a number between 1 or 2");
           // importType = sc.nextInt();
            try {
                importType = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Please provide a valid number");
            }
        }return importType;
    }

    public int exportSystem(){
        int exportType=0;
        System.out.println("---Enter Export Type");
        System.out.println("*1 CSV");
        System.out.println("*2 Console");
        exportType=sc.nextInt();
        while(exportType<1 || exportType>2){
            System.out.println("This is not an available option");
            System.out.println("Enter a number between 1 or 2");
            //exportType=sc.nextInt();
            try{
                exportType= Integer.parseInt(sc.next());
            }catch(NumberFormatException e){
                System.out.println("Please provide a valid number");
            }
        }
        return exportType;
    }


    //methods for each case
    public void status() throws FileNotFoundException, ParseException, SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        int importType=0, exportType=0;
        Owner owner = new Owner();
        System.out.println("Provide a Licence Plate of a Vehicle in ABC-1234 format ex. EET-4788");

        plate = sc.nextLine();
        while (!plate.matches("[A-Z]{3}-[0-9]{4}")){
            System.out.println("Provide a Licence Plate in a correct format ex. EET-4788");
            plate=sc.nextLine();
        }

        importType=importSystem();
        if(importType==1){
            CSV csv = new CSV();
            owner = csv.importfileF1(plate);
        }else if(importType==2){
            System.out.println("Console");
            MySqlRequest request = new MySqlRequest();
            owner = request.fetchplateone(plate);
        }
        //Epilogh Export kai pratoume analoga sthn methodo plateone
        exportType=exportSystem();
        vehicles.plateone(plate, owner, exportType);
        vehicles.navigation();
    }

    public void forecoming_expired() throws FileNotFoundException, ParseException, SQLException, ClassNotFoundException {

        ArrayList<Owner> importlist = new ArrayList<>();
        ArrayList<Owner> exportlist = new ArrayList<>();
        int importType, exportType =0;

        System.out.println("Provide a number of days to see what is going to Expire");
        ex=sc.nextInt();

        //ImportSystem gia eisodo CSV h database kai pratw analoga
        importType=importSystem();
        if (importType == 1) {
            CSV csv = new CSV();
            importlist=csv.importFileF2();

        } else if (importType == 2) {
            MySqlRequest request = new MySqlRequest();
            importlist = request.fetchplates2();
        }

        //ExportSystem gia eksodo CSV h konsolas kai pratw analoga
        exportType =exportSystem();
        if(exportType==1){
            CSV csv = new CSV();
            exportlist =vehicles.plates(importlist, ex);
            csv.exportfile(exportlist);
        }else if(exportType==2){
            exportlist =vehicles.plates(importlist, ex);
            System.out.println("---These Plates are going to Expire in:" + ex + " Days");
            for (int i = 0; i < exportlist.size(); i++) {
                System.out.println(exportlist.get(i).getPlates() + " " + exportlist.get(i).getDate());
            }
        }
        vehicles.navigation();
    }

    public void sort_table() throws FileNotFoundException, ParseException, SQLException, ClassNotFoundException {
        CSV csv = new CSV();
        int exportType=0;

        ArrayList<Owner> importedlist = csv.importfileF3();
        ArrayList<Owner> sortedlist = vehicles.sorting(importedlist);

        exportType =exportSystem();
        if (exportType ==1){
            csv.exportfile(sortedlist);
        }
        else if(exportType ==2){
            for(int i=0; i<sortedlist.size(); i++){
                System.out.println(sortedlist.get(i).getPlates() + " " + sortedlist.get(i).getAfm() + " " + sortedlist.get(i).getDate());
            }
        }
        vehicles.navigation();
    }

    public void cost() throws FileNotFoundException, ParseException, SQLException, ClassNotFoundException {
        System.out.println("Please enter your afm  ex. 818386667");
        String afm =sc.next();
        System.out.println("You Typed: "+ afm);
        System.out.println("Now Please enter number of fine");
        int fine =sc.nextInt();

        MySqlRequest request = new MySqlRequest();
        ArrayList<Owner> fineList= request.fetchafm(afm, fine);
        vehicles.fine(fine, fineList);
        vehicles.navigation();
    }
}
