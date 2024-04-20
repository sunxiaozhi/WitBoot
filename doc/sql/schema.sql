SET NAMES utf8mb4;

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

DROP TABLE IF EXISTS `wb_department`;
CREATE TABLE `wb_department` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编码',
    `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
    `parent_id` bigint(20) NOT NULL COMMENT '上级id',
    `delete_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标识 0已删除 1正常',
    `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
    `modifier` varchar(255) NOT NULL DEFAULT '' COMMENT '修改人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `wb_operation_log`;
CREATE TABLE `wb_operation_log` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `ip` varchar(100) NOT NULL DEFAULT '' COMMENT 'ip',
    `location` varchar(100) NOT NULL DEFAULT '' COMMENT '位置',
    `uri` varchar(255) NOT NULL DEFAULT '' COMMENT 'uri',
    `method` varchar(20) NOT NULL DEFAULT '' COMMENT '请求方式',
    `waste_time` int(8) NOT NULL DEFAULT '0' COMMENT '耗费时间',
    `request_param` varchar(1000) NOT NULL DEFAULT '' COMMENT '请求参数',
    `request_body` varchar(1000) NOT NULL DEFAULT '' COMMENT '请求内容',
    `response_result` varchar(1000) NOT NULL DEFAULT '' COMMENT '响应内容',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;