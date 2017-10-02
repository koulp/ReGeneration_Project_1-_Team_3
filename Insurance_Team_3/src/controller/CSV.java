package controller;

import model.Owner;

import java.io.*;
import java.util.ArrayList;

public class CSV {

    public void exportfile(ArrayList <Owner> ownerList)throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<ownerList.size();i++) {
            sb.append(ownerList.get(i).getPlates());
            sb.append(";");
            sb.append(ownerList.get(i).getDate());//kai edw
            sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("Success!");;
    }

    public Owner importfileF1(String plate){               //gia diavasma dedomenwn apo to arxeio .csv gia to f1 requirement

        String csvFile = "VehiclesData.csv";
        String csvSplitBy =";";
        BufferedReader br = null;
        String line = "";
        int exist =0;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while (!(line = br.readLine()).equals(";;")) {
                String[] row = line.split(csvSplitBy);
                if ((row[0].compareTo(plate)) == 0) {
                    System.out.println("Data have been Imported from CSV File");
                    Owner owner = new Owner();
                    owner.setPlate(row[0]);
                    owner.setAfm(row[1]);
                    owner.setDate(row[2]);
                    exist =1;
                    return owner;
                    //break;
                }
            }
            if(exist==0){
                System.out.println("This plate does not exist in CSV File");
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } return null;
    }

    public ArrayList<Owner> importFileF2 (){               //gia diavasma dedomenwn apo to arxeio .csv gia to f2 requirement

        String csvFile = "VehiclesData.csv";
        String csvSplitBy =";";
        BufferedReader br = null;
        String line = "";
        int exist =0;
        ArrayList<Owner> forecoming = new ArrayList<Owner>();
        System.out.println("Data have been Imported from CSV File");

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) !=null) {
                String[] row = line.split(csvSplitBy);
                Owner owner = new Owner();
                owner.setPlate(row[0]);
                owner.setDate(row[2]);

                forecoming.add(owner);
                exist = 1;
            }
            if(exist==0){
                System.out.println("This plate does not exist in CSV File");
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } return forecoming;
    }

    public ArrayList<Owner> importfileF3(){               //gia diavasma dedomenwn apo to arxeio .csv gia to f3 requirement

        String csvFile = "VehiclesData.csv";
        String csvSplitBy =";";
        BufferedReader br = null;
        String line = "";
        int exist =0;
        ArrayList <Owner> ownerListF3 = new ArrayList<>();
        System.out.println("Data have been Imported from CSV File");
        try {
            br = new BufferedReader(new FileReader(csvFile));
            //while (!(line = br.readLine()).equals(";;")) {
            while ((line = br.readLine())!=null) {
                String[] row = line.split(csvSplitBy);
                Owner owner = new Owner();
                owner.setPlate(row[0]);
                owner.setAfm(row[1]);
                owner.setDate(row[2]);

                ownerListF3.add(owner);
                exist =1;
            }
            if(exist==0){
                System.out.println("This CSV File has nothing");
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } return ownerListF3;
    }
}
