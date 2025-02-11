package com.cy.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * @BelongsProject: AltronBoot
 * @BelongsPackage: org.wql.util
 * @Author: 阿隆
 * @CreateTime: 2024-11-11 11:11
 * @Description: 生成图像验证码工具类
 * @Version: 1.0
 */
public class ValidateCodeUtil {
    private static Random random = new Random();
    private int width = 160;// 宽
    private int height = 40;// 高
    private int lineSize = 30;// 干扰线数量
    private int stringNum = 4;//随机产生字符的个数

    private String randomString = "0123456789abcdefghijklmnopqrstuvwxyz";

    private String code;

    /*
     *  获取字体
     */
    private Font getFont() {
        //return new Font("Times New Roman", Font.ROMAN_BASELINE, 40);
        return new Font("Times New Roman", Font.BOLD, 40);
    }

    /*
     *  获取颜色
     */
    private static Color getRandomColor(int fc, int bc) {

        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 12);

        return new Color(r, g, b);
    }

    /*
     *  绘制干扰线
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(20);
        int yl = random.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /*
     *  获取随机字符
     */
    private String getRandomString(int num) {
        num = num > 0 ? num : randomString.length();
        return String.valueOf(randomString.charAt(random.nextInt(num)));
    }

    /*
     *  绘制字符串
     */
    private String drawString(Graphics g, String randomStr, int i) {
        g.setFont(getFont());
        g.setColor(getRandomColor(108, 190));
        //(random.nextInt(randomString.length()));
        String rand = getRandomString(random.nextInt(randomString.length()));
        randomStr += rand;
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
        return randomStr;
    }

    /*
     *  生成随机图片
     */
    public String getRandomCodeImage() {

        //HttpSession session = request.getSession();
        //session.removeAttribute(sessionKey);
        //session.setAttribute(sessionKey, random_string);

        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setColor(getRandomColor(105, 189));
        g.setFont(getFont());

        // 绘制干扰线
        for (int i = 0; i < lineSize; i++) {
            drawLine(g);
        }

        // 绘制随机字符
        String random_string = "";
        for (int i = 0; i < stringNum; i++) {
            random_string = drawString(g, random_string, i);
            code = random_string;
        }

        g.dispose();


        String imgBase64 = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", stream);
            imgBase64 = new String(Base64.getEncoder().encode(stream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgBase64;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
