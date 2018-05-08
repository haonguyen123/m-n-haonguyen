
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nguye
 */
public class LoginImpl extends UnicastRemoteObject implements Login{
    public LoginImpl() throws RemoteException{}
    @Override
    public NhanVien doLogin(NhanVien tk) throws RemoteException {
        NhanVien tk1 = new NhanVien();
        System.out.println(tk.MaNV+ ""+tk.password);
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            Connection.openConnection();
            Statement stmt = Connection.conn.createStatement();
            
            String sql = "select MaNV,password,role from user where MaNV='"+tk.MaNV+"'and password='"+new MD5Hash().md5(tk.password)+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tk1.setMaNV(rs.getString("MaNV"));
                tk1.setPassword(rs.getString("password"));
                tk1.setRole(rs.getInt("role"));
            }
            Connection.conn.close();
        } catch (Exception e) {
        }
        return tk1;
    }
    
}
