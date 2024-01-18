import {
    CssBaseline,
    List,
    ListItem,
    ListItemText,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import {useEffect, useState} from "react";
import axios from "axios";
import './Employee.css';
import {Link, useNavigate, useParams} from "react-router-dom";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import {assignProject, employeeApi, unassignProject} from "../utils/Constants.ts";
import {projectAll} from "../utils/Types.ts";


function EmployeeDetails() {


    const {id} = useParams();

    const [values, setValues] = useState({
        Id: id,
        firstName: '',
        lastName: '',
        phoneNumber: '',
    })


    const [project, setProject] = useState<projectAll[]>([])

    const [allProjects, setAllProjects] = useState<projectAll[]>([])

    const navigate = useNavigate();


    useEffect(() => {
        axios.get(`${employeeApi}` + id)
            .then(res => {
                setValues({
                    ...values,
                    firstName: res.data.firstName,
                    lastName: res.data.lastName,
                    phoneNumber: res.data.phoneNumber
                })
            }).catch(er => {
            console.log(er.response.data)
            navigate(`/Pages/ErrorPageNotFoundEmp/${id}`)
        })


    }, [id, values, navigate])

    useEffect(() => {
        axios.get(`${employeeApi}${id}/projects`)
            .then(res => {
                setProject(res.data)
            }).catch(er => {
            console.log(er)
        })
    }, [id]);

    useEffect(() => {
        axios.get(`${employeeApi}${id}/unassignedProject`)
            .then(res => {
                setAllProjects(res.data)

            }).catch(er => {
            console.log(er)
        })
    }, [id]);


    const handleAsign = (projectId: number) => {
        axios.post(`${assignProject}${id}`, {projectId: projectId})
            .then(res => {
                console.log(res.data)
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    const handleUnAsign = (ProjId: number) => {
        axios.delete(`${unassignProject}${id}/${ProjId}`, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }
        ).then((result) => {
            console.log(result.data);
            window.location.reload();
        }).catch(er => {
            console.log(er)

        })
    }


    return (
        <>
            <CssBaseline/>
            <Container sx={{bgcolor: "white", paddingTop: "40px"}} maxWidth={false} disableGutters>
                <TableContainer component={Paper}>
                    <Table className="table" sx={{minWidth: 1200}} aria-label="simple table">
                        <TableHead>
                            <TableRow sx={{'&:last-child td, &:last-child th': {border: 1}}}>
                                <TableCell><h2>ID</h2></TableCell>
                                <TableCell align="right"><h2>FirstName</h2></TableCell>
                                <TableCell align="right"><h2>LastName</h2></TableCell>
                                <TableCell align="right"><h2>PhoneNumber</h2></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            <TableRow sx={{border: 1}}>
                                <TableCell className="sticky-table-cell" align="left" key={values.Id}>
                                    <h3>{values.Id}</h3></TableCell>
                                <TableCell className="sticky-table-cell" align="right"><h3>{values.firstName}</h3>
                                </TableCell>
                                <TableCell className="sticky-table-cell" align="right"><h3>{values.lastName}</h3>
                                </TableCell>
                                <TableCell className="sticky-table-cell" align="right"><h3>{values.phoneNumber}</h3>
                                </TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>

                <h1 style={{backgroundColor: "grey"}}>Assigned Projects</h1>

                <List sx={{width: '100%', maxWidth: 1680, bgcolor: 'background.paper', color: "text.primary"}}>
                    {project.map((value) => (
                        <ListItem
                            key={value.idProject}
                            disableGutters
                        >
                            <ListItemText primary={`NAME: ${value.name}`}/>
                            <ListItemText primary={`HOURS: ${value.hours}`}/>
                            <TableCell className="sticky-table-cell" align="right"><Button
                                onClick={() => handleUnAsign(value.idProject)}
                                variant="contained"
                                sx={{width: "80%"}}
                            >UnAssign</Button>
                            </TableCell>
                            <TableCell className="sticky-table-cell" align="right"> <Link
                                to={`/Pages/ProjectDetails/${value.idProject}`}>
                                <Button variant="contained" sx={{width: "50%"}}>DETAILS</Button>
                            </Link>
                            </TableCell>
                        </ListItem>
                    ))}
                </List>

                <h1 style={{backgroundColor: "grey"}}>Unassigned Projects</h1>

                <List sx={{width: '100%', maxWidth: 1680, bgcolor: 'background.paper', color: "text.primary"}}>
                    {allProjects.map((value) => (
                        <ListItem
                            key={value.idProject}
                            disableGutters
                        >
                            <ListItemText primary={`PROJECT NAME: ${value.name}`}/>
                            <ListItemText primary={`PROJECT ID: ${value.id}`}/>
                            <TableCell className="sticky-table-cell" align="right"><Button
                                onClick={() => handleAsign(value.id)}
                                variant="contained"
                                sx={{width: "100%"}}
                            >ASSIGN</Button>
                            </TableCell>
                            <TableCell className="sticky-table-cell" align="right"> <Link
                                to={`/Pages/ProjectDetails/${value.id}`}>
                                <Button variant="contained" sx={{width: "30%"}}>DETAILS</Button>
                            </Link>
                            </TableCell>
                        </ListItem>
                    ))}
                </List>

            </Container>
        </>
    )
}

export default EmployeeDetails;