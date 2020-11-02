package ClasesUtiles;

public interface ColaADT <T> {
    
    public void agrega(T nuevo);
    public T quita();
    public T primero();
    public boolean estaVacia();
    public boolean estaLlena();
    
}
