package com.codepath.example.animationgesturesdemo.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.example.animationgesturesdemo.R;

public class AnimationDemoActivity extends Activity {
	private Button btnFadeOut;
	private TextView tvMessage;
	private Button btnRepeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_demo);
		btnFadeOut = (Button) findViewById(R.id.btnFade);
		btnRepeat = (Button) findViewById(R.id.btnRepeat);
		tvMessage = (TextView) findViewById(R.id.tvMessage);
	}

	// Fade out and then when completed fade back in
	public void onFadeOut(View v) {
		btnFadeOut.animate().alpha(0).setDuration(2000).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				btnFadeOut.animate().alpha(1f).setDuration(2000);
			}
		});
	}

	// Slide message from button up to display, then later slide out
	public void onSlideMessage(View v) {
		tvMessage.setVisibility(View.VISIBLE);
		final int yPosInitial = getScreenHeight() + tvMessage.getHeight();
		int yPosDest = tvMessage.getHeight();
		tvMessage.setY(yPosInitial);
		tvMessage.animate().translationY(yPosDest).setDuration(2000).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				tvMessage.animate().translationY(yPosInitial).setDuration(2000).setStartDelay(2000);
			}
		});
	}
	
	public void onRepeatAnimation(View v) {
		// Setup scale Y
		ObjectAnimator scaleAnimY = ObjectAnimator.ofFloat(btnRepeat, "scaleY", 1.0f, 1.5f);
		scaleAnimY.setDuration(3000);
		scaleAnimY.setRepeatCount(ValueAnimator.INFINITE);
		scaleAnimY.setRepeatMode(ValueAnimator.REVERSE);
		// Setup scale X
		ObjectAnimator scaleAnimX = ObjectAnimator.ofFloat(btnRepeat, "scaleX", 1.0f, 1.5f);
		scaleAnimX.setDuration(3000);
		scaleAnimX.setRepeatCount(ValueAnimator.INFINITE);
		scaleAnimX.setRepeatMode(ValueAnimator.REVERSE);
		// Setup animation set
		AnimatorSet set = new AnimatorSet();
		set.playTogether(scaleAnimX, scaleAnimY);
		set.start();		
	}
	
	public void onActivityTransition(View v) {
		Intent i = new Intent(AnimationDemoActivity.this, MainActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
	}

	// Returns height of the screen
	public int getScreenHeight() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
}
