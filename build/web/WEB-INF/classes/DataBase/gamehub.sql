-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-01-2024 a las 02:19:21
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
-- Base de datos: `gamehub`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consolas`
--

CREATE TABLE `consolas` (
  `id_consola` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `potencia_cpu` varchar(255) DEFAULT NULL,
  `potencia_gpu` varchar(255) DEFAULT NULL,
  `compania` varchar(255) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `unidades_disponibles` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `consolas`
--

INSERT INTO `consolas` (`id_consola`, `nombre`, `potencia_cpu`, `potencia_gpu`, `compania`, `precio`, `unidades_disponibles`) VALUES
(1, 'PlayStation 5', 'AMD Ryzen Zen 2, 8 núcleos, 16 hilos, 3.5 GHz', 'AMD RDNA 2, 36 unidades de cómputo, 2.23 GHz', 'Sony Interactive', 500.00, 96),
(2, 'Xbox Series X', 'AMD Zen 2 Octa-core a 3.8 GHz', 'AMD RDNA 2, 12 TFLOPs', 'Microsoft', 499.00, 100),
(9, 'Xbox One', 'AMD Jaguar 8-core', 'AMD GCN GPU', 'Microsoft', 169.00, 50),
(10, 'Xbox Series S', 'AMD Zen 2 8-core', 'AMD RDNA 2 GPU ', 'Microsoft', 249.00, 25),
(11, 'Nintendo Switch', 'ARM Cortex-A57', 'NVIDIA Maxwell GPU', 'Nintendo', 299.00, 50),
(12, 'Nintendo Switch Lite ', 'ARM Cortex-A57', 'NVIDIA Maxwell GPU', 'Nintendo', 199.00, 50),
(13, 'PlayStation 4', 'AMD Jaguar 8-core', 'AMD GCN GPU ', 'Sony Interactive', 299.00, 55),
(14, 'PlayStation 5 con CD', 'AMD Zen 2 8-core', 'AMD RDNA 2 GPU', 'Sony Interactive', 419.00, 60),
(15, 'PlayStation 5 sin CD', 'AMD Zen 2 8-core', 'AMD RDNA 2 GPU', 'Sony Interactive', 349.00, 56);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegos`
--

CREATE TABLE `juegos` (
  `id_juego` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `compania_desarrolladora` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `puntuacion_metacritic` int(11) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `unidades_disponibles` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `juegos`
--

INSERT INTO `juegos` (`id_juego`, `nombre`, `compania_desarrolladora`, `genero`, `puntuacion_metacritic`, `precio`, `unidades_disponibles`) VALUES
(1, 'Final Fantasy VIII', 'Square Enix', 'Fantasía', 90, 52.00, 22),
(2, 'Final Fantasy VII Reunion', 'Square Enix', 'Fantasía', 90, 50.00, 22),
(4, 'Final Fantasy XIV', 'Square Enix', 'Fantasía', 100, 70.00, 10),
(5, 'Fae Farm', 'Phoenix Labs', 'Cozy, Simulación', 88, 60.00, 10),
(6, 'Cyberpunk 2077', 'CD Projekt', 'RPG', 95, 45.00, 20),
(7, 'The Legend of Zelda: BOTW', 'Nintendo', 'Aventura', 98, 65.00, 15),
(8, 'Diablo IV', 'Blizzard', 'MMORPG', 95, 70.00, 15),
(9, 'Rocket League', 'Psyonix', 'Deportes', 89, 25.00, 11),
(10, 'Destiny 2', 'Bungie', 'Shooter', 87, 50.00, 20),
(11, 'The Last of Us Part II', 'Naughty Dog', 'Acción y Aventuras', 93, 44.00, 20),
(12, 'State of Decay 2', 'Undead Labs', 'Acción, Aventuras, Supervivencia', 75, 29.99, 23);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegos_plataformas`
--

CREATE TABLE `juegos_plataformas` (
  `id_juego` int(11) DEFAULT NULL,
  `id_consola` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `juegos_plataformas`
--

INSERT INTO `juegos_plataformas` (`id_juego`, `id_consola`) VALUES
(2, 1),
(1, 2),
(7, 11),
(7, 12),
(12, 9),
(12, 10),
(12, 2),
(11, 14),
(11, 15),
(11, 13),
(1, 13),
(1, 14),
(1, 15),
(1, 11),
(6, 11),
(6, 12),
(6, 13),
(6, 14),
(10, 13),
(10, 14),
(10, 15),
(10, 9),
(8, 14),
(8, 15),
(8, 2),
(5, 11),
(5, 12),
(4, 13),
(4, 14),
(4, 15),
(9, 13),
(9, 1),
(9, 11),
(9, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tickets`
--

INSERT INTO `tickets` (`ticket_id`, `id_usuario`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket_items`
--

CREATE TABLE `ticket_items` (
  `ticket_item_id` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `tipo_producto` enum('Consola','Juego') NOT NULL,
  `id_consola` int(11) DEFAULT NULL,
  `id_juego` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `precio_unitario` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ticket_items`
--

INSERT INTO `ticket_items` (`ticket_item_id`, `ticket_id`, `tipo_producto`, `id_consola`, `id_juego`, `cantidad`, `precio_unitario`) VALUES
(1, 1, 'Juego', NULL, 2, 1, 50.00),
(2, 2, 'Consola', 1, NULL, 1, 499.99),
(3, 2, 'Juego', NULL, 2, 1, 50.00),
(4, 3, 'Juego', NULL, 2, 1, 50.00),
(5, 4, 'Juego', NULL, 2, 1, 50.00),
(6, 5, 'Juego', NULL, 2, 1, 50.00),
(7, 6, 'Juego', NULL, 2, 1, 50.00),
(8, 7, 'Juego', NULL, 2, 1, 50.00),
(9, 8, 'Juego', NULL, 2, 1, 50.00),
(10, 9, 'Juego', NULL, 2, 1, 50.00),
(11, 10, 'Juego', NULL, 2, 1, 50.00),
(12, 11, 'Juego', NULL, 2, 2, 100.00),
(13, 12, 'Juego', NULL, 2, 1, 50.00),
(14, 13, 'Juego', NULL, 2, 1, 50.00),
(15, 14, 'Juego', NULL, 2, 1, 50.00),
(16, 14, 'Juego', NULL, 1, 1, 20.00),
(17, 15, 'Consola', 1, NULL, 1, 499.00),
(18, 15, 'Juego', NULL, 2, 1, 50.00),
(19, 16, 'Consola', 1, NULL, 1, 499.00),
(20, 16, 'Juego', NULL, 2, 1, 50.00),
(21, 17, 'Consola', 1, NULL, 1, 499.00),
(22, 17, 'Juego', NULL, 1, 1, 20.00),
(23, 18, 'Consola', 1, NULL, 1, 499.00),
(24, 18, 'Juego', NULL, 1, 1, 20.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `tipodeusuario` enum('Administrador','Usuario','','') DEFAULT 'Usuario',
  `saldo` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `username`, `password`, `tipodeusuario`, `saldo`) VALUES
(1, 'Senua', '1234', 'Administrador', 2000.00),
(2, 'Senua2', '1234', 'Usuario', 140.00);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `consolas`
--
ALTER TABLE `consolas`
  ADD PRIMARY KEY (`id_consola`);

--
-- Indices de la tabla `juegos`
--
ALTER TABLE `juegos`
  ADD PRIMARY KEY (`id_juego`);

--
-- Indices de la tabla `juegos_plataformas`
--
ALTER TABLE `juegos_plataformas`
  ADD KEY `id_juego` (`id_juego`),
  ADD KEY `id_consola` (`id_consola`);

--
-- Indices de la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `fk_tickets_usuarios` (`id_usuario`);

--
-- Indices de la tabla `ticket_items`
--
ALTER TABLE `ticket_items`
  ADD PRIMARY KEY (`ticket_item_id`),
  ADD KEY `fk_ticket_items_tickets` (`ticket_id`),
  ADD KEY `fk_ticket_items_producto_consola` (`id_consola`),
  ADD KEY `fk_ticket_items_producto_juego` (`id_juego`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `unique_username` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `consolas`
--
ALTER TABLE `consolas`
  MODIFY `id_consola` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `juegos`
--
ALTER TABLE `juegos`
  MODIFY `id_juego` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `tickets`
--
ALTER TABLE `tickets`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `ticket_items`
--
ALTER TABLE `ticket_items`
  MODIFY `ticket_item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `juegos_plataformas`
--
ALTER TABLE `juegos_plataformas`
  ADD CONSTRAINT `juegos_plataformas_ibfk_1` FOREIGN KEY (`id_juego`) REFERENCES `juegos` (`id_juego`),
  ADD CONSTRAINT `juegos_plataformas_ibfk_2` FOREIGN KEY (`id_consola`) REFERENCES `consolas` (`id_consola`);

--
-- Filtros para la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `fk_tickets_usuarios` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`);

--
-- Filtros para la tabla `ticket_items`
--
ALTER TABLE `ticket_items`
  ADD CONSTRAINT `fk_ticket_items_producto_consola` FOREIGN KEY (`id_consola`) REFERENCES `consolas` (`id_consola`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ticket_items_producto_juego` FOREIGN KEY (`id_juego`) REFERENCES `juegos` (`id_juego`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ticket_items_tickets` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`ticket_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
