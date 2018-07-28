package apps.dabinu.com.piggysmart.models;

import java.io.Serializable;

public class TransactionModel implements Serializable{

    public String name, amount;
    public boolean isDebt;

    public TransactionModel(){

    }

    public TransactionModel(String name, String amount, boolean isDebt){
        this.name = name;
        this.amount = amount;
        this.isDebt = isDebt;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public boolean isDebt() {
        return isDebt;
    }
}
