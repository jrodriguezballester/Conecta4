# Reto 1
## CONECTA 4
### Requisitos

Crea el juego conecta cuatro. 

Requisitos:

* Tablero de 7x6 (7 en el eje "x" y 6 en el "y").
* Fichas Rojas y Amarillas. La primera partida la comienza siempre la Roja (la segunda la Amarilla, la tercera la Roja...).
* No hay que implementar una funcionalidad que te permita jugar contra la App. Se asume que jugarán dos personas reales alternándose.
* Al seleccionar la columna se coloca la ficha en la parte inferior.
* Guardar el número partidas ganadas de cada equipo mientras la App no se finaliza.
* Dos botones para reiniciar la partida en marcha y para resetear el contador de victorias y derrotas.
* Puedes añadirle todas las funcionalidades extra que consideres.

### Ideas 

* Hacer una o varias activities?
* No hacer menu
* primera activity presentación o reglas del juego
* segunda activity juego en si
  * xml (tablero,botones,fichas ...)
  * logica del juego: poner ficha, comprobar si gana, nuevo turno ...
  * logica restante: botones, reinicio, cuando gana ...

### Commit ViewBinding

Utilización de viewbinding para enlazar activity con el XML
MainActivity y su XML en pañales