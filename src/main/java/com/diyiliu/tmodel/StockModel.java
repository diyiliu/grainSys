package com.diyiliu.tmodel;

import com.diyiliu.util.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Description: StockModel
 * Author: DIYILIU
 * Update: 2017-07-20 16:07
 */
public class StockModel extends AbstractTableModel {

    private final String[] columnNames = new String[]{"编号", "姓名", "毛重","去皮","净重", "单价", "金额", "时间", "状态"};
    private List data;

    private final String sql = "SELECT" +
            " t.IN_NO," +
            " m.`NAME`," +
            " t.GROSS," +
            " t.TARE," +
            " t.SUTTLE," +
            " t.PRICE," +
            " t.MONEY," +
            " t.CREATE_TIME," +
            " t.STATE" +
            " FROM" +
            " stock t" +
            " INNER JOIN member m ON m.ID = t.MEMBER_ID" +
            " ORDER BY t.CREATE_TIME DESC limit 0,30";

    public StockModel(){
        refresh();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = ((Object[]) data.get(rowIndex))[columnIndex];
        if (value instanceof Date){

            return String.format("%1$tm/%1$td %1$tR", value);
        }

        if (columnIndex == 5){

            return String.format("%.2f", value);
        }

        if (columnIndex == 6 && value != null){
            String str = String.valueOf(value);
            return str.substring(0, str.indexOf("."));
        }

        /*if (columnIndex == 8 && value != null){

            return Constants.StockState.getName((Integer) value);
        }*/

        return value;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void refresh() {
        try {
            QueryRunner runner = new QueryRunner(DbUtil.getDataSource());
            data = runner.query(sql, new ArrayListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.fireTableDataChanged();
    }

    public void refresh(String sql, Object... params) {
        try {
            QueryRunner runner = new QueryRunner(DbUtil.getDataSource());
            data = runner.query(sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.fireTableDataChanged();
    }

    public Object[] getRowData(int rowIndex){

        return (Object[]) data.get(rowIndex);
    }
}
