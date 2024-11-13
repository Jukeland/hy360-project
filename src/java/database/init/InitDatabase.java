package database.init;

import static database.DB_Connection.getInitialConnection;
import database.tables.EditBookingsTable;
import database.tables.EditEventsTable;
import database.tables.EditTicketsTable;
import database.tables.EditUsersTable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        InitDatabase init = new InitDatabase();
        init.initDatabase();
        init.initTables();
        
    }

    public void dropDatabase() throws SQLException, ClassNotFoundException {
        
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        String sql = "DROP DATABASE hy360_2024";
        stmt.executeUpdate(sql);
        System.out.println("Database dropped successfully...");
        
    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE hy360_2024");
        stmt.close();
        conn.close();
        
        initTables();
        
    }

    public void initTables() throws SQLException, ClassNotFoundException {
        
        EditUsersTable eut = new EditUsersTable();
        eut.createUserTable(); 
                
        EditEventsTable eet = new EditEventsTable();
        eet.createEventTable();
        
        EditBookingsTable ebt = new EditBookingsTable();
        ebt.createBookingTable();
       
        EditTicketsTable ett = new EditTicketsTable();
        ett.createTicketTable();

    }

}
