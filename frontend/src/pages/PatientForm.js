import {React, useState } from "react";
import { useLocation,useNavigate, createSearchParams } from "react-router-dom";
import axios from 'axios';
import Header from "../layouts/Header";



function PatientForm()
{
  const location = useLocation();
  const dentistId = new URLSearchParams(location.search).get('dentistId');
  
  const navigate = useNavigate();
  

  const [patient, setPatient] = useState({
      name: "",
      surname: "",
      ssn: "",
      birthdate: "",
      gender: "",
      email: "",
      telephone: "",
      address: "",
      dentist_id: 0
    });

    const [error, setError] = useState("");
    let [existError, setExistError] = useState(false);

    function handleChange(event)
    {
        const { name, value } = event.target;

        setPatient(prevValue => {
            return {
              ...prevValue,
              [name]: value
            };
          });
    }
    
    const handleLogin = async(event) =>
    {
      
      event.preventDefault();
      console.log("line 37 Login")
      try{
        
        const response = await axios.post(`http://localhost:8080/patients/addPatient?dentistId=${dentistId}`,
        
        JSON.stringify(patient),
          {
            headers: {"Content-Type": "application/json"},
            withCredentials:false
          }
        );
        const queryParams = {submittedName:patient.name, submittedSurname:patient.surname, result: ""};
        if(response.status===201)
        {
          queryParams.result = "true";
          console.log("Succesful submit");
          
          
        }else{
          queryParams.result = "fail";
          console.log("Unsuccesful submit");
        }
        navigate({pathname: `/dentistpage/${dentistId}/patientForm/resultForm`, search: `?${createSearchParams(queryParams)}`});
      }catch(err){
        console.log(err);

        if (err.response && err.response.data) {
          console.log("Exception message:", err.response.data);
          
          setError(err.response.data);
          setExistError(true);
        }
      };
      
      
    }



   
    return (
      <div>
        <Header title={"ToothHub"}/>
        <div className="centered-container">
        <div style={{height:"100%", marginTop:"2%", backgroundColor:"#8DDFCB", borderRadius: "25px", padding:"10px 30px"}}>
          <h2 style={{textAlign:"center", paddingLeft:"10px", color:"black"}}>New Patient Form</h2>

          <form onSubmit={handleLogin}>

            {existError && (
                  <p style={{color:"red", fontSize:"10px"}}>{error}</p>
                  )}

            <label for="fname" style={{color:"black"}}>First name:</label><br/>
            <input type="text"  name="name" onChange= {handleChange} value={patient.name} style={{float:"left", paddingLeft:"15px", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            <label for="lname" style={{color:"black"}}>Last name:</label><br/>
            <input type="text"  name="surname" onChange= {handleChange} value={patient.surname} style={{float:"left", paddingLeft:"15px", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            <label for="ssn" style={{color:"black"}}>Social Security Number (SSN):</label><br/>
            <input type="text"  name="ssn" onChange= {handleChange} value={patient.ssn} style={{float:"left", paddingLeft:"15px", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            <label for="birthdate" style={{color:"black"}}>Birthdate:</label><br/>
            <input type="date"  name="birthdate" onChange= {handleChange} value={patient.birthdate} style={{float:"left", paddingLeft:"15px", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            <label for="gender" style={{color:"black", padding: "15px 15px"}}>Choose gender: </label> 
                <select name="gender" id="gender" onChange= {handleChange} style={{ borderRadius: "25px", margin:"10px 10px"}}> 
                    <option name= "gender" value={"Male"} >Male</option> 
                    <option name="gender" value={"Female"} >Female</option> 
                    
                </select>
            <br/>   
            <br/>
            <br/>
            <label for="email" style={{color:"black"}}>Email:</label><br/>
            <input type="text"  name="email" onChange= {handleChange} value={patient.email} style={{float:"left", paddingLeft:"15px", color:"black"}}/>
            <br/>   
            <br/>
            <br/>

            <label for="telephone" style={{color:"black"}}>Telephone:</label><br/>
            <input type="text"  name="telephone" onChange= {handleChange} value={patient.telephone} style={{float:"left", paddingLeft:"15px", color:"black"}}/>
            <br/>   
            <br/>
            <br/>

            <label for="address" style={{color:"black"}}>Address:</label><br/>
            <input type="text"  name="address" onChange= {handleChange} value={patient.address} style={{float:"left", paddingLeft:"15px", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            


            <button >Save</button>

          </form>
        </div>
      </div>
    </div>);




}

export default PatientForm;