package servlets;

import database.tables.EditEventsTable;
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
        
        try{
           
                EditEventsTable eet = new EditEventsTable();
                eet.addEventFromJSON(event.toString());
                
        } catch(SQLException | ClassNotFoundException e){
            
            System.err.println("AddEvent servlet says: error");
            
        }
        
    }
    
}
