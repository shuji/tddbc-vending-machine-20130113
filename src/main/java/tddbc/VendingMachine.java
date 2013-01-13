package tddbc;

import java.util.HashSet;
import java.util.Set;

public class VendingMachine {
    private static final Set<Integer> ALLOW_MONEYS = new HashSet<>();
    static {
        ALLOW_MONEYS.add(10);
        ALLOW_MONEYS.add(50);
        ALLOW_MONEYS.add(100);
        ALLOW_MONEYS.add(500);
        ALLOW_MONEYS.add(1000);
    };

    private MoneyStock moneyStock = new MoneyStock();
    private DrinkStock drinkStock = new DrinkStock();

    private int changeAmount;
    int saleAmount = 0;

    public void insert(int insertAmount) throws UnsupportedMoneyException {
        if (!isValidAmount(insertAmount)) throw new UnsupportedMoneyException();
        moneyStock.append(insertAmount);
    }

    public int getCreditAmount() {
        return moneyStock.getCreditAmount();
    }

    private boolean isValidAmount(int insertAmount) {
        return (ALLOW_MONEYS.contains(insertAmount));
    }

    public String getStockText(int buttonId) {
        return getDrinkStock().getStockText(buttonId);
    }

    public void purchase(int buttonId) {
        int price = getDrinkStock().getPrice(buttonId);
        if (moneyStock.getCreditAmount() >= price) {
            getDrinkStock().decrement(buttonId);
            moneyStock.remove(price);
            saleAmount += price;
        }
    }

    public int getChangeAmount() {
        return changeAmount;
    }

    public void payback() {
        changeAmount += moneyStock.getCreditAmount();
        moneyStock.reset();
    }

    public boolean canPurchase(int buttonId) {
        return getDrinkStock().canPurchase(buttonId, getCreditAmount());
    }

    public int getSaleAmount() {
        return saleAmount;
    }

    public DrinkStock getDrinkStock() {
        return drinkStock;
    }

}
