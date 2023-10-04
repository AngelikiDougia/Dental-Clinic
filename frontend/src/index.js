import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import {RouterProvider, createBrowserRouter} from "react-router-dom";

import Home from './pages/Home';
import PersonalPage from './pages/DentistPersonalPage';
import PatientsList from './pages/PatientsList';
import AppointmentsList from './pages/AppointmentsList';
import PatientProfile from './pages/PatientProfile';
import UpdatedPatientProfile from './pages/UpdatedPatientProfile';
import AppointmentDetails from './pages/AppointmentDetails';
import UpdatedAppointment from './pages/UpdatedAppointment';
import PatientForm from './pages/PatientForm';
import AppointmentForm from './pages/AppointmentForm';
import ResultForm from './pages/ResultForm';
import ResultAppointmentForm from './pages/ResultAppointmentForm';
import DentistForm from './pages/DentistForm';
import PatientDentalHistory from './pages/PatientDentalHistory';
import SettingsPage from './pages/SettingsPage';
import DentistList from './pages/DentistList';
import DentistProfile from './pages/DentistProfile';

//const token = window.localStorage.getItem("authorization");


const App = createBrowserRouter([
  {
    path: "/",
    element: <Home />,

  },
  {
    path: "dentistpage/:dentistId",
    element:  <PersonalPage />,
    
  },

  {
    
    path: "dentistpage/:dentistId/patientslist",
    element: <PatientsList />
    
  },
  
  {
    path:"dentistpage/:dentistId/appointmentslist",
    element: <AppointmentsList />
  },
  {
    path:"dentistpage/:dentistId/settings",
    element: <SettingsPage/>
  }
  ,

  {
    path:"dentistpage/:dentistId/patientForm",
    element: <PatientForm />
  },
  {
   
    path: "dentistpage/:dentistId/patientslist/viewProfile",
    element: <PatientProfile />

  },
  {
   
    path: "dentistpage/:dentistId/patientslist/viewPreviousAppointments",
    element: <PatientDentalHistory />

  }
  ,

  {
   
    path: "dentistpage/:dentistId/patientslist/editProfile",
    element: <UpdatedPatientProfile />

  },

  {
    path: "dentistpage/:dentistId/appointmentslist/viewAppointment",
    element: <AppointmentDetails />
  },

  {
    path: "dentistpage/:dentistId/appointmentslist/editAppointment",
    element: <UpdatedAppointment/>
  },

  {
    path: "dentistpage/:dentistId/appointmentForm",
    element: <AppointmentForm/>
  },

  {
    path: "dentistpage/:dentistId/patientForm/resultForm",
    element: <ResultForm />
  },

  {
    path:"dentistpage/:dentistId/appointmentForm/resultForm",
    element: <ResultAppointmentForm />
  }
  ,
  
  {
    path: "/adminpage",
    element: localStorage.getItem("loggedIn")==="true" ? <DentistList/>: <Home/>
  }
  
  ,

  {
    path: "/adminpage/dentistForm",
    element: <DentistForm />
  },

  {
    path: "/adminpage/editDentistProfile",
    element: <DentistProfile />
  }

  
]);





const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router= {App} />
  </React.StrictMode>
);

