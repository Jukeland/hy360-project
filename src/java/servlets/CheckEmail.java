package servlets;

import database.tables.EditUsersTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import mainClasses.User;
import org.json.JSONObject;

/**
 *
 * @author anton
 */
public class CheckEmail extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        JSON_Converter jc = new JSON_Converter();
        JSONObject data = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        
        EditUsersTable eut = new EditUsersTable();
        
        ArrayList<User> users;
        
        try{
            
            users = eut.databaseToUsers();
            
            for(User u : users){
                if(data.getString("email").equals(u.getEmail())){
                    response.setStatus(403);
                    return;
                }      
            }
            
            response.setStatus(200);
            
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        
    }

}
