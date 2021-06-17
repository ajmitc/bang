package bang.game.player;

public enum Character {
    // Each time he is hit, he draws a card
    BART_CASSIDY("Bart Cassidy", 4, "BART_CASSIDY.JPG"),

    // Reveal second card he draws.  On Heart or Diamonds, draw another card.
    BLACK_JACK("Black Jack", 4, "BLACK_JACK.JPG"),

    // Can play BANG as Missed and vice versa
    CALAMITY_JANET("Calamity Janet", 4, "CALAMITY_JANET.JPG"),

    // Each time he is hit by a player, he takes a card from that player's hand
    EL_GRINGO("El Gringo", 3, "EL_GRINGO.JPG"),

    // Players see him +1 away
    PAUL_REGRET("Paul Regret", 3, "PAUL_REGRET.JPG"),

    // May draw first card from discard pile
    PEDRO_RAMIREZ("Pedro Ramirez", 4, "PEDRO_RAMIREZ.JPG"),

    // He may draw his first card from hand of another player
    JESSE_JONES("Jesse Jones", 4, "JESSE_JONES.JPG"),

    // Whenever he is the target of a bang, he may draw!.  On a heart, he is missed.
    JOURDONNAIS("Jourdonnais", 4, "JOURDONNAIS.JPG"),

    // Each time he draws, draw two and pick one
    LUCKY_DUKE("Lucky Duke", 4, "LUCKY_DUKE.JPG"),

    // Look at top 3 cards of deck and chooses 2 to draw
    KIT_CARLSON("Kit Carlson", 4, "KIT_CARLSON.JPG"),

    // She sees all players at -1 distance
    ROSE_DOOLAN("Rose Doolan", 4, "ROSE_DOOLAN.JPG"),

    // He may discard 2 cards to regain 1 life point
    SID_KETCHUM("Sid Ketchum", 4, "SID_KETCHUM.JPG"),

    // Player needs 2 Missed! cards to cancel his BANG! card
    SLAB_THE_KILLER("Slab the Killer", 4, "SLAB_THE_KILLER.JPG"),

    // As soon as she has no cards in hand, she draws a card
    SUZY_LAFAYETTE("Suzy LaFayette", 4, "SUZY_LAFAYETTE.JPG"),

    // Whenever a player is eliminated, he takes in hand all cards of that player
    VULTURE_SAM("Vulture Sam", 4, "VULTURE_SAM.JPG"),

    // He can play any number of BANG cards
    WILLY_THE_KID("Willy the Kid", 4, "WILLY_THE_KID.JPG")
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
