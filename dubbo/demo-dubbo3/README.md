# module description
The real business logics of the project. You can provide service implementations or call other services.

# dubbo-admin控制台 
解压apache-dubbo-admin-0.5.0-bin-release.zip
修改 apache-dubbo-admin-0.5.0-bin-release/bin/config/application.properties 配置 
```
admin.registry.address=nacos://127.0.0.1:8848
admin.config-center=nacos://127.0.0.1:8848
admin.metadata-report.address=nacos://127.0.0.1:8848
```
进入lib目录启动
```
java -Dserver.port=7080 \
-Dspring.config.location=../bin/config/application.properties \
-Ddubbo.protocol.port=20890 \
-jar dubbo-admin.jar
```