package pspconstructor.Components;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Carlo
 */
public class GCBackground extends GCBase{

    public String Filename = "", FilePath = "";
    public Image Image = new ImageIcon(getClass().getResource("/pspconstructor/Images/Blank.png")).getImage();

    public GCBackground(String name) {
        super(name);
    }

    public void setFileName(String filename) {
        Filename = filename;
    }

    public void setFilePath(String filepath) {
        FilePath = filepath;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
