package pedrotti.gonzalo.proyecto.Bienvenido;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.navigation.NavigationView;

import pedrotti.gonzalo.proyecto.Campo.TodosLosCampos;
import pedrotti.gonzalo.proyecto.Fragments.MainFragments.ActividadesFragment;
import pedrotti.gonzalo.proyecto.Fragments.MainFragments.CampoFragment;
import pedrotti.gonzalo.proyecto.Fragments.MainFragments.CuentaFragment;
import pedrotti.gonzalo.proyecto.Fragments.MainFragments.EstadisticaFragment;
import pedrotti.gonzalo.proyecto.Fragments.MainFragments.HomeFragment;
import pedrotti.gonzalo.proyecto.Fragments.MainFragments.MainFragment;
import pedrotti.gonzalo.proyecto.MenuReporte;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Reportes.Reporte;
import pedrotti.gonzalo.proyecto.Reportes.ReporteActividad;
import pedrotti.gonzalo.proyecto.TiposMapas;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

import static pedrotti.gonzalo.proyecto.Constantes.ERROR_DIALOG_REQUEST;
import static pedrotti.gonzalo.proyecto.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static pedrotti.gonzalo.proyecto.Constantes.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Bienvenido extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

private Usuario user;
private boolean mLocationPermissionGranted = false;

private Button btnVerCampos;
private Button btnProyectosActuales;
private Button btnMiCuenta;
private Button btnServicios;
private Button btnVerInfo;
private Button btnEstadísticas;

DrawerLayout drawerLayout;
ActionBarDrawerToggle actionBarDrawerToggle;
Toolbar toolbar;
NavigationView navigationView;

//variables para cargar el fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private static final String TAG = "Bienvenido";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
//        this.setTitle(R.string.micuenta);

        toolbar = findViewById(R.id.navigation_drawer_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.navigation_drawer);
        navigationView = findViewById(R.id.navigation_drawer_view);

        //Establecer evento onClick al navigationView

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //Cargar fragment Principal
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        //Definimos como Fragment de Inicio a MainFragment (Puede ser cualquiera)
//        fragmentTransaction.add(R.id.content_main, new MainFragment());
        fragmentTransaction.add(R.id.content_main, new HomeFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId() == R.id.ic_home){
            showSelectedFragment(new HomeFragment());
        }

        if(menuItem.getItemId() == R.id.ic_campo){
            showSelectedFragment(new CampoFragment());
        }

        if(menuItem.getItemId() == R.id.ic_estadistica){
            showSelectedFragment(new EstadisticaFragment());

        }

        if(menuItem.getItemId() == R.id.ic_cuenta){
            showSelectedFragment(new CuentaFragment());
        }

        if(menuItem.getItemId() == R.id.ic_actividades){
            showSelectedFragment(new ActividadesFragment());
        }

        return true;
    }

    public void showSelectedFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragment)
////                .addToBackStack(null)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
    }

    //
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bienvenido);
//
//        //Titulo de la Activity
//        this.setTitle(R.string.micuenta);
//
//
//        //Declaración de Botones
//        btnVerCampos = (Button)findViewById(R.id.btnVerCampos);
//        btnProyectosActuales =(Button)findViewById(R.id.btnProyectosActuales);
//        btnMiCuenta =(Button)findViewById(R.id.btnMiCuenta);
//        btnServicios = (Button)findViewById(R.id.btnServicios);
//        btnVerInfo = (Button)findViewById(R.id.btnInformacionApp);
//        btnEstadísticas = (Button)findViewById(R.id.btnEstadisticas);
//
//
//        Bundle bundle = getIntent().getExtras();
//        user = bundle.getParcelable("DATOS_USER");
//
//        //Metodo para abrir el mapa con los campos registrados(Clase Mapa)
//        btnVerCampos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Se reenvian a la pantalla de TodosLosCampos
//                Intent verCampos = new Intent(getApplicationContext(), TodosLosCampos.class);
//                //Se comenta para el registro de un campo
//                verCampos.putExtra("DATOS_USER",user);
//                verCampos.putExtra("usuario_id",user.getUsu_id());
//                startActivity(verCampos);
//            }
//        });
//
//        btnProyectosActuales.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(Bienvenido.this, "Se muestran Proyectos Actuales del Usuario", Toast.LENGTH_SHORT).show();
//                Intent proyectos = new Intent(getApplicationContext(), ProyectosUsuario.class);
//                proyectos.putExtra("DATOS_USER",user);
//                startActivity(proyectos);
//            }
//        });
//
//        btnVerInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alerta = new AlertDialog.Builder(Bienvenido.this);
//                alerta.setMessage("La App Número 1 para obtener Recomendaciones de Momento de Aplicación \n" + " \n" + "Obtenga Información Climática Actual y Extendida de sus Lotes" ).setPositiveButton("Aceptar", null).setTitle("Acerca de MiCampo Mobile").setIcon(R.drawable.logo).create().show();
//            }
//        });
//
//        btnMiCuenta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent cuenta = new Intent(getApplicationContext(), Cuenta.class);
////                Intent cuenta = new Intent(getApplicationContext(), Reporte.class);
////                Intent cuenta = new Intent(getApplicationContext(), ReporteActividad.class);
//                cuenta.putExtra("DATOS_USER",user);
//                startActivity(cuenta);
//            }
//        });
//
//
//        btnServicios.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alerta = new AlertDialog.Builder(Bienvenido.this);
//                alerta.setMessage("Compra de Granos e Insumos \n " + " \n" + "Contratistas de Servicios ").setNegativeButton("Entendido", null).setTitle("MiCampo Mercado").setIcon(R.drawable.logo).create().show();
//            }
//        });
//
//        btnEstadísticas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                AlertDialog.Builder estadisticas = new AlertDialog.Builder(Bienvenido.this);
////                estadisticas.setMessage("Proximamente Podrá: \n " + " \n" + "-Ver y Comparar Actividades Realizadas "+"\n" + "-Comparar Dosis Recomendadas Vs Utilizadas" + "\n" + "-Generar Estadísticas e Informes" ).setNegativeButton("Aceptar", null).setTitle("MiCampo Estadísticas").setIcon(R.drawable.logo).create().show();
//                Intent estadisticas = new Intent(Bienvenido.this,ReporteActividad.class);
////                Intent estadisticas = new Intent(Bienvenido.this, MenuReporte.class);
//                estadisticas.putExtra("DATOS_USER", user);
//                startActivity(estadisticas);
//            }
//        });
//        checkMapServices();
//    }
//
//
//
//    //Solicitud de Permisos
//
//    private boolean checkMapServices(){
//        if(isServicesOK()){
//            if(isMapsEnabled()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Esta aplicación requiere utilizar su ubicación para trabajar correctarmente. Desea Activarla?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }
//
//    public boolean isMapsEnabled(){
//        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
//
//        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//            buildAlertMessageNoGps();
//            return false;
//        }
//        return true;
//    }
//
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//
//
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//
//    public boolean isServicesOK(){
//        Log.d(TAG, "isServicesOK: checking google services version");
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Bienvenido.this);
//
//        if(available == ConnectionResult.SUCCESS){
//            //everything is fine and the user can make map requests
//            Log.d(TAG, "isServicesOK: Google Play Services is working");
//            return true;
//        }
//        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //an error occured but we can resolve it
//            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Bienvenido.this, available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }else{
//            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: called.");
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ENABLE_GPS: {
//                if(mLocationPermissionGranted){
////                    getChatrooms();
//                }
//                else{
//                    getLocationPermission();
//                }
//            }
//        }
//
//    }
//        //Fin Solicitud de Permisos
//
//
//    public void mapaTipos (View view){
//        Intent intent = new Intent(getApplicationContext(), TiposMapas.class);
//        startActivity(intent);
//    }

}
