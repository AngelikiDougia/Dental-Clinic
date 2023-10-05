import {React, useState } from "react";
import { useNavigate,  createSearchParams } from "react-router-dom";
import axios from 'axios';
import Header from "../layouts/Header";



function DentistForm()
{
  
  
  
  const navigate = useNavigate();
  

  const [dentist, setDentist] = useState({
      name: "",
      surname: "",
      username: "",
      password: "",
      email: "",
      telephone: "",
      address: ""
    });

    

    function handleChange(event)
    {
        const { name, value } = event.target;

        setDentist(prevValue => {
            return {
              ...prevValue,
              [name]: value
            };
          });
    }
    
    const handleSubmit = async(event) =>
    {
      
      event.preventDefault();
      console.log("line 37 Login")
      try{
        
        const response = await axios.post(`http://localhost:8080/createDentist`,
        
        JSON.stringify(dentist),
          {
            headers: {"Content-Type": "application/json"},
            withCredentials:false
          }
        );
        const queryParams = {submittedName:dentist.name, submittedSurname:dentist.surname, result: ""};
        if(response.status===201)
        {
          queryParams.result = "true";
          console.log("Succesful submit");
          
          
        }else{
          queryParams.result = "fail";
          console.log("Unsuccesful submit");
        }


        navigate({pathname: `/adminpage`, search: `?${createSearchParams(queryParams)}`});
      }catch(err){
        console.log(err);
        
      };
      
      
    }



   
    return (
      <div>
        <Header title={"ToothHub"}/>
        <main>
        <div className="centered-container">
              <div style={{ height:"100%", marginTop:"2%", backgroundColor:"#8DDFCB", borderRadius: "25px", padding:"10px 30px"}}>
                
                <h2 style={{textAlign:"center", paddingLeft:"20px",paddingRight:"20px",paddingTop:"10px", color:"black"}}>New Dentist Form</h2>

                <form style={{textAlign:"left", paddingLeft:"20px",paddingRight:"20px", color:"black"}} onSubmit={handleSubmit}>
                    <label for="text" style={{color:"black"}}>Username:</label><br/>
                    <input type="text"  name="username"  value={dentist.username} style={{float:"left", color:"black"}} onChange={handleChange} required/>
                    <br/> 
                    <label for="text" style={{color:"black"}}>Password:</label><br/>
                    <input type="password"  name="password"  value={dentist.password} style={{float:"left", color:"black"}} onChange={handleChange} required/>
                    <br/> 
                    <label for="text" style={{color:"black"}}>Name:</label><br/>
                    <input type="text"  name="name"  value={dentist.name} style={{float:"left", color:"black"}} onChange={handleChange}/>
                    <br/> 
                    <label for="text" style={{color:"black"}}>Lastname:</label><br/>
                    <input type="text"  name="surname"  value={dentist.surname} style={{float:"left", color:"black"}} onChange={handleChange}/>
                    <br/> 
                    <label for="text" style={{color:"black"}}>Email:</label><br/>
                    <input type="text"  name="email"  value={dentist.email} style={{float:"left", color:"black"}} onChange={handleChange}/>
                    <br/> 

                    <button type="submit" className="btn btn-outline-primary btn-sm" style={{display: "block", marginBottom: "8px", color:"#614BC3", borderBlockColor:"#614BC3"}}>
                        Save
                    </button>
                    <button  className="btn btn-outline-primary btn-sm" style={{display: "block", color:"#614BC3", borderBlockColor:"#614BC3"}} onClick = {()=>{navigate(`/adminpage`)}} >
                        Cancel
                    </button>

                </form>
            </div>
        </div>
        </main>
    </div>);




}

export default DentistForm;