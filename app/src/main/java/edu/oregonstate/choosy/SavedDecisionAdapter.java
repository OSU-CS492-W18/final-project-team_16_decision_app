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
    OnSavedDecisionClickListener mSavedDecisionClickListener;

    SavedDecisionAdapter(OnSavedDecisionClickListener savedDecisionClickListener){
        mSavedDecisionsList = new ArrayList<String>();
        mSavedDecisionClickListener = savedDecisionClickListener;
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
        String decision = mSavedDecisionsList.get(mSavedDecisionsList.size() - position - 1);
        holder.bind(decision);
    }

    public void addSavedDecision(String decisionItem){
        mSavedDecisionsList.add(decisionItem);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mSavedDecisionsList.size();
    }

    class SavedDecisionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mSavedDecisionTextView;

        public SavedDecisionViewHolder(View itemView){
            super(itemView);
            mSavedDecisionTextView = (TextView)itemView.findViewById(R.id.tv_main_inrv_saved_decision_item_text);
        }
        public void bind(String decision){
            mSavedDecisionTextView.setText(decision);
        }

        public void onClick(View view){
            Log.d(TAG, "onClick has been clicked !!!!!!!!!");
        }
    }

}
