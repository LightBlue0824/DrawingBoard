package util;

import java.util.LinkedList;

public class MyShape {
    private LinkedList<Point> pointList = new LinkedList<>();      //记录point

    /**
     * 添加一个point
     * @param p
     */
    public void addPoint(Point p){
        pointList.add(p);
    }

    /**
     * 返回图形的点列表
     * @return 图形的点列表
     */
    public LinkedList<Point> getPointList(){
        return new LinkedList<>(pointList);
    }
}
