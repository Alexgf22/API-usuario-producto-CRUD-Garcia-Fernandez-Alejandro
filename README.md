# API-usuario-producto-CRUD-Garcia-Fernandez-Alejandro

## ¿Sería deseable tener un producto repetido? Si es así, ¿como lo solucionarías?

* La decisión de permitir o no productos duplicados en mi base de datos
depende de las necesidades y lógica de mi aplicación. Si decido permitir
productos duplicados, es importante que ajuste mi lógica de negocio para
manejar adecuadamente estos casos.
Por ejemplo, si estoy vendiendo productos y cada unidad cuenta con
su propio ID, en ese caso permitir productos duplicados podría ser una
decisión correcta ya que me aseguraré de contar con un sistema de gestión
de inventario adecuado que tome en cuenta todas las unidades de un producto
al calcular el inventario total.
En general, la forma en que gestione la duplicación de productos debe estar
alineada con los requisitos y la lógica de mi aplicación.

## Estudiar si sería deseable usar DTO en el caso de tener que añadir Mappings para la relación entre usuario y producto

El uso de DTOs (Objetos de Transferencia de Datos) puede ser ventajoso
al gestionar la relación entre usuarios y productos.
Estos permiten prevenir bucles de referencia, disminuir la carga
de información, potenciar el rendimiento y garantizar la confidencialidad
de los datos. En síntesis, los DTOs proporcionan una capa de abstracción
útil para controlar cómo se representan y transfieren los datos entre
distintas partes de la aplicación.


### Razones para usar y para no usar DTO:

- Razones para usar DTOs:

  * Optimización de datos: Facilitan el envío de datos esenciales, disminuyendo la carga de información y optimizando el rendimiento.
  * Previenen ciclos de referencias: Eluden problemas al serializar objetos con relaciones bidireccionales.
  * Control de privacidad: Simplifican el control sobre qué información se comparte y cuál se mantiene confidencial.

- Razones para no usar DTOs:

  * Simplicidad y menor complejidad: En apps simples, la incorporación de DTOs puede complicar el proceso sin aportar beneficios significativos.
  * Sobrecarga de código y mantenimiento: Pueden generar más código y demandar esfuerzo adicional para su mantenimiento.
  * Posible duplicación de código: Puede haber cierta repetición si se aplican a varias entidades con propiedades parecidas.
  * Rendimiento en operaciones simples: La sobrecarga de usar DTOs puede no valer la pena en operaciones simples.






