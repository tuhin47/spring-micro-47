LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu`
    DISABLE KEYS */;
INSERT INTO `menu`
VALUES (0, 'root', NULL, '-'),
       (1, 'L1-M1', 0, 'fa-1'),
       (2, 'L1-M2', 0, 'fa-2'),
       (3, 'L2-SM1', 1, 'fa-3'),
       (4, 'L3-CM1', 3, 'fa-4'),
       (5, 'L2-CM2', 2, 'fa-5');
/*!40000 ALTER TABLE `menu`
    ENABLE KEYS */;
UNLOCK TABLES;