package view.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;

public class FontLoad {
    private static Font montserratRegular;
    private static Font montserratBold;
    private static Font montserratMedium;

    static {
        try {
            montserratRegular = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat-Regular.ttf"));
            montserratBold = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat-Bold.ttf"));
            montserratMedium = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat-Medium.ttf"));

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserratRegular);
            ge.registerFont(montserratBold);
            ge.registerFont(montserratMedium);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            montserratRegular = new Font("SansSerif", Font.PLAIN, 16);
            montserratBold = new Font("SansSerif", Font.PLAIN, 16);
            montserratMedium = new Font("SansSerif", Font.PLAIN, 16);
        }
    }

    public static Font getMontserratRegular(float size) {
        return montserratRegular.deriveFont(size);
    }

    public static Font getMontserratBold(float size) {
        return montserratBold.deriveFont(size);
    }

    public static Font getMontserratMedium(float size) {
        return montserratMedium.deriveFont(size);
    }
}