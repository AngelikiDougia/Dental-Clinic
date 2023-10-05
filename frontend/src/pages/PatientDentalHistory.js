import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";
import Header from "../layouts/Header";



export default function PatientDentalHistory()
{

    const location = useLocation();
    const [appointments, setAppointments] = useState([]);
    const ssn = new URLSearchParams(location.search).get('ssn');

    const fetchData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/appointments/getPatientAppointments/${ssn}`);
          setAppointments(response.data);
        } catch (error) {
          console.error('An error occurred:', error);
        }
    };

    useEffect(() => {
    fetchData();
    }, []);

    return <div>

        <Header title= {"ToothHub"}/>
        <div style={{width: "50%", margin: "5% 20%"}}>
        <h3 style={{textAlign:"center"}}>Previous Appointments</h3>
        <br/>
        <table className="table table-striped" style={{textAlign:"center"}}>
                      <thead className="thead-light">
                        <tr>
                          
                          <th scope="col" >Date </th>
                          <th scope="col" >Time</th>
                          <th scope="col" >Treatment</th>
                          
                        </tr>
                      </thead>
                      <tbody>
                        {appointments.map(appointment => (
                          <tr key={appointment.id}>
                            <td>{appointment.date}</td>
                            <td>{appointment.time}</td>
                            <td>{appointment.therapy}</td>
             
                          </tr>
                      ))}
                      </tbody>
                </table>
        </div>
    </div>













}