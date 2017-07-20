package com.example.cas.cardioapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import Utiles.TextChangedListener;
import Utiles.Usuario;
import Utiles.UsuarioReceiver;

/**
 * Created by levi on 15/11/14.
 */
public class CrearUsuariosFragment extends FragmentActivity implements View.OnClickListener {

    private EditText txtNombre, txtTelefono, txtEmail, txtDireccion;
    private ImageView ImgViewUsuario;
    private Button BtnAgregar;
    private int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_usuario);
        InicializarComponentes();
    }


    private void InicializarComponentes() {
        txtNombre = (EditText) findViewById(R.id.CampoNombre);
        txtTelefono = (EditText) findViewById(R.id.CampoTelefono);
        txtEmail = (EditText) findViewById(R.id.CampoEmail);
        txtDireccion = (EditText) findViewById(R.id.CampoDireccion);
        ImgViewUsuario = (ImageView) findViewById(R.id.imgViewUsuario);
        ImgViewUsuario.setOnClickListener(this);

        txtNombre.addTextChangedListener(new TextChangedListener(){
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                BtnAgregar.setEnabled(!charSequence.toString().trim().isEmpty());
            }
        });
        BtnAgregar = (Button) findViewById(R.id.BtnAgregar);
        BtnAgregar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == BtnAgregar ){
            AgregarUsuario(
                    txtNombre.getText().toString(),
                    txtTelefono.getText().toString(),
                    txtEmail.getText().toString(),
                    txtDireccion.getText().toString(),
                    String.valueOf(ImgViewUsuario.getTag())
            );
            String mesg = String.format("%s Ha sido agregado a al lista!",txtNombre.getText());
            Toast.makeText(view.getContext(), mesg, Toast.LENGTH_SHORT).show();
            BtnAgregar.setEnabled(false);
            LimpiarCampos();

        }else if(view == ImgViewUsuario){
            Intent intent = null;
            if (Build.VERSION.SDK_INT < 19){
                //Para versiones antes de API 19
                //****Forma de Angeder a la Galeria de Imagenes
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
            }else{
                //API's mayores que la 19
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
            }
            intent.setType("image/*");
            startActivityForResult(intent,request_code);

        }
    }

    private void AgregarUsuario(String nombre, String telefono, String email, String direccion, String ImgUri) {
        Usuario Usuarios = new Usuario(nombre,telefono,email,direccion,ImgUri);
        Intent intent = new Intent("ListadeUsuarios");
        intent.putExtra("operacion", UsuarioReceiver.USUARIO_AGREGADO);
        intent.putExtra("datos",Usuarios);
        //Adapater.add(Usuarios);
    }

    private void LimpiarCampos() {
        txtNombre.getText().clear();
        txtTelefono.getText().clear();
        txtEmail.getText().clear();
        txtDireccion.getText().clear();
        //Solo para los campos
        ImgViewUsuario.setImageResource(R.drawable.usuario);

        txtNombre.requestFocus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK && requestCode== request_code){
            ImgViewUsuario.setImageURI(data.getData());
            ImgViewUsuario.setTag(data.getData());
        }
    }
}
