### 封装JDBC

- 使用方法:
```
// 导入jar包
import com.qa.MysqlTools;

String mysql = "127.0.1:3306/user";
String user = "root";
String pwd = "root";
String sqlpath = "D:\\delete.sql";

// 调用方法
MysqlTools.sqlScript(mysql, user, pwd, sqlpath);
```
