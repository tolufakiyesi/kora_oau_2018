package apps.dabinu.com.piggysmart.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import apps.dabinu.com.piggysmart.R;
import apps.dabinu.com.piggysmart.adapters.TransactionAdapter;
import apps.dabinu.com.piggysmart.models.TransactionModel;



public class AllTransactionsFragment extends Fragment {


    public AllTransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_all_transactions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.addNewTransaction).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //inflate add new dialog

                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
                alert.setCancelable(false);
                View view = (getActivity().getLayoutInflater()).inflate(R.layout.model_add_new, null);
                alert.setView(view);


                alert.create();
                alert.show();








            }
        });

        final ArrayList<TransactionModel> allMyTransactions = new ArrayList<>();


        File[] allMySavedFiles = getActivity().getFilesDir().listFiles();

        for(File i: allMySavedFiles){
            try{
                TransactionModel object = (TransactionModel) (new ObjectInputStream(getActivity().openFileInput(i.getName()))).readObject();
                allMyTransactions.add(object);
            }
            catch (Exception e){

            }
        }

        if(allMyTransactions.isEmpty()){
            (getView().findViewById(R.id.noTransaction)).setVisibility(View.VISIBLE);
            (getView().findViewById(R.id.yesTransaction)).setVisibility(View.GONE);
        }
        else{
            (getView().findViewById(R.id.noTransaction)).setVisibility(View.GONE);
            (getView().findViewById(R.id.yesTransaction)).setVisibility(View.VISIBLE);

            RecyclerView recyclerView = getView().findViewById(R.id.display_all_transactions);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            TransactionAdapter adapter = new TransactionAdapter(getActivity().getApplicationContext(), allMyTransactions, Color.GREEN, true);
            recyclerView.setAdapter(adapter);


            adapter.setOnItemClickListener(new TransactionAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {

                }

                @Override
                public void onItemLongClick(int position, View v) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Delete this transaction?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //delete transaction
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }

    }

}