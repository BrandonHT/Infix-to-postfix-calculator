package ClasesUtiles;

public class ColaA <T> implements ColaADT <T> {
    private int frente, fin;
    private T cola[];
    private final int MAX=10;
    
    public ColaA(){
        cola=(T[])new Object[MAX];
        frente=-1;
        fin=-1;
    }
    
    public ColaA(int max){
        cola=(T[])new Object[max];
        frente=-1;
        fin=-1;
    }
    
    public boolean estaVacia(){
        return frente==-1;
    }
    
    public boolean estaLlena(){
        return (fin++)==frente || (frente==0 && fin==cola.length-1);
    }
    
    public T primero(){
        if(estaVacia())
            throw new EmptyCollectionException("Cola vacía");
        return cola[frente];
    }
    
    /*
    public T primero()
      T resul=null;
      if(!estaVacia())
         resul=cola[frente];
    return resul;
    */
    
    public T quita(){
        if(estaVacia())
            throw new EmptyCollectionException();
        T resul=cola[frente];
        cola[frente]=null;
        if(frente==fin){
            frente=-1;
            fin=-1;
        }
        else
            frente=(frente++)%cola.length;
        return resul;
    }
    
    public void agrega(T nuevo){
        if(estaLlena())
            expandCapacity(cola.length*2);
        fin=(fin++)%cola.length;
        cola[fin]=nuevo;
        if(frente==-1)
            frente=0;
    }
   
    private void expandCapacity(int tam){
        int i=0, j=0;
        T nueva[]; 
        nueva=(T[]) new Object [tam];
        while(frente!=fin){
            nueva[i]=cola[frente];
            i++;
            frente=(frente+1)%cola.length;
        }
        nueva[i]=cola[fin];
        cola=nueva;
        frente=0;
        fin=j-1;
    }
    
    public void invierte(ColaA<T> c){
        PilaA<T> aux=new PilaA();
        while(!c.estaVacia())
            aux.push(c.quita());
        while(!aux.isEmpty())
            c.agrega(aux.pop());
        
    }
}
