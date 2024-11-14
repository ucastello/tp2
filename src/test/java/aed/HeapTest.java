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
    Heap = new Heap<Integer>(info, comp); 
    oldHeap = new Heap<>(Heap, Integer::compare);

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
    }

    @Test
    void agregarUnElem(){
        Heap.agregar(10);
        assertTrue(oldHeap.longitud() +1 == Heap.longitud());
        assertTrue(Heap.maximo() == 10);

        int i = 1;
        while (i < Heap.longitud()) {
            Integer elem = Heap.obtener(i);
            int j = 0;
            boolean res = false;
            while (j <oldHeap.longitud()) {
                if (oldHeap.obtener(j) == elem){
                    res = true;
                }
            j++;
            }
            assertTrue(res);
            i++;
        }
    }

    @Test
    void eliminarVariosElementoArbitrario(){
        Integer elem1 =Heap.obtener(Heap.longitud()-1);
        Heap.eliminarPosicion(Heap.longitud()-1);
        Integer elem2 =Heap.obtener(Heap.longitud()/2);
        Heap.eliminarPosicion(Heap.longitud()/2);

        int i = 0;
        boolean res = true;
        while (i<Heap.longitud()) {
            if (Heap.obtener(i) == elem1 || Heap.obtener(i)==elem2){
                res = false;
            }
            i++;
        }
        assertTrue(res);
    }
}
