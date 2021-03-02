package com.mercurc.huntingmoney;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import static com.mercurc.huntingmoney.GameView.dWidth;

public class Money1 {

    Bitmap money[] = new Bitmap[4];

    int moneyX, moneyY, velocity, moneyFrame;
    int width, height;

    Random random;

    public Money1 (Context context){

        money[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.parashut1_1);
        money[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.parashut1_2);
        money[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.parashut1_3);
        money[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.parashut1_4);

        width = money[1].getWidth();
        height = money[1].getHeight();

        width /= 4;
        height /= 4;

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
        velocity = 5 + random.nextInt(15);
    }
}
