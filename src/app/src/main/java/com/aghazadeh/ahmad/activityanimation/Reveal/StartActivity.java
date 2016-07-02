package com.aghazadeh.ahmad.activityanimation.Reveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.aghazadeh.ahmad.activityanimation.R;

public class StartActivity extends AppCompatActivity {
    private RelativeLayout bgViewGroup;
    private Interpolator interpolator;
    private static final int DELAY = 100;
    boolean hidden=true;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                 

                    // finding X and Y co-ordinates
                    int cx = (fab.getLeft() + fab.getRight());
                    int cy = (fab.getTop());

                    // to find  radius when icon is tapped for showing layout
                    int startradius=0;
                    int endradius = Math.max(1000, 1000);

                    // performing circular reveal when icon will be tapped
                    Animator animator = ViewAnimationUtils.createCircularReveal(fab,                     cx, cy, startradius, endradius);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(400);

                    //reverse animation
                    // to find radius when icon is tapped again for hiding layout
                    //  starting radius will be the radius or the extent to which circular reveal animation is to be shown

                    int reverse_startradius = Math.max(fab.getWidth(),fab.getHeight());

                    //endradius will be zero
                    int reverse_endradius=0;

                    // performing circular reveal for reverse animation
                    Animator animate = ViewAnimationUtils.createCircularReveal(fab,cx,cy,reverse_startradius,reverse_endradius);
                    if(hidden){

                        // to show the layout when icon is tapped
                        fab.setVisibility(View.VISIBLE);
                        animator.start();
                        hidden = false;
                    }
                    else {
                        fab.setVisibility(View.VISIBLE);

                        // to hide layout on animation end
                        animate.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                fab.setVisibility(View.INVISIBLE);
                                hidden = true;
                            }
                        });
                        animate.start();
                    }
                    


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


}
