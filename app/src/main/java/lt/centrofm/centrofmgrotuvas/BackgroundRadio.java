package lt.centrofm.centrofmgrotuvas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class BackgroundRadio extends Service {
    private static final String TAG = null;
    static MediaPlayer grotuvas;
    Uri source;

    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        source = Uri.parse("http://www.centrofm.lt:8000/centrofm");
        Log.d("CFM", "URI ASSIGNED");
        grotuvas = new MediaPlayer();
        grotuvas.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Log.d("CFM", "PLAYER CREATED AND TYPE SET");
        try {
            grotuvas.setDataSource(this, source);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "Wrong application permissions!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(this, "Illegal state!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("CFM", "PLAYER DATA SOURCE SET");

        grotuvas.prepareAsync();
        grotuvas.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer player) {
                player.start();
                Log.d("CFM", "PLAYER PREPARED ASYNC");
                Intent intent = new Intent();
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                Notification noti = new Notification.Builder(getApplicationContext())
                        .setTicker("CentroFM - Kėdainių radijo stotis")
                        .setContentTitle("Jūsų telefone groja CentroFM!")
                        .setContentText("Klausyitės ir 106.1 MHz dažniu!")
                        .setSmallIcon(R.drawable.icon)
                        .setContentIntent(pIntent).getNotification();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, noti);
            }
        });
    }
    @Override
    public void onDestroy() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        grotuvas.stop();
        super.onDestroy();
    }
}