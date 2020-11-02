/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculator;
//Se importan las clases utilizadas como la Clase PilaADT, la Clase PilaA y la Clase RevisorParentesis
import ClasesUtiles.PilaA;
import ClasesUtiles.PilaADT;
import ClasesUtiles.RevisorParentesis;

/**
 * <pre>
 * Se define un jFrame que contará con diversos métodos y atributos que le permitirán a la calculadora realizar sus funciones.
 * La calculadora es capaz de revisar si tiene balanceado el número de paréntesis.
 * Puede extraer el texto del jTextField y convertirlo a una expresión "Postfija".
 * Para evaluar la expresión estando en "Postfija" la calculadora asigna a los operadores un número de prioridad.
 * En caso de que no tenga errores, podrá regresar el resultado en el jTextField de la calculadora.
 * Convierte el texto ingresado en la calculadora en forma infija a un arreglo de String para poder facilitar el manejo de la cadena.
 * </pre>
 * @author Amanda Velasco Gallardo - Brandon Francisco Hernández Troncoso - Ana Silvia Reyes Perez - Eduardo Javier Cornejo Becerril
 * @version 3.2
 */

public class Calculator extends javax.swing.JFrame {

    /**
     * Atributos de la jFrame.
     * Entrada: Recibirá el texto ingresado al jTextField.
     * Resultado: Almacenará el resultado de la operación realizada.
     */
    private String entrada;//Atributo String que almacenará la cadena en infija
    private double resultado;//Atributo en double que almacenará el resultado de las operaciones.
    /**
     * Creates new form NewJFrame
     */
    public Calculator() {
        initComponents();
    }
    
    /**
     * Método que permite revisar si la cantidad de parentesis en una expresión dada es el mismo tanto izauierdos como derechos.
     * @param texto: Se espera un String.
     * @return boolean <ul>
     * <li> True: si el número de parentesis derechos es el mismo al de paréntesis izauierdos. </li>
     * <li> Flase: si el número de parentesis derechos es diferente al de paréntesis izquierdos. </li>
     * </ul>
     */
    public boolean revisarParentesis(String texto){
        RevisorParentesis revisor = new RevisorParentesis(texto);//Se instancía un objeto de la clase ReivsorParentesis
        return revisor.igualNumero();                              //y se manda llamar su metodo de "igualNumero" que arroja 
    }                                                              //una respuesta boolean de true o false.
    
    /**
     * Método que permite convertir un arreglo en formato infija a un arreglo en formato de postfija.
     * @param cadenaEnInfija: Se espera un arreglo de String.
     * @return String[]
     */
    public String[] infijaPostfija(String cadenaEnInfija[]){
        PilaADT<String> pilaAux = new PilaA();//Se declara una pila auxiliar.
        String postfija[] = new String[cadenaEnInfija.length]; //Se declara un arreglo que almacenará el arreglo en postfija.
        int i=0;
        int j=0;//Contadores
        while(i<cadenaEnInfija.length){
            if(cadenaEnInfija[i].equals("("))//Si es parentesis izquierdo
                pilaAux.push(cadenaEnInfija[i]);
            else if(cadenaEnInfija[i].equals(")")){//Si es parentesis derecho
                    while (!pilaAux.peek().equals("(")){
                        postfija[j] = pilaAux.pop();
                        j++;
                    }
                    pilaAux.pop();
                }
            else if (!cadenaEnInfija[i].equals("+") && !cadenaEnInfija[i].equals("-") && !cadenaEnInfija[i].equals("*") && !cadenaEnInfija[i].equals("/")){ //En caso de que no sea ningún operador
                        postfija[j] = cadenaEnInfija[i];
                        j++;
                    }
            else {
                while (!pilaAux.isEmpty() && asignaPrioridadOperador(pilaAux.peek()) > asignaPrioridadOperador(cadenaEnInfija[i])) {//Si es operador, evaluar su prioridad
                    postfija[j] = pilaAux.pop();
                    j++;
                }
                pilaAux.push(cadenaEnInfija[i]);
            }
            i++;
        }
        while (!pilaAux.isEmpty()) { //Almacenar los elementos restantes de la pila en el arreglo en postfija.
            postfija[j] = pilaAux.pop();
            j++;
        }
        return postfija;
    }
    
    /**
     * Método que permite asignar la prioridad a un dato si es operador, o un default si es cualquier otra cosa.
     * @param dato: Se espera un String
     * @return int(prioridad)
     */
    public int asignaPrioridadOperador(String dato){
        int prioridad;
        if(dato.charAt(0) == '+' || dato.charAt(0) == '-')//Si es suma o resta
            prioridad=1;
        else if(dato.charAt(0) == '*' || dato.charAt(0) == '/')//Si es multiplicacion o division
            prioridad=2;
        else //Si es otra cosa que no sea un operador
            prioridad=0;
        return prioridad;
    }
    
    /**
     * Método que permite obtener un resultado completo y ejecutar todos los métodos que le ayudan a cumplir el funcionamiento.
     * @return boolean <ul>
     * <li> True: si se pudo evaluar completamente la expresión.</li>
     * <li> False: si no se pudo evaluar correctamente la expresión. </li>
     * </ul>
     */
    public boolean sacarResultado(){
        boolean condicion=revisarParentesis(entrada); //se manda llamar la función de "revisar paréntesis".
        if (condicion){//Si está balanceada la expresión en paréntesis.
            String cadenaEnArreglo[] = convertirCadenaAUnArreglo(entrada); //Se manda llamar el método de convertir la cadena a arreglo.
            String cadenaPostfija[] = infijaPostfija(cadenaEnArreglo); //Se manda llamar el método que convierte de infija a postfija
            resultado = calculaResultado(cadenaPostfija);//Se evalúa el resultado de la postfija y se almacena en resultado
        }
        return condicion;
    }
    
    /**
     * Método que permite evaluar una cadena en Postfija tomando en cuenta operadores y paréntesis.
     * @param postfija: Se espera un arreglo de String.
     * @return double(resultado)
     */
    public double calculaResultado(String postfija[]){
        double resultado=0;
        double num1, num2;
        int i=0;//Contador
        PilaADT<Double> pilaAux = new PilaA();//Declaración de una pila auxiliar.
        while (i<postfija.length && postfija[i]!=null){ //En caso de que el contador sea menor al tamaño del arreglo y el elemento del arreglo no sea nulo
            if (!postfija[i].equals("+") && !postfija[i].equals("-") && !postfija[i].equals("*") && !postfija[i].equals("/"))//Si no es operador
                pilaAux.push(Double.valueOf(postfija[i]));
            else {//Si es operador, se evalúan los números.
                num2=pilaAux.pop();
                num1=pilaAux.pop();
                if(postfija[i].charAt(0)== '+')//suma
                    resultado = num1 + num2;
                else if(postfija[i].charAt(0)== '-')//resta
                    resultado = num1 - num2;
                else if(postfija[i].charAt(0)== '*')//multiplicación
                    resultado = num1 * num2;
                else if(postfija[i].charAt(0)== '/'){//división
                    if (num2 == 0)
                        throw new RuntimeException();//Se lanza una excepción de error para que no truene el programa.
                    resultado = num1 / num2;
                }
                pilaAux.push(resultado);                        
                }
            i++;
        }
          return pilaAux.pop();            
        }    
    
    /**
     * Método que permite convertir una cadena de String a un arreglo separado por un caracter especial.
     * @param entrada: Se espera una cadena de String.
     * @return String[]
     */
    public String[] convertirCadenaAUnArreglo(String entrada){
        return entrada.split(" "); //Se utiliza a split para devolver la cadena en arreglo con espacios.
    }
    
    /**
     * Método que permite poner en estado enable o disable los operadores dependiendo de la variable que se le ingrese como parámetro.
     * @param var: Se espera un boolean
     */
    public void enableOperadores(boolean var){
        //Se ponen enable o disable segun la variable sea true o false
        Suma.setEnabled(var);
        Resta.setEnabled(var);
        Mult.setEnabled(var);
        Div.setEnabled(var);
    }
    
    /**
     * Método que permite poner en estado enable o disable los paréntesis dependiendo de la variable que se le ingrese como parámetro.
     * @param var: Se espera un boolean.
     */
    public void enableParentesis(boolean var){
        //Se ponen enable o disable segun la variable sea true o false
        parentIzq.setEnabled(var);
        parentDer.setEnabled(var);
    }
    
    /**
     * Método que permite bloquear todos los elementos de la interfaz gráfica dependiendo de la variable que se le ingrese.
     * @param var: Se espera un boolean.
     */
    public void bloqueaTodo(boolean var){
        //Se ponen enable o disable segun la variable sea true o false
        Uno.setEnabled(var);
        Dos.setEnabled(var);
        Tres.setEnabled(var);
        Cuatro.setEnabled(var);
        Cinco.setEnabled(var);
        Seis.setEnabled(var);
        Siete.setEnabled(var);
        Ocho.setEnabled(var);
        Nueve.setEnabled(var);
        Cero.setEnabled(var);
        Suma.setEnabled(var);
        Resta.setEnabled(var);
        Mult.setEnabled(var);
        Div.setEnabled(var);
        Igual.setEnabled(var);
        parentDer.setEnabled(var);
        parentIzq.setEnabled(var);
        Neg.setEnabled(var);
        Punto.setEnabled(var);
    }
    
    /**
     * Método que permite desbloquear elementos de la interfaz gráfica dependiendo de la variable que se le ingrese.
     * @param var: Se espera un boolean.
     */
    
    public void desbloqueaC(boolean var){
//Se ponen enable o disable segun la variable sea true o false
        Uno.setEnabled(var);
        Dos.setEnabled(var);
        Tres.setEnabled(var);
        Cuatro.setEnabled(var);
        Cinco.setEnabled(var);
        Seis.setEnabled(var);
        Siete.setEnabled(var);
        Ocho.setEnabled(var);
        Nueve.setEnabled(var);
        Cero.setEnabled(var);
        Punto.setEnabled(var);
        Igual.setEnabled(var);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jTextField1 = new javax.swing.JTextField();
        Uno = new javax.swing.JButton();
        Dos = new javax.swing.JButton();
        Tres = new javax.swing.JButton();
        Cuatro = new javax.swing.JButton();
        Cinco = new javax.swing.JButton();
        Siete = new javax.swing.JButton();
        Ocho = new javax.swing.JButton();
        parentIzq = new javax.swing.JButton();
        parentDer = new javax.swing.JButton();
        Suma = new javax.swing.JButton();
        Resta = new javax.swing.JButton();
        Mult = new javax.swing.JButton();
        Seis = new javax.swing.JButton();
        Nueve = new javax.swing.JButton();
        Cero = new javax.swing.JButton();
        Div = new javax.swing.JButton();
        Punto = new javax.swing.JButton();
        Borrar = new javax.swing.JButton();
        Neg = new javax.swing.JButton();
        Igual = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(600, 340));

        jTextField1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jTextField1.setFocusable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        Uno.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Uno.setText("1");
        Uno.setName("Boton1"); // NOI18N
        Uno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnoActionPerformed(evt);
            }
        });

        Dos.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Dos.setText("2");
        Dos.setName("Boton2"); // NOI18N
        Dos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DosActionPerformed(evt);
            }
        });

        Tres.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Tres.setText("3");
        Tres.setName("Boton3"); // NOI18N
        Tres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TresActionPerformed(evt);
            }
        });

        Cuatro.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Cuatro.setText("4");
        Cuatro.setName("Boton4"); // NOI18N
        Cuatro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CuatroActionPerformed(evt);
            }
        });

        Cinco.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Cinco.setText("5");
        Cinco.setName("Boton5"); // NOI18N
        Cinco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CincoActionPerformed(evt);
            }
        });

        Siete.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Siete.setText("7");
        Siete.setName("Boton7"); // NOI18N
        Siete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SieteActionPerformed(evt);
            }
        });

        Ocho.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Ocho.setText("8");
        Ocho.setName("Boton8"); // NOI18N
        Ocho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OchoActionPerformed(evt);
            }
        });

        parentIzq.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        parentIzq.setText("(");
        parentIzq.setName("ParentIzq"); // NOI18N
        parentIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentIzqActionPerformed(evt);
            }
        });

        parentDer.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        parentDer.setText(")");
        parentDer.setName("parentDer"); // NOI18N
        parentDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentDerActionPerformed(evt);
            }
        });

        Suma.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Suma.setText("+");
        Suma.setEnabled(false);
        Suma.setName("Suma"); // NOI18N
        Suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SumaActionPerformed(evt);
            }
        });

        Resta.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Resta.setText("-");
        Resta.setEnabled(false);
        Resta.setName("Resta"); // NOI18N
        Resta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestaActionPerformed(evt);
            }
        });

        Mult.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Mult.setText("x");
        Mult.setEnabled(false);
        Mult.setName("Multiplicacion"); // NOI18N
        Mult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MultActionPerformed(evt);
            }
        });

        Seis.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Seis.setText("6");
        Seis.setName("Boton6"); // NOI18N
        Seis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeisActionPerformed(evt);
            }
        });

        Nueve.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Nueve.setText("9");
        Nueve.setName("Boton9"); // NOI18N
        Nueve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NueveActionPerformed(evt);
            }
        });

        Cero.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Cero.setText("0");
        Cero.setName("Boton0"); // NOI18N
        Cero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CeroActionPerformed(evt);
            }
        });

        Div.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Div.setText("/");
        Div.setEnabled(false);
        Div.setName("Multiplicacion"); // NOI18N
        Div.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DivActionPerformed(evt);
            }
        });

        Punto.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Punto.setText(".");
        Punto.setName("Punto"); // NOI18N
        Punto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuntoActionPerformed(evt);
            }
        });

        Borrar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Borrar.setText("C");
        Borrar.setName("Borrar"); // NOI18N
        Borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarActionPerformed(evt);
            }
        });

        Neg.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        Neg.setText("+/-");
        Neg.setName("neg"); // NOI18N
        Neg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NegActionPerformed(evt);
            }
        });

        Igual.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        Igual.setText("=");
        Igual.setName("Igual"); // NOI18N
        Igual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IgualActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Uno, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Dos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Tres, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Cuatro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Cinco, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Siete, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Ocho, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(parentIzq, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(parentDer, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Suma, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Resta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Mult, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Seis, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Nueve, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Cero, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Div, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Punto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Borrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Neg, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Igual, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Igual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(Cuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Cinco, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Seis, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Mult, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(Uno, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Dos, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Tres, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Suma, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Resta, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Div, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Cero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(Siete, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Ocho, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(Nueve, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(parentIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(parentDer, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(Punto, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Neg, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Uno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Dos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tres, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Suma, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Resta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cinco, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Seis, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Mult, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Div, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Siete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ocho, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Nueve, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(parentIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(parentDer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Punto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Neg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cero, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Igual, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void UnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnoActionPerformed
    enableOperadores(true);
    jTextField1.setText(jTextField1.getText()+"1");
    }//GEN-LAST:event_UnoActionPerformed

    private void IgualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IgualActionPerformed
    entrada=jTextField1.getText();
    
        try{
            if (sacarResultado())
                jTextField1.setText(""+resultado);
            else
                jTextField1.setText("SyntaxError");
        } catch (Exception e){
            jTextField1.setText("MathError");
        }
    bloqueaTodo(false);
    }//GEN-LAST:event_IgualActionPerformed

    private void SumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SumaActionPerformed
    enableOperadores(false);  
    enableParentesis(true);
    jTextField1.setText(jTextField1.getText()+" + ");
    Neg.setEnabled(true);// TODO add your handling code here:
    }//GEN-LAST:event_SumaActionPerformed

    private void SeisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeisActionPerformed
    enableOperadores(true);        // TODO add your handling code here:
    jTextField1.setText(jTextField1.getText()+"6");
    }//GEN-LAST:event_SeisActionPerformed

    private void DosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DosActionPerformed
    enableOperadores(true);  
    jTextField1.setText(jTextField1.getText()+"2");// TODO add your handling code here:
    }//GEN-LAST:event_DosActionPerformed

    private void TresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TresActionPerformed
    enableOperadores(true);  
    jTextField1.setText(jTextField1.getText()+"3");// TODO add your handling code here:
    }//GEN-LAST:event_TresActionPerformed

    private void CuatroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CuatroActionPerformed
    enableOperadores(true);
    jTextField1.setText(jTextField1.getText()+"4");
    // TODO add your handling code here:
    }//GEN-LAST:event_CuatroActionPerformed

    private void CincoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CincoActionPerformed
    enableOperadores(true);
    jTextField1.setText(jTextField1.getText()+"5");// TODO add your handling code here:
    }//GEN-LAST:event_CincoActionPerformed

    private void SieteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SieteActionPerformed
    enableOperadores(true);        // TODO add your handling code here:
    jTextField1.setText(jTextField1.getText()+"7");
    }//GEN-LAST:event_SieteActionPerformed

    private void OchoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OchoActionPerformed
    enableOperadores(true);        // TODO add your handling code here:
    jTextField1.setText(jTextField1.getText()+"8");
    }//GEN-LAST:event_OchoActionPerformed

    private void NueveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NueveActionPerformed
    enableOperadores(true);        // TODO add your handling code here
    jTextField1.setText(jTextField1.getText()+"9");
    }//GEN-LAST:event_NueveActionPerformed

    private void parentIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentIzqActionPerformed
    enableOperadores(false); // TODO add your handling code here:
    jTextField1.setText(jTextField1.getText()+"( ");
    Neg.setEnabled(true);
    }//GEN-LAST:event_parentIzqActionPerformed

    private void parentDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentDerActionPerformed
    enableOperadores(true);
    jTextField1.setText(jTextField1.getText()+" )");// TODO add your handling code here:
    Neg.setEnabled(true);
    }//GEN-LAST:event_parentDerActionPerformed

    private void NegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NegActionPerformed
    enableOperadores(false); 
    enableParentesis(false);
    jTextField1.setText(jTextField1.getText()+"-");
    Neg.setEnabled(false);// TODO add your handling code here:
    }//GEN-LAST:event_NegActionPerformed

    private void PuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuntoActionPerformed
    enableOperadores(false);  
    enableParentesis(false);
    Neg.setEnabled(false);
    jTextField1.setText(jTextField1.getText()+".");
    // TODO add your handling code here:
    }//GEN-LAST:event_PuntoActionPerformed

    private void CeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CeroActionPerformed
    enableOperadores(true);
    jTextField1.setText(jTextField1.getText()+"0");
    }//GEN-LAST:event_CeroActionPerformed

    private void BorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarActionPerformed
    enableParentesis(true);       // TODO add your handling code here:;
    enableOperadores(false);
    jTextField1.setText("");
    desbloqueaC(true);
    entrada=null;
    }//GEN-LAST:event_BorrarActionPerformed

    private void MultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MultActionPerformed
    enableOperadores(false);        // TODO add your handling code here:
    enableParentesis(true);
    jTextField1.setText(jTextField1.getText()+" * ");
    Neg.setEnabled(true);
    }//GEN-LAST:event_MultActionPerformed

    private void RestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestaActionPerformed
    enableOperadores(false);        // TODO add your handling code here:
    enableParentesis(true);
    jTextField1.setText(jTextField1.getText()+" - ");
    Neg.setEnabled(true);
    }//GEN-LAST:event_RestaActionPerformed

    private void DivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DivActionPerformed
    enableOperadores(false);        // TODO add your handling code here:
    enableParentesis(true);
    jTextField1.setText(jTextField1.getText()+" / ");
    Neg.setEnabled(true);
    }//GEN-LAST:event_DivActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Borrar;
    private javax.swing.JButton Cero;
    private javax.swing.JButton Cinco;
    private javax.swing.JButton Cuatro;
    private javax.swing.JButton Div;
    private javax.swing.JButton Dos;
    private javax.swing.JButton Igual;
    private javax.swing.JButton Mult;
    private javax.swing.JButton Neg;
    private javax.swing.JButton Nueve;
    private javax.swing.JButton Ocho;
    private javax.swing.JButton Punto;
    private javax.swing.JButton Resta;
    private javax.swing.JButton Seis;
    private javax.swing.JButton Siete;
    private javax.swing.JButton Suma;
    private javax.swing.JButton Tres;
    private javax.swing.JButton Uno;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton parentDer;
    private javax.swing.JButton parentIzq;
    // End of variables declaration//GEN-END:variables
}
