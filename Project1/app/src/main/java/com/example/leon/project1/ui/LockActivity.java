package com.example.leon.project1.ui;

/**
 * Created by Leon on 3/7/2016.
 */

        import android.animation.ArgbEvaluator;
        import android.animation.ObjectAnimator;
        import android.app.Activity;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.os.Handler;
        import android.provider.Settings;
        import android.support.v7.graphics.Palette;
        import android.util.Log;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.afollestad.materialdialogs.MaterialDialog;
        import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback.*;
        import com.example.leon.project1.FingerprintScan;
        //import com.example.leon.project1.R;
        import com.example.leon.project1.util.LogFile;
        import com.example.leon.project1.util.Util;
        import com.samsung.android.sdk.pass.SpassFingerprint;

public class LockActivity extends Activity implements FingerprintScan.FingerprintScanListener {

    private TextView label;
    private TextView unlockMsg;
    private ImageView icon;
    private ImageView fingerprintAnim;
    private TextView resLabel;

    private int titleTextColor;
    private int bodyTextColor;

    private FingerprintScan scan;
    private int attempts = 0;

    private int backgroundColor;

    private boolean backPressed = false;
    private boolean onFocus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //
        setContentView(com.example.leon.project1.R.layout.lock_fake_activity);

        //label = (TextView) findViewById(com.example.leon.project1.R.id.label_locked);
        icon = (ImageView) findViewById(com.example.leon.project1.R.id.icon_locked);
        unlockMsg = (TextView) findViewById(com.example.leon.project1.R.id.unlock_msg_textview);
        fingerprintAnim = (ImageView) findViewById(com.example.leon.project1.R.id.fingerprintImage);
        resLabel = (TextView) findViewById(com.example.leon.project1.R.id.textViewSpass);

        backgroundColor = getIntent().getIntExtra(Util.BACKGROUND_COLOR, 0);
        getWindow().getDecorView().setBackgroundColor(backgroundColor);

        startLocking();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        //change in orientation or in settings
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(com.example.leon.project1.R.layout.lock_fake_activity);
            label = (TextView) findViewById(com.example.leon.project1.R.id.label_locked);
            icon = (ImageView) findViewById(com.example.leon.project1.R.id.icon_locked);
            fingerprintAnim = (ImageView) findViewById(com.example.leon.project1.R.id.fingerprintImage);
            resLabel = (TextView) findViewById(com.example.leon.project1.R.id.textViewSpass);
            unlockMsg = (TextView) findViewById(com.example.leon.project1.R.id.unlock_msg_textview);
        }

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(com.example.leon.project1.R.layout.lock_fake_activity_land );
            label = (TextView) findViewById(com.example.leon.project1.R.id.label_locked);
            icon = (ImageView) findViewById(com.example.leon.project1.R.id.icon_locked);
            fingerprintAnim = (ImageView) findViewById(com.example.leon.project1.R.id.fingerprintImage);
            resLabel = (TextView) findViewById(com.example.leon.project1.R.id.textViewSpass);
            unlockMsg = (TextView) findViewById(com.example.leon.project1.R.id.unlock_msg_textview);
        }

        if (bodyTextColor != 0 && titleTextColor != 0) {
            label.setTextColor(titleTextColor);
            unlockMsg.setTextColor(bodyTextColor);
            resLabel.setTextColor(bodyTextColor);
        }
        icon.setImageResource(com.example.leon.project1.R.drawable.ic_background);
        label.setText("FingerLock");
    }

    @Override
    protected void onStart() {
        super.onStart();
        onFocus = true;
        backPressed = false;
        //object of FingerScanner is identifying
        if(!scan.isIdentifying()) {
            startLocking();
        }
    }

    //if user lifts finger before scan complete
    @Override
    protected void onStop() {
        onFocus = false;
        scan.cancelIdentify();
        super.onStop();
    }

    //If back button pressed then go to previous screen
    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();
    }

    //start locking function
    protected void startLocking() {

        prepareWindow();

        scan = new FingerprintScan(getApplicationContext(), this);
        scan.initialize();
    }

    protected void prepareWindow() {

        icon.setImageResource(com.example.leon.project1.R.drawable.ic_background);
        label.setText(getString(com.example.leon.project1.R.string.app_name));
        //draw icon
        Bitmap icBitmap = Util.drawableToBitmap(icon.getDrawable());

        Palette.generateAsync(icBitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                if (swatch != null) {
                    //used for animations of buttons
                    final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(
                            getWindow().getDecorView(),
                            "backgroundColor",
                            new ArgbEvaluator(),
                            backgroundColor,
                            swatch.getRgb());
                    backgroundColorAnimator.setDuration(1000);
                    backgroundColorAnimator.start();

                    titleTextColor = swatch.getTitleTextColor();
                    bodyTextColor = swatch.getBodyTextColor();

                    label.setTextColor(titleTextColor);
                    unlockMsg.setTextColor(bodyTextColor);
                    resLabel.setTextColor(bodyTextColor);
                }
            }
        });

    }

    //if authentication succeds then send result as true to wrapper activity
    @Override
    public void onIdentifySucceded() {
        fingerprintAnim.setImageResource(com.example.leon.project1.R.drawable.scan_success);
        resLabel.setText(getString(com.example.leon.project1.R.string.spass_auth_success));
        sendActivityResultOk();
    }

    @Override
    public void onIdentifyStarted() {
        fingerprintAnim.setImageResource(com.example.leon.project1.R.drawable.scan_dot);
    }

    @Override
    public void onIdentifyReady() {
    }

    @Override
    public void onIdentifyFailed(int status) {
        fingerprintAnim.setImageResource(com.example.leon.project1.R.drawable.scan_mismatch);

        Log.e("SPASS", "Failed: " + FingerprintScan.getEventStatusName(status));
        if (status == SpassFingerprint.STATUS_AUTHENTIFICATION_FAILED) {
            attempts++;
            if (attempts < 5) {
                setFailedAnimationEnd();
                resLabel.setText(getString(com.example.leon.project1.R.string.spass_auth_failed));
                scan = new FingerprintScan(getApplicationContext(), this);
                scan.initialize();
            } else {
                LogFile.i(this, "Access to FingerLock failed");
                onIdentifyErrorAttempts();
            }
        } else if (status == SpassFingerprint.STATUS_QUALITY_FAILED) {
            setFailedAnimationEnd();
            resLabel.setText(getString(com.example.leon.project1.R.string.spass_quality_failed));
            scan = new FingerprintScan(getApplicationContext(), this);
            scan.initialize();
        } else if (status == SpassFingerprint.STATUS_SENSOR_FAILED) {
            setFailedAnimationEnd();
            resLabel.setText(getString(com.example.leon.project1.R.string.spass_sensor_failed));
        } else {
            if (onFocus || backPressed) {
                LogFile.i(this, "Access to FingerLock failed");
                sendActivityResultCancel();
            } else {
                setFailedAnimationEnd();
            }
        }
    }

    @Override
    public void onIdentifyErrorAttempts() {
        fingerprintAnim.setImageResource(com.example.leon.project1.R.drawable.scan_mismatch);
        resLabel.setText(getString(com.example.leon.project1.R.string.spass_auth_failed_attempts));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendActivityResultCancel();
            }
        }, 3000);
    }

    @Override
    public void onInitializationFailed(int msg) {
        new MaterialDialog.Builder(this)
                .title("Error")
                .content(msg)
                .positiveText("OK")  // the default is 'Accept'
                .negativeText("Cancel")
                .titleColorRes(com.example.leon.project1.R.color.primaryColor)
                .positiveColorRes(com.example.leon.project1.R.color.accentColor)
                .negativeColorRes(com.example.leon.project1.R.color.accentColor)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        sendActivityResultCancel();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                        sendActivityResultOk();
                    }
                })
                .cancelable(false)
                .build()
                .show();
    }

    @Override
    public void onUnregisteredFingerprints() {

        new MaterialDialog.Builder(this)
                .title("Error")
                .content(com.example.leon.project1.R.string.no_finger)
                .positiveText(com.example.leon.project1.R.string.register)  // the default is 'Accept'
                .negativeText(com.example.leon.project1.R.string.close)
                .titleColorRes(com.example.leon.project1.R.color.primaryColor)
                .positiveColorRes(com.example.leon.project1.R.color.accentColor)
                .negativeColorRes(com.example.leon.project1.R.color.accentColor)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        Intent i = new Intent(Settings.ACTION_SETTINGS);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        sendActivityResultCancel();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                        sendActivityResultCancel();
                    }
                })
                .build()
                .show();
    }

    private void sendActivityResultOk() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        overridePendingTransition(0,0);
        finish();
    }

    private void sendActivityResultCancel() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    private void setFailedAnimationEnd(){
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                fingerprintAnim.setImageResource(com.example.leon.project1.R.drawable.highlight_dot);
                resLabel.setText(getString(com.example.leon.project1.R.string.spass_init));
            }
        }, 1750);
    }

}
