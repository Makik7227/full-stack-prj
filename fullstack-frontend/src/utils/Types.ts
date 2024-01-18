export interface project {
    id: number,
    name: string
}

export interface person {
    firstName: string
    lastName: string
    phoneNumber: string;
}

export interface response {
    response: string | "No Response"
}

export interface employee {
    id: number,
    firstName: string,
    lastName: string,
    phoneNumber: number
}

export interface projectAll {
    name: string
    id: number
    hours: number
    idProject: number
}

export interface emp {
    firstName: string,
    lastName: string,
    hours: number
    idEmployee: number,
    id: number,
    idProject: number
}