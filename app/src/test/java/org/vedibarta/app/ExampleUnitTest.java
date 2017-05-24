package org.vedibarta.app;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        String[] mp = "2-shm/04-beshalach/beshalach1.zip2-shm/04-beshalach/beshalach2.zip".split(",");
        System.out.print("mpe=" + mp[0]);
        assertTrue(mp.length == 1);

    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    public void RxTest() {
        Observable.just("Hello, world!")
                .map(s -> s + " - jake")
                .subscribe(s -> System.out.println(s));

        Observable.fromArray("1", "2", "3")
                .subscribeOn(Schedulers.io())
                .filter(s -> s.equals("2"))
                .doOnNext(s -> throwException(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(par -> System.out.println(par), e -> e.printStackTrace(), () -> System.out.println("completed"));
        assertTrue(new Object() != null);
    }

    private void throwException(String s) throws Exception {
        throw new Exception("I'm the exception!");
    }


}