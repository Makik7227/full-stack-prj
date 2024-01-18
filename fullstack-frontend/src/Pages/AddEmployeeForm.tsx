import axios from 'axios';
import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {CssBaseline, Table, TableBody, TableCell, TableRow, TextField} from "@mui/material";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import {employeeApi} from "../utils/Constants.ts";
import {person, response} from "../utils/Types.ts";

export const FormComp = () => {
    const initialState: person = {
        firstName: "",
        lastName: "",
        phoneNumber: ''
    };

    const inputStyle = {border: "2px solid black", height: 350, padding: 10};

    const [person, setPerson] = useState<person>(initialState);
    const [response, setResponse] = useState<response>();

    const navigate = useNavigate();

    const submitForm = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        axios.post<response>(`${employeeApi}`, person)
            .then((response) => {
                setResponse(response.data);
                console.log(response.data);
                navigate('/Pages/Employees');
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    const onChangeHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        const lettersOnlyValue = value.replace(/[^a-zA-Z]/g, '');
        setPerson((prev) => ({
            ...prev,
            [name]: lettersOnlyValue
        }));
    };

    const onChangeHandlerPhone = (event: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        const numericValue = value.replace(/[^0-9]/g, '');
        if (numericValue.length <= 9) {
            setPerson((prev) => ({
                ...prev,
                [name]: numericValue
            }));
        }
    };

    return (
        <>
            <CssBaseline/>
            <Container sx={{bgcolor: "white", paddingTop: "40px"}} maxWidth={false} disableGutters>
                <form onSubmit={submitForm}>
                    <Table style={inputStyle}>
                        <TableBody>
                            <TableRow sx={{height: "10vh"}}>
                                <TableCell>
                                    <TextField
                                        type="text"
                                        name="firstName"
                                        id="outlined-textarea"
                                        label="First Name"
                                        placeholder="ExampleName"
                                        multiline
                                        value={person.firstName}
                                        required
                                        onChange={onChangeHandler}
                                    />
                                </TableCell>
                            </TableRow>
                            <TableRow sx={{height: "10vh"}}>
                                <TableCell>
                                    <TextField
                                        type="text"
                                        name="lastName"
                                        id="outlined-textarea"
                                        label="Last Name"
                                        placeholder="ExampleLast"
                                        multiline
                                        value={person.lastName}
                                        required
                                        onChange={onChangeHandler}
                                    />
                                </TableCell>
                            </TableRow>
                            <TableRow sx={{height: "10vh"}}>
                                <TableCell>
                                    <TextField
                                        type="text"
                                        name="phoneNumber"
                                        id="outlined-textarea"
                                        label="Phone Number"
                                        placeholder="777666555"
                                        multiline
                                        required
                                        value={person.phoneNumber}
                                        onChange={onChangeHandlerPhone}
                                    />
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>
                                    <Button sx={{backgroundColor: 'wheat', color: "text.primary"}}
                                            type="submit">Submit</Button>
                                </TableCell>
                            </TableRow>
                            {response?.response && (
                                <TableRow>
                                    <TableCell colSpan={2}>{response.response}</TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </form>
            </Container>
        </>
    );
};

export default FormComp;
