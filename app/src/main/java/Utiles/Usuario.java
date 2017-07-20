package Utiles;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by levi on 15/11/14.
 */
public class Usuario implements Serializable {
    private String Nombre, Telefono, Email, Direccion;
    private String ImgUri; //NO es posible serializar objetos Uri

    public Usuario(String nombre, String telefono, String email, String direccion,String imgUri) {
        Nombre = nombre;
        Telefono = telefono;
        Email = email;
        Direccion = direccion;
        ImgUri = imgUri;
    }

    //<editor-fold desc="Getter Method's">


    public String getImgUri() {
        return ImgUri;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getEmail() {
        return Email;
    }

    public String getDireccion() {
        return Direccion;
    }
    //</editor-fold>


    //<editor-fold desc="Setter Method's">

    public void setImgUri(String imgUri) {
        ImgUri = imgUri;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
    //</editor-fold>


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (Direccion != null ? !Direccion.equals(usuario.Direccion) : usuario.Direccion != null)
            return false;
        if (Email != null ? !Email.equals(usuario.Email) : usuario.Email != null) return false;
        if (ImgUri != null ? !ImgUri.equals(usuario.ImgUri) : usuario.ImgUri != null) return false;
        if (!Nombre.equals(usuario.Nombre)) return false;
        if (Telefono != null ? !Telefono.equals(usuario.Telefono) : usuario.Telefono != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Nombre.hashCode();
        result = 31 * result + (Telefono != null ? Telefono.hashCode() : 0);
        result = 31 * result + (Email != null ? Email.hashCode() : 0);
        result = 31 * result + (Direccion != null ? Direccion.hashCode() : 0);
        result = 31 * result + (ImgUri != null ? ImgUri.hashCode() : 0);
        return result;
    }
}
