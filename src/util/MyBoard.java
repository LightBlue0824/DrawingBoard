package util;

import java.io.Serializable;
import java.util.ArrayList;

public class MyBoard implements Serializable {
    private ArrayList<MyShape> shapeArrayList = new ArrayList<>();

    public void addShape(MyShape shape){
        shapeArrayList.add(shape);
    }

    public ArrayList<MyShape> getShapeList(){
        return new ArrayList<>(shapeArrayList);
    }
}
