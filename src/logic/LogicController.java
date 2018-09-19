package logic;

import logic.serviceImpl.ShapeProcessImpl;
import logicService.ShapeProcessService;

/**
 * Controller，对展示层隐藏并提供对应的实现
 */
public class LogicController {
    public static ShapeProcessService getShapeProcessImpl(){
        return new ShapeProcessImpl();
    }
}
