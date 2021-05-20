package Cola;


public class Cola {
    private Nodo primero;
    private Nodo ultimo;
    
    public Cola() {
        primero = null;
        ultimo = null;
    }
    
    public boolean vacia() {
        return primero == null;
    }
    
    public boolean llena() {
        return ultimo.siguiente == primero;
    }
    
    public void insertar(String elemento) {
        Nodo nuevo = new Nodo(elemento);
        nuevo.siguiente = nuevo;
        if(vacia()) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.siguiente = nuevo;
            nuevo.siguiente = primero;
            ultimo = nuevo;
        }
    }
    
    public String quitar() {
        String dato = null;
        if(!vacia()) {
            dato = primero.dato;
            if(primero == ultimo) {
                primero = ultimo = null;
            } else {
                primero = primero.siguiente;
                ultimo.siguiente = primero;
            }
        }
        return dato;
    }
    
    public String primerElemento() {
        if(!vacia())
            return primero.dato;
        return null;
    }
}
