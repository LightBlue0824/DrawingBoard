package data.serviceImpl;

import dataService.BoardIODataService;
import util.MyBoard;
import util.MyShape;

import java.io.*;
import java.util.ArrayList;

public class BoardIODataImpl implements BoardIODataService {
    private static final String path = "savedFiles/";

    /**
     * 保存画板内容
     * @param board 画板内的图形
     * @return 是否成功
     */
    @Override
    public boolean saveBoard(MyBoard board, String filename) {
        try {
            FileOutputStream fs = new FileOutputStream(path+filename+".ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(board);
            fs.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取已保存的画板
     * @param filename 画板的文件名
     * @return 读取到的画板里的图形
     */
    @Override
    public MyBoard loadBoard(String filename) {
        MyBoard board = null;
        try {
            FileInputStream fs = new FileInputStream(path+filename+".ser");
            ObjectInputStream is = new ObjectInputStream(fs);
            board = (MyBoard) is.readObject();
            fs.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return board;
    }

    /**
     * 读取已保存的文件列表
     * @return
     */
    @Override
    public String[] loadFilesList() {
        File directory = new File(path);
        String[] list = directory.list();
        for(int i = 0; i < list.length; i++){
            list[i] = list[i].replace(".ser", "");
        }
        return list;
    }
}
