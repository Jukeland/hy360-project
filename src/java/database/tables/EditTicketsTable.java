package database.tables;

import com.google.gson.Gson;
import mainClasses.Ticket;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author anton
 */
public class EditTicketsTable {

    /* function to convert a json string to a ticket object */
    public Ticket jsonToTicket(String json) {
        
        Gson gson = new Gson();
        Ticket t = gson.fromJson(json, Ticket.class);
        return t;
        
    }

    /* function to convert a ticket object to a json string */
    public String ticketToJSON(Ticket t) {
        
        Gson gson = new Gson();
        String json = gson.toJson(t, Ticket.class);
        return json;
        
    }

    /* function to add a ticket to the database from a json string */
    public void addTicketFromJSON(String json) throws SQLException, ClassNotFoundException {
        
        Ticket t = jsonToTicket(json);
        createNewTicket(t);
        
    }

    /* function to create a new ticket in the database from a ticket object t */
    public void createNewTicket(Ticket t) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        try {
            
            String insertQuery = "INSERT INTO "
                    + " tickets (price, available, type) "
                    + " VALUES ("
                    + "'" + t.getPrice() + "', "
                    + "'" + t.getAvailable() + "', "
                    + "'" + t.getType() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("# The ticket was successfully added to the database.");
            stmt.close();

        } catch (Exception ex) {
            System.err.println("Got an exception! ");
            System.err.println(ex.getMessage());
        }
        
    }

    /* function to get a ticket with ticket_id = id from the database */
    public Ticket databaseToTicket(int id) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try {
            
            rs = stmt.executeQuery("SELECT * FROM tickets WHERE ticket_id = '" + id + "'");
            
            if (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Ticket ticket = gson.fromJson(json, Ticket.class);
                
                return ticket;
            }
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return null;
        
    }

    /* function to create the tickets table */
    public void createTicketTable() throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE tickets "
                + "(ticket_id INTEGER not NULL AUTO_INCREMENT, "
                + " price INTEGER not NULL, "
                + " available INTEGER not NULL, "
                + " type VARCHAR(50) not NULL, "
                + " PRIMARY KEY (ticket_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
        
    }

    /* function to get all tickets from the database */
    public ArrayList<Ticket> databaseToTickets() throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Ticket> tickets = new ArrayList<>();
        ResultSet rs;

        try {
            
            rs = stmt.executeQuery("SELECT * FROM tickets");
            
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Ticket ticket = gson.fromJson(json, Ticket.class);
                tickets.add(ticket);
            }
            
            stmt.close();
            con.close();

            return tickets;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return null;
        
    }
    
}
