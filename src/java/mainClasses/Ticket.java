package mainClasses;

/**
 *
 * @author anton
 */
public class Ticket {
    
    int ticket_id, price, available, event_id; 
    String type;
    
    public void setEvent_id(int event_id){
        this.event_id = event_id;
    }
    
    public int getEvent_id(){
        return this.event_id;
    }
    
    public void setTicket_id(int ticket_id){
        this.ticket_id = ticket_id;
    }
    
    public int getTicket_id(){
        return this.ticket_id;
    }
    
    public void setPrice(int price){
        this.price = price;
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public void setAvailable(int available){
        this.available = available;
    }
    
    public int getAvailable(){
        return this.available;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getType(){
        return this.type;
    }
    
}
