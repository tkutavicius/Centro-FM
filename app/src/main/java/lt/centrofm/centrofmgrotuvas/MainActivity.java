package lt.centrofm.centrofmgrotuvas;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((AppCompatActivity) MainActivity.this).getSupportActionBar().setTitle("");

        Pagrindinis fragment = new Pagrindinis();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

         if (id == R.id.nav_kontaktai) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://centrofm.lt/kontaktai/"));
            startActivity(intent);

        } else if (id == R.id.nav_uzsakyk) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://centrofm.lt/uzklausos/"));
            startActivity(intent);

        } else if (id == R.id.nav_programa) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://kibiras.centrofm.lt/tinklelis"));
            startActivity(intent);

         } else if (id == R.id.nav_paremk) {
             Intent intent = new Intent(Intent.ACTION_VIEW);
             intent.setData(Uri.parse("http://centrofm.lt/paremk.php"));
             startActivity(intent);

        } else if (id == R.id.nav_balsuok) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.radijas.fm/lt/radijas/lietuvos/centro-fm/"));
            startActivity(intent);

        } else if (id == R.id.nav_tinklapis) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.centrofm.lt"));
            startActivity(intent);

        } else if (id == R.id.nav_facebook) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/758030890970533"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/centrofm.lt/")));
            }
        } else if (id == R.id.nav_google) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.apps.plus");
            intent.setData(Uri.parse("https://plus.google.com/+CentrofmLt/"));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://plus.google.com/+CentrofmLt/")));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
