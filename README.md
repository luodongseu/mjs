# mjs
卖教售项目

# 集群架构

![架构图](http://on-img.com/chart_image/5a98a8fce4b0337bad14f00e.png)


# 卖教售的PHP项目 地址 https://gitee.com/dtstudio/mjs-php

## 项目结构说明
- `index.php` 总的入口文件，会根据终端路由到`web/index.php`或者`app/index.php`
- `index.html` web下载客户端的静态介绍页面
- `web/` 后台管理页面
- `data/` 微信终端页面 
    - `data/config.php` 全局配置
    - `data/app/` 公共模版
    - `data/tpl/app` 微信页面
        - `data/tpl/app/default/bosscenter*` 微信页面的零售商页面
        - `data/tpl/app/default/xunyi*` 微信页面的店员页面
        - `data/tpl/web/` 后台管理面的模版
- `framework/` 公共函数、公共类等 
