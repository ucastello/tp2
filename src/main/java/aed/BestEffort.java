package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    private balanceCiudad[] ciudades;
    private Integer [] masGanancia;
    private Integer [] masPerdida;
    private Integer [] trasladosYGananciasHistoricas;                                      //Lista con dos elementos donde el primero refiere a la cantidad de traslados que se hicieron y el segundo a las ganancias totales
    private Heap<Integer[]> mayorSuperavit;
    private Heap<TuplaDeInfo> trasladoAntigueadad;
    private Heap<TuplaDeInfo> trasladoRedituabilidad;

    public class balanceCiudad {
        Integer punteroASuperavit;
        Integer gananciaHistorica;
        Integer perdidaHistorica;
        public balanceCiudad (Integer puntero, Integer ganancia, Integer perdida){
            this.punteroASuperavit = puntero;
            this.gananciaHistorica = ganancia;
            this.perdidaHistorica = perdida;
        }
    }

    public BestEffort(int cantCiudades, Traslado[] traslados){
        ciudades = new balanceCiudad[cantCiudades];
        masGanancia = new Integer[cantCiudades];
        masPerdida = new Integer[cantCiudades];
        int i = 0;

        Comparator<Integer[]> porSuperavit = new Comparator<Integer[]>() {                         //Esto si se puede habria que hacerlo en otro lugar
            public int compare (Integer[] elem1,Integer[] elem2){
                if (elem1[1] > elem2[1] || ((elem1[1] == elem2[1] && elem1[0] < elem2[0]))){
                    return 1;
                }
                return -1;
            }
        };

        mayorSuperavit = new Heap<Integer[]>(new ArrayList<Integer[]>(),porSuperavit);

        while (i < cantCiudades) {                                                                                       // O(|C|)
            balanceCiudad infoCiudad = new balanceCiudad(i,0, 0);                                       // los elementos que van a ciudades son de la pinta[ganancia,perdida, puntero a superavit(al principio se ordenan por posicion)]  
            ciudades[i] = infoCiudad;
            masGanancia[i] = i;                                                            
            masPerdida[i] = i;
            Integer [] superavit = new Integer[2];
            superavit[0] = i;
            superavit [1] = 0;                                                          
            mayorSuperavit.agregar(superavit);                                            //los elementos no se mueven, asi que esto es O(1)
            i++;
        }
        i = 0;
        ArrayList<TuplaDeInfo> trasladosConInfo = new ArrayList<TuplaDeInfo>();            //Creo el array para luego pasarlo a Heap
        while (i < traslados.length){                                                      //O(|T|)
            TuplaDeInfo trasladoConPunteros = new TuplaDeInfo(traslados[i],i,i);
            trasladosConInfo.add(trasladoConPunteros);
            i++;
        }
        i = 0;

        ComparadorAntiguedad antiguedad = new ComparadorAntiguedad();

        trasladoAntigueadad = new Heap<>(trasladosConInfo,antiguedad);

        ComparadorRedituabilidad redituabilidad = new ComparadorRedituabilidad();
        i = 0;
        trasladoRedituabilidad = new Heap<>(trasladosConInfo,redituabilidad);

        trasladosYGananciasHistoricas = new Integer[2];
        trasladosYGananciasHistoricas[0] = 0;
        trasladosYGananciasHistoricas[1] = 0;
    }

    public void registrarTraslados(Traslado[] traslados){
        int i = 0;
        while (i < traslados.length){
            TuplaDeInfo traslado = new TuplaDeInfo(traslados[i], trasladoAntigueadad.longitud(), trasladoRedituabilidad.longitud());
            trasladoAntigueadad.agregar(traslado);
            trasladoRedituabilidad.agregar(traslado);
            i++;
        }
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
