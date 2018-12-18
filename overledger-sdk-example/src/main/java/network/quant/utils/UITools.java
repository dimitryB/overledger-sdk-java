package network.quant.utils;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class UITools {

    static {
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream("font/OpenSans-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream("font/OpenSans-Bold.ttf")));
            setFont();
        } catch (Exception e) {
            log.error("Unable to load font");
        }
    }

    private static Font REGULAR;
    private static Font BOLD;

    private static void setFont() {
        try {
            REGULAR = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream("font/OpenSans-Regular.ttf"));
            BOLD = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream("font/OpenSans-Bold.ttf"));
        } catch (Exception e) {
            log.error("Unable to load font");
        }
    }

    public static Font getFont(int type, float size) {
        if (Font.BOLD == type) {
            return BOLD.deriveFont(size);
        } else {
            return REGULAR.deriveFont(size);
        }
    }

}
