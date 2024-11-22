var logged_in_username = "";
var logged_in_password = "";
var logged_in_userType = "";
var logged_in_id = "";

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
        
        $('#table_body').append('<tr><td>' + obj[i].name + '</td><td>' + obj[i].date + '</td><td>' + obj[i].time + '</td><td>' + obj[i].type + '</td><td>' + obj[i].capacity + '</td><td><button type="button" onclick="book_event(\'' + name + '\')">Book</button></td></tr>');
    
    }
    
}

function book_event(name){
    
    const data = {};
    data['name'] = name;
    data['username'] = logged_in_username;
    
    let jsonData = JSON.stringify(data);
    
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
        $('#book_msg').append("Booking was successful!");
    }else if(opCode === "failure"){
        $('#book_msg').append("Booking was not successful!");
    }
    
}




function bookings_init(){
    
    $('#login_msg').empty();
    $('#login_wrapper').load('bookings.html');
    
}




function admin_add_event(){
    
    $('#event_form_wrapper').load('event_form.html');
    $('#add_event').hide();
    
}

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