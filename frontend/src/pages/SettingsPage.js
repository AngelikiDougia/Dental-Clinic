import {React,  useEffect, useState } from "react";
import { useParams,useNavigate } from "react-router-dom";
import SideBar from "../layouts/SideBar";
import axios from 'axios';



export default function SettingsPage()
{

    const {dentistId} = useParams();
    const navigate = useNavigate();
    const [dentist, setDentist] = useState({
        name: "",
        surname: "",
        email: "",
        telephone: "",
        address: ""
    });

    useEffect(() => {
        loadProfile();
      }, []);

      

    const loadProfile = async () => {
        const result = await axios.get(`http://localhost:8080/getDentist/${dentistId}`);
        setDentist(result.data);
    };

    const handleSubmit  = async (event) =>{
        
        event.preventDefault();
        await axios.put(`http://localhost:8080/updateDentist/${dentistId}`, dentist);
        navigate(`/dentistpage/${dentistId}`);
    
            
        
    }

    const handleChange = (event) => {
        setDentist({ ...dentist, [event.target.name]: event.target.value });
    };


    return (
    <div>
        <SideBar id={dentistId}/> 
        <main>
        <div className="centered-container">
              <div style={{ height:"100%", marginTop:"2%", backgroundColor:"#8DDFCB", borderRadius: "25px", padding:"10px 30px"}}>
                
                <h2 style={{textAlign:"center", paddingLeft:"20px",paddingRight:"20px",paddingTop:"10px", color:"black"}}>Personal Details</h2>

                <form style={{textAlign:"left", paddingLeft:"20px",paddingRight:"20px", color:"black"}} onSubmit={handleSubmit}>
                    <label for="text" style={{color:"black"}}>Name:</label><br/>
                    <input type="text"  name="name"  value={dentist.name} style={{float:"left", color:"black"}} onChange={handleChange}/>
                    <br/> 
                    <label for="text" style={{color:"black"}}>Lastname:</label><br/>
                    <input type="text"  name="surname"  value={dentist.surname} style={{float:"left", color:"black"}} onChange={handleChange}/>
                    <br/> 
                    <label for="text" style={{color:"black"}}>Email:</label><br/>
                    <input type="text"  name="email"  value={dentist.email} style={{float:"left", color:"black"}} onChange={handleChange}/>
                    <br/> 
                    <label for="text" style={{color:"black"}}>Telephone:</label><br/>
                    <input type="text"  name="telephone"  value={dentist.telephone} style={{float:"left", color:"black"}} onChange={handleChange}/>
                    <br/> 
                    <label for="text" style={{color:"black"}}>Address:</label><br/>
                    <input type="text"  name="address"  value={dentist.address} style={{float:"left", color:"black"}} onChange={handleChange}/>
                    <br/> 

                    <button type="submit" className="btn btn-outline-primary btn-sm" style={{display: "block", marginBottom: "8px", color:"#614BC3", borderBlockColor:"#614BC3"}}>
                        Save
                    </button>
                    <button  className="btn btn-outline-primary btn-sm" style={{display: "block", color:"#614BC3", borderBlockColor:"#614BC3"}} onClick = {()=>{navigate(`/dentistpage/${dentistId}/`)}} >
                        Cancel
                    </button>

                </form>
            </div>
        </div>
        </main>
    </div>)
}