package presentation;

import javax.swing.*;
import java.awt.*;

public class MainUI {
    private JFrame frame;           //窗口

    public MainUI(){
        initialUI();
    }

    private void initialUI(){
        //添加组件，画板、按钮等
        JPanel panel = new JPanel(new FlowLayout());

        DrawingBoard board = new DrawingBoard();
        panel.add(board);
        frame = new JFrame("画板");
        frame.setSize(1280, 720);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
