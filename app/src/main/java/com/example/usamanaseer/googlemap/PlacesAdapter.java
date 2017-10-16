package com.example.usamanaseer.googlemap;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.maps.android.quadtree.PointQuadTree;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UsamaNaseer on 10/16/2017.
 */

public class PlacesAdapter extends ArrayAdapter<Result> {

    List<Result> res;
    public PlacesAdapter(@NonNull Context context, @LayoutRes int resource,List<Result> res) {
        super(context, resource);
        this.res = res;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_places, null);
        }


        if (res != null) {
            ImageView img1 = (ImageView) v.findViewById(R.id.img_icon);
            TextView tt1 = (TextView) v.findViewById(R.id.txt_name);
            TextView tt2 = (TextView) v.findViewById(R.id.txt_type);
            TextView tt3 = (TextView) v.findViewById(R.id.txt_address);
            ImageLoader imageLoader = ImageLoader.getInstance();
            String imageUri = res.get(position).getIcon();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();
            ImageLoader.getInstance().init(config);
            imageLoader.displayImage(imageUri, img1);
            if (tt1 != null) {
                tt1.setText(res.get(position).getName());
            }

            if (tt2 != null) {
                tt2.setText(res.get(position).getTypes().get(0));
            }

            if (tt3 != null) {
                tt3.setText(res.get(position).getVicinity());
            }
        }

        return v;
    }


    @Override
    public int getCount() {
        return res.size();
    }


}
