package servlets;

import database.tables.EditUsersTable;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import mainClasses.JSON_Converter;

/**
 *
 * @author anton
 */
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSON_Converter jc = new JSON_Converter();
        String user = jc.getJSONFromAjax(request.getReader());

        System.out.println(user);
        
        try{
            
            EditUsersTable eut = new EditUsersTable();
            eut.addUserFromJSON(user);
            
        }catch(SQLException | ClassNotFoundException e){
                e.printStackTrace();
        }

    }
 
}