package com.alexneo.drugsbase;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DrugDetailsActivity extends AppCompatActivity implements ObservableScrollViewCallbacks{

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f; // 0.3f

    private View mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private TextView mTitleView;
    private TextView mPriceView;
    private TextView mAddictionView;
    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_item);

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mActionBarSize = getActionBarSize();

        mImageView = findViewById(R.id.cover);
        mOverlayView = findViewById(R.id.overlay);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mTitleView = (TextView) findViewById(R.id.title);
        mPriceView = (TextView) findViewById(R.id.price);
        mAddictionView = (TextView) findViewById(R.id.addiction);
        mTitleView.setText(getTitle());
        setTitle(null);

        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                onScrollChanged(0, false, false);

//                mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);

                // If you'd like to start from scrollY == 0, don't write like this:
                //mScrollView.scrollTo(0, 0);
                // The initial scrollY is 0, so it won't invoke onScrollChanged().
                // To do this, use the following:
                //onScrollChanged(0, false, false);

                // You can also achieve it with the following codes.
                // This causes scroll change from 1 to 0.
                //mScrollView.scrollTo(0, 1);
                //mScrollView.scrollTo(0, 0);
            }
        });

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

    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale price text
        float scalePrice = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mPriceView, 0);
        ViewHelper.setPivotY(mPriceView, 0);
        ViewHelper.setScaleX(mPriceView, scalePrice);
        ViewHelper.setScaleY(mPriceView, scalePrice);

        // Scale addiction text
        float scaleAddiction = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mAddictionView, 0);
        ViewHelper.setPivotY(mAddictionView, 0);
        ViewHelper.setScaleX(mAddictionView, scaleAddiction);
        ViewHelper.setScaleY(mAddictionView, scaleAddiction);

        // Scale title text
        float scaleTitle = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scaleTitle);
        ViewHelper.setScaleY(mTitleView, scaleTitle);


        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scaleTitle);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);

        // Translate price text
        int maxPriceTranslationY = (int) (mFlexibleSpaceImageHeight - mPriceView.getHeight() * scalePrice);
        int priceTranslationY = maxPriceTranslationY - scrollY - 96;
        ViewHelper.setTranslationY(mPriceView, priceTranslationY);

        // Translate addiction text
        int maxAddictionTranslationY = (int) (mFlexibleSpaceImageHeight - mAddictionView.getHeight() * scaleAddiction);
        int addictionTranslationY = maxAddictionTranslationY - scrollY - 96;
        ViewHelper.setTranslationY(mAddictionView, addictionTranslationY);

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

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
