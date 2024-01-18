import {CssBaseline,} from "@mui/material";

import './Employee.css';
import {Link, useParams} from "react-router-dom";
import Button from "@mui/material/Button";


function ErrorPageNotFoundEmp() {

    const {id} = useParams();


    return (
        <>
            <CssBaseline/>
            <h1>EMPLOYEE WITH ID: {id} WAS NOT FOUND</h1>
            <Link to={'/Pages/Employees'}>
                <Button variant="contained"
                        sx={{width: "30%"}}>
                    BACK
                </Button>
            </Link>

        </>
    )
}

export default ErrorPageNotFoundEmp;