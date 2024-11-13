package mainClasses;

/**
 *
 * @author anton
 */
public class User {
    
    int user_id, balance;
    String first_name, last_name, email, username, password, card_number;
    
    public void setBalance(int balance){
        this.balance = balance;
    }
    
    public int getBalance(){
        return this.balance;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    
    public int getUser_id(){
        return this.user_id;
    }
    
    public void setCard_number(String card_number){
        this.card_number = card_number;
    }
    
    public String getCard_number(){
        return this.card_number;
    }
    
    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }
    
    public String getFirst_name(){
        return this.first_name;
    }
    
    public void setLast_name(String last_name){
        this.last_name = last_name;
    }
    
    public String getLast_name(){
        return this.last_name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return this.email;
    }
    
}
