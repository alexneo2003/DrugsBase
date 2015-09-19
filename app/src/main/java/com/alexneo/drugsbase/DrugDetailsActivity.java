package com.alexneo.drugsbase;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
        // тут ошибка будет, мне кажется.
        AddictionLevel addiction = (AddictionLevel) bundle.get("addiction");
        String cover = bundle.getString("cover");

        // ну в общем каи текста заполнять ты уже знаешь, покажу только ещё одну вещь
        textView.setText(string);
        descriptionView.setText(description);
        usageView.setText(usage);
        affectView.setText(affect);
        cautionsView.setText(cautions);
        priceView.setText("$"+price);
//        coverView.setImageDrawable(cover);
//        coverView.setImageResource(R.drawable.lsd);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drug_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent getActivityIntent(Context context, Drug drug) {

        Intent intent= new Intent(context, DrugDetailsActivity.class);
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
