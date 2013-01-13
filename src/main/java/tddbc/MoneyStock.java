package tddbc;

public class MoneyStock {
    
    private int creditAmount = 0;

    int getCreditAmount() {
        return creditAmount;
    }

    void append(int value) {
        this.creditAmount += value;
    }

    void remove(int value) {
        this.creditAmount -= value;
    }

    void reset() {
        this.creditAmount = 0;
    }

}
