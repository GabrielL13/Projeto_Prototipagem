import java.util.List;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fechadura {
    private String id;
    private String password;
    private List<String> usuarios;
    private List<String> registros;

    Fechadura(){}
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<String> getRegistros() {
        return registros;
    }
    public void setRegistros(List<String> registros) {
        this.registros = registros;
    }
    public List<String> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void salvar(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Fechaduras").child(getID()).setValue(this);
    }
}
