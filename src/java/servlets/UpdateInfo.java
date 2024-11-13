package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import org.json.JSONObject;
import database.tables.EditUsersTable;
import java.sql.SQLException;
import mainClasses.User;

/**
 *
 * @author anton
 */
public class UpdateInfo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        JSON_Converter jc = new JSON_Converter();
        JSONObject user = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        System.out.println("UpdateInfo Servlet says: user = " + user);
        try{
           
                EditUsersTable eut = new EditUsersTable();
                eut.updateUser(user);
                
        } catch(SQLException | ClassNotFoundException e){
            
            System.err.println("UpdateInfo Servlet error");
            
        }
        
    }

}
