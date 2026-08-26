// Crea interfaces para User, Task, LoginRequest, LoginResponse.
interface Task {
    id: number;
    title: string;
    completed: boolean;
    user: User;
}

interface User {
    id: number;
    email: string;
    name: string;
    password: string;
    tasks: Task[];
}

interface LoginRequest {
    email: string;
    password: string;
}

interface LoginResponse {
    token: string;
    user: User;
}

//Crea una función tipada createTask(title: string, user: User): Task.
function createTask(title: string, user: User): Task {
    return {
        id: Math.random(),
        title,
        completed: false,
        user
    };
}

//Crea un tipo ApiResponse<T> genérico para envolver respuestas de la API: { data: T; status: number; message: string }.
type ApiResponse<T> = {
    data: T;
    status: number;
    message: string;
};

//Crea una función handleApiResponse<T>(response: ApiResponse<T>): T | null que devuelva data si status === 200, o null si no.
function handleApiResponse<T>(response: ApiResponse<T>): T | null {
    if (response.status === 200) {
        return response.data;
    }   else {
        return null;
    }
}