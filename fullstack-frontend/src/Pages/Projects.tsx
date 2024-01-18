import {
    CssBaseline,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow
} from "@mui/material";
import React, {useEffect, useState} from "react";
import axios from "axios";
import './Employee.css';
import Button from "@mui/material/Button";
import {Link, useNavigate} from "react-router-dom";
import Container from "@mui/material/Container";
import {projectApi} from "../utils/Constants.ts";
import {project} from "../utils/Types.ts";

function Projects() {


    const [project, setProject] = useState<project[]>([])
    const [page, setPage] = React.useState(0);
    const [rowPerPage] = useState(5);


    const handleChangePage = (_event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = () => {
        setPage(0);
    };
    const loadProjects = async () => {
        await axios.get(`${projectApi}`, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }
        ).then((result) => {
            console.log(result.data);
            setProject(result.data);
        })

    }

    useEffect(() => {
        loadProjects().then();
    }, []);


    const navigate = useNavigate();


    const deleteProject = async (id: number) => {

        await axios.delete(`${projectApi}/${id}`, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json;charset=UTF-8'
                }

            }
        ).then((result) => {
            console.log(result.data);
            setProject((prevProjects) => prevProjects.filter(project => project.id !== id));
        })
    }


    return (
        <>
            <CssBaseline/>
            <Container sx={{bgcolor: "white", paddingTop: "40px"}} maxWidth={false} disableGutters>
                <Button onClick={() => navigate("/Pages/AddProjectForm")} variant="contained"
                        sx={{bgcolor: 'lightskyblue', width: '20%', color: "text.primary"}}>ADD PROJECT</Button>
                <TableContainer component={Paper}>
                    <Table className="table" sx={{width: "100%"}} aria-label="simple table">
                        <TableHead>
                            <TableRow sx={{'&:last-child td, &:last-child th': {border: 1}}}>
                                <TableCell><>ID</>
                                </TableCell>
                                <TableCell align="center"><>Name</>
                                </TableCell>
                                <TableCell align="center"><>ACTIONS</>
                                </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {project
                                .slice(page * rowPerPage, page * rowPerPage + rowPerPage)
                                .map((project) => (
                                    <TableRow

                                        sx={{border: 1}}
                                    >


                                        <TableCell sx={{width: 500}} className="sticky-table-cell" align="left"
                                                   key={project.id}>{project.id}</TableCell>
                                        <TableCell sx={{width: 500}} className="sticky-table-cell" align="center">
                                            {project.name}</TableCell>
                                        <TableCell sx={{width: 500}} className="sticky-table-cell" align="right">
                                            <Link to={`/Pages/ProjectDetails/${project.id}`}>
                                                <Button variant="contained"
                                                        sx={{width: "30%", marginRight: "5px"}}>DETAILS</Button>
                                            </Link>
                                            <Link to={`/Pages/UpdateFormProject/${project.id}`}>
                                                <Button variant="contained"
                                                        sx={{width: "30%", marginRight: "5px"}}>UPDATE</Button>
                                            </Link>
                                            <Button onClick={() => deleteProject(project.id)} variant="contained"
                                                    sx={{width: "30%"}}>DELETE</Button>
                                        </TableCell>


                                    </TableRow>))}


                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[5]}
                    component="div"
                    count={project.length}
                    rowsPerPage={rowPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Container>
        </>
    )
}

export default Projects;