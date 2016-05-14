package com.apache.dxballfinal;

/**
 * Created by apache on 12-29-2015.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameCanvas extends View {


    public static Bar bar = new Bar(1, 1, 1, 1);
    public static float move = 40;
    Paint paint;
    ;
    Brick[] bricks = new Brick[200];
    Ball ball;
    Result result = new Result();
    float midX = 0, midY = 0, barX, barY = 0, maxX, maxY;
    int numBricks = 0;
    float ballX = 0, ballY = 0;
    boolean firstTime = true;
    List<Brick> brickList = new ArrayList<Brick>();
    Score score = new Score(0, 0);
    boolean gameOver = true;
    boolean firstStart = true;
    boolean stageClear = false;
    StringBuilder msg = new StringBuilder();
    private RectF surface;
    private int stage;
    private int totalBrickDestroyed;
    private int pointReset;
    private double[] gravity;
    private double accX = 0;
    private double accY = 0;
    private double accZ = 0;
    private double currentX = 0;

    public GameCanvas(Context context) {
        super(context);

        paint = new Paint();

    }

    public static Bar getBar() {
        return bar;
    }

    void start() {

        if (!stageClear) {
            score.setPoint(0);
        }

        if (firstStart) {
            stage = 1;
            score.setLife(3);

        }
        bricks = new Brick[200];
        result = new Result();
        brickList = new ArrayList<Brick>();
        score = new Score(0, 0);
        firstTime = true;
        gameOver = false;
        firstStart = false;
        totalBrickDestroyed = 0;

    }

    protected void onDraw(Canvas canvas) {
        if (firstTime) {
            firstTime = false;
            maxX = canvas.getWidth();
            maxY = canvas.getHeight();
            midX = maxX / 2;
            midY = maxY / 2;

            ballX = midX;
            ballY = midY;
            barX = 0;
            score.setLife(3);

            createSurface();
            createBricks(stage);
            createBall();
            createBar();
        }

        //moving bar ball

        if (!gameOver) {
            ball.moveBall(maxX, maxY);

        }
        //canvas setup
        canvas.drawRGB(255, 255, 255);
        paint.setColor(Color.GREEN);
        paint.setStyle(Style.FILL);

        drawBall(canvas);
        drawBar(canvas);
        drawBrick(canvas);
        drawScorecard(canvas);
        drawResult(canvas);

        collisionChecker();
        invalidate();

    }

    void drawBall(final Canvas canvas) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //draw ball
                paint.setColor(Color.rgb(235, 29, 2));
                canvas.drawOval(ball.getBall(), paint);
            }
        };
        runnable.run();
    }

    void drawBar(final Canvas canvas) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //draw bar
                paint.setColor(Color.rgb(103, 0, 62));
                canvas.drawRect(bar.getBar(), paint);
            }
        };
        runnable.run();
    }

    void drawBrick(final Canvas canvas) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                //draw brick
                for (int i = 0; i < numBricks; i++) {
                    if (bricks[i].brickOk) {
                        if (brickList.get(i) != null) {
                            paint.setColor(bricks[i].getBrickColor());
                            canvas.drawRect(brickList.get(i).getBrick(), paint);
                        }
                    }
                }
            }
        };
        runnable.run();
    }

    void drawScorecard(final Canvas canvas) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                //draw Scorecard
                paint.setColor(Color.rgb(0, 200, 100));
                canvas.drawRect(0, 0, maxX, 80, paint);
                paint.setColor(Color.WHITE);
                paint.setTextSize(50);
                canvas.drawText("STAGE: " + stage, 20, 60, paint);
                canvas.drawText("LIFE: " + score.getLife(), midX - 150, 60, paint);
                canvas.drawText("POINT: " + score.getPoint(), maxX - 400, 60, paint);
            }
        };
        runnable.run();
    }

    void drawResult(final Canvas canvas) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //draw Result
                isGameOver();
                if (gameOver) {
                    paint.setColor(Color.rgb(16, 185, 121));
                    canvas.drawRect(midX - 300, midY - 300, midX + 300, midY + 300, paint);
                    paint.setColor(Color.rgb(30, 80, 195));
                    if (!firstStart) {
                        paint.setTextSize(100);
                        canvas.drawText(result(), midX - 200, midY - 180, paint);
                        canvas.drawText("Total: " + score.getTotalScore(), midX - 220, midY, paint);

                        paint.setTextSize(75);
                        canvas.drawText("Touch To Play", midX - 250, midY + 200, paint);
                    } else {
                        paint.setTextSize(75);
                        canvas.drawText("Touch To Start", midX - 250, midY, paint);

                    }
                }
            }
        };
        runnable.run();


    }

    boolean isGameOver() {
        result();
        return gameOver;
    }


    public void createBricks(int stage) {
        int brickWidth = (int) (maxX / 5);
        int brickHeight = (int) (maxY / 15);

        numBricks = 0;

        switch (stage) {
            case 1:
                // Stage 1
                for (int column = 0; column < 5; column++) {
                    for (int row = 1; row < 5; row++) {

                        bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight, stage);
                        brickList.add(bricks[numBricks]);
                        numBricks++;
                    }
                }
                break;
            case 2:
                //Stage 2
                for (int column = 0; column < 5; column++) {
                    for (int row = 1; row < 5; row++) {
                        bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight, stage);
                        brickList.add(bricks[numBricks]);
                        numBricks++;
                    }
                }
                break;
            case 3:
                //Stage 3
                for (int column = 0; column < 5; column++) {
                    for (int row = 1; row < 5; row++) {
                        bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight, stage);
                        brickList.add(bricks[numBricks]);
                        numBricks++;
                    }
                }
                break;
            case 4:
                //Stage 4
                for (int column = 0; column < 5; column++) {
                    for (int row = 1; row < 6; row++) {
                        bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight, stage);
                        brickList.add(bricks[numBricks]);
                        numBricks++;
                    }
                }
                break;
            case 5:
                //Stage 5
                for (int column = 0; column < 5; column++) {
                    for (int row = 1; row < 6; row++) {
                        bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight, stage);
                        brickList.add(bricks[numBricks]);
                        numBricks++;
                    }
                }
                break;
            default:
                // Stage 1
                stage = 1;
                for (int column = 0; column < 5; column++) {
                    for (int row = 1; row < 5; row++) {

                        bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight, stage);
                        brickList.add(bricks[numBricks]);
                        numBricks++;
                    }
                }
                break;
        }

    }


    public void createBall() {
        ball = new Ball(ballX, ballY, 50);
    }

    public void createBar() {
        bar = new Bar(barX + (maxX / 2) - 175, maxY - 50, barX + (maxX / 2) + 175, maxY - 15);

    }

    public void createSurface() {
        surface = new RectF(0, maxY, maxX, maxY);
    }

    public boolean checkCollisionBar() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        runnable.run();

        if (RectF.intersects(ball.getBall(), bar.getBar())) {


            ball.moveBallFromBar(bar);

            return true;
        }
        return false;
    }

    public void checkCollisionBrick() {
        for (int i = 0; i < numBricks; i++) {
            if (RectF.intersects(ball.getBall(), bricks[i].getBrick()) && bricks[i].brickOk) {
                bricks[i].changeBrickColor();

                // Log.d("BC:", String.valueOf(bricks[i].getBrickColor()));

                if (bricks[i].bColor == 0) {
                    //   Log.d("BCW:", String.valueOf(bricks[i].getBrickColor()));
                    totalBrickDestroyed++;

                    bricks[i].brickOk = false;
                }
                ball.moveBallFromBrick();
                score.setPoint(10);

            }
        }


    }

    void ballDropped() {

        if (RectF.intersects(ball.getBall(), surface)) {
            score.setLife(-1);

            pointReset++;
            if (pointReset == 3) {
                score.setPoint(0);
                pointReset = 0;
            }
            createBall();
        }

    }

    String result() {

        if (score.getLife() != 0 && totalBrickDestroyed == numBricks && !gameOver) {
            gameOver = true;
            stageClear = true;
            msg = new StringBuilder();
            msg.append("You Win!!!");
            stage++;
            if (stage > 5) {
                stage = 1;
                gameOver = true;
            }

            Log.d("Stage", String.valueOf(msg));


        }
        if (score.getLife() == 0) {

            stageClear = false;
            gameOver = true;
            msg = new StringBuilder();
            msg.append("You Lost!");
            // msg.append(result.showMsgLost());

        }

        return msg.toString();
    }

    void collisionChecker() {
        Runnable rBall = new Runnable() {
            @Override
            public void run() {
             ballDropped();
            }
        };
        rBall.run();

        Runnable rBrick = new Runnable() {
            @Override
            public void run() {
                checkCollisionBrick();
            }
        };
        rBrick.run();

        Runnable rBar = new Runnable() {
            @Override
            public void run() {
                checkCollisionBar();
            }
        };
        rBar.run();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameOver) {
            start();
            gameOver = false;
        }

        float touchX = event.getX();
        if (maxX / 2 < touchX) {

            bar.moveBar(maxX, "right", move);
        } else if (maxX / 2 > touchX) {

            bar.moveBar(maxX, "left", -move);
        }

        return super.onTouchEvent(event);
    }


}
