package org.redrock.web.Utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBCPUtil {
    private static DataSource dataSource = null;

    static {
        Properties properties = new Properties();
        try {
            Context context=new InitialContext();
            dataSource=(DataSource) context.lookup("java:/comp/env/jdbc/ChristmasGames");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("初始化错误，请检查配置文件");
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        try {
            System.out.println("从连接池中得到数据成功");
            return dataSource.getConnection();
        }catch (SQLException e){
            throw new RuntimeException("服务器忙");
        }
    }
    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        if (resultSet!=null){
            try{
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resultSet=null;
        }
        if (statement!=null){
            try{
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement=null;
        }
        if (connection!=null){
            try{
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection=null;
        }
    }
}
