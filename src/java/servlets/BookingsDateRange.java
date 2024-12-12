package servlets;

import database.tables.EditBookingsTable;
import database.tables.EditEventsTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Booking;
import mainClasses.Event;
import mainClasses.JSON_Converter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author anton
 */
public class BookingsDateRange extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        JSON_Converter jc = new JSON_Converter();
        JSONObject data = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        
        try{
            
            EditBookingsTable ebt = new EditBookingsTable();
            EditEventsTable eet = new EditEventsTable();
            ArrayList<Booking> bookings = ebt.databaseBookingsDateRange(data.getString("from_date"), data.getString("to_date"));
            Event e;
            JSONArray list = new JSONArray();
            
            for(Booking b : bookings){
                
                e = eet.databaseToEvent(b.getEvent_id());
                int ticket_num = b.getReg_num() + b.getVip_num();
                list.put("\"name\":\"" + e.getName() + "\", \"date\":\"" + e.getDate() + "\", \"time\":\"" + e.getTime() + "\", \"ticket_num\":\"" + ticket_num + "\", \"price\":\"" + b.getPrice() + "\", \"booking_id\":\"" + b.getBooking_id() + "\"");
                
            }
            
            System.out.println("\n=== BookingsDateRange Servlet says: list = " + list + " ===\n");
            PrintWriter out = response.getWriter();
            out.print(list);
            
        }catch(Exception ex){
            
            System.err.println("BookingsDateRange Servlet says: Got an exception!");
            System.err.println(ex.getMessage());
            
        }
        
    }

}
