# Getting Started

自定义一个Realm  (UserRealm),单独认证时使用

在ShiroConfig中将UserRealm加入进去
在ShiroConfig中将SecurityManager 中将两个realm都加进去

在ShiroConfig 设置不拦截原来登陆页
filterChainDefinitionMap.put("/login/**", "anon");

在controller添加login页面

注：cas获取权限的方式可能不同，单独修改