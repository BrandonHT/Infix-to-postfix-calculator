/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestCasesCalculator;

import Calculator.Calculator;
import ClasesUtiles.RevisorParentesis;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kroster
 */
public class CalculatorTest {
    
    public CalculatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of revisarParentesis method, of class Calculator.
     */
    @Test
    public void testRevisarParentesis() {
        System.out.println("revisarParentesis");
        String texto = "( 5 + 8 - ( 9 + 8 ) + )";
        boolean expResult = true;
        RevisorParentesis a = new RevisorParentesis(texto);
        assertEquals(expResult, true);
    }

    /**
     * Test of infijaPostfija method, of class Calculator.
     */
    @Test
    public void testInfijaPostfija() {
        System.out.println("infijaPostfija");
        String[] cadenaEnInfija = new String [3];
        cadenaEnInfija[0]="1";
        cadenaEnInfija[1]="+";
        cadenaEnInfija[2]="1";
        Calculator instance = new Calculator();
        String[] expResult = new String[3];
        expResult[0]="1";
        expResult[1]="1";
        expResult[2]="+";
        String[] result = instance.infijaPostfija(cadenaEnInfija);
        assertArrayEquals(expResult,result);
    }

    /**
     * Test of asignaPrioridadOperador method, of class Calculator.
     */
    @Test
    public void testAsignaPrioridadOperador() {
        System.out.println("asignaPrioridadOperador");
        String dato = "*";
        Calculator instance = new Calculator();
        int expResult = 2;
        int result = instance.asignaPrioridadOperador(dato);
        assertEquals(expResult, result);
    }

    /**
     * Test of calculaResultado method, of class Calculator.
     */
    @Test
    public void testCalculaResultado() {
        System.out.println("calculaResultado");
        String[] postfija=new String[5];
        postfija[0]="1";
        postfija[1]="2";
        postfija[2]="+";
        Calculator instance = new Calculator();
        double expResult = 3.0;
        double result = instance.calculaResultado(postfija);
        assertEquals(expResult, result, 0.5);
    }

    /**
     * Test of convertirCadenaAUnArreglo method, of class Calculator.
     */
    @Test
    public void testConvertirCadenaAUnArreglo() {
        String entrada = "5 + 8 + 7 + 6";
        Calculator instance = new Calculator();
        String a[] = new String[10];
        String[] result = instance.convertirCadenaAUnArreglo(entrada);
        boolean res=true;
        a[0]="5";
        a[1]="+";
        a[2]="8";
        a[3]="+";
        a[4]="7";
        a[5]="+";
        a[6]="6";
        int i =0;
        while(i<result.length && res){
            if(a[i].equals(result[i]))
                res=true;
            else
                res=false;
            i++;
                
        }
        assertEquals(res,true);
    }
    
}
