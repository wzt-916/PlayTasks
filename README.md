# Playtask
开发Playtask软件：

![image-20231120120812736](image/1.jpg)

## Playtask界面

##### Playtask界面：任务栏的TabLayout布局

<img src="image/2.jpg" alt="image-20231120120812736" style="zoom:50%;" />

##### Playtask界面：底部导航栏

<img src="image/5.png" style="zoom:50%;" />

##### Playtask界面：底部导航栏(任务)与TabLayout布局

<img src="image/6.png" style="zoom:50%;" />

##### Playtask界面：各种任务页面的实现显示

<img src="image/7.png" style="zoom: 50%;" />

<img src="image/8.png" style="zoom:50%;" />

##### Playtask界面：右上角角标添加

<img src="image/9.png" style="zoom:50%;" />

##### Playtask界面：右上角角标添加点击后跳转新的页面

添加任务：

<img src="image/10.png" style="zoom:50%;" />

加入副本：

<img src="image/11.png" style="zoom:50%;" />

## Playtask功能

##### Playtask功能：实现添加任务功能

<img src="image/13.png" style="zoom:50%;" />

##### Playtask功能：实现添加任务功能

<img src="image/14.png" style="zoom:50%;" />

##### Playtask功能：实现删除任务功能

## 错误记录

#### **实现底部导航栏**

在 `switch` 语句中处理底部导航栏的 ID 出现错误，可能是由于 ID 不匹配或者处理逻辑出现了问题：

![](image/3.png)

将switch语句改成if语句即可：

#### <img src="image/4.jpg" style="zoom: 50%;" />字符串比较

在Java中，字符串比较时应该使用`equals()`方法而不是`==`运算符。`==`比较的是对象的引用是否相同，而`equals()`比较的是字符串的内容是否相同。

<img src="image/12.jpg" style="zoom: 50%;" />

##### 删除页面不对应

在“每周任务”删除数据，结果“每日任务”的任务被删除了。

排查后发现，每个menu都有属于自己的id号，如图：

![](image/15.jpg)

因此在处理菜单的时候，需要获取当前的menu的id：

![](image/16.jpg)
