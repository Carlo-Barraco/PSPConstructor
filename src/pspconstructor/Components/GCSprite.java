package pspconstructor.Components;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Carlo
 */
public class GCSprite extends GCBase
{

    public String Filename = "", FilePath = "";
    public ImageIcon Image = new ImageIcon(getClass().getResource("/pspconstructor/Images/BlankSprite.png"));
    public int Width = 0, Height = 0, OriginX = 0, OriginY = 0;

    public GCSprite(String name)
    {
        super(name);
    }

    public String getFilePath()
    {
        return FilePath;
    }

    public String getFilename()
    {
        return Filename;
    }

    public int getHeight()
    {
        return Height;
    }

    public ImageIcon getImage()
    {
        return Image;
    }

    public int getOriginX()
    {
        return OriginX;
    }

    public int getOriginY()
    {
        return OriginY;
    }

    public int getWidth()
    {
        return Width;
    }

    public int getID()
    {
        return ID;
    }

    public void setFileName(String filename)
    {
        Filename = filename;
    }

    public void setFilePath(String filepath)
    {
        FilePath = filepath;
    }

    public void setImage(ImageIcon image)
    {
        Image = image;
    }

    public void setWidth(int width)
    {
        Width = width;
    }

    public void setHeight(int height)
    {
        Height = height;
    }

    public void setOriginX(int x)
    {
        OriginX = x;
    }

    public void setOriginY(int y)
    {
        OriginY = y;
    }

    public ArrayList<String> getCode()
    {
        ArrayList<String> codeBlocks = new ArrayList<String>();
        //get the sprite's final name
        //ComponentHandler.Sprites.get(logMain.tnGC.getChildAt(0).getIndex((TreeNode) (logMain.getSelectionPath().getLastPathComponent()))).setName(logMain.getSelectionPath().getLastPathComponent().toString());
        codeBlocks.add(Name);
        return codeBlocks;
    }
}
