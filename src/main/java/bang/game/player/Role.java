package bang.game.player;

public enum Role {
    SHERIFF("SCERIFFO.JPG"),
    DEPUTY("VICE.JPG"),
    OUTLAW("FUORILEGGE.JPG"),
    RENEGADE("RINNEGATO.JPG");

    private String imageFilename;
    Role(String imageFilename){
        this.imageFilename = imageFilename;
    }

    public String getImageFilename() {
        return imageFilename;
    }
}
