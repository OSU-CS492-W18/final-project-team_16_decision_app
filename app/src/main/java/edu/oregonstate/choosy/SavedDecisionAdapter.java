package edu.oregonstate.choosy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by tarrenengberg on 3/20/18.
 */

public class SavedDecisionAdapter extends RecyclerView.Adapter<SavedDecisionAdapter.SavedDecisionViewHolder> {

    private ArrayList<String> mSavedDecisionsList;
    private OnSavedDecisionClickListener mSavedDecisionClickListener;

    SavedDecisionAdapter(OnSavedDecisionClickListener savedDecisionClickListener){
        mSavedDecisionsList = new ArrayList<String>();
        mSavedDecisionClickListener = savedDecisionClickListener;
    }

    public void updateSavedDecisionsData(ArrayList<String> decisionData){
        mSavedDecisionsList = decisionData;
        notifyDataSetChanged();
    }

    @Override
    public SavedDecisionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_saved_decision_item, parent, false);
        SavedDecisionViewHolder viewHolder = new SavedDecisionViewHolder(view);
        return viewHolder;
    }

    public interface OnSavedDecisionClickListener{
        void onSavedDecisionClick(String itemText);
    }

    @Override
    public void onBindViewHolder(SavedDecisionViewHolder holder, int position) {
        holder.bind(mSavedDecisionsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSavedDecisionsList.size();
    }

    class SavedDecisionViewHolder extends RecyclerView.ViewHolder {
        private TextView mSavedDecisionTextView;

        public SavedDecisionViewHolder(View itemView){
            super(itemView);
            mSavedDecisionTextView = (TextView)itemView.findViewById(R.id.tv_main_inrv_saved_decision_item_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String decisionText = mSavedDecisionsList.get(getAdapterPosition());
                    mSavedDecisionClickListener.onSavedDecisionClick(decisionText);
                }
            });
        }

        public void bind(String decision){
            mSavedDecisionTextView.setText(decision);
        }

    }

}
