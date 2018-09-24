package data;

import data.serviceImpl.BoardIODataImpl;
import dataService.BoardIODataService;

/**
 * DataController，对逻辑层隐藏并提供对应的实现
 */
public class DataController {

    public static BoardIODataService getBoardIODataImpl(){
        return new BoardIODataImpl();
    }
}
