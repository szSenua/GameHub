-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-12-2023 a las 20:25:26
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
(1, 'PlayStation 5', 'AMD Ryzen Zen 2, 8 núcleos, 16 hilos, 3.5 GHz', 'AMD RDNA 2, 36 unidades de cómputo, 2.23 GHz', 'Sony', 499.99, 100),
(2, 'Xbox Series X', 'AMD Zen 2 Octa-core a 3.8 GHz', 'AMD RDNA 2, 12 TFLOPs', 'Microsoft', 499.00, 100);

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
(1, 'Final Fantasy VIII', 'Square Enix', 'Fantasía', 90, 20.00, 5),
(2, 'Final Fantasy VII Reunion', 'Square Enix', 'Fantasía', 90, 50.00, 7);

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
(1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket_items`
--

CREATE TABLE `ticket_items` (
  `ticket_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(1, 'Senua', '1234', 'Administrador', 100.00),
(2, 'Senua2', '1234', 'Usuario', 100.00);

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
  ADD KEY `fk_ticket_items_tickets` (`ticket_id`);

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
  MODIFY `id_consola` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `juegos`
--
ALTER TABLE `juegos`
  MODIFY `id_juego` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tickets`
--
ALTER TABLE `tickets`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
  ADD CONSTRAINT `fk_ticket_items_tickets` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`ticket_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
