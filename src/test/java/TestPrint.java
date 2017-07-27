import org.junit.Test;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: TestPrint
 * Author: DIYILIU
 * Update: 2017-07-26 15:24
 */
public class TestPrint {


    @Test
    public void test(){
        InputStream textStream = null;
        try {
            textStream = ClassLoader.getSystemResourceAsStream("test.pdf");
            //String printStr = "打印测试内容";// 获取需要打印的目标文本
            if (textStream != null) // 当打印内容不为空时
            {
                // 指定打印输出格式
                DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;//SERVICE_FORMATTED.PRINTABLE
                // 定位默认的打印服务
                PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
                // 创建打印作业
                DocPrintJob job = printService.createPrintJob();
                // 设置打印属性
                PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
                // 设置纸张大小,也可以新建MediaSize类来自定义大小
                pras.add(MediaSizeName.ISO_A4);
                DocAttributeSet das = new HashDocAttributeSet();
                // 指定打印内容
                Doc doc = new SimpleDoc(textStream, flavor, das);
                // 不显示打印对话框，直接进行打印工作
                job.print(doc, pras); // 进行每一页的具体打印操作
            } else {
                // 如果打印内容为空时，提示用户打印将取消
                JOptionPane.showConfirmDialog(null,
                        "Sorry, Printer Job is Empty, Print Cancelled!",
                        "Empty", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (textStream != null){
                try {
                    textStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test2() {

        int height = 175 + 3 * 15 + 20;

        // 通俗理解就是书、文档
        Book book = new Book();

        // 打印格式
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);

        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(230, height);
        p.setImageableArea(5, -20, 230, height + 20);
        pf.setPaper(p);

        // 把 PageFormat 和 Printable 添加到书中，组成一个页面
        book.append(new Prient(), pf);

        // 获取打印服务对象
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(book);

        try {
            //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
            boolean a=job.printDialog();
            if(a)
            {
                job.print();
            } else{
                job.cancel();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            System.out.println("================打印出现异常");
        }
    }
}


class Prient implements Printable {

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Default", Font.PLAIN, 14));
        g2d.drawString("等位排单号", 50, 10);
        g2d.drawString("-------------------------------------", 7, 20);
        g2d.drawString("手机号码：" + "11111111111", 7, 35);
        g2d.drawString("领号日期：" + "11111", 7, 65);
        g2d.drawString("-------------------------------------", 7, 80);
        g2d.setFont(new Font("Default", Font.PLAIN, 25));
        g2d.drawString("小号", 7, 105);
        g2d.setFont(new Font("Default", Font.PLAIN, 14));
        g2d.drawString("您之前还有" + 5 + "桌客人在等待", 7, 130);
        g2d.drawString("-------------------------------------", 7, 145);
        g2d.drawString("*打印时间:" + "1111" + "*", 7, 160);
        g2d.drawString("店名：" + "11", 7, 175);

        return PAGE_EXISTS;
    }
}