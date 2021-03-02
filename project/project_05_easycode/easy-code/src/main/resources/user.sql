-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(11) NOT NULL,
    `username` varchar(20) DEFAULTNULL,
    `sex` varchar(6) DEFAULTNULL,
    `birthday` date DEFAULTNULL,
    `address` varchar(20) DEFAULTNULL,
    `password` varchar(20) DEFAULTNULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS = 1;