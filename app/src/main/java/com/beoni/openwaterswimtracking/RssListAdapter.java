package com.beoni.openwaterswimtracking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.beoni.openwaterswimtracking.model.RssItemSimplified;

import java.util.ArrayList;

//TODO: change implementation of the all adapter by using Android Annotations https://github.com/androidannotations/androidannotations/wiki/Adapters-and-lists
public class RssListAdapter extends ArrayAdapter
{

    private Context ctx;

    private int layoutId;

    public RssListAdapter(Context ctx, int layoutId, ArrayList<RssItemSimplified> rssItems) {
        super(ctx, layoutId, rssItems);
        this.layoutId = layoutId;
        this.ctx = ctx;
    }


    @Override
    public View getView(int position, View viewItem, ViewGroup viewGroup) {
        final RssItemView holder;

        if (viewItem == null)
        {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            viewItem = inflater.inflate(layoutId, viewGroup, false);
            holder = new RssItemView();
            holder.btnKnowMore = (Button) viewItem.findViewById(R.id.btn_know_more);
            holder.titleView = (TextView) viewItem.findViewById(R.id.rss_title);
            holder.dateView = (TextView) viewItem.findViewById(R.id.rss_date);
            viewItem.setTag(holder);
        }
        else
            holder = (RssItemView) viewItem.getTag();

        final RssItemSimplified item = (RssItemSimplified) this.getItem(position);

        holder.titleView.setText(item.getTitle());
        holder.dateView.setText(item.getDate().toString());
        holder.btnKnowMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String link = item.getLink();
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(uri);
                getContext().startActivity(intent);
            }
        });

        return viewItem;
    }

    static class RssItemView
    {
        Button btnKnowMore;
        TextView titleView;
        TextView dateView;
    }
}
