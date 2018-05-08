
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nguye
 */
public class ProfileImpl extends UnicastRemoteObject implements Profile{
    public ProfileImpl() throws RemoteException{}

    @Override
    public NhanVien getProfile(String id) throws RemoteException {
        NhanVien nv = new NhanVien();
        try {
            Connection.openConnection();
            Statement stmt = Connection.conn.createStatement();
            String sql = "select * from user,nhanvien where user.MaNV= nhanvien.MaNV AND nhanvien.MaNV="+id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                nv.setMaNV(rs.getString("MaNV"));
                nv.setUsername(rs.getString("username"));
                nv.setHo(rs.getString("Ho"));
                nv.setTen(rs.getString("Ten"));
                nv.setEmail(rs.getString("email"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setTinh(rs.getString("Tinh"));
                nv.setNuoc(rs.getString("Nuoc"));
                nv.setCode(rs.getInt("Code"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setNgaySinh(rs.getString("Ngaysinh"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setBacLuong(rs.getInt("BacLuong"));
                nv.setMaCV(rs.getString("MaCV"));
                nv.setRole(rs.getInt("role"));  
            }
            Connection.conn.close();
        } catch (Exception e) {
        }
        return nv;
    }

    @Override
    public void editProfile(NhanVien nv) throws RemoteException {       
        try {
            Connection.openConnection();
            Statement stmt = Connection.conn.createStatement();
            String sql = "UPDATE user,nhanvien SET user.email =?,user.username =?,nhanvien.Ho=?,nhanvien.Ten=?,nhanvien.DiaChi=?,nhanvien.Tinh=?,nhanvien.Nuoc=?,nhanvien.Code=?,nhanvien.NgaySinh=?,nhanvien.SoDienThoai=?,nhanvien.BacLuong=?,nhanvien.MaCV=?,nhanvien.GioiTinh=? WHERE user.MaNV = nhanvien.MaNV AND user.MaNV =? ";
            PreparedStatement psmt = Connection.conn.prepareStatement(sql);
            
            psmt.setString(1, nv.email);
            psmt.setString(2, nv.username);
            psmt.setString(3, nv.Ho);
            psmt.setString(4, nv.Ten);
            psmt.setString(5, nv.DiaChi);
            psmt.setString(6, nv.Tinh);
            psmt.setString(7, nv.Nuoc);
            psmt.setInt(8, nv.Code);
            psmt.setString(9, nv.NgaySinh);
            psmt.setString(10, nv.SoDienThoai);
            psmt.setInt(11, nv.BacLuong);
            psmt.setString(12, nv.MaCV);
            psmt.setString(13, nv.GioiTinh);
            psmt.setString(14,nv.MaNV);        
            psmt.executeUpdate();
            System.out.println(psmt.executeUpdate());
            Connection.conn.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void creatProfile(NhanVien tk) throws RemoteException {
        try {
            Connection.openConnection();
            String sql = "START TRANSACTION;"
                    + "INSERT INTO user (MaNV,username,password,email,role) VALUES (?,?,?,?,?);"
                    + "INSERT INTO nhanvien (MaNV) VALUES (?);"
                    + "COMMIT;";
            PreparedStatement psmt = Connection.conn.prepareStatement(sql);
            psmt.setString(1, tk.MaNV);
            psmt.setString(2, tk.username);
            psmt.setString(3, new MD5Hash().md5(tk.password));
            psmt.setString(4, tk.email);
            psmt.setInt(5, tk.role);
            psmt.setString(6, tk.MaNV);
            psmt.executeUpdate();
            Connection.conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    @Override
    public void deleteProfile(NhanVien nv) throws RemoteException {
        try {
            Connection.openConnection();
            String sql = "START TRANSACTION;"
                    + "DELETE FROM user WHERE user.MaNV=?;"
                    + "DELETE FROM nhanvien WHERE nhanvien.MaNV=?;"
                    + "COMMIT;";
            PreparedStatement psmt = Connection.conn.prepareStatement(sql);
            psmt.setString(1, nv.MaNV);
            psmt.setString(2, nv.MaNV);
            psmt.execute();;
            Connection.conn.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void editUserProfile(NhanVien nv) throws RemoteException {
        try {
            Connection.openConnection();
            String sql = "UPDATE nhanvien SET nhanvien.Ho=?,nhanvien.Ten=?,nhanvien.DiaChi=?,nhanvien.Tinh=?,nhanvien.Nuoc=?,nhanvien.Code=?,nhanvien.SoDienThoai=?,nhanvien.GioiTinh=? WHERE  nhanvien.MaNV =? ";
            System.out.println(sql);
            System.out.println(nv.Ten);
            PreparedStatement psmt = Connection.conn.prepareStatement(sql);
            psmt.setString(1, nv.Ho);
            psmt.setString(2, nv.Ten);
            psmt.setString(3, nv.DiaChi);
            psmt.setString(4, nv.Tinh);
            psmt.setString(5, nv.Nuoc);
            psmt.setInt(6, nv.Code);
            psmt.setString(7, nv.SoDienThoai);
//            psmt.setInt(8, nv.BacLuong);
//            psmt.setString(9, nv.MaCV);
            psmt.setString(8, nv.GioiTinh);
            psmt.setString(9,nv.MaNV);        
            psmt.executeUpdate();
            Connection.conn.close();
        } catch (Exception e) {
        }
    }

    @Override
    public List<NhanVien> getAll() throws RemoteException {
        List<NhanVien>  arr = new ArrayList<NhanVien>();
        try {
            Connection.openConnection();
            Statement stmt = Connection.conn.createStatement();
            String sql = "SELECT * FROM user,nhanvien WHERE user.MaNV = nhanvien.MaNV";
            ResultSet rs = stmt.executeQuery(sql);
// Fetch each row from the result set
            while (rs.next()) {
               
              String manv = rs.getString("MaNV");
              String username = rs.getString("username");
              String password = rs.getString("password");
              String email = rs.getString("email");
              int role = Integer.parseInt(rs.getString("role"));
              String ho = rs.getString("Ho");
              String ten = rs.getString("Ten");
              String gioitinh = rs.getString("GioiTinh");
              String diachi = rs.getString("DiaChi");
              String tinh = rs.getString("Tinh");
              String nuoc = rs.getString("Nuoc");
              int code = rs.getInt("Code");
              String ngaysinh = rs.getString("NgaySinh");
              String sodienthoai = rs.getString("SoDienThoai");
              int bacluong = rs.getInt("BacLuong");
              String macv = rs.getString("MaCV");
//               System.out.println(rs.getString("MaNV"));
              //Assuming you have a user object
              NhanVien user = new NhanVien();
              user.setMaNV(manv);
              user.setUsername(username);
              user.setPassword(password);
              user.setEmail(email);
              user.setRole(role);
              user.setHo(ho);
              user.setTen(ten);
              user.setGioiTinh(gioitinh);
              user.setDiaChi(diachi);
              user.setTinh(tinh);
              user.setNuoc(nuoc);
              user.setCode(code);
              user.setNgaySinh(ngaysinh);
              user.setSoDienThoai(sodienthoai);
              user.setBacLuong(bacluong);
              user.setMaCV(macv);
              arr.add(user);
            }
        } catch (Exception e) {
        }
//        System.out.println(arr.size());
        return arr;
    }
  

    @Override
    public void editAccount(NhanVien tk) throws RemoteException {
        try {
            Connection.openConnection();
            String sql = "UPDATE user SET user.username = ?,user.password=?,user.email=?,user.role=? WHERE user.MaNV = ? ;";
            PreparedStatement psmt = Connection.conn.prepareStatement(sql); 
            psmt.setString(1, tk.username);
            psmt.setString(2, new MD5Hash().md5(tk.password));
            psmt.setString(3, tk.email);
            psmt.setInt(4, tk.role);
            psmt.setString(5, tk.MaNV);
            psmt.executeUpdate();
            Connection.conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    public static void main(String[] args) {
//        MD5Hash md5 = new MD5Hash();
//        String a = md5.md5("dsdsd");
//        System.out.println(a);
        
    }
}
