package com.example.cas.cardioapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.nio.ByteBuffer;
import java.util.List;

import Fragments.BluetoothFragment;


public class BluetoothList extends FragmentActivity implements BluetoothFragment.BTListener,
        OnItemClickListener {

    private ListView lista;
    private BluetoothFragment btFragment;
    //private byte[] datos = new byte[5];
    private String[] datos = new String[2];
    private int counterBuffer = 0;
    private ProgressDialog pd;
    private float dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_list);

        lista = (ListView) findViewById(R.id.lvBTList);
        btFragment = (BluetoothFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentBT);

        btFragment.setBTListener(this);

        setPd("Cargando");
        lista.setOnItemClickListener(this);

        btFragment.ActivateBT();
        btFragment.recibirPaqBT();

    }

    /**
     * Listener de BluetoothFragment
     */
    @Override
    public void onFinishedLoadBT(List<String> nameDevices) {
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameDevices));
        pd.dismiss();

    }

    @Override
    public void onReceivePaq(Message message, byte[] data) {
        String mensaje = new String(data, 0, message.arg1);
        add(mensaje);
        myLog("receiving: data = " + String.valueOf(dat));
        Intent intent = new Intent("graficos");
        if (true) {
            intent.putExtra("datos",dat);
            sendBroadcast(intent);

        } else {
            myLog("receiving: error en iniciacion");
        }
    }

    public void add(String s) {
        datos[counterBuffer] = s;
        counterBuffer++;
        if(counterBuffer==2){
            Float[] dato={Float.parseFloat(datos[0]),Float.parseFloat(datos[1])};
            dat=dato[0]+dato[1];
            counterBuffer=0;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long id) {
        Tarea mTarea = new Tarea();
        mTarea.execute(position);
        setPd("Conectando");
    }
    private void myLog(String msg) {
        Log.d("BluetoothClass", msg);
    }

    class Tarea extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            btFragment.connectDevice(params[0]);
            return btFragment.isBTConected();
        }

        @Override
        protected void onPostExecute(Boolean isConected) {
            super.onPostExecute(isConected);
            pd.dismiss();
            if (isConected) {
                finish();
            } else {
                Toast.makeText(BluetoothList.this, "Error. Intente Nuevamente",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * Configurando Progress Dialog
     *
     * @param msg mensaje a mostrar
     * @since 1.0.0
     */
    private void setPd(String msg) {
        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setMessage(msg);
        pd.setCancelable(true);
        pd.show();
    }

}