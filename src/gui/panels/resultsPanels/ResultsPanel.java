package gui.panels.resultsPanels;

import urlImage.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ResultsPanel<T> extends JPanel {
    private JScrollPane scrollPane;
    protected int itemsCount;

    public ResultsPanel() {
        itemsCount = 0;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(1, 8, 500, 8));
        setBackground(Color.WHITE);
    }


    public abstract void addItem(T item) throws IOException;

    public void reset() {
        itemsCount = 0;
        removeAll();
        revalidate();
    }

    public void addScrollPane(JFrame parent) {
        scrollPane = new JScrollPane(this,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        parent.add(scrollPane);
    }

    protected void setItemName(String name) {
        JTextField newArticle = new JTextField(name);
        newArticle.setBackground(Color.getColor("HeadColor"));
        newArticle.setEditable(false);
        newArticle.setFont(new Font(Font.SERIF, Font.BOLD, 22));
        add(newArticle);
    }

    protected void setHead(JTextArea head) {
        head.setEditable(false);
        add(head);
    }

    protected void setImage(String imagePath) throws IOException {
        JPanel panel = new JPanel();
        JLabel image = new JLabel();
        image.setIcon(new ImageIcon(resizeImage(imagePath)));
        panel.add(image);
        panel.setBackground(Color.WHITE);
        panel.setSize(image.getWidth(), image.getHeight());
        add(panel);
    }


    private BufferedImage resizeImage(String path) throws IOException {
        if (path == null || path.isEmpty())
            return new BufferedImage(1, 1, 1);
        BufferedImage originalImage = ImageIO.read(new File(path));
        if (originalImage.getWidth() <= 1400)
            return originalImage;

        double targetWidth = 1400;
        double targetHeight = originalImage.getHeight() / (originalImage.getWidth() / targetWidth);
        return Image.resize(originalImage, (int)targetWidth, (int)targetHeight);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
