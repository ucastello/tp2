package aed;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
  
public class Heap<T> {
    private ArrayList<T> data;
    private int ultimo;
    private Comparator<T> comparador;

    public Heap (ArrayList<T> info, Comparator<T> com){
        comparador = com;
        data = new ArrayList<>(info);
        ultimo = data.size()-1;
        this.Heapify();
    }

    public Heap (Heap<T> h, Comparator<T> comp){                                //Constructor por copia
        comparador = comp;
        ultimo = h.longitud()-1;
        data = new ArrayList<T>();
        int i = 0;
        while (i <= ultimo){
            data.add(h.obtener(i));
            i++;
        }
    }

    public T maximo (){
        return data.get(0);
    }

    public T eliminarPosicion (int i){
        T res = data.get(i);
        if (i == ultimo){
            data.remove(i);
        }
        else if (esHoja(i)){
            intercambiar(i, ultimo);
            data.remove(ultimo);
        } 
        else if (ultimo == i+1){
            intercambiar(i, ultimo);
            data.remove(ultimo);
        }
        else{
            intercambiar(i, ultimo);
            data.remove(ultimo);
            if (hijosConMasPrioridad(i)){
                bajar(i);
            }
            else if (comparador.compare(data.get(i), data.get((i-1)/2)) > 0){              //si quiero eliminar la raiz a este else no entra nunca, creo que no habria problema igual
                subir(i);
            }
        }
        ultimo = data.size()-1;
        return res;
    }

    public int longitud(){
        return data.size();
    }

    public T obtener(int indice){
        return data.get(indice);
    }

    public void agregar (T elem){
        if (data.size()== 0){
            data.add(0,elem);
            ultimo += 1;
        } else {
            data.add(ultimo+1,elem);
            ultimo +=1;
            subir(ultimo);
        }
        
    }
    public void modificarElem (Integer indice, T elem){
        data.add(indice, elem);
    }
        
    private void Heapify(){
        int i = data.size()/2-1;
        while (i >= 0){
            bajar(i);
            i--;
        }
    }

    private Boolean esHoja(int indice){
        return (2*indice+1 > (data.size()-1));
    }

    private void intercambiar (int Indice1,int Indice2){
        T num = data.get(Indice1);
        data.set(Indice1, data.get(Indice2));
        data.set(Indice2, num);
    }

    private Boolean hijosConMasPrioridad (int indice){                                          // devuelve True si tiene algun hijo con mas prioridad
        if ( 2*indice+2 > (data.size()-1)){
            return (comparador.compare(data.get(indice), data.get(2*indice+1)) < 0);
        }
        return ((comparador.compare(data.get(indice), data.get(2*indice+1)) < 0) || (comparador.compare(data.get(indice), data.get(2*indice + 2)) < 0));
    }

    private int hijoMayorPrioridad (int indice){
        int res = 2*indice+1;
        if ((2*indice+2 >= data.size())){
            return res;
        }
        else if (comparador.compare(data.get(2*indice+1), data.get(2*indice+2))<0){
            res = 2*indice +2;
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
        while (i >1 && (comparador.compare(data.get(i), data.get((i-1)/2))>0)){             
            intercambiar(i, (i-1)/2);
            i = (i-1)/2;
        }
        if (comparador.compare(data.get(1), data.get(0))>0){                
            intercambiar(1, 0);
        }
    }

}
