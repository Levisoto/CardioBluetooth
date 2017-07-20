package Utiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by levi on 15/11/14.
 */
public class UsuarioReceiver extends BroadcastReceiver {
    public static final int USUARIO_AGREGADO = 1;
    public static final int USUARIO_ELIMINADO = 2;
    public static final int USUARIO_ACTUALIZADO = 3;

    private final ArrayAdapter<Usuario> adapter;

    public UsuarioReceiver(ArrayAdapter<Usuario> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int operacion = intent.getIntExtra("operacion",-1);
        switch (operacion){
            case USUARIO_AGREGADO:AgregarUsuario(intent);  break;
            case USUARIO_ELIMINADO:EliminarUsuario(intent); break;
            case USUARIO_ACTUALIZADO:ActualizarUsuario(intent); break;
        }
    }

    private void AgregarUsuario(Intent intent) {
        Usuario usuario = (Usuario) intent.getSerializableExtra("datos");
        adapter.add(usuario);
    }

    private void EliminarUsuario(Intent intent) {
        ArrayList<Usuario> lista = (ArrayList<Usuario>) intent.getSerializableExtra("datos");
        for (Usuario u : lista) adapter.remove(u);
    }

    private void ActualizarUsuario(Intent intent) {
        Usuario usuario = (Usuario) intent.getSerializableExtra("datos");
        int posicion = adapter.getPosition(usuario);
        adapter.insert(usuario,posicion);
    }
}
