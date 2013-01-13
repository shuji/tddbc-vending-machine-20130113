package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class DrinkStockTest {
    private static final int コーラ = 1;
    private static final int レッドブル = 2;
    private static final int 水 = 3;

    public static class 初期状態 {

        private DrinkStock sut;

        @Before
        public void setUp() throws Exception {
            sut = new DrinkStock();
        }

        @Test(expected = IllegalArgumentException.class)
        public void ID_1に対して在庫数を取得しようとすると例外を送出する() throws Exception {
            sut.getNum(1);
        }

        @Test
        public void ID_1に対してコーラを設定する() throws Exception {
            sut.set(1, new DrinkInfo("コーラ", 120));
            assertThat(sut.getNum(1), is(0));
        }

    }

    public static class ID1にコーラが0本ある状態 {

        private DrinkStock sut;

        @Before
        public void setUp() throws Exception {
            sut = new DrinkStock();
            sut.set(1, new DrinkInfo("コーラ", 120));
        }

        @Test
        public void ID_1の在庫は5本() throws Exception {
            assertThat(sut.getNum(1), is(0));
        }

        @Test
        public void ID_1に対してコーラを追加する() throws Exception {
            sut.addNumTo(1);
            assertThat(sut.getNum(1), is(1));
        }

        @Test
        public void ID_1に対してコーラを5本追加する() throws Exception {
            sut.addNumTo(コーラ, 5);
            assertThat(sut.getNum(1), is(5));
        }
        
        @Test
        public void _110円でコーラを購入できない() {
            assertThat(sut.canPurchase(1, 110), is(false));
        }

        @Test
        public void _120円でもコーラを購入できない() {
            assertThat(sut.canPurchase(1, 120), is(false));
        }

    }
    
    public static class ID1にコーラが5本ある状態 {

        private DrinkStock sut;

        @Before
        public void setUp() throws Exception {
            sut = new DrinkStock();
            sut.set(コーラ, new DrinkInfo("コーラ", 120));
            sut.addNumTo(1, 5);
        }
        
        @Test
        public void ID_1の在庫は5本() throws Exception {
            assertThat(sut.getNum(1), is(5));
        }


        @Test
        public void _110円でコーラを購入できない() {
            assertThat(sut.canPurchase(1, 110), is(false));
        }

        @Test
        public void _120円でコーラを購入できる() {
            assertThat(sut.canPurchase(1, 120), is(true));
        }

    }


    public static class _3種類のジュースがそれぞれ在庫がある場合 {
        private DrinkStock sut;

        @Before
        public void setUp() throws Exception {
            sut = new DrinkStock();
            sut.set(コーラ, new DrinkInfo("コーラ", 120, 5));
            sut.set(レッドブル, new DrinkInfo("レッドブル", 200, 5));
            sut.set(水, new DrinkInfo("水", 100, 5));
            assert sut.getNum(1) == 5;
            assert sut.getNum(2) == 5;
            assert sut.getNum(3) == 5;
        }
        
        @Test
        public void _90円で購入できるドリンクはない() throws Exception {
            List<Integer> actual = sut.getPurchasables(90);
            assertThat(actual.size(), is(0));
        }

        @Test
        public void _100円で購入できるドリンクは水だけ() throws Exception {
            List<Integer> actual = sut.getPurchasables(100);
            assertThat(actual.size(), is(1));
            assertThat(actual.get(0), is(水));
        }

        @Test
        public void _120円で購入できるドリンクは水とコーラ() throws Exception {
            List<Integer> actual = sut.getPurchasables(120);
            assertThat(actual.size(), is(2));
            assertThat(actual.get(0), is(コーラ));
            assertThat(actual.get(1), is(水));            
        }
        
    }

    public static class _3種類のジュースに在庫0がある場合 {
        private DrinkStock sut;

        @Before
        public void setUp() throws Exception {
            sut = new DrinkStock();
            sut.set(コーラ, new DrinkInfo("コーラ", 120, 0));
            sut.set(レッドブル, new DrinkInfo("レッドブル", 200, 5));
            sut.set(水, new DrinkInfo("水", 100, 1));
            assert sut.getNum(コーラ) == 0;
            assert sut.getNum(レッドブル) == 5;
            assert sut.getNum(水) == 1;
        }
        
        @Test
        public void _500円で購入できるドリンクは水とレッドブル() throws Exception {
            List<Integer> actual = sut.getPurchasables(500);
            assertThat(actual.size(), is(2));
            assertThat(actual.get(0), is(レッドブル));
            assertThat(actual.get(1), is(水));            
        }

    }
    
}
