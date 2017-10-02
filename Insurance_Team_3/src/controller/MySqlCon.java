package controller;

import java.sql.*;
import java.text.ParseException;

public class MySqlCon {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://192.168.33.10/Insurance?autoReconnect=true&useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "admin123";

    private Connection conn;
    private Statement stmt;

    public MySqlCon(){}

    public Statement getStmt() {
        return stmt;
    }
    public void setStmt(Statement stmt){
        this.stmt=stmt;
    }

    public Connection getConn(){
        return conn;
    }
    public void setConn(Connection conn){
        this.conn=conn;
    }
    //open close mysql Db
    public void mysqlCon() throws ClassNotFoundException, SQLException, ParseException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            setConn(DriverManager.getConnection(DB_URL, USER, PASS));
            System.out.println("Creating statement...");
            setStmt(conn.createStatement());
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    public void closedb(Connection conn,Statement stmt,ResultSet rs) throws SQLException {
        //STEP 6: Clean-up environment
        rs.close();
        conn.close();
        conn.close();

        //Close resources
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException se2) {
        }// nothing we can do
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }//end
    }
}
