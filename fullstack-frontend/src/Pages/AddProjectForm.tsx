import axios from 'axios';
import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {CssBaseline, Table, TableBody, TableCell, TableRow, TextField} from "@mui/material";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import {projectApi} from "../utils/Constants.ts";
import {project} from "../utils/Types.ts";

export const FormCompProject = () => {


    interface response {
        response: string | "No Response"
    }

    const initialState: project = {
        id: 0,
        name: "",
    }
    const inputStyle = {border: "2px solid black", height: 180, "padding": 10}
    const [project, setProject] = useState<project>(initialState)
    const [response, setResponse] = useState<response>()

    const navigate = useNavigate()

    const sumbitForm = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        axios.post<response>(`${projectApi}/`, project)
            .then((response) => {
                setResponse(response.data)
                console.log(response.data)
                navigate('/Pages/Projects');

            })
            .catch(function (error) {
                console.log(error);
            });
    }


    const validateName = (input: string) => {
        // Allow only first letters and then numbers
        const regex = /^[A-Za-z][A-Za-z0-9]*$/;
        return regex.test(input);
    };

    const onChangeHandler = (event: React.ChangeEvent<HTMLInputElement>): void => {
        const {name, value} = event.target;
        console.log('dsdsdsaadsasdads');
        // Validate the input using validateName function
        if (validateName(value) || value === '') {
            setProject((prev) => ({
                ...prev,
                [name]: value
            }));
        }
    };

    return (
        <>
            <CssBaseline/>
            <Container sx={{bgcolor: "white", paddingTop: "40px"}} maxWidth={false} disableGutters>
                <form onSubmit={sumbitForm}>
                    <Table style={inputStyle}>
                        <TableBody>
                            <TableRow sx={{height: "10vh"}}><TableCell><TextField
                                required
                                type="text"
                                name="name"
                                value={project.name}
                                id="outlined-textarea"
                                label="Project Name"
                                placeholder="ExampleName"
                                multiline
                                onChange={onChangeHandler}/></TableCell></TableRow>
                            <TableRow sx={{height: "10vh"}}><TableCell><Button
                                sx={{backgroundColor: 'wheat', color: "text.primary", width: "100%"}}
                                type="submit">Submit</Button></TableCell></TableRow>
                            {response?.response &&
                                <TableRow><TableCell colSpan={2}>{response.response}</TableCell></TableRow>}


                        </TableBody>
                    </Table>
                </form>
            </Container>
        </>
    )
}
export default FormCompProject;