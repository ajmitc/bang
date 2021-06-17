package bang.game.card;

public enum CardEffect {
    APPALOOSA(""),
    BANG_2C("BANG_2F.JPG"),
    BANG_2D("BANG_2Q.JPG"),
    BANG_3C("BANG_3F.JPG"),
    BANG_3D("BANG_3Q.JPG"),

    MISSED_2S("MANCATO_2P.JPG"),
    MISSED_3S("MANCATO_3P.JPG"),
    MISSED_4S("MANCATO_4P.JPG"),
    MISSED_5S("MANCATO_5P.JPG"),
    MISSED_6S("MANCATO_6P.JPG"),
    MISSED_7S("MANCATO_7P.JPG"),
    MISSED_8S("MANCATO_8P.JPG"),
    MISSED_TC("MANCATO_TF.JPG"),
    MISSED_AC("MANCATO_AF.JPG"),
    MISSED_JC("MANCATO_JF.JPG"),
    MISSED_QC("MANCATO_QF.JPG"),
    MISSED_KC("MANCATO_KF.JPG"),

    BEER_6("BIRRA_6.JPG"),
    BEER_7("BIRRA_7.JPG"),
    BEER_8("BIRRA_8.JPG"),
    BEER_9("BIRRA_9.JPG"),
    BEER_T("BIRRA_T.JPG"),
    BEER_J("BIRRA_J.JPG"),

    BARREL_K("BARILE_K.JPG"),
    BARREL_Q("BARILE_Q.JPG"),

    REMINGTON("REMINGTON.JPG"),
    REV_CARABINA("CARABINA.JPG"),
    SCHOFIELD_JC("SCHOFIELD_J.JPG"),
    SCHOFIELD_KC("SCHOFIELD_K.JPG"),
    SCHOFIELD_QC("SCHOFIELD_Q.JPG"),
    WINCHESTER("WINCHESTER.JPG"),

    // Can play any number of BANG! cards
    VOLCANIC_TC("VOLCANIC_FIORI.JPG"),
    VOLCANIC_TS("VOLCANIC_PICCHE.JPG"),

    GATLING("GATLING.JPG"),  // BANG to all other players

    // Reveal as many cards as players, each player takes 1
    GENERAL_STORE_9C("EMPORIO_9F.JPG"),
    GENERAL_STORE_QS("EMPORIO_QP.JPG"),

    // Others view you at +1 distance
    MUSTANG_8H("MUSTANG_8.JPG"),
    MUSTANG_9H("MUSTANG_9.JPG"),

    // View players at -1 distance
    SCOPE("MIRINO.JPG"),

    // All other players discard a BANG or lose 1 life
    INDIANS_AD("INDIANI_AQ.JPG"),
    INDIANS_KD("INDIANI_KQ.JPG"),

    // Panic!  Draw a card from a player 1 seat away
    PANIC_8D("PANICO_8Q.JPG"),
    PANIC_AH("PANICO_AC.JPG"),
    PANIC_JH("PANICO_JC.JPG"),
    PANIC_QH("PANICO_QC.JPG"),

    DUEL_8C("DUELLO_8F.JPG"),
    DUEL_JS("DUELLO_JP.JPG"),
    DUEL_QD("DUELLO_QQ.JPG"),

    DYNAMITE("DINAMITE.JPG"),

    STAGECOACH("DILIGENZA.JPG"),
    WELLS_FARGO("WELLSFARGO.JPG"),

    // Choose a player to discard 1 card
    CAT_BALOU_9D("CATBALOU_9Q.JPG"),
    CAT_BALOU_JD("CATBALOU_JQ.JPG"),
    CAT_BALOU_KD("CATBALOU_KQ.JPG"),
    CAT_BALOU_TD("CATBALOU_TQ.JPG"),

    JAIL_4H("PRIGIONE_4.JPG"),
    JAIL_JH("PRIGIONE_J.JPG"),
    JAIL_TH("PRIGIONE_T.JPG"),

    SALOON("SALOON.JPG")
    ;

    private String imageFilename;
    CardEffect(String imageFilename){
        this.imageFilename = imageFilename;
    }

    public String getImageFilename() {
        return imageFilename;
    }
}
