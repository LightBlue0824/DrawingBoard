package logicService;

import util.MyShape;
import util.Point;

import java.util.ArrayList;

public interface ShapeProcessService {

    /**
     * 识别图形
     * @param shape 需要进行识别的图形
     * @return 图形类型
     */
    String recognizeShape(MyShape shape);

    /**
     * 标准化图形
     * @param shape 需要进行标准化的图形
     * @return 标准化后图形的:圆为[1]宽高(半径);其他图形为线端点列表
     */
    ArrayList<Point> standardizeShape(MyShape shape);
}
