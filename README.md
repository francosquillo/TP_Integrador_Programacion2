# TP_Integrador_Programacion2
## -Creacion y configuracion de base de datos

Para la conexion a la base de datos y el correcto funcionamiento del codigo hay que tener instalado el xampp, las extensiones de visual studio code "Database client JDBC" y "extension pack for java" y el archivo de la carpeta "db" llamado "pedidos_db.sql".

Si tenemos una base de datos local en la computadora hay que meterse a los servicios de la computadora para desactivarla y luego reiniciar la computadora para que no se genere un problema con el xampp.

Si ya se detuvo el servicio de la computador o no fue necesario tenermos que activar los servidores de apache y mysql en el xampp si no ocurre ningun error le damos a la opcion de "admin", eso va a abrir una pagina en "phpMyadmin" donde tenemos que irnos a la parte de "importar" y ahi cargamos el archivo "pedidos_db.sql".

Una vez hecho eso vamos al visual studio code y a la izquierda deberia aparecer una pestaña llamada "database" una vez entramos ahi tocamos "create connection" y en los campos ponemos

Host=127.0.0.1

Ususario=root

Password=*hay que dejarla vacia*

port=3306

Luego si todo salio bien el codigo ya deberia poder andar perfectamente

## -Guia de ejecucion
Al ejecutarse el codigo se abrira un menu con 5 opciones
1-Gestion de categorias
2-Gestion de productos
3-Gestion de usuarios
4-Gestion de pedidos
0-Salir

Al seleccionar cualquier opcion excepto salir se abrira otro menu como este:
1-Listar
2-Crear
3-Editar
4-Eliminar

Si se selecciona la opcion 1:
Se mostraran todos los objetos correspondientes a la categoria elegida (categoria, producto, ususario, pedido)

Si se selecciona la opcion 2:
Se le pedira al ususario ingresar los atributos de la categoria elegida y si en algun atributo se ingresa algun dato no valido se cancela la creacion

Si se selecciona la opcion 3:
Se le pedira al usuario que ingrese el id del objeto a modificar y se le pedira al usuario que ingrese los nuevos atributos

Si se selecciona la opcion 4:
Se le pedira al ususario que ingrese el id del objeto a borrar y el objeto sera borrado

## -Datos de prueba
-Categorias

Bebidas, Bebidas frías y calientes

Plato principal, Plato fuerte de la comida

Postre, Refrigerio dulce

-Productos

Coca Cola 500ml, 1500, Gaseosa, 100

Helado, 200, Helado chico, 150

Hamburguesa simple, 300, Hamburgesa con queso, 300
-Usuarios

Augusto, ingrassia, admin@aethergames.com, 2615555555, 123456, ADMIN

Hernesto, Martinez, HerMartinez@aethergames.com, 2613253492, Contraseña, ADMIN

Estela, Rios, RiosEs@aethergames.com, 2614937264, Rios09, ADMIN

-Pedidos

2026-06-15, PENDIENTE, 1500, EFECTIVO

2026-06-01, CANCELADO, 3000, TRANSFERENCIA

2026-02-08, CONFIRMADO, 2250, TARJETA

## -Link video
