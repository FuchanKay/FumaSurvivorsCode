package com.fuchankay.game;

public class PlayerStats {
    private static float dmgMult = 1f;
    private static float projSpeedMult = 2f;
    private static float cdr = 1f;
    private static float projSizeMult = 1f;
    public static float getDmgMult() {
        return dmgMult;
    }
    public static void setDmgMult(float dmgMult) {
        PlayerStats.dmgMult = dmgMult;
    }
    public static float getProjSpeedMult() {
        return projSpeedMult;
    }
    public static void setProjSpeedMult(float projSpeedMult) {
        PlayerStats.projSpeedMult = projSpeedMult;
    }
    public static float getCdr() {
        return cdr;
    }
    public static void setCdr(float cdr) {
        PlayerStats.cdr = cdr;
    }
    public static float getProjSizeMult() {
        return projSizeMult;
    }
    public static void setProjSizeMult(float projSizeMult) {
        PlayerStats.projSizeMult = projSizeMult;
    }
}
