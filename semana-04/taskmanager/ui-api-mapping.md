# Mapeo de Contrato UI <-> API REST (Task Manager)

| Acción en UI (HTML) | Evento DOM | Método HTTP | Endpoint | Request Body (JSON) | Estado HTTP Esperado |
| **Cargar tareas** | `DOMContentLoaded` | `GET` | `/api/tasks` | *Ninguno* | `200 OK` |
| **Añadir tarea** | `onsubmit` (Form) | `POST` | `/api/tasks` | `{"title": "..."}` | `201 Created` / `200 OK` |
| **Completar tarea** | `onchange` (Checkbox)| `PUT` | `/api/tasks/{id}/complete` | *Ninguno* | `200 OK` |
| **Eliminar tarea** | `onclick` (Botón) | `DELETE` | `/api/tasks/{id}` | *Ninguno* | `200 OK` / `204 No Content` |
| **Buscar inexistente**| *Error spimulado* | `GET` | `/api/tasks/9999` | *Ninguno* | `404 Not Found` (JSON Error) |