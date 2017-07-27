import org.junit.Test;

import java.util.Date;

/**
 * Description: TestFormat
 * Author: DIYILIU
 * Update: 2017-07-21 10:33
 */
public class TestFormat {


    @Test
    public void test(){

        Date d = new Date();

        System.out.println(d);
        String s = String.format("%1$tm/%1$td %1$tR", d);

        System.out.println(s);

    }


    @Test
    public void test1(){

        Date d = new Date();

        String s = String.format("%1$tF %1$tT", d);

        System.out.println(s);

    }

    @Test
    public void test2(){


        String s = String.format("%.0f", 10.623);

        System.out.println(s);

    }

    @Test
    public void test3(){

        String str = "10.623";

        String s =  str.substring(0, str.indexOf("."));

        System.out.println(s);

    }
}
