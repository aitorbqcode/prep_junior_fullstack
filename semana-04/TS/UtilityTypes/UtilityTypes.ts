interface User {
    id: number;
    name: string;
    email: string;
    password: string;
    createdAt: Date;
}

// Partial: todos los campos opcionales (útil para updates parciales)
type UpdateUserDTO = Partial<User>;

// Pick: solo algunos campos
type UserPublic = Pick<User, "id" | "name" | "email">;

// Omit: todos los campos menos los indicados
type CreateUserDTO = Omit<User, "id" | "createdAt">;

// Readonly: todos los campos inmutables
// Cuando se habla de campos inmutables en este contexto (gracias al utilitario Readonly<User>),
// significa que una vez que ese objeto es creado, sus propiedades no se pueden modificar, alterar ni reasignar.
type UserReadonly = Readonly<User>;

// Record: diccionario tipado
// Sirve para asociar claves con valores de un tipo específico.
// En este caso, estamos creando un tipo llamado TasksByStatus que es un diccionario donde las claves son los estados de las tareas ("pending", "completed", "cancelled")
// y los valores son arrays de objetos Task. Es decir asociamos cada estado de tarea con una lista de tareas que pertenecen a ese estado.
type TasksByStatus = Record<"pending" | "completed" | "cancelled", Task[]>;