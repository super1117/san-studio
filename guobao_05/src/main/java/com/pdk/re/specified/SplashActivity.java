package com.pdk.re.specified;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.pdk.re.specified.models.FireBaseModel;

public class SplashActivity extends AppCompatActivity {

    private static final long TOTAL_MILL = 3500L;
    private long onCreateTime = 0L;
    private FireBaseModel model;
    private Handler HANDLER = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Pair<Integer, String> limit = model.getAllow(SplashActivity.this);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("flag", limit.first);
            intent.putExtra("flagStr", limit.second);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateTime = System.nanoTime();
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.fetch().addOnCompleteListener(this, task -> {
            firebaseRemoteConfig.fetchAndActivate();
            model = new FireBaseModel(//?: "https://166bet.com/?id=25124822"
                    firebaseRemoteConfig.getBoolean("openApp"),
                    firebaseRemoteConfig.getBoolean("isIpAllowed"),
                    firebaseRemoteConfig.getString("allowedSimCard"),
                    firebaseRemoteConfig.getString("link2")
            );
            if (System.nanoTime() - onCreateTime >= TOTAL_MILL) {
                HANDLER.sendEmptyMessage(0);
            } else {
                HANDLER.sendEmptyMessageDelayed(0, TOTAL_MILL - System.nanoTime() + onCreateTime);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
