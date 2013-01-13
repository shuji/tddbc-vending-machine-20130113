package tddbc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DrinkStock {

//    final int price = 120;
//    int number = 5;

    public String getStockText(int buttonId) {
        return drinks.get(buttonId).toString();
    }

    public void decrement(int buttonId) {
        drinks.get(buttonId).addNum(-1);
    }

    public boolean canPurchase(int buttonId, int creditAmount) {
        return drinks.get(buttonId).canPurchase(creditAmount);
    }

    private Map<Integer, DrinkInfo> drinks = new HashMap<>();

    public int getNum(int buttonId) {
        if (!drinks.containsKey(buttonId)) throw new IllegalArgumentException();
        return drinks.get(buttonId).getNum();
    }

    public void set(int buttonId, DrinkInfo drinkInfo) {
        drinks.put(buttonId, drinkInfo);
    }

    public void addNumTo(int buttonId) {
        addNumTo(buttonId, 1);
    }

    public void addNumTo(int buttonId, int num) {
        drinks.get(buttonId).addNum(num);
    }

    public List<Integer> getPurchasables(int amount) {
        List<Integer> result = new LinkedList<>();
        for (Entry<Integer, DrinkInfo> e : drinks.entrySet()) {
            DrinkInfo drinkInfo = e.getValue();
            if (0 < drinkInfo.getNum() && drinkInfo.price <= amount) result.add(e.getKey());
        }
        return result;
    }

    public int getPrice(int buttonId) {
        return drinks.get(buttonId).price;
    }

}
