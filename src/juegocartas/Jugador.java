/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegocartas;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author HP LAPTOP
 */
public class Jugador {
    private int TOTAL_CARTAS = 10;
    private int MARGEN= 20;
    private int DISTANCIA = 50;
    
    
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    
    private Random r = new Random();
    
    
    
    
    public void repartir(){
        for(int i = 0; i < TOTAL_CARTAS; i++){
            cartas[i] = new Carta(r);
        }
    }
    
    public void mostrar(JPanel pnl){
        pnl.removeAll();
        int x = MARGEN;
        for(Carta c: cartas){
            c.mostrar(pnl, x, MARGEN);
            
            x += DISTANCIA;
        }
        pnl.repaint();
    }
    
    public String getGrupos(){
        String mensaje = "No se encontraron grupos";
        int[] contadores = new int[NombreCarta.values().length];
        for(Carta c: cartas){
            contadores[c.getNombre().ordinal()]++; //devuelve un enumerado, pregunta por la posición
        }
        int totalGrupos=0;
        for(int c:contadores){
            if(c>=2){
                totalGrupos++;
            }        
        }
        if(totalGrupos>0){
            mensaje="Los grupos encontrados fueron:\n";
            for(int i = 0; i<contadores.length; i++){
                if(contadores[i]>=2){
                    mensaje += Grupo.values()[contadores[i]]+" de "+ NombreCarta.values()[i] + "\n";
                }
            }
        }
        return mensaje;        
    }
    
    public void ordenarCartas(){
        Arrays.sort(cartas, Comparator.comparingInt(Carta::getIndice));
        
    }
    
    //public ArrayList<Carta>
    



    public String getEscaleras() {
        String mensaje = "";
        Arrays.sort(cartas, Comparator.comparingInt(Carta::getIndice));

        for (Pinta pinta : Pinta.values()) {
            ArrayList<Carta> arrayPinta = new ArrayList<>();
            for (Carta c : cartas) {
                if (c.getPinta().equals(pinta)) {
                    arrayPinta.add(c);
                }
            }

            // Crear un LinkedHashSet a partir del ArrayList para eliminar duplicados y mantener el orden
            Set<Carta> set = new LinkedHashSet<>(arrayPinta);

            // Limpiar el ArrayList original
            arrayPinta.clear();

            // Agregar los elementos del LinkedHashSet de vuelta al ArrayList para mantener el orden y eliminar duplicados
            arrayPinta.addAll(set);

            int inicio = 0;
            ArrayList<int[]> matrizEscaleras = new ArrayList<>();
            for (int j = 1; j < arrayPinta.size(); j++) {
                if (arrayPinta.get(j).getIndice() != arrayPinta.get(j - 1).getIndice() + 1) {
                    if (j - inicio > 1) {  // Si hay más de un elemento consecutivo
                        int longitud = j - inicio;
                        int[] conjunto = new int[longitud];

                        // Copiar los elementos del arreglo original al nuevo arreglo 'conjunto'
                        for (int k = 0; k < longitud; k++) {
                            conjunto[k] = arrayPinta.get(k + inicio).getIndice();
                        }

                        matrizEscaleras.add(conjunto);
                    }
                    inicio = j;
                }
            }

            for (int i = 0; i < matrizEscaleras.size(); i++) {
                mensaje += "\nEscalera " + pinta.name() + ":";
                for (int j = 0; j < matrizEscaleras.get(i).length; j++) {
                    Carta c = new Carta(r);
                    c.setIndice(matrizEscaleras.get(i)[j]);
                    mensaje += " " + c.getNombre().toString() + " ";
                }
            }
            matrizEscaleras.clear();
            arrayPinta.clear();
        }

        return mensaje;
    }
}


