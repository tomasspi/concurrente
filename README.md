# Trabajo Práctico Integrador Programación Concurrente 2018
## Enunciado

En este práctico se debe resolver el control de acceso a una playa de estacionamiento con 3 entradas
(calles) diferentes. En esta playa hay 2 pisos, y en cada piso pueden estacionar 30 autos. La playa
cuenta con 2 salidas diferentes y una única estación de pago (caja). En los accesos a la playa y en
los egresos existen barreras que deben modelarse.
La playa cuenta con lugares (3) donde los vehículos se detienen cuando quieren entrar (barrera), una
vez que ingresaron se les indica un piso y estacionan (puede ser piso 1 o piso 2). Se debe cuidar que
no se permita el ingreso (superar barrera) a más vehículos de los espacios disponibles totales.
Los autos que se retiran de la playa deben liberar un espacio del piso en que se encontraban
(diferenciar estacionamiento en cada piso). Cuando un vehículo se va a retirar puede optar por
salida a calle 1 ó salida a calle 2.
Luego debe abonar la estadía. El cobro de la estadía le lleva a un empleado promedio al menos 3
minutos. (Existe una sola caja)
En caso de que la playa esté llena, se debe encender un cartel luminoso externo que indica tal
situación.
El sistema controlador debe estar conformado por distintos hilos, los cuales deben ser asignados a
cada conjunto de responsabilidades afines en particular. Por ej. Ingreso de vehículos, manejo de
barreras, etc.
Debe realizar:

	1. La red de Petri que modela el sistema.
	2. Agregar las restricciones necesarias para evitar interbloqueos ni accesos cuando no hay lugar,\n mostrarlo con la 	herramienta elegida y justificarlo.
	3. Simular la solución en un proyecto desarrollado con la herramienta adecuada (explique porque eligió la herramienta 	usada).
	4. Colocar tiempo en las estación de pago caja (en la/s transición/es correspondiente/s).
	5. Hacer la tabla de eventos.
	6. Hacer la tabla de estados o actividades.
	7. Determinar la cantidad de hilos necesarios (justificarlo)
	8. Implementar dos casos de Políticas para:
		- Prioridad llenar de vehículos planta baja (piso 1) y luego habilitar el piso
		superior. Prioridad salida indistinta (caja).
		- Prioridad llenado indistinta. Prioridad salida a calle 2.
	9. Hacer el diagrama de clases.
	10. Hacer los diagramas de secuencias.
	11. Hacer el código.
	12. Hacer el testing.
