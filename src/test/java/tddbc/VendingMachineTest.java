package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
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
        }

        @Test
        public void 投入前に投入金額を取得したら0円() {
            assertThat(sut.getCreditAmount(), is(0));
        }

        @Test
        public void 在庫情報を取得できる() {
            assertThat(sut.getStockText(), is("コーラ:120円:5本"));
        }

        @Test
        public void お金が足りないと購入しても在庫は減らない() {
            sut.purchase();
            assertThat(sut.getStockText(), is("コーラ:120円:5本"));
        }

        @Test
        public void お金が足りないと購入しても投入金額は減らない() {
            sut.purchase();
            assertThat(sut.getCreditAmount(), is(0));
        }
    }

    public static class お金が投入されている状態 {
        private VendingMachine sut;

        @Before
        public void setUp() {
            sut = new VendingMachine();
            sut.insert(1000);
        }

        @Test
        public void 投入後は投入金額が取得できる() {
            assertThat(sut.getCreditAmount(), is(1000));
        }

        @Test
        public void 複数回投入して投入金額を取得() {
            sut.insert(100);
            assertThat(sut.getCreditAmount(), is(1100));
        }

        @Test
        public void 十分なお金を投入して購入すると在庫が減る() {
            sut.purchase();
            assertThat(sut.getStockText(), is("コーラ:120円:4本"));
        }

        @Test
        public void 購入すると投入金額が減る() {
            sut.purchase();
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
    }

    @RunWith(Theories.class)
    public static class 金種による扱い {
        private VendingMachine sut;

        @Before
        public void setUp() {
            sut = new VendingMachine();
            sut.insert(100);
        }

        @Theory
        public void 扱えるお金を投入_投入金額は増える(@TestedOn(ints = {10, 50, 100, 500, 1000}) int amount) {
            sut.insert(amount);
            assertThat(sut.getCreditAmount(), is(100 + amount));
        }

        @Theory
        public void 扱えないお金を投入_投入金額は変わらない(@TestedOn(ints = {1, 5, 2000, 5000, 10000}) int amount) {
            sut.insert(amount);
            assertThat(sut.getCreditAmount(), is(100));
        }
    }
}
