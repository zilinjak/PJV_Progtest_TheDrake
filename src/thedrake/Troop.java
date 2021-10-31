package thedrake;

public class Troop {
    private final Offset2D reversPivot;
    private final Offset2D aversPivot;
    private final String name;
    TroopFace face;

    // Hlavní konstruktor
    public Troop(String name, Offset2D aversPivot, Offset2D reversPivot){
        this.name = name;
        this.aversPivot = aversPivot;
        this.reversPivot = reversPivot;
    }

    // Konstruktor, který nastavuje oba pivoty na stejnou hodnotu
    public Troop(String name, Offset2D pivot){
        this.name = name;
        aversPivot = pivot;
        reversPivot = pivot;
    }

    // Konstruktor, který nastavuje oba pivoty na hodnotu [1, 1]
    public Troop(String name){
        this.name = name;
        aversPivot = new Offset2D(1,1);
        reversPivot = new Offset2D(1,1);
    }

    // Vrací jméno jednotky
    public String name(){
        return name;
    }

    // Vrací pivot na zadané straně jednotky
    public Offset2D pivot(TroopFace face){
        if (face == TroopFace.REVERS)
            return reversPivot;
        else
            return aversPivot;
    }
}
