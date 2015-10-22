package br.com.targettrust.exercicio07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void startMeUpAnimation(View view) {
        final TextView tv = (TextView) findViewById(R.id.logo);
        //Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        //Animation anim = AnimationUtils.loadAnimation(this, R.anim.move_to_right_anim);
        //Animation anim = AnimationUtils.loadAnimation(this, R.anim.fall_bounce_anim);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        // tv.startAnimation(anim);  OU

        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv.startAnimation(anim);
                anim.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // We start the Activity

                    }
                });

            }
        });
    }

    public void scaleupAnimation(View view) {
        // Create a scale-up animation that originates at the button
        // being pressed.
        ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(view, 0, 0,
                view.getWidth(), view.getHeight());
        // Request the activity be started, using the custom animation options.
            startActivity(new Intent(MainActivity.this, AnimationActivity.class),
                opts.toBundle());
    }

    public void thumbNailScaleAnimation(View view) {
        view.setDrawingCacheEnabled(true);
        view.setPressed(false);
        view.refreshDrawableState();
        Bitmap bitmap = view.getDrawingCache();
        ActivityOptions opts = ActivityOptions.makeThumbnailScaleUpAnimation(
                view, bitmap, 0, 0);
        // Request the activity be started, using the custom animation options.
        startActivity(new Intent(MainActivity.this, AnimationActivity.class),
                opts.toBundle());
        view.setDrawingCacheEnabled(false);
    }

    public void fadeAnimation(View view) {
        ActivityOptions opts = ActivityOptions.makeCustomAnimation(
                MainActivity.this, R.anim.fade_in, R.anim.fade_out);
        // Request the activity be started, using the custom animation options.
        startActivity(new Intent(MainActivity.this, AnimationActivity.class),
                opts.toBundle());
    }

}
