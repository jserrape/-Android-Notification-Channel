package es.gidm.notificationstatusbar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationStatusBarActivity extends AppCompatActivity {
  private static final int ID_NOTIFICACION = 2;

  private int contador = 0;

  private final CharSequence ticker =
      "Un mensaje de notificación largo para que se divida en varias líneas";
  private final CharSequence titulo = "Notificación";
  private final CharSequence contenido = "Has recibido una notificación";

  private Intent notificacionIntent;
  private PendingIntent contenidoIntent;

  private Uri sonidoURI = Uri
      .parse("android.resource://es.gidm.notificationstatusbar/"
          + R.raw.ding);
  private long[] patronVibracion = {0, 200, 200, 300};

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    notificacionIntent = new Intent(getApplicationContext(),
        NotificationSubActivity.class);
    contenidoIntent = PendingIntent.getActivity(getApplicationContext(), 0,
        notificacionIntent, 0);

    final Button boton = (Button) findViewById(R.id.boton);
    boton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Notification.Builder notificationBuilder =
            new Notification.Builder(getApplicationContext())
                .setTicker(ticker)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true)
                .setContentTitle(titulo)
                .setContentText(contenido + " (" + ++contador + ")")
                .setContentIntent(contenidoIntent);
        //.setSound(sonidoURI)
        //.setVibrate(patronVibracion);

        NotificationManager notificationManager = (NotificationManager)
            getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICACION, notificationBuilder.build());
      }
    });
  }
}