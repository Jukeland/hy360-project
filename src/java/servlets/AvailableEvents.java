package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import org.json.JSONObject;
import database.tables.EditEventsTable;
import database.tables.EditBookingsTable;
import database.tables.EditUsersTable;
import database.tables.EditTicketsTable;
import mainClasses.Event;
import mainClasses.Booking;
import mainClasses.User;
import mainClasses.Ticket;

/**
 *
 * @author anton
 */
public class AvailableEvents extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
        try{
            
            JSON_Converter jc = new JSON_Converter();
            JSONObject user = new JSONObject(jc.getJSONFromAjax(request.getReader()));
            
            
            
        }catch(Exception e){
            
        }
        
    }

}
