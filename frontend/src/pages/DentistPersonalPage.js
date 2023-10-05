import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import SideBar from "../layouts/SideBar";
import axios from "axios";
export default function PersonalPage(props)
{
    
    const {dentistId} = useParams();
    const navigate = useNavigate();
    const handleLogin = async(event) =>
    {
      
      event.preventDefault();
      console.log("line 32 Login")
      try{
        const response = await axios.post("http://localhost:8080/login", 
          JSON.stringify(response),
          {
            headers: {"Content-Type": "application/json"},
            withCredentials:false
          }
        );
        
        if(response.status===200 && response.data!="-1")
        {
          console.log("line 46" + response.data);
          navigate(`dentistpage/${response.data}`)
        }
        
      }catch(err){
        console.log(err);
      };
      
      
    }


    return <div>

                <SideBar id={dentistId}/>  
                
                
                 
                 
                

            </div>



}