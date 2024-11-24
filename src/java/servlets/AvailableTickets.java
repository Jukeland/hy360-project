package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.tables.EditTicketsTable;
import database.tables.EditEventsTable;
import java.sql.SQLException;
import mainClasses.JSON_Converter;
import org.json.JSONObject;
import mainClasses.Ticket;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author anton
 */
public class AvailableTickets extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try{
        
            JSON_Converter jc = new JSON_Converter();
            JSONObject event = new JSONObject(jc.getJSONFromAjax(request.getReader()));
            JSONArray list = new JSONArray();
            
            EditTicketsTable ett = new EditTicketsTable();
            EditEventsTable eet = new EditEventsTable();
            int event_id = eet.databaseGetEventId(event.getString("name"));
            
            /* get available tickets for given event */
            ArrayList<Ticket> tickets = ett.databaseGetEventTickets(event_id);
            
            for(Ticket t: tickets){ 
                list.put("\"type\":\"" + t.getType() + "\", \"price\":\"" + t.getPrice() + "\"");
            }
            
            System.out.println("\n=== AvailableTickets Servlet says: list = " + list + " ===\n");
            
            PrintWriter out = response.getWriter();
            out.print(list);
            
        }catch(IOException | ClassNotFoundException | SQLException | JSONException ex){
            
            System.err.println("AvailableTickets Servlet says: Got an exception! ");
            System.err.println(ex.getMessage());
            
        }
        
    }

}
