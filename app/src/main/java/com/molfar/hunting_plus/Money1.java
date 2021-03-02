package com.molfar.hunting_plus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import static com.molfar.hunting_plus.GameView.dWidth;

public class Money1 {

    Bitmap money[] = new Bitmap[4];

    int moneyX, moneyY, velocity, moneyFrame;
    int width, height;

    Random random;

    public Money1 (Context context){

        money[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.money_fly1);
        money[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.money_fly2);
        money[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.money_fly3);
        money[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.money_fly4);

        width = money[1].getWidth();
        height = money[1].getHeight();

        width /= 2;
        height /= 2;

        money[0] = Bitmap.createScaledBitmap(money[0], width, height, false);
        money[1] = Bitmap.createScaledBitmap(money[1], width, height, false);
        money[2] = Bitmap.createScaledBitmap(money[2], width, height, false);
        money[3] = Bitmap.createScaledBitmap(money[3], width, height, false);


        random = new Random();
        moneyFrame = 0;
        resetPosition();

    }

    public Bitmap getBitmap(){
        return  money[moneyFrame];
    }

    public int getWidth(){
        return  money[0].getWidth();
    }

    public int getHeith(){
        return money[0].getHeight();
    }


    public void resetPosition() {
        moneyX = dWidth + random.nextInt(1200);
        moneyY = random.nextInt(300);
        velocity = 14 + random.nextInt(17);
    }
}
