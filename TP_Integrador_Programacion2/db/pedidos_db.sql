-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-06-2026 a las 04:17:31
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pedidos_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL,
  `eliminado` tinyint(1) NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT current_timestamp(),
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_pedido`
--

CREATE TABLE `detalle_pedido` (
  `id_detalle` int(11) NOT NULL,
  `eliminado` tinyint(1) NOT NULL,
  `createdAt` datetime NOT NULL,
  `cantidad` int(11) NOT NULL,
  `subtotal` double NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `id_pedido` int(11) NOT NULL,
  `eliminado` tinyint(1) NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `fecha` date NOT NULL,
  `estado` enum('PENDIENTE','CONFIRMADO','TERMINADO','CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
  `total` double NOT NULL,
  `forma_Pago` enum('TARJETA','TRANSFERENCIA','EFECTIVO','') NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id_producto` int(11) NOT NULL,
  `eliminado` tinyint(1) NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT current_timestamp(),
  `nombre` varchar(100) NOT NULL,
  `precio` double NOT NULL,
  `descripcion` varchar(250) NOT NULL,
  `stock` int(11) NOT NULL,
  `imagen` int(250) NOT NULL,
  `disponible` tinyint(1) NOT NULL,
  `id_categoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `eliminado` tinyint(1) NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT current_timestamp(),
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `mail` varchar(100) NOT NULL,
  `celular` varchar(50) NOT NULL,
  `contrasenia` varchar(200) NOT NULL,
  `rol` enum('ADMIN','USUARIO') NOT NULL DEFAULT 'USUARIO'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos
--

INSERT INTO usuario (id_usuario, eliminado, createdAt, nombre, apellido, mail, celular, contrasenia, rol) VALUES
(5, 0, '2026-06-01 23:09:42', 'Augusto', 'Ingrassia', 'admin@aethergames.com', '2615555555', '123456', 'ADMIN'),
(6,0, '2026-06-01 23:09:42', 'Hernesto', 'Martinez', 'HerMartinez@aethergames.com', '2613253492', 'Contraseña', 'ADMIN'),
(7,0, '2026-06-01 23:09:42', 'Estela', 'Rios', 'RiosEs@aethergames.com', '2614937264', 'Rios09','ADMIN');

INSERT INTO categoria (id_categoria,eliminado,createdAt,nombre,descripcion) VALUES
(5,0,'2026-06-15 10:00:00','Bebidas','Bebidas frías y calientes'),
(6,0,'2026-06-15 10:00:00','Plato principal','Plato fuerte de la comida'),
(7,0,'2026-06-15 10:00:00','Postre','Refrigerio dulce');

INSERT INTO producto (id_producto,eliminado,createdAt,nombre,precio,descripcion,stock,imagen,disponible,id_categoria)VALUES 
(5,0,'2026-06-15 10:10:00','Coca Cola 500ml',1500,'Gaseosa',100,1,1,5),
(6,0,'2026-06-15 10:10:00','Helado','200','Helado chico',150,1,1,7),
(7,0,'2026-06-15 10:10:00','Hamburguesa simple',300,'Hamburgesa con queso',300,1,1,6);

INSERT INTO pedido (id_pedido,eliminado,createdAt,fecha,estado,total,forma_Pago,id_usuario)VALUES 
(5,0,'2026-06-15 10:15:00','2026-06-15','PENDIENTE',1500,'EFECTIVO',6),
(6,0,'2026-06-10 10:00:00','2026-06-01','CANCELADO',3000,'TRANSFERENCIA',5),
(7,0,'2026-06-15 23:45:30','2026-02-08','CONFIRMADO',2250,'TARJETA',7);

INSERT INTO detalle_pedido (id_detalle,eliminado,createdAt,cantidad,subtotal,id_pedido,id_producto) VALUES 
(5,0,'2026-06-15 10:20:00',1,2250,7,5),
(6,0,'2026-02-01 21:10:45',1,3000,6,6),
(7,0,'2026-01-01 05:00:00',1,1500,5,7);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `id_pedido` (`id_pedido`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `id_categoria` (`id_categoria`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `mail` (`mail`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
