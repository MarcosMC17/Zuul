

public class Item
{
    private String descripcion;
    private int peso;
    private boolean sePuedeCoger;
    private String id;

    public Item(String id, String descripcion, int peso, boolean sePuedeCoger) 
    {
        this.id=id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.sePuedeCoger = sePuedeCoger;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()    {
        return descripcion;
    }

    public int getPeso(){
        return peso;
    }

    public String getInfoItem(){
        String infoItem = id + ": " + descripcion + "\n" + peso + " kg";
        return infoItem;
    }
    
    public String getId()    {
        return id;
    }
    
    public boolean getSePuedeCoger(){
        return sePuedeCoger;
    }
    
}
