package presentation;

import dataService.BoardIODataService;
import logic.LogicController;
import logicService.BoardIOLogicService;
import util.MyBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    private JFrame frame;           //窗口
    private DrawingBoard drawingBoard;
    private JTextField filenameField;
    JComboBox<String> filenameListBox;

    private BoardIOLogicService boardIOLogic = LogicController.getBoardIOLogicImpl();

    public MainUI(){
        initialUI();
    }

    private void initialUI(){
        //添加组件，画板、按钮等
        JPanel panel = new JPanel(new FlowLayout());
        Box vBox = Box.createVerticalBox();

        drawingBoard = new DrawingBoard();
//        DrawingBoard drawingBoard = new DrawingBoard();
        panel.add(drawingBoard);
        frame = new JFrame("画板");
        frame.setSize(1280, 720);
        frame.add(panel);
        panel.add(vBox);

        JLabel label_filename = new JLabel("文件名:");
        vBox.add(label_filename);

        filenameField = new JTextField("untitled");
        filenameField.setPreferredSize(new Dimension(100, 30));
        vBox.add(filenameField);

        JButton button_finish = new JButton("确认结束");
        button_finish.addActionListener(new FinishBtnActionListener());
        vBox.add(button_finish);

        JButton button_clear = new JButton("清空");
        button_clear.addActionListener(new ClearBtnActionListener());
        vBox.add(button_clear);

        JButton button_save = new JButton("保存");
        button_save.addActionListener(new SaveBtnActionListener());
        vBox.add(button_save);

        JLabel label_openFile = new JLabel("打开文件:");
        vBox.add(label_openFile);

        filenameListBox = new JComboBox<>(boardIOLogic.loadFilesList());
        vBox.add(filenameListBox);


        JButton button_open = new JButton("打开已选文件");
        button_open.addActionListener(new OpenBtnActionListener());
        vBox.add(button_open);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //-------------------------------------
    //-------------------------------------

    /**
     * 确认结束按钮的监听类
     */
    private class FinishBtnActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingBoard.sureToFinish();
        }
    }
    /**
     * 清空按钮的监听类
     */
    private class ClearBtnActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingBoard.clearBoard();
        }
    }
    /**
     * 保存按钮的监听类
     */
    private class SaveBtnActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String filename = filenameField.getText();
            boolean result = drawingBoard.saveBoard(filename);
            System.out.println(result);
            if(result){     //保存成功，刷新文件列表
                filenameListBox.addItem(filename);
            }
        }
    }
    /**
     * 打开按钮的监听类
     */
    private class OpenBtnActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String filename = (String)filenameListBox.getSelectedItem();
            MyBoard boardToOpen = boardIOLogic.loadBoard(filename);
            filenameField.setText(filename);
            drawingBoard.loadBoard(boardToOpen);
        }
    }
}
