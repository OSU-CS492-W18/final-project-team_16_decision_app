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

    private ArrayList<DecisionUtils.decisionObject> mSavedDecisionsList;
    private OnSavedDecisionClickListener mSavedDecisionClickListener;

    SavedDecisionAdapter(OnSavedDecisionClickListener savedDecisionClickListener){
        mSavedDecisionsList = new ArrayList<DecisionUtils.decisionObject>();
        mSavedDecisionClickListener = savedDecisionClickListener;
    }

    public void updateSavedDecisionsData(ArrayList<DecisionUtils.decisionObject> decisionData){
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
        void onSavedDecisionClick(DecisionUtils.decisionObject decision);
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
                    DecisionUtils.decisionObject decisionText = mSavedDecisionsList.get(getAdapterPosition());
                    mSavedDecisionClickListener.onSavedDecisionClick(decisionText);
                }
            });
        }

        public void bind(DecisionUtils.decisionObject decision){
            mSavedDecisionTextView.setText(decision.getString());
        }

        public void removeDecision() {
            int pos = getAdapterPosition();
            DecisionUtils.decisionObject dec = mSavedDecisionsList.get(pos);
            String name = dec.firstOption;

            ChoosyDatabase db = new ChoosyDatabase(itemView.getContext());
            db.deleteDecision(name);

            //Remove from arraylist and notify update
            mSavedDecisionsList.remove(pos);
            notifyDataSetChanged();
        }

    }

}
