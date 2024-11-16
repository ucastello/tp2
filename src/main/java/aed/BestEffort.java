package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    private balanceCiudad[] ciudades;
    private ArrayList<Integer> masGanancia;
    private ArrayList<Integer> masPerdida;
    private Integer [] trasladosYGananciasHistoricas;                                      //Lista con dos elementos donde el primero refiere a la cantidad de traslados que se hicieron y el segundo a las ganancias totales
    private Heap<Integer[]> mayorSuperavit;
    private Heap<TuplaDeInfo> trasladoAntiguedad;
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
        masGanancia = new ArrayList<>(cantCiudades);
        masPerdida = new ArrayList<>(cantCiudades);
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
            masGanancia.add(i);                                                   
            masPerdida.add(i);
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

        ComparadorAntiguedad antiguedad = new ComparadorAntiguedad();

        trasladoAntiguedad = new Heap<>(trasladosConInfo,antiguedad);
        i = 0;
        while (i < trasladoAntiguedad.longitud()) {                                                                                                //pueden existir punteros que no se cambien en el heapify,asi que aca lo cambiamos
            trasladoAntiguedad.obtener(i).modificarTupla(trasladoAntiguedad.obtener(i).infotras, i, trasladoAntiguedad.obtener(i).redit);
            i++;
        }

        ComparadorRedituabilidad redituabilidad = new ComparadorRedituabilidad();
        trasladoRedituabilidad = new Heap<>(trasladoAntiguedad.heapALista(),redituabilidad);                   //obtengo la lista con los punteros acutalizado
        i = 0;
        while (i < trasladoRedituabilidad.longitud()) {
            trasladoRedituabilidad.obtener(i).modificarTupla(trasladoRedituabilidad.obtener(i).infotras, trasladoRedituabilidad.obtener(i).antig,i);
            i++;
        }

        trasladosYGananciasHistoricas = new Integer[2];
        trasladosYGananciasHistoricas[0] = 0;
        trasladosYGananciasHistoricas[1] = 0;
    }

    public void registrarTraslados(Traslado[] traslados){
        int i = 0;
        while (i < traslados.length){
            TuplaDeInfo traslado = new TuplaDeInfo(traslados[i], trasladoAntiguedad.longitud(), trasladoRedituabilidad.longitud());
            trasladoAntiguedad.agregar(traslado);
            trasladoRedituabilidad.agregar(traslado);
            i++;
        }
    }

    public int[] despacharMasRedituables(int n){
        int [] res = new int [n];
        int i = 0;
        while (i < n || (trasladoRedituabilidad.longitud() == 0)) {
            TuplaDeInfo despacho = trasladoRedituabilidad.eliminarPosicion(0);
            trasladoAntiguedad.eliminarPosicion(despacho.antig);
            res[i] = despacho.infotras.id;
            actualizarBalances(despacho.infotras);
            i++;
        }
        return res;
    }

    public int[] despacharMasAntiguos(int n){
        int [] res = new int [n];
        int i = 0;
        while (i < n && (trasladoAntiguedad.longitud() != 0)) {
            TuplaDeInfo despacho = trasladoAntiguedad.eliminarPosicion(0);
            trasladoRedituabilidad.eliminarPosicion(despacho.redit);
            res[i] = despacho.infotras.id;
            actualizarBalances(despacho.infotras);
            i++;
        }
        return res;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return masGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return masPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }

    private void actualizarBalances(Traslado t){
        Integer maximaGanancia = ciudades[masGanancia.get(0)].gananciaHistorica;
        Integer maximaPerdida = ciudades[masPerdida.get(0)].perdidaHistorica;
        ciudades[t.origen].gananciaHistorica += t.gananciaNeta;
        if ((ciudades[t.origen].gananciaHistorica).equals(maximaGanancia)){
            masGanancia.add(t.origen);
        }
        else if ((ciudades[t.origen].gananciaHistorica) > (maximaGanancia)){
            masGanancia = new ArrayList<Integer>();
            masGanancia.add(t.origen);
        }
        
        ciudades[t.destino].perdidaHistorica += t.gananciaNeta;
        if ((ciudades[t.destino].perdidaHistorica).equals(maximaPerdida)){
            masPerdida.add(t.destino);
        }
        else if ((ciudades[t.destino].perdidaHistorica) > (maximaPerdida)){
            masPerdida = new ArrayList<Integer>();
            masPerdida.add(t.destino);
        }
        trasladosYGananciasHistoricas[0] += 1;
        trasladosYGananciasHistoricas[1] += t.gananciaNeta;
    }
}
