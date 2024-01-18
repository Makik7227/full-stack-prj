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
import {employeeApi} from "../utils/Constants.ts";
import {employee} from "../utils/Types.ts";

function Employee() {

    const [page, setPage] = React.useState(0);
    const [employees, setEmployee] = useState<employee[]>([])
    const [rowPerPage] = useState(5);

    const loadEmployee = async () => {
        await axios.get(`${employeeApi}`, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }
        ).then((result) => {
            console.log(result.data);
            setEmployee(result.data);
        })

    }

    const handleChangePage = (_event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = () => {
        setPage(0);
    };

    useEffect(() => {
        loadEmployee().then();

    }, []);

    const deleteEmployee = async (id: number) => {

        await axios.delete(`${employeeApi}${id}`, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json;charset=UTF-8'
                }

            }
        ).then((result) => {
            console.log(result.data);
            setEmployee((prevEmployee) => prevEmployee.filter(employee => employee.id !== id));
        })
    }

    const navigate = useNavigate();


    return (
        <>
            <CssBaseline></CssBaseline>
            <Container maxWidth="xl" sx={{bgcolor: "white", paddingTop: "20px", width: "100%"}}>
                <Button onClick={() => navigate("/Pages/AddEmployeeForm")} variant="contained"
                        sx={{bgcolor: 'lightskyblue', color: "text.primary"}}>ADD EMPLOYEE</Button>
                <Table className="table" sx={{width: "100%"}} aria-label="simple table">

                    <TableContainer component={Paper}>
                        <TableHead>
                            <TableRow sx={{'&:last-child td, &:last-child th': {border: 1}}}>

                                <TableCell>Id</TableCell>
                                <TableCell>First name</TableCell>
                                <TableCell>Last name</TableCell>
                                <TableCell>Phone</TableCell>
                                <TableCell>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {employees
                                .slice(page * rowPerPage, page * rowPerPage + rowPerPage)
                                .map((employee) => {
                                    return (<TableRow

                                            sx={{border: 1}}
                                        >


                                            <TableCell sx={{width: 500}} className="sticky-table-cell" align="left"
                                                       key={employee.id}>{employee.id}</TableCell>
                                            <TableCell sx={{width: 500}} className="sticky-table-cell" align="right">
                                                {employee.firstName}</TableCell>
                                            <TableCell sx={{width: 500}} className="sticky-table-cell" align="right">
                                                {employee.lastName}</TableCell>
                                            <TableCell sx={{width: 500}} className="sticky-table-cell" align="right">
                                                {employee.phoneNumber}</TableCell>
                                            <TableCell sx={{width: 500}} className="sticky-table-cell"
                                                       align="right">


                                                <Link to={`/Pages/EmployeeDetails/${employee.id}`}><Button
                                                    variant="contained"
                                                    sx={{width: "30%", marginRight: "5px"}}>DETAILS</Button></Link>
                                                <Link to={`/Pages/UpdateFormEmployee/${employee.id}`}><Button
                                                    variant="contained"
                                                    sx={{width: "30%", marginRight: "5px"}}>UPDATE</Button></Link>

                                                <Button onClick={() => deleteEmployee(employee.id)}
                                                        variant="contained"
                                                        sx={{width: "30%"}}>DELETE</Button>
                                            </TableCell>

                                        </TableRow>
                                    )
                                })}
                        </TableBody>
                    </TableContainer>
                    <TablePagination
                        rowsPerPageOptions={[5]}
                        component="div"
                        count={employees.length}
                        rowsPerPage={rowPerPage}
                        page={page}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                    />
                </Table>
            </Container>


        </>
    )
}

export default Employee;