package mainClasses;

/**
 *
 * @author anton
 */
public class Event {
    
    int event_id, capacity;
    String name, date, time, type;
    
    public void setEvent_id(int event_id){
        this.event_id = event_id;
    }
    
    public int getEvent_id(){
        return this.event_id;
    }
    
    public void setTime(String time){
        this.time = time;
    }
    
    public String getTime(){
        return this.time;
    }
    
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    
    public int getCapacity(){
        return this.capacity;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setDate(String date){
        this.date = date;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getType(){
        return this.type;
    }
    
}
