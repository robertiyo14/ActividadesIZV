package com.izv.actividadesizv;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoLista extends Fragment {

    private ListView lvActividades;
    private static ArrayList<ActividadRest> alActividades;
    private static Adaptador ad;
    View v;
    public static final String URLBASE = "http://ieszv.x10.bz/restful/api/";
    private final int DETALLE = 0;

    public FragmentoLista() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case DETALLE:
                    ActividadRest a =(ActividadRest) data.getSerializableExtra("actividad");
                    FragmentoDetalle.mostrarActividad(a);
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragmento_lista, container, false);
        lvActividades = (ListView)v.findViewById(R.id.listView);
        alActividades = new ArrayList();
        ad = new Adaptador(v.getContext(),R.layout.detalle,alActividades);
        lvActividades.setAdapter(ad);
        registerForContextMenu(lvActividades);
        lvActividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActividadRest a = alActividades.get(position);
                Toast.makeText(v.getContext(),"hola "+a.getId(),Toast.LENGTH_LONG).show();
                if(Principal.esTablet){
                    FragmentoDetalle.mostrarActividad(a);
                }else{
                    Intent i = new Intent(v.getContext(),DetalleActividad.class);
                    i.putExtra("actividad",a);
                    startActivityForResult(i,DETALLE);
                }
            }
        });
        cargarActividades();
        return v;
    }

    public static void addActividad(ActividadRest a){
        alActividades.add(a);
        ad.notifyDataSetChanged();
    }

    public static void notificar(){
        alActividades = null;
        cargarActividades();
    }

    private static void ordenar(){
        Collections.sort(alActividades);
        ad.notifyDataSetChanged();
    }

    private static class GetRestFul extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... s) {
            String[] r = new String[s.length];
            int i = 0;
            for(String a: s){
                r[i] = ClientRestFul.get(URLBASE+a);
                i++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String... s) {
            super.onPostExecute(s);
            JSONTokener token = new JSONTokener(s[0]);
            try {
                //JSONObject raiz = new JSONObject(tokener);
                JSONArray array = new JSONArray(token);
                for(int i=0;  i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    ActividadRest a = new ActividadRest(object);
                    Log.v("Fragmento ",object.toString());
                    alActividades.add(a);
                }
            }catch(Exception ex){}
//            ordenar();
        }
    }

    private static void cargarActividades(){
        String[] peticiones = new String[1];
        peticiones[0] = "actividad/Rober";
        GetRestFul get = new GetRestFul();
        get.execute(peticiones);
    }

}
