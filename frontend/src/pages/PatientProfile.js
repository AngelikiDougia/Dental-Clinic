import React, { useState, useEffect } from "react";
import { useLocation, useParams,useNavigate} from "react-router-dom";
import axios from "axios";

import Header from "../layouts/Header";
import Card from "../layouts/Card";


export default function PatientProfile(props)
{
    const location = useLocation();
    const {dentistId} = useParams();
    const navigate = useNavigate();
    const ssn = new URLSearchParams(location.search).get('ssn');
    
    const [patient, setPatient] = useState({});

    const showPreviousAppointments = () =>{
      navigate(`/dentistpage/${dentistId}/patientslist/viewPreviousAppointments?ssn=${ssn}`)
    }
    
    

    useEffect(() => {
        axios.get(`http://localhost:8080/patients/getPatient/${ssn}`)
          .then(response => {
            setPatient(response.data);
          })
          .catch(error => {
            console.error('Error fetching patient data:', error);
          });
      }, [ssn]);

      const details ={type:"patient", firstRow:patient.name, secondRow:patient.surname, 
      thirdRow: patient.birthdate,
       forthRow: patient.gender,
      fifthRow: patient.telephone,
      sixthRow: patient.email,
      seventhRow: patient.address
      };
    return <div>
        <Header title= {"ToothHub"}/>

        <main>
          
            <a onClick= {showPreviousAppointments} style={{marginLeft: "58%", marginTop:"5%", paddingTop:"5%", marginBottom:"0%"}}><u>Previous Appointments ⮕</u> </a>
            <Card details={details}/>
          
        
        </main>

    </div>;




}