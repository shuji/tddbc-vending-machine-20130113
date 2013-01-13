package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MoneyStockTest {

    public static class 初期状態 {
        private MoneyStock sut;

        @Before
        public void setUp() {
            sut = new MoneyStock();
        }

        @Test
        public void 投入金額を取得したら0円() {
            assertThat(sut.getCreditAmount(), is(0));
        }

    }
    
    public static class _1000円が追加されている状態 {
        private MoneyStock sut;

        @Before
        public void setUp() {
            sut = new MoneyStock();
            sut.append(1000);
        }

        @Test
        public void 投入金額を取得したら1000円() {
            assertThat(sut.getCreditAmount(), is(1000));
        }

    }

}
