package tddbc;

public class DrinkInfo {

    public final String name;
    public final int price;
    private int num;

    public DrinkInfo(String name, int price) {
        this(name, price, 0);
    }

    public DrinkInfo(String name, int price, int num) {
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void addNum(int d) {
        this.num += d;
    }

    @Override
    public String toString() {
        return String.format("%s:%d円:%d本", name, price, getNum());
    }

    public boolean canPurchase(int creditAmount) {
        return num > 0 && price <= creditAmount;
    }

}
