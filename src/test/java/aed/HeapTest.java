package test.java.aed;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HeapTest {
    private Heap<Integer> Heap;

    @BeforeEach
    void setUp(){
    Comparator<Integer> comp = Integer::compare;
    ArrayList<Integer> info = new ArrayList<>();
    info.add(3);
    info.add(8);
    info.add(2);
    info.add(6);
    info.add(5);
    info.add(1);
    Heap = new Heap<Integer>(info, comp); 

    }



    @Test 
    void vacio(){
        assertTrue(Heap.data.size() > 0);
    }    
}
