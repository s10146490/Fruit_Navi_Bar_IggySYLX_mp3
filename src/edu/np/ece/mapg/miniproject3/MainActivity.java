package edu.np.ece.mapg.miniproject3;

import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class MainActivity extends Activity {
	
	private DrawerLayout drawLayout;
	private ListView listView;
	private String[] listViewName;
	private ActionBarDrawerToggle drawerListener;
	private MyAdapter myAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        myAdap = new MyAdapter(this);
        drawLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        listView = (ListView) this.findViewById(R.id.left_drawer);
        listViewName = getResources().getStringArray(R.array.ListViewName);
        listView.setAdapter(myAdap);
        
        listView.setOnItemClickListener(listener);
        
        drawerListener = new ActionBarDrawerToggle(this, drawLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
        	@Override
        	public void onDrawerOpened(View drawerView) {
        		// TODO Auto-generated method stub
        		super.onDrawerOpened(drawerView);
        	}
        	
        	@Override
        	public void onDrawerClosed(View drawerView) {
        		// TODO Auto-generated method stub
        		super.onDrawerClosed(drawerView);
        	}
        };
        drawLayout.setDrawerListener(drawerListener);
        
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	
    	super.onPostCreate(savedInstanceState);
    	drawerListener.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }
    
    OnItemClickListener listener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
		public void selectItem(int position){
			
			Fragment fragment = new FruitFragment();
	        Bundle args = new Bundle();
	        args.putInt(FruitFragment.ARG_FRUIT_NUMBER, position);
	        fragment.setArguments(args);

	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			listView.setItemChecked(position,true);
			setTitle(listViewName[position]);
			drawLayout.closeDrawer(listView);
		}
		
		public void setTitle(String title){
			getActionBar().setTitle(title);
		}
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(drawerListener.onOptionsItemSelected(item))
    	{
    		return true;
    	}
        return super.onOptionsItemSelected(item);
    }
	
	public static class FruitFragment extends Fragment {
	    public static final String ARG_FRUIT_NUMBER = "fruit_number";

	    public FruitFragment() {
	        // Empty constructor required for fragment subclasses
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.activity_fruit_layout, container, false);
	        int i = getArguments().getInt(ARG_FRUIT_NUMBER);
	        String fruit = getResources().getStringArray(R.array.ListViewName)[i];
	        String fruitmsg = getResources().getStringArray(R.array.FruitMessage)[i];
	        
	        ((TextView) rootView.findViewById(R.id.textView1)).setText(fruitmsg);
	        int imageId = getResources().getIdentifier(fruit.toLowerCase(Locale.getDefault()),
	                        "drawable", getActivity().getPackageName());
	        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
	        getActivity().setTitle(fruit);
	        return rootView;
	    }
	}
}

class MyAdapter extends BaseAdapter{

	private Context context;
	String[] fruits;
	int[] fimage = {R.drawable.apple, R.drawable.pear, R.drawable.orange, R.drawable.lemon, R.drawable.banana, R.drawable.papaya};
	public MyAdapter(Context context) {
		this.context=context;
		fruits = context.getResources().getStringArray(R.array.ListViewName);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fruits.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return fruits[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row=null;
		if(convertView == null)
		{
			LayoutInflater inflaterz = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflaterz.inflate(R.layout.custom_row, parent, false);
		}
		else
		{
			row = convertView;
		}
		TextView tv1 = (TextView) row.findViewById(R.id.textView1);
		ImageView im1 = (ImageView) row.findViewById(R.id.imageView1);
		tv1.setText(fruits[position]);
		im1.setImageResource(fimage[position]);
		return row;
	}
	
}