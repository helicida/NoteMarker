package helicida.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

import helicida.firebasetest.ListNotes.ListNotesFragment;
import helicida.firebasetest.ListNotes.NotesActivity;

public class MainActivity extends AppCompatActivity {

    private CustomViewPager viewPager;  // Custom viewPager en el que usaremos las pestanyas
    private TabLayout pestanyas;        // TabLayout que acoplramos al view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Actividad que usaremos para referenciar dentro del FAB
        final Activity a = this;

        // Floating actionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(a, NotesActivity.class);
                startActivity(intent);
            }
        });

        // Declaramos el viewPager y el TAB
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        pestanyas = (TabLayout) findViewById(R.id.pestanyas);

        //  Acoplamos el TAB al viewPager
        setupViewPager(viewPager);
        pestanyas.setupWithViewPager(viewPager);

    }

    // Con este metodo personalizado acoplamos las pestanyas a nuestro viewPager
    private void setupViewPager(CustomViewPager viewPager) {
        // Declaramos el adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Le acoplamos tantas pestanyas como queramos
        adapter.addFragment(new ListNotesFragment(), "Notas");
        // adapter.addFragment(new MapFragment(), "Mapa");

        // Acoplamos del todo las pestanyas y las activamos
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
    }

    // Declaramos el ViewPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {

        // Dos listas con los fragmentos que tendremos en nuestras pestanyas y los titulos de cada una
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
