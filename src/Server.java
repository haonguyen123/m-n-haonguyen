import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class Server {
    public static void main(String[] args) throws RemoteException {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
//            System.out.println(reg);
            LoginImpl log = new LoginImpl();
            ProfileImpl prof = new ProfileImpl();
            reg.rebind("login", log);
            reg.rebind("profile", prof);
            System.out.println("Start Server ......");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
        
    }

    
}
