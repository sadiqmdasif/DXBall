package com.apache.dxballfinal;
/**
 * Created by apache on 12-29-2015.
 */

import android.graphics.RectF;

public class Bar extends RectF {
    float barLeft, barRight;
    private RectF bar;

    public Bar() {
    }

    ;

    public Bar(float left, float top, float right, float bottom) {

        bar = new RectF(left, top, right, bottom);
    }


    public RectF getBar() {
        return bar;
    }

    public float getBarCenter() {
        return bar.centerX();
    }

    public float getBarRight() {
        return bar.right;
    }

    public float getBarLeft() {
        return bar.left;
    }


    public void moveBar(float maxX, String dir, float move) {

        if (dir == "left") {
            if ((barLeft > move) && (bar.left + move > 0)) {

                bar.left += move;
                bar.right += move;
                barLeft = bar.left;
                barRight = bar.right;

            }
        } else if (dir == "right") {
            if ((barRight < (maxX - move)) && (bar.right < maxX)) {

                bar.left += move;
                bar.right += move;
                barLeft = bar.left;
                barRight = bar.right;
            }
        }
    }
}