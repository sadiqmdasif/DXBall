package com.apache.dxballfinal;

/**
 * Created by apache on 12-29-2015.
 */
public class Score {
    private int life = 0;
    private int point = 0;

    public Score(int life, int point) {
        this.life = life;
        this.point = point;
    }

    public String getTotalScore() {
        int res = 0;
        if (point != 0)
            res = point + life * 25;
        return String.valueOf(res);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int lf) {
        life = life + lf;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int pt) {
        point = point + pt;
    }
}
