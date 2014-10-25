package com.app.kp.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;

public class LoadingView extends View {

	AnimatorSet animation;
	//float rotation, scaleX, scaleY;

	Path hexagon, cross;
	Paint hexagonPaint, crossPaint;
	Matrix transformationMatrix;

	public LoadingView(Context context) {
		super(context);
		// TODO 自動產生的建構子 Stub
		Initialize();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自動產生的建構子 Stub
		Initialize();
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自動產生的建構子 Stub
		Initialize();
	}

	void Initialize() {
		setScaleX(1);
		setScaleY(1);
		// ScaleX = ScaleY = 1;
		int baseDuration = 1400;
		animation = new AnimatorSet();
		ObjectAnimator rotation = ObjectAnimator.ofFloat(this, "rotation", 0, 60);
		rotation.setDuration(baseDuration);
		rotation.setRepeatCount(ValueAnimator.INFINITE);
		ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(this,
				PropertyValuesHolder.ofFloat("scaleX", 1, .9f), PropertyValuesHolder.ofFloat("scaleY", 1, .9f));
		scale.setRepeatMode(Animation.RESTART);
		scale.setDuration(baseDuration / 2);
		scale.setRepeatCount(ValueAnimator.INFINITE);
		animation.playTogether(rotation, scale);
		animation.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自動產生的方法 Stub
		super.onDraw(canvas);
		DrawHexagon(canvas);

		canvas.save();
		
		canvas.scale(.4f, .5f, getWidth() / 2, getHeight() / 2);
		//DrawCross(canvas);
		canvas.restore();
	}

	@Override
	public void setScaleX(float scaleX) {
		// TODO 自動產生的方法 Stub
		super.setScaleX(scaleX);
		invalidate();
	}

	@Override
	public void setScaleY(float scaleY) {
		// TODO 自動產生的方法 Stub
		super.setScaleY(scaleY);
		invalidate();
	}

	@Override
	public void setRotation(float rotation) {
		// TODO 自動產生的方法 Stub
		super.setRotation(rotation);
		invalidate();
	}

	void DrawHexagon(Canvas canvas) {
		// The extra padding is to avoid edges being clipped
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());
		int halfHeight = (getHeight() - padding) / 2;
		int side = (getWidth() - padding) / 2;
		int foo = (int) Math.sqrt(side * side - halfHeight * halfHeight);

		Path path;// = hexagon ?? (hexagon = new Path ());
		if (hexagon != null) {
			path = hexagon;
		} else {
			
			path = hexagon = new Path();
		}
		hexagon.reset();
		path.moveTo(getWidth() / 2, padding / 2);
		path.rLineTo(-side / 2, 0);
		path.rLineTo(-foo, halfHeight);
		path.rLineTo(foo, halfHeight);
		path.rLineTo(side, 0);
		path.rLineTo(foo, -halfHeight);
		path.rLineTo(-foo, -halfHeight);
		path.close();

		Matrix m;// = transformationMatrix ?? (transformationMatrix = new Matrix
					// ());
		if (transformationMatrix != null) {
			m = transformationMatrix;
		} else {
			
			m = transformationMatrix = new Matrix();
		}
		m.reset();
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		m.postRotate(getRotation(), centerX, centerY);
		m.postScale(getScaleX(), getScaleY(), centerX, centerY);
		path.transform(m);

		if (hexagonPaint == null) {
			hexagonPaint = new Paint();
			// {
			// Color = new android.Graphics.Color (0x22, 0x76, 0xB9),

			// };
			// Color color=new Color();
			// color.rgb(0x22, 0x76, 0xB9)
			hexagonPaint.setColor(Color.rgb(0x22, 0x76, 0xB9));
			hexagonPaint.setAntiAlias(true);
			hexagonPaint.setPathEffect(new CornerPathEffect(30));
		}

		canvas.drawPath(path, hexagonPaint);
	}

	void DrawCross(Canvas canvas) {
		int smallSegment = getWidth() / 6;

		Path path;// = cross ?? (cross = new Path ());
		if (cross != null) {
			path = cross;
		} else {
			
			path = cross = new Path();
		}
		cross.reset();
		
		path.moveTo(0, 0);
		path.rLineTo(smallSegment, 0);
		path.lineTo(getWidth() / 2, getHeight() / 2);
		path.lineTo(getWidth() - smallSegment, 0);
		path.rLineTo(smallSegment, 0);
		path.lineTo(getWidth() / 2 + smallSegment, getHeight() / 2);
		path.lineTo(getWidth(), getHeight());
		path.rLineTo(-smallSegment, 0);
		path.lineTo(getWidth() / 2, getHeight() / 2);
		path.lineTo(smallSegment, getHeight());
		path.rLineTo(-smallSegment, 0);
		path.lineTo(getWidth() / 2 - smallSegment, getHeight() / 2);
		path.close();

		if (crossPaint == null) {
			crossPaint = new Paint();// {
			// AntiAlias = true,
			// Color = Android.Graphics.Color.White
			// };
			crossPaint.setAntiAlias(true);
			crossPaint.setColor(Color.WHITE);
		}

		canvas.drawPath(path, crossPaint);
	}
}
