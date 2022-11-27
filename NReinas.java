/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package TrabajoFinal;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Lara
 */
public class NReinas {
    private int tablero; //tablero de tamaño int x int
    
    public NReinas(int tablero){
        this.tablero = tablero;
    }
    
    public NReinas(){
        this(8); //Por defecto es de 8x8 para ilustrar el Problema de las 8 Reinas
    }
    
    private boolean comparteDiagonal(int k, int[] columnasOcupadas, int miColumna){
        boolean comparteDiagonal = false;
        int filaActual=0;
        int miFila = k+1;
        int resta;
        while(!comparteDiagonal && filaActual < miFila){
            resta = columnasOcupadas[filaActual] - miColumna;
            if(resta == miFila-filaActual || resta == 0 || resta == filaActual-miFila){
                comparteDiagonal = true;
            }else{
                filaActual++;
            }
        }
        return comparteDiagonal;
    }
    
    private boolean comparteColumna(int k, int[] columnasOcupadas, int miColumna){
        boolean comparteColumna = false;
        int filaActual = 0;
        int miFila = k+1;
        while(!comparteColumna && filaActual<miFila){
            if(columnasOcupadas[filaActual]==miColumna){
                comparteColumna = true;
            }else{
                filaActual++;
            }
        }
        return comparteColumna;
    }
    
    private void reinas(int k, int[] columnasOcupadas, LinkedList soluciones){
        /**
         * k representa las soluciones k-prometedoras, es decir, la cantidad de filas armadas que se confirmaron como seguras (ninguna reina se amenaza entre si)
         * columnas ocupadas representa las coordenadas de columnas que ya tienen una reina
         * soluciones almacenará todas las respuestas validas
         */
        if(k == this.tablero-1){
            //exito
            int[] solucion = new int[this.tablero]; 
            for(int i=0; i<this.tablero; i++){
                solucion[i]=columnasOcupadas[i];
            }
            soluciones.add(solucion);
        }else{
            /*Se exploran las soluciones k+1 prometedoras*/
            for(int columna = 0; columna<this.tablero; columna++){
                if(!this.comparteColumna(k, columnasOcupadas, columna) && !this.comparteDiagonal(k, columnasOcupadas, columna)){
                    columnasOcupadas[k+1] = columna; //Solucion k+1 prometedora
                    this.reinas(k+1, columnasOcupadas, soluciones);
                }
            }
        }
    }
    
    private void mostrarSolucion(int[] solucion){
        String dibujoTablero = " x ";
        for(int columna=1; columna<=this.tablero; columna++){
            dibujoTablero+=" "+columna+" ";
        }
        dibujoTablero+="\n";
        for(int fila=0; fila<this.tablero; fila++){
            dibujoTablero+=" "+(fila+1)+" ";
            for(int columna=0; columna<this.tablero; columna++){
                //"\u265B"
                if(solucion[fila]==columna){
                    dibujoTablero+="[\033[0;34m\u265B\u001B[0m]";
                }else{
                    dibujoTablero+="["+(columna+1)+"]";
                }
            }
            dibujoTablero+="\n";
        }
        System.out.println(dibujoTablero);
    }
    
    private void verSoluciones(boolean dibujar){
        LinkedList<int[]> soluciones = new LinkedList<>();
        int[] columnasOcupadas = new int[this.tablero];
        reinas(-1, columnasOcupadas, soluciones);
        ListIterator<int[]> iterador = soluciones.listIterator();
        System.out.println("Hay "+soluciones.size()+" soluciones para un tablero de: "+this.tablero+" x "+this.tablero+" celdas \n");
        if(dibujar){
            while(iterador.hasNext()){
                System.out.println("> Solución nro "+(iterador.nextIndex()+1));
                this.mostrarSolucion(iterador.next());
            }
        }
    }
    
    public void verCantidadSoluciones(){
        //Indica el numero total de soluciones
        this.verSoluciones(false);
    }
    
    public void mostrarSoluciones(){
        //Indica el numero total de soluciones y además las representa. 
        this.verSoluciones(true);
    }
}
