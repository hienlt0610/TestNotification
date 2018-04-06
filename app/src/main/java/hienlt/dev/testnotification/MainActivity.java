package hienlt.dev.testnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNotification();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(NotificationListenerExampleService.NOTIFICATION_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private void checkNotification() {
        if (!Settings.Secure.getString(getContentResolver(),
                "enabled_notification_listeners").contains(getPackageName())) {
            getApplicationContext().startActivity(new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String event = intent.getStringExtra("notification_event");
            Toast.makeText(MainActivity.this, event, Toast.LENGTH_SHORT).show();
        }
    };
}
