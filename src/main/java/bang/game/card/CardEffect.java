package bang.game.card;

public enum CardEffect {
    APPALOOSA(""),
    MUSTANG_8("MUSTANG_8.JPG"),
    MUSTANG_9("MUSTANG_9.JPG"),

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

    PANIC_8D("PANICO_8Q.JPG"),
    //PANIC_AQ("PANICO_8Q.JPG"),
    //PANIC_8Q("PANICO_8Q.JPG"),
    //PANIC_8Q("PANICO_8Q.JPG"),
    ;

    private String imageFilename;
    CardEffect(String imageFilename){
        this.imageFilename = imageFilename;
    }

    public String getImageFilename() {
        return imageFilename;
    }
}
