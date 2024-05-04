LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu`
    DISABLE KEYS */;
INSERT INTO `menu` (id, label, icon, parent)
VALUES (-1, 'ROOT', '-', NULL),
       (0, 'Documents', 'pi pi-fw pi-inbox', -1),
       (1, 'Home', 'pi pi-fw pi-home', 0),
       (2, 'Invoices', 'pi pi-fw pi-file', 1),
       (3, 'Work', 'pi pi-fw pi-cog', -1),
       (4, 'Expenses', 'pi pi-fw pi-file', 3),
       (5, 'Resume', 'pi pi-fw pi-file', 3);
/*!40000 ALTER TABLE `menu`
    ENABLE KEYS */;
UNLOCK TABLES;