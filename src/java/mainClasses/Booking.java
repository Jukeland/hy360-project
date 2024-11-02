package mainClasses;

/**
 *
 * @author anton
 */
public class Booking {
    
    int booking_id, user_id, event_id, ticket_num, price;
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
    
    public void setTicket_num(int ticket_num){
        this.ticket_num = ticket_num;
    }
    
    public int getTicket_num(){
        return this.ticket_num;
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
