package com.diyiliu.view;

import com.diyiliu.util.UIHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Description: MainFrame
 * Author: DIYILIU
 * Update: 2017-07-18 09:54
 */
public class MainFrame extends JFrame {

    private JPanel plInput;

    private JLabel lbName, lbTel, lbAddress,
            lbQuality, lbPrice, lbMoney;

    private JTextField tfName, tfTel, tfAddress,
            tfQuality, tfPrice, tfMoney;

    private JButton btClear, btInput;

    public MainFrame(){
        UIHelper.beautify();

        lbName = new JLabel("姓名");
        lbTel = new JLabel("电话");
        lbAddress = new JLabel("地址");
        lbQuality = new JLabel("质量");
        lbPrice = new JLabel("单价");
        lbMoney = new JLabel("金额");

        tfName = new JTextField();
        tfTel = new JTextField();
        tfAddress = new JTextField();
        tfQuality = new JTextField();
        tfPrice = new JTextField();
        tfMoney = new JTextField();

        btClear = new JButton("清空");
        btInput = new JButton("录入");

        plInput = new JPanel();
        GroupLayout layout = new GroupLayout(plInput);
        plInput.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().addComponent(lbName).addComponent(lbQuality));
        hGroup.addGroup(layout.createParallelGroup().addComponent(tfName).addComponent(tfQuality));
        hGroup.addGroup(layout.createParallelGroup().addComponent(lbTel).addComponent(lbPrice));
        hGroup.addGroup(layout.createParallelGroup().addComponent(tfTel).addComponent(tfPrice));
        hGroup.addGroup(layout.createParallelGroup().addComponent(lbAddress).addComponent(lbMoney));
        hGroup.addGroup(layout.createParallelGroup().addComponent(tfAddress).addComponent(tfMoney));
        hGroup.addGroup(layout.createParallelGroup().addComponent(btClear).addComponent(btInput));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lbName)
                .addComponent(tfName)
                .addComponent(lbTel)
                .addComponent(tfTel)
                .addComponent(lbAddress)
                .addComponent(tfAddress)
                .addComponent(btClear));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lbQuality)
                .addComponent(tfQuality)
                .addComponent(lbPrice)
                .addComponent(tfPrice)
                .addComponent(lbMoney)
                .addComponent(tfMoney)
                .addComponent(btInput));
        layout.setVerticalGroup(vGroup);

        this.add(plInput);

        this.setSize(760, 600);
        UIHelper.setCenter(this);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {

        new MainFrame();
    }
}
