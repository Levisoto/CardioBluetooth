package Fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cas.cardioapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import Utiles.DatosBluetoothReceiver;

/**
 * Created by levi on 24/11/14.
 */
public class BluetoothFragment extends Fragment {

    // variables
    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();

    // final variables
    private static final int BLUETOOTH_REQUEST_CODE = 666;

    // variables for BT devices
    private List<BluetoothDevice> device = new ArrayList<BluetoothDevice>();
    private List<String> nameDevice = new ArrayList<String>();
    private BluetoothDevice actualDevice = null;
    private static Boolean isBTConected = false;

    private static BluetoothSocket mSocket;
    private static OutputStream mOutStream;
    private static InputStream mInStream;

    // listener
    BTListener listener = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return new View(getActivity());
    }

    /**
     * Activamos Bluetooth si es que no lo estuviera pidiendo permiso
     *
     * @since 1.0.0
     */
    public void ActivateBT() {
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                myLog("Activando Bluetooth");
                Intent enableBTIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTIntent, BLUETOOTH_REQUEST_CODE);
            } else {
                myLog("Bluetooth Activado");
                LoadBT();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // lanzamos LoadBT() si es que aceptamos encender el BT
        if (requestCode == BLUETOOTH_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    myLog("Aceptamos encender el BT y cargamos los pairedDevices");
                    LoadBT();
                    break;

                case Activity.RESULT_CANCELED:
                    Toast.makeText(getActivity(), "Debe activar el Bluetooth",
                            Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Metodo para cargar todo los dispositivos Bluetooth con los que se
     * encuentra apareados el dispositivo
     *
     * @since 1.0.0
     */

    public void LoadBT() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();

        for (BluetoothDevice mBluetoothDevice : pairedDevices) {
            device.add(mBluetoothDevice);
            nameDevice.add(mBluetoothDevice.getName());
        }

        if (listener != null) {
            // Mandamos por una interface los nombres de los dispositivos
            // apareados
            listener.onFinishedLoadBT(nameDevice);
        }

    }

    /**
     * conectar con el dispositivo seleccionado
     *
     * @param idDevice
     *            posici�n del dispositivo a conectarse
     * @since 1.0.0
     */
    public void connectDevice(int idDevice) {
        actualDevice = device.get(idDevice);

        BluetoothSocket tmp = null;
        try {
            tmp = actualDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
        }

        mSocket = tmp;
        try {
            mSocket.connect();
            isBTConected = true;
            iniConection();
        } catch (IOException e) {
            isBTConected = false;
        }
    }

    private void iniConection() {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = mSocket.getInputStream();
            tmpOut = mSocket.getOutputStream();
        } catch (IOException e) {
            Log.e("Error", e + "");
        }

        mInStream = tmpIn;
        mOutStream = tmpOut;
    }

    /**
     * Aseguramos que estamos conectado
     *
     * @return si esta conectado con algun dispositivo
     * @since 1.0.0
     */
    public static Boolean isBTConected() {
        return isBTConected;
    }

    /**
     * Metodo para enviar paquetes por Bluetooth
     *
     * @param msg
     *            mensaje a enviar
     * @return
     *          si el envio es exitoso
     * @since 1.0.0
     */
    public static boolean enviarPaqBT(String msg) {
        if (isBTConected == true) {
            for (Byte letra : msg.getBytes())
                try {
                    mOutStream.write(letra);

                    Log.d("Mensaje Bluetooth", "Enviando dato = " + msg);
                } catch (IOException e) {
                    Log.d("Mensaje Bluetooth", "No se envio dato = " + msg);
                    return false;
                }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Metodo para recibir paquetes por Bluettoth
     *
     * @since 1.1.0
     */
    public void recibirPaqBT() {
        final Handler handler = new Handler();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(mInStream != null) {
                        try {
                            final byte[] buffer = new byte[1024];
                            final int bytes;
                            bytes = mInStream.read(buffer);
                            Message message= handler.obtainMessage(0, bytes, 0, buffer);
                            //Log.d("cantidad",String.valueOf(bytes));

                            if (listener != null) {
                                   listener.onReceivePaq(message,buffer); }
                            /*
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (listener != null) {

                                        for (int i = 0; i < bytes; i++) {
                                                listener.onReceivePaq(buffer[i]);
                                            }
                                            //listener.onReceivePaq(buffer[i]);
                                        }
                                    }

                            });*/
                        } catch (IOException e) {
                            break;
                        }
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * Detenemos y desconectamos BT
     *
     * @since 1.0.0
     */
    public static void stopBT() {
        isBTConected = false;
        try {
            if (mInStream != null)
                mInStream.close();

            if (mSocket != null)
                mSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Apagamos Bluetooth
     *
     * @since 1.0.0
     */
    public static void disableBT() {
        if (mBluetoothAdapter.isEnabled())
            mBluetoothAdapter.disable();
    }

    /**
     * Listener para la comunicaci�n con otras Activity
     *
     * @author Jorge
     * @version 1.0.0
     * @since 1.0.0
     */
    public interface BTListener {
        void onFinishedLoadBT(List<String> nombres);
        void onReceivePaq(Message message, byte[] buffer);
        //void onReceivePaq( byte data);
    }

    public void setBTListener(BTListener listener) {
        this.listener = listener;
    }

    private void myLog(String msg) {
        Log.d("BluetoothClass", msg);
    }
}
