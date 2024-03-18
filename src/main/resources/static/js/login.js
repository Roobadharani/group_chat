var email=document.querySelector("#email");
var uname=document.querySelector("#name");
var password=document.querySelector("#password");
var phone=document.querySelector("#phone");
var registerForm=document.querySelector("#RegisterForm")
registerForm.addEventListener('submit', check, true);

function check(event)
{
    console.log("function check called");

  const myHeaders=new Headers();
  myHeaders.append("Content-Type", "application/json");

  const raw= JSON.stringify({

    "mailId" : email,
    "username" : uname,
    "password" : password,
    "mobileNumber" : phone
  });

  const requestOptions={
    method: "POST",
    headers: myHeaders,
    body:raw,

  };
  console.log("values assigned");
  fetch('http://localhost:8086/register', requestOptions)
      .then(response => {

        if (response.ok) {
          return response.text();
        }
      //  throw new Error('Login failed. Please check your credentials.');
      })
      .then(token => {
          localStorage.setItem("token", token);
          window.location.href = "index.html";
          }

      )
      .catch(error => {
        console.error("Error:", error);
      });

  };

