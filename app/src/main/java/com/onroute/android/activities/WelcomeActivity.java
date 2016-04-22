package com.onroute.android.activities;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.onroute.android.R;
import com.onroute.android.activities.base.InjectableActivity;
import com.onroute.android.activities.dashboard.DashboardActivity_;
import com.onroute.android.services.DatabaseService;
import com.onroute.android.util.EaseInOutQuintInterpolator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends InjectableActivity {
    private final static String TAG = WelcomeActivity.class.getSimpleName();

    @ViewById FrameLayout contentFrame;
    @ViewById LinearLayout promptFrame;
    @ViewById VideoView videoView;
    @ViewById LinearLayout advancedFrame;
    @ViewById TextView welcomeText;
    @ViewById TextView instructionText;
    //    @ViewById TextView smsText;
//    @ViewById View loginAdvanced;
    @ViewById View loginPersonas;
    @ViewById View personas;
    @ViewById ImageView welcomeIcon;
    @ViewById TextView phoneNumberField;


    private BluetoothAdapter bluetoothAdapter;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private boolean isPhoneNumberCleared = false;

    private boolean hasAnimated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        super.onCreate(savedInstanceState);
//        initializeBluetooth();
    }


    @AfterViews
    void afterViews() {
        videoView.setVideoURI(Uri.parse("/sdcard/raymond-s1e1.avi"));
        videoView.setOnPreparedListener(PreparedListener);

        String welcomeHtmlText = getResources().getString(R.string.login_instruction_landing);
        welcomeText.setText(Html.fromHtml(welcomeHtmlText));

//        String instHtmlText = "<font color=#FFFFFF>Connect to the</font> <font color=#FF9800>OnRoute</font> " +
//                "<font color=#FFFFFF>bluetooth from your phone to start</font>";
        instructionText.setText(Html.fromHtml(welcomeHtmlText));
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Start the database service!
        if (!DatabaseService.isRunning(this)) startService(new Intent(this, DatabaseService.class));
    }


    @Click
    void contentFrameClicked() {
        Log.d(TAG, "show animation");
        if (hasAnimated) return;
        hasAnimated = true;

        animateBarFullscreen();
    }


    private void initializeBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d(TAG, "hello");

        // If the bluetoothAdapter is null, then Bluetooth is not supported
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            // TODO: Hide the bluetooth CTA
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        // Setting the device discoverable for '0' seconds makes it always discoverable
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(discoverableIntent);


        // Register the BroadcastReceiver

        /*IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
        registerReceiver(mReceiver, filter1);*/

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(bluetoothReceiver, filter);
    }


    void animateBarFullscreen() {
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
                promptFrame.getLayoutParams();

        final int oldMargin = params.bottomMargin;
        final int oldHeight = promptFrame.getMeasuredHeight();
        final int parentHeight = contentFrame.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                params.bottomMargin = (int) (oldMargin * (1f - interpolatedTime));
                params.height = (int) (oldHeight + (parentHeight - oldHeight) * interpolatedTime);
                promptFrame.setLayoutParams(params);
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }


            @Override
            public void onAnimationEnd(Animation animation) {
                advancedFrame.setVisibility(View.VISIBLE);
                promptFrame.setVisibility(View.GONE);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(250);
//                loginAdvanced.startAnimation(fadeIn);
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(500);
        animation.setInterpolator(new EaseInOutQuintInterpolator(3));
        promptFrame.startAnimation(animation);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);
        welcomeText.startAnimation(fadeOut);
        welcomeIcon.startAnimation(fadeOut);

    }


    @Click(R.id.sms_text)
    protected void onPhoneVerified() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(250);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }


            @Override
            public void onAnimationEnd(Animation animation) {
                loginPersonas.setVisibility(View.VISIBLE);
//                loginAdvanced.setVisibility(View.GONE);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(250);
                loginPersonas.startAnimation(fadeIn);
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        loginAdvanced.startAnimation(fadeOut);
    }


    @Click({
            R.id.login_personas,
            R.id.persona_airport,
            R.id.persona_bar,
            R.id.persona_camera,
            R.id.persona_office,
            R.id.persona_other,
            R.id.persona_rest
    })
    protected void onPersonasCollected() {
        // TODO: Register client here and goto next screen
        Intent intent = new Intent(this, DashboardActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    /**
     * Click handler for when the phone number buttons are pressed
     *
     * @param view The view that got clicked on.
     */
    @Click({
            R.id.auth_phone_btn_0,
            R.id.auth_phone_btn_1,
            R.id.auth_phone_btn_2,
            R.id.auth_phone_btn_3,
            R.id.auth_phone_btn_4,
            R.id.auth_phone_btn_5,
            R.id.auth_phone_btn_6,
            R.id.auth_phone_btn_7,
            R.id.auth_phone_btn_8,
            R.id.auth_phone_btn_9
    })
    protected void onPhoneNumberButtonClick(View view) {
        if (!isPhoneNumberCleared) {
            phoneNumberField.setText("");
            isPhoneNumberCleared = true;
        }

        phoneNumberField.setTextColor(getResources().getColor(R.color.orange500));

        Button viewButton = (Button) view;
        String buttonText = viewButton.getText().toString();
        phoneNumberField.append(buttonText);
    }


    /**
     * Click handler for when the phone number section's action buttons (Enter/Delete) are pressed.
     * Basically, the function either resets the phonenumber field or checks the phone-number.
     *
     * @param view The view that got clicked on.
     */
    @Click({
            R.id.auth_phone_btn_delete,
            R.id.auth_phone_btn_enter,
    })
    protected void onPhoneActionButtonClick(View view) {
        phoneNumberField.setTextColor(getResources().getColor(R.color.grey300));

        switch (view.getId()) {
            case R.id.auth_phone_btn_delete:
                // Reset the phone number field
                phoneNumberField.setText(R.string.auth_login_enter_phone_number);
                isPhoneNumberCleared = false;
                break;

            case R.id.auth_phone_btn_enter:
                // Register client here and goto next screen
                Intent intent = new Intent(this, DashboardActivity_.class);
                startActivity(intent);
        }
    }


    @UiThread
    protected void onUserConnected(String macAddress) {
        Log.d(TAG, macAddress);

        passengerResource.checkin(macAddress);

        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }


            @Override
            public void onAnimationEnd(Animation animation) {
                advancedFrame.setVisibility(View.VISIBLE);
                promptFrame.setVisibility(View.GONE);

//                loginAdvanced.setVisibility(View.GONE);
                loginPersonas.setVisibility(View.VISIBLE);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(250);
                loginPersonas.startAnimation(fadeIn);
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        promptFrame.startAnimation(animation);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);
        welcomeText.startAnimation(fadeOut);
        welcomeIcon.startAnimation(fadeOut);
    }


    /**
     * A custom MediaPlayer listener to play the video in the background in an infinite loop
     * without any volume.
     */
    private MediaPlayer.OnPreparedListener PreparedListener = new MediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();
                }

                mediaPlayer.setVolume(0f, 0f);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    // Create a BroadcastReceiver
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
                /*
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                */
                /**
                 * This code can retrieve Name and MAC without pairing but gives client an error
                 */
                /*
                try {
                    device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
                    device.getClass().getMethod("cancelPairingUserInput").invoke(device);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                */
            }

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                onUserConnected(device.getAddress());
                finish();
            }
        }
    };
}
