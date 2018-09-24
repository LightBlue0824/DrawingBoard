package logic;

import logic.serviceImpl.BoardIOLogicImpl;
import logic.serviceImpl.ShapeProcessImpl;
import logicService.BoardIOLogicService;
import logicService.ShapeProcessService;

/**
 * LogicController，对展示层隐藏并提供对应的实现
 */
public class LogicController {
    public static ShapeProcessService getShapeProcessImpl(){
        return new ShapeProcessImpl();
    }

    public static BoardIOLogicService getBoardIOLogicImpl(){
        return new BoardIOLogicImpl();
    }
}
