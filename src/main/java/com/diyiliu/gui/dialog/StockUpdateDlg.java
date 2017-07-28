package com.diyiliu.gui.dialog;

import com.diyiliu.bean.Stock;
import com.diyiliu.util.Constants;
import com.diyiliu.util.DbUtil;
import com.diyiliu.util.UIHelper;
import com.sun.deploy.panel.NumberDocument;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class StockUpdateDlg extends JDialog {
    private JPanel contentPane;
    private JButton btSave;
    private JButton btCancel;
    private JTextField tfName;
    private JTextField tfTel;
    private JTextField tfGross;
    private JTextField tfTare;
    private JTextField tfSuttle;
    private JTextField tfPrice;
    private JTextField tfMoney;
    private JComboBox cbxState;
    private JTextField tfTime;
    private String inNo;

    private boolean newStock = true;
    public StockUpdateDlg() {
        this.setTitle("入库");

        tfName.setEditable(false);
        tfTel.setEditable(false);
        tfGross.setEditable(false);
        tfTare.setDocument(new NumberDocument());
        tfTare.setEditable(false);
        tfSuttle.setEditable(false);
        tfPrice.setEditable(false);
        tfMoney.setEditable(false);
        tfTime.setEditable(false);

        initCmbState(cbxState);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btSave);

        tfTare.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int gross = Integer.valueOf(tfGross.getText());

                int tare = 0;
                if (StringUtils.isNotBlank(tfTare.getText())) {
                    tare = Integer.valueOf(tfTare.getText().trim());
                }

                if (tare > gross) {
                    tfTare.setForeground(Color.RED);
                    btSave.setEnabled(false);
                } else {
                    btSave.setEnabled(true);
                    tfTare.setForeground(tfGross.getForeground());

                    int suttle = gross - tare;
                    tfSuttle.setText(String.valueOf(suttle));

                    BigDecimal price = new BigDecimal(tfPrice.getText());

                    BigDecimal result = price.multiply(new BigDecimal(suttle));
                    double money = result.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    // 不显示小数
                    String sMoney = String.valueOf(money);
                    tfMoney.setText(sMoney.substring(0, sMoney.indexOf(".")));
                }

            }
        });

        btSave.addActionListener((e) ->
                onOK()
        );

        btCancel.addActionListener((e) ->
                onCancel()
        );

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction((e) -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        UIHelper.setCenter(this);
    }

    private void onOK() {
        if (StringUtils.isBlank(tfGross.getText()) || StringUtils.isBlank(tfPrice.getText())) {
            JOptionPane.showMessageDialog(this, "毛重或价格不允许为空!", "提示", JOptionPane.WARNING_MESSAGE);
        }
        try {
            QueryRunner runner = new QueryRunner(DbUtil.getDataSource());

            Integer tare = null;
            Integer suttle = null;
            Double money = null;
            if (StringUtils.isNotBlank(tfTare.getText())) {
                tare = Integer.valueOf(tfTare.getText());
                suttle = Integer.valueOf(tfSuttle.getText());
                money = Double.valueOf(tfMoney.getText());
            }

            /*String sql;
            Object[] values;
            if (StringUtils.isNotBlank(tfTare.getText()) && cbxState.getSelectedIndex() > -1) {
                Constants.StockState stockState = (Constants.StockState) cbxState.getSelectedItem();
                sql = "update stock set gross=?, tare=?, suttle=?, price=?, money=?, state=? where in_no=?";
                values = new Object[]{tfGross.getText(), tare, suttle,
                        tfPrice.getText(), money, stockState.getIndex(), inNo};
            } else {
                sql = "update stock set gross=?, tare=?, suttle=?, price=?, money=? where in_no=?";
                values = new Object[]{tfGross.getText(), tare, suttle,
                        tfPrice.getText(), money, inNo};
            }*/

            StringBuffer strb = new StringBuffer("update stock set gross=?, tare=?, suttle=?, price=?, money=?");
            java.util.List param = new ArrayList();
            param.add(tfGross.getText());
            param.add(tare);
            param.add(suttle);
            param.add(tfPrice.getText());
            param.add(money);

            /**
             * 新单入库
             */
            if (newStock && StringUtils.isNotEmpty(tfTare.getText())){

                strb.append(", in_time=?, state=?");
                param.add(new Date());
                param.add(Constants.StockState.WAIT.getIndex());
            }

            if (cbxState.getSelectedIndex() > -1 && StringUtils.isNotEmpty(tfTare.getText())){
                Constants.StockState stockState = (Constants.StockState) cbxState.getSelectedItem();
                strb.append(", state=?");
                param.add(stockState.getIndex());

                if (stockState.getIndex() == Constants.StockState.PAID.getIndex()){
                    strb.append(", pay_time=?");
                    param.add(new Date());
                }
            }
            strb.append(" where in_no=?");
            param.add(inNo);

            int num = runner.update(strb.toString(), param.toArray());
            if (num == 1) {
                dispose();
                JOptionPane.showMessageDialog(this, "操作成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "操作失败!", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void fillData(Stock stock) {

        inNo = stock.getInNo();
        tfName.setText(stock.getMember().getName());
        tfTel.setText(stock.getMember().getTel());
        tfGross.setText(String.valueOf(stock.getGross()));

        if (stock.getTare() != null) {
            tfTare.setText(String.valueOf(stock.getTare()));
            tfSuttle.setText(String.valueOf(stock.getSuttle()));
            tfMoney.setText(String.valueOf(stock.getMoney()));
            newStock = false;
        }

        tfPrice.setText(String.valueOf(stock.getPrice()));

        if (stock.getState() != null) {
            int state = stock.getState();
            for (int i = 0; i < cbxState.getItemCount(); i++) {

                Constants.StockState s = (Constants.StockState) cbxState.getItemAt(i);
                if (state == s.getIndex()) {
                    cbxState.setSelectedIndex(i);
                }
            }
        }

        cbxState.setSelectedItem(0);

        tfTime.setText(String.format("%1$tF %1$tT", stock.getCreateTime()));
    }

    public void initCmbState(JComboBox comboBox) {
        comboBox.addItem(Constants.StockState.DEBT);
        comboBox.addItem(Constants.StockState.PAID);

        comboBox.setSelectedItem(null);
    }

    public JTextField getTfName() {
        return tfName;
    }

    public JTextField getTfTel() {
        return tfTel;
    }

    public JTextField getTfGross() {
        return tfGross;
    }

    public JTextField getTfTare() {
        return tfTare;
    }

    public JTextField getTfSuttle() {
        return tfSuttle;
    }

    public JTextField getTfPrice() {
        return tfPrice;
    }

    public JTextField getTfMoney() {
        return tfMoney;
    }

    public JComboBox getCbxState() {
        return cbxState;
    }

    public JTextField getTfTime() {
        return tfTime;
    }

    public static void main(String[] args) {
        UIHelper.beautify();
        StockUpdateDlg dialog = new StockUpdateDlg();
        dialog.setVisible(true);
        System.exit(0);
    }
}
