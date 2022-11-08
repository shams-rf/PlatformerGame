package utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// Class that saves & loads different images and elements of the game such as player, level, etc.
public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";

    public static BufferedImage getSpriteAtlas(String fileName) {

        // Method to import image from resources folder into game
        // Use try catch block to read image input stream
        // Use another try catch block to close the input stream to avoid errors
        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

        try {
            img = ImageIO.read(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
