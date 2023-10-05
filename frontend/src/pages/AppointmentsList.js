import {React,  useEffect, useState } from "react";
import {  useParams, useNavigate, createSearchParams } from "react-router-dom";
import SideBar from "../layouts/SideBar";
import axios from 'axios';


function AppointmentsList(props)
{
    
    const {dentistId} = useParams();
    const [appointments, setAppointments] = useState([]);

    const [specificDate,setSpecificDate] = useState("");

    
    
    const navigate = useNavigate();

    const fetchData = async () => {

        try {
          let response;
          
          if (specificDate) {
            // If specificDate is defined, fetch appointments for that date
            response = await axios.get(
              `http://localhost:8080/appointments/getAppointmentsByDate/${dentistId}?date=${specificDate}`
            );
          } else {
            // If specificDate is not defined, fetch all appointments
            response = await axios.get(`http://localhost:8080/appointments/getAllAppointments/${dentistId}`);
          }
          setAppointments(response.data);
        } catch (error) {
          console.error('An error occurred:', error);
        }
      };

      const deleteAppointment = async (id) => {
        const selected_id = id;
        const selected_appointment = appointments.find(appointment => appointment.id === selected_id)
        const confirmDelete = window.confirm(`Are you sure you want to delete the appointment \nfor date: (${selected_appointment.date}) and time: ${selected_appointment.time}?`);
      
        if (!confirmDelete) {
          // User clicked cancel, do nothing
          return;
        }
      
        try {
          await axios.delete(`http://localhost:8080/appointments/deleteAppointment/${id}`);
          console.log('Appointment deleted successfully');
          fetchData(); // Fetch the updated list after deletion
        } catch (error) {
          console.error('An error occurred while deleting:', error);
        }
      };

      function navigateToAppointmentForm()
      {
        navigate(`/dentistpage/${dentistId}/appointmentForm?dentistId=${dentistId}`, {state: {someData: {dentistId}}});
      }
  
      function viewAppointment(appointment) //TODO
      {
  
        const queryParams = {appointmentId: appointment.id};
        navigate({pathname: `/dentistpage/${dentistId}/appointmentslist/viewAppointment`, search: `?${createSearchParams(queryParams)}`});
  
      }
  
      function editAppointment(appointment) //TODO
      {
  
        const queryParams = {appointmentId: appointment.id};
        navigate({pathname: `/dentistpage/${dentistId}/appointmentslist/editAppointment`, search: `?${createSearchParams(queryParams)}`});
  
      }

      useEffect(() => {
        fetchData();
      }, [specificDate]);
    return <div >
                <SideBar id={dentistId}/>  
                
                
                 
              
                

                <main style={{textAlign: "center", height:"100%", width:"50%", marginLeft:"22rem", marginTop : "5rem",padding: "2px"}}>
                
                <button type="button" class="btn btn-secondary" style={{float:"right", marginBottom:"5px"}} onClick={navigateToAppointmentForm}>Add Appointment</button>
                
                
                <input type="date"  placeholder="Filter By Date" name="date" onChange= {(event)=>setSpecificDate(event.target.value)} value= {specificDate} style={{float:"left", paddingLeft:"15px"}}/>
                
                
                <table className="table table-striped">
                      <thead className="thead-light">
                        <tr>
                          
                          <th scope="col">Date</th>
                          <th scope="col">Time</th>
                          <th score="col">View Details</th>
                          <th scope="col">Edit Details</th>
                          <th scope="col">Delete</th> 
                        </tr>
                      </thead>
                      <tbody>
                      {appointments.map(appointment => {
                          if (!appointment) {
                            // Skip rendering if appointment is null or undefined
                            return null;
                          }
                          return (
                            <tr key={appointment.id}>
                              <td>{appointment.date}</td>
                              <td>{appointment.time}</td>
                              <td><button type="button" className="btn btn-outline-secondary" onClick={() => viewAppointment(appointment)}>View</button></td>
                              <td><button type="button" className="btn btn-outline-secondary" onClick={() => editAppointment(appointment)}>Edit</button></td>
                              <td><button type="button" className="btn btn-outline-secondary" onClick={() => deleteAppointment(appointment.id)}>Delete</button></td>
                            </tr>
                          );
                        })}
                      </tbody>
                </table>
                
                
                

 
                </main>

            </div>



}

export default AppointmentsList;