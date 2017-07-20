package Graficos;

import android.util.Log;

import com.androidplot.xy.XYSeries;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by levi on 01/10/14.
 */
public class SeriesDatos implements XYSeries {
    private Number datasource;
    private String title;
    private MyObservable notifier;

    public SeriesDatos(Number datasource, String title) {
        this.datasource = datasource;
        this.title = title;
    }
    @Override
    public int size() {
        return 20;
    }

    @Override
    public Number getX(int index) {
        return  index;
    }

    @Override
    public Number getY(int index) {
        myLog("dibujando"+datasource.toString());
        return datasource;
    }

    @Override
    public String getTitle() {
        return title;
    }

    class MyObservable extends Observable {
        @Override
        public void notifyObservers() {
            setChanged();
            super.notifyObservers();
        }
    }
    {
        notifier = new MyObservable();
    }
    private void myLog(String msg) {
        Log.d("SerieDatos", msg);
    }

    public void addObserver(Observer observer) {
        notifier.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        notifier.deleteObserver(observer);
    }
}
