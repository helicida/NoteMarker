package helicida.firebasetest.ListNotes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;

import helicida.firebasetest.Mensaje;
import helicida.firebasetest.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NotesActivityFragment extends Fragment implements LocationListener {

    Firebase ref;                   // Base firebase
    private ProgressDialog dialog;  // Pantalla de localización

    Location localizacion = null;       // Localización
    private ImageView imagenMensaje;    // Foto a enviar
    boolean imagen = false;             // Booleano que determina si el mensaje tiene imagen o no

    public NotesActivityFragment() {}

    public void onStart() {

        super.onStart();

        // Cuando inicia el fragmento comprobamos si se ha sacado una foto en caso positivo, la acoplamos a nuestra imagen
        if (imagen){
            File imagePath = new File(getRutaImagen());
            Picasso.with(getContext()).load(imagePath).centerCrop().resize(240, 300).into(imagenMensaje);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        configFirebase();   // Hacemos el config firebase

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentoLista = inflater.inflate(R.layout.fragment_notes, container, false); //Definimos el fragment

        dialog = ProgressDialog.show(getContext(), "", "Localizando tu posición...");   // Dialog que mostrará localizando
        ref = new Firebase("https://helicidatest.firebaseio.com/");                     // Ruta firebase

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

                    // Creamos nuestro mensaje
                    Mensaje mensajeASubir = new Mensaje();

                    // Le damos toda la información
                    mensajeASubir.setTitulo(tituloText.getText().toString());
                    mensajeASubir.setDescripcion(descripcionText.getText().toString());
                    mensajeASubir.setLatitud(localizacion.getLatitude());
                    mensajeASubir.setLongitud(localizacion.getLongitude());

                    // Si tiene imagen le damos la de la imagen
                    if (imagen) {
                        mensajeASubir.setRutaImagen(getRutaImagen());
                    }

                    // Subimos el mensaje a Firebase y cerramos la actividad de añadir mensaje
                    mensajeRef.setValue(mensajeASubir);
                    getActivity().finish();
                }
            }
        });

        // Declaramos la imagen del mensaje
        imagenMensaje = (ImageView) fragmentoLista.findViewById(R.id.imagenMensaje);

        // Y le damos un listener, que abrirá la camara al darl encima
        imagenMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagen = true;
                Intent camara = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(camara);
            }
        });

        return fragmentoLista;
    }

    public String getRutaImagen() {
        // Con este código obtenemos la ruta de la imagen
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        return cursor.getString(column_index_data);
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
