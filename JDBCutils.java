package util;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;



public class JDBCutils {
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("properties");
        Properties properties = new Properties();
        properties.load(is);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        //加载驱动
        Class.forName(driverClass);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;

    }

    public static void closeResource(Connection connection, Statement preparedStatement){
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //通用的增删改操作
    //注：SQL的占位符个数与args可变形参的长度一致
    public static void update(String sql,Object...args) throws SQLException, IOException, ClassNotFoundException {
        //1.获取数据库连接
        Connection connection = JDBCutils.getConnection();
        //2.编译SQL语言
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //3.填充占位符
        for(int i= 0;i<args.length;i++){
            preparedStatement.setObject(i+1,args[i]);
        }
        //4.执行
        preparedStatement.execute();
        //5.关闭流
        JDBCutils.closeResource(connection,preparedStatement);
    }



    //针对于test表的通用查询操作
    public static ORM编程思想 queryForTest(String sql , Object ...argh) throws SQLException, IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Connection connection = JDBCutils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //填充占位符
        for (int i = 0; i <argh.length; i++) {
            preparedStatement.setObject(i+1,argh[i]);
        }
        //
        ResultSet resultSet = preparedStatement.executeQuery();
        //获取结果集的元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        //通过ResultSetMetadata获取结果集中的列（getColumnCount）
        int columnCount = metaData.getColumnCount();
        if (resultSet.next()) {
            ORM编程思想 orm = new ORM编程思想();


            //处理结果集一行数据中的每一个列
            for (int c = 0; c < columnCount; c++) {
                Object object = resultSet.getObject(c + 1);
                //获取每个列的列名
                String columnName = metaData.getColumnName(c + 1);
                //给orm对象指定某个属性赋值,通过反射
                Field declaredField = ORM编程思想.class.getDeclaredField(columnName);
                declaredField.setAccessible(true);
                declaredField.set(orm,object);

            }
            return orm;


        }
        JDBCutils.closeResource(connection,preparedStatement);
        resultSet.close();

        return null;
    }
    @Test
    public void test() throws SQLException, IOException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        String sql = "select  `name` from test where id = ?";
        ORM编程思想 orm编程思想 = queryForTest(sql, "1001");
        System.out.println(orm编程思想);
    }
}
