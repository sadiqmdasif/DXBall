package com.apache.dxballfinal;
/**
 * Created by apache on 12-29-2015.
 */

import android.graphics.RectF;
import android.util.Log;

public class Ball {

    RectF ball;

    float speedY = 13, speedX = 13;

    public Ball(float centerX, float centerY, float radius) {

        ball = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);


    }

    public RectF getBall() {
        return ball;
    }

    protected void moveBall(float maxX, float maxY) {

        if (ball.top < 120 || ball.bottom > maxY) {
            speedY = -speedY;
        } else if (ball.left < 5 || ball.right > maxX - 5) {
            speedX = -speedX;
        }


        ball.left += speedX;
        ball.top += speedY;
        ball.right += speedX;
        ball.bottom += speedY;
    }

    protected void moveBallFromBrick() {

        speedY = -speedY;

        ball.left += speedX;
        ball.top += speedY;
        ball.right += speedX;
        ball.bottom += speedY;
    }

    protected void moveBallFromBar(Bar bar) {

        float barSize = bar.getBarRight() - bar.getBarLeft();

        Log.d("Ball: ", String.valueOf(ball.centerX()) + "Bar: " + String.valueOf(bar.getBarCenter()));

        if (ball.centerX() < bar.getBarCenter()) {
            if (ball.centerX() < bar.getBarCenter() - barSize / 4) {
                speedY = -speedY;
                speedX = -(speedX);
                Log.d("1: ", String.valueOf(bar.getBarCenter()) + "Ball:" + String.valueOf(ball.centerX()));

                ball.left += speedX;
                ball.top += speedY;
                ball.right += speedX;
                ball.bottom += speedY;
            } else if (ball.centerX() > bar.getBarCenter() - barSize / 4) {
                speedY = -speedY;
                Log.d("2: ", String.valueOf(bar.getBarCenter()) + "Ball:" + String.valueOf(ball.centerX()));

                ball.left += speedX;
                ball.top += speedY;
                ball.right += speedX;
                ball.bottom += speedY;
            }

        } else if (ball.centerX() > bar.getBarCenter()) {

            if (ball.centerX() < bar.getBarCenter() + barSize / 4) {
                speedY = -speedY;
                Log.d("3: ", String.valueOf(bar.getBarCenter()) + "Ball:" + String.valueOf(ball.centerX()));

                ball.left += speedX;
                ball.top += speedY;
                ball.right += speedX;
                ball.bottom += speedY;
            } else if (ball.centerX() > bar.getBarCenter() + barSize / 4) {
                speedY = -speedY;
                speedX = -(speedX);
                Log.d("4: ", String.valueOf(bar.getBarCenter()) + "Ball:" + String.valueOf(ball.centerX()));

                ball.left += speedX;
                ball.top += speedY;
                ball.right += speedX;
                ball.bottom += speedY;
            }
        }

    }
}