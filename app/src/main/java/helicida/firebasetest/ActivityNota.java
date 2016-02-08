package helicida.firebasetest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ActivityNota extends AppCompatActivity implements LocationListener {

    Firebase ref;
    Location localizacion = null;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_nota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Firebase.setAndroidContext(this);

        dialog = ProgressDialog.show(this, "", "Localizando...");

        ref = new Firebase("https://helicidatest.firebaseio.com/");

        Button subirButton = (Button) this.findViewById(R.id.subirButton);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, this);

        final Activity activity = this;

        subirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tituloText = (EditText) activity.findViewById(R.id.tituloText);
                EditText descripcionText = (EditText) activity.findViewById(R.id.descripcionText);

                if (tituloText.getText() != null && descripcionText.getText() != null) {

                    // Usando push automaitcamente genera la clave de referencia

                    Firebase mensaje = ref.child("mensaje");
                    Firebase mensajeRef = mensaje.push();
                    mensajeRef.setValue(new Mensaje(tituloText.getText().toString(), descripcionText.getText().toString(), localizacion.getLatitude(), localizacion.getLongitude()));
                    activity.finish();
                }
            }
        });

    }

    // Metodos locationListener
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onLocationChanged(Location location) {
        localizacion = location;
        dialog.dismiss();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderDisabled(String provider) {

    }

}
