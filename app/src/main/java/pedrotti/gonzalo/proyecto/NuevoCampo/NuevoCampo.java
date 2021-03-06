package pedrotti.gonzalo.proyecto.NuevoCampo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import pedrotti.gonzalo.proyecto.Campo.CamposAdapter;
import pedrotti.gonzalo.proyecto.Modelo.Campo;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;


public class NuevoCampo extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener{

    private GoogleMap mMap;
    private Marker marker;

    private double lat1=0;
    private double long1=0;
    private Usuario user;
    private int toque = 0;
    private int param1=0;
    TextView tvLat1, tvLong1;
    EditText etNombre;
    private int j =0;

    private CamposAdapter adapter;


    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLong1() {
        return long1;
    }

    public void setLong1(double long1) {
        this.long1 = long1;
    }

    public void setToque(int valor){
        toque = valor;
    }

    public int getToque(){
        return toque;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_campo);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       this.setTitle(R.string.crearCampo);

//
//       Bundle bundle =getIntent().getExtras();
//
//       user = bundle.getParcelable("DATOS_USER");

        user = new Usuario();
//
        user.setUsu_id(1);

//        SharedPreferences preferences = getSharedPreferences("datos",  Context.MODE_PRIVATE);
//
//        int id = preferences.getInt("usuario_id", 0);
//
//        user.setUsu_id(1);

       Button btnAyuda = (Button)findViewById(R.id.btnAyuda);
       Button btnRegistrarCampo = (Button)findViewById(R.id.btnProyectosActuales);

       etNombre = (EditText)findViewById(R.id.txtNombreLote);

        //Funciones del Boton Registrar al hacer Click
        btnRegistrarCampo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                guardarCampo();
            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ayuda = new Intent(getApplicationContext(), AyudaRegistrarCampo.class);
                startActivity(ayuda);
            }
        });
    }

    public void guardarCampo(){

        /*
         * Este if verifica que el param1 haya tomado el valor 1
         * el valor 1 lo toma solo si el usuario seleccionó un punto en el mapa
         * si no seleccionó un punto, la variable lat1 y long1 se definen como cero
         * Entonces: Si es cero, entra al if y muestra el mensaje de completar los campos*/
        if (param1==1){
            j=1;
        }else{
            lat1=0;
            long1=0;
            j=0;
        }

        //El trim elimina espacios al principio y al fin
        String nombre = etNombre.getText().toString().trim();

        //Controles
        if (nombre.isEmpty() || lat1==0 || long1==0 ) {
            Toast.makeText(getApplicationContext(), "Asegúrese de Que Seleccionó un punto y Completó el nombre", Toast.LENGTH_LONG).show();
        }

        //inicio else
        else{
            Response.Listener<String> respuesta = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Campo campo = new Campo();
                    try{
                        JSONObject jsonRespuesta = new JSONObject(response);
                        boolean ok = jsonRespuesta.getBoolean("success");
                        if(ok==true){
                            AlertDialog.Builder alerta = new AlertDialog.Builder(NuevoCampo.this);
                            alerta.setMessage("Campo Registrado con Éxito! Vuelva para ver su nuevo Campo o Continúe Registrando nuevos Campos").setPositiveButton("Aceptar",null).create().show();
                            etNombre.setText("");
                            marker.remove();
                            setToque(0);

                        }else{

                            AlertDialog.Builder alerta = new AlertDialog.Builder(NuevoCampo.this);
                            alerta.setMessage("Fallo en el Registro").setNegativeButton("Reintentar",null).create().show();
                        }
                    }
                    catch(JSONException e){
                        e.getMessage();
                        Toast.makeText(getApplicationContext(), "Error al intentar guardar", Toast.LENGTH_LONG).show();
                    }
                }
            };
            NuevoCampoRequest r = new NuevoCampoRequest(user.getUsu_id(),nombre,lat1,long1, respuesta);
            RequestQueue cola = Volley.newRequestQueue(NuevoCampo.this);
            cola.add(r);
        }
        //fin del else
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    //Este metodo muestra las coordenadas de un punto luego de haber tocado el mapa en dicho punto
    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(NuevoCampo.this,
                "Coordenadas Aquí: \n" + latLng.latitude + " : " + latLng.longitude,
                Toast.LENGTH_SHORT).show();
    }

    //Este metodo agrega una marca en el mapa luego de mantener presionado el mismo
    @Override
    public void onMapLongClick(LatLng latLng) {
        if(getToque()<1){
                marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Vertice " + (getToque() + 1)));
                setToque(1);
                if(getToque()==1){
                    setLat1(marker.getPosition().latitude);
                    setLong1(marker.getPosition().longitude);
                    Toast.makeText(this, "Punto 1: " + getLat1()+"," + getLong1(), Toast.LENGTH_SHORT).show();

                    //Pasamos de Double a String
//                    String stringLat1 =String.valueOf(marker.getPosition().latitude);
//                    String stringLong1 = String.valueOf(marker.getPosition().longitude);

                    lat1= marker.getPosition().latitude;
                    long1=marker.getPosition().longitude;
                    param1=1;

                    //Colocamos texto en el textView en formato String
//                    tvLat1.setText(stringLat1);
//                    tvLong1.setText(stringLong1);
                }
        }else{
            Toast.makeText(this, "Ya seleccionó una Ubicación para su Campo.", Toast.LENGTH_SHORT).show();
        }
    }


}

