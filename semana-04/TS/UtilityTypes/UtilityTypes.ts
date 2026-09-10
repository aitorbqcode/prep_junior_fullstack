interface Task {
    id: number;
    title: string;
    completed: boolean;
    userId: number;
}

// Partial<T>: hace todos los campos opcionales (útil para updates)
type TaskUpdate = Partial<Task>;
// Resultado: { id?: number; title?: string; completed?: boolean; userId?: number }

// Required<T>: hace todos los campos obligatorios
type FullTask = Required<Task>;

// Pick<T, K>: selecciona solo algunos campos
type TaskSummary = Pick<Task, "id" | "title">;

// Omit<T, K>: elimina algunos campos
type TaskCreateDTO = Omit<Task, "id">;  // para crear, no tienes id todavía

// Record<K, V>: objeto con claves K y valores V
type TasksByStatus = Record<"pendiente" | "completada", Task[]>;