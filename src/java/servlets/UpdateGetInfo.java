package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.tables.EditUsersTable;
import mainClasses.JSON_Converter;
import org.json.JSONObject;
import java.sql.SQLException;

/**
 *
 * @author anton
 */
public class UpdateGetInfo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        JSON_Converter jc = new JSON_Converter();
        JSONObject user = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        
        try{
            
            String res;
            EditUsersTable eut = new EditUsersTable();
            res = eut.databaseUserToJSON(user.getString("username"), user.getString("password"));
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(res);
            
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("UpdateGetInfo servlet error");
        }
        
    }
    
}