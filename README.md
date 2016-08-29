# Electronica Don Pepe

TP final de la materia Programacion Avanzada II, UTN FRGP, 2do Cuatrimestre 2013

## Logica del Negocio
### Introduccion:
Electronica Don Pepe es una empresa de capitales argentinos dedicada a la venta minorista de electronica de consumo y componentes electronicos. Posee 3 sucursales, un deposito y aproximadamente 18 vendedores.

### Descripcion del problema:
A los vendedores de Electronica Don Pepe se les paga un sueldo fijo mas sumas
variables en funcion de las ventas que logren realizar durante el mes. Dichas sumas variables se clasifican en 2 grupos (Comisiones y Premios)
* Comisiones
    * Comision por ventas.
    * Comision por ventas de un producto en particular.
* Premios
    * Mejor vendedor del mes
    * Mejor vendedor de una campaña

Actualmente el calculo del sueldo se realiza manualmente de la siguiente forma:
* Luego de realizada la venta, el cajero que cobro la venta coloca la factura en bandejas correspondientes a cada vendedor.
* A fin de mes personal de RRHH cuenta las ventas, calcula las comisiones y los premios
* Luego de realizado el calculo de las comisiones y premios personal de RRHH carga los
conceptos dentro del sistema de payroll a fin de que sean acreditados junto con el sueldo a cada vendedor.


Dicho proceso suele ser motivo de quejas de los vendedores, ya que resulta comun que los Cajeros coloquen por error facturas en la bandeja de otro vendedor (implicandole una pérdida economica al vendedor). Por otro lado, personal de RRHH manifiesta que sumar una a una las facturas para luego liquidar las comisiones es un proceso
muy engorroso y poco productivo.

### Calculo de premios y comisiones
#### Detalle del calculo de las comisiones y premios

**Comision por ventas:** Es un adicional que perciben todos los vendedores en funcion de las ventas que hayan logrado durante el mes. Se calcula de acuerdo a la siguiente tabla:

    Entre 1 y 5 ventas => $ 200
    Entre 6 y 10 ventas => $ 400
    Entre 11 y 15 ventas => $ 700
    Mas de 15 ventas => $1000
    
(Ej. : Vendedor X tiene 14 ventas  => Su comision por ventas es de $700)

**Comision por ventas de un producto en particular:** Es un adicional que perciben todos los vendedores que hayan vendido un producto determinado. Para ciertos productos existe un adicional fijo por cada unidad vendida. Se calcula de acuerdo a la siguiente formula:

    Cantidad de ventas del producto * Adicional

Ej.: 

    * Vendedor X tiene 10 ventas de “Producto Z”
    * Adicional por venta “Producto Z” $6
    ==> Su comision por venta de un producto en particular es $60 (10 * $ 6)

**Mejor vendedor del mes:** Al vendedor que logre el mayor numero de ventas se le asigna un adicional de $2000

Ej.:
     
    * Vendedor X: 10 ventas
    * Vendedor Y: 6 ventas
    * Vendedor Z: 15 ventas 
    ==> El vendedor Z recibe el adicional de $2000

**Mejor vendedor de una campaña:** Al vendedor que logre el mayor numero de ventas de un producto en particular se le asigna un adicional de $1000

Ej.: 

    * Vendedor X: 10 ventas
    * Vendedor Y: 6 ventas
    * Vendedor Z: 15 ventas 
    ==> El vendedor Z recibe el adicional de $1000

### Aclaraciones
* Las comisiones aplican a todos los vendedores
* Los premios solo aplican a un vendedor (El que haya cumplido con los requisitos para obtenerlo)
* Los Premios y las Comisiones son compatibles entre sí, es decir un mismo vendedor 
puede cobrar todos los adicionales (o ninguno).
* Los montos de los adicionales son los vigentes a la fecha actual, sin embargo los mismos pueden ser ajustados.

### Objetivo de la solucion
Automatizar el calculo de los premios y comisiones a fin de poder reducir el numero de reclamos / errores.

### Objetivos específicos de la solucion
* Sistematizar la carga de las ventas.
* Automatizar el calculo de las comisiones y premios. 
* Ofrecerle a cada vendedor la posibilidad de poder controlar las ventas que lleva 
realizadas.
* Permitir a RRHH actualizar los montos de los adicionales.

### Alcance de la solucion:
Forma parte de la solucion:
* La carga de los datos iniciales de los vendedores.
* La carga de los datos iniciales de los productos.
* La carga de los datos iniciales de las comisiones y premios.

No formara parte de la solucion:
* La registracion contable de la venta.
* El control de inventario / existencias.
* El calculo del sueldo basico o de las deducciones por ley.

### Requerimientos Funcionales
* Permitir a los vendedores agregar ventas.
* Permitir al personal de RRHH obtener un reporte de las comisiones y premios por 
vendedor.
* Permitir a los vendedores verificar las ventas que llevan realizadas.
* Permitir a personal de RRHH actualizar los montos de los premios.
* Permitir la administracion de usuarios.

### Requerimientos No Funcionales
* La solucion debe implementar autenticacion de los usuarios.
* La solucion no puede utilizar procedimientos almacenados
