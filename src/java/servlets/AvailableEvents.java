package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import database.tables.EditEventsTable;
import java.sql.SQLException;
import mainClasses.Event;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author anton
 */
public class AvailableEvents extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
        try{
            
            EditEventsTable eet = new EditEventsTable();
            ArrayList<Event> avail_events = eet.getAvailableEvents();
            JSONArray list = new JSONArray();
            
            for(Event e: avail_events){
                list.put("\"name\":\"" + e.getName() + "\", \"date\":\"" + e.getDate() + "\", \"time\":\"" + e.getTime() + "\", \"type\":\"" + e.getType() + "\", \"capacity\":\"" + e.getCapacity() + "\"");
            }
            
            System.out.println("\n=== AvailableEvents Servlet says: list = " + list + " ===\n");
            
            PrintWriter out = response.getWriter();
            out.print(list);
            
        }catch(IOException | ClassNotFoundException | SQLException | JSONException ex){
            
            System.err.println("AvailableEvents Servlet says: Got an exception! ");
            System.err.println(ex.getMessage());
            
        }
        
    }
    
    public ArrayList<Event> remove_duplicates(ArrayList<Event> events){
        
        ArrayList<Event> newList = new ArrayList<>();
        for(Event e : events){
            if(!newList.contains(e))
                newList.add(e);
        }
        
        return newList;
        
    }

}
