package po;

import util.MyShape;

import java.util.ArrayList;

/**
 * 用于逻辑层和数据层之间传递
 */
public class ShapesPO {
    private ArrayList<MyShape> shapeArrayList;

    public ShapesPO(ArrayList<MyShape> list){
        //构造之后无法改变
        this.shapeArrayList = list;
    }

    public ArrayList<MyShape> getShapeArrayList(){
        return new ArrayList<>(this.shapeArrayList);
    }

}
