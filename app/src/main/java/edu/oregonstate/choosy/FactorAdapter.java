package edu.oregonstate.choosy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tarrenengberg on 3/22/18.
 */

public class FactorAdapter extends RecyclerView.Adapter<FactorAdapter.FactorViewHolder> {

    private ArrayList<DecisionUtils.factorObject> mFactorList;


    FactorAdapter(){
        mFactorList = new ArrayList<DecisionUtils.factorObject>();
    }

    public void updateFactorData(ArrayList<DecisionUtils.factorObject> factorData){
        mFactorList = factorData;
        notifyDataSetChanged();
    }

    @Override
    public FactorAdapter.FactorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.factor_item, parent, false);
        FactorAdapter.FactorViewHolder viewHolder = new FactorAdapter.FactorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FactorAdapter.FactorViewHolder holder, int position) {
        holder.bind(mFactorList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFactorList.size();
    }

    class FactorViewHolder extends RecyclerView.ViewHolder{
        private TextView mFactorTextView;

        public FactorViewHolder(View itemView){
            super(itemView);
            mFactorTextView = (TextView)itemView.findViewById(R.id.tv_factor_item_text);
        }
        public void bind(DecisionUtils.factorObject factor){
            mFactorTextView.setText(factor.name);
        }
    }
}
