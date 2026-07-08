¿Qué hace el proyecto?

Este proyecto sirve para tener un sistema de tareas, donde estas hay que añadirle un id, un nombre, una prioridad, si esta completada y al user que esta asociada.
Cada una de estas se asocia a un user al cual hay que añadirle su id, su nombre y email.
Al ejecutarlo puedes crear usuarios, crear tareas, marcar tareas como completadas y listas las tareas de un usuario.

¿Cómo ejecutarlo?
Para ejecutarlo, es importante que antes te hayas añadido el esquema en tu programa de base de datos. El esquema se encuentra en la carpeta SQL de este proyecto.
En la carpeta DataBase en el fichero DataBaseConnection te encontraras esto:

private static final String URL = "";
private static final String USER = "";
private static final String PASSWORD = "";

Donde debes añadir tus datos para sincronizarte con tu base de datos, en mi caso esta hecho para conectarme con pgAdmin, tenlo en cuenta
que otros programas pueden requerir otros datos para vincular el programa con la base de datos.

Finalmente una vez estructurado solo queda ir al archivo main y darle al play.

¿Por qué has estructurado el código así?
He estructurado el codigo siguiendo una estructura por capas de forma que quede ordenado con las capas siguientes:

Model: Tengo las clases de los objetos que manipulamos e usamos en este proyecto como son los User y Tasks

Repository: Sistemas que conectan con la base de datos los objetos de Model

Service: Se encarga de conectar la UI con los repositorios 

UI: Es la parte visual del programa donde te indica las acciones posibles a realizar que luego verás en pantalla