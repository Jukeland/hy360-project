<%-- 
    Document   : admin_page
    Created on : Nov 5, 2024, 7:29:26 PM
    Author     : anton
--%>

<%@ page import="mainClasses.User" %>
<%@ page import="mainClasses.Ticket" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="database.tables.EditUsersTable" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="mainClasses.Event" %>
<%@ page import="database.tables.EditEventsTable" %>
<%@ page import="mainClasses.Booking" %>
<%@ page import="database.tables.EditBookingsTable" %>
<%@ page import="database.tables.EditTicketsTable" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>admin</title>
        <style>
            tr th {
                font-weight: bold
            }

            h3 {
                font-weight: bold;
                margin-top: 20px;
                margin-bottom: 40px;
            }

            table {
                margin-bottom: 150px;
                width: 100%;
            }
            
            #event_form_wrapper{
                max-width: fit-content;
                margin-left: auto;
                margin-right: auto;
            }
        </style>
    </head>
    
    <body>
        
        <%
            ArrayList<User> users;
            EditUsersTable eut = new EditUsersTable();
            int num_of_users = 0;
            
            ArrayList<Event> events;
            EditEventsTable eet = new EditEventsTable();
            int event_money_gained = 0;
            int event_vip_money_gained = 0;
            int event_reg_money_gained = 0;
            
            int total_vip_money_gained = 0;
            int total_reg_money_gained = 0;
            
            ArrayList<Booking> bookings;
            EditBookingsTable ebt = new EditBookingsTable();
            
            ArrayList<Ticket> tickets;
            EditTicketsTable ett = new EditTicketsTable();
            
            int count = 0;
            int max = 0;
            Event pop_ev = null;
            
            try{
            
                users = eut.databaseToUsers();
                events = eet.databaseToEvents();
                bookings = ebt.databaseToBookings();
                int num_events = events.size();
            
        %>
        
        <div>
            <h3>Users</h3>
            <table id="users_table">
                
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Action</th>
                    </tr>
                </thead>
                
                <tbody>
                    <%
                        for(User u : users){
                            num_of_users++;
                    %>
                    <tr>
                        <td><%=u.getUsername()%></td>
                        <td><%=u.getFirst_name()%></td>
                        <td><%=u.getLast_name()%></td>
                        <td>
                            <button type="button" onclick="admin_delete('<%=u.getUser_id()%>', 'user')">Delete</button>
                        </td>
                    </tr>
                </tbody>
                <%
                    }
                %>
                
            </table>
                        
            <h3>Events</h3>
            <table id="events_table">
                
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Type</th>
                        <th>Capacity</th>
                        <th>Money Gained</th>
                        <th>Action</th>
                    </tr>
                </thead>
                
                <tbody>
                    <%
                        for(Event ev : events){
                            count = 0;
                            event_money_gained = 0;
                            event_vip_money_gained = 0;
                            event_reg_money_gained = 0;
                            int ev_id = ev.getEvent_id();
                            for(Booking b : bookings){
                                if(ev_id == b.getEvent_id()){
                                    event_money_gained += b.getPrice();
                                    count++;
                                }
                            }

                            tickets = ett.databaseGetTicketsByType(ev_id, "VIP", 0);
                            for(Ticket t : tickets){
                                event_vip_money_gained += t.getPrice();
                                total_vip_money_gained += t.getPrice();
                            }
                            
                            tickets = ett.databaseGetTicketsByType(ev_id, "Regular", 0);
                            for(Ticket t : tickets){
                                event_reg_money_gained += t.getPrice();
                                total_reg_money_gained += t.getPrice();
                            }
                            
                            if(count > max){
                                max = count;
                                pop_ev = ev;
                            }
                    %>
                    <tr>
                        <td><%=ev.getName()%></td>
                        <td><%=ev.getDate()%></td>
                        <td><%=ev.getTime()%></td>
                        <td><%=ev.getType()%></td>
                        <td><%=ev.getCapacity()%></td>
                        <td>VIP: <%=event_vip_money_gained%>€, Regular: <%=event_reg_money_gained%>€, Total: <%=event_money_gained%>€</td>
                        <td>
                            <button type="button" onclick="admin_delete('<%=ev.getEvent_id()%>', 'event')">Delete</button>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
                
            </table>
                
            <div id="event_form_wrapper">
                <button id="add_event" onclick="admin_add_event()">Add Event</button>
            </div>
                
            <div id="popular_event_wrapper">
                <h3>Most Popular Event</h3>
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Type</th>
                        <th>Capacity</th>
                    </tr>
                    </thead>
                    
                    <tbody>
                        <tr>
                            <td><%=pop_ev.getName()%></td>
                            <td><%=pop_ev.getDate()%></td>
                            <td><%=pop_ev.getTime()%></td>
                            <td><%=pop_ev.getType()%></td>
                            <td><%=pop_ev.getCapacity()%></td>
                        </tr>
                    </tbody>
                </table>
            </div>
                        
            <div id="total_money_gained">
                <h3>Total Money Gained</h3>
                <table>
                    <thead>
                        <tr>
                            <th>VIP</th>
                            <th>Regular</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    
                    <tbody>
                        <tr>
                            <td><%=total_vip_money_gained%>€</td>
                            <td><%=total_reg_money_gained%>€</td>
                            <td><%=total_vip_money_gained + total_reg_money_gained%>€</td>
                        </tr>
                    </tbody>
                </table>
            </div>
                   
            <div id="most_money_gained">
                
                <h3>Most Money Gained By An Event In Date Range</h3>
                
                <form id="most_money_gained_form" onsubmit="most_money_gained_POST();return false">
                    
                    <label for="from_date">From date:</label>
                    <input type="date" id="from_date" name="from_date"
                        value="2024-12-12"
                        min="2024-01-01" max="2025-12-31"
                        required>

                    <br>

                    <label for="to_date">To date:</label>
                    <input type="date" id="to_date" name="to_date"
                        value="2024-12-12"
                        min="2024-01-01" max="2025-12-31"
                        required>
                
                    <br>   
                    
                    <input type="submit" value="See Event">
                    
                </form>
                
                <br>
                
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Type</th>
                            <th>Capacity</th>
                            <th>Money Gained</th>
                        </tr>
                    </thead>
                    
                    <tbody id="most_money_gained_body">
                        
                    </tbody>
                    
                </table>
                
            </div>
                        
            <div id="bookings_date_range">
                
                <h3>Bookings In Date Range</h3>
                
                <form id="bookings_date_range_form" onsubmit="bookings_date_range_POST();return false">
                    
                    <label for="from_date">From date:</label>
                    <input type="date" id="from_date" name="from_date"
                        value="2024-12-12"
                        min="2024-01-01" max="2025-12-31"
                        required>

                    <br>

                    <label for="to_date">To date:</label>
                    <input type="date" id="to_date" name="to_date"
                        value="2024-12-12"
                        min="2024-01-01" max="2025-12-31"
                        required>
                
                    <br>   
                    
                    <input type="submit" value="See Bookings">
                    
                </form>
                
                <br>
                
                <table>
                    <thead>
                        <tr>
                            <th>Event Name</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Tickets</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    
                    <tbody id="bookings_date_range_body">
                        
                    </tbody>
                    
                </table>
                
            </div>
    
        </div>
                        
                    <%
                                         
            }catch (SQLException | ClassNotFoundException e) {
                System.err.println("AdminPage says: Got an exception!");
                System.err.println(e.getMessage());
            }
                    %>
                    
    </body>
    
</html>
