package edu.fpoly.edict;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class InputWordFragment extends Fragment {
	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;
	
	public static InputWordFragment newInstance(String param1, String param2) {
		InputWordFragment fragment = new InputWordFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public InputWordFragment() {
		// Required empty public constructor
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}
	View viewLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		viewLayout =  inflater.inflate(R.layout.fragment_input_word, container, false);
		final View quacam1View = viewLayout.findViewById(R.id.thumb_quacam_1);
		quacam1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, quacam1View, R.drawable.quacam);
			}
		});

		final View quatao1View = viewLayout.findViewById(R.id.thumb_quatao_1);
		quatao1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, quatao1View, R.drawable.quatao);
			}
		});
		final View quadudu1View = viewLayout.findViewById(R.id.thumb_quadudu_1);
		quadudu1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, quadudu1View, R.drawable.quadudu);
			}
		});
		final View catim1View = viewLayout.findViewById(R.id.thumb_catim_1);
		catim1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, catim1View, R.drawable.catim);
			}
		});
		final View quale1View = viewLayout.findViewById(R.id.thumb_quale_1);
		quale1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, quale1View, R.drawable.quale);
			}
		});
		final View quaot1View = viewLayout.findViewById(R.id.thumb_quaot_1);
		quaot1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, quaot1View, R.drawable.quaot);
			}
		});

		mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
		
		return viewLayout;
	}
	
	private void zoomImageFromThumb(View parentLayout, final View thumbView, int imageResId) {
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}
		
		Log.d("Zoom Image: ", "" + imageResId + " ----------------------");

		final ImageView expandedImageView = (ImageView) parentLayout.findViewById(R.id.expanded_image);
		expandedImageView.setImageResource(imageResId);
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();
		thumbView.getGlobalVisibleRect(startBounds);
		parentLayout.findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);
		float startScale;
		if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds.width() / startBounds.height()) {
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}

		thumbView.setAlpha(0f);
		expandedImageView.setVisibility(View.VISIBLE);

		expandedImageView.setPivotX(0f);
		expandedImageView.setPivotY(0f);

		AnimatorSet set = new AnimatorSet();
		set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		final float startScaleFinal = startScale;
		expandedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				AnimatorSet set = new AnimatorSet();
				set.play(ObjectAnimator.ofFloat(expandedImageView, View.X,startBounds.left))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
				set.setDuration(mShortAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	
	}

}
