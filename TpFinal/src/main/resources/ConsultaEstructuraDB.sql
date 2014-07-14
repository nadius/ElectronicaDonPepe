-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 14-02-2014 a las 19:16:12
-- Versión del servidor: 5.5.35
-- Versión de PHP: 5.3.10-1ubuntu3.9

-- SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- SET time_zone = "+00:00";

--
-- Base de datos: `ElectronicaDonPepe_Branch`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Adicional`
--

CREATE TABLE IF NOT EXISTS `Adicional` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fechaCreacion` datetime NOT NULL,
  `fechaDesde` datetime NOT NULL,
  `fechaHasta` datetime NOT NULL,
  `total` float DEFAULT NULL,
  `comisionVenta_id` int(11) DEFAULT NULL,
  `premioVendedor_id` int(11) DEFAULT NULL,
  `vendedor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9AAF8BB6E6996CD0` (`vendedor_id`),
  KEY `FK9AAF8BB612273084` (`comisionVenta_id`),
  KEY `FK9AAF8BB6E3622F69` (`premioVendedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Adicional_ComisionProducto`
--

CREATE TABLE IF NOT EXISTS `Adicional_ComisionProducto` (
  `Adicional_id` int(11) NOT NULL,
  `comisionesProducto_id` int(11) NOT NULL,
  KEY `FKC152FC6609C4502` (`comisionesProducto_id`),
  KEY `FKC152FC6A3BEF044` (`Adicional_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Adicional_Premio`
--

CREATE TABLE IF NOT EXISTS `Adicional_Premio` (
  `Adicional_id` int(11) NOT NULL,
  `campanias_id` int(11) NOT NULL,
  KEY `FK204DB1999754C433` (`campanias_id`),
  KEY `FK204DB199A3BEF044` (`Adicional_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Campania`
--

CREATE TABLE IF NOT EXISTS `Campania` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `fechaCreacion` datetime NOT NULL,
  `producto_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKFB837826F4566E30` (`producto_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Comision`
--

CREATE TABLE IF NOT EXISTS `Comision` (
  `id` int(11) NOT NULL,
  `fechaCreacion` datetime NOT NULL,
  `fechaDesde` datetime NOT NULL,
  `fechaHasta` datetime NOT NULL,
  `importe` float NOT NULL,
  `unidades` int(11) NOT NULL,
  `vendedor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDFC02DDDE6996CD0` (`vendedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ComisionProducto`
--

CREATE TABLE IF NOT EXISTS `ComisionProducto` (
  `id` int(11) NOT NULL,
  `fechaCreacion` datetime NOT NULL,
  `fechaDesde` datetime NOT NULL,
  `fechaHasta` datetime NOT NULL,
  `importe` float NOT NULL,
  `unidades` int(11) NOT NULL,
  `vendedor_id` int(11) DEFAULT NULL,
  `producto_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDFC02DDDE6996CD0ee5d365d` (`vendedor_id`),
  KEY `FKEE5D365DF4566E30` (`producto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ComisionVenta`
--

CREATE TABLE IF NOT EXISTS `ComisionVenta` (
  `id` int(11) NOT NULL,
  `fechaCreacion` datetime NOT NULL,
  `fechaDesde` datetime NOT NULL,
  `fechaHasta` datetime NOT NULL,
  `importe` float NOT NULL,
  `unidades` int(11) NOT NULL,
  `vendedor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDFC02DDDE6996CD0405e6e6f` (`vendedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ComisionVenta_Venta`
--

CREATE TABLE IF NOT EXISTS `ComisionVenta_Venta` (
  `ComisionVenta_id` int(11) NOT NULL,
  `elementos_id` int(11) NOT NULL,
  KEY `FK862F7C5C12273084` (`ComisionVenta_id`),
  KEY `FK862F7C5C2D87FD70` (`elementos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Comision_Producto_Monto`
--

CREATE TABLE IF NOT EXISTS `Comision_Producto_Monto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monto` float NOT NULL,
  `producto_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4F0ED58AF4566E30` (`producto_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Comision_Venta_Monto`
--

CREATE TABLE IF NOT EXISTS `Comision_Venta_Monto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `max` int(11) DEFAULT NULL,
  `min` int(11) NOT NULL,
  `monto` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequences`
--

CREATE TABLE IF NOT EXISTS `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Premio`
--

CREATE TABLE IF NOT EXISTS `Premio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fechaCreacion` datetime NOT NULL,
  `fechaDesde` datetime NOT NULL,
  `fechaHasta` datetime NOT NULL,
  `importe` float NOT NULL,
  `isCampania` bit(1) NOT NULL,
  `premiado_id` int(11) DEFAULT NULL,
  `producto_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8EF9B8F0F4566E30` (`producto_id`),
  KEY `FK8EF9B8F0F58AB2A` (`premiado_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Premio_Monto`
--

CREATE TABLE IF NOT EXISTS `Premio_Monto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campania` bit(1) NOT NULL,
  `monto` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Producto`
--

CREATE TABLE IF NOT EXISTS `Producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `precioUnitario` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `RolUsuario`
--

CREATE TABLE IF NOT EXISTS `RolUsuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuario`
--

CREATE TABLE IF NOT EXISTS `Usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `rol_id` int(11) DEFAULT NULL,
  `vendedor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5B4D8B0EED24EBE4` (`rol_id`),
  KEY `FK5B4D8B0EE6996CD0` (`vendedor_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Vendedor`
--

CREATE TABLE IF NOT EXISTS `Vendedor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Venta`
--

CREATE TABLE IF NOT EXISTS `Venta` (
  `id` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `importe` float NOT NULL,
  `vendedor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4EB7A2CE6996CD0` (`vendedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Venta_Producto`
--

CREATE TABLE IF NOT EXISTS `Venta_Producto` (
  `Venta_id` int(11) NOT NULL,
  `productos_id` int(11) NOT NULL,
  KEY `FK1D689E33D1B68984` (`Venta_id`),
  KEY `FK1D689E338A8CA39D` (`productos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Adicional`
--
ALTER TABLE `Adicional`
  ADD CONSTRAINT `FK9AAF8BB6E3622F69` FOREIGN KEY (`premioVendedor_id`) REFERENCES `Premio` (`id`),
  ADD CONSTRAINT `FK9AAF8BB612273084` FOREIGN KEY (`comisionVenta_id`) REFERENCES `ComisionVenta` (`id`),
  ADD CONSTRAINT `FK9AAF8BB6E6996CD0` FOREIGN KEY (`vendedor_id`) REFERENCES `Vendedor` (`id`);

--
-- Filtros para la tabla `Adicional_ComisionProducto`
--
ALTER TABLE `Adicional_ComisionProducto`
  ADD CONSTRAINT `FKC152FC6A3BEF044` FOREIGN KEY (`Adicional_id`) REFERENCES `Adicional` (`id`),
  ADD CONSTRAINT `FKC152FC6609C4502` FOREIGN KEY (`comisionesProducto_id`) REFERENCES `ComisionProducto` (`id`);

--
-- Filtros para la tabla `Adicional_Premio`
--
ALTER TABLE `Adicional_Premio`
  ADD CONSTRAINT `FK204DB199A3BEF044` FOREIGN KEY (`Adicional_id`) REFERENCES `Adicional` (`id`),
  ADD CONSTRAINT `FK204DB1999754C433` FOREIGN KEY (`campanias_id`) REFERENCES `Premio` (`id`);

--
-- Filtros para la tabla `Campania`
--
ALTER TABLE `Campania`
  ADD CONSTRAINT `FKFB837826F4566E30` FOREIGN KEY (`producto_id`) REFERENCES `Producto` (`id`);

--
-- Filtros para la tabla `Comision`
--
ALTER TABLE `Comision`
  ADD CONSTRAINT `FKDFC02DDDE6996CD0` FOREIGN KEY (`vendedor_id`) REFERENCES `Vendedor` (`id`);

--
-- Filtros para la tabla `ComisionProducto`
--
ALTER TABLE `ComisionProducto`
  ADD CONSTRAINT `FKEE5D365DF4566E30` FOREIGN KEY (`producto_id`) REFERENCES `Producto` (`id`),
  ADD CONSTRAINT `FKDFC02DDDE6996CD0ee5d365d` FOREIGN KEY (`vendedor_id`) REFERENCES `Vendedor` (`id`);

--
-- Filtros para la tabla `ComisionVenta`
--
ALTER TABLE `ComisionVenta`
  ADD CONSTRAINT `FKDFC02DDDE6996CD0405e6e6f` FOREIGN KEY (`vendedor_id`) REFERENCES `Vendedor` (`id`);

--
-- Filtros para la tabla `ComisionVenta_Venta`
--
ALTER TABLE `ComisionVenta_Venta`
  ADD CONSTRAINT `FK862F7C5C2D87FD70` FOREIGN KEY (`elementos_id`) REFERENCES `Venta` (`id`),
  ADD CONSTRAINT `FK862F7C5C12273084` FOREIGN KEY (`ComisionVenta_id`) REFERENCES `ComisionVenta` (`id`);

--
-- Filtros para la tabla `Comision_Producto_Monto`
--
ALTER TABLE `Comision_Producto_Monto`
  ADD CONSTRAINT `FK4F0ED58AF4566E30` FOREIGN KEY (`producto_id`) REFERENCES `Producto` (`id`);

--
-- Filtros para la tabla `Premio`
--
ALTER TABLE `Premio`
  ADD CONSTRAINT `FK8EF9B8F0F58AB2A` FOREIGN KEY (`premiado_id`) REFERENCES `Vendedor` (`id`),
  ADD CONSTRAINT `FK8EF9B8F0F4566E30` FOREIGN KEY (`producto_id`) REFERENCES `Producto` (`id`);

--
-- Filtros para la tabla `Usuario`
--
ALTER TABLE `Usuario`
  ADD CONSTRAINT `FK5B4D8B0EE6996CD0` FOREIGN KEY (`vendedor_id`) REFERENCES `Vendedor` (`id`),
  ADD CONSTRAINT `FK5B4D8B0EED24EBE4` FOREIGN KEY (`rol_id`) REFERENCES `RolUsuario` (`id`);

--
-- Filtros para la tabla `Venta`
--
ALTER TABLE `Venta`
  ADD CONSTRAINT `FK4EB7A2CE6996CD0` FOREIGN KEY (`vendedor_id`) REFERENCES `Vendedor` (`id`);

--
-- Filtros para la tabla `Venta_Producto`
--
ALTER TABLE `Venta_Producto`
  ADD CONSTRAINT `FK1D689E338A8CA39D` FOREIGN KEY (`productos_id`) REFERENCES `Producto` (`id`),
  ADD CONSTRAINT `FK1D689E33D1B68984` FOREIGN KEY (`Venta_id`) REFERENCES `Venta` (`id`);