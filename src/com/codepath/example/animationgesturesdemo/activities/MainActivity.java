package com.codepath.example.animationgesturesdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codepath.example.animationgesturesdemo.R;

public class MainActivity extends Activity {
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onLaunchDemo(View v) {
		if (v.getId() == R.id.btnAnimationDemo) {
			startActivity(new Intent(this, AnimationDemoActivity.class));
		} else {
			startActivity(new Intent(this, GestureDemoActivity.class));
		}
	}

}
