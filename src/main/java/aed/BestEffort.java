package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    private Integer[][] ciudades;
    private Integer [] masGanancia;
    private Integer [] masPerdida;
    private Integer [] trasladosYGananciasHistoricas;                                  //Lista con dos elementos donde el primero refiere a la cantidad de traslados que se hicieron y el segundo a las ganancias totales
    private Heap<Integer[]> mayorSuperavit;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        ciudades = new Integer[cantCiudades][];
        masGanancia = new Integer[cantCiudades];
        masPerdida = new Integer[cantCiudades];
        int i = 0;

        Comparator<Integer[]> porSuperavit = new Comparator<Integer[]>() {                         //Esto si se puede habria que hacerlo en otro lugar
            public int compare (Integer[] elem1,Integer[] elem2){
                if (elem1[1] > elem2[1] || (elem1[1] == elem2[1] && elem1[0] > elem2[0])){
                    return 1;
                }
                return -1;
            }
        };

        mayorSuperavit = new Heap<Integer[]>(new ArrayList<Integer[]>(),porSuperavit);

        while (i < cantCiudades) {                                                        // O(|C|)
            Integer [] infoCiudad = new Integer[3];                                       // los elementos que van a ciudades son de la pinta[ganancia,perdida, puntero a superavit(al principio se ordenan por posicion)]  
            infoCiudad[0] = 0;
            infoCiudad[1] = 0;
            infoCiudad[3] = i;
            ciudades[i] = infoCiudad;
            masGanancia[i] = i;                                                            
            masPerdida[i] = i;
            Integer [] superavit = new Integer[2];
            superavit[0] = i;
            superavit [1] = 0;                                                          
            mayorSuperavit.agregar(superavit);
            i++;
        }

    }

    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }
    
}
