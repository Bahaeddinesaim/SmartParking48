-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 06 mai 2024 à 22:59
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `parking_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `tb_occupation`
--

CREATE TABLE `tb_occupation` (
  `proprio_id` varchar(20) NOT NULL,
  `proprio_nom` varchar(50) NOT NULL,
  `mt_type` varchar(30) NOT NULL,
  `mt_matricule` varchar(20) DEFAULT NULL,
  `id_place` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `tb_place`
--

CREATE TABLE `tb_place` (
  `num_place` int(11) NOT NULL,
  `type_place` varchar(30) NOT NULL,
  `disponible` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `tb_place`
--

INSERT INTO `tb_place` (`num_place`, `type_place`, `disponible`) VALUES
(1, 'PLACE VOITURE', 'oui');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `tb_occupation`
--
ALTER TABLE `tb_occupation`
  ADD PRIMARY KEY (`proprio_id`),
  ADD UNIQUE KEY `id_place` (`id_place`);

--
-- Index pour la table `tb_place`
--
ALTER TABLE `tb_place`
  ADD PRIMARY KEY (`num_place`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `tb_occupation`
--
ALTER TABLE `tb_occupation`
  ADD CONSTRAINT `fk` FOREIGN KEY (`id_place`) REFERENCES `tb_place` (`num_place`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
