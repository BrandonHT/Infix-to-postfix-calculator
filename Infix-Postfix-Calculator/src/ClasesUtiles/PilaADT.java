package ClasesUtiles;

public interface PilaADT<T> {
    public void push(T dato);
    public T pop();
    public T peek();
    public boolean isEmpty();
    
}
