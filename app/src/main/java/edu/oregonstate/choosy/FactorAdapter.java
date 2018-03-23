package edu.oregonstate.choosy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private OnFactorClickListener listener;

    FactorAdapter(OnFactorClickListener factorListener){
        mFactorList = new ArrayList<DecisionUtils.factorObject>();
        listener = factorListener;
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

    public interface OnFactorClickListener{
        void onFactorClick(DecisionUtils.factorObject fact);
    }

    class FactorViewHolder extends RecyclerView.ViewHolder{
        private TextView mFactorTextView;

        public FactorViewHolder(View itemView){
            super(itemView);
            mFactorTextView = (TextView)itemView.findViewById(R.id.tv_factor_item_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DecisionUtils.factorObject factor = mFactorList.get(getAdapterPosition());
                    listener.onFactorClick(factor);
                }
            });
        }
        public void bind(DecisionUtils.factorObject factor){
            mFactorTextView.setText(factor.name);
        }

        public void removeFactor() {
            int pos = getAdapterPosition();
            DecisionUtils.factorObject fact = mFactorList.get(pos);
            String name = fact.name;
            String comp = fact.comp;

            //Remove from db
            ChoosyDatabase db = new ChoosyDatabase(itemView.getContext());
            db.deleteFactor(name, comp);

            //Remove from arraylist and notify update
            mFactorList.remove(pos);
            notifyDataSetChanged();
        }
    }
}
