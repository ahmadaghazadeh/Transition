package com.aghazadeh.ahmad.activityanimation.SharedElementsActivities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aghazadeh.ahmad.activityanimation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("X1", "Ahmad Aghazadeh");
        ImageView ivProfile= (ImageView) findViewById(R.id.ivProfile);
        TextView textView= (TextView) findViewById(R.id.textView);
        Pair<View, String> p1 = Pair.create((View)ivProfile, "profileImage");
        Pair<View, String> p3 = Pair.create((View)textView, "profileName");
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1, p3);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
}
