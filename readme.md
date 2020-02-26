##说明
此版本cas-server使用oracle，使用自定义权限认证CustomAuthenticationConfiguration
未使用cas自带的方式，因为此数据库密码为  md5(用户名+密码+盐) 方式认证
默认方式只能对密码和盐进行操作，无法获取用户名

仿照
https://blog.csdn.net/Anumbrella/article/details/81590595
代码：https://github.com/Shuyun123/CAS
第三章

其他参考文章
https://blog.csdn.net/yelllowcong/article/details/79236360


overlays使用
https://blog.csdn.net/qq_24874939/article/details/84534854


cas-server 5.3
https://blog.csdn.net/u011872945/article/details/81047025
https://blog.csdn.net/qq_36640713/article/details/82049506
