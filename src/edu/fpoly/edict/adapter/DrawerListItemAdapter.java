package edu.fpoly.edict.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.fpoly.edict.R;

public class DrawerListItemAdapter extends BaseAdapter {
	ArrayList<DrawerListItem> list ;
	private Context mContext;
	
	public DrawerListItemAdapter(Context context) {
		list = new ArrayList<DrawerListItem>();
		mContext = context;
	}
	
	/**
	 * Nạp các thông tin của Drawer từ danh sách được định nghĩa trong string.xml
	 */
	public void loadDrawerList(){
		Resources res = mContext. getResources();
		
		String[] titles = res.getStringArray(R.array.drawer_title_array);
		for (String title : titles) {
			DrawerListItem item = new DrawerListItem();
			item.setTitle(title);
			list.add(item);
		}
		
		TypedArray icons = res.obtainTypedArray(R.array.drawer_icon_array);
		for (int i = 0; i < icons.length(); i++) {
			list.get(i).setIcon(icons.getDrawable(i));
		}
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.drawer_list_item, null);
			
			TextView tv = (TextView) convertView.findViewById(R.id.drawer_title);
			ImageView iv = (ImageView) convertView.findViewById(R.id.drawer_icon);
			
			tv.setText(list.get(position).getTitle());
			
			if (list.get(position).getIcon() != null)
				iv.setImageDrawable(list.get(position).getIcon());
		}
		
		return convertView;
	}
	
}
