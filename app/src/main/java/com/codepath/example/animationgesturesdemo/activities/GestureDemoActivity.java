package com.codepath.example.animationgesturesdemo.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.example.animationgesturesdemo.R;
import com.codepath.example.animationgesturesdemo.gestures.OnDoubleTapListener;
import com.codepath.example.animationgesturesdemo.gestures.OnSwipeTouchListener;

public class GestureDemoActivity extends Activity {

	private Button btnLongClick;
	private Button btnDoubleTap;
	private TextView tvSwipeMe;
	private TextView tvDragMe;
	private TextView tvDropZone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_demo);
		// Setup views
		btnLongClick = (Button) findViewById(R.id.btnLongClick);
		btnDoubleTap = (Button) findViewById(R.id.btnDoubleTap);
		tvSwipeMe = (TextView) findViewById(R.id.tvSwipeMe);
		tvDragMe = (TextView) findViewById(R.id.tvDragMe);
		tvDropZone = (TextView) findViewById(R.id.tvDropZone);
		// Setup gestures
		btnLongClick.setOnLongClickListener(longClickListener);
		btnDoubleTap.setOnTouchListener(new MyOnDoubleTapListener(this));
		tvSwipeMe.setOnTouchListener(new MyOnSwipeTouchListener(this));
		tvDragMe.setOnTouchListener(new MyTouchListener());
		tvDropZone.setOnDragListener(new MyDragListener()); 
	}

	private void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private OnLongClickListener longClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			showToast("Long clicked!");
			return true;
		}
	};

	private class MyOnDoubleTapListener extends OnDoubleTapListener {
		public MyOnDoubleTapListener(Context c) {
			super(c);
		}

		@Override
		public void onDoubleTap(MotionEvent e) {
			showToast("Double tapped!");
		}
	};

	private class MyOnSwipeTouchListener extends OnSwipeTouchListener {
		public MyOnSwipeTouchListener(Context c) {
			super(c);
		}

		@Override
		public void onSwipeDown() {
			showToast("Swiped down!");
		}

		@Override
		public void onSwipeLeft() {
			showToast("Swiped left!");
		}

		@Override
		public void onSwipeRight() {
			showToast("Swiped right!");
		}

		@Override
		public void onSwipeUp() {
			showToast("Swiped up!");
		}
	};
	
	private final class MyTouchListener implements OnTouchListener {
	    public boolean onTouch(View view, MotionEvent motionEvent) {
	      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        ClipData data = ClipData.newPlainText("", "");
	        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
	        view.startDrag(data, shadowBuilder, view, 0);
	        view.setVisibility(View.INVISIBLE);
	        return true;
	      } else {
	        return false;
	      }
	    }
	  }

	  private class MyDragListener implements OnDragListener {
	    Drawable enterShape = getResources().getDrawable(R.drawable.shape_border_green);
	    Drawable normalShape = getResources().getDrawable(R.drawable.shape_border_red);

	    @Override
	    public boolean onDrag(View v, DragEvent event) {
	      int action = event.getAction();
	      switch (action) {
	      case DragEvent.ACTION_DRAG_STARTED:
	        // do nothing
	        break;
	      case DragEvent.ACTION_DRAG_ENTERED:
	        v.setBackground(enterShape);
	        break;
	      case DragEvent.ACTION_DRAG_EXITED:
	        v.setBackground(normalShape);
	        break;
	      case DragEvent.ACTION_DROP:
	    	// Handle the dragged view being dropped over a target view
	    	View draggedTextView = (View) event.getLocalState();
	    	// Get View dragged item is being dropped on
	    	TextView dropTarget = (TextView) v; 
	    	dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
	    	dropTarget.setText("Dropped!");
	    	// Get owner of the dragged view
	        ViewGroup owner = (ViewGroup) draggedTextView.getParent();
	        // Remove the dragged view
	        owner.removeView(draggedTextView);
	        // Display toast
	        showToast("Dropped into zone!");
	        break; 
	      case DragEvent.ACTION_DRAG_ENDED:
	    	  if (event.getResult()) { // drop succeeded
	            v.setBackground(enterShape);
	    	  } else { // drop failed
	    		final View draggedView = (View) event.getLocalState();
	    		draggedView.post(new Runnable(){
					@Override
					public void run() {
						draggedView.setVisibility(View.VISIBLE);
					}
	    	    });
	    		v.setBackground(normalShape);
	    	  }
	      default:
	        break; 
	      }
	      return true;
	    }
	  }

}
