package bang.game.card;

import bang.util.ImageUtil;

import java.awt.image.BufferedImage;

public class Card {
    private boolean discardOnPlay = true;
    private CardType type;
    private BufferedImage image;

    public Card(CardType type){
        this.type = type;
        this.image = ImageUtil.get(type.getImageFilename());
    }

    public CardType getType() {
        return type;
    }

    public boolean isDiscardOnPlay() {
        return discardOnPlay;
    }

    public void setDiscardOnPlay(boolean discardOnPlay) {
        this.discardOnPlay = discardOnPlay;
    }

    public BufferedImage getImage() {
        return image;
    }
}
