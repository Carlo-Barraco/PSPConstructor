/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pspconstructor.Components;

/**
 *
 * @author Carlo
 */
public class GCBase
{
    public String Name = "";
    public int ID = -1;

    public GCBase(String name)
    {
        Name = name;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }
    
}
