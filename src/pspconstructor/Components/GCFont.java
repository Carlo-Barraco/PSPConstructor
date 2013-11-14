package pspconstructor.Components;

/**
 *
 * @author Carlo
 */
public class GCFont extends GCBase {

    public String Filename = "", FilePath = "";

    public GCFont(String name)
    {
        super(name);
    }
    
    public void setFileName(String filename) {
        Filename = filename;
    }

    public void setFilePath(String filepath) {
        FilePath = filepath;
    }
}
