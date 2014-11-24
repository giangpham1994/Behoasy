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

public class LookupFragment extends Fragment {
	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;
	
	public static LookupFragment newInstance(String param1, String param2) {
		LookupFragment fragment = new LookupFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public LookupFragment() {
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
		viewLayout = inflater.inflate(R.layout.fragment_lookup, container, false);
		
		final View chobull1View = viewLayout.findViewById(R.id.thumb_chobull_1);
		chobull1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, chobull1View, R.drawable.chobull);
			}
		});

		final View huoucaoco1View = viewLayout.findViewById(R.id.thumb_huoucaoco_1);
		huoucaoco1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, huoucaoco1View, R.drawable.huoucaoco);
			}
		});
		final View conde1View = viewLayout.findViewById(R.id.thumb_conde_1);
		conde1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, conde1View, R.drawable.conde);
			}
		});
		final View ocsen1View = viewLayout.findViewById(R.id.thumb_ocsen_1);
		ocsen1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, ocsen1View, R.drawable.ocsen);
			}
		});
		final View congau1View = viewLayout.findViewById(R.id.thumb_congau_1);
		congau1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(viewLayout, congau1View, R.drawable.congau);
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
