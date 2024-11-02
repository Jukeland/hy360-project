package mainClasses;

/**
 *
 * @author anton
 */
public class User {
    
    int user_id, card_number;
    String first_name, last_name, email;
    
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    
    public int getUser_id(){
        return this.user_id;
    }
    
    public void setCard_number(int card_number){
        this.card_number = card_number;
    }
    
    public int getCard_number(){
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
