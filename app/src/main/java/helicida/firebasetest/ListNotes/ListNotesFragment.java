package helicida.firebasetest.ListNotes;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ListNotesFragment extends Fragment {

    Firebase ref;
    Firebase mensajes;
    TextView text1, text2;

    public ListNotesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentoLista = inflater.inflate(R.layout.fragment_list_notes, container, false); //Definimos el fragment

        configFirebase();   // Hacemos el config firebase

        ref = new Firebase("https://helicidatest.firebaseio.com/"); // Ruta firebase
        mensajes = ref.child("mensaje"); // mensajes

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Declaramos el listView
        ListView listUsers = (ListView) fragmentoLista.findViewById(R.id.listView1);

        FirebaseListAdapter mAdapter = new FirebaseListAdapter<Mensaje>(getActivity(), Mensaje.class, R.layout.adapter_firebase_users_layout, mensajes) {
            @Override
            protected void populateView(View view, Mensaje mensaje, int position) {
                super.populateView(view, mensaje, position);

                // Declaramos el image view y los textViews
                ImageView imagen = (ImageView) view.findViewById(R.id.messageIcon);
                text1 = (TextView) view.findViewById(R.id.text1);
                text2 = (TextView) view.findViewById(R.id.text2);

                // Si tiene imagen se la acoplamos
                if (mensaje.getRutaImagen() != null) {
                    File archivoImagen = new File (mensaje.getRutaImagen());

                    Picasso.with(getContext()).load(archivoImagen).
                            transform(new RoundedTransformation(100, 0)).
                            centerCrop().
                            resize(100, 100).
                            into(imagen);
                }
                else{
                    // Si no le acoplamos la nuestra predefinida
                    Drawable iconoMensaje = ContextCompat.getDrawable(getContext(), R.drawable.messaje_icon);
                    imagen.setImageDrawable(iconoMensaje);
                }

                // Le damos los valores
                text1.setText(mensaje.getTitulo() + "   -   " + mensaje.getLatitud() + ", " + mensaje.getLongitud());
                text2.setText(String.valueOf(mensaje.getDescripcion()));
            }
        };

        listUsers.setAdapter(mAdapter);

        return fragmentoLista;
    }

    public void configFirebase(){
        // Configuración
        Firebase.setAndroidContext(getContext());
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
