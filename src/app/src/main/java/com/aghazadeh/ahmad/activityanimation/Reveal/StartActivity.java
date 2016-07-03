package com.aghazadeh.ahmad.activityanimation.Reveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
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
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aghazadeh.ahmad.activityanimation.R;
import com.aghazadeh.ahmad.activityanimation.SharedElementsActivities.MainActivity;

import java.io.File;

public class StartActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                /*getPackageManager().setComponentEnabledSetting(
                        new ComponentName("com.aghazadeh.ahmad.activityanimation", "com.aghazadeh.ahmad.activityanimation.MainActivity-Red"),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);*/

                installShortcut(10);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity() {

        int cx = rootLayout.getWidth() / 2;
        int cy = rootLayout.getHeight() / 2;

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();
    }


    public   void installShortcut(long did) {
        try {
            Intent addIntent = createShortcutIntent(this,did, false);
            addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            sendBroadcast(addIntent);
        } catch (Exception e) {

        }
    }

    public   void uninstallShortcut(long did) {
        try {
            Intent addIntent = createShortcutIntent(this,did, true);
            addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
            sendBroadcast(addIntent);
        } catch (Exception e) {

        }
    }

    public static float density = 1;
    private static Paint roundPaint;
    private static RectF bitmapRect;

    private  Intent createShortcutIntent(Context context, long did, boolean forDelete) {
        Intent shortcutIntent = new Intent(context, MainActivity.class);
        shortcutIntent.setAction("com.aghazadeh.ahmad.activityanimation" + did);
        shortcutIntent.addFlags(0x4000000);

        String photo = null;
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "salaaaam");
        addIntent.putExtra("duplicate", false);
        if (!forDelete) {
            Bitmap bitmap = null;
            if (photo != null) {
                try {

                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.flower_1);
                    if (bitmap != null) {
                        int size = dp(58);
                        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                        result.eraseColor(Color.TRANSPARENT);
                        Canvas canvas = new Canvas(result);
                        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                        if (roundPaint == null) {
                            roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            bitmapRect = new RectF();
                        }
                        float scale = size / (float) bitmap.getWidth();
                        canvas.save();
                        canvas.scale(scale, scale);
                        roundPaint.setShader(shader);
                        bitmapRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        canvas.drawRoundRect(bitmapRect, bitmap.getWidth(), bitmap.getHeight(), roundPaint);
                        canvas.restore();
                        Drawable drawable = context.getResources().getDrawable(R.drawable.flower_1);
                        int w = dp(15);
                        int left = size - w - dp(2);
                        int top = size - w - dp(2);
                        drawable.setBounds(left, top, left + w, top + w);
                        drawable.draw(canvas);
                        try {
                            canvas.setBitmap(null);
                        } catch (Exception e) {
                            //don't promt, this will crash on 2.x
                        }
                        bitmap = result;
                    }
                } catch (Throwable e) {

                }
            }
            if (bitmap != null) {
                addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap);
            } else {

                addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.flower_1));
                // addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.book_user));
                // addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.book_channel));
                //addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.book_group));

            }
        }


        return addIntent;
    }

    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

}
