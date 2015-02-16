package com.izv.actividadesizv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by rober on 11/02/2015.
 */
public class Adaptador extends ArrayAdapter<String> {

    private Context contexto;
    private ArrayList<ActividadRest> lista;
    private int recurso;
    static LayoutInflater i;


    public static class ViewHolder{
        public TextView tv1;
        public int posicion;
    }

    public Adaptador(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.contexto=context;
        this.lista=objects;
        this.recurso=resource;
        this.i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tv1=(TextView)convertView.findViewById(R.id.tvFecha);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder)convertView.getTag();
        }
        vh.posicion = position;
//        if(lista.get(position) instanceof Complementaria){
//            vh.tv1.setText(((Complementaria) lista.get(position)).getFecha());
//        }else if(lista.get(position) instanceof Extraescolar){
//            vh.tv1.setText(((Extraescolar) lista.get(position)).getFechaSalida());
//        }
        vh.tv1.setText(lista.get(position).getFechai().toString());
        return convertView;

    }

    private void borrar(final int elemento){
        AlertDialog.Builder alert = new AlertDialog.Builder(contexto);
        alert.setTitle("alta");
        LayoutInflater inflater = LayoutInflater.from(contexto);

        alert.setPositiveButton("insertar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        lista.remove(elemento);
                        notifyDataSetChanged();

                    }
                });
        alert.setNegativeButton("cancelar",null);
        alert.show();
        //tostada("Elemento añadido");

    }
}