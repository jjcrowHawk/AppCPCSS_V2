package espol.example.personal.comunitarias.Pedidos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import espol.example.personal.comunitarias.BaseDeDatos.ciudad.Ciudad;
import espol.example.personal.comunitarias.BaseDeDatos.estadocivil.Estadocivil;
import espol.example.personal.comunitarias.BaseDeDatos.etnia.Etnia;
import espol.example.personal.comunitarias.BaseDeDatos.niveleducacion.Niveleducacion;
import espol.example.personal.comunitarias.BaseDeDatos.provincia.Provincia;
import com.example.personal.comunitarias.R;

import java.util.ArrayList;

/**
 * Created by Sianna-chan on 21/05/2017.
 */

public class IntroPedidos extends AppCompatActivity {

    private int tiempo = 10;
    int pStatus = 0;
    private Handler handler = new Handler();

    private ProgressDialog mProgressDialog;

    //Listas
    ArrayList<String> lista_estadocivil, lista_niveledu, lista_nacionalidad,
            lista_ocup, lista_prov, lista_inst,lista_etnia,lista_ciudades_provincias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new IntroPedidos.Progress_cargando().execute();

    }

    public class Progress_cargando extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            lista_estadocivil       = new Estadocivil().getListaEstadoCivilNombres();
            lista_niveledu          = new Niveleducacion().getListaNivelEducacionNombres();
            lista_etnia             = new Etnia().getListaNombresEtnia();
            lista_prov              = new Provincia().getListaNombreProvincia();
            lista_ciudades_provincias=new ArrayList<String>();
            ArrayList<Ciudad> ciudades= new Ciudad().getListaCiudadesWS();
            ArrayList<Provincia> provincias= new Provincia().getListaProvinciasWS();
            for(Ciudad c: ciudades){
                for(int i=0;i<provincias.size();i++)
                    if(c.getProvinciaid() == provincias.get(i).getIdprovincia()){
                        String ciudad_provincia = c.getNombre() + ", " + provincias.get(i).getNombre();
                        lista_ciudades_provincias.add(ciudad_provincia);
                        System.out.println("ciudad_provincia: "+ciudad_provincia);
                    }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            TabsPedido t = new TabsPedido();
            t.setLista_estadocivil(lista_estadocivil);
            t.setLista_niveledu(lista_niveledu);
            t.setLista_prov(lista_prov);
            t.setLista_etnia(lista_etnia);
            t.setLista_ciudades_provincias(lista_ciudades_provincias);
            Intent i=new Intent(getBaseContext(), t.getClass());
            startActivity(i);
            finish();
        }
    }

}
