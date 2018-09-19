package logic.serviceImpl;

import logicService.ShapeProcessService;
import util.MyShape;
import util.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class ShapeProcessImpl implements ShapeProcessService {
    @Override
    public String recognizeShape(MyShape shape) {
        String result;
        result = judgeShape(shape);

        return result;
    }

    /**
     * 计算图形的面积
     * @param shape
     * @return
     */
    private double calcArea(MyShape shape){
        ArrayList<Point> points = shape.getPointList();
        int i, j;
        int n = points.size();
        double area = 0;

        //叉乘法计算面积
        for(i = 0; i < n; i++){
            j  = (i+1)%n;

            area += points.get(i).getX() * points.get(j).getY();
            area -= points.get(i).getY() * points.get(j).getX();
        }
        area /= 2.0;
        area = Math.abs(area);      //跟点的顺序有关，取正值
//        System.out.println(area);
        return area;
    }

    /**
     * 判断图形的形状
     * @param shape
     * @return
     */
    private String judgeShape(MyShape shape){
        String result = "其他图形";

        ArrayList<Point> points = shape.getPointList();

        double area = calcArea(shape);
        double circleArea = calcRectArea(shape);

        double ratio = area / circleArea;

        //0.785为内切圆的比例，0.5为内接三角形的比例
        if(ratio <= 1 && ratio > 0.82){
            result = "长方形";
            //判断是否是正方形
            int width = shape.getMaxX() - shape.getMinX();
            int height = shape.getMaxY() - shape.getMinY();
            double whRatio = (width*1.0)/height;        //长宽比
            if(whRatio > 0.9 && whRatio < 1.1){
                result = "正方形";
            }
        }
        else if(ratio > 0.65){
            result = "圆";
        }
        else if(ratio > 0.05){
            result = "三角形";
        }
        else{
            result = "线";
        }

        System.out.println(result);
        return result;
    }

    /**
     * 计算外接矩形的面积（近似外接）
     * @param shape
     * @return
     */
    private double calcRectArea(MyShape shape) {
        double area = 0;

        int width = shape.getMaxX() - shape.getMinX();
        int height = shape.getMaxY() - shape.getMinY();

        area = width * height;
        return area;
    }
//    /**已弃用
//     * 判断顶点是否是误差导致的
//     * @param p
//     * @param vertexes
//     * @return
//     */
//    private boolean isErrorRepeat(Point p, ArrayList<Point> vertexes){
//        boolean result = false;
//        int error = 10;          //误差为5
//
//        for(int i = 0; i < vertexes.size(); i++){
//            Point curV = vertexes.get(i);
//            if((p.getX() < curV.getX()+error && p.getX() > curV.getX()-error)
//                && (p.getY() < curV.getY()+error && p.getY() > curV.getY()-error)){         //在误差范围内
//                result = true;
//            }
//        }
//        return result;
//    }

    //    /**已弃用
//     * 计算以图形中心为圆心，最长半径圆的面积
//     * @param shape
//     * @return
//     */
//    private double calcCircleArea(MyShape shape){
//        ArrayList<Point> points = shape.getPointList();
//        double area = 0;
//        Point centerPoint = shape.getCenterPoint();         //图形中心
//        double r2_max = 0;             //半径的平方
//
//        System.out.println(centerPoint.getX() + "   " + centerPoint.getY());
//        for(int i = 0; i < points.size(); i++){
//            Point curPoint = points.get(i);
//            int x = curPoint.getX();
//            int y = curPoint.getY();
//            if(x == shape.getMaxX() || x == shape.getMinX()
//                    || y == shape.getMaxY() || y == shape.getMinY()){       //最长半径只会在有最值坐标的点上出现
//                double r2 = (x-centerPoint.getX())*(x-centerPoint.getX()) + (y-centerPoint.getY())*(y-centerPoint.getY());
////                System.out.println(x + " " + (x-centerPoint.getX()));
////                System.out.println(y + " " + (y-centerPoint.getY()));
//                r2_max = Math.max(r2, r2_max);
//            }
//        }
//
//        area = Math.PI * r2_max;
//
//        System.out.println(area);
//        return area;
//    }

}
