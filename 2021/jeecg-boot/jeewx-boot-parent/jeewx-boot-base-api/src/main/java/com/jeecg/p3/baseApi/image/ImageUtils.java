package com.jeecg.p3.baseApi.image;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ImageUtils {


    public static void main(String[] args) {
        System.out.println(getBase64ByImgUrl("http://www.jeecg.org/static/image/common/logo.png"));
    }

    public static String getBase64ByImgUrl(String url) {
        String suffix = url.substring(url.lastIndexOf(".") + 1);
        try {
            URL urls = new URL(url);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Image image = Toolkit.getDefaultToolkit().getImage(urls);
            BufferedImage biOut = toBufferedImage(image);
            ImageIO.write(biOut, suffix, baos);
            String base64Str = Base64Util.encode(baos.toByteArray());
            return base64Str;
        } catch (Exception e) {
            return "";
        }

    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded  
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen  
        }
        if (bimage == null) {
            // Create a buffered image using the default color model  
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        // Copy image to buffered image  
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image  
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    /**
     * ???????????????url???????????????base64?????????
     *
     * @param imgUrl ??????url
     * @return ????????????base64????????????
     */
    public static String image2Base64(String imgUrl) {

        URL url = null;

        InputStream is = null;

        ByteArrayOutputStream outStream = null;

        HttpURLConnection httpUrl = null;

        try {

            url = new URL(imgUrl);

            httpUrl = (HttpURLConnection) url.openConnection();

            httpUrl.connect();

            httpUrl.getInputStream();

            is = httpUrl.getInputStream();


            outStream = new ByteArrayOutputStream();

            //????????????Buffer?????????  

            byte[] buffer = new byte[1024];

            //??????????????????????????????????????????-1???????????????????????????  

            int len = 0;

            //????????????????????????buffer????????????????????????  

            while ((len = is.read(buffer)) != -1) {

                //???????????????buffer???????????????????????????????????????????????????????????????len?????????????????????  

                outStream.write(buffer, 0, len);

            }

            // ???????????????Base64??????  

            return Base64Util.encode(outStream.toByteArray());

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (is != null)

            {

                try {

                    is.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            if (outStream != null)

            {

                try {

                    outStream.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            if (httpUrl != null)

            {

                httpUrl.disconnect();

            }

        }

        return imgUrl;

    }

    /**
     * base64?????????????????????
     *
     * @param str ??????url
     * @return ????????????base64????????????
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * ????????????url???????????????base64?????????
     *
     * @param imgURL ??????url
     * @return ????????????base64????????????
     */
    public static String ImageToBase64ByOnline(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // ??????URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // ????????????
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            InputStream is = conn.getInputStream();
            // ????????????????????????
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // ?????????
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ???????????????Base64??????
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }
}