package com.alexneo.drugsbase;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexneo.drugsbase.views.ObservableScrollViewWithFling;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrugDetailsActivity extends AppCompatActivity implements ObservableScrollViewCallbacks{

    ObservableScrollViewWithFling mScrollView;
    private TextView mTitleView; //Title used instead of Toolbar.title
    private TextView mPriceView;
    private TextView mAddictionView;
    Toolbar mToolbarView;
    protected LinearLayout llTintLayer; //Layout that we're tinting when scrolling

    protected FrameLayout flImage; //Layout that hosts the header image
    private int mParallaxImageHeight;
    private int mScrollY = 0; //Keeps track of our scroll.
    private boolean mIsToolbarShown = true;
    private int mToolbarHeight;

    private boolean goingUp = false;
    private int mToolbarBackgroundColor;

    public DrugDetailsActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_item_activity);

        //Store flexible space height
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);

        mScrollView = (ObservableScrollViewWithFling) findViewById(R.id.observable_sv);
        mTitleView = (TextView) findViewById(R.id.title);
        mPriceView = (TextView) findViewById(R.id.price);
        mAddictionView = (TextView) findViewById(R.id.addiction);
        mToolbarView = (Toolbar) findViewById(R.id.toolbar_view);
        llTintLayer = (LinearLayout) findViewById(R.id.ll_above_photo);
        flImage = (FrameLayout) findViewById(R.id.fl_image);


        TextView textView = (TextView) findViewById(R.id.title);
        TextView descriptionView = (TextView) findViewById(R.id.description);
        TextView usageView = (TextView) findViewById(R.id.usage);
        TextView affectView = (TextView) findViewById(R.id.affect);
        TextView cautionsView = (TextView) findViewById(R.id.cautions);
        TextView addictionView = (TextView) findViewById(R.id.addiction);
        TextView priceView = (TextView) findViewById(R.id.price);
        ImageView coverView = (ImageView) findViewById(R.id.cover);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String string =  bundle.getString("title");
        String description = bundle.getString("description");
        String usage = bundle.getString("usage");
        String cautions = bundle.getString("cautions");
        String affect = bundle.getString("affect");
        int price = bundle.getInt("price");
        AddictionLevel addiction = (AddictionLevel) bundle.get("addiction");
        String cover = bundle.getString("cover");

        textView.setText(string);
        descriptionView.setText(description);
        usageView.setText(usage);
        affectView.setText(affect);
        cautionsView.setText(cautions);
        priceView.setText("$" + price);
        ImageLoader.getInstance().displayImage(cover, coverView);

        assert addiction != null;
        switch (addiction) {
            case None:
                addictionView.setText(R.string.addiction_level_none);
                break;
            case Low:
                addictionView.setText(R.string.addiction_level_low);
                break;
            case Medium:
                addictionView.setText(R.string.addiction_level_medium);
                break;
            case High:
                addictionView.setText(R.string.addiction_level_high);
                break;
        }

        configureToolbarView();
        configureScrollView();

    }


    private void configureScrollView() {
        mScrollView.setScrollViewCallbacks(this);
        mScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mScrollView.setOnFlingListener(new ObservableScrollViewWithFling.OnFlingListener() {
            @Override
            public void onFlingStarted() {
                if (goingUp && !mIsToolbarShown) {
                    showFullToolbar(50);
                }
            }

            @Override
            public void onFlingStopped() {

            }
        });

        ViewTreeObserver vto = mTitleView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mTitleView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mTitleView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                updateFlexibleSpaceText(0);
            }
        });
    }

    private void configureToolbarView() {

        getSupportActionBar();

        mToolbarView.setNavigationIcon(R.mipmap.ic_arrow_left);
        mToolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Remove toolbars title, as we have our own title implementation
        mToolbarView.post(new Runnable() {
            @Override
            public void run() {
                mToolbarView.setTitle("");

            }
        });

        mToolbarBackgroundColor = getResources().getColor(R.color.colorPrimary);
        TypedValue tv = new TypedValue();
        if (this.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            mToolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        setBackgroundAlpha(mToolbarView, 0.0f, mToolbarBackgroundColor);
    }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        //Store actual scroll state:
        if (mScrollY > scrollY) {
            goingUp = true;
        } else if (mScrollY < scrollY) {
            goingUp = false;
        }

        //If we're close to edge, show toolbar faster
        if (mScrollY - scrollY > 50 && !mIsToolbarShown) {
            showFullToolbar(50); //speed up
        } else if (mScrollY - scrollY > 0 && scrollY <= mParallaxImageHeight && !mIsToolbarShown) {
            showFullToolbar(250);
        }

        //Show or hide full toolbar color, so it will become visible over scrollable content:
        if (scrollY >= mParallaxImageHeight - mToolbarHeight) {
            setBackgroundAlpha(mToolbarView, 1, mToolbarBackgroundColor);
        } else {
            setBackgroundAlpha(mToolbarView, 0, mToolbarBackgroundColor);
        }

        //Translate flexible image in Y axis
        ViewHelper.setTranslationY(flImage, scrollY / 2);

        //Calculate flexible space alpha based on scroll state
        float alpha = 1 - (float) Math.max(0, mParallaxImageHeight - (mToolbarHeight) - scrollY) / (mParallaxImageHeight - (mToolbarHeight * 1.5f));
        setBackgroundAlpha(llTintLayer, alpha, mToolbarBackgroundColor);

        //Store last scroll state
        mScrollY = scrollY;

        //Move the flexible text
        updateFlexibleSpaceText((scrollY));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        //If we're scrolling up, and are too far away from toolbar, hide it:
        if (scrollState == ScrollState.UP) {
            if (mScrollY > mParallaxImageHeight) {
                if (mIsToolbarShown) {
                    hideFullToolbar();
                }
            } else {
                // Don't hide toolbar yet
            }
        } else if (scrollState == ScrollState.DOWN) {
            //Show toolbar as fast as we're starting to scroll down
            if (!mIsToolbarShown) {
                showFullToolbar(250);
            }
        }
    }

    private void setBackgroundAlpha(View view, float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        view.setBackgroundColor(a + rgb);
    }


    public void showFullToolbar(int duration) {
        mIsToolbarShown = true;

        final AnimatorSet animatorSet = buildAnimationSet(duration,
                buildAnimation(mToolbarView, -mToolbarHeight, 0),
                buildAnimation(mTitleView, -mToolbarHeight, 0));

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                updateFlexibleSpaceText(mScrollY); //dirty update fling-fix
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                updateFlexibleSpaceText(mScrollY); //dirty update fling-fix
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animatorSet.start();

    }

    private ObjectAnimator buildAnimation(View view, float from, float to) {
        return ObjectAnimator
                .ofFloat(view, View.TRANSLATION_Y, from, to);
    }

    public void hideFullToolbar() {
        mIsToolbarShown = false;
        final AnimatorSet animatorSet = buildAnimationSet(250,
                buildAnimation(mToolbarView, 0, -mToolbarHeight),
                buildAnimation(mTitleView, 0, -mToolbarHeight));
        animatorSet.start();
    }

    private AnimatorSet buildAnimationSet(int duration, ObjectAnimator... objectAnimators) {

        AnimatorSet a = new AnimatorSet();
        a.playTogether(objectAnimators);
        a.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.accelerate_decelerate));
        a.setDuration(duration);

        return a;
    }

    /**
     * Scale title view and move it in Flexible space
     * @param scrollY
     */
    private void updateFlexibleSpaceText(final int scrollY) {
        if (!mIsToolbarShown) return;

        int adjustedScrollY = scrollY;
        if (scrollY < 0) {
            adjustedScrollY = 0;
        } else if (scrollY > mParallaxImageHeight) {
            adjustedScrollY = mParallaxImageHeight;
        }

        float maxScale = 0.4f;
        float scaleTitle = maxScale * ((float) (mParallaxImageHeight - mToolbarHeight) - adjustedScrollY) / (mParallaxImageHeight - mToolbarHeight);
        if (scaleTitle < 0) {
            scaleTitle = 0;
        }


        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, 1 + scaleTitle); // 1 + scale
        ViewHelper.setScaleY(mTitleView, 1 + scaleTitle); // 1 + scale

        // Translate title text
//        int maxTitleTranslation = (int) (mParallaxImageHeight - mTitleView.getHeight() * scaleTitle);
        int maxTitleTranslation = (int) (mParallaxImageHeight * 0.7f);
        int titleTranslation = (int) (maxTitleTranslation * ((float) scaleTitle / maxScale));
        ViewHelper.setTranslationY(mTitleView, titleTranslation);

        ViewHelper.setPivotX(mPriceView, 0);
        ViewHelper.setPivotY(mPriceView, 0);
        ViewHelper.setScaleX(mPriceView, 1); // 1 + scale
        ViewHelper.setScaleY(mPriceView, 1); // 1 + scale

        // Translate price text
        int maxPriceTranslation = (int) (mParallaxImageHeight * 0.7f);
        int priceTranslation = (int) (maxPriceTranslation * ((float) scaleTitle / maxScale) - 56);
        ViewHelper.setTranslationY(mPriceView, priceTranslation);

        ViewHelper.setPivotX(mAddictionView, 0);
        ViewHelper.setPivotY(mAddictionView, 0);
        ViewHelper.setScaleX(mAddictionView, 1); // 1 + scale
        ViewHelper.setScaleY(mAddictionView, 1); // 1 + scale

        // Translate addiction text
        int maxAddictionTranslation = (int) (mParallaxImageHeight * 0.7f);
        int addictionTranslation = (int) (maxAddictionTranslation * ((float) scaleTitle / maxScale) - 36);
        ViewHelper.setTranslationY(mAddictionView, addictionTranslation);


    }

    public static Intent getActivityIntent(Context context, Drug drug) {

        Intent intent= new Intent(context, DrugDetailsActivity.class);
        intent.putExtra("id", drug.id);
        intent.putExtra("title", drug.name);
        intent.putExtra("description", drug.description);
        intent.putExtra("usage", drug.usage);
        intent.putExtra("affect", drug.affect);
        intent.putExtra("cautions", drug.cautions);
        intent.putExtra("addiction", drug.addiction);
        intent.putExtra("price", drug.price);
        intent.putExtra("cover", drug.cover);
        return intent;
    }
}
