import {
    CssBaseline,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField
} from "@mui/material";
import {useEffect, useState} from "react";
import axios from "axios";
import './Employee.css';
import {Link, useNavigate, useParams} from "react-router-dom";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import {projectApi, projectHours} from "../utils/Constants.ts";
import {emp} from "../utils/Types.ts";

function ProjectDetails() {


    const {id} = useParams();

    const [values, setValues] = useState({
        Id: id,
        name: '',
        hours: 0
    })
    const navigate = useNavigate();

    const [emp, setEmp] = useState<emp[]>([])


    useEffect(() => {
        axios.get(`${projectApi}/` + id)
            .then(res => {
                setValues({...values, name: res.data.name, hours: res.data.hours})
            }).catch(er => {
            console.log(er.response.data);
            navigate(`/Pages/ErrorPageNotFound/${id}`);
        })

    }, [id, values, navigate])


    useEffect(() => {
        axios.get(`${projectApi}/${id}/byId`)
            .then(res => {
                setEmp(res.data)
                console.log(res.data)
            }).catch(er => {
            console.log(er)
        })
    }, [id]);


    const [hours, setHours] = useState('')

    const handleAsign = (empId: number) => {
        axios.put(`${projectHours}/${empId}/${id}`, {hours: parseInt(hours.toString())})
            .then(res => {
                console.log(res.data)
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error);
            });
    }


    return (
        <>
            <CssBaseline/>
            <Container sx={{bgcolor: "white", paddingTop: "40px", allignItems: "center"}} maxWidth={false}
                       disableGutters>
                <Box sx={{bgcolor: "grey", position: "relative"}}>
                    <Typography sx={{color: "text.primary"}} variant="h1" gutterBottom>
                        Name: {values.name}
                    </Typography>
                    <Typography sx={{color: "text.primary"}} variant="h1" gutterBottom>
                        Id:{values.Id}
                    </Typography>
                </Box>


                <h1 style={{backgroundColor: "grey"}}>Assigned Employees</h1>

                <TableContainer component={Paper}>
                    <Table className="table" sx={{minWidth: 1200}} aria-label="simple table">
                        <TableHead>
                            <TableRow sx={{'&:last-child td, &:last-child th': {border: 1}}}>
                                <TableCell><h2>ID</h2></TableCell>
                                <TableCell align="right"><h2>NAME</h2></TableCell>
                                <TableCell align="right"><h2>LASTNAME</h2></TableCell>
                                <TableCell align="right"><h2>HOURS</h2></TableCell>
                                <TableCell align="right"><h2>ACTIONS</h2></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {emp.map((emp) => (
                                <TableRow sx={{border: 1}}>
                                    <TableCell className="sticky-table-cell" align="left" key={emp.id}><h3>{emp.id}</h3>
                                    </TableCell>
                                    <TableCell className="sticky-table-cell" align="right"><h3>{emp.firstName}</h3>
                                    </TableCell>
                                    <TableCell className="sticky-table-cell" align="right"><h3>{emp.lastName}</h3>
                                    </TableCell>
                                    <TableCell className="sticky-table-cell" align="right">
                                        <h3>{emp.hours}</h3>
                                    </TableCell>
                                    <TableCell className="sticky-table-cell" align="right">
                                        <TextField type="number" name="hours" required={true}
                                                   sx={{marginRight: "40px", marginTop: "40px"}}
                                                   onChange={(e) => setHours(e.target.value)}/>
                                        <Button
                                            onClick={() => handleAsign(emp.id)}
                                            variant="contained"
                                            sx={{width: "50%", marginRight: "10px", marginBottom: "10px"}}
                                        >ADD HOURS</Button>
                                        <Link to={`/Pages/EmployeeDetails/${emp.id}`}><Button variant="contained"
                                                                                              sx={{
                                                                                                  width: "50%",
                                                                                                  marginRight: "10px"
                                                                                              }}>DETAILS</Button></Link></TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>


            </Container>
        </>

    )
}

export default ProjectDetails;