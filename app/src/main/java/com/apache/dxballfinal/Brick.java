package com.apache.dxballfinal;
/**
 * Created by apache on 12-29-2015.
 */

import android.graphics.Color;
import android.graphics.RectF;

public class Brick {

    public boolean brickOk;
    int bColor = 0;
    int brickColor = 0;
    int brickColorList[] = {Color.WHITE, Color.rgb(36, 159, 159),Color.rgb(128, 0, 128), Color.rgb(0, 97, 166),  Color.rgb(255, 146, 0), Color.rgb(252, 24, 0)};
    private RectF rect;

    public Brick(int row, int column, int width, int height, int stage) {

        brickOk = true;
        int padding = 10;
        brickColor = brickColorList[stage];
        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
        bColor = stage;

    }

    public void changeBrickColor() {
        bColor--;
        brickColor = brickColorList[bColor];
    }

    public int getBrickColor() {
        return brickColor;
    }

    public RectF getBrick() {
        return rect;
    }
}