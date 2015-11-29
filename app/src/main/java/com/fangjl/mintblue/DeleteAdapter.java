package com.fangjl.mintblue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DeleteAdapter extends BaseAdapter {

	public static ListItemDelete itemDelete = null;
	private List<String> listDatas;
	private LayoutInflater mInflater;
	private Context context;
	private String table_name;
	private SQLiteDatabase db;

	public DeleteAdapter(Context context, List<String> listDatas,String table_name,SQLiteDatabase db) {
		mInflater = LayoutInflater.from(context);
		this.listDatas = listDatas;
		this.context = context;
		this.table_name = table_name;
		this.db = db;
	}

	@Override
	public int getCount() {
		return listDatas == null ? 0 : listDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return listDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_delete, null);
			holder.itemData = (TextView) convertView
					.findViewById(R.id.itemData);
			holder.btnDelete = (Button) convertView
					.findViewById(R.id.btnDelete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                delete(listDatas.get(position).substring(0, listDatas.get(position).indexOf('\n')), table_name, db);
				showInfo("删除成功");
			}
		});
		holder.itemData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showInfo("点击了数据： " + listDatas.get(position).substring(0, listDatas.get(position).indexOf('\n')));
			}
		});
		holder.itemData.setText(listDatas.get(position));
		return convertView;
	}

	public void delete(String danci,String table_name,SQLiteDatabase db){
		String sql = "delete from "+table_name+" where word = "+"'"+danci+"'";
		db.execSQL(sql);
	}


	class ViewHolder {
		TextView itemData;
		Button btnDelete;
	}

	private Toast mToast;

	public void showInfo(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void ItemDeleteReset() {
		if (itemDelete != null) {
			itemDelete.reSet();
		}
	}
}
