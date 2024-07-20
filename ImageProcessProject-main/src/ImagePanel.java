import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImagePanel extends JPanel {
    private BufferedImage originalImage;
    private BufferedImage manipulatedImage;
    private int mouseX = -1;
    private String currentFilter = "Original";

    public ImagePanel(BufferedImage image) {
        this.originalImage = image;
        this.manipulatedImage = cloneImage(image);
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Painting the original image from start
        g.drawImage(originalImage, 0, 0, this);

        //If mouse is passed value '0' in X axis it will display a red line across the Y axis
        // while left part of image is manipulated and right stays original (unless mouse goes to end of image)

        if (mouseX >= 0) {
            //Drawing left part of manipulated image from 0,0 to mouseX location
            g.drawImage(manipulatedImage, 0, 0, mouseX, manipulatedImage.getHeight(), 0, 0, mouseX, manipulatedImage.getHeight(), this);
            g.setColor(Color.RED);
            g.drawLine(mouseX, 0, mouseX, getHeight());
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString(this.currentFilter,0,20);
        }
    }

    public void showOriginal() {
        this.currentFilter = "Original";
        this.manipulatedImage = cloneImage(originalImage);
        repaint();
    }

    public void applyNegative() {
        this.currentFilter = "Negative";
        this.manipulatedImage = negative(cloneImage(originalImage));
        repaint();
    }

    public void applyGrayScale() {
        this.currentFilter = "Gray Scale";
        this.manipulatedImage = grayScale(cloneImage(originalImage));
        repaint();
    }

    public void applyTint() {
        this.currentFilter = "Tint";
        this.manipulatedImage = tintImage(cloneImage(originalImage));
        repaint();
    }

    public void applyShiftRight(){
        this.currentFilter = "Shift Right";
        this.manipulatedImage = shiftRight(cloneImage(originalImage));
        repaint();
    }

    public void applyShiftLeft(){
        this.currentFilter = "Shift Left";
        this.manipulatedImage = shiftLeft(cloneImage(originalImage));
        repaint();
    }

    public void applySepia(){
        this.currentFilter = "Sepia";
        this.manipulatedImage = sepia(cloneImage(originalImage));
        repaint();
    }

    public void applyMirror(){
        this.currentFilter = "Mirror";
        this.manipulatedImage = mirror(cloneImage(originalImage));
        repaint();
    }

    public void applyNoise(){
        this.currentFilter = "Noise";
        this.manipulatedImage = addGaussianNoise(cloneImage(originalImage),2, 50);
        repaint();
    }

    public void applyVintage(){
        BufferedImage firstStageImage = sepia(cloneImage(originalImage));
        this.currentFilter = "Vintage";
        this.manipulatedImage = addGaussianNoise(firstStageImage,10, 10);
        repaint();
    }

    public void applyDarker(){
        this.currentFilter = "Darker";
        this.manipulatedImage = darker(cloneImage(originalImage));
        repaint();
    }

    public void applyLighter(){
        this.currentFilter = "Lighter";
        this.manipulatedImage = lighter(cloneImage(originalImage));
        repaint();
    }

    private BufferedImage negative(BufferedImage bufferedImage) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color currentColor = new Color(bufferedImage.getRGB(x, y));
                int red = 255 - currentColor.getRed();
                int green = 255 - currentColor.getGreen();
                int blue = 255 - currentColor.getBlue();
                Color updatedColor = new Color(red, green, blue);
                bufferedImage.setRGB(x, y, updatedColor.getRGB());
            }
        }
        return bufferedImage;
    }

    private BufferedImage grayScale(BufferedImage bufferedImage) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color color = new Color(bufferedImage.getRGB(x, y));
                int average = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color newColor = new Color(average, average, average);
                bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return bufferedImage;
    }

    private BufferedImage tintImage(BufferedImage bufferedImage) {
        final int constant = 100;
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color color = new Color(bufferedImage.getRGB(x, y));
                int average = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color newColor = new Color(average, Math.min(255,average+constant), average);
                bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return bufferedImage;
    }

    private BufferedImage shiftRight(BufferedImage bufferedImage) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color color = new Color(bufferedImage.getRGB(x, y));
                Color newColor = new Color(color.getGreen(), color.getBlue(), color.getRed());
                bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return bufferedImage;
    }

    private BufferedImage shiftLeft(BufferedImage bufferedImage) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color color = new Color(bufferedImage.getRGB(x, y));
                Color newColor = new Color(color.getBlue(), color.getRed(), color.getGreen());
                bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return bufferedImage;
    }

    private BufferedImage sepia(BufferedImage bufferedImage){
        for (int x = 0; x < bufferedImage.getWidth() - 1; x++) {
            for (int y = 0; y < bufferedImage.getHeight() - 1; y++) {
                Color currentColor = new Color(bufferedImage.getRGB(x, y));
                int red = (int) ((currentColor.getRed() * 0.393)+(currentColor.getGreen() * 0.769)+
                        (currentColor.getBlue() * 0.189));
                int green = (int) ((currentColor.getRed() * 0.349)+(currentColor.getGreen() * 0.686)+
                        (currentColor.getBlue() * 0.168));
                int blue = (int) ((currentColor.getRed() * 0.272)+(currentColor.getGreen() * 0.534)+
                        (currentColor.getBlue() * 0.131));
                Color sepiaColor = new Color(Math.min(red, 255),Math.min(green,255),Math.min(blue,255));
                bufferedImage.setRGB(x,y,sepiaColor.getRGB());

            }
        }
        return bufferedImage;
    }

    private BufferedImage mirror(BufferedImage bufferedImage) {
        for (int x = 0; x < bufferedImage.getWidth() / 2; x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color currentColor = new Color(bufferedImage.getRGB(x, y));
                bufferedImage.setRGB(x,y, new Color (bufferedImage.getRGB(bufferedImage.getWidth() - x - 1,y)).getRGB());
                bufferedImage.setRGB(bufferedImage.getWidth() - x - 1, y, currentColor.getRGB());
            }
        }
        return bufferedImage;
    }

    private static BufferedImage addGaussianNoise(BufferedImage bufferedImage, double mean, double stddev) {
        Random random = new Random();
        BufferedImage noisyImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());

        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                Color color = new Color(bufferedImage.getRGB(x, y));

                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                int noisyR = (int) (r + random.nextGaussian() * stddev + mean);
                int noisyG = (int) (g + random.nextGaussian() * stddev + mean);
                int noisyB = (int) (b + random.nextGaussian() * stddev + mean);

                noisyR = Math.max(0, Math.min(255, noisyR));
                noisyG = Math.max(0, Math.min(255, noisyG));
                noisyB = Math.max(0, Math.min(255, noisyB));

                Color noisyColor = new Color(noisyR, noisyG, noisyB);
                noisyImage.setRGB(x, y, noisyColor.getRGB());
            }
        }

        return noisyImage;
    }

    private BufferedImage darker(BufferedImage bufferedImage) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color currentColor = new Color(bufferedImage.getRGB(x, y));
                int red = currentColor.getRed();
                int green = currentColor.getGreen();
                int blue = currentColor.getBlue();

                int factor = 100;

                red = Math.max(red - factor, 0);
                green = Math.max(green - factor, 0);
                blue = Math.max(blue - factor, 0);
                Color updatedColor = new Color(red, green, blue);
                bufferedImage.setRGB(x, y, updatedColor.getRGB());
            }
        }
        return bufferedImage;
    }

    private BufferedImage lighter(BufferedImage bufferedImage) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color currentColor = new Color(bufferedImage.getRGB(x, y));
                int red = currentColor.getRed();
                int green = currentColor.getGreen();
                int blue = currentColor.getBlue();

                int factor = 100;

                red = Math.min(red + factor, 255);
                green = Math.min(green + factor, 255);
                blue = Math.min(blue + factor, 255);
                Color updatedColor = new Color(red, green, blue);
                bufferedImage.setRGB(x, y, updatedColor.getRGB());
            }
        }
        return bufferedImage;
    }


    private BufferedImage cloneImage(BufferedImage image) {
        BufferedImage clone = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = clone.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return clone;
    }
}

