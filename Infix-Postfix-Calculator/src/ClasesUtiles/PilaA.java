package ClasesUtiles;

public class PilaA<T> implements PilaADT<T> {
    private T pila[];
    private int tope;
    private final int MAX=50;
    
    public PilaA(){
        pila=(T[])new Object[MAX];
        tope=-1;
    }
    
    public PilaA(int max){
        pila=(T[])new Object[max];
        tope=-1;
    }
    
    public boolean isEmpty(){
        return tope==-1;
    }
    
    public void push(T dato){
        if(tope==pila.length-1)
            expandCapacity(pila.length*2);
        tope++;
        pila[tope]=dato;
    }
    
    private void expandCapacity(int n){
        T nuevo[]=(T[])new Object[n];
        for(int i=0;i<tope;i++)
            nuevo[i]=pila[i];
        pila=nuevo;
    }
    
    public T pop(){
        if(isEmpty())
            throw new EmptyCollectionException("Pila vacía");
        else{
            T resp=pila[tope];
            pila[tope]=null;
            tope--;
            return resp;
        }
    }
    
    /*
    public T pop(){
        T resp=null;
        if(!isEmpty()){
            resp=pila[tope];
            pila[tope]=null;
            tope--;
        }
        return resp;
    }
    */
    
    public T peek(){
        if(isEmpty())
            throw new EmptyCollectionException("Pila vacía");
        else
            return pila[tope];
    }
    
}
