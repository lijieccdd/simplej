本项目为跟着《架构探险：从零开始写 JavaWeb 框架》所写代码

V1.0
# 1.实现了基础IOC容器
    1.1 从默认配置文件simplej.properties中加载配置信息
    1.2 从配置信息中得到基础扫描包名
    1.3 通过递归扫描包下的所有class加载到class集合中保存
    1.4 class遍历集合，将标注了service和controller注解的类进行实例化并保存到map<class,instance> beanMap中
    1.5 class遍历集合，通过class得到字段集合，将标注了autowrite的字段通过类型设置值
    
# 2.实现了基础mvc功能
    2.1 得到所有标注了controller注解的类的集合
    2.2 遍历集合，通过反射得到controller的方法集合
    2.3 遍历方法集合，如果方法标注了requestmapping,通过requestmapping得到配置的url,并将url、handler:controller.class 
    method，保存到容器map<url,handler> actionMap中
    2.4 创建核心servlet，拦截所有请求进行处理
            从请求中得到请求路径
            根据请求路径从actionMap中得到handler
            封装请求的参数到paramMap中
            从handler得到处理controller.class，根据controller.class从beanMap得到实例
            从handler得到处理方法method
            通过反射调用方法，得到处理结果result
            根据result类型进行处理，如果为view类型则转发或者重定向到相应的jsp，如果为data类型则将model转化为json以
            application/json类型返回客户端

# 3.存在问题
    3.1 配置文件不能动态配置
    3.2 controller层入参不灵活、自动注入方式不灵活
    3.3 未找到handler的处理方式、静态资源不能配置多个
    3.3 不支持aop功能
    3.4 框架扩展性不强