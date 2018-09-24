package dataService;

import util.MyBoard;
import util.MyShape;

import java.util.ArrayList;

public interface BoardIODataService {
    /**
     * 保存画板内容
     * @param board 画板内的图形
     * @return 是否成功
     */
    boolean saveBoard(MyBoard board, String filename);

    /**
     * 读取已保存的画板
     * @param filename 画板的文件名
     * @return 读取到的画板里的图形
     */
    MyBoard loadBoard(String filename);

    /**
     * 读取已保存的文件列表
     * @return
     */
    String[] loadFilesList();

}
