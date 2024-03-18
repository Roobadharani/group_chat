//style in js for login/signup page
//const loginText = document.querySelector(".title-text .login");
//const loginForm = document.querySelector("form.login");
//const loginBtn = document.querySelector("label.login");
//const signupBtn = document.querySelector("label.signup");
//const signupLink = document.querySelector("form .signup-link a");
//// const signupsubmit=document.querySelector("signups-submit");
//signupBtn.onclick = (() => {
//   loginForm.style.marginLeft = "-50%";
//   loginText.style.marginLeft = "-50%";
//});
//loginBtn.onclick = (() => {
//   loginForm.style.marginLeft = "0%";
//   loginText.style.marginLeft = "0%";
//});
//signupLink.onclick = (() => {
//   signupBtn.click();
//   return false;
//});
// signupsubmit.onclick = (() => {
//   loginForm.style.marginLeft = "-50%";
//   loginText.style.marginLeft = "-50%";
// });

//sign-up form POST fetch
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
  .then((result)=> console.log(result))
  .then(alert("Login Successfully!"))
  .then(window.location.href = "index.html")
  .catch((error)=> alert(error))

});

////fetch POST for login
//document.getElementById("login-form").addEventListener("submit", function(event){
//  event.preventDefault();
//
//  const username = document.getElementById("login-username").value;
//  const password = document.getElementById("login-password").value;
//
//
//const myHeaders=new Headers();
//myHeaders.append("Content-Type", "application/json");
//
//const raw= JSON.stringify({
//  "username" : username,
//  "password" : password,
//});
//
//const requestOptions={
//  method: "POST",
//  headers: myHeaders,
//  body:raw,
//  redirect: "follow"
//};
//fetch('http://localhost:8086/login', requestOptions)
//    .then(response => {
//
//      if (response.ok) {
//
//        window.location.href = "index.html";
//      }
//     // throw new Error('Login failed. Please check your credentials.');
//    })
//    .then(token => {
//        localStorage.setItem("token", token);
//
//        }
//
//    )
//    .catch(error => {
//      console.error("Error:", error);
//    });
//
//});
//
//
//
////fetch for search filter using tokens
//const token = localStorage.getItem("token");
//const headers = {
//  "Authorization": `Bearer ${token}`
//};
//
//fetch('http://localhost:9090/places', {
//  method: 'GET',
//  headers: headers
//})
//.then(response => {
//  if (response.ok) {
//    return response.json();
//  }
//  throw new Error('Network response was not ok.');
//})
//.then(data => {
//  console.log(data);
//})
//.catch(error => {
//  console.error('There was a problem with the GET request:', error);
//});
//
