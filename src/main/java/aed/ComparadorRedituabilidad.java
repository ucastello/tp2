package aed;

import java.util.Comparator;

public class ComparadorRedituabilidad implements Comparator<TuplaDeInfo> {
    
    @Override
    public int compare (TuplaDeInfo elem1, TuplaDeInfo elem2){
        if ((elem1.infotras.gananciaNeta > elem2.infotras.gananciaNeta) || (elem1.infotras.gananciaNeta == elem2.infotras.gananciaNeta && elem1.infotras.id < elem2.infotras.id)){
            return 1;
        }
        return -1;
    }
}
