
package logica;

import java.util.ArrayList;
import javax.ejb.Local;

@Local
public interface SessaoLocal {
    
    void login(String username, String password);

    void Logout();

    String getUsername();
}
