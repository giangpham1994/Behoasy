package edu.fpoly.edict.adapter;

import android.graphics.drawable.Drawable;

/**
 * Lớp này dùng để lưu thông tin cho từng mục của Navigation Drawer.<br>
 * Lớp được dùng cho lớp DrawerListItemAdapter
 * 
 */
public class DrawerListItem {
	String title;
	Drawable icon;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	
}
