package logicService;

import util.MyShape;

public interface ShapeProcessService {

    /**
     * 识别图形
     * @param shape 需要进行识别的图形
     * @return 图形类型
     */
    String recognizeShape(MyShape shape);

}
