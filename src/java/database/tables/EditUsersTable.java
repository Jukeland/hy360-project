package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import mainClasses.User;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author anton
 */
public class EditUsersTable {

    /* function to convert a json string to a user object */
    public User jsonToUser(String json) {
        
        Gson gson = new Gson();
        User u = gson.fromJson(json, User.class);
        return u;
        
    }

    /* function to convert a user object to a json string */
    public String userToJSON(User u) {
        
        Gson gson = new Gson();
        String json = gson.toJson(u, User.class);
        return json;
        
    }

    /* function to add a user to the database from a json string */
    public void addUserFromJSON(String json) throws SQLException, ClassNotFoundException {
        
        User u = jsonToUser(json);
        createNewUser(u);
        
    }
    
    public boolean userExists(String username, String password) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        
        try{
            
            rs = stmt.executeQuery("select exists(select * from users where username = '" + username + "' and password ='" + password + "')");
            boolean result = false;
            if(rs.next()){
                result = rs.getInt(1) == 1;
            }
            stmt.close();
            con.close();
            
            return result;
            
        }catch(SQLException e){
            
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            
        }
        
        return false;
        
    }

    /* function to create a new user in the database from a user object u */
    public void createNewUser(User u) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        try {
            
            String insertQuery = "INSERT INTO "
                    + " users (username, password, email, first_name, last_name, balance, card_number) "
                    + " VALUES ("
                    + "'" + u.getUsername() + "',"
                    + "'" + u.getPassword() + "',"
                    + "'" + u.getEmail() + "',"
                    + "'" + u.getFirst_name() + "', "
                    + "'" + u.getLast_name() + "', "         
                    + "'" + u.getBalance() + "', "
                    + "'" + u.getCard_number() + "'"
                    + ")";
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The user was successfully added to the database.");
            stmt.close();

        } catch (SQLException ex) {
            
            System.err.println("Got an exception! ");
            System.err.println(ex.getMessage());
            
        }
        
    }
    
    public void updateUser(JSONObject jo) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        try{
            
            String update = "UPDATE users SET "
                + "card_number='" + jo.getString("card_number") + "',"
                + "balance='1000'"
                + "WHERE " 
                + "user_id ='" + databaseGetUserId(jo.getString("username")) + "'";
            stmt.executeUpdate(update);
            
        }catch(SQLException | JSONException e){
            
            System.err.println("updateUser says: Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
        stmt.close();
        con.close();
        
    }
    
    public void updateUserBalance(JSONObject jo) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        try{
            
            String update = "UPDATE users SET "
                + "balance='" + jo.getInt("balance") + "'"
                + "WHERE " 
                + "user_id ='" + databaseGetUserId(jo.getString("username")) + "'";
            stmt.executeUpdate(update);
            
        }catch(SQLException | JSONException e){
            
            System.err.println("updateUser says: Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
        stmt.close();
        con.close();
        
    }

    /* function to get a user with user_id = id from the database */
    public User databaseToUser(int id) throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try {
            
            rs = stmt.executeQuery("SELECT * FROM users WHERE user_id = '" + id + "'");
            
            if (rs.next()) {
                
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                User user = gson.fromJson(json, User.class);
                
                return user;
                
            }
            
        } catch (JsonSyntaxException | SQLException e) {
            
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
        return null;
        
    }
    
    public String databaseUserToJSON(String username, String password) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        
        try {
            
            rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);

            return json;
            
        } catch (SQLException e) {
            
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
        return null;
        
    }

    /* function to create the users table */
    public void createUserTable() throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE users "
                + "(user_id INTEGER not NULL AUTO_INCREMENT, "
                + " username VARCHAR(50) not NULL, "
                + " password VARCHAR(50) not NULL, "
                + " email VARCHAR(50) not NULL, "
                + " first_name VARCHAR(50) not NULL, "
                + " last_name VARCHAR(50) not NULL, "
                + " balance INTEGER not NULL, "
                + " card_number VARCHAR(16) not NULL, "
                + " PRIMARY KEY (user_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
        
    }

    /* function to get all users from the database */
    public ArrayList<User> databaseToUsers() throws SQLException, ClassNotFoundException {
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<User> users = new ArrayList<>();
        ResultSet rs;

        try {
            
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                User user = gson.fromJson(json, User.class);
                users.add(user);
            }
            stmt.close();
            con.close();

            return users;

        } catch (JsonSyntaxException | SQLException e) {
            
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
        return null;
        
    }
    
    public void deleteUser(int id) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        try{
            
            stmt.executeUpdate("DELETE FROM bookings WHERE user_id = " + id);
            stmt.executeUpdate("DELETE FROM users WHERE user_id = " + id);
            stmt.close();
            con.close();
            
        }catch(SQLException e){
            
            System.err.println("del user says: Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
    }
    
    public int databaseGetUserId(String username) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        
        try{
           
            rs = stmt.executeQuery("SELECT user_id FROM users WHERE username = '" + username + "'");
            int result = -1;
            if(rs.next())
                result = rs.getInt(1);
            stmt.close();
            con.close();

            return result;
            
        }catch(SQLException e){
            
            System.err.println("EditUsersTable getId says: Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
        return -1;
        
    }
    
    public String databaseGetUsername(int id) throws SQLException, ClassNotFoundException{
        
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        
        try{
            
            rs = stmt.executeQuery("SELECT username FROM users WHERE user_id = '" + id + "'");
            String result = "";
            if(rs.next())
                result = rs.getString(1);
            stmt.close();
            con.close();
            
            return result;
            
        }catch(SQLException e){
            
            System.err.println("EditUsersTable getUsername says: Got an exception! ");
            System.err.println(e.getMessage());
            
        }
        
        return "";
        
    }
    
}
