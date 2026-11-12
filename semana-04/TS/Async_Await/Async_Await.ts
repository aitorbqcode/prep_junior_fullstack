// Promise básica
//El promise es un objeto que sirve para indicar que ahora mismo no tiene el resultado pero cuando los tenga te lo devolverá.
function fetchUser(id: number): Promise<Task> {
    // El promise recibe una función con dos parámetros: resolve y reject. resolve se llama cuando la operación es exitosa, reject cuando hay un error.
    return new Promise((resolve, reject) => {
        //El setTimeout simula una llamada a la API que tarda 100ms en responder. Dentro de la función, se llama a resolve o reject según el id.
        // Estado Pendiente (Pending): Acabas de hacer la llamada y el setTimeout de 100ms está contando. La promesa está en el aire esperando.
        setTimeout(() => {
            // Si el id es mayor que 0, resolvemos la promesa con un objeto Task simulado. Si no, rechazamos la promesa con un error.
            // Estado Resuelta / Éxito (Fulfilled): Han pasado los 100ms, el id era mayor que 0, y se ejecuta resolve(...). La promesa cumple su palabra y entrega la tarea simulada.
            if (id > 0) resolve({ id, title: "Tarea fake", completed: false, userId: 1 });
            // Estado Rechazada / Error (Rejected): El id era 0 o negativo, y se ejecuta reject(...). La promesa rompe su palabra porque algo fue mal y te devuelve un error ("ID inválido").
            else reject(new Error("ID inválido"));
            // El 100 es el tiempo de espera en milisegundos antes de ejecutar la función. Aquí simulamos una llamada a la API que tarda 100ms en responder.
        }, 100);
    });
}

// Async/await — forma más limpia de trabajar con Promises
// Async marca la función como asíncrona, lo que significa que devuelve una promesa.
// Await se usa dentro de funciones async para esperar a que una promesa se resuelva o rechace antes de continuar con la ejecución del código.
async function loadUserTasks(userId: number): Promise<Task[]> {
    try {
        // El await es la pieza clave para manejar código asíncrono de forma ordenada. Su nombre en inglés lo describe a la perfección: significa "espera".
        // Lo que hace es pausar la ejecución de la función hasta que la promesa se resuelva o rechace.
        // Durante la espera (esos 100ms): El programa no se queda congelado; la aplicación web puede seguir respondiendo a clics del usuario, animaciones, etc., mientras la red hace su trabajo.
        const user = await fetchUser(userId);
        // en Angular aquí harías: const tasks = await this.http.get<Task[]>(url).toPromise()
        return [user];
    } catch (error) {
        console.error("Error cargando tareas:", error);
        return [];
    }
}

// Promise.all: ejecutar varias promesas en paralelo
async function loadDashboard(userId: number) {
    const [tasks, profile] = await Promise.all([
        loadUserTasks(userId),
        fetchUser(userId)
    ]);
        return { tasks, profile };
    }




/*
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
*/
