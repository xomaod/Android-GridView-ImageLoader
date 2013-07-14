package com.example.gridview;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private GridView grid;
	private GridAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		grid = (GridView) findViewById(R.id.gridView1);
		
		Vector<HashMap<String, String>> vData = new Vector<HashMap<String,String>>();
		
		for(int i=0; i<1000; i++){
			String name = "Name "+ (i+1);
			String desc = "Description "+ (i+1);
			HashMap<String, String> hData = new HashMap<String, String>();
			hData.put("name", name);
			hData.put("desc", desc);
			
			vData.addElement(hData);
		}
		
		adapter = new GridAdapter(vData);
		grid.setAdapter(adapter);
		grid.setNumColumns(2);
		
		File cacheDir = StorageUtils.getCacheDirectory(this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
        .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
        .threadPoolSize(3) // default
        .threadPriority(Thread.NORM_PRIORITY - 1) // default
        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
        .memoryCacheSize(2 * 1024 * 1024)
        .memoryCacheSizePercentage(13) // default
        .discCache(new UnlimitedDiscCache(cacheDir)) // default
        .discCacheSize(50 * 1024 * 1024)
        .discCacheFileCount(100)
        .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
        .imageDownloader(new BaseImageDownloader(this)) // default
        .imageDecoder(new BaseImageDecoder()) // default
        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
        .build();
		
		ImageLoader.getInstance().init(config);
		
	}

	
	   //sub class
		class GridAdapter extends BaseAdapter{
				
				Vector<HashMap<String,String>> vData;
				GridAdapter(Vector<HashMap<String,String>> vData){
					this.vData = vData;
				}
				
				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return vData.size();
				}

				@Override
				public Object getItem(int index) {
					// TODO Auto-generated method stub
					return vData.elementAt(index);
				}

				@Override
				public long getItemId(int index) {
					// TODO Auto-generated method stub
					return index;
				}

				@Override
				public View getView(int index, View convertView, ViewGroup parent) {
					// TODO Auto-generated method stub
					HashMap<String, String> hData = vData.elementAt(index);
					
					View v;
					
					if(convertView == null){
						v = getLayoutInflater().inflate(R.layout.grid_element, parent, false);
					}else{
						v = convertView;
					}
					
					TextView txtName = (TextView) v.findViewById(R.id.gridText);
					
					
					txtName.setText(hData.get("name"));
					
					
					if(index % 2 == 0){
						if( index % 3 == 0)
							v.setBackgroundColor(Color.CYAN);
						else
							v.setBackgroundColor(Color.BLUE);
					}else{
						if( index % 3 == 0)
							v.setBackgroundColor(Color.YELLOW);
						else
							v.setBackgroundColor(Color.GREEN);
					}
					
					ImageView img = (ImageView) v.findViewById(R.id.gridImg);
					ImageLoader.getInstance().displayImage("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSFmiyV_1EhSzV92hTrf_PDPyWi00tb082t57188fxRf_eckoth", img);
					
					return v;
				}
				
			}
	
	
}





