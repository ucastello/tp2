package aed;

import java.util.ArrayList;

public class BestEffort {
    private Integer[][] ciudades;
    private Integer [] masGanancia;
    private Integer [] masPerdida;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        ciudades = new Integer[cantCiudades][];
        masGanancia = new Integer[cantCiudades];
        masPerdida = new Integer[cantCiudades];
        int i = 0;
        while (i < cantCiudades) {                                                        // O(|C|)
            Integer [] infoCiudad = new Integer[3];                                       // los elementos que van a ciudades son de la pinta[ganancia,perdida, puntero a superavit(al principio se ordenan por posicion)]  
            infoCiudad[0] = 0;
            infoCiudad[1] = 0;
            infoCiudad[3] = i;
            ciudades[i] = infoCiudad;
            masGanancia[i] = i;
            masPerdida[i] = i;
            i++;
        }
    }

    public void registrarTraslados(Traslado[] traslados){
        // Implementar
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
