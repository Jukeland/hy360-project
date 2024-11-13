<%-- 
    Document   : admin_page
    Created on : Nov 5, 2024, 7:29:26 PM
    Author     : anton
--%>

<%@ page import="mainClasses.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="database.tables.EditUsersTable" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="mainClasses.Event" %>
<%@ page import="database.tables.EditEventsTable" %>
<%@ page import="mainClasses.Booking" %>
<%@ page import="database.tables.EditBookingsTable" %>

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
            int money_gained = 0;
            
            ArrayList<Booking> bookings;
            EditBookingsTable ebt = new EditBookingsTable();
            
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
                            int ev_id = ev.getEvent_id();
                            for(Booking b : bookings){
                                if(ev_id == b.getEvent_id()){
                                    money_gained = b.getTicket_num() * b.getPrice();
                                }
                            }
                    %>
                    <tr>
                        <td><%=ev.getName()%></td>
                        <td><%=ev.getDate()%></td>
                        <td><%=ev.getTime()%></td>
                        <td><%=ev.getType()%></td>
                        <td><%=ev.getCapacity()%></td>
                        <td><%=money_gained%>€</td>
                        <td>
                            <button type="button" onclick="admin_delete('<%=ev.getEvent_id()%>', 'event')">Delete</button>
                        </td>
                    </tr>
                </tbody>
                
            </table>
    
        </div>
                        
                    <%
                            
                        }
            }catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
                    %>

        <br>
        <br>
        
        <div id="event_form_wrapper">
            <button id="add_event" onclick="admin_add_event()">Add Event</button>
        </div>
                    
    </body>
    
</html>
