package com.diyiliu.view;

import com.diyiliu.util.UIHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description: LoginDialog
 * Author: DIYILIU
 * Update: 2017-07-17 15:42
 */
public class LoginDialog extends JFrame implements ActionListener {

    private JLabel lbUsername, lbPassword;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btLogin, btCancel;

    private JPanel plContain;
    public LoginDialog() {
        lbUsername = new JLabel("用户名 ");
        lbPassword = new JLabel("密  码 ");

        tfUsername = new JTextField();
        tfPassword = new JPasswordField();

        btLogin = new JButton("  登录  ");
        btCancel = new JButton("  取消  ");

        plContain = new JPanel();
        GroupLayout layout = new GroupLayout(plContain);
        plContain.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(20);
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(lbUsername).addComponent(lbPassword));
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tfUsername)
                .addComponent(tfPassword)
                .addGroup(layout.createSequentialGroup().addComponent(btLogin).addGap(10).addComponent(btCancel)));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(30);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lbUsername).addComponent(tfUsername));
        vGroup.addGap(15);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lbPassword).addComponent(tfPassword));
        vGroup.addGap(20);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(btLogin).addComponent(btCancel));
        layout.setVerticalGroup(vGroup);

        this.add(plContain);


        // 添加监听
        btCancel.addActionListener(this);
        btCancel.setActionCommand("cancel");

        this.setSize(255, 180);
        UIHelper.setCenter(this);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("cancel")){
            this.dispose();
        }
    }

    public static void main(String[] args) {

        UIHelper.beautify();
        new LoginDialog();
    }
}
