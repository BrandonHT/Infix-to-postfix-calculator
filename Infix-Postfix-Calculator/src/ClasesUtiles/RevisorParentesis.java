package ClasesUtiles;

public class RevisorParentesis {
    private String expresion;

    public RevisorParentesis() {
    }

    public RevisorParentesis(String expresion) {
        this.expresion = expresion;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }
    
    public boolean igualNumero(){
        boolean resp=false;
        int n=expresion.length();
        if(n>0){
            PilaA<Character> p=new PilaA(n);
            int i=0;
            char c;
            while(i<n){
                c=expresion.charAt(i);
                if(c=='(')
                    p.push(c);
                else
                    if(c==')')
                        try{
                            p.pop();
                        } catch(EmptyCollectionException e){
                        i=n;
                        }
                i++;
            }
            resp=i==n && p.isEmpty();
        }
        return resp;
    }
    
    
}
