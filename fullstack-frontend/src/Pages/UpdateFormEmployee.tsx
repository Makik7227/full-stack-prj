import axios from 'axios';
import React, {ChangeEvent, useEffect, useRef, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import {CssBaseline, Table, TableBody, TableCell, TableRow, TextField} from "@mui/material";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import {employeeApi} from "../utils/Constants.ts";

export const FormCompUpdateEmp = () => {
    const inputStyle = {border: "2px solid black", height: 350, padding: 10};

    const {id} = useParams();
    const [values, setValues] = useState({
        Id: id,
        firstName: '',
        lastName: '',
        phoneNumber: ''
    });

    const valuesRef = useRef(values);

    useEffect(() => {
        valuesRef.current = values;
    }, [values]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await axios.get(`${employeeApi}` + id);
                setValues({
                    ...valuesRef.current,
                    firstName: res.data.firstName,
                    lastName: res.data.lastName,
                    phoneNumber: res.data.phoneNumber
                });
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData().then();
    }, [id]);

    const navigate = useNavigate();

    const validateName = (input: string) => {
        const regex = /^[A-Za-z]+$/;
        return regex.test(input);
    };

    const handleNameChange = (name: string, e: ChangeEvent<HTMLInputElement>) => {
        const {value} = e.target;

        if (validateName(value) || value === '') {
            setValues({...values, [name]: value});
        }
    };

    const handlePhoneNumberChange = (e: ChangeEvent<HTMLInputElement>): void => {
        const {name, value} = e.target;
        const numericValue = value.replace(/[^0-9]/g, '');

        if (numericValue.length <= 9 || numericValue === '') {
            setValues((prev) => ({
                ...prev,
                [name]: numericValue
            }));
        }
    };

    const handleSubmit = (e: React.ChangeEvent<HTMLFormElement>) => {
        e.preventDefault();
        axios.put(`${employeeApi}` + id, values)
            .then(res => {
                console.log(res.data);
                navigate(`/Pages/Employees`);
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    return (
        <>
            <CssBaseline/>
            <CssBaseline/>
            <Container sx={{bgcolor: "white", paddingTop: "40px"}} maxWidth={false} disableGutters>
                <form onSubmit={handleSubmit}>
                    <Table style={inputStyle}>
                        <TableBody>
                            <TableRow>
                                <TableCell>
                                    <TextField
                                        type="text"
                                        name="firstName"
                                        id="outlined-textarea"
                                        label="First Name"
                                        placeholder="ExampleFirst"
                                        multiline
                                        value={values.firstName}
                                        onChange={(e) => handleNameChange('firstName', e as ChangeEvent<HTMLInputElement>)}
                                    />
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>
                                    <TextField
                                        type="text"
                                        name="lastName"
                                        id="outlined-textarea"
                                        label="Last Name"
                                        placeholder="ExampleLast"
                                        multiline
                                        value={values.lastName}
                                        onChange={(e) => handleNameChange('lastName', e as ChangeEvent<HTMLInputElement>)}
                                    />
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>
                                    <TextField
                                        type="text"
                                        name="phoneNumber"
                                        id="outlined-textarea"
                                        label="Phone Number"
                                        placeholder="777666555"
                                        multiline
                                        value={values.phoneNumber}
                                        onChange={(e) => handlePhoneNumberChange(e as ChangeEvent<HTMLInputElement>)}
                                    />
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>
                                    <Button sx={{backgroundColor: 'wheat', color: "text.primary"}}
                                            type="submit">Submit</Button>
                                </TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </form>
            </Container>
        </>
    );
};

export default FormCompUpdateEmp;
