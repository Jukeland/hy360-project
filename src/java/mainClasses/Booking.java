package mainClasses;

/**
 *
 * @author anton
 */
public class Booking {
    
    int booking_id, user_id, event_id, vip_num, reg_num, price;
    String date;
    
    public void setBooking_id(int booking_id){
        this.booking_id = booking_id;
    }
    
    public int getBooking_id(){
        return this.booking_id;
    }
    
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    
    public int getUser_id(){
        return this.user_id;
    }
    
    public void setEvent_id(int event_id){
        this.event_id = event_id;
    }
    
    public int getEvent_id(){
        return this.event_id;
    }
    
    public void setVip_num(int ticket_num){
        this.vip_num = ticket_num;
    }
    
    public int getVip_num(){
        return this.vip_num;
    }
    
    public void setReg_num(int ticket_num){
        this.reg_num = ticket_num;
    }
    
    public int getReg_num(){
        return this.reg_num;
    }
    
    public void setDate(String date){
        this.date = date;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public void setPrice(int price){
        this.price = price;
    }
    
    public int getPrice(){
        return this.price;
    }
    
}
