import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrame extends JFrame {

    //Creating a String Array of filter names
    final String[] filters = {"Original", "Negative", "Gray Scale", "Tint", "Shift Right", "Shift Left", "Sepia", "Mirror", "Noise", "Vintage","Darker","Lighter"};

    public ImageFrame(File fileToProcess) {

        if (fileToProcess.exists()) {
            try {
                BufferedImage bufferedImage = ImageIO.read(fileToProcess);
                ImagePanel displayImage = new ImagePanel(bufferedImage);

                JComboBox<String> filterBox = getStringComboBox(filters, displayImage);

                this.setLayout(new BorderLayout());
                this.add(filterBox, BorderLayout.SOUTH);
                this.add(displayImage, BorderLayout.CENTER);
                this.setTitle("Image Process Project");
                this.setIconImage(new ImageIcon(getClass().getResource("/Images/iconforproject.png")).getImage());
                this.setResizable(false);
                this.pack();
                this.setLocationRelativeTo(null);
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                this.setVisible(true);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("File does not exist: " + fileToProcess.getAbsolutePath());
        }
    }

    private static JComboBox<String> getStringComboBox(String[] filters, ImagePanel displayImage) {
        JComboBox<String> filterBox = new JComboBox<>(filters);

        filterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) filterBox.getSelectedItem();
                System.out.println("Current Filter is: "+ selectedItem);
                if (selectedItem != null) {
                    switch (selectedItem) {
                        case "Original":
                            displayImage.showOriginal();
                            break;
                        case "Negative":
                            displayImage.applyNegative();
                            break;
                        case "Gray Scale":
                            displayImage.applyGrayScale();
                            break;
                        case "Tint":
                            displayImage.applyTint();
                            break;
                        case "Shift Right":
                            displayImage.applyShiftRight();
                            break;
                        case "Shift Left":
                            displayImage.applyShiftLeft();
                            break;
                        case "Sepia":
                            displayImage.applySepia();
                            break;
                        case "Mirror":
                            displayImage.applyMirror();
                            break;
                        case "Noise":
                            displayImage.applyNoise();
                            break;
                        case "Vintage":
                            displayImage.applyVintage();
                            break;
                        case "Darker":
                            displayImage.applyDarker();
                            break;
                        case "Lighter":
                            displayImage.applyLighter();
                            break;
                    }
                }
            }
        });
        return filterBox;
    }

}
