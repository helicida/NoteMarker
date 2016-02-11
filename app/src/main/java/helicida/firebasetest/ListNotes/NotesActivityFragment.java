package helicida.firebasetest.ListNotes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import helicida.firebasetest.Mensaje;
import helicida.firebasetest.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NotesActivityFragment extends Fragment implements LocationListener {

    Firebase ref;
    private ProgressDialog dialog;
    Location localizacion = null;

    public NotesActivityFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        configFirebase();   // Hacemos el config firebase

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentoLista = inflater.inflate(R.layout.fragment_notes, container, false); //Definimos el fragment

        dialog = ProgressDialog.show(getContext(), "", "Localizando tu posición...");   // Dialog que mostrará localizando
        ref = new Firebase("https://helicidatest.firebaseio.com/");         // Ruta firebase

        // Boton
        Button subirButton = (Button) fragmentoLista.findViewById(R.id.subirButton);

        // Localizacion
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);

        subirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Declaramos los EditText
                EditText tituloText = (EditText) fragmentoLista.findViewById(R.id.tituloText);
                EditText descripcionText = (EditText) fragmentoLista.findViewById(R.id.descripcionText);

                if (tituloText.getText() != null && descripcionText.getText() != null) {
                    // Usando push automaitcamente genera la clave de referencia
                    Firebase mensaje = ref.child("mensaje");
                    Firebase mensajeRef = mensaje.push();
                    mensajeRef.setValue(new Mensaje(tituloText.getText().toString(), descripcionText.getText().toString(), localizacion.getLatitude(), localizacion.getLongitude()));
                    getActivity().finish();
                }
            }
        });

        return fragmentoLista;
    }

    public void configFirebase(){
        Firebase.setAndroidContext(getContext());   // Configuramos firebase con el context adecuado y activamos la caché
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
