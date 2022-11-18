package com.qa;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

/**
 * @author Tesla Liu
 * @date 2022/11/18 14:34
 * 描述
 */
public class MysqlTools {
    public static Connection conn = null;
    public static PreparedStatement statement = null;
    public static String driver = null;
    public static String url = null;
    public static String user = null;
    public static  String pwd = null;

    /**
     * 加载配置文件&&初始化
     * @param mysqlsource 数据库地址、端口、数据库名
     * @param username 登录用户名
     * @param password 登录密码
     * @return 连接结果
     */
    public static Connection getConnection(String mysqlsource, String username, String password){
        Connection connection = null;
        try {
            driver = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://" + mysqlsource + "?userSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
            user = username;
            pwd = password;
            // 加载驱动
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 调用mybatis执行sql脚本
     * @param sqlpath sql脚本路径
     */
    public static void sqlScript(String mysqlsource, String username, String password, String sqlpath) {
        Connection conn = getConnection(mysqlsource, username, password);
        ScriptRunner runner = new ScriptRunner(conn);
        Reader reader;
        try {
            reader = new FileReader(sqlpath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            runner.runScript(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * 关闭连接  释放资源
     */
    public static void closeAll(){
        // 关闭PreparedStatement对象
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 关闭Connection 对象
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
