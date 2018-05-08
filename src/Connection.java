
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nguye
 */
public class Connection {
    public static java.sql.Connection conn;
    public static void openConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlynhanvien?allowMultiQueries=true&rewriteBatchedStatements=true&useUnicode=yes&characterEncoding=UTF-8", "haonguyen", "1234");
    }
   
}
