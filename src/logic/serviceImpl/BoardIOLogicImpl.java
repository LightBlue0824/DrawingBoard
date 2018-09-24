package logic.serviceImpl;

import data.DataController;
import dataService.BoardIODataService;
import logicService.BoardIOLogicService;
import util.MyBoard;
import util.MyShape;

import java.util.ArrayList;

public class BoardIOLogicImpl implements BoardIOLogicService {

    private BoardIODataService boardIOData = DataController.getBoardIODataImpl();

    /**
     * 保存画板内容
     * @param board 画板内的图形
     * @return 是否成功
     */
    @Override
    public boolean saveBoard(MyBoard board, String filename) {
        return boardIOData.saveBoard(board, filename);
    }

    /**
     * 读取已保存的画板
     * @param filename 画板的文件名
     * @return 读取到的画板里的图形
     */
    @Override
    public MyBoard loadBoard(String filename) {
        return boardIOData.loadBoard(filename);
    }

    /**
     * 读取已保存的文件列表
     * @return
     */
    @Override
    public String[] loadFilesList() {
        return boardIOData.loadFilesList();
    }
}
