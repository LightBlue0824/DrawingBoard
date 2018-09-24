package logic.serviceImpl;

import logicService.ShapeProcessService;
import util.MyShape;
import util.Point;

import java.util.ArrayList;

public class ShapeProcessImpl implements ShapeProcessService {
    @Override
    public String recognizeShape(MyShape shape) {
        String result;
        result = judgeShape(shape);

        return result;
    }

    @Override
    public ArrayList<Point> standardizeShape(MyShape shape) {
        ArrayList<Point> result = new ArrayList<>();

        switch (shape.getType()){
            case "圆":{
                int radius = calcStdCircleRadius(shape);
                result.add(new Point(radius,radius));
                break;
            }
            case "三角形":{

                ArrayList<Point> vertexList = shape.getVertexList();
                for(int i = 0; i < vertexList.size(); i++){
                    if(i % 2 == 1){
                        result.add(vertexList.get((i+1)%6));
                    }
                    else{
                        result.add(vertexList.get(i));
                    }
                }
                break;
            }
            case "正方形":{
                ArrayList<Point> tempVertexList = shape.getVertexList();
                int differenceX = tempVertexList.get(1).getX() - tempVertexList.get(0).getX();
                int differenceY = tempVertexList.get(1).getY() - tempVertexList.get(0).getY();

                boolean isClockWise = isClockWise(shape, differenceX, differenceY);     //判断是否是顺时针
                for(int i = 2; i < tempVertexList.size(); i+=2){

                    if(isClockWise){
                        int tempDifferenceX = differenceX;
                        differenceX = -differenceY;
                        differenceY = tempDifferenceX;
                    }
                    else{
                        int tempDifferenceX = -differenceX;
                        differenceX = differenceY;
                        differenceY = tempDifferenceX;
                    }
                    //计算新的线段端点
                    Point lastPoint = tempVertexList.get(i-1);
                    tempVertexList.set(i, lastPoint);
                    tempVertexList.set(i+1, new Point(lastPoint.getX()+differenceX, lastPoint.getY()+differenceY));
                }
                result = tempVertexList;
                break;
            }
            case "长方形":{
                ArrayList<Point> tempVertexList = shape.getVertexList();
                double[] longAndShort = calcLongAndShort(tempVertexList);       //计算长短边
                double short_avg = longAndShort[0];
                double long_avg = longAndShort[1];

                int differenceX = tempVertexList.get(1).getX() - tempVertexList.get(0).getX();
                int differenceY = tempVertexList.get(1).getY() - tempVertexList.get(0).getY();
                boolean isClockWise = isClockWise(shape, differenceX, differenceY);     //判断是否是顺时针
                double firstLineLen = Math.sqrt(Math.pow(differenceX,2)+Math.pow(differenceX, 2));        //第一条边的长度
                boolean isLong = Math.abs(firstLineLen-short_avg) > Math.abs(firstLineLen-long_avg);       //是否是长边

                for(int i = 2; i < tempVertexList.size()+2; i+=2){
                    isLong = !isLong;       //长短交替
                    double curLen = isLong ? long_avg : short_avg;

                    if(isClockWise){
                        int tempDifferenceX = differenceX;
                        differenceX = -differenceY;
                        differenceY = tempDifferenceX;
                    }
                    else{
                        int tempDifferenceX = -differenceX;
                        differenceX = differenceY;
                        differenceY = tempDifferenceX;
                    }
                    if(differenceX == 0){
                        differenceX = 0;
                        differenceY = differenceY/Math.abs(differenceY)*(int)Math.abs(curLen);
                    }
                    else{
                        double slope = differenceY*1.0/differenceX;
                        differenceX = differenceX/Math.abs(differenceX)*(int)Math.abs(curLen*Math.cos(Math.atan(slope)));
                        if(differenceY != 0){
                            differenceY = differenceY/Math.abs(differenceY)*(int)Math.abs(curLen*Math.sin(Math.atan(slope)));
                        }
                    }
                    //计算新的线段端点
                    Point lastPoint = tempVertexList.get((i-1)%8);
                    tempVertexList.set(i%8, lastPoint);

                    tempVertexList.set((i+1)%8, new Point(lastPoint.getX()+differenceX, lastPoint.getY()+differenceY));
                }
                result = tempVertexList;
                break;
            }
        }
        return result;
    }

    /**
     * 判断图形的形状
     * @param shape 图形
     * @return 形状
     */
    private String judgeShape(MyShape shape){
        String result;

        //笔画识别
        int lineCount = shape.getVertexList().size() / 2;       //线数量
        switch (lineCount){
            case 1:{
                result = "圆";
                break;
            }
            case 3:{
                result = "三角形";
                break;
            }
            case 4:{
                result = "长方形";
                //判断是否是正方形
                double[] longAndShort = calcLongAndShort(shape.getVertexList());
                double width = longAndShort[0];
                double height = longAndShort[1];
//                int width = shape.getMaxX() - shape.getMinX();
//                int height = shape.getMaxY() - shape.getMinY();
                double whRatio = (width*1.0)/height;        //长宽比
                if(whRatio > 0.9 && whRatio < 1.1){
                    result = "正方形";
                }
                break;
            }
            default:{
                result = "其他图形";
                break;
            }
        }

        return result;
    }

    /**
     * 计算规范化圆的半径
     */
    private int calcStdCircleRadius(MyShape shape){
        int radius;
        int width = shape.getMaxX()-shape.getMinX();
        int height = shape.getMaxY()-shape.getMinY();
        radius = (width+height)/2/2;
        return radius;
    }

    /**
     * 判断是什么方向画的矩形
     * @param shape 图形
     * @param differenceX 边的两端x差
     * @param differenceY 边的两端y差
     * @return 是否是顺时针
     */
    private boolean isClockWise(MyShape shape, int differenceX, int differenceY){
        boolean result;

        ArrayList<Point> pointList = shape.getPointList();
        int differenceX_1 = -differenceY;        //顺时针
        int differenceY_1 = differenceX;
        int differenceX_2 = differenceY;        //逆时针
        int differenceY_2 = -differenceX;
        Point lastPoint = pointList.get(1);
        Point point_1 = new Point(lastPoint.getX()+differenceX_1, lastPoint.getY()+differenceY_1);
        Point point_2 = new Point(lastPoint.getX()+differenceX_2, lastPoint.getY()+differenceY_2);
        int sum_1 = 0, sum_2 = 0;

        for(int i = 0; i < pointList.size(); i++){
            Point cur = pointList.get(i);
            sum_1 = sum_1 + cur.getX()-point_1.getX() + cur.getY()-point_1.getY();
            sum_2 = sum_2 + cur.getX()-point_2.getX() + cur.getY()-point_2.getY();
        }

        sum_1 = Math.abs(sum_1);
        sum_2 = Math.abs(sum_2);

        result = sum_1 < sum_2;     //谁小，说明是什么方向

        return result;
    }

    /**
     * 计算图形的长和宽
     * @param vertexList 端点列表
     * @return [0]:宽，[1]:长
     */
    private double[] calcLongAndShort(ArrayList<Point> vertexList){
        double[] result = new double[2];
        double[] lengthArr = new double[4];
        for(int i = 0; i < 4; i++){
            int tempDX = vertexList.get(i*2+1).getX() - vertexList.get(i*2).getX();
            int tempDY = vertexList.get(i*2+1).getY() - vertexList.get(i*2).getY();
            double tempLen = Math.sqrt(Math.pow(tempDX,2)+Math.pow(tempDY, 2));
            lengthArr[i] = tempLen;
        }
        //对lengthArr排序
        for(int i = 0; i < lengthArr.length; i++){
            for(int j = 0; j < i;j++){
                if(lengthArr[i] < lengthArr[j]){
                    double temp = lengthArr[i];
                    lengthArr[i] = lengthArr[j];
                    lengthArr[j] = temp;
                }
            }
        }
        double short_avg = (lengthArr[0]+lengthArr[1])/2;       //长短边的均值
        double long_avg = (lengthArr[2]+lengthArr[3])/2;

        result[0] = short_avg;
        result[1] = long_avg;
        return result;
    }
//    /**
//     * 计算图形的面积
//     * @param shape 图形
//     * @return 面积
//     */
//    private double calcArea(MyShape shape){
//        ArrayList<Point> points = shape.getPointList();
//        int i, j;
//        int n = points.size();
//        double area = 0;
//
//        //叉乘法计算面积
//        for(i = 0; i < n; i++){
//            j  = (i+1)%n;
//
//            area += points.get(i).getX() * points.get(j).getY();
//            area -= points.get(i).getY() * points.get(j).getX();
//        }
//        area /= 2.0;
//        area = Math.abs(area);      //跟点的顺序有关，取正值
////        System.out.println(area);
//        return area;
//    }

//    /**
//     * 判断图形的形状
//     * @param shape 图形
//     * @return 形状
//     */
//    private String judgeShape(MyShape shape){
//        String result;
//
//        double area = calcArea(shape);
//        double circleArea = calcRectArea(shape);
//
//        double ratio = area / circleArea;
//
//        //0.785为内切圆的比例，0.5为内接三角形的比例
//        if(ratio <= 1 && ratio > 0.82){
//            result = "长方形";
//            //判断是否是正方形
//            int width = shape.getMaxX() - shape.getMinX();
//            int height = shape.getMaxY() - shape.getMinY();
//            double whRatio = (width*1.0)/height;        //长宽比
//            if(whRatio > 0.9 && whRatio < 1.1){
//                result = "正方形";
//            }
//        }
//        else if(ratio > 0.65){
//            result = "圆";
//        }
//        else if(ratio > 0.05){
//            result = "三角形";
//        }
//        else{
//            result = "线";
//        }
//
////        System.out.println(result);
//        return result;
//    }

//    /**
//     * 计算外接矩形的面积（近似外接）
//     * @param shape 图形
//     * @return 面积
//     */
//    private double calcRectArea(MyShape shape) {
//        double area;
//
//        int width = shape.getMaxX() - shape.getMinX();
//        int height = shape.getMaxY() - shape.getMinY();
//
//        area = width * height;
//        return area;
//    }
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
