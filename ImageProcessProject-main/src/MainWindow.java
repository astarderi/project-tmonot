import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWindow extends JFrame {

    private File fileToOpen;
    private boolean isGoodToGo;

    public MainWindow() {
        MainPanel panel = new MainPanel();
        this.setSize(panel.getBackgroundImage().getWidth(panel), panel.getBackgroundImage().getHeight(panel) + MainPanel.MARGIN);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Image Process Project");
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/iconforproject.png")).getImage());
        this.setVisible(true);
        this.setLayout(new BorderLayout());


        JButton start = new JButton("פתח תמונה");
        this.add(start, BorderLayout.SOUTH);
        mainAction(start);
        this.add(panel, BorderLayout.CENTER);

    }

    public void mainAction(JButton start) {

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creating a JFileChooser that opens the file explorer in InitialPath Location
                //Filter type of files in JFileChoose using FileNameExtensionFilter class
                File initialPath = new File("C:\\");
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG & GIF Images", "jpg", "png", "gif");
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(initialPath);
                fileChooser.setDialogTitle("Select a file");

                int userSelection = fileChooser.showOpenDialog(null);

                //Checking if there is a user selection, and showing file name and path
                //If a file has been selected, boolean isGoodToGo changes to TRUE
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    fileToOpen = fileChooser.getSelectedFile();

                    JOptionPane.showMessageDialog(null, "Selected file: " +
                            fileToOpen.getName() + "\nFile Path is: " + fileToOpen.getAbsolutePath());
                    isGoodToGo = true;
                }

                //If isGoodToGo is TRUE we can proceed to next stage of image manipulation by opening ImageFrame, and disposing the MainWindow
                if (isGoodToGo) {
                    new ImageFrame(fileToOpen);
                    dispose();
                }
            }
        });
    }
}
