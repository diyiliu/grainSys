import com.diyiliu.util.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

/**
 * Description: TestSql
 * Author: DIYILIU
 * Update: 2017-07-27 15:00
 */
public class TestSql {


    @Test
    public void test(){

        Date date = new Date();
        System.out.println(String.format("%1$tF %1$tT", date));
        String sql = "update member set update_time=? where name=?";
        Object[] param = new Object[]{date, "王旺"};

        try {
            QueryRunner runner = new QueryRunner(DbUtil.getDataSource());
            int num = runner.update(sql, param);
            if (num > 0){

                System.out.println("执行成功!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
