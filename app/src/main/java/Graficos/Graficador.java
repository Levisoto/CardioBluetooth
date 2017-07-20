package Graficos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.Plot;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by levi on 29/09/14.
 */
public class Graficador {
    public Number data;
    SeriesDatos sine1Series = new SeriesDatos(data, "Sine 1");

    private class Miactualizador implements Observer{
        Plot Grafico;

        public Miactualizador(Plot plot) {
            this.Grafico = plot;
        }
        @Override
        public void update(Observable observable, Object data) {
            Grafico.redraw();
            myLog("dibujando");
        }
    }
    //private SimpleXYSeries SincronizaSeries = null;
    //private SimpleXYSeries DrawSeries = null;
    //private LayoutInflater layoutInflater;
    //ArrayList<Double> series1Numbers = new ArrayList<Double>();
    private Miactualizador DibActulizado;


    public void Enlazador (double incX,double incY,double LimY,XYPlot Draw){
        DibActulizado = new Miactualizador(Draw);
        Draw.setDomainStep(XYStepMode.INCREMENT_BY_VAL,incX);
        Draw.setRangeStep(XYStepMode.INCREMENT_BY_VAL, incY);
        Draw.setRangeBoundaries(-LimY,LimY, BoundaryMode.FIXED);


    }

    public void grafico(XYPlot Draw){
        Draw.addSeries(sine1Series,new LineAndPointFormatter(Color.rgb(0, 0, 0), null, null, null));
        sine1Series.addObserver(DibActulizado);

    }

    private void myLog(String msg) {
        Log.d("ScanningFragment", msg);
    }


}
