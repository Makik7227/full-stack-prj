import './App.css'
import {Route, Routes} from "react-router-dom";
import Employee from "./Pages/Employee.tsx";
import ResponsiveAppBar from "./Sections/Navbar.tsx";
import Projects from "./Pages/Projects.tsx";
import FormComp from "./Pages/AddEmployeeForm.tsx";
import FormCompProject from "./Pages/AddProjectForm.tsx";
import FormCompUpdateEmp from "./Pages/UpdateFormEmployee.tsx";
import FormCompUpdateProject from "./Pages/UpdateFormProject.tsx";
import EmployeeDetails from "./Pages/EmployeeDetails.tsx";
import ProjectDetails from "./Pages/ProjectDetails.tsx";
import ErrorPageNotFound from "./Pages/ErrorPageNotFound.tsx";
import ErrorPageNotFoundEmp from "./Pages/ErrorPageNotFoundEmp.tsx";

function App() {

    return (
        <>
            <ResponsiveAppBar/>
            <Routes>
                <Route path="/" element={<Employee/>}/>
                <Route path="/Pages/Employees" element={<Employee/>}/>
                <Route path="/Pages/Projects" element={<Projects/>}/>
                <Route path="/Pages/AddEmployeeForm" element={<FormComp/>}/>
                <Route path="/Pages/AddProjectForm" element={<FormCompProject/>}/>
                <Route path='/Pages/UpdateFormEmployee/:id' element={<FormCompUpdateEmp/>}/>
                <Route path='/Pages/UpdateFormProject/:id' element={<FormCompUpdateProject/>}/>
                <Route path='/Pages/EmployeeDetails/:id' element={<EmployeeDetails/>}/>
                <Route path='/Pages/ErrorPageNotFound/:id' element={<ErrorPageNotFound/>}/>
                <Route path='/Pages/ErrorPageNotFoundEmp/:id' element={<ErrorPageNotFoundEmp/>}/>

                <Route path='/Pages/ProjectDetails/:id' element={<ProjectDetails/>}/>
            </Routes>
        </>

    )
}

export default App
