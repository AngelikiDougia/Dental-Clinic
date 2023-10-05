import {React,  useEffect, useState } from "react";
import { useLocation, useParams,useNavigate, createSearchParams } from "react-router-dom";
import SideBar from "../layouts/SideBar";
import axios from 'axios';

function PatientsList(props)
{
    const location = useLocation();
    const {passedId} = location.state?.someData
    const {dentistId} = useParams(passedId);
    const [patients, setPatients] = useState([]);
    
    const navigate = useNavigate();

    
    const fetchData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/patients/getAllPatients/${dentistId}`);
          setPatients(response.data);
        } catch (error) {
          console.error('An error occurred:', error);
        }
      };
  
     


    function navigateToPatientForm()
    {
      navigate(`/dentistpage/${dentistId}/patientForm?dentistId=${dentistId}`, {state: {someData: {dentistId}}});
    }


    const deletePatient = async (ssn) => {
      const selected_ssn = ssn;
      const selected_patient = patients.find(patient => patient.ssn ===selected_ssn);
      const confirmDelete = window.confirm(`Are you sure you want to delete the selected patient?\n(Name: ${selected_patient.name}\nLast name: ${selected_patient.surname}\nSsn: ${selected_patient.ssn})`);
      
      if (!confirmDelete) {
        // User clicked cancel, do nothing
        return;
      }
    
      try {
        await axios.delete(`http://localhost:8080/patients/deletePatient/${ssn}`);
        console.log('Patient deleted successfully');
        fetchData(); // Fetch the updated list after deletion
      } catch (error) {
        console.error('An error occurred while deleting:', error);
      }
    };

    function viewPatient(patient)
    {

      const queryParams = {ssn: patient.ssn};
      navigate({pathname: `/dentistpage/${dentistId}/patientslist/viewProfile`, search: `?${createSearchParams(queryParams)}`});

    }

    function editPatient(patient)
    {

      const queryParams = {ssn: patient.ssn};
      navigate({pathname: `/dentistpage/${dentistId}/patientslist/editProfile`, search: `?${createSearchParams(queryParams)}`});

    }
    
    useEffect(() => {
      fetchData();
    }, []);



    return <div >
                <SideBar id={dentistId}/> 
                
                
                  
              
                

                <main style={{textAlign: "center", height:"100%", width:"50%", marginLeft:"22rem", marginTop : "5rem",padding: "2px"}}>
                
                <button type="button" class="btn btn-secondary" style={{float:"right", marginBottom:"5px"}} onClick={navigateToPatientForm}>Add Patient</button>
                

                <table className="table table-striped">
                      <thead className="thead-light">
                        <tr>
                          
                          <th scope="col">FirstName</th>
                          <th scope="col">LastName</th>
                          <th score="col">View Details </th>
                          <th scope="col">Edit Details</th>
                          <th scope="col">Delete</th>
                        </tr>
                      </thead>
                      <tbody>
                        {patients.map(patient => (
                          <tr key={patient.ssn}>
                            <td>{patient.name}</td>
                            <td>{patient.surname}</td>
                            <td><button type="button" className="btn btn-outline-secondary" onClick={()=>viewPatient(patient)}>View</button></td>
                            <td><button type="button" className="btn btn-outline-secondary" onClick={()=>editPatient(patient)}>Edit</button></td>
                            <td><button type="button" className="btn btn-outline-secondary" onClick={()=>deletePatient(patient.ssn)}>Delete</button></td>
              {/* Add more cells for additional columns */}
                          </tr>
                      ))}
                      </tbody>
                </table>
                
                

 
                </main>

            </div>



}

export default PatientsList;