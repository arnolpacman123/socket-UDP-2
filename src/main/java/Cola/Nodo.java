package Cola;

public class Nodo {
    
    String dato;
    Nodo siguiente;
    
    public Nodo(String dato) {
        this.dato = dato;
        siguiente = null;
    }
    
    public Nodo() {
        this("");
    }
}
