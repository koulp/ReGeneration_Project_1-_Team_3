package model;

import view.Show;
import controller.CSV;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Vehicles {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public void plateone(String plate, Owner owner, int exportType) throws FileNotFoundException,ParseException {
        LocalDate localdate1 = LocalDate.now();
        ArrayList<Owner> owner1 = new ArrayList<>();

        if(exportType==1){
            CSV csv = new CSV();
            owner1.add(owner);
            csv.exportfile(owner1);
        }else {
            //typwsh status pinakidas
            System.out.println("---Vehicle Insurance Status---");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date newdate = sdf1.parse(owner.getDate());
            java.sql.Date sqlnewDate = new java.sql.Date(newdate.getTime());
            LocalDate localdate2 = sqlnewDate.toLocalDate();
            if (localdate1.isAfter(localdate2)) {
                System.out.println("Plate:       " + plate);
                System.out.println("Expire Date: " + owner.getDate());
                System.out.println("Status:      " + ANSI_RED + "Expired" + ANSI_RESET);
            }
            if (localdate1.isBefore(localdate2)) {
                System.out.println("Plate:       " + plate);
                System.out.println("Expire Date: " + owner.getDate());
                System.out.println("Status:      "+ ANSI_GREEN + "Not Expired" + ANSI_RESET);
            }
        }
    }

    public ArrayList<Owner> plates(ArrayList<Owner> ownerList, int ex) throws ParseException {
        ArrayList <Owner> forecoming = new ArrayList<Owner>();
        LocalDate localdate1 = LocalDate.now();
        LocalDate ex_date = localdate1.plusDays(ex);
        for (int i = 0; i < ownerList.size(); i++) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date newdate = sdf1.parse(ownerList.get(i).getDate());
            java.sql.Date sqlnewDate = new java.sql.Date(newdate.getTime());
            LocalDate localdate2 = sqlnewDate.toLocalDate();
            if (localdate2.isBefore(ex_date) && localdate2.isAfter(localdate1)) {
                Owner owner = new Owner(ownerList.get(i).getPlates(), ownerList.get(i).getDate());
                forecoming.add(owner);
            }
        }
        return forecoming;
    }

    public ArrayList<Owner> sorting(ArrayList<Owner> csvOwner ){     /* Sorting the plates using the bubblesort method*/
        Owner temp = new Owner();
        for (int i = 0; i < csvOwner.size(); i++) {
            for (int j = 1; j < (csvOwner.size() - i); j++) {
                if (csvOwner.get(j-1).getPlates().compareTo(csvOwner.get(j).getPlates()) > 0 ){
                    temp = csvOwner.get(j - 1);
                    csvOwner.set(j - 1, csvOwner.get(j));
                    csvOwner.set(j, temp);
                }
            }
        }
        return csvOwner;
    }

    public void fine(int fine, ArrayList <Owner> fineList) throws ParseException {
        int sum = 0, expired=0;
        LocalDate localdate1 = LocalDate.now();
        for (int i = 0; i < fineList.size(); i++) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date newdate = sdf1.parse(fineList.get(i).getDate());
            java.sql.Date sqlnewDate = new java.sql.Date(newdate.getTime());
            LocalDate localdate2 = sqlnewDate.toLocalDate();
            if (localdate1.isAfter(localdate2)) {
                System.out.println(fineList.get(i).getPlates()+ " " + fineList.get(i).getDate() + ANSI_RED + "  EXPIRED" + ANSI_RESET );
                sum++;
                expired++;
            }
            if (localdate1.isBefore(localdate2))
                System.out.println( fineList.get(i).getPlates() + " " + fineList.get(i).getDate() + ANSI_GREEN + " NOT EXPIRED"+ ANSI_RESET );
        }
        if (expired==0)
            System.out.println("You have no uninsured vehicles");
        else if (expired==1) {
            System.out.println("The Total Fine Cost for your Vehicle is: " + fine);
        } else if (expired > 1) {
            sum = fine * sum;
            System.out.println("You have: " + expired + " Vehicles that have expired");
            System.out.println("The Total Fine Cost for your Vehicles is: " + ANSI_RED + sum + " EURO"+ ANSI_RESET );
        }
    }

    public void navigation(){
        System.out.println();
        System.out.println("Where do you want to go from here?  ");
        Show sh = new Show();
        try {
            sh.shows();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}