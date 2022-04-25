package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class FontGenerator {
    private static final HashMap<Character, BufferedImage> letterMap = new HashMap<>();

    public static void prime() {
        try {
            BufferedImage fontImage = ImageIO.read(new File("images/font.png"));
            int left = 0;
            int right = 0;
            for (int i = 97; i <= 122; i++) {
                boolean flag = false;
                outer: while (!flag) {
                    for (int j = 0; j < 10; j++) {
                        if (fontImage.getRGB(right, j) == -16777216) {
                            right++;
                            continue outer;
                        }
                    }
                    flag = true;
                }
                letterMap.put((char) i, fontImage.getSubimage(left, 0, right - left, 10));
                left = right + 1;
                right = left;
            }
            left = 0;
            right = 0;
            for (int i = 65; i <= 90; i++) {
                boolean flag = false;
                outer: while (!flag) {
                    for (int j = 12; j < 22; j++) {
                        if (fontImage.getRGB(right, j) == -16777216) {
                            right++;
                            continue outer;
                        }
                    }
                    flag = true;
                }
                letterMap.put((char) i, fontImage.getSubimage(left, 12, right - left, 10));
                left = right + 1;
                right = left;
            }
            final char[] chars = new char[] {
                    '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                    '.', ':', ',', ';', '\'', '"', '(', '!', '?', ')',
                    '+', '-', '*', '/', '='
            };
            left = 0;
            right = 0;
            for (int i = 0; i < chars.length; i++) {
                boolean flag = false;
                outer: while (!flag) {
                    for (int j = 24; j < 34; j++) {
                        if (fontImage.getRGB(right, j) == -16777216) {
                            right++;
                            continue outer;
                        }
                    }
                    flag = true;
                }
                letterMap.put(chars[i], fontImage.getSubimage(left, 24, right - left, 10));
                System.out.println(left + " " + right);
                left = right + 1;
                right = left;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println(letterMap.get('h').getRGB(0, 0));
    }

    public static void writeText(Graphics g, String str, int startX, int startY, float scale, Color fontColor) {
        float curX = startX, curY = startY;
        for (char c : str.toCharArray()) {
            if (c == '\n') {
                curX = startX;
                curY += 11 * scale;
            } else if (c == ' ') {
                curX += 3 * scale;
            } else {
                BufferedImage letterToDraw = letterMap.get(c);
                for (int i = 0; i < letterToDraw.getWidth(); i++) {
                    for (int j = 0; j < letterToDraw.getHeight(); j++) {
                        if (letterToDraw.getRGB(i, j) != 0) {
                            letterToDraw.setRGB(i, j, fontColor.getRGB());
                        }
                    }
                }
                g.drawImage(letterToDraw, (int) curX, (int) curY, (int) (letterToDraw.getWidth() * scale),
                        (int) (letterToDraw.getHeight() * scale), null);
                curX += (letterToDraw.getWidth() + 1) * scale;
            }
        }
    }

    public static void writeText(Graphics g, String str, int startX, int startY, float scale) {
        writeText(g, str, startX, startY, scale, Color.BLACK);
    }

    public static void writeText(Graphics g, String str, int startX, int startY, Color fontColor) {
        writeText(g, str, startX, startY, 1, fontColor);
    }

    public static void writeText(Graphics g, String str, int startX, int startY) {
        writeText(g, str, startX, startY, 1, Color.BLACK);
    }
}
