package com.izv.actividadesizv;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Principal extends Activity {
    public static boolean esTablet = false;
    private FragmentoDetalle fd;
    private FragmentoLista fl;
    private static int ANADIR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ActionBar actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valoresSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                switch (itemPosition) {
                    case 0:
                        //Toast.makeText(getBaseContext(), "Seleccionado valor 1", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        //Toast.makeText(getBaseContext(), "Seleccionado valor 2", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        fd=(FragmentoDetalle)getFragmentManager().findFragmentById(R.id.fragmentoDetalle);
        fl=(FragmentoLista)getFragmentManager().findFragmentById(R.id.fragmentoLista);
        View detalle = findViewById(R.id.fragmentoDetalle);
        esTablet = detalle != null && detalle.getVisibility() == View.VISIBLE;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Actividad a;
//        a = (Actividad) data.getSerializableExtra("actividad");
//        ga.add(a);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_new:
                //nuevoInmueble();
                Intent i = new Intent(this, Anadir.class);
                Bundle b = new Bundle();
                b.putBoolean("esTablet", esTablet);
                i.putExtras(b);
                startActivityForResult(i, ANADIR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
