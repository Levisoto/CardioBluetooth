package Fragments;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.example.cas.cardioapp.R;

import Graficos.Graficador;
import Graficos.MyPlot;
import Utiles.DatosBluetoothReceiver;

/**
 * Created by levi on 24/11/14.
 */
public class GraficosFragment extends Fragment {

    TextView textView;
    XYPlot plot;
    MyPlot myPlot=null;
    private DatosBluetoothReceiver receiver;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_graficos,container,false);
        InicializarComponentes(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new DatosBluetoothReceiver(textView,myPlot);
        getActivity().registerReceiver(receiver, new IntentFilter("graficos"));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }


    private void InicializarComponentes(View rootView) {
        textView = (TextView) rootView.findViewById(R.id.textView2);
        plot = (XYPlot) rootView.findViewById(R.id.Draw);
        myPlot= new MyPlot(plot);

    }

    private void myLog(String msg) {
        Log.d("ScanningFragment", msg);
    }
}
