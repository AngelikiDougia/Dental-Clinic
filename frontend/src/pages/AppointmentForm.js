import {React,  useEffect, useState } from "react";
import { useLocation, useParams,useNavigate, createSearchParams } from "react-router-dom";
import axios from 'axios';
import Header from "../layouts/Header";


function AppointmentForm()
{

    const location = useLocation();
    
    let queryParams = {submittedDate: "", submittedTime:"", result: " ", responseBody: ""}; 
    const dentistId = new URLSearchParams(location.search).get('dentistId');
    const [appointment,setAppointment] = useState({
        date: "",
        time: "",
        duration: 0,
        therapy: "Tooth Filling"  //default value
        
        
      });
    const [patient, setPatient] = useState("");
    const [description, setDescription] = useState(false);
    const [therapy,setTherapy] = useState("");
    const [error, setError] = useState("");
    let [existError, setExistError] = useState(false);
   
    const navigate = useNavigate();

    function handleChange(event)
    {
        const { name, value } = event.target;

        if(name==="therapy" && value==="Other")
        {
          console.log("i get here!")
          setDescription(true);
          

        }else if(name==="therapy" && value!=="Other"){
          setDescription(false);
          setTherapy("");
        }

        setAppointment(prevValue => {
            return {
              ...prevValue,
              [name]: value
            };
          });
    }

    function handleChangeOther(event)
    {
        const { name, value } = event.target;

        setTherapy(value)
    }

    function updateSSN(event)
    {
      setPatient(event.target.value);
    }


    
    const handleNewAppointment = async(event) =>
    {
        const updatedAppointment = { ...appointment };
        event.preventDefault();
        if(updatedAppointment.therapy==="Other"){ updatedAppointment.therapy =  therapy};
        try{
          await axios.post(`http://localhost:8080/appointments/addAppointment?dentistId=${dentistId}&ssn=${patient}`,
          updatedAppointment
          ).then(
            res => {
            if(res.data==="Resource saved successfully"){
                
              queryParams.submittedDate = appointment.date;
              queryParams.submittedTime = appointment.time;
              queryParams.result = true;
              queryParams.responseBody = res.data
              navigate({pathname:`/dentistpage/${dentistId}/appointmentForm/resultForm`, search: `?${createSearchParams(queryParams)}`})
            }
          }
          );
          
        
        } catch(err)
        {
           console.log(err);

           if (err.response && err.response.data) {
            console.log("Exception message:", err.response.data);
            queryParams.responseBody = err.response.data;
            queryParams.result = "fail";
            setError(err.response.data);
            setExistError(true);
          }
        }
          
          
    }
    

    return (
          <div>
          <Header title= {"ToothHub"}/>
          <div className="centered-container">
              <div style={{ height:"100%", marginTop:"2%", backgroundColor:"#8DDFCB", borderRadius: "25px", padding:"10px 30px"}}>
                
                <h2 style={{textAlign:"center", paddingLeft:"20px",paddingRight:"20px",paddingTop:"10px", color:"black"}}>New Appointment Form</h2>

                <form onSubmit={handleNewAppointment} style={{textAlign:"left", paddingLeft:"20px",paddingRight:"20px", color:"black"}}>

                {existError && (
                  <p style={{color:"red", fontSize:"10px"}}>{error}</p>
                  )}

                  <label for="Date" style={{color:"black"}}>Date:</label><br/>
                  <input type="date"  name="date"  onChange= {handleChange} value={appointment.date} style={{float:"left", color:"black"}}/>
                  <br/>   
                  <br/>
                  <br/>
                  <label for="Time" style={{color:"black"}}>Start Time:</label><br/>
                  <input type="time"  name="time" onChange= {handleChange} value= {appointment.time} style={{float:"left", color:"black"}}  required/>
                  <br/>   
                  <br/>
                  <br/>
                  <label for="Duration" style={{color:"black"}}>Duration:</label><br/>
                  <input type="text"  name="duration" onChange= {handleChange} value={appointment.duration} style={{float:"left", color:"black"}}/>
                  <br/>   
                  <br/>
                  <br/>
                  <label for="ssn" style={{color:"black"}}>SSN:</label><br/>
                  <input type="text"  name="ssn" onChange= {updateSSN}  value={patient} style={{float:"left", color:"black"}}/>
                  <br/>  
                  <br/>
                  <br/>
                  <a href={`/dentistpage/${dentistId}/patientForm`} style={{display: "block", color:"aliceblue"}}>New patient ssn? Register patient first!</a>
                  <br/>   
                  <br/>
                  <br/>

                  <label for="therapy" style={{color:"black"}}>Treatment: </label><br/>
                  <select name="therapy" className="form-select" ariaLabel="Default select example" onChange={handleChange} style={{width:"70%"}} >
                    <option value="Tooth Extraction">Tooth Extraction</option>
                    <option value="Tooth Filling" selected>Tooth Filling </option>
                    <option value="Root Canal">Root Canal</option>
                    <option value="Tooth Cleaning">Tooth Cleaning</option>
                    <option value="Dental Bridge">Dental Bridge</option>
                    <option value="Other">Other</option>
                  </select>
                  <br/>   
                  {(description===true) ? (<div> <label for="Therapy" style={{color:"black"}}>Enter Treatment:</label><br/>
                  <input type="text" name="therapy" onChange= {handleChangeOther}  value={therapy} style={{float:"left", color:"black"}}/></div>) : null}
                  <br/>   
                  <br/>
                  <br/>
                  
                  <button style={{display: "inline"}}>Save</button>
                    
                  

                </form>

            </div>
        </div>
        </div>
        );











}

export default AppointmentForm;