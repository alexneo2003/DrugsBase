package com.alexneo.drugsbase.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

public class IconTabLayout extends TabLayout {

    public IconTabLayout(Context context) {
        super(context);
    }

    public IconTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void setTabsFromPagerAdapter(@NonNull PagerAdapter adapter) {
        this.removeAllTabs();
        int i = 0;

        for(int count = adapter.getCount(); i < count; ++i) {
            if(adapter instanceof IconTabProvider)
                this.addTab(this.newTab().setIcon(((IconTabProvider)adapter).getPageIcon(i)));
            else
                this.addTab(this.newTab().setText(adapter.getPageTitle(i)));
        }

    }


}
