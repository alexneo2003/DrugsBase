package com.alexneo.drugsbase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DrugDetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_item);
        TextView textView = (TextView) findViewById(R.id.title);
        TextView addictionView = (TextView) findViewById(R.id.addiction);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String string =  bundle.getString("title");
        String description = bundle.getString("description");
        String causions = bundle.getString("causions");
        String affect = bundle.getString("affect");
        String usage = bundle.getString("usage");
        int price = bundle.getInt("price");
        // тут ошибка будет, мне кажется.
        AddictionLevel addiction = (AddictionLevel) bundle.get("addiction");
        String cover = bundle.getString("cover");

        // ну в общем каи текста заполнять ты уже знаешь, покажу только ещё одну вещь
        textView.setText(string);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drug_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent getActivityIntent(Context context, Drug drug) {

        Intent intent = new Intent(context, DrugDetailsActivity.class);
        intent.putExtra("title", drug.name);
        intent.putExtra("description", drug.description);
        intent.putExtra("usage", drug.usage);
        intent.putExtra("affect", drug.affect);
        intent.putExtra("causions", drug.causions);
        intent.putExtra("addiction", drug.addiction);
        intent.putExtra("price", drug.price);
        intent.putExtra("cover", drug.cover);
        return intent;
    }
}
