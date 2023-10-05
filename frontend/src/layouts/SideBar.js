import {React} from "react"
import patientsIcon from "../svgs/people-fill.svg"
import appointmentsIcon from "../svgs/table.svg"
import logoutIcon from "../svgs/box-arrow-left.svg"
import settingsIcon from "../svgs/user-settings.svg"
import { useNavigate, Navigate, useLocation } from "react-router-dom";


function SideBar(props)
{
    const dentistId = props.id;
    const navigate = useNavigate();
    const location = useLocation();    

    const handlePatientsClick = async(event) =>
    {
      
      event.preventDefault();
      //console.log(url);
      navigate(`/dentistpage/${props.id}/patientslist`, {state: {someData: props.id}});
          
      
        
      
      
      
    }

    const handleNavigateClick = () => {
        window.localStorage.clear()
        window.history.replaceState(null, null, "/");
        window.history.pushState(null, null, "/");
  
        // Now, navigate to the home page using the router

        navigate("/");
      };

    const handleAppointmentsClick = async(event) =>
    {
        event.preventDefault();
        navigate(`/dentistpage/${props.id}/appointmentslist`, {state: {someData: props.id}})
    }

    const handleLogoClick = async(event) =>
    {
        event.preventDefault();
        navigate(`/dentistpage/${props.id}`);
    }


    return<div className="sidebar" >
                
                    <a onClick={handleLogoClick} style={{marginBottom:"5px"}}>
                        <span className="logo">🦷</span>
                        <span className="nameTitle">ToothHub</span>
                    </a>

                    <a onClick={handlePatientsClick} >
                        <img src={patientsIcon} alt="Patients Icon" className="icon me-2" />
                        Show Patients

                    </a>

                    <a onClick={handleAppointmentsClick} >
                        <img src={appointmentsIcon} alt="Appointments Icon" className="icon me-2" />
                        Show Appointments
                    </a>

                    <a onClick={()=>{navigate(`/dentistpage/${props.id}/settings`)}}>
                        <img src={settingsIcon} alt="Settings Icon" className="icon me-2" />
                        Profile Settings
                    </a>

                    
                    <a  onClick={handleNavigateClick} className="logout">
                        <img src={logoutIcon} alt="Logout Icon" className="icon me-2" />
                        Log Out
                    </a> 
                    
                    
                       
                
                
            


                
                
        </div> 

}

export default SideBar;