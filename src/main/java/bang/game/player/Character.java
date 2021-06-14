package bang.game.player;

public enum Character {
    BART_CASSIDY("Bart Cassidy", 4, "BART_CASSIDY.JPG"),
    BLACK_JACK("Black Jack", 4, "BLACK_JACK.JPG"),
    CALAMITY_JANET("Calamity Janet", 4, "CALAMITY_JANET.JPG"),
    EL_GRINGO("El Gringo", 3, "EL_GRINGO.JPG")
    ;

    private String name;
    private int maxHitpoints;
    private String imageFilename;
    Character(String name, int maxHitpoints, String imageFilename){
        this.name = name;
        this.maxHitpoints = maxHitpoints;
        this.imageFilename = imageFilename;
    }

    public String getName() {
        return name;
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public String toString(){
        return name;
    }
}
