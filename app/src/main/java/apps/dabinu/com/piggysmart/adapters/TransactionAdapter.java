package apps.dabinu.com.piggysmart.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import apps.dabinu.com.piggysmart.R;
import apps.dabinu.com.piggysmart.models.TransactionModel;



public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    Context context;
    ArrayList<TransactionModel> transactions;
    private static ClickListener clickListener;
    int colorToChangeTo;
    boolean isAll;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> transactions, int colorToChangeTo, boolean isAll) {
        this.context = context;
        this.transactions = transactions;
        this.colorToChangeTo = colorToChangeTo;
        this.isAll = isAll;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.model_transaction_card, parent, false);

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.transactionName.setText(transactions.get(position).getName());
        holder.transactionAmount.setText(transactions.get(position).getAmount());
        if(!isAll){
            holder.theCard.setCardBackgroundColor(colorToChangeTo);
        }
        else{
            if(transactions.get(position).isDebt()){
                holder.theCard.setCardBackgroundColor(Color.RED);
            }
            else{
                holder.theCard.setCardBackgroundColor(Color.GREEN);
            }
        }


    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView transactionName, transactionAmount;
        public CardView theCard;

        public MyViewHolder(View itemView) {
            super(itemView);

            transactionName = itemView.findViewById(R.id.the_name);
            transactionAmount = itemView.findViewById(R.id.the_amount);
            theCard = itemView.findViewById(R.id.the_card);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }


    }

    public void setOnItemClickListener(ClickListener clickListener) {
        TransactionAdapter.clickListener = clickListener;
    }


    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

}