package com.enamakel.backseattester.adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.TabbedActivity;
import com.enamakel.backseattester.data.models.media.MovieModel;
import com.enamakel.backseattester.data.resources.MediaResource;

import java.util.List;

import javax.inject.Inject;


public class MovieListAdapter extends BaseAdapter {
    List<MovieModel> movies;
    Context context;
    static LayoutInflater inflater = null;

    @Inject MediaResource mediaResource;


    public MovieListAdapter(Activity mainActivity, List<MovieModel> movies) {
        this.movies = movies;
        context = mainActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return movies.size();
    }


    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MovieModel movie = (MovieModel) getItem(position);

        // Inflate all the items
        View rowView = inflater.inflate(R.layout.movie_list_item, null);
        TextView title = (TextView) rowView.findViewById(R.id.movieListItemTitle);
        TextView description = (TextView) rowView.findViewById(R.id.movieListItemDescription);
        Button watch = (Button) rowView.findViewById(R.id.movieListItemWatch);

        // Set the text
        title.setText(movie.getTitle());
        description.setText(String.format("%s (%s) - %s",
                        movie.getStudio(), movie.getYear(), movie.getGenre())
        );

        // Set the click listener
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabbedActivity.info("watching " + movie.getTitle());
                mediaResource.watchMovie(movie);
            }
        });

        return rowView;
    }
}
