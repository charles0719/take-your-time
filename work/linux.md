# 截取指定时间内的文件

```sh
sed -n ‘/2010-11-17 09:25:55/,/2010-11-17 09:25:55/p’ logfile
```

# 补充

## 几天内新增的文件

```sh
find . -type f -ctime -1| xargs ls –l
```

（1） -type f 只搜索文件，不包含文件夹

（2）ctime中的c-change的意思

（3）-ctime +n： n天前修改的；-ctime –n：n天内修改的，修改日期过去n天的

## awk

```sh
du bakAndUp.sh |awk {'print $1'}
```
{print $1}就是将某一行（一条记录）中以空格为分割符的第一个字段打印出来

## 简单的sh脚本

```sh
cd /data1/ballas/default   
files=`find ./ -type f -newermt '2019-01-22 14:30:00' ! -newermt '2019-01-22 14:40:00'|sort`
sum=0
 for i in $files
do
  a=` du $i|awk {'print $1'}`
 sum=$[a+sum]
done
echo $sum
```

# 删除0kb的文件

```sh
find . -name "*" -type f -size 0c | xargs -n 1 rm -f
```

