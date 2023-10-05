import {React,  useEffect, useState } from "react";
import { useLocation,useNavigate } from "react-router-dom";
import axios from 'axios';
import Header from "../layouts/Header";



function DentistProfile()
{
  
  
  
  const navigate = useNavigate();
  const location = useLocation();
  const dentistId= new URLSearchParams(location.search).get("dentistId");
  console.log("dentistId: ", dentistId);

  const [dentist, setDentist] = useState({
      name: "",
      surname: "",
      username: "",
      password: "",
      email: "",
    
      telephone: "",
      address: ""
    });

    

    function handleChange(event)
    {
        const { name, value } = event.target;

        setDentist(prevValue => {
            return {
              ...prevValue,
              [name]: value
            };
          });
    }

    useEffect(() => {
        loadDentist();
      }, []);

    const [showPassword, setShowPassword] = useState(false);

    const loadDentist = async () => {
        const result = await axios.get(`http://localhost:8080/getDentist/${dentistId}`);
        setDentist(result.data);
    };
    
    const handleSubmit = async(event) =>
    {
      
      event.preventDefault();
      console.log("line 37 Login")
      try{
        
        const response = await axios.put(`http://localhost:8080/updateDentist/${dentistId}`,
        
        JSON.stringify(dentist),
          {
            headers: {"Content-Type": "application/json"},
            withCredentials:false
          }
        );
        const queryParams = {submittedName:dentist.name, submittedSurname:dentist.surname, result: ""};
        if(response.status===201)
        {
          queryParams.result = "true";
          console.log("Succesful submit");
          
          
        }else{
          queryParams.result = "fail";
          console.log("Unsuccesful submit");
        }


        navigate(`/adminpage`);
      }catch(err){
        console.log(err);
        
      };
      
      
    }

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
      }


   
    return (
      <div>
        <Header title={"ToothHub"}/>
        <main>
        <div className="centered-container">
              <div style={{ height:"100%", marginTop:"2%", backgroundColor:"#8DDFCB", borderRadius: "25px", padding:"10px 30px"}}>
                
                <h2 style={{textAlign:"center", paddingLeft:"20px",paddingRight:"20px",paddingTop:"10px", color:"black"}}>Update Dentist Form</h2>

                <form style={{textAlign:"left", paddingLeft:"20px",paddingRight:"20px", color:"black"}} onSubmit={handleSubmit}>
                    <label for="text" style={{color:"black"}}>Username:</label><br/>
                    <input type="text"  name="username"  value={dentist.username} style={{float:"left", color:"black"}} onChange={handleChange} required/>
                    <br/> 
                    <br/>
                    <br/>
                    <p style={{color:"red"}}>Click on "Show Encrypted Password" only if <br/>you intend to change it, else keep it hidden!</p>
                    
                    <button type="button" className="btn btn-outline-primary btn-sm"style={{display: "block", color:"#614BC3", borderBlockColor:"#614BC3"}}  onClick={togglePasswordVisibility}>
                        {showPassword ? "Hide Encrypted Password" : "Show Encrypted Password"}
                    </button>
                    <br/>
                    <br/>
                    {/* Password input field (conditionally rendered) */}
                    {showPassword && (
                        <div>
                        <label htmlFor="text" style={{ color: "black" }}>Password (<span style={{ color: "red" }}>Encrypted</span>):</label><br />
                        <input type="password" name="password" value={dentist.password} style={{ float: "left", color: "black" }} onChange={handleChange} required />
                        <br />
                        </div>
                    )}
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
                    <button  className="btn btn-outline-primary btn-sm" style={{display: "block", color:"#614BC3", borderBlockColor:"#614BC3"}} onClick = {()=>{navigate(`/adminpage`)}} >
                        Cancel
                    </button>

                </form>
            </div>
        </div>
        </main>
    </div>);




}

export default DentistProfile;