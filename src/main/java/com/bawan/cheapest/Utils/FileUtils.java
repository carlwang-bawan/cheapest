package com.bawan.cheapest.Utils;

import java.io.File;

/**
 * Created by wangzhen on 2016/11/15.
 */
public class FileUtils {

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static void renameFile(String oldname,String newname){
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(oldname);
            File newfile=new File(newname);
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname+"已经存在！");
            else{
                oldfile.renameTo(newfile);
            }
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }
}
