# MyPlant

1. 系统后端，使用hbase数据库来存储各种文件等非结构化数据。
2. 使用MySQL数据库来存储结构化的数据

#### 目录结构

- .git   ##git的数据文件夹，版本控制的配置和数据。
- .idea  ## idea的配置文件文件夹
- .mvn ## maven 的配置文件文件夹
- **src   ## 源程序文件夹**
  - main
    - java ## 源程序文件夹
      - com.songc
        - **config   ##程序初始化配置程序，Swagger配置和HBASE配置。**
        - **controller  ## RESTful服务接口，处理HTTP请求，程序的入口。调用dao和service程序处理前端请求。**
        - **core   ## 波形匹配和荧光图像提取电信号算法实现。**
        - **dao    ## 实现对数据库的增删查改，被controller或者service调用。**
        - dto    ## 简单POJO Java对象，用于数据传输。
        - entity  ## 数据库中表对应的实体对象，简单的POJO对象。
        - **service  ## 系统交互逻辑的实现，调用dao程序， 被controller调用。**
        - util    ## 系统中一些静态函数的实现。灰度值提取算法，滑动滤波等功能。
        - DemoApplication.java  ## 程序的入口程序。
    - **resource  ##资源文件夹，程序springboot的配置，静态资源等。**
      - **application.properities  ##程序的默认配置文件。**
      - **application—*.properities ## 程序的其他配置文件。\* 代表配置文件的名字。** 
  - test  ##单元测试文件夹，方便测试程序是否可以正常运行。
- target  ## 代码编译后的程序文件文件夹
- **pom.xml ## 项目依赖Maven的配置文件**

#### 依赖和系统需求

1. JAVA：JDK8.0 以上。
2. 软件：Maven，Git。
3. 开发环境：IDEA  [使用教程](https://youmeek.gitbooks.io/intellij-idea-tutorial/content/)   需要安装一个lombock 插件。
4. HBASE 需要建立一个`h_file`表，包含`h_info`,`h_content`两列。`analysis_result`表，包含`image`列。

#### 使用和启动：

1. 编译：双击Maven的Package，对项目进行编译打包，生成jar文件，位于`target`文件夹加下。

   ![maven](E:\MyFile\交接材料\文档\maven.png)

   2. 启动：运行编译生成的jar包。命令如下：

   ```shell
   java -jar 文件名.jar

   ## 启动jar文件，并使用自定义的配置文件,这里是使用test配置文件。
   java -jar 文件名.jar --spring.profiles.active=test
    
   ## 启动 打包好的jar 文件， 并重定义日志输出到 logs/log5-8.log 文件中
   nohup java -jar demo-0.0.1-SNAPSHOT.jar > ./logs/log5-08.log &
   ```

### 