# 项目介绍

项目地址：https://gitee.com/leshalv/screw

项目作用：生成数据库文档

样例地址：https://github.com/charles0719/take-your-time

# springboot引用本地lib

1.1**添加插件**

```xml
			<plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <includeSystemScope>true</includeSystemScope>
                </configuration>
            </plugin>
```

1.2**添加依赖**

```xml
        <dependency>
            <groupId>t1</groupId>
            <artifactId>t2</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/../lib/Dm7JdbcDriver-16.jar</systemPath>
        </dependency>
```

# 如何编写Maven项目的Plugins插件

1. 引入maven依赖
```xml
<!-- https://mvnrepository.com/artifact/org.apache.maven/maven-plugin-api -->
    <dependency>
      <groupId>org.apache.maven</groupId>
      <artifactId>maven-plugin-api</artifactId>
      <version>3.5.0</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.maven.plugin-tools/maven-plugin-annotations -->
    <dependency>
      <groupId>org.apache.maven.plugin-tools</groupId>
      <artifactId>maven-plugin-annotations</artifactId>
      <version>3.5.2</version>
      <scope>provided</scope>
    </dependency>
```
2. 修改打包方式

```xml
<packaging>maven-plugin</packaging>
```

3. 定义一个类继承`AbstractMojo`,实现`excute`方法

```java
@Mojo(name = "run-v2")
public class RunMojo extends AbstractMojo {
    @Parameter
    private String msg;
    @Parameter(property = "options")
    private String[] options;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("123");
        System.out.println(msg);
        System.out.println(options.length);
    }
}
```

4. 运行`mvn clean install`

5. 新建一个模块或工程，pom.xml中引入刚才自定义的插件

```xml
 		<plugin>
            <groupId>cn.charles</groupId>
            <artifactId>sout-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <configuration>
                <msg>hello,charles</msg>
                <options>
                    <option>111</option>
                    <option>222</option>
                </options>
            </configuration>
        </plugin>
```

6. 配置中引入@Parameter作为参数

# maven插件如何调试

IDEA调试Maven插件小技巧：把插件加入依赖中，打断点就可以了。

Todo: 以後研究

# screw配置类解析

1. DataSource  数据库连接信息

2. EngineConfig  引擎配置。freemarker还是volecity，生成文件类型，是否打开输出目录，文件产生位置，文件名

3. ProcessConfig  指定表名，指定表前缀，指定表后缀，忽略表前缀，忽略表后缀

# screw 代码解析

生成文档主要分两步

1. 生成数据
2. 根据数据产生文档

# 获取数据

有datasource的配置数据，去获取对应的数据连接对象。

根据url是否包含:mysql:来获取mysql对象class，用enum枚举获取class

然后利用class反射构造来生成数据库对象。构造函数私有， 禁止通过new方式实例化对象。

在数据库对象中查询数据

# 如何把获取到的table信息存放到实体类中
1. 获取到List<Map<>>的集合数据 
1. 封装属性和set方法到List集合中
```java
private static <T> List<FieldMethod> getFieldMethods(Class<T> clazz) throws IntrospectionException,
                                                                         NoSuchFieldException {
        //结果集合
        List<FieldMethod> fieldMethods = new ArrayList<>();
        //BeanInfo
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        //循环处理值
        for (PropertyDescriptor pd : pds) {
            Method writeMethod = pd.getWriteMethod();
            if (writeMethod == null) {
                continue;
            }
            //获取字段
            Field field = clazz.getDeclaredField(pd.getName());
            //获取只写方法
            FieldMethod fieldMethod = new FieldMethod();
            fieldMethod.setField(field);
            fieldMethod.setMethod(writeMethod);
            //放入集合
            fieldMethods.add(fieldMethod);
        }
        return fieldMethods;
    }
```
2. 将map数据封装到实体类中，**用到注解**
```java
T rsp = clazz.newInstance();
        //设置属性值
        for (FieldMethod filed : fieldMethods) {
            Field field = filed.getField();
            Method method = filed.getMethod();
            MappingField jsonField = field.getAnnotation(MappingField.class);
            if (!Objects.isNull(jsonField)) {
                method.invoke(rsp, map.get(jsonField.value()));
            }
        }
        return rsp;
```

# 双重检查锁获取连接对象

```java
private Connection getConnection() throws QueryException {
        try {
            //不为空
            if (!Objects.isNull(connection) && !connection.isClosed()) {
                return connection;
            }
            //同步代码块
            synchronized (AbstractDatabaseQuery.class) {
                //为空或者已经关闭
                if (Objects.isNull(connection) || connection.isClosed()) {
                    this.connection = this.getDataSource().getConnection();
                }
            }
            return this.connection;
        } catch (SQLException e) {
            throw ExceptionUtils.mpe(e);
        }
    }
```

# 枚举的好处

1. 保持有限的个数
2. 获取相关的信息

```java
		EngineFileType fileType = EngineFileType.valueOf("HTML");
        Class<EngineFileType> EngineFileType = fileType.getDeclaringClass();
```

# 打开文件

也可以了解下打开浏览器

```java
Runtime.getRuntime()
    .exec("cmd /c start " + getEngineConfig().getFileOutputDir());
```

# 首字母转大写和驼峰

```java
    /**
     * 下划线转驼峰
     */
    private String lineToHump(String str) {
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母转大写
     *
     * @param str {@link String}
     * @return  {@link String}
     */
    private String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
```

# freemarker生成文件

```java
	@Test
    public void t1() throws Exception{
        Configuration configuration = new Configuration(
                Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(),"/"));
        configuration.setLocale(new Locale(DEFAULT_LOCALE));
        configuration.setDefaultEncoding(DEFAULT_ENCODING);
        Template template = configuration.getTemplate("hello.ftl");
        File file = new File("D://update//hello.html");
        User user = new User("charles","123456");
        try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),DEFAULT_ENCODING))){
            template.process(user,out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
```

补充，freemarker可以生成doc文件。

# 获取版本

```java
Package pkg = ReflectionTest.class.getPackage();
Package pkg1 = Package.getPackage("java.util");
System.out.println(pkg);
System.out.println(pkg1);
```

MF文件中的版本号

# 数据优化（去除空格，转义）

```java
    public static void beanAttributeValueTrim(Object bean) {
        try {
            if (bean != null) {
                //获取所有的字段包括public,private,protected,private
                Field[] fields = bean.getClass().getDeclaredFields();
                for (Field f : fields) {
                    if ("java.lang.String".equals(f.getType().getName())) {
                        //获取字段名
                        String key = f.getName();
                        Object value = getFieldValue(bean, key);

                        if (value == null) {
                            continue;
                        }
                        setFieldValue(bean, key, value.toString().trim());
                    }
                }
            }
        } catch (Exception e) {
            throw ExceptionUtils.mpe(e);
        }
    }

    private static Object getFieldValue(Object bean, String fieldName) throws Exception {
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);

        Object rObject;
        Method method;

        @SuppressWarnings("rawtypes")
        Class[] classArr = new Class[0];
        method = bean.getClass().getMethod(methodName, classArr);
        rObject = method.invoke(bean);

        return rObject;
    }
```

# 实体类A的数据转为实体类B

```java
public static <TSource, TDestination> TDestination mapping(TSource source,
                                                           TDestination destination) {
    if (!Objects.isNull(source)) {
        Method[] srcMethods = source.getClass().getMethods();
        Method[] destMethods = destination.getClass().getMethods();
        for (Method m : srcMethods) {
            String srcMethodName = m.getName();
            //调用get方法
            if (srcMethodName.startsWith("get")) {
                try {
                    //获取当前方法返回值(获取当前属性值)
                    Object getValue = m.invoke(source);
                    for (Method dm : destMethods) {
                        //目标方法名称
                        String destMethodName = dm.getName();
                        if (destMethodName.startsWith("set")
                            && destMethodName.substring(3).equals(srcMethodName.substring(3))) {
                            dm.invoke(destination, getValue);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return destination;
    }
    return null;
}
```

# 获取项目地址

```java
    @Test
    public void t2(){
        String property = System.getProperty("user.dir");
        System.out.println(property);
    }
```

# 创意想法

1. 可以把打包java程序做成maven插件
2. 学习的思维：如何利用现有的东西进行改造，为我所用
3. 如何生成wsdl接口的文档

