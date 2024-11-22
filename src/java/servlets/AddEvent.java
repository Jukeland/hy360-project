package servlets;

import database.tables.EditEventsTable;
import database.tables.EditTicketsTable;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import org.json.JSONObject;

/**
 *
 * @author anton
 */
public class AddEvent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSON_Converter jc = new JSON_Converter();
        JSONObject event = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        JSONObject ticket = new JSONObject();
        
        try{
           
            int vip = event.getInt("vip");
            int price = event.getInt("price");
            int vip_price = price + 20;
            int capacity = event.getInt("capacity");
            
            EditEventsTable eet = new EditEventsTable();
            eet.addEventFromJSON(event.toString());
            
            EditTicketsTable ett = new EditTicketsTable();
            for(int i = 0; i < capacity; i++){
                
                if(i < vip){
                    ticket.put("event_id", Integer.toString(eet.databaseGetEventId(event.getString("name"))));
                    ticket.put("price", Integer.toString(vip_price));
                    ticket.put("available", Integer.toString(1));
                    ticket.put("type", "VIP");
                }else{
                    ticket.put("event_id", Integer.toString(eet.databaseGetEventId(event.getString("name"))));
                    ticket.put("price", Integer.toString(price));
                    ticket.put("available", Integer.toString(1));
                    ticket.put("type", "regular");
                }

                ett.addTicketFromJSON(ticket.toString());
                
            }
                
        } catch(SQLException | ClassNotFoundException e){
            
            System.err.println("AddEvent servlet says: error");
            
        }
        
    }
    
}
