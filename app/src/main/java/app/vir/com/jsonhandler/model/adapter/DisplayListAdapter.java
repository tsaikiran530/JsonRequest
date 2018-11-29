package app.vir.com.jsonhandler.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.LinkedHashMap;
import java.util.List;

import app.vir.com.jsonhandler.R;
import app.vir.com.jsonhandler.model.pojo.Album;

public class DisplayListAdapter extends RecyclerView.Adapter<DisplayListAdapter.ViewHolder> {
    Context context;
    private List<Album> mDataset;
    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleText;


        public ViewHolder(View v) {
            super(v);
            titleText = (TextView) v.findViewById(R.id.title_text);


        }
    }

    public DisplayListAdapter(Context context, List<Album> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if ((mDataset.get(position).getTitle() != null)) {
            if (!mDataset.get(position).getTitle().isEmpty()) {
                holder.titleText.setText(mDataset.get(position).getTitle());
            } else {
                holder.titleText.setText("No Data");
            }

        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



    public Album getItemAtPosition(int position){
       return mDataset.get(position);
    }
}
