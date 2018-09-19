package presentation;

import logic.LogicController;
import logicService.ShapeProcessService;
import util.MyShape;
import util.Point;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class DrawingBoard extends JPanel{

//    private boolean isMoving = false;           //记录是否在按住拖动鼠标
    private ArrayList<MyShape> shapeArrayList = new ArrayList<>();
    private MyShape curShape = new MyShape();   //记录当前正在画的图形

    private int x1,y1;          //记录上一个点的坐标
    private Graphics2D g2;

    private ShapeProcessService shapeProcess = LogicController.getShapeProcessImpl();       //图形处理的接口

    /**
     * 构造时初始化界面
     */
    public DrawingBoard(){
        initialBoard();

    }

    /**
     * 初始化，设置大小、边框、背景等
     */
    private void initialBoard(){

        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        Border border = BorderFactory.createLineBorder(Color.black);
        this.setBorder(border);

        this.addMouseListener(new BoardMouseListener());
        this.addMouseMotionListener(new BoardMouseMotionListener());
    }

    /**
     * 重写父类paint，实现画图
     * @param g
     */
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//
//        System.out.println("here");
//        g2 = (Graphics2D)g;
////        Graphics2D g2 = (Graphics2D)g;
//        g2.setStroke(new BasicStroke(2.0f));
////        g.drawLine(10,10,30,30);
//
////        //画出每个图形
////        for(int i = 0; i < shapeArrayList.size(); i++){
////            LinkedList<Point> pointList = shapeArrayList.get(i).getPointList();
////            //迭代画出点
////            Iterator<util.Point> it =  pointList.iterator();
////            if(it.hasNext()){
////                Point startP = it.next();       //第一个起点
////
////                System.out.println("1"+pointList.size());
////                while(it.hasNext()){
////                    Point endP = it.next();
////                    System.out.println("2");
////
////                    System.out.println(startP.getX()+" "+startP.getY()+" "+endP.getX()+" "+endP.getY());
////                    g2.drawLine(startP.getX(), startP.getY(), endP.getX(), endP.getY());
////                    startP = endP;          //终点为下一次的起点
////                }
////            }
////        }
//    }

    //-------------------------------
    //-------------------------------

    /**
     * 画板的鼠标监听类
     */
    private class BoardMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            //按下鼠标时，设置初始坐标
            g2 = (Graphics2D)getGraphics();
            g2.setStroke(new BasicStroke(2f));
            x1 = e.getX();
            y1 = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //鼠标释放时
            //自动补全
//            Point firstPoint = curShape.getPointList().getFirst();
//            Point lastPoint = curShape.getPointList().getLast();
            if(curShape.getPointList().size() == 0){        //如果是单机鼠标,没有记录点
                return;
            }
            Point firstPoint = curShape.getPointList().get(0);
            Point lastPoint = new Point(e.getX(), e.getY());
            g2.drawLine(firstPoint.getX(), firstPoint.getY(), lastPoint.getX(), lastPoint.getY());

            //记录为一个图形
            shapeArrayList.add(curShape);

            //释放时进行识别
            String recognizeResult = shapeProcess.recognizeShape(curShape);

            curShape = new MyShape();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * 画板的鼠标行为监听类
     */
    private class BoardMouseMotionListener implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent e) {
            int xCur = e.getX();
            int yCur = e.getY();
//            System.out.println(xCur + "  " + yCur);
            Point curPoint = new Point(xCur, yCur);
            curShape.addPoint(curPoint);

//            System.out.println(x1 + " " + y1+" "+xCur + "  " + yCur);
            g2.drawLine(x1, y1, xCur, yCur);
            x1 = xCur;          //记录为起点
            y1 = yCur;

//            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}
