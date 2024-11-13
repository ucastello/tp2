package main.java.aed;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T extends Comparable<T>> {
    private ArrayList data;
    private int ultimo;
    private Comparator comparador;
    
    private void Heapify(){
        int i = data.size()/2-1;
        while (i >= 0){
            bajar(i);
            i--;
        }
    }

    private Boolean esHoja(int indice){
        return (2*indice > (data.size()-1)|| 2*indice +1 > (data.size()-1));
    }

    private void intercambiar (int Indice1,int Indice2){
        T num = new data.get(Indice1);
        data.set(Indice1, data.get(Indice2));
        data.set(Indice2, num);
    }

    private Boolean hijosConMasPrioridad (int indice){                                          // devuelve True si tiene algun hijo con mas prioridad
        if ( 2*indice+1 > (data.size()-1)){
            return (comparador.compare(data.get(indice), data.get(2*indice)) < 0);
        }
        else{
            return ((comparador.compare(data.get(indice), data.get(2*indice)) < 0) || (comparador.compare(data.get(indice), data.get(2*indice + 1)) < 0));
        }
    }

    private int hijoMayorPrioridad (int indice){
        int res = 2*indice;
        if (2*i+1 > (data.size()-1)){
            return res;
        } else if (comparador.compare(data.get(2*indice), data.get(2*indice+1))<0){
            res = 2*indice +1;
        }
        return res;
    }

    private void bajar(int i){
        while (!esHoja(i) && hijosConMasPrioridad(i)) {
            int hijoMayorPrioridad = hijoMayorPrioridad(i);
            intercambiar(i, hijoMayorPrioridad);
            i = hijoMayorPrioridad;
        }
    }
    
    private void subir (int i){
        while (i >1 && (comparador.compare(data.get(i), data.get(i/2))>0)){             
            intercambiar(i, i/2);
            i = i/2;
        }
        if (comparador.compare(data.get(1), data.get(0))>0){                
            intercambiar(1, 0);
        }
    }

    public Heap (ArrayList info, Comparator com){
        comparador = com;
        data = ArrayList(info);
        ultimo = data.size()-1;
        Heapify(data);
    }

    public T maximo (){
        return data.get(0);
    }

    public T eliminarPosicion (int i){
        T res = data.get(i);
        if (i == ultimo){
            data.remove(i);
        }
        else{
            intercambiar(i, ultimo);
            data.remove(ultimo);
            if (hijosConMasPrioridad(i)){
                bajar(i);
            }
            else if (comparador.compare(data.get(i), data.get(i/2)) > 0){              //si quiero eliminar la raiz a este else no entra nunca, creo que no habria problema igual
                subir(i);
            }
        }
        ultimo = data.size();
        return res;
    }
}

