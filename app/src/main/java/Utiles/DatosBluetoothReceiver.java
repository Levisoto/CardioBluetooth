package Utiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;

import java.util.Observable;
import java.util.Observer;

import Graficos.Graficador;
import Graficos.MyPlot;
import Graficos.SeriesDatos;

/**
 * Created by levi on 26/11/14.
 */
public class DatosBluetoothReceiver extends BroadcastReceiver {

    private final TextView textView;
    private final MyPlot plot;


    public DatosBluetoothReceiver(TextView textView, MyPlot plot) {
        this.textView = textView;
        this.plot = plot;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Float text= (Float) intent.getSerializableExtra("datos");
        textView.setText(String.valueOf(text));
        plot.setPlot(text);

    }
}
