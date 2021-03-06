package com.example.yachtdice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Attr;
import org.w3c.dom.Text;

import java.util.Random;

public class DiceView extends ConstraintLayout {
    //주사위 각각의 정보
    class DiceInfo {
        ImageView img;
        Animation anim;
        int value = 1; //주사위 눈 값
        Boolean keep = false; //킵하는지 저장
        int id;

        public DiceInfo(int id) {
            this.id = id;
            value = 1;
            keep = false;
            img = (ImageView) findViewById(id);
        }
    }

    ConstraintLayout cl;
    private final int diceNumber = 5;
    private int rollCount;
    DiceInfo dice[];
    //주사위 눈 바뀌는 애니메이션
    private final int[] ani = new int[]
            {R.drawable.anim1, R.drawable.anim2, R.drawable.anim3, R.drawable.anim4, R.drawable.anim5, R.drawable.anim6};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public DiceView(Context context) {
        this(context, null);
    }

    public DiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiceView(Context context, AttributeSet attrs, int defStyleAttrs){
        super(context, attrs, defStyleAttrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflate(getContext(), R.layout.dice_view, (ViewGroup) getParent());
        cl = (ConstraintLayout) inflater.inflate(R.layout.dice_view, null);
        // 메인에 다이스 뷰 등록
        View view = View.inflate(context, R.layout.dice_view, this);

        //주사위 5개 객체 생성
        dice = new DiceInfo[diceNumber];
        int id = 0;
        for (int i = 0; i < diceNumber; i++) {
            id = getResources().getIdentifier("dice"+(i+1), "id", "com.example.yachtdice");
//            id = getResources().getIdentifier("dice1", "id", "com.example.yachtdice");
            dice[i] = new DiceInfo(id);
            dice[i].img = (ImageView) cl.findViewById(id);
        }

        rollCount = 0;
    }

    //주사위 굴리기
    public void rollDice() {
        Random rand = new Random();

        for (int i=0;i<diceNumber;i++){
            if(dice[i].keep == false){
                int r = rand.nextInt(6)+1;
                int resID = getResources().getIdentifier("dice"+r,"drawable","com.example.yachtdice");
    //            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),getResources().getIdentifier("anim"+result[i],"drawable", getApplicationContext().getPackageName()));
    //            dice[i].img.startAnimation(anim);

                // 이미지 변경       ** 작동 안됨
                dice[i].img.setImageResource(resID);
                dice[i].value = r;
            }
        }
    }

    //주사위 킵
    public void keepDice(int index) {
        DiceInfo d = getDice(index);
        if(d.keep == true){
            d.keep = false;
        } else {
            d.keep = true;
        }
        // 애니메이션 추가 필요
    }

    //주사위 값 반환
    public int[] getDiceValues() {
        int[] values = new int[diceNumber];
        for (int i = 0; i < diceNumber; i++) {
            values[i] = dice[i].value;
        }
        return values;
    }

    // 객체 id로 주사위 찾기
    public DiceInfo getDice(int id){
        for(int i=0; i<diceNumber;i++){
            if(dice[i].id == id){
                return dice[i];
            }
        }
        // 찾기 오류
        return new DiceInfo(0);
    }
}
