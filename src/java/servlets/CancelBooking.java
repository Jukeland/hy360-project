package servlets;

import database.tables.EditBookingsTable;
import database.tables.EditEventsTable;
import database.tables.EditTicketsTable;
import database.tables.EditUsersTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Event;
import mainClasses.JSON_Converter;
import mainClasses.Ticket;
import mainClasses.User;
import mainClasses.Booking;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author anton
 */
public class CancelBooking extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSON_Converter jc = new JSON_Converter();
        JSONObject data = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        
        try{
            
            EditTicketsTable ett = new EditTicketsTable();
            EditBookingsTable ebt = new EditBookingsTable();
            EditUsersTable eut = new EditUsersTable();
            EditEventsTable eet = new EditEventsTable();
            
            Booking b = ebt.databaseToBooking(data.getInt("booking_id"));
            Event ev = eet.databaseToEvent(b.getEvent_id());
            
            int user_id = eut.databaseGetUserId(data.getString("username"));
            User user = eut.databaseToUser(user_id);
            
            /* add the booking price to the user's current balance */
            eut.setBalance(user_id, user.getBalance() + b.getPrice());
            
            /* update tickets to available */
            ArrayList<Ticket> tickets;
            ArrayList<Integer> ticket_ids = new ArrayList<>();
            int min, max;
           
            for(int j = 0; j < b.getVip_num(); j++){
                
                /* get all VIP tickets for this event */
                ticket_ids.clear();
                tickets = ett.databaseGetTicketsByType(ev.getEvent_id(), "VIP", 0);
                
                /* find min and max of ticket_id for this event */ 
                for(int i = 0; i < tickets.size(); i++){
                    ticket_ids.add(tickets.get(i).getTicket_id());
                }
                ticket_ids.sort(null);
                min = ticket_ids.get(0);
                max = ticket_ids.get(ticket_ids.size() - 1);

                /* pick a random ticket from this event that is not already available */
                int random_ticket = min + (int)(Math.random() * ((max - min) + 1));
                while(!ticket_ids.contains(random_ticket)){
                    random_ticket = min + (int)(Math.random() * ((max - min) + 1));
                }
                
                /* set random tickets for this event to available */
                ett.updateTicket(random_ticket, 1);
                
            }
            
            for(int j = 0; j < b.getReg_num(); j++){
                
                /* get all regular tickets for this event */
                ticket_ids.clear();
                tickets = ett.databaseGetTicketsByType(ev.getEvent_id(), "regular", 0);
                
                /* find min and max of ticket_id for this event */ 
                for(int i = 0; i < tickets.size(); i++){
                    ticket_ids.add(tickets.get(i).getTicket_id());
                }
                ticket_ids.sort(null);
                min = ticket_ids.get(0);
                max = ticket_ids.get(ticket_ids.size() - 1);

                /* pick a random ticket from this event that is not already available */
                int random_ticket = min + (int)(Math.random() * ((max - min) + 1));
                while(!ticket_ids.contains(random_ticket)){
                    random_ticket = min + (int)(Math.random() * ((max - min) + 1));
                }
                
                /* set random ticket for this event to available */
                ett.updateTicket(random_ticket, 1);
                
            }
            
            ebt.databaseDeleteBooking(b.getBooking_id());
            
        }catch(ClassNotFoundException | SQLException | JSONException ex){
            
            System.err.println("BookRegister Servlet says: Got an exception!");
            System.err.println(ex.getMessage());
            
        }
        
    }

}
