-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 21 Nov 2021 pada 10.44
-- Versi server: 10.4.19-MariaDB
-- Versi PHP: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apotek`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `invoice`
--

CREATE TABLE `invoice` (
  `section` varchar(30) NOT NULL,
  `date` date NOT NULL,
  `seq` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `invoice`
--

INSERT INTO `invoice` (`section`, `date`, `seq`) VALUES
('transaction', '2021-08-28', 1),
('transaction', '2021-08-28', 2),
('transaction', '2021-08-28', 3),
('transaction', '2021-08-28', 4),
('purchase', '2021-08-28', 1),
('purchase', '2021-08-28', 2),
('purchase', '2021-08-28', 3),
('purchase', '2021-09-09', 1),
('purchase', '2021-09-21', 1),
('transaction', '2021-10-25', 1),
('purchase', '2021-10-25', 1),
('transaction_return', '2021-10-27', 1),
('purchase_return', '2021-10-27', 1),
('purchase_return', '2021-10-27', 2),
('transaction', '2021-11-03', 1),
('purchase', '2021-11-04', 1),
('purchase', '2021-11-16', 1),
('purchase', '2021-11-16', 2),
('purchase', '2021-11-16', 3),
('purchase', '2021-11-16', 4),
('transaction', '2021-11-16', 1),
('transaction', '2021-11-16', 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `med`
--

CREATE TABLE `med` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` text NOT NULL,
  `base_price` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `stock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `med`
--

INSERT INTO `med` (`id`, `name`, `description`, `base_price`, `price`, `stock`) VALUES
(27, 'Paracetamol', 'Obat panas\n', 4500, 5000, 0),
(28, 'Penisilin', 'obat jantung', 4000, 5000, 10);

-- --------------------------------------------------------

--
-- Struktur dari tabel `purchases`
--

CREATE TABLE `purchases` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `supplier` int(11) DEFAULT NULL,
  `total` int(11) NOT NULL,
  `transaction_code` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `purchases`
--

INSERT INTO `purchases` (`id`, `date`, `supplier`, `total`, `transaction_code`) VALUES
(1, '2021-08-28', 0, 100000, 'PM/20210828/001'),
(2, '2021-08-28', 0, 600000, 'PM/20210828/002'),
(3, '2021-08-28', 0, 300000, 'PM/20210828/003'),
(4, '2021-09-09', 0, 500000, 'PM/20210909/001'),
(5, '2021-09-21', 0, 55000, 'PM/20210921/001'),
(6, '2021-10-25', 0, 60000, 'PM/20211025/001'),
(7, '2021-11-04', 0, 30000, 'PM/20211104/001'),
(8, '2021-11-16', 0, 812000, 'PM/20211116/001'),
(9, '2021-11-16', 0, 25000, 'PM/20211116/002'),
(10, '2021-11-16', 0, 60000, 'PM/20211116/003'),
(11, '2021-11-16', 0, 50000, 'PM/20211116/004');

-- --------------------------------------------------------

--
-- Struktur dari tabel `purchase_details`
--

CREATE TABLE `purchase_details` (
  `id` int(11) NOT NULL,
  `med_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `purchase_details`
--

INSERT INTO `purchase_details` (`id`, `med_id`, `qty`, `transaction_id`) VALUES
(1, 1, 10, 1),
(2, 2, 50, 2),
(3, 3, 20, 3),
(4, 1, 50, 4),
(5, 12, 10, 5),
(6, 12, 10, 6),
(7, 24, 30000, 7),
(8, 12, 12000, 8),
(9, 25, 800000, 8),
(10, 22, 25000, 9),
(11, 26, 60000, 10),
(12, 27, 10, 11);

-- --------------------------------------------------------

--
-- Struktur dari tabel `purchase_returns`
--

CREATE TABLE `purchase_returns` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `reason` text DEFAULT NULL,
  `total` int(11) NOT NULL,
  `transaction_code` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `purchase_returns`
--

INSERT INTO `purchase_returns` (`id`, `date`, `reason`, `total`, `transaction_code`) VALUES
(1, '2021-10-27', NULL, 72000, 'PMR/20211027/001'),
(2, '2021-10-27', NULL, 72000, 'PMR/20211027/002');

-- --------------------------------------------------------

--
-- Struktur dari tabel `purchase_return_details`
--

CREATE TABLE `purchase_return_details` (
  `id` int(11) NOT NULL,
  `med_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `purchase_return_details`
--

INSERT INTO `purchase_return_details` (`id`, `med_id`, `qty`, `transaction_id`) VALUES
(1, 12, 12, 1),
(2, 12, 12, 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `address` text NOT NULL,
  `city` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `post_code` varchar(6) DEFAULT '0',
  `phone` varchar(20) DEFAULT NULL,
  `contact` varchar(26) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`id`, `name`, `address`, `city`, `province`, `post_code`, `phone`, `contact`) VALUES
(1, 'Hamdan Abyadi', 'Dusun Sembir, Desa Gunasari RT/RW 01/09', 'Sumedang', 'Jawa Barat', '45311', '081991106450', 'WA'),
(3, 'Wibowo', 'Klaten\n', 'Klaten', 'Jawa Tengah', '4561', '089443032', 'WA'),
(4, 'Supplier', 'rumah supplier\n', 'kota supplier', 'provinsi supplier', '5543', '08032093', 'HP');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `total` int(11) NOT NULL,
  `transaction_code` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transactions`
--

INSERT INTO `transactions` (`id`, `date`, `total`, `transaction_code`) VALUES
(5, '2021-10-25', 12000, 'PJ/20211025/001'),
(6, '2021-11-03', 60000, 'PJ/20211103/001'),
(7, '2021-11-16', 25000, 'PJ/20211116/001'),
(8, '2021-11-16', 25000, 'PJ/20211116/002');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaction_details`
--

CREATE TABLE `transaction_details` (
  `id` int(11) NOT NULL,
  `med_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaction_details`
--

INSERT INTO `transaction_details` (`id`, `med_id`, `qty`, `transaction_id`) VALUES
(8, 27, 5, 7),
(9, 27, 5, 8);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaction_returns`
--

CREATE TABLE `transaction_returns` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `reason` text DEFAULT NULL,
  `total` int(11) NOT NULL,
  `transaction_code` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaction_returns`
--

INSERT INTO `transaction_returns` (`id`, `date`, `reason`, `total`, `transaction_code`) VALUES
(1, '2021-10-27', NULL, 72000, 'PJR/20211027/001');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaction_return_details`
--

CREATE TABLE `transaction_return_details` (
  `id` int(11) NOT NULL,
  `med_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaction_return_details`
--

INSERT INTO `transaction_return_details` (`id`, `med_id`, `qty`, `transaction_id`) VALUES
(1, 12, 12, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `level` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `level`) VALUES
(1, 'admin', 'admin', 1),
(2, 'user1', '123', 2),
(7, 'user555', '123', 2);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `invoice`
--
ALTER TABLE `invoice`
  ADD KEY `seq` (`seq`);

--
-- Indeks untuk tabel `med`
--
ALTER TABLE `med`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `purchase_details`
--
ALTER TABLE `purchase_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `med_id` (`med_id`),
  ADD KEY `transaction_id` (`transaction_id`);

--
-- Indeks untuk tabel `purchase_returns`
--
ALTER TABLE `purchase_returns`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `purchase_return_details`
--
ALTER TABLE `purchase_return_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `med_id` (`med_id`),
  ADD KEY `transaction_id` (`transaction_id`);

--
-- Indeks untuk tabel `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `transaction_details`
--
ALTER TABLE `transaction_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `med_id` (`med_id`),
  ADD KEY `transaction_id` (`transaction_id`);

--
-- Indeks untuk tabel `transaction_returns`
--
ALTER TABLE `transaction_returns`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `transaction_return_details`
--
ALTER TABLE `transaction_return_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `med_id` (`med_id`),
  ADD KEY `transaction_id` (`transaction_id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `med`
--
ALTER TABLE `med`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT untuk tabel `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT untuk tabel `purchase_details`
--
ALTER TABLE `purchase_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT untuk tabel `purchase_returns`
--
ALTER TABLE `purchase_returns`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `purchase_return_details`
--
ALTER TABLE `purchase_return_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `transaction_details`
--
ALTER TABLE `transaction_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `transaction_returns`
--
ALTER TABLE `transaction_returns`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `transaction_return_details`
--
ALTER TABLE `transaction_return_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `transaction_details`
--
ALTER TABLE `transaction_details`
  ADD CONSTRAINT `transaction_details_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_details_ibfk_2` FOREIGN KEY (`med_id`) REFERENCES `med` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
