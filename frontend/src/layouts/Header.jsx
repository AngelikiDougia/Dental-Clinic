import React from "react";

function Header(props)
{
    return (<header>
        
        <h1>
            <span className="logo">🦷</span> 
            <span className="nameTitle">{props.title}</span> 
        </h1>
        
    </header>);

}

export default Header;
