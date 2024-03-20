'use strict';
const loginText = document.querySelector(".title-text .login");
const loginForm = document.querySelector("form.login");
const loginBtn = document.querySelector("label.login");
const signupBtn = document.querySelector("label.signup");
const signupLink = document.querySelector("form .signup-link a");
var usernamePage = document.querySelector('#wrapper');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var password=document.querySelector('#login-password').value.trim();
var navbar=document.querySelector("#navbar-placeholder");
var addUserName=document.querySelector("#profile");
var stompClient = null;
var username = null;


signupBtn.onclick = (() => {
   loginForm.style.marginLeft = "-50%";
   loginText.style.marginLeft = "-50%";
});
loginBtn.onclick = (() => {
   loginForm.style.marginLeft = "0%";
   loginText.style.marginLeft = "0%";
});
signupLink.onclick = (() => {
   signupBtn.click();
   return false;
});

document.getElementById("signup-form").addEventListener("submit", function(event){
  event.preventDefault();

  const email = document.getElementById("signup-email").value;
  const username = document.getElementById("signup-username").value;
  const password = document.getElementById("signup-password").value;
  const mobileNumber = document.getElementById("signup-mobile").value;

const myHeaders=new Headers();
myHeaders.append("Content-Type", "application/json");

const raw= JSON.stringify({
  "userMailId" : email,
  "username" : username,
  "userPassword" : password,
  "mobileNumber" : mobileNumber
});

const requestOptions={
  method: "POST",
  headers: myHeaders,
  body:raw,
  redirect: "follow"
};

fetch('http://localhost:8086/signup', requestOptions)
  .then((response)=> response.text())
  .then(msg=>alert(msg))
  .then((result)=> console.log(result))
  .catch((error)=> console.error(error))

});



var username="";
//fetch POST for login
document.getElementById("login-form").addEventListener("submit", function(event){
  event.preventDefault();

  const username = document.getElementById("login-username").value;
  const password = document.getElementById("login-password").value;


const myHeaders=new Headers();
myHeaders.append("Content-Type", "application/json");

const raw= JSON.stringify({
  "username" : username,
  "password" : password,
});

const requestOptions={
  method: "POST",
  headers: myHeaders,
  body:raw,
  redirect: "follow"
};
fetch('http://localhost:8086/login', requestOptions)
    .then(response => {
        return response.text(); // Read the response body
    })
    .then(text => {
      const responseData = JSON.parse(text);
      const username = responseData.username;
    })
    .then(text => {
        if (username !== null) {
          connect();
        } else {
            alert("Username or Password not matched!");
            throw new Error('Login failed. Please check your credentials.');
        }
    })
    .catch(error => {
       alert(error);
    });
  })



var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

messageForm.addEventListener('click', sendMessage, true)

function connect() {
console.log("start connection");
    username = document.querySelector('#login-username').value.trim();
console.log(username);
  if(username){
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        navbar.classList.remove('hidden');
        const servletUrl='http://localhost:8086/ws';
        var socket = new SockJS(servletUrl);
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
  }
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}




function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        getAllData();
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';

    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {

        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}
function getAllData() {
    fetch("http://localhost:8086/msg")
        .then((res) => {
            if (!res.ok) {
                throw new Error('Network response was not ok');
            }
            return res.json(); // Parse the JSON data here
        })
        .then((msg) => {
            console.log(msg); // Log the parsed JSON data
            displayChats(msg); // Pass the parsed JSON data to displayChats function
        })
        .catch((error) => alert(error));
}

function displayChats(messages) {
    messages.forEach(message => {
        var messageElement = document.createElement('li');
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);

        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;

    });
}
//
//document.getElementById('profile').addEventListener('mouseover', function() {
//        // Simulate fetching user information from the database (replace this with your actual API call)
//        setTimeout(function() {
//            // Sample user information (replace with actual data retrieved from the database)
//            fetch("http://localhost:8086/")
//            // Build HTML for the dropdown content
//            var dropdownContent = '<a><strong>Name:</strong> ' + userInfo.name + '</a>';
//            dropdownContent += '<a><strong>Email:</strong> ' + userInfo.email + '</a>';
//            // Add more user information to the dropdown content as needed
//
//            // Update the dropdown content
//            document.getElementById('profileDropdown').innerHTML = dropdownContent;
//        }, 500); // Simulated delay of 500 milliseconds (replace with actual API call)
//    });

//logout
document.getElementById("logout").addEventListener('click',function(){

const myHeaders=new Headers();
myHeaders.append("Content-Type", "application/json");

const raw= JSON.stringify({
  "username" : username,
});

const requestOptions={
  method: "DELETE",
  headers: myHeaders,
  body:raw,
  redirect: "follow"
};

fetch('http://localhost:8086/logout', requestOptions)
  .then((response)=> console.log(response.text()))
  .then(msg=>console.log(msg))
  .then(alert("loggedOut Successfully!"))
  .catch((error)=> console.error(error))
});


//one to one chat
//show name of that user
//history only loaded to that user


