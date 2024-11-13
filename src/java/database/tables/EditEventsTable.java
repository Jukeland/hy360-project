package database.tables;

import com.google.gson.Gson;
import mainClasses.Event;
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
public class EditEventsTable {
    
    /* function to convert a json string to an event object */
    public Event jsonToEvent(String json) {
        
        Gson gson = new Gson();
        Event e = gson.fromJson(json, Event.class);
        
        return e;
        
    }
    
     /* function to convert an event object to a json string */
    public String eventToJSON(Event e) {
        
        Gson gson = new Gson();
        String json = gson.toJson(e, Event.class);
        
        return json;
        
    }
    
    /* function to add an event to the database from a json string */
    public void addEventFromJSON(String json) throws SQLException, ClassNotFoundException {
        
        Event e = jsonToEvent(json);
        createNewEvent(e);
        
    }
    
    /* function to create a new event in the database from an event object e */
    public void createNewEvent(Event e) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        try {
            
            String insertQuery = "INSERT INTO "
                    + " events (name,date,time,type,capacity)"
                    + " VALUES ("
                    + "'" + e.getName()+ "',"
                    + "'" + e.getDate()+ "',"
                    + "'" + e.getTime() + "',"
                    + "'" + e.getType() + "',"
                    + "'" + e.getCapacity() + "'"                    
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The booking was successfully added in the database.");

            stmt.close();

        } catch (Exception ex) {
            System.err.println("Got an exception! ");
            System.err.println(ex.getMessage());
        }
        
    }
    
    /* function to get an event with event_id = id from the database */
    public Event databaseToEvent(int id) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        
        try {
            
            rs = stmt.executeQuery("SELECT * FROM events WHERE event_id= '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Event ev = gson.fromJson(json, Event.class);
            
            return ev;
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        
        return null;
        
    }
    
    /* function to create the events table */
    public void createEventTable() throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE events "
                + "(event_id INTEGER not NULL AUTO_INCREMENT, "
                + " name VARCHAR(50) not NULL, "
                + " date DATE not NULL, "
                + " time VARCHAR(10) not NULL, "
                + " type VARCHAR(30) not NULL, "
                + " capacity INTEGER not NULL, "
                + " PRIMARY KEY (event_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }
    
    public void deleteEvent(int id) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        try{
            
            stmt.executeUpdate("DELETE FROM bookings WHERE event_id = " + id);
            stmt.executeUpdate("DELETE FROM events WHERE event_id = " + id);
            stmt.close();
            con.close();
            
        }catch(SQLException e){
            
            System.err.println("del event says: Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
    }
    
    /* function to get all events from the database */
    public ArrayList<Event> databaseToEvents() throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Event> events = new ArrayList<>();
        ResultSet rs;
        
        try {
            
            rs = stmt.executeQuery("SELECT * FROM events");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Event event = gson.fromJson(json, Event.class);
                events.add(event);
            }
            stmt.close();
            con.close();
            
            return events;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        
        return null;
        
    }
    
}
