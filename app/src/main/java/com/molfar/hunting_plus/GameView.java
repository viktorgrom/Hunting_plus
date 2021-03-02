package com.molfar.hunting_plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {

    Bitmap background;
    Rect rect;
    static int dWidth, dHeight;
    int width_ball, height_ball, width_target, height_target;

    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 10;

    ArrayList<Money1> money1;
    ArrayList<Money2> money2;

    Bitmap ball, target;
    float ballX, ballY;
    float sX, sY;
    float fX, fY;
    float dX, dY;
    float tempX, tempY;

    Paint borderPaint, scorePaint;

    int score = 0;
    int life = 10;
    Context context;

    boolean gameState = true;


    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bgrdd);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        //dWidth = dWidth/ 6;
        //dHeight = dHeight / 6;
        rect = new Rect(0, 0 , dHeight, dWidth);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();

            }
        };
        money1 = new ArrayList<>();
        money2 = new ArrayList<>();
        for (int i = 0; i<2; i++){
            Money1 money_1 = new Money1(context);
            money1.add(money_1);
            Money2 money_2 = new Money2(context);
            money2.add(money_2);
        }
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.heart_on);
        target = BitmapFactory.decodeResource(getResources(), R.drawable.heart_off);

        width_ball = ball.getWidth();
        height_ball = ball.getHeight();
        width_target = target.getWidth();
        height_target = target.getHeight();

        width_ball /= 2;
        height_ball /= 2;
        width_target /= 2;
        height_target /= 2;

        ball = Bitmap.createScaledBitmap(ball, width_ball, height_ball, false);
        target = Bitmap.createScaledBitmap(target, width_ball, height_ball, false);

        ballX = ballY = 0;
        sX = sY = fX= fY = 0;
        dX = dY = 0;
        tempX = tempY = 0;

        scorePaint = new Paint();
        scorePaint.setTextSize(256);
        scorePaint.setColor(Color.BLUE);

        borderPaint = new Paint();
        borderPaint.setColor(Color.RED);
        borderPaint.setStrokeWidth(5);



        this.context = context;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (life < 1){
            gameState = false;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("score", score);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
        canvas.drawBitmap(background, null , rect, null);

        for (int i=0; i<money1.size(); i++){
            canvas.drawBitmap(money1.get(i).getBitmap(), money1.get(i).moneyX, money1.get(i).moneyY, null);

            money1.get(i).moneyFrame++;
            if (money1.get(i).moneyFrame >3){
                money1.get(i).moneyFrame = 0;
            }
            money1.get(i).moneyX -= money1.get(i).velocity;
            if (money1.get(i).moneyX < -money1.get(i).getWidth()){
                money1.get(i).resetPosition();
                life--;
            }

            canvas.drawBitmap(money2.get(i).getBitmap(), money2.get(i).moneyX, money2.get(i).moneyY, null);

            money2.get(i).moneyFrame++;
            if (money2.get(i).moneyFrame >3){
                money2.get(i).moneyFrame = 0;
            }
            money2.get(i).moneyX -= money2.get(i).velocity;
            if (money2.get(i).moneyX < -money2.get(i).getWidth()){
                money2.get(i).resetPosition2();
                life --;
            }

            if (ballX <= (money1.get(i).moneyX + money1.get(i).getWidth())
                    && ballX + ball.getWidth() >= money1.get(i).moneyX
                    && ballY <= (money1.get(i).moneyY + money1.get(i).getHeith())
            && ballY >= money1.get(i).moneyY){
                money1.get(i).resetPosition();
                score++;
            }

            if (ballX <= (money2.get(i).moneyX + money2.get(i).getWidth())
                    && ballX + ball.getWidth() >= money2.get(i).moneyX
                    && ballY <= (money2.get(i).moneyY + money2.get(i).getHeith())
                    && ballY >= money2.get(i).moneyY){
                money2.get(i).resetPosition2();
                score++;
            }

        }
        if (sX > 0 && sY > dHeight * .6f){
            canvas.drawBitmap(target, sX - target.getWidth()/2, sY - target.getHeight()/2, null);
        }
        if ((Math.abs(fX - sX) > 0 || Math.abs(fY - sY) > 0) && fY > 0 && fY > dHeight *.6f){
            canvas.drawBitmap(target, fX - target.getWidth()/2, fY - target.getHeight()/2, null);
        }
        if ((Math.abs(dX) > 10 || Math.abs(dY) > 10) && sY > dHeight * .6f && fY > dHeight * .6f){
            ballX = fX - ball.getWidth()/2 - tempX;
            ballY = fY - ball.getHeight()/2 - tempY;
            canvas.drawBitmap(ball, ballX, ballY, null);
            tempX += dX;
            tempY += dY;

        }

        canvas.drawLine(0, dHeight * .6f, dWidth, dHeight * .6f, borderPaint);
        canvas.drawText(score + "", 200, 86, scorePaint);

        if (gameState)
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = dY = fX = fY = tempX = tempY = 0;
                sX = event.getX();
                sY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                fX = event.getX();
                fY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                dX = event.getX();
                dY = event.getY();
                ballX = event.getX();
                ballY = event.getX();
                dX = fX - sX;
                dY = fY - sY;
                break;
        }
        return true;
    }
}
