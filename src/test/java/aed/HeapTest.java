package aed;

import static org.junit.jupiter.api.Assertions.*;
  
import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HeapTest {
    private Heap<Integer> Heap;
    private Heap<Integer> oldHeap;
    private Heap<TuplaDeInfo> heapAntiguedad;
    private Heap<TuplaDeInfo> heapRedituabilidad;

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void setUp(){
        Comparator<Integer> comp = Integer::compare;
        ArrayList<Integer> info = new ArrayList<>();
        info.add(3);
        info.add(8);
        info.add(2);
        info.add(5);
        info.add(6);
        info.add(1);
        info.add(9);
        info.add(7);
        Heap = new Heap<Integer>(info, comp); 
        oldHeap = new Heap<>(Heap, Integer::compare);

        Traslado[] traslados = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 1, 2, 30, 2),
            new Traslado(5, 2, 1, 90, 1)
        };
        ArrayList<TuplaDeInfo> listaTraslados = new ArrayList<>();
        for (int i = 0; i < traslados.length; i++) {
            listaTraslados.add(new TuplaDeInfo(traslados[i], i, i));
        }
        
        // Recreamos la inicializacion de las variables en el BestEffort (unico lugar donde importarian estos tipos de datos)
        Comparator<TuplaDeInfo> antigComparador = new ComparadorAntiguedad();
        Comparator<TuplaDeInfo> reditComparador = new ComparadorRedituabilidad();
        heapAntiguedad = new Heap<TuplaDeInfo>(listaTraslados, antigComparador);
        heapRedituabilidad = new Heap<TuplaDeInfo>(heapAntiguedad.heapALista(), reditComparador);
        int i = 0;

        while (i < heapAntiguedad.longitud()) {                                                                                                
            heapAntiguedad.obtener(i).modificarTupla(heapAntiguedad.obtener(i).infotras, i, heapAntiguedad.obtener(i).redit);
            i++;
        }
        i = 0;
        while (i < heapRedituabilidad.longitud()) {
            heapRedituabilidad.obtener(i).modificarTupla(heapRedituabilidad.obtener(i).infotras, heapRedituabilidad.obtener(i).antig,i);
            i++;
        }
    }


    private boolean pertenece (Heap<Integer> h, Integer elem){
        boolean res = false;
        int i = 0;
        while (i < h.longitud()) {
            if (h.obtener(i) == elem){
                res = true;
            }
            i++;
        }
        return res;
    }
    private boolean PadreMayorQueHijo(Heap<Integer> h){
        boolean res = true;
        int i = 0;
        while (i<h.longitud()/2){
            if (2*i+2 == h.longitud()){
                if (h.obtener(i) < h.obtener(2*i+1)){
                res = false;
                }
            } else {
                if (h.obtener(i) < h.obtener(2*i+1) || h.obtener(i) < h.obtener(2*i+2)){
                    res =  false;
                }
            }
            i ++;
        }
        return res;
    }
    private boolean esColaDePrioridad (Heap<Integer> h){
        boolean res = true;
        Integer elemAnterior =h.eliminarPosicion(0);
        while (h.longitud()>0) {
            if (h.obtener(0)> elemAnterior){
                res = false;
            }
            elemAnterior = h.eliminarPosicion(0);
        }
        return res;
    }
    private boolean punterosCorrectos(Heap<TuplaDeInfo> heap, Integer criterio){
        boolean res = true;
        int i = 0;
        while (i < heap.longitud()) {
            TuplaDeInfo traslado = heap.obtener(i);
            if (criterio == 0){
                if (!(traslado.antig.equals(i))){
                    res = false;
                }
            }
            else {
                if (i != traslado.redit){
                    res = false;
                }
            }
            i++;
        }
        return res;
    }
    @Test 
    void vacio(){
        Comparator<Integer> comparador = Integer::compare;
        ArrayList<Integer> lista = new ArrayList<>();
        Heap<Integer> h = new Heap<Integer>(lista, comparador);
        assertTrue(h.longitud() == 0);
    } 
    
    @Test
    void eliminarMaximoUnaVez(){
        Heap.eliminarPosicion(0);
        assertTrue(oldHeap.longitud() == Heap.longitud()+1);
        
        int i = 0;
        while (i< Heap.longitud()) {
            assertTrue(oldHeap.obtener(0)>Heap.obtener(i));
            i++;
        }
        assertTrue(esColaDePrioridad(Heap));
    }

    @Test
    void agregarUnElem(){
        Heap.agregar(10);
        assertTrue(oldHeap.longitud() +1 == Heap.longitud());
        assertTrue(Heap.maximo() == 10);

        int i = 1;
        while (i < Heap.longitud()) {
            Integer elem = Heap.obtener(i);
            assertTrue(pertenece(oldHeap, elem));
            i++;
        }
        assertTrue(esColaDePrioridad(Heap));
    }

    @Test
    void eliminarVariosElementoArbitrario(){
        Integer elem1 =Heap.obtener(Heap.longitud()-1);
        Heap.eliminarPosicion(Heap.longitud()-1);
        Integer elem2 =Heap.obtener(Heap.longitud()/2);
        Heap.eliminarPosicion(Heap.longitud()/2);

        assertFalse(pertenece(Heap, elem1));
        assertFalse(pertenece(Heap, elem2));
        assertTrue(esColaDePrioridad(Heap));
    }
    @Test
    void punterosActualizadosEliminandoSoloDeUnHeap(){
        heapAntiguedad.eliminarPosicion(3);
        heapAntiguedad.eliminarPosicion(1);
        assertTrue(punterosCorrectos(heapAntiguedad,0));
        assertTrue(punterosCorrectos(heapRedituabilidad, 1));
        }

    @Test 
    void punterosActualizadosEliminandoMixtos(){
        heapAntiguedad.eliminarPosicion(3);
        heapAntiguedad.eliminarPosicion(1);
        heapRedituabilidad.eliminarPosicion(0);
        heapRedituabilidad.eliminarPosicion(2);
        assertTrue(punterosCorrectos(heapAntiguedad,0));
        assertTrue(punterosCorrectos(heapRedituabilidad, 1));
    }
    
    @Test 
    void vaciarElHeap(){
        while (heapAntiguedad.longitud()!=0) {
            assertTrue(punterosCorrectos(heapAntiguedad,0));
            assertTrue(punterosCorrectos(heapRedituabilidad, 1));
            heapAntiguedad.eliminarPosicion(0);
        }
        assertTrue(punterosCorrectos(heapAntiguedad,0));
        assertTrue(punterosCorrectos(heapRedituabilidad, 1));
    }



    Integer NCLAVES = 100000;
    private Integer clave(Integer i) {
        return NCLAVES * ((i * i - 100 * i) % NCLAVES) + i;
    }

    @Test
    void stress() {

        Heap<Integer> conjunto = new Heap<Integer>(new ArrayList<Integer>(),Integer::compare);

        // Insertar
        for (Integer i = 0; i < NCLAVES; i++) {
            Integer k = clave(i);
            assertEquals(false, pertenece(conjunto,k));
            conjunto.agregar(k);
            assertEquals(true, pertenece(conjunto,k));
        }

        // Salen en orden
        assertTrue(PadreMayorQueHijo(conjunto));
        assertTrue(esColaDePrioridad(conjunto));
        
    }
}
