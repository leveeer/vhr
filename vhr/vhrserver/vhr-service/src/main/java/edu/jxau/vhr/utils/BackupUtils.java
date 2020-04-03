package edu.jxau.vhr.utils;


import java.io.*;


public class BackupUtils {

    /**
     * 执行生成备份
     */
    public static void doBackup(String root,String pwd,String dbName,String backPath,String backName) throws Exception {
        String pathSql = backPath + "\\" + backName;
        File fileSql = new File(pathSql);
        //创建备份sql文件
        File file = new File(backPath);
        if (!file.exists() && !file .isDirectory()) {
            throw new Exception("备份失败！");
        }else {
            //mysqldump -hlocalhost -uroot -p123456 db > /home/back.sql
            StringBuffer sb = new StringBuffer();
            sb.append("mysqldump");
            sb.append(" -hlocalhost");
            sb.append(" -u" + root);
            sb.append(" -p" + pwd);
            sb.append(" " + dbName + " >");
            sb.append(pathSql);
            System.out.println("cmd命令为：" + sb.toString());
            Runtime runtime = Runtime.getRuntime();
            System.out.println("开始备份：" + dbName);
            Process process = runtime.exec("cmd /c" + sb.toString());
            System.out.println("备份成功!");
        }
    }


    /**
     * 恢复数据库
     * @param root
     * @param pwd
     * @param dbName
     * @param filePath
     * mysql -hlocalhost -uroot -p123456 db < /home/back.sql
     */
    public static void dbRestore(String root,String pwd,String dbName,String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("mysql");
        sb.append(" -hlocalhost");
        sb.append(" -u"+root);
        sb.append(" -p"+pwd);
        sb.append(" "+dbName+" <");
        sb.append(filePath);
        System.out.println("cmd命令为："+sb.toString());
        Runtime runtime = Runtime.getRuntime();
        System.out.println("开始还原数据");
        Process process = runtime.exec("cmd /c"+sb.toString());
        InputStream is = process.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(is,"utf8"));
        String line = null;
        while ((line=bf.readLine())!=null){
            System.out.println(line);
        }
        is.close();
        bf.close();
        System.out.println("还原成功！");
    }


}
