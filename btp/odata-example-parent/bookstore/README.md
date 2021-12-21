1、创建数据库
    bookstore
    
    修改数据库配置：
    spring.datasource.url=jdbc:mysql://193.112.22.138:3306/bookstore?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    spring.datasource.username=root
    spring.datasource.password=XXX

2、访问地址
    http://localhost:8080/odata/$metadata
    http://localhost:8080/odata/Books