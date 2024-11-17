package aed;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
  
public class Heap<T> {
    private ArrayList<T> data;
    private int ultimo;
    private Comparator<T> comparador;

    public Heap (ArrayList<T> info, Comparator<T> com){                         // O(n) 
        comparador = com;
        data = info;
        ultimo = data.size()-1;
        this.Heapify();                                                             
    }

    //Constructor por copia
    public Heap (Heap<T> h, Comparator<T> comp){                                
        comparador = comp;
        ultimo = h.longitud()-1;
        data = new ArrayList<T>();
        int i = 0;
        while (i <= ultimo){
            data.add(h.obtener(i));
            i++;
        }
    }

    public T maximo (){                                                             // O(1)
        return data.get(0);
    }

    public T eliminarPosicion (int i){                                              // O(log(n))
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
            if (!esHoja(i) && hijosConMasPrioridad(i)){
                bajar(i);                                                           // O(log(n))
            }
            else if (comparador.compare(data.get(i), data.get((i-1)/2)) > 0){              
                subir(i);                                                           // O(log(n))
            }
        }
        ultimo = data.size()-1;
        return res;
    }

    public int longitud(){                                                // O(1)
        return data.size();                                                     
    }

    public T obtener(int indice){                                         // O(1)
        return data.get(indice);
    }

    public void agregar (T elem){                                         // O(log(n))
        data.add(elem);
        ultimo += 1;
        if (ultimo != 0){
            subir(ultimo);                                                // O(log(n))
        }
    }
    public void modificarElem (Integer indice, T prioridadAnterior,T prioridadActual){                         // Modifica un elemento en O(1) si no se cambia la prioridad, si la cambia la complejidad seria de O(log(n))
        data.set(indice, prioridadActual);    
        if (comparador.compare(prioridadAnterior, prioridadActual) > 0){
            bajar(indice);
        }
        else if (comparador.compare(prioridadAnterior, prioridadActual) < 0) {
            subir(indice);   
        } 
    }
    
    public ArrayList<T> heapALista(){                                          // Devuelve una lista con elementos con aliasing [en O(n)]             
        ArrayList <T> res = new ArrayList<>();
        int i = 0;
        while ( i < this.data.size()) {                                        // Cantidad de elementos-iteraciones
            res.add(this.data.get(i));                                         // O(1)
            i++;
        }
        return res;
    }
        
    private void Heapify(){                                                    // O(n)
        int i = data.size()/2-1;
        while (i >= 0){                                                        
            bajar(i);
            i--;
        }
    }

    private Boolean esHoja(int indice){
        return (2*indice+1 > (data.size()-1));
    }

    private void intercambiar (int Indice1,int Indice2){                                // O(1)
        T num1 = data.get(Indice1);
        T num2 = data.get(Indice2);
        // Si es traslado hay que modificar los punteros, al hacerlo no se modifica la prioridad de ninguno de los elementos asi que la complejidad sigue siendo O(1)
        if (esTraslado(num1)){
            TuplaDeInfo numAInfo1 = (TuplaDeInfo) num1; 
            TuplaDeInfo numAInfo2 = (TuplaDeInfo) num2;
            if (ordenadoPorAntiguedad()){
                numAInfo1.modificarTupla(numAInfo1.infotras, Indice2,numAInfo1.redit);                          
                numAInfo2.modificarTupla(numAInfo2.infotras,Indice1, numAInfo2.redit);
                } 
            else {
                numAInfo1.modificarTupla(numAInfo1.infotras, numAInfo1.antig,Indice2);
                numAInfo2.modificarTupla(numAInfo2.infotras,numAInfo2.antig, Indice1);
            }
            num1 = (T) numAInfo1;
            num2 = (T) numAInfo2;
        }
        data.set(Indice1, num2);
        data.set(Indice2, num1);
    }

    private Boolean hijosConMasPrioridad (int indice){                                          // devuelve True si tiene algun hijo con mas prioridad
        if ( 2*indice+2 > (data.size()-1)){
            return (comparador.compare(data.get(indice), data.get(2*indice+1)) < 0);
        }
        return ((comparador.compare(data.get(indice), data.get(2*indice+1)) < 0) || (comparador.compare(data.get(indice), data.get(2*indice + 2)) < 0));
    }

    private int hijoMayorPrioridad (int indice){                                    // O(1)                                       
        int res = 2*indice+1;
        if ((2*indice+2 >= data.size())){
            return res;
        }
        else if (comparador.compare(data.get(2*indice+1), data.get(2*indice+2))<0){
            res = 2*indice +2;
        }
        return res;
    }

    private void bajar(int i){                                                              // O(log(n))
        while (!esHoja(i) && hijosConMasPrioridad(i)) {                                     // Como maximo hace log(n) iteraciones
            int hijoMayorPrioridad = hijoMayorPrioridad(i);
            intercambiar(i, hijoMayorPrioridad);
            i = hijoMayorPrioridad;
        }
    }

    private void subir (int i){                                                             // O(log(n))
        while (i > 0 && (comparador.compare(data.get(i), data.get((i-1)/2))>0)){            // Como maximo hace log(n) iteraciones        
            intercambiar(i, (i-1)/2);
            i = (i-1)/2;
        }
    }

    private boolean esTraslado (T elem){                                                      
        TuplaDeInfo prueba = new TuplaDeInfo(null, null, null);
        return (elem.getClass() == prueba.getClass());
    }
    private boolean ordenadoPorAntiguedad (){
        ComparadorAntiguedad ejemplo = new ComparadorAntiguedad();
    return (this.comparador.getClass() == ejemplo.getClass());
    }
}
