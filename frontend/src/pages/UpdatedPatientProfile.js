import React, { useState, useEffect } from "react";
import { useLocation, useParams,useNavigate} from "react-router-dom";
import axios from "axios";
import Header from "../layouts/Header";



function UpdatedPatientProfile(props)
{
    let { dentistId } = useParams(); 
   
    const navigate = useNavigate();
    const location = useLocation();
    const ssn = new URLSearchParams(location.search).get('ssn'); //console.log(ssn);

    const [patient, setPatient] = useState({

        name: "",
        surname: "", 
        ssn: "",
        birthdate: "",
        email: "",
        telephone: "",
        address: "",
        gender: "",
        dentist_id: dentistId
    });
  


    useEffect(() => {
        loadPatient();
      }, []);


    const loadPatient = async () => {
        const result = await axios.get(`http://localhost:8080/patients/getPatient/${ssn}`);
        setPatient(result.data);
    };

    const handleChange = (event) => {
        setPatient({ ...patient, [event.target.name]: event.target.value });
    };

    const handleSubmit  = async (event) => {
        event.preventDefault();
        await axios.put(`http://localhost:8080/patients/updatePatient/${ssn}?dentistId=${dentistId}`, patient);
        navigate(`/dentistpage/${dentistId}`);
    };
    
    const handleCancel = async(event) =>{
      event.preventDefault();
      navigate(`/dentistpage/${dentistId}`);
    }

    return       <div>
    <Header title={"ToothHub"}/>
    <div className="centered-container">
    <div style={{height:"100%", marginTop:"2%", backgroundColor:"#8DDFCB", borderRadius: "25px", padding:"10px 30px"}}>
      <h2 style={{textAlign:"center", paddingLeft:"10px", color:"black"}}>Update Patient Form</h2>

      <form onSubmit={handleSubmit} >

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
            <select name="gender" id="gender" onChange= {handleChange} style={{ borderRadius: "25px", margin:"10px 10px"}} value={patient.gender}> 
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

        
            <button type="submit" className="btn btn-outline-primary btn-sm" style={{display: "block", marginBottom: "8px", color:"#614BC3", borderBlockColor:"#614BC3"}}>
              Submit
            </button>
            <button  className="btn btn-outline-primary btn-sm" style={{display: "block", color:"#614BC3", borderBlockColor:"#614BC3"}} onClick={handleCancel}>
              Cancel
            </button>
          </form>
      </div>
    </div>

    </div>

}


export default UpdatedPatientProfile;