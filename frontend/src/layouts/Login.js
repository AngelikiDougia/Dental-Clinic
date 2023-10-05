import React, { useState } from "react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";



function Login()
{
    const [credential, setCredential] = useState({
        username: "",
        password: ""
        
      });
      const [invalidCredentials, setInvalidCredentials] = useState(false);

      const [errorMessage, setErrorMessage] = useState("");
   
    const navigate = useNavigate();

    function handleChange(event) {
        const { name, value } = event.target;
    
        setCredential(prevValue => {
          return {
            ...prevValue,
            [name]: value
          };
        });
    }

   

    const handleLogin = async(event) =>
    {
      
      event.preventDefault();
      console.log("line 32 Login")
      try{
        await axios.post("http://localhost:8080/login", 
          credential
        ).then(
          res => {console.log(res.data);

          if(res.data.message == "username not exists"){
            setInvalidCredentials(true);
            setErrorMessage("Unknown Username ");
            
          } else if (res.data.message == "Login Success") {
            window.localStorage.setItem("authorization","true");
            navigate(`dentistpage/${res.data.dentistId}`);
            
          } else {
            setInvalidCredentials(true);
            setErrorMessage(" Username and Password don't exist");
          }
        }, (fail) => {
          console.error(fail); // Error!
        }

        );
        
        
      } catch(err){

        navigate("/");
      }
      
    }

    return (
        
        <form className="form" onSubmit={handleLogin}>
          {invalidCredentials && (
            <p style={{ color: 'red' }}>{errorMessage}</p>
          )}
          <input type="text" placeholder="Username" onChange={handleChange} name="username" value={credential.username}/>
          <input type="password" placeholder="Password" onChange={handleChange} name="password" value={credential.password}/>
          
    
          <button type="submit">Login</button>
        </form>
      );



}

export default Login;

