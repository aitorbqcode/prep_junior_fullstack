Ejercicios:


1. ¿Qué diferencia hay entre programación defensiva  y diseño por contrato? ¿Cuándo usarías cada enfoque?

Programación Defensiva ("No me fío de nadie"): El método asume que los datos vendrán mal, así que los comprueba y corrige sobre la marcha (con if o valores por defecto) para que el programa nunca explote.
Diseño por Contrato ("Las reglas son las reglas"): El método exige unos requisitos fijos (precondiciones). Si el cliente le pasa un dato incorrecto, rompe el contrato y el programa explota inmediatamente (throw exception).

Programación Defensiva:

Úsala fuera de tu control, donde los datos pueden venir con errores:

Formularios y JSONs que envía el usuario.

Llamadas a APIs externas de otras empresas.

Lectura de archivos y bases de datos.

Diseño por Contrato ➡ En el Interior

Úsala dentro de tu propio código (entre tus clases y servicios):

Si la capa exterior ya validó los datos, la lógica interna no necesita repetir los mismos if veinte veces.

Si algo falla aquí dentro, es un error del programador y quieres que explote rápido (Fail-Fast) en los test para arreglarlo antes de ir a producción.