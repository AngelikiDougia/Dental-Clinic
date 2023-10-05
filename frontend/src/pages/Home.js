import {React, useState} from "react";
import Login from "../layouts/Login"
import Header from "../layouts/Header";
import AdministrationLogin from "../layouts/AdministrationLogin";



export default function Home()
{

    const [administrationLogin,setAdministrationLogin] = useState(false);
    


    return (
        <div>
        <Header title = {"ToothHub"} />
        <div className="home-container">
          
          
            <div className="quote child">
              <p>"Making the <br/>
              world a<br/>
              better <br />
              place, <br />
              One smile <br/>
              at a time."
              </p>
            </div>
            <div className="container-login child">
                <h1 className="greeting">Welcome back! </h1>
                {administrationLogin ? <AdministrationLogin/> :<Login />}
                {administrationLogin ? <a style={{color:"aliceblue"}}  onClick={()=>{setAdministrationLogin(!administrationLogin)}}><u>Login as a dentist?</u></a>
                    :<a style={{color:"aliceblue"}}  onClick={()=>{setAdministrationLogin(!administrationLogin)}}><u>Create a new dentist account? (Login as administrator)</u></a>}
            </div>
            
        </div>
        <footer>©️ {new Date().getFullYear()} Developed by Aggeliki Dougia</footer>
        </div>


    )
};