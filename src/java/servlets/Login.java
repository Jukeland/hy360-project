package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.JSON_Converter;
import database.tables.EditUsersTable;
import org.json.JSONObject;

/**
 *
 * @author anton
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        JSON_Converter jc = new JSON_Converter();
        JSONObject jsonObj = new JSONObject(jc.getJSONFromAjax(request.getReader()));
        
        EditUsersTable eut = new EditUsersTable();
        
        JSONObject jo = new JSONObject();
        
        jo.put("username", jsonObj.getString("username"));
        try{
            if(eut.userExists(jsonObj.getString("username"), jsonObj.getString("password"))){
                jo.put("userType", "regular");
            }else if(jsonObj.getString("username").equals("admin") && jsonObj.getString("password").equals("admin12*")){
                jo.put("userType", "admin");
            }else{
                response.sendError(401, "Invalid username or password");
                return;
            }
            
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedIn", jo.getString("username"));
            session.setAttribute("userType", jo.getString("userType"));
            response.setStatus(200);
            response.setContentType("application/json");
            jo.put("message", "Successful login");
            PrintWriter out = response.getWriter();
            out.println(jo);
            
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
