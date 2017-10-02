package controller;

import model.Owner;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class MySqlRequest {

    private ResultSet rs = null;
    private String sql;

    //Querys to Database
    public Owner fetchplateone(String plate) throws SQLException, ParseException, ClassNotFoundException {
        MySqlCon mysqlcon = new MySqlCon();
        mysqlcon.mysqlCon();
        Owner owner = new Owner();
        sql = "SELECT exp_date FROM Vehicle WHERE plates='" + plate + "'";
        rs = mysqlcon.getStmt().executeQuery(sql);
        if (rs.first()) {

            owner.setDate(rs.getString("exp_date").trim()); //trimming cause attribute date on database has spaces
            //ex_date = ex_date.trim();
            //ex_date = ex_date.substring(0, ex_date.length() -1);
        } else {
            System.out.println("this plate does not exist in our database ");
        }
        mysqlcon.closedb(mysqlcon.getConn(), mysqlcon.getStmt(),rs);
        return owner;
    }

    public ArrayList<Owner> fetchplates2() throws SQLException, ParseException, FileNotFoundException, ClassNotFoundException {
        int rowcount = 0, i = 0;
        ArrayList<Owner> OwnerList = new ArrayList<Owner>();
        MySqlCon mysqlcon = new MySqlCon();
        mysqlcon.mysqlCon();
        sql = "SELECT plates,exp_date FROM Vehicle";
        rs = mysqlcon.getStmt().executeQuery(sql);

        //STEP 5: Extract data from result set
        if (rs.last()) {
            rowcount = rs.getRow();
            rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
        }
        while (rs.next() || i < rowcount) {
            //Retrieve by column name
            Owner owner = new Owner(rs.getString("plates"), rs.getString("exp_date").trim());
            OwnerList.add(owner);
            i++;
        }

        mysqlcon.closedb(mysqlcon.getConn(), mysqlcon.getStmt(), rs);
        return OwnerList;
    }

    public ArrayList<Owner> fetchafm(String afm, int fine) throws SQLException, ParseException {
        int rowcount = 0, i = 0;
        ArrayList<Owner> fineList = new ArrayList<Owner>();
        MySqlCon mysqlcon = new MySqlCon();
        try {
            mysqlcon.mysqlCon();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Query data
        sql = "SELECT plates,exp_date FROM Vehicle WHERE afm='" + afm + "'";
        rs = mysqlcon.getStmt().executeQuery(sql);

        //STEP 5: Extract data from result set
        if (rs.last()) {
            rowcount = rs.getRow();
            rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
        }
        while (rs.next() || i < rowcount) {
            //Retrieve by column name
            Owner owner = new Owner(rs.getString("plates"), rs.getString("exp_date").trim());
            fineList.add(owner);
            i++;
        }

        mysqlcon.closedb(mysqlcon.getConn(), mysqlcon.getStmt(),rs);
        return fineList;
    }
}
