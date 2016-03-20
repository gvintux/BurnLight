package me.fyret.burnlight;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private boolean isLightOn;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "me.fyret.burnlight");
        final Switch lightSwitch = (Switch)findViewById(R.id.light_switch);
        isLightOn = false;
        lightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLightOn = !isLightOn;
                lightSwitch.setChecked(isLightOn);
                if(isLightOn)
                {
                    wakeLock.acquire();
                }
                else
                {
                    wakeLock.release();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wakeLock.isHeld()) wakeLock.release();
    }
}
