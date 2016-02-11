package helicida.firebasetest.ListNotes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

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
                text1 = (TextView) view.findViewById(R.id.text1);
                text1.setText(mensaje.getTitulo() + "   -   " + mensaje.getLatitud() + ", " + mensaje.getLongitud());

                text2 = (TextView) view.findViewById(R.id.text2);
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
