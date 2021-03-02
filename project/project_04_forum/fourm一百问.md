# 定时器

springboot使用@EnableScheduling和@Scheduled就可以完成定时任务

## 重复执行定时器

可以使用redis分布式锁是通过setnx命令实现的

## 服务器宕机后任务丢失

使用数据库记录执行时间，计算当前时间和上次执行周期