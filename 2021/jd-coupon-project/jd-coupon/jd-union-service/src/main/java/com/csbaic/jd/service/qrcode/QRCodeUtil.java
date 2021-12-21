package com.csbaic.jd.service.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author icecrea on 18-1-5
 */
public class QRCodeUtil {
    private static final int width = 300;// 默认二维码宽度
    private static final int height = 300;// 默认二维码高度
    private static final String format = "png";// 默认二维码文件格式
    private static final Map<EncodeHintType, Object> hints = new HashMap();// 二维码参数

    static {
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 字符编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        hints.put(EncodeHintType.MARGIN, 2);// 二维码与图片边距
    }
    /**
     * 返回一个 BufferedImage 对象
     * @param content 二维码内容
     * @param width   宽
     * @param height  高
     */
    public static BufferedImage toBufferedImage(String content, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
    /**
     * 将二维码图片输出到一个流中
     * @param content 二维码内容
     * @param stream  输出流
     * @param width   宽
     * @param height  高
     */
    public static void writeToStream(String content, OutputStream stream, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
    }
    /**
     * 生成二维码图片文件
     * @param content 二维码内容
     * @param path    文件保存路径
     * @param width   宽
     * @param height  高
     */
    public static void createQRCode(String content, String path, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        //toPath() 方法由 jdk1.7 及以上提供
        MatrixToImageWriter.writeToPath(bitMatrix, format, new File(path).toPath());
    }

    /**
     * 设置二维码图片
     * @param matrixImage
     * @param logUri
     * @return
     * @throws IOException
     */
    public static BufferedImage setMatrixLogo(BufferedImage matrixImage,String logUri) throws IOException{
        /**
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        /**
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(new URL(logUri));

        //开始绘制图片
        g2.drawImage(logo,matrixWidth/5*2,matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5, null);
        //绘制边框
        BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke);
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
        g2.setColor(Color.white);
        // 绘制圆弧矩形
        g2.draw(round);

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke2);
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2, matrixHeigh/5*2+2, matrixWidth/5-4, matrixHeigh/5-4,20,20);
        g2.setColor(new Color(128,128,128));
        g2.draw(round2);// 绘制圆弧矩形


        g2.dispose();
        matrixImage.flush() ;
        return matrixImage ;
    }



    public static void main(String[] args) throws Exception {
        OutputStream outputStream=new FileOutputStream(new File("/home/icecrea/hello.png"));
        QRCodeUtil.writeToStream("www.baidu.com",outputStream,width,height);
        QRCodeUtil.createQRCode("www.qunar.com","/home/icecrea/qunar.png",width,height);
    }
}