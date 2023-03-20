package com.example.up_project_morozov;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterUserPhotos extends BaseAdapter
{
    private Context mContext;
    List<UserPhotoModel> maskList;

    public AdapterUserPhotos(Context mContext, List<UserPhotoModel> maskList) {
        this.mContext = mContext;
        this.maskList = maskList;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.user_photo_item,null);

        ImageView Image = v.findViewById(R.id.photoIV);
        TextView dateCreate = v.findViewById(R.id.addtimeTV);

        UserPhotoModel maskProfileImage  = maskList.get(position);

        if(maskProfileImage.getImageProfile().exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(maskProfileImage.getImageProfile().getAbsolutePath());
            Image.setImageBitmap(myBitmap);
        }
        dateCreate.setText(maskProfileImage.getData());

        return v;
    }
}
