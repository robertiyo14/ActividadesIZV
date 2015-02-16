package com.izv.actividadesizv;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoDetalle extends Fragment {

    private static TextView tv;

    public FragmentoDetalle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragmento_detalle, container, false);
        tv = (TextView)v.findViewById(R.id.tvFragmentoDetalle);
        return v;
    }

    public static void mostrarActividad(ActividadRest a){
        tv.setText(a.toString());
    }


}
