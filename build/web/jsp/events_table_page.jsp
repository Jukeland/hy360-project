<%-- 
    Document   : events_table_page
    Created on : Nov 11, 2024, 8:35:11 PM
    Author     : anton
--%>

<%@ page import="mainClasses.Event" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="database.tables.EditEventsTable" %>
<%@ page import="java.sql.SQLException" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Events</title>
    <link rel="stylesheet" href="../css/style.css">
    <style>
         
        #wrapper{
            height: 100%;
            width: fit-content;
            display: flex;

            text-align: center;
            margin:auto;
            font-family: fantasy;
            font-size: 20px;
        }
        
        h3{
            height: 100%;
            width: fit-content;
            display: flex;

            text-align: center;
            margin:auto;
            font-family: fantasy;
            font-size: 40px;
        }
        
    </style>
</head>
<body>
    <div>
      <div>
        <header class="headers" id="title">
          <h1>Event Booking</h1>
        </header>
      </div>
  
  
      <div class="black_div">
        <nav id="navigator">
          <ul>
            <li class="nav_list headers"><a href="../index.html">Home</a></li>
            <li class="nav_list headers"><a href="../register.html">Register</a></li>
            <li class="nav_list headers"><a href="../login.html">Login</a></li>
            <li class="nav_list headers"><a href="events_table_page.jsp">Events</a></li>
          </ul>
        </nav>
      </div>
    </div>
    <h3>Events</h3>
    <div id="wrapper">
        <%
            ArrayList<Event> events;
            EditEventsTable eet = new EditEventsTable();
            
            try{
                events = eet.databaseToEvents();
        %>
        
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
                <%
                    for(Event ev : events){
                %>
                <tr>
                    <td><%=ev.getName()%></td>
                    <td><%=ev.getDate()%></td>
                    <td><%=ev.getTime()%></td>
                    <td><%=ev.getType()%></td>
                    <td><%=ev.getCapacity()%></td>
                </tr>
            <%
                    }
                }catch(SQLException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            %>
            </tbody>
            
        </table>
            
    </div>
            
</body>
</html>
