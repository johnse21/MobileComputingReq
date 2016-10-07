package com.mlabs.bbm.firstandroidapp_morningclass;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class OnTouchActivity extends AppCompatActivity {
    private double initX=0, initY=0, finalX=0, finalY=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_touch);

        final ImageView imgView = (ImageView)findViewById(R.id.imgView);
        final EditText x1Edit = (EditText)findViewById(R.id.x1Edit);
        final EditText x2Edit = (EditText)findViewById(R.id.x2Edit);
        final EditText y1Edit = (EditText)findViewById(R.id.y1Edit);
        final EditText y2Edit = (EditText)findViewById(R.id.y2Edit);

        imgView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        initX = motionEvent.getX();
                        initY = motionEvent.getY();
                        x1Edit.setText(String.format("%.2f",initX));
                        y1Edit.setText(String.format("%.2f",initY));
                        break;
                    case MotionEvent.ACTION_UP:
                        finalX = motionEvent.getX();
                        finalY = motionEvent.getY();
                        x2Edit.setText(String.format("%.2f",finalX));
                        y2Edit.setText(String.format("%.2f",finalY));

                        getQuadrant(imgView);
                        break;
                }
                return true;
            }
        });


    }

    public void getQuadrant(ImageView imgview){
        final EditText diffEdit = (EditText)findViewById(R.id.differenceEditText);
        final EditText quadEdit = (EditText)findViewById(R.id.quadrantEditText);
        String diff = "";

        diff = (initX > finalX) ? "X's: "+ String.format("%.2f",(initX - finalX)) : "X's: "+ String.format("%.2f",(finalX - initX));

        diff += (initY > finalY) ? " Y's: "+ String.format("%.2f",(initY - finalY)) : " Y's: "+ String.format("%.2f",(finalY - initY));

        diffEdit.setText(String.format("%s", diff));

        double midX = imgview.getWidth()/2, midY = imgview.getHeight()/2;

        if (finalX > midX && finalY < midY) {
            quadEdit.setText(String.format("%s", "QUADRANT 1"));
        }else if (finalX < midX && finalY < midY) {
            quadEdit.setText(String.format("%s", "QUADRANT 2"));
        }else if (finalX < midX && finalY > midY) {
            quadEdit.setText(String.format("%s", "QUADRANT 3"));
        }else if (finalX > midX && finalY > midY) {
            quadEdit.setText(String.format("%s", "QUADRANT 4"));
        }

    }
}
