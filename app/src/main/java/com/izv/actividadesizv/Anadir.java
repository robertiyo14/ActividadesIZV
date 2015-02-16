package com.izv.actividadesizv;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class Anadir extends Activity {

    private TextView tvDe, tvHasta,tvFechaSalida,tvFechaLlegada,tvLugarSalida,tvLugarLlegada,tvHoraSalida,tvHoraLlegada;
    private EditText etDescripcion, etDepartamento,  etLugar,etLugarSalida,etLugarLlegada;
    private Spinner spProfesor, spGrupo;
    private static TextView etHoraLlegada,etHoraSalida, etFecha, etHoraDe, etHoraHasta,etFechaSalida,etFechaLlegada;
    private RadioButton rbComplementaria, rbExtraescolar;
    private Button btHoraSalida, btHoraLlegada, btFechaSalida, btFechaLlegada,btHoraDe, btHoraHasta, btFecha;
    ArrayList<String> alProfesores = new ArrayList<String>();
    ArrayList<String> alGrupos = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        boolean esTablet=b.getBoolean("esTablet");
        if(esTablet){
            showAsPopup(this);
        }
        setContentView(R.layout.activity_anadir);

        /************COMUNES************/
        tvHasta = (TextView)findViewById(R.id.tvHasta);
        etDepartamento = (EditText)findViewById(R.id.etDepartamento);
        spProfesor = (Spinner)findViewById(R.id.spProfesor);
        spGrupo = (Spinner)findViewById(R.id.spGrupo);
        etDescripcion = (EditText)findViewById(R.id.etDescripcion);
        rbComplementaria = (RadioButton)findViewById(R.id.rbComplementaria);
        rbExtraescolar = (RadioButton)findViewById(R.id.rbExtraescolar);

        /**************POBLAR ARRAY DE PROFESORES***************/

        String[] peticiones = new String[1];
        peticiones[0] = "profesor";
        GetRestFul get = new GetRestFul();
        get.execute(peticiones);

        /**************POBLAR ARRAY DE GRUPOS***************/

        alGrupos.add("Grupo1");
        alGrupos.add("Grupo2");
        ArrayAdapter<String> aaGrupos = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alGrupos);
        aaGrupos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrupo.setAdapter(aaGrupos);

        /****ACTIVIDAD COMPLEMENTARIA****/
        tvDe = (TextView)findViewById(R.id.tvDe);
        etFecha = (TextView)findViewById(R.id.etFecha);
        etLugar = (EditText)findViewById(R.id.etLugar);
        etHoraDe = (TextView)findViewById(R.id.etHoraDe);
        etHoraHasta = (TextView)findViewById(R.id.etHoraHasta);
        btHoraDe = (Button)findViewById(R.id.btHoraDe);
        btHoraHasta = (Button)findViewById(R.id.btHoraHasta);
        btFecha = (Button)findViewById(R.id.btFecha);
        /****ACTIVIDAD EXTRAESCOLAR****/
        tvFechaSalida = (TextView)findViewById(R.id.tvFechaDe);
        tvFechaLlegada = (TextView)findViewById(R.id.tvFechaHasta);
        tvHoraSalida = (TextView)findViewById(R.id.tvHoraDe);
        tvHoraLlegada = (TextView)findViewById(R.id.tvHoraHasta);
        tvLugarLlegada = (TextView)findViewById(R.id.tvLlegada);
        tvLugarSalida = (TextView)findViewById(R.id.tvSalida);
        etHoraSalida = (TextView)findViewById(R.id.etHoraSalida);
        etHoraLlegada = (TextView)findViewById(R.id.etHoraLlegada);
        etFechaSalida = (TextView)findViewById(R.id.etFechaDe);
        etFechaLlegada = (TextView)findViewById(R.id.etFechaHasta);
        etLugarSalida = (EditText)findViewById(R.id.etSalida);
        etLugarLlegada = (EditText)findViewById(R.id.etLlegada);
        btFechaLlegada = (Button)findViewById(R.id.btFechaDe);
        btFechaSalida = (Button)findViewById(R.id.btFechaHasta);
        btHoraSalida = (Button)findViewById(R.id.btHoraSalida);
        btHoraLlegada = (Button)findViewById(R.id.btHoraLlegada);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_aceptar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_accept:
                String profesor,departamento,grupo,descripcion;
                profesor = (spProfesor.getSelectedItemPosition()+1)+"";
                departamento = etDepartamento.getText().toString();
                grupo = spGrupo.getSelectedItem().toString();
                descripcion = etDescripcion.getText().toString();
                if(rbComplementaria.isChecked()){
                    String horaInicio,horaFin,lugar,fecha;
                    horaInicio = etHoraDe.getText().toString();
                    horaFin = etHoraHasta.getText().toString();
                    lugar = etLugar.getText().toString();
                    fecha = etFecha.getText().toString();
                    ActividadRest a = new ActividadRest(
                            profesor,
                            "Complementaria",
                            fecha+" "+horaInicio,
                            fecha+" "+horaFin,
                            lugar,
                            lugar,
                            descripcion,
                            "Rober");
                    ParametrosPost p = new ParametrosPost();
                    p.url = FragmentoLista.URLBASE+"actividad";
                    p.object = a.getJSON();
                    PostRestFul pr = new PostRestFul();
                    pr.execute(p);
                }else if(rbExtraescolar.isChecked()){
                    String horaSalida,horaLlegada,fechaSalida,fechaLlegada,lugarSalida,lugarLlegada;
                    horaSalida = etHoraSalida.getText().toString();
                    horaLlegada = etHoraLlegada.getText().toString();
                    fechaSalida = etFechaSalida.getText().toString();
                    fechaLlegada = etFechaLlegada.getText().toString();
                    lugarSalida = etLugarSalida.getText().toString();
                    lugarLlegada = etLugarLlegada.getText().toString();
                    ActividadRest a = new ActividadRest(
                            profesor,
                            "extraescolar",
                            fechaSalida+" "+horaSalida,
                            fechaLlegada+" "+horaLlegada,
                            lugarSalida,
                            lugarLlegada,
                            descripcion,
                            "Rober");
                    ParametrosPost p = new ParametrosPost();
                    p.url = FragmentoLista.URLBASE+"actividad";
                    p.object = a.getJSON();
                    PostRestFul pr = new PostRestFul();
                    pr.execute(p);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void complementaria(View v){
        /********Muestro complementarias*******/
        tvDe.setVisibility(View.VISIBLE);
        tvHasta.setVisibility(View.VISIBLE);
        etHoraDe.setVisibility(View.VISIBLE);
        etHoraHasta.setVisibility(View.VISIBLE);
        etLugar.setVisibility(View.VISIBLE);
        etFecha.setVisibility(View.VISIBLE);
        btHoraDe.setVisibility(View.VISIBLE);
        btHoraHasta.setVisibility(View.VISIBLE);
        btFecha.setVisibility(View.VISIBLE);
        /********Oculto extraescolares*******/
        tvLugarSalida.setVisibility(View.GONE);
        tvLugarLlegada.setVisibility(View.GONE);
        tvHoraSalida.setVisibility(View.GONE);
        tvHoraLlegada.setVisibility(View.GONE);
        tvFechaSalida.setVisibility(View.GONE);
        tvFechaLlegada.setVisibility(View.GONE);
        etHoraSalida.setVisibility(View.GONE);
        etHoraLlegada.setVisibility(View.GONE);
        etFechaSalida.setVisibility(View.GONE);
        etFechaLlegada.setVisibility(View.GONE);
        etLugarSalida.setVisibility(View.GONE);
        etLugarLlegada.setVisibility(View.GONE);
        btFechaLlegada.setVisibility(View.GONE);
        btFechaSalida.setVisibility(View.GONE);
        btHoraSalida.setVisibility(View.GONE);
        btHoraLlegada.setVisibility(View.GONE);
    }

    public void extraescolar(View v){
        /********Oculto complementarias*******/
        tvDe.setVisibility(View.GONE);
        tvHasta.setVisibility(View.GONE);
        etHoraDe.setVisibility(View.GONE);
        etHoraHasta.setVisibility(View.GONE);
        etLugar.setVisibility(View.GONE);
        etFecha.setVisibility(View.GONE);
        btHoraDe.setVisibility(View.GONE);
        btHoraHasta.setVisibility(View.GONE);
        btFecha.setVisibility(View.GONE);
        /********Muestro extraescolares*******/
        tvLugarSalida.setVisibility(View.VISIBLE);
        tvLugarLlegada.setVisibility(View.VISIBLE);
        tvHoraSalida.setVisibility(View.VISIBLE);
        tvHoraLlegada.setVisibility(View.VISIBLE);
        tvFechaSalida.setVisibility(View.VISIBLE);
        tvFechaLlegada.setVisibility(View.VISIBLE);
        etHoraSalida.setVisibility(View.VISIBLE);
        etHoraLlegada.setVisibility(View.VISIBLE);
        etFechaSalida.setVisibility(View.VISIBLE);
        etFechaLlegada.setVisibility(View.VISIBLE);
        etLugarSalida.setVisibility(View.VISIBLE);
        etLugarLlegada.setVisibility(View.VISIBLE);
        btFechaLlegada.setVisibility(View.VISIBLE);
        btFechaSalida.setVisibility(View.VISIBLE);
        btHoraSalida.setVisibility(View.VISIBLE);
        btHoraLlegada.setVisibility(View.VISIBLE);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if(getTag().toString().compareTo("fechaSalida")==0){
                etFechaSalida.setText(year+"-"+month+"-"+day);
            }else if(getTag().toString().compareTo("fechaLlegada")==0){
                etFechaLlegada.setText(year + "-" + month + "-" + day);
            }else{
                etFecha.setText(year+"-"+month+"-"+day);
            }
        }
    }//FIN CLASE FECHA

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(getTag().toString().compareTo("horaDe")==0){
                etHoraDe.setText(hourOfDay + ":" + minute);
            }else if(getTag().toString().compareTo("horaHasta")==0){
                etHoraHasta.setText(hourOfDay + ":" + minute);
            }else if(getTag().toString().compareTo("horaSalida")==0){
                etHoraSalida.setText(hourOfDay + ":" + minute);
            }else{
                etHoraLlegada.setText(hourOfDay + ":" + minute);
            }
        }
    }//FIN CLASE HORA

    public void fechaSalida(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"fechaSalida");
    }

    public void fechaLlegada(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"fechaLlegada");
    }

    public void fecha(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"fecha");
    }

    public void horaSalida(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"horaSalida");
    }

    public void horaLlegada(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"horaLlegada");
    }

    public void horaDe(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"horaDe");
    }

    public void horaHasta(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"horaHasta");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void showAsPopup(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.height = 650;
        params.width = 650;
        params.alpha = 1.0f;
        params.dimAmount = 0.5f;
        activity.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    static class ParametrosPost{
        String url;
        JSONObject object;
    }

    private class PostRestFul extends AsyncTask<ParametrosPost, Void, String> {

        @Override
        protected String doInBackground(ParametrosPost... s) {
            String r = ClientRestFul.post(s[0].url, s[0].object);
            Log.v("Añadir",r);
            return r;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                FragmentoLista.notificar();
                finish();
            }catch(Exception ex){}

        }
    }

    private class GetRestFul extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... s) {
            String[] r = new String[s.length];
            int i = 0;
            for(String a: s){
                r[i] = ClientRestFul.get(FragmentoLista.URLBASE+a);
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
                    Profesor p = new Profesor(object);
                    alProfesores.add(p.getNombre());
                }
                ArrayAdapter<String> aaProfesor = new ArrayAdapter<String>(Anadir.this,
                        android.R.layout.simple_spinner_item, alProfesores);
                aaProfesor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spProfesor.setAdapter(aaProfesor);
            }catch(Exception ex){}
        }
    }
}
