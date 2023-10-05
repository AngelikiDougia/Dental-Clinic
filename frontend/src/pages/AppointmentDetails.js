import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";

import Header from "../layouts/Header";
import Card from "../layouts/Card";


function AppointmentDetails(props)
{

    const location = useLocation();
    const id = new URLSearchParams(location.search).get('appointmentId');
    
    const [appointment, setAppointment] = useState({});

    useEffect(() => {
        axios.get(`http://localhost:8080/appointments/getAppointment/${id}`)
          .then(response => {
            setAppointment(response.data);
          })
          .catch(error => {
            console.error('Error fetching appointment data:', error);
          });
      }, [id]);

      const patientName = appointment.patient ? appointment.patient.name : "";
      const patientSurname = appointment.patient ? appointment.patient.surname : "";
      const patientBirthdate = appointment.patient ? appointment.patient.birthdate : "";
      const patientGender = appointment.patient ? appointment.patient.gender : "";

      const details ={type:"appointment", firstRow: patientName,secondRow: patientSurname,
      thirdRow: patientBirthdate,forthRow: patientGender,
      fifthRow:appointment.date, sixthRow:appointment.time, seventhRow: appointment.duration,
      eighthRow: appointment.therapy};
    return <div>
        <Header title= {"ToothHub"}/>

        <main>
          
            
            <Card details= {details} />
          
        
        </main>

    </div>



}


export default AppointmentDetails;