package com.example.cas.cardioapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import Utiles.TabsPagerAdapter;


public class MyActivity extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private TabsPagerAdapter adapter;
    private ActionBar abar;
    private String[] titulos = {"Lista de Usuarios","Analisis Grafico"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        viewPager = (ViewPager) findViewById(R.id.contenedor);

        //Obtenemos una referencia a la actionbar
        abar = getSupportActionBar();
        adapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //Establecemos el modo de navegación por pestañas
        abar.setNavigationMode(
                ActionBar.NAVIGATION_MODE_TABS);

        //Ocultamos el título de la actividad
        abar.setDisplayShowTitleEnabled(false);

        for ( String nombre:titulos){
            ActionBar.Tab tab = abar.newTab().setText(nombre);
            tab.setTabListener(this);
            abar.addTab(tab);
        }

        viewPager.setOnPageChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.add:
                Log.i("ActionBar", "Nuevo!");
                Intent intent1 = new Intent(this, CrearUsuariosFragment.class);
                startActivity(intent1);
                return true;
            case R.id.save:
                Log.i("ActionBar", "Guardar!");
                return true;
            case R.id.menu_settings:
                Log.i("ActionBar", "Settings!");
                return true;
            case R.id.bluetooth:
                Log.i("ActionBar", "Bluetooth!");
                Intent intent = new Intent(this, BluetoothList.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //<editor-fold desc="METODOS TAB CHANGE LISTNER">
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
    //</editor-fold>


    //<editor-fold desc="METODOS VIEW CHANGE LISTNER">
    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        abar.setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
    //</editor-fold>

}
