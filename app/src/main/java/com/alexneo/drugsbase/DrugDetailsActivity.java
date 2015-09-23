package com.alexneo.drugsbase;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class DrugDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_item);
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
