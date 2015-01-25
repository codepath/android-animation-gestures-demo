package com.codepath.example.animationgesturesdemo.gestures;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnDoubleTapListener implements OnTouchListener {

    private GestureDetector gestureDetector;
    
    public OnDoubleTapListener(Context c) {
      gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        
        @Override
        public boolean onDoubleTap(MotionEvent e) {
        	OnDoubleTapListener.this.onDoubleTap(e);
        	return super.onDoubleTap(e);
        }
    }
    
    public void onDoubleTap(MotionEvent e) {
    	
    }
}
