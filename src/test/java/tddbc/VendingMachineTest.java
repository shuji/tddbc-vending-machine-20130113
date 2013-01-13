package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class VendingMachineTest {

    public static class お金が投入されていない状態 {
        private VendingMachine sut;

        @Before
        public void setUp() {
            sut = new VendingMachine();
            sut.getDrinkStock().set(1, new DrinkInfo("コーラ", 120, 5));
        }

        @Test
        public void コーラの在庫情報を取得できる() {
            assertThat(sut.getStockText(1), is("コーラ:120円:5本"));
        }

        @Test
        public void お金が足りないと購入しても在庫は減らない() {
            sut.purchase(1);
            assertThat(sut.getStockText(1), is("コーラ:120円:5本"));
        }

        @Test
        public void お金が足りないと購入しても投入金額は減らない() {
            sut.purchase(1);
            assertThat(sut.getCreditAmount(), is(0));
        }

        @Test
        public void コーラを購入できない() {
            assertThat(sut.canPurchase(1), is(false));
        }

        @Test
        public void 売上げが0円() {
            assertThat(sut.getSaleAmount(), is(0));
        }        
    }

    public static class _1000円が投入されている状態 {
        private VendingMachine sut;

        @Before
        public void setUp() throws Exception {
            sut = new VendingMachine();
            sut.getDrinkStock().set(1, new DrinkInfo("コーラ", 120, 5));
            sut.insert(1000);
        }

        @Test
        public void 複数回投入して投入金額を取得() throws Exception {
            sut.insert(100);
            assertThat(sut.getCreditAmount(), is(1100));
        }

        @Test
        public void コーラを購入するとコーラの在庫が4本になる() {
            sut.purchase(1);
            assertThat(sut.getStockText(1), is("コーラ:120円:4本"));
        }

        @Test
        public void コーラを購入できる() {
            assertThat(sut.canPurchase(1), is(true));
        }

        @Test
        public void コーラを購入すると投入金額が880円になる() {
            sut.purchase(1);
            assertThat(sut.getCreditAmount(), is(880));
        }

        @Test
        public void 払い戻していないときのお釣りは0円() {
            assertThat(sut.getChangeAmount(), is(0));
        }

        @Test
        public void 払い戻すと投入金額がお釣りになる() {
            sut.payback();
            assertThat(sut.getChangeAmount(), is(1000));
        }

        @Test
        public void 二回連続で払い戻してもお釣りは増えない() {
            sut.payback();
            sut.payback();
            assertThat(sut.getChangeAmount(), is(1000));
        }

        @Test
        public void 払い戻すと預かり金額が0円になる() {
            sut.payback();
            assertThat(sut.getCreditAmount(), is(0));
        }
        
        @Test
        public void 売上げが0円() {
            assertThat(sut.getSaleAmount(), is(0));
        }        

    }

    @RunWith(Theories.class)
    public static class 金種による扱い {
        private VendingMachine sut;

        @Before
        public void setUp() throws Exception {
            sut = new VendingMachine();
            sut.getDrinkStock().set(1, new DrinkInfo("コーラ", 120, 5));
            sut.insert(100);
        }

        @Theory
        public void 扱えるお金を投入_投入金額は増える(@TestedOn(ints = { 10, 50, 100, 500, 1000 }) int amount) throws Exception {
            sut.insert(amount);
            assertThat(sut.getCreditAmount(), is(100 + amount));
        }

    }

    @RunWith(Theories.class)
    public static class 扱えないお金の場合 {
        private VendingMachine sut;
        @DataPoints
        public static int[] VALUES = { 1, 5, 2000, 5000, 10000 };

        @Before
        public void setUp() {
            sut = new VendingMachine();
            sut.getDrinkStock().set(1, new DrinkInfo("コーラ", 120, 5));
        }

        @Theory
        public void 投入金額は変わらない(int amount) throws Exception {
            try {
                sut.insert(amount);
            } catch (UnsupportedMoneyException e) {
            }
            assertThat(sut.getCreditAmount(), is(0));
        }

        @Theory
        public void UnsupportedMoneyExceptionを送出する(int amount) throws Exception {
            try {
                sut.insert(amount);
                fail();
            } catch (UnsupportedMoneyException e) {
                // OK
            }
        }
    }
    public static class _120円を投入しコーラを1回購入した状態 {
        
        private VendingMachine sut;

        @Before
        public void setUp() throws Exception {
            sut = new VendingMachine();
            sut.getDrinkStock().set(1, new DrinkInfo("コーラ", 120, 5));
            sut.insert(100);
            sut.insert(10);
            sut.insert(10);
            sut.purchase(1);
        }
        
//        @Ignore
        @Test
        public void 売上げが120円() {
            assertThat(sut.getSaleAmount(), is(120));
        }        
        
    }

}
