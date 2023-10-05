import React, { useState, useEffect } from "react";
import { useLocation, useParams,useNavigate} from "react-router-dom";
import axios from "axios";
import Header from "../layouts/Header";


function UpdatedAppointment(props)
{

    const {dentistId} = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const appointmentId = new URLSearchParams(location.search).get('appointmentId');

    const [appointment, setAppointment] = useState({

        date: "",
        time: "", 
        duration: "",
        therapy: " ",
        patient: {ssn: "",}
    });
    
    const [description, setDescription] = useState(false);
    const [therapy,setTherapy] = useState("");
    const [error, setError] = useState("");
    let [existError, setExistError] = useState(false);


    const handleSsnChange = (event) => {
      const newSsn = event.target.value;
    
      // Create a new appointment object with the updated "ssn" value
      const updatedAppointment = {
        ...appointment, // Copy the existing appointment state
        patient: {
          ...appointment.patient, // Copy the existing patient object
          ssn: newSsn, // Update the "ssn" field
        },
      };
      setAppointment(updatedAppointment);
    }

    useEffect(() => {
        loadAppointment();
      }, []);

      

    const loadAppointment = async () => {
        const result = await axios.get(`http://localhost:8080/appointments/getAppointment/${appointmentId}`);
        setAppointment(result.data);
    };

    const handleChange = (event) => {
        setAppointment({ ...appointment, [event.target.name]: event.target.value });
        const {name, value} = event.target;
        if(name==="therapy" && value==="Other")
        {
          console.log("i get here!")
          setDescription(true);
          

        }else if(name==="therapy" && value!=="Other"){
          setDescription(false);
          setTherapy("");
        }
    };

    const handleSubmit  = async (event) => {
        event.preventDefault();
        const updatedAppointment = { ...appointment };
        if(updatedAppointment.therapy==="Other"){ updatedAppointment.therapy =  therapy};
        try{
        await axios.put(`http://localhost:8080/appointments/updateAppointment/${appointmentId}?dentistId=${dentistId}&ssn=${appointment.patient.ssn}`, appointment);
        navigate(`/dentistpage/${dentistId}/appointmentslist/`);

        }catch(err){
          if (err.response && err.response.data) {
            
            setError(err.response.data);
            setExistError(true);
          }
        }
      };

      function handleChangeOther(event)
      {
          const { name, value } = event.target;
  
          setTherapy(value)
      }
    return <div>
        <Header title= {"ToothHub"}/>
        <div className="centered-container">

        <div style={{ height:"100%", marginTop:"2%", backgroundColor:"#8DDFCB", borderRadius: "25px", padding:"10px 30px"}}>
        <h2 style={{textAlign:"center", paddingLeft:"20px",paddingRight:"20px",paddingTop:"10px", color:"black"}}>Update Appointment Form</h2>
        <form onSubmit={handleSubmit}>

        {existError && (
                  <p style={{color:"red", fontSize:"10px"}}>{error}</p>
                  )}

            <label for="date" style={{color:"black"}}>Date:</label><br/>
            <input type="date"  name="date" onChange= {handleChange} value={appointment.date} style={{float:"left", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            <label for="time" style={{color:"black"}}>Time:</label><br/>
            <input type="time"  name="time" onChange= {handleChange} value={appointment.time} style={{float:"left", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            <label for="duration" >Duration:</label><br/>
            <input type="text"  name="duration" onChange= {handleChange} value={appointment.duration} style={{float:"left", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            <label for="ssn" style={{color:"black"}}>SSN:</label><br/>
            <input type="text"  name="ssn" onChange= {handleSsnChange}  value={appointment.patient===null ? "" :appointment.patient.ssn} style={{float:"left", color:"black"}}/>
            <br/>   
            <br/>
            <br/>
            <label for="therapy" style={{color:"black"}}>Treatment: </label><br/>
                  <select name="therapy" className="form-select" ariaLabel="Default select example" onChange={handleChange} style={{width:"70%"}} value={appointment.therapy}>
                    <option value="Tooth Extraction">Tooth Extraction</option>
                    <option value="Tooth Filling" selected>Tooth Filling </option>
                    <option value="Root Canal">Root Canal</option>
                    <option value="Tooth Cleaning">Tooth Cleaning</option>
                    <option value="Dental Bridge">Dental Bridge</option>
                    <option value="Other">Other</option>
                </select>
                <br/>   
              {(description===true) ? (<div> <label for="Therapy" style={{color:"black"}}>Enter Treatment:</label><br/>
              <input type="text" name="therapy" onChange= {(event)=>{setTherapy(event.target.value)}}  value={therapy} style={{float:"left", color:"black"}}/></div>) : null}
              <br/>   
              <br/>
              <br/>

              <button type="submit" className="btn btn-outline-primary btn-sm" style={{display: "block", marginBottom: "8px", color:"#614BC3", borderBlockColor:"#614BC3"}}>
              Submit
            </button>
            <button  className="btn btn-outline-primary btn-sm" style={{display: "block", color:"#614BC3", borderBlockColor:"#614BC3"}} onClick={()=>{navigate(`/dentistpage/${dentistId}`)}}>
              Cancel
            </button>
          </form>
          
        </div>
        </div>
    </div>
    


}

export default UpdatedAppointment;