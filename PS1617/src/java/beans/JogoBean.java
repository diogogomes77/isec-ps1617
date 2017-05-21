
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;


@Named(value = "jogoBean")
@Dependent
public class JogoBean {

    private List<List<String>> _Matrix;

    public List<List<String>> get_Matrix() {
        return this._Matrix;
    }

    public JogoBean() {
        this._Matrix = new ArrayList<List<String>>();
        this._Matrix.add(new ArrayList<String>());
        this._Matrix.add(new ArrayList<String>());
        this._Matrix.add(new ArrayList<String>());
        this._Matrix.add(new ArrayList<String>());
        this._Matrix.get(0).add("X");
        this._Matrix.get(0).add("Y");
        this._Matrix.get(0).add("X");
        this._Matrix.get(0).add("Y");
        this._Matrix.get(1).add("X");
        this._Matrix.get(1).add("Y");
        this._Matrix.get(1).add("X");
        this._Matrix.get(1).add("Y");
        this._Matrix.get(2).add("X");
        this._Matrix.get(2).add("Y");
        this._Matrix.get(2).add("X");
        this._Matrix.get(2).add("Y");
        this._Matrix.get(3).add("X");
        this._Matrix.get(3).add("Y");
        this._Matrix.get(3).add("X");
        this._Matrix.get(3).add("Y");
        
    }
    
}
