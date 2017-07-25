import com.diyiliu.util.UIHelper;

import javax.swing.*;

/**
 * Description: TestFrame
 * Author: DIYILIU
 * Update: 2017-07-17 16:50
 */
public class TestFrame extends JFrame {


    private JTable table1;
    private JTextField tfName;
    private JTextField tfTel;
    private JTextField tfAddress;
    private JTextField tfQuality;
    private JTextField tfPrice;
    private JTextField tfMoney;
    private JButton btInput;
    private JButton btClear;
    private JButton button3;
    private JButton button4;
    private JPanel plContain;
    private JPanel plHeader;
    private JLabel lbName;


    public TestFrame() {
        this.add(plContain);
        this.pack();
        // 设置窗口居中
        UIHelper.setCenter(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // 设置样式
            String ui = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(ui);

        } catch (Exception e) {
            e.printStackTrace();
        }
        new TestFrame();
    }
}
