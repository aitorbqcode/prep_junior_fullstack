// Crea interfaces para User, Task, LoginRequest, LoginResponse.
interface User{
    id: number;
    email: string;
    name: string;
    password: string;
    tasks: Task[];
}

interface Task{
    id: number;
    title: string;
    isCompleted: boolean;
    userId: number;
}

interface LoginRequest{
    email: string;
    password: string
}

interface LoginResponse{
    token: String;
    user: User;
}

//Crea una función tipada createTask(title: string, user: User): Task.

function createTask(title: string, userId: number): Task {
    return {
        id: Math.random(),
        title,
        isCompleted: false,
        userId
    };
}

//Crea un tipo ApiResponse<T> genérico para envolver respuestas de la API: { data: T; status: number; message: string }.
type ApiResponse<T> = {
    data: T;
    status: number;
    message: string
}

//Crea una función handleApiResponse<T>(response: ApiResponse<T>): T | null que devuelva data si status === 200, o null si no.

function handleApiResponse<T>(response: ApiResponse<T>): T | null {
    if(response.status == 200){
        return response.data;
    }
    return null;
}

// Actualiza o crea nuevas interfaces/tipos para que una Tarea (Task)
// tenga una propiedad de prioridad ("baja" | "media" | "alta") y un estado ("pendiente" | "en_progreso" | "completada").
type taskPriority = "baja" | "media" | "alta";
type taskStatus = "pendiente" | "en_progreso" | "completada";

type taskWithStatusAndPriority = Task & {status: taskStatus} & {priority: taskPriority};

// Crea una función llamada searchTasks que reciba:
// Un array de tareas (Task[]).
// Un objeto de opciones de búsqueda opcional (?) que pueda contener: completed (boolean), priority (el literal que creaste antes) y userId (number).
// Pista: Usa un type o interface para las opciones de búsqueda y destructúralas dentro de la función. Debe devolver las tareas que coincidan con los filtros proporcionados.

type TaskFilterOptions = {
    completed?: boolean;
    priority?: "baja" | "media" | "alta";
    userId?: number;
};

function searchTasks(tasks: taskWithStatusAndPriority[], filters: TaskFilterOptions): Task[]{
    return tasks.filter(t => {
        if (t.isCompleted != undefined && filters.completed != t.isCompleted) return false;
        if (t.priority != undefined && t.priority != filters.priority) return false;
        if (t.userId != filters.userId) return false;
        return true;
    })
}

// Utiliza tu tipo ApiResponse<T> y tu función handleApiResponse que creaste antes para simular una llamada de red que te devuelve las tareas del usuario.
//Crea una función simulada fetchUserTasks(userId: number): ApiResponse<Task[]> que devuelva un objeto con status: 200, message: "Success", y un array de tareas simuladas.
// Luego, procesa esa respuesta usando handleApiResponse.