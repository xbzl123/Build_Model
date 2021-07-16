import com.blankj.utilcode.util.EncodeUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

public class Test {
    public static void main(String[] arg){
        final File file = new File("111.jpg");

//		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/123.jpg");
        if(!file.exists())return ;
        String result = QRCodeDecoder.syncDecodeQRCode(file.getPath());
        byte[] result1 = uncompress(EncodeUtils.base64Decode(result));
//        System.out.println();
//        System.out.println("getLorexSecureDevices: =====result="+EncodeUtils.base64Decode(result));
        System.out.println("getLorexSecureDevices: =====result="+result);
//
//        System.out.println("getLorexSecureDevices: =====result2="+uncompress(EncodeUtils.base64Decode(result)));
    }

    //使用GZIPInputStream进行GZIP解压缩：
    public static byte[] uncompress(byte[] bytes) {

        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
