import java.util.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    public Room currentRoom;
    private Stack<Room> prevRooms;
    
    

    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom)
    {
        prevRooms = new Stack<Room>();
        this.currentRoom = currentRoom;
    }
    
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            prevRooms.push(currentRoom);
            currentRoom = nextRoom;
            look();
        }
    }
    
    public void back() 
    {

        if (!prevRooms.empty()) {
            currentRoom = prevRooms.pop();    
        }

        else{
            System.out.println("No hay habitacion anterior");
        }
    }
    
    public void look() {   
        System.out.println(currentRoom.getLongDescription());
    }
    
    public void eat(){
        System.out.println(currentRoom.getLongDescription());
    }
}
