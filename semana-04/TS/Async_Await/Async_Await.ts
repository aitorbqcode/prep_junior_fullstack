// Simulación de llamada a la API
// 1. Marca la función como asíncrona. Promete devolver Task[] en el futuro.
async function fetchTasks(userId: number): Promise<Task[]> {

    // 2. Hace la petición HTTP y 'await' ESPERA a que el servidor envíe los encabezados de respuesta
    const response = await fetch(`http://localhost:8080/api/users/${userId}/tasks`);

    // 3. Comprueba si el código de estado HTTP fue exitoso (200-299)
    if (!response.ok) {
        throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    // 4. 'await' ESPERA a que el cuerpo de la respuesta se descargue por completo y se transforme de JSON a objeto JS
    const data: Task[] = await response.json();

    // 5. Devuelve los datos limpios
    return data;
}

// Manejo de errores con async/await
async function loadUserTasks(userId: number): Promise<Task[] | null> {
    try {
        return await fetchTasks(userId);
    } catch (error) {
        console.error("Error cargando tareas:", error);
        return null;
    }
}