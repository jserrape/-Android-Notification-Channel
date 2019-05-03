package es.gidm.notificationstatusbar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NotificationStatusBarActivity extends AppCompatActivity {

    private int contador = 1;

    private final String id = "123";

    private final String titulo = "Notificación";
    private final String contenido = "Has recibido una notificación";

    private Uri sonidoURI = Uri.parse("android.resource://es.gidm.notificationstatusbar/" + R.raw.ding);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createNotificationChannel();

        final Button boton = (Button) findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Notification.Builder nb = (Notification.Builder) new Notification.Builder(getApplicationContext(), id)
                            .setContentTitle(getString(R.string.notificacion)) //Titulo
                            .setContentText(getString(R.string.n_notificacion) + " " + contador) //Contenido
                            .setSmallIcon(R.drawable.ic_warning_black_24dp) //Añado un icono
                            .setAutoCancel(true); //Eliminar al hacer click
                            //.setStyle(new Notification.InboxStyle()); //Solo aparece el título

                    NotificationManager mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mManager.notify(101, nb.build());

                    ++contador;
                }
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (Toast.makeText(getApplicationContext(), "Creación del canal", Toast.LENGTH_SHORT)).show();

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(id, titulo, importance);
            channel.setDescription(contenido);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.setLightColor(Color.BLUE);
            channel.setSound(sonidoURI, new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build());

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        } else {
            (Toast.makeText(getApplicationContext(), "Versión de Android inferior a API 26", Toast.LENGTH_SHORT)).show();
        }
    }
}