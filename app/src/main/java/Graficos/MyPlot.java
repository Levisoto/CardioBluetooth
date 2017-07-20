package Graficos;


import android.graphics.Color;

import com.androidplot.Plot;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by levi on 30/11/14.
 */
public class MyPlot {

    private final XYPlot plot;
    ArrayList<Double> datos = new ArrayList<Double>();
    Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
    double DatoX,DatoY,fin=10,incre=0.1;
    int i=0;

    public MyPlot(XYPlot plot) {
        this.plot = plot;
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 2);
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 0.1);
        plot.getGraphWidget().getBackgroundPaint().setColor(Color.rgb(255, 255, 255));
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.rgb(255, 255, 255));
        plot.setRangeBoundaries(0,5, BoundaryMode.FIXED);
        //plot.setDomainBoundaries(0, fin, BoundaryMode.AUTO);
    }


    public void setPlot(final Float number) {
        //new Miactualizador(plot);
        if (i==0) {
            for (int j=0;j<=fin/incre;j++){
                DatoX = DatoX + incre;
                datos.add(DatoX);
                DatoY = 0;
                datos.add(DatoY);}
            XYSeries series1 = new SimpleXYSeries(
                    datos,          // SimpleXYSeries takes a List so turn our array into a List
                    SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                    "Series1");
            plot.addSeries(series1, new LineAndPointFormatter(Color.rgb(0, 0, 0), null, null, null));
            i++;
        }else{

            Runnable graf = new Runnable() {
                @Override
                public void run() {
                    DatoX = DatoX + incre;
                    datos.remove(0);
                    datos.add(DatoX);
                    DatoY = number;
                    datos.remove(0);
                    datos.add(DatoY);
                    XYSeries series1 = new SimpleXYSeries(
                            datos,          // SimpleXYSeries takes a List so turn our array into a List
                            SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                            "Series1");
                    plot.clear();
                    plot.addSeries(series1,new LineAndPointFormatter(Color.rgb(0, 0, 0), null, null, null));
                    plot.redraw();
                }
            };
            graf.run();
        }
    }

    /*

    private Number[] Cambiar (Number number){
        for (int i=0;i<series2Numbers.length-1;i++){
            series2Numbers[i]=series2Numbers[i+1];
        }
        series2Numbers[series2Numbers.length-1]=number;
        return series2Numbers;
    }
*/
    private class Miactualizador implements Observer {
        Plot plot;

        public Miactualizador(Plot plot) {
            this.plot = plot;
        }
        @Override
        public void update(Observable observable, Object data) {
            plot.redraw();
        }
    }

}
