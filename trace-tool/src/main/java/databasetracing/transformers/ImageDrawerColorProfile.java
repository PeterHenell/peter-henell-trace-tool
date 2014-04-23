package databasetracing.transformers;

import java.awt.Color;

public class ImageDrawerColorProfile {

    private Color BACKGROUND_COLOR;
    private Color LINE_COLOR;
    private Color SELECT_COLOR;
    private Color UPDATE_COLOR;
    private Color INSERT_COLOR;
    private Color DELETE_COLOR;
    private Color EXEC_COLOR;


    private ImageDrawerColorProfile() {
    }


    public Color getBACKGROUND_COLOR() {
        return BACKGROUND_COLOR;
    }


    public Color getLINE_COLOR() {
        return LINE_COLOR;
    }


    public Color getSELECT_COLOR() {
        return SELECT_COLOR;
    }


    public Color getUPDATE_COLOR() {
        return UPDATE_COLOR;
    }


    public Color getINSERT_COLOR() {
        return INSERT_COLOR;
    }


    public Color getDELETE_COLOR() {
        return DELETE_COLOR;
    }


    public Color getEXEC_COLOR() {
        return EXEC_COLOR;
    }


    public static ImageDrawerColorProfile getPrintableColorProfile() {
        ImageDrawerColorProfile profile = new ImageDrawerColorProfile();
        profile.BACKGROUND_COLOR = Color.WHITE;
        profile.LINE_COLOR = Color.BLACK;
        profile.SELECT_COLOR = new Color(0x228B22);
        profile.UPDATE_COLOR = new Color(0x71585);
        profile.INSERT_COLOR = new Color(0xFF6347);
        profile.DELETE_COLOR = new Color(0x800080);
        profile.EXEC_COLOR = new Color(0x4169E1);

        return profile;
    }


    public static ImageDrawerColorProfile getBlackColorProfile() {
        ImageDrawerColorProfile profile = new ImageDrawerColorProfile();
        profile.BACKGROUND_COLOR = Color.BLACK;
        profile.LINE_COLOR = new Color(0xC0C0C0);
        profile.SELECT_COLOR = Color.GREEN;
        profile.UPDATE_COLOR = Color.YELLOW;
        profile.INSERT_COLOR = new Color(0xCD7F32);
        profile.DELETE_COLOR = new Color(0x800080);
        profile.EXEC_COLOR = new Color(0x1589FF);

        return profile;
    }


    public static ImageDrawerColorProfile getStandardColorProfile() {
        ImageDrawerColorProfile profile = new ImageDrawerColorProfile();
        profile.BACKGROUND_COLOR = new Color(0x2C3539);
        profile.LINE_COLOR = Color.WHITE;
        profile.SELECT_COLOR = Color.GREEN;
        profile.UPDATE_COLOR = Color.YELLOW;
        profile.INSERT_COLOR = new Color(0xCD7F32);
        profile.DELETE_COLOR = new Color(0x800080);
        profile.EXEC_COLOR = new Color(0x1589FF);

        return profile;
    }
}
