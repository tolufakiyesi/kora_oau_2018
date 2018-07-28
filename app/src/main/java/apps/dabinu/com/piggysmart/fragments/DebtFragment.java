package apps.dabinu.com.piggysmart.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import apps.dabinu.com.piggysmart.R;
import apps.dabinu.com.piggysmart.adapters.TransactionAdapter;
import apps.dabinu.com.piggysmart.models.TransactionModel;


public class DebtFragment extends Fragment {


    boolean isDebt = false;


    public DebtFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_debt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        getView().findViewById(R.id.addNewDebt).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
                alert.setCancelable(false);
                final View view = (getActivity().getLayoutInflater()).inflate(R.layout.model_add_new, null);
                alert.setView(view);



                ((RadioButton) view.findViewById(R.id.toPay)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isDebt = true;
                        if(isChecked){
                            if(((RadioButton) view.findViewById(R.id.toCollect)).isChecked()){
                                ((RadioButton) view.findViewById(R.id.toCollect)).setChecked(false);
                            }
                        }
                    }
                });


                ((RadioButton) view.findViewById(R.id.toCollect)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                        isDebt = false;
                        if(isChecked){
                            if(((RadioButton) view.findViewById(R.id.toPay)).isChecked()){
                                ((RadioButton) view.findViewById(R.id.toPay)).setChecked(false);
                            }
                        }

                    }
                });

                alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(((EditText) view.findViewById(R.id.name)).getText().toString().equals("")){
                            ((EditText) view.findViewById(R.id.name)).setError("This field is required");
                        }
                        else if(((EditText) view.findViewById(R.id.amount)).getText().toString().equals("")){
                            ((EditText) view.findViewById(R.id.amount)).setError("This field is required");
                        }
                        else if(!((RadioButton) view.findViewById(R.id.toCollect)).isChecked() && !((RadioButton) view.findViewById(R.id.toPay)).isChecked()){
                            Toast.makeText(getActivity().getApplicationContext(), "Choose a trnsaction type", Toast.LENGTH_LONG).show();
                        }
                        else{
                            try{
                                FileOutputStream fos = getActivity().openFileOutput(Long.toString(System.currentTimeMillis()), Context.MODE_APPEND);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new TransactionModel(((EditText) view.findViewById(R.id.name)).getText().toString().trim(), ((EditText) view.findViewById(R.id.amount)).getText().toString().trim(), isDebt));
                                oos.close();
                                fos.close();
                                Toast.makeText(getActivity().getApplicationContext(), "Successful!!!", Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e){
                                Toast.makeText(getActivity().getApplicationContext(), "Failed, try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });


                alert.setNegativeButton("Cancel", null);

                alert.create();
                alert.show();








            }
        });


        final ArrayList<TransactionModel> allMyTransactions = new ArrayList<>();


        File[] allMySavedFiles = getActivity().getFilesDir().listFiles();

        for(File i: allMySavedFiles){
            try{
                TransactionModel object = (TransactionModel) (new ObjectInputStream(getActivity().openFileInput(i.getName()))).readObject();
                if(object.isDebt()){
                    allMyTransactions.add(object);
                }
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

            RecyclerView recyclerView = getView().findViewById(R.id.display_all_debts);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            TransactionAdapter adapter = new TransactionAdapter(getActivity().getApplicationContext(), allMyTransactions, Color.RED, false);
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
