package com.diyiliu.gui.frame;

import com.diyiliu.util.UIHelper;

import javax.swing.*;

/**
 * Description: LoginFrm
 * Author: DIYILIU
 * Update: 2017-07-18 14:08
 */
public class LoginFrm extends JFrame{
    private JPanel plContainer;
    private JTextField tfUsername;
    private JCheckBox cbRemember;
    private JButton btLogin;
    private JPasswordField tfPassword;


    public LoginFrm(){

        this.setContentPane(plContainer);
        this.setSize(300, 240);
        // 设置窗口居中
        UIHelper.setCenter(this);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        UIHelper.beautify();
        new LoginFrm();
    }
}
