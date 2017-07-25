import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * Description: TestHash
 * Author: DIYILIU
 * Update: 2017-07-24 16:19
 */
public class TestHash {


    @Test
    public void test() {

        String str = UUID.randomUUID().toString().substring(24);
        String s = String.format("%1$ty%1$tm%1$td%1$tH", new Date());
        System.out.println(str + s);
    }
}
