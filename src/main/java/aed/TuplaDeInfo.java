package aed;

public class TuplaDeInfo {
    Traslado infotras;
    Integer redit;
    Integer antig;
    public TuplaDeInfo (Traslado t, Integer antiguedad,Integer redituabilidad){
        this.infotras = t;
        this.antig = antiguedad;
        this.redit = redituabilidad;
    }
    public void modificarTupla (Traslado tnuevo, Integer antiguedadnueva,Integer redituabilidadnueva){
        this.infotras = tnuevo;
        this.antig = antiguedadnueva;
        this.redit = redituabilidadnueva;
    }
}
