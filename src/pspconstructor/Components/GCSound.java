package pspconstructor.Components;

/**
 *
 * @author Carlo
 */
public class GCSound extends GCBase{

    public String Filename = "", FilePath = "";

    public GCSound(String name) {
        super(name);
    }

    public void setFileName(String filename) {
        Filename = filename;
    }

    public void setFilePath(String filepath) {
        FilePath = filepath;
    }
}
