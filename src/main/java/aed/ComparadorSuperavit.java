package aed;

import java.util.Comparator;

public class ComparadorSuperavit implements Comparator<Integer[]>{
    
    @Override
    public int compare (Integer[] elem1,Integer[] elem2){
        if ((elem1[1] - elem1[2])> (elem2[1] - elem2[2]) || (((elem1[1] - elem1[2]) ==(elem2[1] - elem2[2]) && elem1[0] < elem2[0]))){
            return 1;
        }
        return -1;
    }
}
