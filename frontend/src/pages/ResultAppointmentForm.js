import React from "react";
import { useLocation, useParams,useNavigate } from "react-router-dom";
import Header from "../layouts/Header";

import leftArrow from "../svgs/arrow-return.svg"

export default function ResultAppointmentForm(props)
{

    const {dentistId} = useParams();
    console.log(dentistId);
    const navigate = useNavigate();
    const location = useLocation();
    const date = new URLSearchParams(location.search).get('submittedDate');
    const time = new URLSearchParams(location.search).get('submittedTime');
    const result = new URLSearchParams(location.search).get('result');

    const navigateToDentistPage = () => {
      
        navigate(`/dentistpage/${dentistId}`);
      };

    return <div>

        <Header title= {"ToothHub"}/>

        <main>
        <br/>
        <br/>
            {result==="true"? <p style={{fontSize: "22px", textAlign: "center" , color: "aliceblue"}}>The appointment for date {date}  and time {time}<br/>
            was successfully registered in the system! <br/>
            <span style={{fontSize: "50px"}}>✔️</span></p> : 

            <p style={{fontSize: "22px", textAlign: "center" , color: "aliceblue"}}>The appointment with date {date}  and time {time}<br/>
            wasn't successfully registered in the system! <br/>
            <span style={{fontSize: "50px"}}> ❌</span></p>
            
            }
            

        <br/>
        
        <button type="button" className="btn btn-outline-secondary" style={{marginLeft: "40px", padding: "10px 20px",color:"#614BC3"}} onClick= {navigateToDentistPage} >
            <img src={leftArrow}/>
             Go Back 
        </button>
        
        
        </main>
        
    </div>




}