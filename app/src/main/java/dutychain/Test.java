package dutychain;

//责任链模式
public class Test {
    public static void main(String[] args){
        LogHandler logHandler = getLogObj();
        logHandler.handler(LogLevel.debug,"this is a message!");
    }
    public static LogHandler getLogObj(){
        DeBug deBug = new DeBug(LogLevel.debug);
        Info info = new Info(LogLevel.info);
        Error error = new Error(LogLevel.error);
        deBug.setNextHandler(info);
        info.setNextHandler(error);
        return deBug;
    }
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        //创建文件对象，指定要写出的文件路径
//        File file=new File("test.txt");
//        try {
//            //创建文件字节输出流对象，准备向d.txt文件中写出数据,true表示在原有的基础上增加内容
//            FileOutputStream fout=new FileOutputStream(file,true);
//            Scanner sc=new Scanner(System.in);
//
//            System.out.println("请写出一段字符串:");
//            String msg=sc.next()+"\r\n";;
//
//            /******************(方法一)按字节数组写入**********************/
//            //byte[] bytes = msg.getBytes();//msg.getBytes()将字符串转为字节数组
//
//            //fout.write(bytes);//使用字节数组输出到文件
//            /******************(方法一)逐字节写入**********************/
//            byte[] bytes = msg.getBytes();
//            for (int i = 0; i < bytes.length; i++) {
//                fout.write(bytes[i]);//逐字节写文件
//            }
//            fout.flush();//强制刷新输出流
//            fout.close();//关闭输出流
//            System.out.println("写入完成！");
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
