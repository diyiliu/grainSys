package com.diyiliu.listener;

import com.diyiliu.bean.Member;
import com.diyiliu.tmodel.StockModel;
import com.diyiliu.util.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

/**
 * Description: MemberPrompt
 * Author: DIYILIU
 * Update: 2017-07-21 13:57
 */
public class MemberPrompt implements KeyListener {

    private JComboBox comboBox;
    private JTextField textField;
    private StockModel tableModel;
    public MemberPrompt(JComboBox comboBox, StockModel tableModel) {

        this.comboBox = comboBox;
        textField = (JTextField) comboBox.getEditor().getEditorComponent();
        this.tableModel = tableModel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (KeyEvent.VK_ENTER == e.getKeyCode()){
            String name = textField.getText().trim();
            if (StringUtils.isEmpty(name)){

                tableModel.refresh();
                return;
            }

            String sql = "SELECT t.IN_NO,m.`NAME`,t.GROSS,t.TARE,t.SUTTLE,t.PRICE,t.MONEY,t.CREATE_TIME,t.STATE from stock t " +
                    "INNER JOIN member m on m.ID = t.MEMBER_ID and m.`NAME`=?";
            tableModel.refresh(sql, name);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String name = textField.getText().trim();
        comboBox.removeAllItems();
        comboBox.hidePopup();
        if (StringUtils.isBlank(name)){

            return;
        }

        String sql = "select ID, NAME from member where name like ?";
        try {
            QueryRunner runner = new QueryRunner(DbUtil.getDataSource());
            java.util.List<Member> list = runner.query(sql, new BeanListHandler<>(Member.class), "%" + name + "%");
            if (list.size() > 0) {
                for (Member member : list) {

                    comboBox.addItem(member);
                }
            }
            comboBox.showPopup();
            comboBox.setSelectedItem(null);
            textField.setText(name);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }
}
