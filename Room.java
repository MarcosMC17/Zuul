import java.util.HashMap;
import java.util.*;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Room southEastExit;
    private Room northWestExit;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> objetos;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        objetos = new HashMap<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */

    /**
     * Define una salida para la habitacion.
     * @param direction El nombre de la direccion de la salida
     * @param neighbor La habitacion a la que se llega usando esa salida
     */
    public void setExit(String direction, Room neighbour){
        exits.put(direction, neighbour);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String dir){
        return exits.get(dir);
    }

    /**
     * Devuelve la informaci�n de las salidas existentes
     * Por ejemplo: "Exits: north east west"
     *
     * @return Una descripci�n de las salidas existentes.
     */
    public String getExitString(){
        Set <String> nDirecciones = exits.keySet();
        String exitsDesc = "Exit ";

        for(String dirActual : nDirecciones){
            exitsDesc = exitsDesc + dirActual + " ";
        }

        return exitsDesc;
    }
    
    public void addItem(String nombre, Item objeto){
        objetos.put(nombre, objeto);
    }
    
    public Item getItem(String nombreObj){
        return objetos.get(nombreObj);
    }
    
    public void eliminarItem(String nObjeto){
        objetos.remove(nObjeto);
    }
    
    
    /**
     * Devuelve un texto con la descripcion larga de la habitacion del tipo:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return Una descripcion de la habitacion incluyendo sus salidas
     */
    public String getLongDescription(){
        String desc = "You are " + description + "\n" + getExitString();
        if(objetos.size() >= 1){
            Collection <Item> objItem = objetos.values();
            for(Item objActual : objItem){
                desc = desc + "\n" + objActual.getInfoItem();
            }
        }
        return desc;
    }

}
