package com.izv.actividadesizv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class DetalleActividad extends Activity {

    ActividadRest a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_actividad);
        a = (ActividadRest)getIntent().getExtras().getSerializable("actividad");
        FragmentoDetalle.mostrarActividad(a);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_borrar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_borrar) {
            borrar(a.getId());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void borrar(String id){
        Toast.makeText(this,a.toString()+"-----------"+a.getId(),Toast.LENGTH_LONG).show();
        DeleteRestFul dr = new DeleteRestFul();
        dr.execute(id);
    }

    private class DeleteRestFul extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... s) {
            String r = ClientRestFul.delete(FragmentoLista.URLBASE+"actividad/"+s[0]);
            return r;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //FragmentoLista.notificar();
            }catch(Exception ex){}

        }
    }
}
