package pspconstructor.Components;

/**
 *
 * @author Carlo
 */
public class GCParticle extends GCBase{

    public String Filename = "", FilePath = "";
    public GCSprite Sprite = new GCSprite("");
    
    public GCParticle(String name) {
        super(name);
    }
    
    public void setFileName(String filename) {
        Filename = filename;
    }

    public void setFilePath(String filepath) {
        FilePath = filepath;
    }
}
