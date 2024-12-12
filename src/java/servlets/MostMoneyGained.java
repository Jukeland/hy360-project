package servlets;

import database.tables.EditEventsTable;
import database.tables.EditBookingsTable;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Event;
import mainClasses.Booking;
import mainClasses.JSON_Converter;
import org.json.JSONObject;
import java.util.ArrayList;
import org.json.JSONArray;

/**
 *
 * @author anton
 */
public class MostMoneyGained extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        JSON_Converter jc = new JSON_Converter();
        JSONObject data = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        
        try{
            
            EditEventsTable eet = new EditEventsTable();
            ArrayList<Event> events = eet.databaseEventsInDateRange(data.getString("from_date"), data.getString("to_date"));
            
            EditBookingsTable ebt = new EditBookingsTable();
            
            int money_gained;
            int max = 0;
            int max_id = -1;
            
            for(Event ev : events){
                
                money_gained = 0;
                ArrayList<Booking> bookings = ebt.databaseGetEventBookings(ev.getEvent_id());
                
                for(Booking b : bookings){
                    money_gained += b.getPrice();
                }
                
                if(max < money_gained){
                    max = money_gained;
                    max_id = ev.getEvent_id();
                }
                
            }
            
            Event e = eet.databaseToEvent(max_id);
            JSONArray list = new JSONArray();
            
            list.put("\"name\":\"" + e.getName() + "\", \"date\":\"" + e.getDate() + "\", \"time\":\"" + e.getTime() + "\", \"type\":\"" + e.getType() + "\", \"capacity\":\"" + e.getCapacity()+ "\", \"money_gained\":\"" + max + "\"");
            
            System.out.println("\n=== MostMoneyGained Servlet says: list = " + list + " ===\n");
            PrintWriter out = response.getWriter();
            out.print(list);
            
        }catch(Exception ex){
            
            System.err.println("MostMoneyGained Servlet says: Got an exception!");
            System.err.println(ex.getMessage());
            
        }
        
    }

}
