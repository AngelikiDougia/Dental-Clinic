import {React,  useEffect, useState } from "react";
import {useNavigate, createSearchParams } from "react-router-dom";
import Header from "../layouts/Header";
import axios from 'axios';
import arrowLeft from "../svgs/box-arrow-left.svg"

function DentistList(props)
{
    
    const [dentists, setDentists] = useState([]);
    
    const navigate = useNavigate();

    
    const fetchData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/getAllDentists`);
          setDentists(response.data);
          
        } catch (error) {
          console.error('An error occurred:', error);
        }
      };
  
     


    function navigateToDentistForm()
    {
      navigate(`/adminpage/dentistForm`);
    }

    
    const deleteDentist = async (id) => {
      const selected_id = id;
      const selected_dentist = dentists.find(dentist => dentist.dentistId ===selected_id);
      const confirmDelete = window.confirm(`Are you sure you want to delete the selected dentist?\n(Name: ${selected_dentist.name}\nLast name: ${selected_dentist.surname})`);
      console.log("dentistId: ", id);
      if (!confirmDelete) {
        // User clicked cancel, do nothing
        return;
      }
    
      try {
        await axios.delete(`http://localhost:8080/deleteDentistAndNullify/${selected_id}`);
        console.log('Dentist deleted successfully');
        fetchData(); // Fetch the updated list after deletion
      } catch (error) {
        console.error('An error occurred while deleting:', error);
      }
    };

    

    const editDentist = async(id) =>
    {
      const selected_id = id;
      const queryParams = {dentistId: selected_id};
     
      navigate({pathname: `/adminpage/editDentistProfile`, search: `?${createSearchParams(queryParams)}`});

    };
    
    useEffect(() => {
      fetchData();
    }, []);



    return <div >
                
                
                <Header title = {"ToothHub"} />
                  
                <button className="btn btn-secondary" style={{float:"left", marginTop:"5px"}} onClick={() => {window.localStorage.setItem("loggedIn","false"); navigate(`/`)}}>
                <img src={arrowLeft} alt="Logout Icon" className="icon me-2" />
                Log Out 
                </button>

                <main style={{textAlign: "center", height:"100%", width:"50%", marginLeft:"22rem", marginTop : "5rem",padding: "2px"}}>
                
                <button type="button" className="btn btn-secondary" style={{float:"right", marginBottom:"5px"}} onClick={navigateToDentistForm}>Add Dentist</button>
                

                <table className="table table-striped">
                      <thead className="thead-light">
                        <tr>
                          
                          <th scope="col">FirstName</th>
                          <th scope="col">LastName</th>
                          <th scope="col">View/Edit Details</th>
                          <th scope="col">Delete</th>
                        </tr>
                      </thead>
                      <tbody>
                        {dentists.map(dentist => (
                          
                          <tr key={dentist.dentistId}>
                            <td>{dentist.name}</td>
                            <td>{dentist.surname}</td>
                            <td><button type="button" className="btn btn-outline-secondary" onClick={()=>editDentist(dentist.dentistId)}>Edit</button></td>
                            <td><button type="button" className="btn btn-outline-secondary" onClick={()=>deleteDentist(dentist.dentistId)}>Delete</button></td>
                            
                          </tr>
                      ))}
                      </tbody>
                </table>
                
                

 
                </main>

            </div>



}

export default DentistList;