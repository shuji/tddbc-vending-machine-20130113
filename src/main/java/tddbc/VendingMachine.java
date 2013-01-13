package tddbc;

public class VendingMachine {
    private static final int[] ALLOW_MONEYS = {10, 50, 100, 500, 1000};
    private final int PRICE = 120;

    private int creditAmount;
    private int number = 5;
    private int changeAmount;

    public int getCreditAmount() {
        return creditAmount;
    }

    public void insert(int insertAmount) {
        for (int allowMoney : ALLOW_MONEYS) {
            if (allowMoney == insertAmount) {
                creditAmount += insertAmount;
            }
        }
    }

    public String getStockText() {
        return String.format("コーラ:%d円:%d本", PRICE, number);
    }

    public void purchase() {
        if (creditAmount >= PRICE) {
            number--;
            creditAmount -= PRICE;
        }
    }

    public int getChangeAmount() {
        return changeAmount;
    }

    public void payback() {
        changeAmount += creditAmount;
        creditAmount = 0;
    }
}
