package Utiles;


import android.support.v4.app.FragmentPagerAdapter;

import Fragments.GraficosFragment;
import Fragments.ListaUsuariosFragment;

/**
 * Created by levi on 15/11/14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int i) {
        switch (i){
            case 0: return new ListaUsuariosFragment();
            case 1: return new GraficosFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }
}
