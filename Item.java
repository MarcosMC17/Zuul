

public class Item
{
    private String descripcion;
    private int peso;

    public Item(String descripcion, int peso) 
    {
        this.descripcion = descripcion;
        this.peso = peso;
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
        String infoItem = descripcion + "\n" + peso + " kg";
        return infoItem;
    }
}
