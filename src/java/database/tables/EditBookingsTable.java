package database.tables;

import com.google.gson.Gson;
import mainClasses.Booking;
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
public class EditBookingsTable {

    /* function to add a booking to the database from a json string */
    public void addBookingFromJSON(String json) throws SQLException, ClassNotFoundException {
        
        Booking r = jsonToBooking(json);
        createNewBooking(r);
        
    }
    
    /* function to get all the bookings ascossiated with the user who has the user_id = uid */
    public ArrayList<Booking> databaseGetBookingFromUserId(int uid) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Booking> bookings = new ArrayList<>();
        ResultSet rs;
        
        try {

            rs = stmt.executeQuery("SELECT * FROM bookings WHERE user_id= '" + uid + "'");
            
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Booking book = gson.fromJson(json, Booking.class);
                bookings.add(book);
            }
            
            con.close();
            stmt.close();
            
            return bookings;
            
        } catch (Exception e) {
            System.err.println("EditBookingsTable -> getBookingFromUserId: Got an exception! ");
            System.err.println(e.getMessage());
        }
        
        return null;
        
    }

    /* function to get a booking with booking_id = id from the database */
    public Booking databaseToBooking(int id) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        
        try {
            
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE booking_id= '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Booking bt = gson.fromJson(json, Booking.class);
            
            return bt;
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        
        return null;
        
    }

    /* function to convert a json to a booking */
    public Booking jsonToBooking(String json) {
        
        Gson gson = new Gson();
        Booking r = gson.fromJson(json, Booking.class);
        
        return r;
        
    }

    /* function to conver a booking to a json */
    public String bookingToJSON(Booking r) {
        
        Gson gson = new Gson();
        String json = gson.toJson(r, Booking.class);
        
        return json;
        
    }

    /* function to update the date field of the booking */
    public void updateBooking(int bookingID,  String date) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE bookings SET date='"+date+"' WHERE booking_id= '"+bookingID+"'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
        
    }

    /* function to create the bookings table */
    public void createBookingTable() throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE bookings "
                + "(booking_id INTEGER not NULL AUTO_INCREMENT, "
                + " user_id INTEGER not NULL, "
                + " event_id INTEGER not NULL, "
                + " ticket_num INTEGER not NULL, "
                + " date DATE not NULL, "
                + " price INTEGER not NULL, "
                + "FOREIGN KEY (user_id) REFERENCES users(user_id), "
                + "FOREIGN KEY (event_id) REFERENCES events(event_id), "
                + " PRIMARY KEY (booking_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /* function to create a new booking in the database from a booking object b */
    public void createNewBooking(Booking b) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        try {
            
            String insertQuery = "INSERT INTO "
                    + " bookings (user_id,event_id,ticket_num,date,price)"
                    + " VALUES ("
                    + "'" + b.getUser_id() + "',"
                    + "'" + b.getEvent_id()+ "',"
                    + "'" + b.getTicket_num() + "',"
                    + "'" + b.getDate() + "',"
                    + "'" + b.getPrice() + "'"
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The booking was successfully added in the database.");

            stmt.close();

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        
    }
    
    /* function to get all bookings from the database */
    public ArrayList<Booking> databaseToBookings() throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Booking> bookings = new ArrayList<>();
        ResultSet rs;
        
        try {
            
            rs = stmt.executeQuery("SELECT * FROM bookings");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Booking book = gson.fromJson(json, Booking.class);
                bookings.add(book);
            }
            stmt.close();
            con.close();
            
            return bookings;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        
        return null;
        
    }
    
}
