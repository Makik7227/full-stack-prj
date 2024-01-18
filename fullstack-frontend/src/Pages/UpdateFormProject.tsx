import React, {ChangeEvent, useEffect, useRef, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import {CssBaseline, Table, TableBody, TableCell, TableRow, TextField} from "@mui/material";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import axios from 'axios';
import {projectApi} from "../utils/Constants.ts";

export const FormCompUpdateProject = () => {
    const inputStyle = {border: "2px solid black", height: 180, padding: 10};

    const {id} = useParams();
    const [values, setValues] = useState({
        Id: id,
        name: '',
    });
    const valuesRef = useRef(values);

    useEffect(() => {
        // Update the ref when values change
        valuesRef.current = values;
    }, [values]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await axios.get(`${projectApi}/` + id);
                // Access valuesRef.current instead of values directly
                setValues({...valuesRef.current, name: res.data.name});
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData().then();
    }, [id]);

    const navigate = useNavigate();

    const validateName = (input: string) => {
        // Allow only first letters and then numbers
        const regex = /^[A-Za-z][A-Za-z0-9]*$/;
        return regex.test(input);
    };

    const handleNameChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {value} = e.target;
        if (validateName(value) || value === '') {
            setValues({...values, name: value});
        }
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        axios.put(`${projectApi}/` + id, values)
            .then(res => {
                console.log(res.data);
                navigate(`/Pages/Projects`);
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    return (
        <>
            <CssBaseline/>
            <Container sx={{bgcolor: "white", paddingTop: "40px"}} maxWidth={false} disableGutters>
                <form onSubmit={handleSubmit}>
                    <Table style={inputStyle}>
                        <TableBody>
                            <TableRow>
                                <TableCell>
                                    <TextField
                                        type="text"
                                        name="Project Name"
                                        id="outlined-textarea"
                                        label="Project Name"
                                        placeholder="ExampleName"
                                        required
                                        multiline
                                        value={values.name}
                                        onChange={handleNameChange}
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

export default FormCompUpdateProject;
