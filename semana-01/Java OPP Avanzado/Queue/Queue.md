¿Por qué el array circular resuelve el problema de la implementación naive con array normal?
Porque con un array normal una vez llegamos a la última posicion si no está lleno, pero las posiciones del principio están
vacias tendriamos que mover todas las posiciones del array a una posicion antes cada vez que se quita un elemento. De esta forma
con el uso del módulo podemos conseguir que si la cola no está llena y tenemos que añadir un elemento cuando la última posicion 
es igual a la de la capacidad maxima del array, que su siguiente posicion sea el 0

¿Dónde usarías una pila y dónde una cola en una aplicación real?
La pila para guardar las acciones realizadas para que cuando realices control Z recuperes el progreso anterior de tu cambio.
La cola en cualquier sistema de venta de entradas cuando hay mucha gente te ponen en cola y como antes entras antes sales