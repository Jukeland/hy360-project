package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.tables.EditUsersTable;
import database.tables.EditEventsTable;
import database.tables.EditBookingsTable;
import mainClasses.Booking;
import mainClasses.User;
import java.util.ArrayList;
                
import org.json.JSONObject;
import mainClasses.JSON_Converter;
import java.sql.SQLException;

/**
 *
 * @author anton
 */
@WebServlet(name = "AdminDelete", urlPatterns = {"/AdminDelete"})
public class AdminDelete extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        JSON_Converter jc = new JSON_Converter();
        JSONObject jo = new JSONObject(jc.getJSONFromAjax(request.getReader()));

        try{
            if(jo.getString("type").equals("user")){
                
                EditUsersTable eut = new EditUsersTable();
                System.out.println("adminDel user: id = " + jo.getInt("id"));
                eut.deleteUser(jo.getInt("id"));
                
            }else if(jo.getString("type").equals("event")){
                
                EditEventsTable eet = new EditEventsTable();
                EditUsersTable eut = new EditUsersTable();
                User user = eut.databaseToUser(jo.getInt("id"));
                EditBookingsTable ebt = new EditBookingsTable();
                ArrayList<Booking> bookings = ebt.databaseToBookings();
                
                System.out.println("\n\n==== admin del: user.getBalance = " + user.getBalance());
                
                for(Booking b : bookings){
                    if(b.getUser_id() == user.getUser_id()){
                        user.setBalance(user.getBalance() + b.getPrice() * b.getTicket_num());
                    }
                }
                
                JSONObject u = new JSONObject(eut.userToJSON(user));
                eut.updateUser(u);
                
                System.out.println("adminDel event: id = " + jo.getInt("id"));
                eet.deleteEvent(jo.getInt("id"));
                
            }
                
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("AdminDelete servlet says: error");
        }
    }

}
