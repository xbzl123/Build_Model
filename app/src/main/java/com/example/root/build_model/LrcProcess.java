package com.example.root.build_model;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LrcProcess {

    private List<LrcContent> lrcList;
    //List集合存放歌词内容对象
    private LrcContent mLrcContent;
    //声明一个歌词内容对象 /** * 无参构造函数用来实例化对象 */
    public LrcProcess() {
        mLrcContent = new LrcContent();
        lrcList = new ArrayList<LrcContent>();
    }

    /**
     * 从sd card文件中读取数据
     * @param filename 待读取的sd card
     * @return
     * @throws IOException
     */
    public String readExternal( String filename) throws IOException {
         StringBuilder sb = new StringBuilder("");
         if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//             filename = context.getExternalCacheDir().getAbsolutePath() + File.separator + filename;
             //打开文件输入流
             FileInputStream inputStream = new FileInputStream(filename);
             doRead(inputStream);
             byte[] buffer = new byte[1024];
             int len = inputStream.read(buffer);
             //读取文件内容
             while(len > 0){
                 sb.append(new String(buffer,0,len));
                 //继续将数据放到buffer中
                 len = inputStream.read(buffer);
             } //关闭输入流
             inputStream.close();
         } return sb.toString();
     }

    private void doRead(InputStream is) throws IOException{
        DataInputStream dis = new DataInputStream(is);
        byte[]buffer = new byte[is.available()];
        dis.readFully(buffer);
//        textView.setText(new String(buffer));
        Log.e("", "doRead: ================"+new String(buffer));
        dis.close();
        is.close();
    }
    /*
    * */
    public void dealError(){

    }

    /** * 读取歌词 * @param path * @return */
    public String readLRC(String path) {
        //定义一个StringBuilder对象，用来存放歌词内容 S
        StringBuilder stringBuilder = new StringBuilder();
        File f = new File(path.replace(".mp3", ".lrc"));
        try { //创建一个文件输入流对象
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while((s = br.readLine()) != null) {
                Log.e("INFO", "歌词uuuuuuuuuu"+s);
                if((s.contains("]["))){
                    int i = s.indexOf("][");
                    if(s.length()>i+8 && s.contains("."))
                    if((s.substring(i+7,i+8).equals("["))){
                    StringBuffer sb = new StringBuffer(s);
                    sb.insert(i+7,"]");
                    s = sb.toString();
                    }
                }
                if(s.contains(":")&&!s.substring(0,1).equals("["))
                    s = "["+s;
                if(s.contains(":-")||s.contains("[:")
                        ||(s.substring(s.lastIndexOf(':')+1).equals("") && !s.equals(""))
                        )break;

                if(!s.contains(":")
                        ||!s.substring(s.lastIndexOf(':')).contains("]")
                        )continue;
                int pos = 0;
                int a = s.indexOf("][");
                if(a != -1) {
                    String tmp = s.replace("][", "%");
                    String[] tmpData = tmp.split("%");
                    pos = tmpData.length;
                }
                if(s.contains("][")){
                if(s.substring(s.lastIndexOf(']')+1).equals(""))
                    s = s+"music~~";
                if(s.substring(0,s.lastIndexOf("][")).contains("."))
                if(!s.substring(s.lastIndexOf("][")+10,s.lastIndexOf("][")+11).equals("]")
                        ){
                    StringBuffer sb = new StringBuffer(s);
                    sb.insert(sb.lastIndexOf("][")+10,"]");
                    s = sb.toString();
                }
                }else{

                }
                //替换字符
                s = s.replace("[", "");
                s = s.replace("]", "@");
                //分离“@”字符
                String splitLrcData[] = s.split("@");
                if(pos == 0){
                if(splitLrcData.length > 1) {
                    Log.i("INFO", splitLrcData[1]+"歌词");
                mLrcContent.setLrcStr(splitLrcData[1]);
                Log.i("INFO", splitLrcData[0]+"时间"); //处理歌词取得歌曲的时间
                    int lrcTime = time2Str(splitLrcData[0]);
                    mLrcContent.setLrcTime(lrcTime); //添加进列表数组
                    lrcList.add(mLrcContent); //新创建歌词内容对象
                    mLrcContent = new LrcContent();
                }
                }else{
                    int size = pos;
                    for (int i= size-1;i > -1;i--){
                        mLrcContent.setLrcStr(splitLrcData[size]);
                        Log.i("INFO", splitLrcData[0]+"时间"); //处理歌词取得歌曲的时间
                        int lrcTime = time2Str(splitLrcData[i]);
                        mLrcContent.setLrcTime(lrcTime); //添加进列表数组
                        lrcList.add(mLrcContent); //新创建歌词内容对象
                        mLrcContent = new LrcContent();
                    }
                }
            }
            Collections.sort(lrcList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            stringBuilder.append("木有歌词文件，赶紧去下载！...");
        } catch (IOException e) {
            e.printStackTrace();
            stringBuilder.append("木有读取到歌词哦！");
        }
            return stringBuilder.toString();
    } /** * 解析歌词时间 * 歌词内容格式如下：
     * [00:02.32]陈奕迅
     * [00:03.43]好久不见
     * [00:05.22]歌词制作 王涛
     * @param timeStr * @return */
    public int time2Str(String timeStr) {
        int currentTime = 0;
        if(!timeStr.contains(":")||timeStr.contains("-"))return -1;
        try {
            if (timeStr.contains(".")) {
                timeStr = timeStr.replace(":", ".");
                timeStr = timeStr.replace(".", "@");
                Log.e("", "time2Str: ======" + timeStr);

                String timeData[] = timeStr.split("@"); //将时间分隔成字符串数组 //分离出分、秒并转换为整型

                int minute = Integer.parseInt(timeData[0]);
                int second = Integer.parseInt(timeData[1]);
                int millisecond = Integer.parseInt(timeData[2]); //计算上一行与下一行的时间转换为毫秒数
                currentTime = (minute * 60 + second) * 1000 + millisecond * 10;
            } else {
                timeStr = timeStr.replace(":", "@");
                Log.e("", "time2Str: ======" + timeStr);
                String timeData[] = timeStr.split("@"); //将时间分隔成字符串数组 //分离出分、秒并转换为整型
                if (timeData.length < 1) return -1;
                int minute = Integer.parseInt(timeData[0]);
                int second = Integer.parseInt(timeData[1]);
                currentTime = (minute * 60 + second) * 1000;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentTime;
    }

    public List<LrcContent> getLrcList() {
//        Collections.sort(lrcList);
        return lrcList;
    }
}
