package DataStructure;

/**
 * Created by SuperSong on 2017-07-30.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaist.supersong.bebecode.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class  ImageSlideAdapter extends RecyclerView.Adapter<ImageSlideAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;
    DisplayMetrics metrics;

    public ImageSlideAdapter(Context context, ArrayList<AndroidVersion> android_versions , DisplayMetrics metrics) {
        this.context = context;
        this.android_versions = android_versions;
        this.metrics = metrics;
    }

    @Override
    public ImageSlideAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_slide_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name());
        //Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(60, 120).into(viewHolder.img_android);
        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).resize(200, 300).centerInside().into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_android;
        ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);

        }
    }
}