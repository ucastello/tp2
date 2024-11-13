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
    info.data(3);
    info.data(8);
    info.data(2);
    info.data(6);
    info.data(5);
    info.data(1);
    Heap = new main.java.aed.Heap<>(info, comp); 

    }



    @Test 
    void vacio(){
        assertTrue(Heap.data.size() > 0);
    }    
}
