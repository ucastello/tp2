package aed;

import java.util.ArrayList;

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

    public BestEffort(int cantCiudades, Traslado[] traslados){                             // O (|T| + |C|) 
        ciudades = new balanceCiudad[cantCiudades];
        masGanancia = new ArrayList<>(cantCiudades);
        masPerdida = new ArrayList<>(cantCiudades);
        int i = 0;

        ComparadorSuperavit porSuperavit = new ComparadorSuperavit();
        mayorSuperavit = new Heap<Integer[]>(new ArrayList<Integer[]>(),porSuperavit);

        while (i < cantCiudades) {                                                         // Tiene |C| iteraciones y todas las funciones de adentro son de complejidad O(1), por lo que este while cuenta con una complejidad de O(|C|)
            balanceCiudad infoCiudad = new balanceCiudad(i,0, 0);                              
            ciudades[i] = infoCiudad;
            masGanancia.add(i);                                                            // O(1) por ser ArrayList                                                    
            masPerdida.add(i);                                                             // Idem     
            Integer [] superavit = new Integer[3];                                         
            superavit[0] = i;
            superavit [1] = 0;        
            superavit [2] = 0;      
            // El primer elemento refiere al id de la ciudad el segundo a ganancia y el tercero a perdida                                            
            mayorSuperavit.agregar(superavit);                                              // debido a que los elementos van entrando de manera ordenada al heap (todos tienen el mismo superavit y voy de menor id a mayor) cada agregar es O(1) ya que el elemento que entra nunca tiene mayor prioridad que su padre. 
            i++;
        }

        // Iniciamos los dos heaps de traslados
        i = 0;
        //Creo el array de todos los traslados para luego pasarlo a Heap
        ArrayList<TuplaDeInfo> trasladosConInfo = new ArrayList<TuplaDeInfo>();     
        while (i < traslados.length){                                                       // |T| iteraciones
            TuplaDeInfo trasladoConPunteros = new TuplaDeInfo(traslados[i],i,i);
            trasladosConInfo.add(trasladoConPunteros);                                      // O(1)
            i++;
        }

        ComparadorAntiguedad antiguedad = new ComparadorAntiguedad();                       // O(1)
        trasladoAntiguedad = new Heap<>(trasladosConInfo,antiguedad);                       // Pasar de un arreglo no ordenado a un heap tiene complejidad, en este caso, O(|T|)
        i = 0;
        while (i < trasladoAntiguedad.longitud()) {                                                                                                // |T| iteraciones
            trasladoAntiguedad.obtener(i).modificarTupla(trasladoAntiguedad.obtener(i).infotras, i, trasladoAntiguedad.obtener(i).redit);          // Pueden existir punteros que no se cambien en el heapify, aqui se actualizan todos en O(1) ya que no se ve modificada su prioridad 
            i++;
        }

        ComparadorRedituabilidad redituabilidad = new ComparadorRedituabilidad();
        // Uso la lista con los punteros ya actualizados
        trasladoRedituabilidad = new Heap<>(trasladoAntiguedad.heapALista(),redituabilidad);        // O (|T|)            
        i = 0;
        while (i < trasladoRedituabilidad.longitud()) {                                             // |T| iteraciones
            trasladoRedituabilidad.obtener(i).modificarTupla(trasladoRedituabilidad.obtener(i).infotras, trasladoRedituabilidad.obtener(i).antig,i);        // O(1) debido a que no cambio la prioridad
            i++;
        }

        // Iniciamos la variable trasladosYGananciasHistoricas
        trasladosYGananciasHistoricas = new Integer[2];
        trasladosYGananciasHistoricas[0] = 0;
        trasladosYGananciasHistoricas[1] = 0;
    }

    public void registrarTraslados(Traslado[] traslados){                                                                               // O(|traslados|(log(|T|)))
        int i = 0;
        while (i < traslados.length){                                                                                                   // Siempre realiza |traslados| iteraciones    
            TuplaDeInfo traslado = new TuplaDeInfo(traslados[i], trasladoAntiguedad.longitud(), trasladoRedituabilidad.longitud());
            trasladoAntiguedad.agregar(traslado);                                                                                       // Por ser un Heap, la complejidad de agregar un elemento es O(log(n))
            trasladoRedituabilidad.agregar(traslado);                                                                                   // Idem
            i++;
        }
    }

    public int[] despacharMasRedituables(int n){                                       // O(n(log(|T|) + log(|C|)))
        return despachar(n, 0, trasladoRedituabilidad, trasladoAntiguedad);
    }

    public int[] despacharMasAntiguos(int n){                                          // O(n(log(|T|) + log(|C|)))
        return despachar(n, 1, trasladoAntiguedad, trasladoRedituabilidad);
    }

    public int ciudadConMayorSuperavit(){                                              // Por estar implementado en un Heap esto lo realiza en O(1)
        return mayorSuperavit.maximo()[0];
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){                              
        return masGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return masPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        return trasladosYGananciasHistoricas[1]/trasladosYGananciasHistoricas[0];
    }

    private int [] despachar (Integer cantidad ,Integer criterio, Heap<TuplaDeInfo> heapPrincipal, Heap<TuplaDeInfo> heapSecundario){
        int [] res = new int [cantidad];
        int i = 0;
        while (i < cantidad && (heapPrincipal.longitud() != 0)) {                                                                            // A lo sumo realiza n iteraciones
            TuplaDeInfo despacho = heapPrincipal.eliminarPosicion(0);                                                                      // Por estar implemantado en un Heap esta operacion tinene complejidad O(log(|T|))
            if (criterio == 0){
                heapSecundario.eliminarPosicion(despacho.antig);                                                                             // O(log(|T|))
            }
            else {
                heapSecundario.eliminarPosicion(despacho.redit);                                                                             // O(log(|T|))
            }
            res[i] = despacho.infotras.id;
            actualizarBalances(despacho.infotras);                                                                                           // O(log(|C|))
            i++;
        }
        return res;
    }

    private void actualizarBalances(Traslado t){                                                                //O(log(|C|)) debido a que aparte de actualizarPosicionEnSuperavit los procesos son O(1)
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

        actualizarPosicionEnSuperavit(t.origen);    
        actualizarPosicionEnSuperavit(t.destino);
    }
    
    private void actualizarPosicionEnSuperavit(Integer ciudad){                                                                     // O(log(|C|))                           
        Integer punteroCiudad = ciudades[ciudad].punteroASuperavit;
        Integer[] ciudadActualizada = new Integer[3];
        ciudadActualizada [0] = punteroCiudad;
        ciudadActualizada [1] = ciudades[ciudad].gananciaHistorica;   
        ciudadActualizada [2] = ciudades[ciudad].perdidaHistorica;
        mayorSuperavit.modificarElem(punteroCiudad,mayorSuperavit.obtener(punteroCiudad),ciudadActualizada);                        // Modificar la prioridad de un elemento en una posicion arbitraria en un Heap es O(log(n))
    }

}
