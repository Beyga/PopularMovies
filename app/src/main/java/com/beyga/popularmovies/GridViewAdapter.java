package com.beyga.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private List objects;
    private Context context;

    public GridViewAdapter(@NonNull Context context, @NonNull List objects) {
        this.objects = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Movie movie = (Movie) objects.get(position);
        ViewHolder viewHoler;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
            viewHoler = new ViewHolder(convertView);
            convertView.setTag(viewHoler);
        }else{
            viewHoler = (ViewHolder) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            }
        });

        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(movie.getPoster_path()).into(viewHoler.poster);
        viewHoler.title.setText(movie.getTitle());

        return convertView;
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder{

        ImageView poster;
        TextView title;
        ViewHolder(View v){
            poster = v.findViewById(R.id.movie_image_view);
            title = v.findViewById(R.id.movie_title);
        }
    }


}
