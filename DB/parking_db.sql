-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 24 mai 2024 à 19:34
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
-- Structure de la table `tb_admin`
--

CREATE TABLE `tb_admin` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `tb_admin`
--

INSERT INTO `tb_admin` (`id`, `username`, `password`) VALUES
(1, 'haytham', 'haytham1949'),
(2, 'bahae', 'bahae1946'),
(3, 'h', 'h');

-- --------------------------------------------------------

--
-- Structure de la table `tb_occupation`
--

CREATE TABLE `tb_occupation` (
  `proprio_id` varchar(20) NOT NULL,
  `proprio_nom` varchar(50) NOT NULL,
  `mt_type` varchar(30) NOT NULL,
  `mt_matricule` varchar(20) DEFAULT NULL,
  `id_place` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `randomPassword` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `tb_occupation`
--

INSERT INTO `tb_occupation` (`proprio_id`, `proprio_nom`, `mt_type`, `mt_matricule`, `id_place`, `email`, `randomPassword`) VALUES
('FB126788', 'haytham', 'VOITURE', 'qljcblu', 2, 'farncolegends47@gmail.com', '#z2GoG'),
('lqzfb', 'Bahaeddine', 'VOITURE', 'besfkj', 1, 'bahaeddine4info@gmail.com', 'B)cIyI');

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
(1, 'PLACE VOITURE', 'non'),
(2, 'PLACE VOITURE', 'non'),
(3, 'PLACE VOITURE', 'oui'),
(4, 'PLACE VOITURE', 'oui'),
(5, 'PLACE MOTO', 'oui'),
(6, 'PLACE VELO', 'oui'),
(7, 'PLACE VOITURE', 'oui'),
(10, 'PLACE VOITURE', 'oui'),
(12, '', 'oui');

-- --------------------------------------------------------

--
-- Structure de la table `tb_reservation`
--

CREATE TABLE `tb_reservation` (
  `id_reservation` int(11) NOT NULL,
  `date_debut` varchar(59) NOT NULL,
  `date_expiration` varchar(59) NOT NULL,
  `id_place` int(11) NOT NULL,
  `idproprio` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `tb_reservation`
--

INSERT INTO `tb_reservation` (`id_reservation`, `date_debut`, `date_expiration`, `id_place`, `idproprio`) VALUES
(1, '2024-05-23', '2025-05-23', 5, ''),
(2, '2024-05-23', '2025-05-23', 1, ''),
(3, '2024-05-23', '2025-05-23', 10, ''),
(4, '2024-05-24', '2025-05-24', 10, ''),
(5, '2024-05-24', '2025-05-24', 1, ''),
(6, '2024-05-24', '2025-05-24', 1, ''),
(7, '2024-05-24', '2025-05-24', 2, 'FB126788');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD PRIMARY KEY (`id`);

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
-- Index pour la table `tb_reservation`
--
ALTER TABLE `tb_reservation`
  ADD PRIMARY KEY (`id_reservation`),
  ADD KEY `id_place` (`id_place`),
  ADD KEY `idproprio` (`idproprio`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `tb_admin`
--
ALTER TABLE `tb_admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `tb_reservation`
--
ALTER TABLE `tb_reservation`
  MODIFY `id_reservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `tb_occupation`
--
ALTER TABLE `tb_occupation`
  ADD CONSTRAINT `fk` FOREIGN KEY (`id_place`) REFERENCES `tb_place` (`num_place`);

--
-- Contraintes pour la table `tb_reservation`
--
ALTER TABLE `tb_reservation`
  ADD CONSTRAINT `tb_reservation_ibfk_1` FOREIGN KEY (`id_place`) REFERENCES `tb_place` (`num_place`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
