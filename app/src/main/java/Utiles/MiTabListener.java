package Utiles;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;

public class MiTabListener implements ActionBar.TabListener {

	private Fragment fragment;
	
	public MiTabListener(Fragment fg)
	{
		this.fragment = fg;
	}
	

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        Log.i("ActionBar", tab.getText() + " deseleccionada.");
        fragmentTransaction.remove(fragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        Log.i("ActionBar", tab.getText() + " deseleccionada.");
        fragmentTransaction.remove(fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        Log.i("ActionBar", tab.getText() + " reseleccionada.");
    }
}
