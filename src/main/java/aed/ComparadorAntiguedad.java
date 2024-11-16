package aed;

import java.util.Comparator;

public class ComparadorAntiguedad implements Comparator<TuplaDeInfo>{

    @Override
    public int compare (TuplaDeInfo elem1, TuplaDeInfo elem2){
        if (elem1.infotras.timestamp < elem2.infotras.timestamp){
            return 1;
        }
        return -1;
    }
}
