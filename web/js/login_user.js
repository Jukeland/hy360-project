var logged_in_username = "";
var logged_in_password = "";
var logged_in_userType = "";
var logged_in_id = "";

var event_id;
var vip;
var reg;
var vip_price;
var reg_price;
var final_price;


/* 
 * ========================================================================================
 * 
 *                          The following functions are used to setup the login page (AJAX)
 *   
 * ========================================================================================   
 */


/* check if the user's credentials are on the database and display a message accordingly */
function loginPOST(){
    let myForm = document.getElementById("login_form");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => data[key] = value);
    let jsonData = JSON.stringify(data);
    logged_in_username = data["username"];
    logged_in_password = data["password"];
    console.log("logged_in_username: " + logged_in_username);
    console.log("logged_in_password: " + logged_in_password);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'Login');
    xhr.setRequestHeader("Content-type", "aplication/json");
    xhr.send(jsonData);
    
    xhr.onload = function(){
      if(xhr.status === 200){
          const res = JSON.parse(xhr.responseText);
          set_login_page(res);
          
      }else if(xhr.status === 401){
          logged_in_username = "";
          logged_in_password = "";
          logged_in_userType = "";
          logged_in_id = "";
          $('#login_msg').empty();
          $('#login_msg').append("Provide a correct username and password");
          $('#login_msg').show();
      }else if(xhr.status !== 200){
          logged_in_username = "";
          logged_in_password = "";
          logged_in_userType = "";
          logged_in_id = "";
          alert('Request failed. Returned status ' + xhr.status);
      }
    };
    
    return false;

}

/* set the logged_in_page according to the user type (admin, pet owner, pet keeper) */
function set_login_page(response){
    logged_in_userType = response.userType;
    console.log("logged_in_userType: " + response.userType);
    if(response.userType === "admin"){
        $('#login_msg').empty();
        $('#home').hide();
        $('#login').hide();
        $('#register').hide();
        $('#events_table').hide();
        $('#logout').append('<a href="index.html" onclick="logout()">Logout</a>');
        $('#login_wrapper').load('jsp/admin_page.jsp');
    }else{
        $('#home').hide();
        $('#login').hide();
        $('#register').hide();
        $('#events_table').hide();
        $('#events').append('<a onclick="events_init()">Events</a>');
        $('#logout').append('<a href="index.html" onclick="logout()">Logout</a>');
        $('#profile').append('<a onclick="updateInit()">Profile</a>');
        $('#bookings').append('<a onclick="bookings_init()">Bookings</a>');
        $('#login_form').hide();
        $('#login_msg').empty();
        $('#login_msg').append('Welcome ' + logged_in_username);
    }
}

/* 
 * ========================================================================================
 * 
 *                              The following functions are used to update the user's info (AJAX)
 *   
 * ========================================================================================   
 */

/* get the user's data from the database */
function updateInit(){
    
    $('#login_wrapper').load('profile.html');
    
    let data = {};
    data['username'] = logged_in_username;
    data['password'] = logged_in_password;
    let jsonData = JSON.stringify(data);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'UpdateGetInfo');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if (xhr.readyState === 4 && xhr.status === 200){
            updateView(xhr.responseText);
        } else if (xhr.status !== 200){
            alert(xhr.status);
        }
    };
}

/* display the user's data */
function updateView(response){

    const jr = JSON.parse(response);
    
    $('#username').val(jr.username);
    $('#email').val(jr.email);
    $('#pwd').val(jr.password);
    $('#cnfmpwd').val(jr.password);
    $('#first_name').val(jr.first_name);
    $('#last_name').val(jr.last_name);
    $('#balance').val(jr.balance);
    $('#card_number').val(jr.card_number);
   
}

/* register the updated user's data into the database */
function updatePOST(){

    const data = {};
    data['card_number'] = document.getElementById("card_number").value;
    data['username'] = logged_in_username;

    let jsonData = JSON.stringify(data);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'UpdateInfo');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            updateInit();
        }else if(xhr.status !== 200){
            alert(xhr.status);
        }
    };
}


/* 
 * ========================================================================================
 * 
 *                     The following functions are used to display the available events (AJAX)
 *   
 * ========================================================================================   
 */


function events_init(){
    
    $('#login_msg').empty();
    $('#login_wrapper').load('events.html');
    
    let data = {};
    data['username'] = logged_in_username;
    let jsonData = JSON.stringify(data);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'AvailableEvents');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if (xhr.readyState === 4 && xhr.status === 200){
            show_available_events(xhr.responseText);
        } else if (xhr.status !== 200){
            alert(xhr.status);
        }
    };
    
}

function show_available_events(response){
    
    const jr = JSON.parse(response);
    
    let text = '[{';
    for(var i = 0; i < jr.length; i++){
        text = text + jr[i];
        if(i < jr.length - 1)
            text = text + '},{';
    }
    text = text + '}]';
    const obj = JSON.parse(text);
    
    for(var i = 0; i < jr.length; i++){
        
        let name = obj[i].name;
        
        $('#table_body').append('<tr><td>' + obj[i].name + '</td><td>' + obj[i].date + '</td><td>' + obj[i].time + '</td><td>' + obj[i].type + '</td><td>' + obj[i].capacity + '</td><td><button type="button" onclick="ticket_init(\'' + name + '\', \'' + obj[i].event_id + '\')">Book</button></td></tr>');
    
    }
    
}


/* 
 * ========================================================================================
 * 
 *               The following functions are used to display the available tickets of an event (AJAX)
 *   
 * ========================================================================================   
 */


function ticket_init(name, e_id){
    
    $('#login_wrapper').load('booking_form.html');
    event_id = e_id;
    
    const data = {};
    data['name'] = name;
    
    let jsonData = JSON.stringify(data);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'AvailableTickets');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            show_available_tickets(xhr.responseText);
        }else if(xhr.status !== 200){
            alert(xhr.status);
        }
    };
    
}

function show_available_tickets(response){
    
    const jr = JSON.parse(response);
    
    let text = '[{';
    for(var i = 0; i < jr.length; i++){
        text = text + jr[i];
        if(i < jr.length - 1)
            text = text + '},{';
    }
    text = text + '}]';
    const obj = JSON.parse(text);
    
    vip = 0;
    reg = 0;
    
    for(var i = 0; i < jr.length; i++){
        
        if(obj[i].type === "VIP"){
            vip++;
            vip_price = obj[i].price;
        }else if(obj[i].type === "regular"){
            reg++;
            reg_price = obj[i].price;
        }   

        // $('#tickets_table_body').append('<tr><td>' + obj[i].type + '</td><td>' + obj[i].price + '</td></tr>');
        
    }
    
    $('#tickets_table_body').append('<tr><td>VIP</td><td>' + vip + '</td><td>' + vip_price + '€</td></tr>');
    $('#tickets_table_body').append('<tr><td>Regular</td><td>' + reg + '</td><td>' + reg_price + '€</td></tr>');
    
}

function show_final_price(){
    
    if(vip === 0)
        final_price = $('#reg_tickets').val() * reg_price;
    else if(reg === 0)
        final_price = $('#vip_tickets').val() * vip_price;
    else
        final_price = $('#vip_tickets').val() * vip_price + $('#reg_tickets').val() * reg_price;
    
    $('#final_price').empty();
    $('#final_price').append("Final Price: " + final_price + "€");
    
}


/* 
 * ========================================================================================
 * 
 *              The following functions are used to register a new booking in the database (AJAX)
 *   
 * ========================================================================================   
 */


function book_check(){

    if(parseInt($('#vip_tickets').val()) <= vip && parseInt($('#reg_tickets').val()) <= reg){
        book_post();
    }else{
        $('#ticket_warning').empty();
        $('#ticket_warning').append("You can't book more tickets than available");
    }
        
}

function book_post(){
    
    const data = {};
    data['event_id'] = event_id;
    data['username'] = logged_in_username;
    data['total_vip'] = vip;
    data['total_reg'] = reg;
    data['vip_num'] = parseInt($('#vip_tickets').val());
    data['reg_num'] = parseInt($('#reg_tickets').val());
    data['ticket_num'] = parseInt($('#vip_tickets').val()) + parseInt($('#reg_tickets').val());;
    data['price'] = final_price;
    
    let jsonData = JSON.stringify(data);
    
    console.log("bookPOST: " + jsonData);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'BookRegister');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            show_book_msg('success');
        }else if(xhr.status !== 200){
            show_book_msg('failure');
        }
    };
    
}

function show_book_msg(opCode){
    
    if(opCode === "success"){
        $('#ticket_warning').empty();
        $('#book_msg').empty();
        $('#book_msg').append("Booking was successful!");
    }else if(opCode === "failure"){
        $('#ticket_warning').empty();
        $('#book_msg').empty();
        $('#book_msg').append("Booking was not successful!");
    }
    
}


/* 
 * ========================================================================================
 * 
 *     The following functions are used to display the user's bookings and update their status accordingly (AJAX)
 *   
 * ========================================================================================   
 */

function get_bookings(){
    
    let data = {};
    data['username'] = logged_in_username;
    let jsonData = JSON.stringify(data);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'GetBookings');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if (xhr.readyState === 4 && xhr.status === 200){
            show_bookings(xhr.responseText);
        } else if (xhr.status !== 200){
            $('#man_book_msg').append("You have no bookings");
        }
    };
    
}

function show_bookings(response){
    
    const jr = JSON.parse(response);
   
    let text = '[{';
    for(var i = 0; i < jr.length; i++){
        text = text + jr[i];
        if(i < jr.length - 1)
            text = text + '},{';
    }
    text = text + '}]';
    const obj = JSON.parse(text);
    
    for(var i = 0; i < jr.length; i++){
        
        $('#bookings_body').append('<tr><td>' + obj[i].name + '</td><td>' + obj[i].date + '</td><td>' + obj[i].time + '</td><td>' + obj[i].ticket_num + '</td><td>' + obj[i].price + '€</td><td><button onclick="cancel_booking(\'' + obj[i].booking_id + '\')">Cancel</button></td></tr>');
        
    }
    
}

function cancel_booking(booking_id){
    
    let data = {};
    data['booking_id'] = booking_id;
    data['username'] = logged_in_username;
    let jsonData = JSON.stringify(data);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'CancelBooking');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if (xhr.readyState === 4 && xhr.status === 200){
            bookings_init();
        } else if (xhr.status !== 200){
            alert(xhr.status);
        }
    };
    
}

function bookings_init(){
    
    $('#login_msg').empty();
    $('#book_area').empty();
    $('#review_area').empty();
    $('#login_wrapper').load('bookings.html');
    
    get_bookings();
    
}


/* 
 * ========================================================================================
 * 
 *                      The following functions are used to implement admin privilages (AJAX)
 *   
 * ========================================================================================   
 */


function admin_add_event(){
    
    $('#event_form_wrapper').load('event_form.html');
    $('#add_event').hide();
    
}

/* post request for adding an event */
function add_event_POST(){
    
    let myForm = document.getElementById("event_form");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => data[key] = value);
    
    let jsonData = JSON.stringify(data);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'AddEvent');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            $('#login_wrapper').load('jsp/admin_page.jsp');
        }else if(xhr.status !== 200){
            alert(xhr.status);
        }
    };
    
}

/* post request for getting the event with the most money gained in a period of dates */
function most_money_gained_POST(){
    
    let myForm = document.getElementById("most_money_gained_form");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => data[key] = value);
    
    let jsonData = JSON.stringify(data);
    console.log("most_money_gained data: " + jsonData);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'MostMoneyGained');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            show_most_money_gained(xhr.responseText);
        }else if(xhr.status !== 200){
            alert(xhr.status);
        }
    };
    
}

/* function to update the page with the event info */
function show_most_money_gained(response){
    
    const jr = JSON.parse(response);
    
     let text = '[{';
    for(var i = 0; i < jr.length; i++){
        text = text + jr[i];
        if(i < jr.length - 1)
            text = text + '},{';
    }
    text = text + '}]';
    const obj = JSON.parse(text);
    
     for(var i = 0; i < jr.length; i++){
        
        $('#most_money_gained_body').append('<tr><td>' + obj[i].name + '</td><td>' + obj[i].date + '</td><td>' + obj[i].time + '</td><td>' + obj[i].type + '</td><td>' + obj[i].capacity + '</td><td>' + obj[i].money_gained + '€</td></tr>');
        
    }
    
}

function bookings_date_range_POST(){
    
    let myForm = document.getElementById("bookings_date_range_form");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => data[key] = value);
    
    let jsonData = JSON.stringify(data);
    console.log("bookings_date_range_form data: " + jsonData);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'BookingsDateRange');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            show_bookings_date_range(xhr.responseText);
        }else if(xhr.status !== 200){
            alert(xhr.status);
        }
    };
    
}

function show_bookings_date_range(response){
    
    const jr = JSON.parse(response);
    
     let text = '[{';
    for(var i = 0; i < jr.length; i++){
        text = text + jr[i];
        if(i < jr.length - 1)
            text = text + '},{';
    }
    text = text + '}]';
    const obj = JSON.parse(text);
    
     for(var i = 0; i < jr.length; i++){
        
        $('#bookings_date_range_body').append('<tr><td>' + obj[i].name + '</td><td>' + obj[i].date + '</td><td>' + obj[i].time + '</td><td>' + obj[i].ticket_num + '</td><td>' + obj[i].price + '€</td>');
        
    }
    
}

/* deletes the user selected from the administrator */
function admin_delete(id, type){
    let data = {};
    data['id'] = id;
    data['type'] = type;
    let jsonData = JSON.stringify(data);

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'AdminDelete');
    xhr.send(jsonData);
    
    xhr.onload = function(){
        if (xhr.readyState === 4 && xhr.status === 200){
            $('#login_wrapper').load('jsp/admin_page.jsp');
        } else if (xhr.status !== 200){
            alert(xhr.status);
        }
    };
}

/* re-initialize the user's info */
function logout(){
    logged_in_username = "";
    logged_in_password = "";
    logged_in_userType = "";
    logged_in_id = "";
}