INSERT INTO "system_config"("id", "k", "value", "remark") VALUES
(1, 'siteName', NULL, STRINGDECODE('站点名称')),
(2, 'storageStrategy', NULL, STRINGDECODE('当前启用存储引擎')),
(3, 'username', NULL, STRINGDECODE('管理员账号')),
(4, 'password', NULL, STRINGDECODE('管理员密码')),
(5, 'domain', NULL, STRINGDECODE('站点域名')),
(6, 'customCss', NULL, STRINGDECODE('自定义 CSS')),
(7, 'customJs', NULL, STRINGDECODE('自定义 JS (可用于统计代码)')),
(8, 'tableSize', NULL, STRINGDECODE('表格大小')),
(9, 'showOperator', NULL, STRINGDECODE('是否显示操作按钮')),
(10, 'showDocument', NULL, STRINGDECODE('是否显示文档')),
(11, 'announcement', NULL, STRINGDECODE('网站公告')),
(12, 'showAnnouncement', NULL, STRINGDECODE('是否显示网站公告')),
(13, 'layout', NULL, STRINGDECODE('页面布局'));
