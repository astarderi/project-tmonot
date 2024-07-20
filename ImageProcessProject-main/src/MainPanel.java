import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public static final int MARGIN = 40;

    private Image backgroundImage;

    public MainPanel() {
        this.backgroundImage = new ImageIcon(getClass().getResource("/Images/background.png")).getImage();
        this.setPreferredSize(new Dimension(this.backgroundImage.getWidth(this), this.getBackgroundImage().getHeight(this)));
        this.setBounds(0, 1, this.backgroundImage.getWidth(this), this.backgroundImage.getHeight(this));
        this.setLayout(null);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, this);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Image Process Project", (this.getWidth() / 3) - MARGIN, 50);
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }
}
