import React from "react";




function Card({details})
{

    

    const cardType = details.type;
    let cardTitle = (cardType=="appointment") ? "Appointment Details" : "Patient Details"; 

    let cardFirstSubtitle = (cardType=="appointment") ? "Patient Details" : "Personal Details"; 
    let cardSecondSubtitle = (cardType=="appointment") ? "" : "Contact Details";

    let firstRowTitle = "Name: ";

    let secondRowTitle = "Surname: ";
    
    let thirdRowTitle = "Birthdate: ";

    let forthRowTitle = "Gender: ";

    let fifthRowTitle = (cardType=="appointment")? "Date: " : "Telephone: ";

    let sixthRowTitle = (cardType=="appointment")? "Time: " : "Email: ";

    let seventhRowTitle = (cardType=="appointment")? "Duration: " : "Address: ";

    let eighthRowTitle = (cardType=="appointment") ? "Description: " : "";

    const marginTop = (cardType !== "appointment") ? "1%" : "5%";

    return(
        <div style={{marginLeft: "30%", marginTop: marginTop, backgroundColor: "#C8FFE0", marginRight:"30%", padding: "10px", borderRadius: "30px"}}>
       
        <h2 style={{color:"black", textAlign:"center"}}>{cardTitle}</h2>
        <hr />
        <h4>{cardFirstSubtitle}</h4>
        <hr />
        <p>{firstRowTitle} {details.firstRow} </p>
        <p>{secondRowTitle} {details.secondRow}</p>
        <p>{thirdRowTitle} {details.thirdRow}</p>
        <p>{forthRowTitle} {details.forthRow}</p>
        <hr />
        <h4>{cardSecondSubtitle}</h4>
        <hr />
        <p>{fifthRowTitle} {details.fifthRow} </p>
        <p>{sixthRowTitle} {details.sixthRow}</p>
        <p>{seventhRowTitle} {details.seventhRow}</p>
        <p>{eighthRowTitle} {(eighthRowTitle==="") ? "" : details.eighthRow}</p>
        
        
    </div>


    );



}

export default Card;