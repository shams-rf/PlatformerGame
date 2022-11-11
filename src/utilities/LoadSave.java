package utilities;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// Class that saves & loads different images and elements of the game such as player, level, etc.
public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";

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

    // Method to allow level editing
    public static int[][] getLevelData() {

        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);

        for(int j = 0; j < img.getHeight(); j++) {

            for(int i = 0; i < img.getWidth(); i++) {

                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48) {

                    value = 0;
                }
                levelData[j][i] = value;
            }
        }
        return levelData;
    }
}
