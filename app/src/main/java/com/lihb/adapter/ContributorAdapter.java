package com.lihb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lihb.integrationdemo.R;
import com.lihb.model.Contributor;

import java.util.ArrayList;

/**
 * Created by lihb on 16/1/2.
 */
public class ContributorAdapter extends BaseAdapter {

    private ArrayList<Contributor> mData = new ArrayList<Contributor>();
    private Context mContext;

    public ContributorAdapter(Context context, ArrayList<Contributor> dataList) {
        mContext = context;
        mData = dataList;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.repo_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.userNameTxt = (TextView) rowView.findViewById(R.id.user_name_txt);
//            viewHolder.followerTxt = (TextView) rowView.findViewById(R.id.followers_txt);
            viewHolder.userAvatarImg = (ImageView) rowView
                    .findViewById(R.id.user_avatar_img);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        Glide.with(mContext)
                .load(mData.get(position).avatar_url)
                .override(200, 200)
                .into(holder.userAvatarImg);
        holder.userNameTxt.setText(mData.get(position).login);
//        holder.followerTxt.setText(mData.get(position).login);

        return rowView;
    }

    class ViewHolder {
        public TextView userNameTxt;
//        public TextView followerTxt;
        public ImageView userAvatarImg;
    }
}
