public class Gamestates {

    public static boolean MENU = true;
    public static boolean PLAY = false;
    public static boolean DEAD = false;
    public static boolean WIN = false;

    public static boolean isMENU() {
        return MENU;
    }

    public static void setMENU(boolean MENU) {
        Gamestates.MENU = MENU;
    }

    public static boolean isPLAY() {
        return PLAY;
    }

    public static void setPLAY(boolean PLAY) {
        Gamestates.PLAY = PLAY;
    }

    public static boolean isDEAD() {
        return DEAD;
    }

    public static void setDEAD(boolean DEAD) {
        Gamestates.DEAD = DEAD;
    }

    public static boolean isWIN() {
        return WIN;
    }

    public static void setWIN(boolean WIN) {
        Gamestates.WIN = WIN;
    }
}
