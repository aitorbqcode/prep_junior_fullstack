interface Task {
    id: number;
    title: string;
    completed: boolean;
    userId: number;
}

class TaskApiClient {
    private baseUrl = "http://localhost:8080/api";
    private token: string;

    constructor(token: string) {
        this.token = token;
    }

    // Método auxiliar para no repetir la cabecera de autenticación
    private getHeaders(): Record<string, string> {
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${this.token}`
        };
    }

    // Realiza un fetch a la URL para pedir todas las tareas de un usuario. Por defecto, fetch utiliza el método HTTP GET.
    async getTasks(userId: number): Promise<Task[]> {
        const response = await fetch(`${this.baseUrl}/users/${userId}/tasks`, {
            headers: this.getHeaders()
        });
        // alida que el status HTTP sea un OK (200) con response.ok
        if (!response.ok){
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
        // Transforma el texto JSON recibido a un array de tareas con await response.json(), y finalmente lo devuelve
        return await response.json();
    }

    // Envía datos al backend para registrar una nueva tarea
    async createTask(userId: number, title: string): Promise<Task> {
        // Pasa un objeto de opciones a fetch. Especifica method: 'POST',
        // Añade el encabezado 'Content-Type': 'application/json' para avisar a Spring de que le está enviando un JSON
        // y transforma el objeto { title } a texto con JSON.stringify()
        const response = await fetch(`${this.baseUrl}/users/${userId}/tasks`, {
            method: 'POST',
            headers: this.getHeaders(),
            body: JSON.stringify({ title })
        });
        if (!response.ok){
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
        return await response.json();
    }

    // Actualiza el estado de una tarea existente.
    async completeTask(taskId: number): Promise<Task> {
        // Similar al POST, pero utiliza method: 'PUT' apuntando al ID de la tarea (/api/tasks/${taskId}).
        // Envía en el cuerpo el JSON JSON.stringify({ completed: true }) para cambiar el estado a completado.
        const response = await fetch(`${this.baseUrl}/tasks/${taskId}`, {
            method: 'PUT',
            headers: this.getHeaders(),
            body: JSON.stringify({ completed: true })
        });
        if (!response.ok){
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
        return await response.json();
    }

    // Solicita al servidor que borre una tarea concreta.
    async deleteTask(taskId: number): Promise<void> {
        // Pasa method: 'DELETE' con la URL de la tarea. Su tipo de retorno es Promise<void> porque,
        // a diferencia de los otros métodos, un borrado exitoso no devuelve ningún contenido en el cuerpo (Response 204 No Content)
        // simplemente confirma que no hubo error.
        const response = await fetch(`${this.baseUrl}/tasks/${taskId}`, {
            method: 'DELETE',
            headers: this.getHeaders()
        });
        if (!response.ok){
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
    }
}