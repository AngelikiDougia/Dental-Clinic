import React, { useState } from "react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";



function AdministrationLogin()
{
    const [credential, setCredential] = useState({
        username: "",
        password: ""
        
      });
      
   
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
        await axios.post(`http://localhost:8080/administrator/login?username=${credential.username}&password=${credential.password}`, 
          
        ).then(
          res => {console.log(res.data);

          if(res.data === "Successful Login"){
            console.log("please help me!");
            window.localStorage.setItem("loggedIn","true");
            
            navigate(`/adminpage`);
            /*navigate(`/dentistForm`);*/
            
              
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
          <p style={{color: "red"}}>Administration Login</p>
          <input type="text" placeholder="Username" onChange={handleChange} name="username" value={credential.username}/>
          <input type="password" placeholder="Password" onChange={handleChange} name="password" value={credential.password}/>
          
    
          <button type="submit">Login</button>
        </form>
      );



}

export default AdministrationLogin;

