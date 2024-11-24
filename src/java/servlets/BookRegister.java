package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import mainClasses.Event;
import mainClasses.User;
import org.json.JSONObject;
import database.tables.EditTicketsTable;
import database.tables.EditBookingsTable;
import database.tables.EditUsersTable;
import database.tables.EditEventsTable;
import java.util.ArrayList;
import mainClasses.Ticket;
import java.sql.SQLException;
import org.json.JSONException;

/**
 *
 * @author anton
 */
public class BookRegister extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        JSON_Converter jc = new JSON_Converter();
        JSONObject data = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        
        try{
            
            EditTicketsTable ett = new EditTicketsTable();
            EditBookingsTable ebt = new EditBookingsTable();
            EditUsersTable eut = new EditUsersTable();
            EditEventsTable eet = new EditEventsTable();
            
            int user_id = eut.databaseGetUserId(data.getString("username"));
            User user = eut.databaseToUser(user_id);
            data.put("user_id", user_id);
            
            Event ev = eet.databaseToEvent(data.getInt("event_id"));
            data.put("date", ev.getDate());
            
            /* insert booking into table */
            ebt.addBookingFromJSON(data.toString());
            
            /* subtract the booking price from the user's current balance */
            eut.setBalance(user_id, user.getBalance() - data.getInt("price"));
            
            /* update tickets to not available */
            ArrayList<Ticket> tickets;
            ArrayList<Integer> ticket_ids = new ArrayList<>();
            int min, max;
           
            for(int j = 0; j < data.getInt("vip_num"); j++){
                
                /* get all VIP tickets for this event */
                ticket_ids.clear();
                tickets = ett.databaseGetTicketsByType(data.getInt("event_id"), "VIP", 1);
                
                /* find min and max of ticket_id for this event */ 
                for(int i = 0; i < tickets.size(); i++){
                    ticket_ids.add(tickets.get(i).getTicket_id());
                }
                ticket_ids.sort(null);
                min = ticket_ids.get(0);
                max = ticket_ids.get(ticket_ids.size() - 1);

                /* pick a random ticket from this event that is not already unavailable */
                int random_ticket = min + (int)(Math.random() * ((max - min) + 1));
                while(!ticket_ids.contains(random_ticket)){
                    random_ticket = min + (int)(Math.random() * ((max - min) + 1));
                }
                
                /* set random tickets for this event to unavailable */
                ett.updateTicket(random_ticket, 0);
                
            }
            
            for(int j = 0; j < data.getInt("reg_num"); j++){
                
                /* get all regular tickets for this event */
                ticket_ids.clear();
                tickets = ett.databaseGetTicketsByType(data.getInt("event_id"), "regular", 1);
                
                /* find min and max of ticket_id for this event */ 
                for(int i = 0; i < tickets.size(); i++){
                    ticket_ids.add(tickets.get(i).getTicket_id());
                }
                ticket_ids.sort(null);
                min = ticket_ids.get(0);
                max = ticket_ids.get(ticket_ids.size() - 1);

                /* pick a random ticket from this event that is not already unavailable */
                int random_ticket = min + (int)(Math.random() * ((max - min) + 1));
                while(!ticket_ids.contains(random_ticket)){
                    random_ticket = min + (int)(Math.random() * ((max - min) + 1));
                }
                
                /* set random ticket for this event to unavailable */
                ett.updateTicket(random_ticket, 0);
                
            }
            
        }catch(ClassNotFoundException | SQLException | JSONException ex){
            
            System.err.println("BookRegister Servlet says: Got an exception!");
            System.err.println(ex.getMessage());
            
        }
        
        
    }

}
