package bang.game.card;

public enum CardType {
    BARREL("")
    ;

    private String imageFilename;
    CardType(String imageFilename){
        this.imageFilename = imageFilename;
    }

    public String getImageFilename() {
        return imageFilename;
    }
}
