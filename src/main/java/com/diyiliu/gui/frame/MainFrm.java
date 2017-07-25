package com.diyiliu.gui.frame;

import com.diyiliu.bean.Member;
import com.diyiliu.bean.Stock;
import com.diyiliu.gui.dialog.StockUpdateDlg;
import com.diyiliu.listener.MemberPrompt;
import com.diyiliu.tmodel.StockModel;
import com.diyiliu.util.CommonUtil;
import com.diyiliu.util.Constants;
import com.diyiliu.util.DbUtil;
import com.diyiliu.util.UIHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;

/**
 * Description: MainFrm
 * Author: DIYILIU
 * Update: 2017-07-20 15:40
 */
public class MainFrm extends JFrame implements ActionListener, KeyListener {
    private JTextField tfGross;
    private JTextField tfTel;
    private JTextField tfPrice;
    private JButton btClear;
    private JButton btSave;
    private JPanel plContainer;
    private JTable tbStock;
    private JComboBox cbName;
    private JButton btUpdate;
    private JTextField tfName;

    private StockModel stockModel;

    public MainFrm() {

        this.setContentPane(plContainer);

        cbName.setEditable(true);
        cbName.setUI(new BasicComboBoxUI() {

            @Override
            protected JButton createArrowButton() {
                return null;
            }
        });

        stockModel = new StockModel();
        tbStock.setModel(stockModel);

        tbStock.getColumnModel().getColumn(0).setPreferredWidth(125);

        // 添加监听
        btSave.addActionListener(this);
        btSave.setActionCommand("save");

        btUpdate.addActionListener(this);
        btUpdate.setActionCommand("update");

        btClear.addActionListener(this);
        btClear.setActionCommand("clear");


        tfPrice.addKeyListener(this);

        tbStock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    StockUpdateDlg updateDlg = new StockUpdateDlg();
                    toUpdate(updateDlg, false);
                }
            }
        });


        tfName = (JTextField) cbName.getEditor().getEditorComponent();
        tfName.addKeyListener(new MemberPrompt(cbName, stockModel));

        this.setSize(800, 520);
        // 设置窗口居中
        UIHelper.setCenter(this);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.requestFocus();
    }


    public static void main(String[] args) {
        UIHelper.beautify();
        new MainFrm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if ("save".equals(e.getActionCommand())) {
            doSave();
        } else if ("clear".equals(e.getActionCommand())) {

            cbName.removeAllItems();
            tfTel.setText("");
            tfGross.setText("");
            tfPrice.setText("");

            stockModel.refresh();
        }else if ("update".equals(e.getActionCommand())){

            int count = tbStock.getSelectedRowCount();
            if (count == 1){
                StockUpdateDlg updateDlg = new StockUpdateDlg();
                toUpdate(updateDlg, true);
            }else {

                JOptionPane.showMessageDialog(this, "请选择一条需要修改的信息!", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (KeyEvent.VK_ENTER == e.getKeyCode()) {

            doSave();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public void doSave() {

        String name = tfName.getText().trim();
        String tel = tfTel.getText().trim();

        String gross = tfGross.getText().trim();
        String price = tfPrice.getText().trim();

        if (StringUtils.isBlank(name)) {
            tfName.grabFocus();
            return;
        }

        if (StringUtils.isBlank(gross)) {
            tfGross.grabFocus();
            return;
        }

        if (StringUtils.isBlank(price)) {
            tfPrice.grabFocus();
            return;
        }

        int mid;
        try {
            QueryRunner runner = new QueryRunner(DbUtil.getDataSource());
            int index = cbName.getSelectedIndex();
            if (index < 0) {
                String sql = "insert into member(NAME, TEL, CREATE_TIME) values (?, ?, ?)";
                BigInteger id = runner.insert(sql, new ScalarHandler<BigInteger>(), new Object[]{name, tel, new Date()});
                mid = id.intValue();
            } else {
                Member member = (Member) cbName.getSelectedItem();
                mid = member.getId();
            }

            String sql = "insert into stock(IN_NO, MEMBER_ID, GROSS, PRICE, CREATE_TIME)values(?, ?, ?, ?, ?)";
            int result = runner.update(sql, new Object[]{CommonUtil.createID(), mid, gross, price, new Date()});
            if (result == 1) {
                JOptionPane.showMessageDialog(this, "操作成功!", "入库提醒", JOptionPane.INFORMATION_MESSAGE);

                 sql = "SELECT t.IN_NO,m.`NAME`,t.GROSS,t.TARE,t.SUTTLE,t.PRICE,t.MONEY,t.CREATE_TIME,t.STATE from stock t " +
                        "INNER JOIN member m on m.ID = t.MEMBER_ID and m.`NAME`=?";

                stockModel.refresh(sql, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "操作失败!", "入库提醒", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void toUpdate(StockUpdateDlg updateDlg, Boolean isSuper){
        int row = tbStock.getSelectedRow();
        Object[] data = stockModel.getRowData(row);
        String name = (String) data[1];

        Member member = null;
        try {
            String sql = "select id, name, tel, address from member where name=?";
            QueryRunner runner = new QueryRunner(DbUtil.getDataSource());
            member = runner.query(sql, new BeanHandler<>(Member.class), name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stock stock = new Stock();
        stock.setInNo((String) data[0]);
        stock.setMember(member);
        stock.setGross((Integer) data[2]);
        stock.setTare(data[3] == null? null: (Integer) data[3]);
        stock.setSuttle(data[4] == null? null: (Integer) data[4]);
        stock.setPrice(new BigDecimal(String.valueOf(data[5])));
        stock.setMoney(data[6] == null? null: new BigDecimal(String.valueOf(data[6])));
        stock.setCreateTime((Date) data[7]);

        if (data[3] == null){
            updateDlg.getTfTare().setEditable(true);
            updateDlg.getCbxState().setEnabled(false);
            updateDlg.getTfTare().grabFocus();
        }else {
            updateDlg.getCbxState().grabFocus();
        }

        if (data[8] != null) {
            stock.setState((Integer) data[8]);
        }

        if (isSuper){

            updateDlg.getTfGross().setEditable(true);
            updateDlg.getTfTare().setEditable(true);
            updateDlg.getTfPrice().setEditable(true);
        }

        updateDlg.fillData(stock);

        updateDlg.setVisible(true);

        if (StringUtils.isNotBlank(tfName.getText()))
        {
            String sql = "SELECT t.IN_NO,m.`NAME`,t.GROSS,t.TARE,t.SUTTLE,t.PRICE,t.MONEY,t.CREATE_TIME,t.STATE from stock t " +
                    "INNER JOIN member m on m.ID = t.MEMBER_ID and m.`NAME`=?";
            stockModel.refresh(sql, tfName.getText().trim());
        }else {

            stockModel.refresh();
        }
    }
}
