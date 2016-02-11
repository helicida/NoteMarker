package helicida.firebasetest.Maps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import helicida.firebasetest.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OSMap.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OSMap#newInstance} factory method to
 * create an instance of this fragment.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class OSMap extends Fragment {

    private MapView map;
    private IMapController mapController;


    public OSMap() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_osmap, container, false);

        // Declaramos el mapa
        map = (MapView) view.findViewById(R.id.map);

        // Ajustamos el mapa con los controles y el zoom deseado
        ajustarMapa();
        map.invalidate();

        return view;
    }

    private void ajustarMapa() {
        // Ajustamos los valores
        map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setTilesScaledToDpi(true);

        // Activamos el "pinzado" y el multitocuh
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        /* Movemos el mapa a la posición que queremos IESPoblenou
        GeoPoint gPt = new GeoPoint(41.39825598, 2.20325277);
        mapController.setCenter(gPt);*/

        setZoom(9);
    }

    private void setZoom(int zoom) {
        mapController = map.getController();
        mapController.setZoom(zoom);
    }
}