var password = "";
var confirmPassword = "";
var pwdStrength = "";

const uppercaseLetters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
const hasLowerCaseLetters = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
const specialCharacters = ["!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", ",", ";", "?", "/", "[", "]", "{", "}", "<", ">", ".", "`"];


// this function checks if the two passwords match or not
// and lets the user know
function checkPassword(){
    password = document.getElementById("pwd").value;
    confirmPassword = document.getElementById("cnfmpwd").value;
    if(!(password === confirmPassword))
        document.getElementById("pwdWarning").innerHTML = "Password mismatch. Please input the same password";
    else
        document.getElementById("pwdWarning").innerHTML = "";
    checkPasswordStrength();
}


// this function checks the password strength 
// and lets the user know
function checkPasswordStrength(){
    var numbers = 0;
    var hasLowerCase = false;
    var hasUpperCase = false;
    var hasSpecialChar = false;
    const pwdLength = password.length;

    for(var i = 0; i < pwdLength; i++){
        for(var j = 0; j < uppercaseLetters.length; j++){
            if(password.charAt(i) == uppercaseLetters[j])
                hasUpperCase = true;
            else if(password.charAt(i) == hasLowerCaseLetters[j])
                hasLowerCase = true;
            else if(password.charAt(i) == specialCharacters[j])
                hasSpecialChar = true;
        }

        for(var j = 0; j < 10; j++){
            if(password.charAt(i) == j){
                numbers++;
                j = 11;
            }
        }
    }

    if(numbers/pwdLength >= 0.5){
        document.getElementById("pwdStrength").style.color = "red";
        pwdStrength = "weak";
    }else if(numbers > 0 && hasLowerCase && hasUpperCase && hasSpecialChar){
        document.getElementById("pwdStrength").style.color = "green";
        pwdStrength = "strong";
    }else{
        document.getElementById("pwdStrength").style.color = "orange";
        pwdStrength = "medium";
    }

    if(password !== "")
        document.getElementById("pwdStrength").innerHTML = "Password is " + pwdStrength;
    else
        document.getElementById("pwdStrength").innerHTML = "";
}


// this function hides and shows the user's password
function changeVisibility(){
    if(document.getElementById("pwd").type === "password"){
        document.getElementById("pwd").type = "text";
        document.getElementById("cnfmpwd").type = "text";
        document.getElementById("hideShow").value = "Hide Password";
        document.getElementById("hideShow").innerHTML = "Hide Password";
    }else{
        document.getElementById("pwd").type = "password";
        document.getElementById("cnfmpwd").type = "password";
        document.getElementById("hideShow").value = "Show Password";
        document.getElementById("hideShow").innerHTML = "Show Password";
    }
}

function termsChecked(){
    if(document.getElementById("terms").checked)
        return true;
    else
        return false;
}


function canSubmit(){
    if(pwdStrength === "weak" || pwdStrength === "formidable" || !termsChecked()){
        return false;
    }else{
        return true;
    }
}

function check_username(){
    
    if(document.getElementById("username").value.length < 8){
        $('#username_warning').empty();
        $('#username_warning').css('color', 'red');
        $('#username_warning').append("Username is too short");
        return;
    }
    
    const data = {};
    data['username'] = document.getElementById("username").value;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'CheckUsername');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
    
    xhr.onload = function(){
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#username_warning').empty();
            $('#username_warning').css('color', 'green');
            $('#username_warning').append('The username is available');
        }else if (xhr.status !== 200) {
            $('#username_warning').empty();
            $('#username_warning').css('color', 'red');
            $('#username_warning').append('Username is already in use');
        }
    };
}

function check_email(){
    
    if(document.getElementById("email").value === ""){
       return;
    }
    
    const data = {};
    data['email'] = document.getElementById("email").value;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'CheckEmail');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
    
    xhr.onload = function(){
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#email_warning').empty();
            $('#email_warning').css('color', 'green');
            $('#email_warning').append('The email is available');
        }else if (xhr.status !== 200) {
            $('#email_warning').empty();
            $('#email_warning').css('color', 'red');
            $('#email_warning').append('Email is already in use');
        }
    };
}

function register(){
    if(canSubmit()){
        registerPOST();
        return false;
    }else
        $('#ajax_content').append("You cannot register. Check your inputs and try again!");
}

function registerPOST(){
    let myForm = document.getElementById("Form");
    let formData = new FormData(myForm);

    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });
    console.log("Register");
    data['balance'] = "0";
    data['card_number'] = "0";
    
    console.log(data['first_name']);
    
    var xhr = new XMLHttpRequest();
    
    xhr.onload = function(){
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Success");
            $('#ajax_content').empty();
            $('#ajax_content').append("Successful Registration. Now please log in!");
        }else if (xhr.status !== 200) {
            $('#hope').append('Request failed. Returned status of ' + xhr.status + "<br>");
           const responseData = JSON.parse(xhr.responseText);
            for (const x in responseData) {
                $('#hope').append("<p style='color:red'>" + x + "=" + responseData[x] + "</p>");
            }
        }
    };
    
    xhr.open('POST', 'Register');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function create_DB(){
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'InitDB');
    xhr.send();
    
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
              console.log("The database has been created successfully");
        } else if (xhr.status !== 200) {
             console.log("The database has already been created");
        }
    };
    
}