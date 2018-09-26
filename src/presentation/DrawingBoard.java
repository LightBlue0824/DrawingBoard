package presentation;

import logic.LogicController;
import logicService.BoardIOLogicService;
import logicService.ShapeProcessService;
import util.MyBoard;
import util.MyShape;
import util.Point;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

class DrawingBoard extends JPanel{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

//    private boolean isMoving = false;           //记录是否在按住拖动鼠标
    private MyBoard board = new MyBoard();
    private MyShape curShape = new MyShape();   //记录当前正在画的图形

    private int x1,y1;          //记录上一个点的坐标
    private Graphics2D g2 = (Graphics2D)getGraphics();

    private ShapeProcessService shapeProcess = LogicController.getShapeProcessImpl();       //图形处理的接口
    private BoardIOLogicService boardIO = LogicController.getBoardIOLogicImpl();      //画板IO的接口


    /**
     * 构造时初始化界面
     */
    DrawingBoard(){
        initialBoard();

    }

    /**
     * 初始化，设置大小、边框、背景等
     */
    private void initialBoard(){

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.white);
        Border border = BorderFactory.createLineBorder(Color.black);
        this.setBorder(border);

        this.addMouseListener(new BoardMouseListener());
        this.addMouseMotionListener(new BoardMouseMotionListener());
    }

    /**
     * 画一个标准图形，包括显示类型
     * @param shape
     */
    private void drawStdShape(MyShape shape){
        ArrayList<Point> vertexList = shape.getVertexList();
        if(shape.getType().equals("圆")){
            int radius = vertexList.get(0).getX();        //半径
            Point centerPoint = shape.getCenterPoint();
            g2.drawOval(centerPoint.getX()-radius,centerPoint.getY()-radius,radius*2,radius*2);
        }
        else{
            for(int i = 0; i < vertexList.size(); i+=2){
                Point start = vertexList.get(i);
                Point end = vertexList.get(i+1);
                g2.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            }
        }

        //显示类型
        Point centerPoint = shape.getCenterPoint();
        g2.drawString(shape.getType(), centerPoint.getX(), centerPoint.getY());
    }

    /**
     * 清空画板
     */
    void clearBoard(){
        this.board = new MyBoard();
        this.curShape = new MyShape();   //记录当前正在画的图形

        g2 = (Graphics2D)getGraphics();
        g2.setColor(Color.white);
        g2.fillRect(1, 1, WIDTH-2, HEIGHT-2);
        g2.setColor(Color.black);
    }

    /**
     * 保存画板
     * @param filename
     * @return
     */
    boolean saveBoard(String filename){
        boolean result = boardIO.saveBoard(board, filename);
        return result;
    }

    /**
     * 确认结束图形
     */
    void sureToFinish(){
        if(g2 == null)  return;
        //记录为一个图形
        board.addShape(curShape);
//            shapeArrayList.add(curShape);

        //进行识别
        String recognizeResult = shapeProcess.recognizeShape(curShape);
        curShape.setType(recognizeResult);      //记录，还原时不需再识别
        //进行规范化
        ArrayList<Point> newVertex = shapeProcess.standardizeShape(curShape);
        curShape.setVertexList(newVertex);
        //擦除旧轨迹
        int toEraseWidth = curShape.getMaxX() - curShape.getMinX() + 4;
        int toEraseHeight = curShape.getMaxY() - curShape.getMinY() + 4;
        g2.setColor(Color.white);
        g2.fillRect(curShape.getMinX()-2, curShape.getMinY()-2, toEraseWidth, toEraseHeight);
        g2.setColor(Color.black);
        //画出新图形
        drawStdShape(curShape);

        //开始新图形
        curShape = new MyShape();
    }

    /**
     * 加载打开的画板
     * @param boardToLoad 需要加载的画板
     */
    void loadBoard(MyBoard boardToLoad){
        clearBoard();       //清空画板

        g2.setStroke(new BasicStroke(2f));
        g2.setColor(Color.black);

        this.board = boardToLoad;       //记录为当前画板
        ArrayList<MyShape> shapes = boardToLoad.getShapeList();
        for(int i = 0; i < shapes.size(); i++){
            drawStdShape(shapes.get(i));
        }
    }


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

            curShape.addVertex(new Point(x1,y1));       //添加线的起点
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //添加线的结束点
            curShape.addVertex(new Point(e.getX(), e.getY()));

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
