package util;

import java.io.Serializable;
import java.util.ArrayList;

public class MyShape implements Serializable {
//    private LinkedList<Point> pointList = new LinkedList<>();      //记录point
    private ArrayList<Point> pointList = new ArrayList<>();      //记录point
    private ArrayList<Point> vertexList = new ArrayList<>();     //记录线的端点，起点端点均记录
    private int minX = 0, maxX = 0, minY = 0, maxY = 0;         //方便计算中心
    private String type = "未识别";

    /**
     * 添加一个point
     * @param p 点
     */
    public void addPoint(Point p){
        pointList.add(p);
        checkMinAndMax(p.getX(), p.getY());
    }

    /**
     * 返回图形的点列表
     * @return 图形的点列表
     */
//    public LinkedList<Point> getPointList(){
//        return new LinkedList<>(pointList);
//    }
    public ArrayList<Point> getPointList(){
        return new ArrayList<>(pointList);
    }

    /**
     * 添加一个线的端点
     * @param p 线端点
     */
    public void addVertex(Point p){
        this.vertexList.add(p);
    }

    /**
     * 设置端点列表
     * @param vertexList 端点列表
     */
    public void setVertexList(ArrayList<Point> vertexList){
        this.vertexList = vertexList;
    }

    /**
     * 返回图形的线端点列表
     * @return
     */
    public ArrayList<Point> getVertexList(){
        return new ArrayList<>(vertexList);
    }

    /**
     * 得到图形的中心点
     * @return 中心点
     */
    public Point getCenterPoint(){
        int centerX = (minX+maxX)/2;
        int centerY = (minY+maxY)/2;
        return new Point(centerX, centerY);
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }

    public void setType(String t){
        this.type = t;
    }

    public String getType(){
        return this.type;
    }

    /**
     * 检查并记录最小最大值
     * @param x 当前点x
     * @param y 当前点y
     */
    private void checkMinAndMax(int x, int y){
        if(pointList.size() == 1){
//            System.out.println("here");
            minX = x;
            minY = y;
            maxX = x;
            maxY = y;
            return;
        }
        if(x < minX){
            minX = x;
        }
        else if(x > maxX){
            maxX = x;
        }
        if(y < minY){
            minY = y;
        }
        else if(y > maxY){
            maxY = y;
        }
    }
}
