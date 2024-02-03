SET NAMES utf8mb4;
-- ----------------------------
-- Table structure for wb_user
-- ----------------------------
DROP TABLE IF EXISTS `wb_user`;
CREATE TABLE IF NOT EXISTS `wb_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编码',
    `username` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
    `password` varchar(255) NOT NULL DEFAULT '' COMMENT '加盐密码',
    `name` varchar(100) NOT NULL DEFAULT '' COMMENT '姓名',
    `mobile` varchar(30) NOT NULL DEFAULT '' COMMENT '手机号',
    `gender` tinyint(1)  NOT NULL DEFAULT 1 COMMENT '性别 1男 2女',
    `birthday` date DEFAULT NULL COMMENT '生日',
    `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
    `delete_flag` tinyint(1) NOT NULL DEFAULT 1 COMMENT '删除标识 0已删除 1正常',
    `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
    `modifier` varchar(255) NOT NULL COMMENT '修改人',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_username` (`username`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '管理员表';

