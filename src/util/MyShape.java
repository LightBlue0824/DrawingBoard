package util;

import java.util.ArrayList;

public class MyShape {
//    private LinkedList<Point> pointList = new LinkedList<>();      //记录point
    private ArrayList<Point> pointList = new ArrayList<>();      //记录point
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

    public void setType(String t){
        this.type = t;
    }

    public String getType(){
        return this.type;
    }
}
