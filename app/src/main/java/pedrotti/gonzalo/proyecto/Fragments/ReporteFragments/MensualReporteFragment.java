package pedrotti.gonzalo.proyecto.Fragments.ReporteFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pedrotti.gonzalo.proyecto.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MensualReporteFragment extends Fragment {


    public MensualReporteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mensual_reporte, container, false);
    }

}
