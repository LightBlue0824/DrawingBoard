package vo;

import util.MyShape;

import java.util.ArrayList;

/**
 * 用于展示层和逻辑层之间传递
 */
public class ShapesVO {
    private ArrayList<MyShape> shapeArrayList;

    public ShapesVO(ArrayList<MyShape> list){
        //构造之后无法改变
        this.shapeArrayList = list;
    }

    public ArrayList<MyShape> getShapeArrayList(){
        return new ArrayList<>(this.shapeArrayList);
    }
}
