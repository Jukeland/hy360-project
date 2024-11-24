package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import org.json.JSONObject;
import database.tables.EditBookingsTable;
import database.tables.EditEventsTable;
import database.tables.EditUsersTable;
import java.sql.SQLException;
import mainClasses.Booking;
import mainClasses.Event;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author anton
 */
public class GetBookings extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            
            JSON_Converter jc = new JSON_Converter();
            JSONObject data = new JSONObject(jc.getJSONFromAjax(request.getReader()));
            
            EditBookingsTable ebt = new EditBookingsTable();
            EditEventsTable eet = new EditEventsTable();
            EditUsersTable eut = new EditUsersTable();
            
            ArrayList<Booking> bookings;
            Event e;
            JSONArray list = new JSONArray();
            
            int user_id = eut.databaseGetUserId(data.getString("username"));
            
            bookings = ebt.databaseGetBookingFromUserId(user_id);
            
            for(Booking b : bookings){
                
                e = eet.databaseToEvent(b.getEvent_id());
                int ticket_num = b.getReg_num() + b.getVip_num();
                list.put("\"name\":\"" + e.getName() + "\", \"date\":\"" + e.getDate() + "\", \"time\":\"" + e.getTime() + "\", \"ticket_num\":\"" + ticket_num + "\", \"price\":\"" + b.getPrice() + "\", \"booking_id\":\"" + b.getBooking_id() + "\"");
                
            }
            
            System.out.println("\n=== GetBookings Servlet says: list = " + list + " ===\n");
            PrintWriter out = response.getWriter();
            out.print(list);
            
            
        }catch(IOException | ClassNotFoundException | SQLException | JSONException ex){
            
            System.err.println("GetBookings Servlet says: Got an exception!");
            System.err.println(ex.getMessage());
            
        }
        
    }

}
