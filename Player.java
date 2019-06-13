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
    private int pesoMochila;
    private int pesoMax;
    private HashMap<String, Item> mochila;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom)
    {
        prevRooms = new Stack<Room>();
        mochila = new HashMap<>();
        pesoMochila = 0;
        pesoMax = 20;
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
            look();
        }

        else{
            System.out.println("No hay habitacion anterior");
        }
    }

    public void look() {   
        System.out.println(currentRoom.getLongDescription());
    }

    public void eat(){
        System.out.println("You have eaten now and you are not hungry any more");
    }

    public void take(Command command){
        String obj = command.getSecondWord();
        if(currentRoom.getItem(obj) != null){
            if(currentRoom.getItem(obj).getSePuedeCoger()){
                if((pesoMochila + currentRoom.getItem(obj).getPeso()) <= pesoMax){
                    mochila.put(obj, currentRoom.getItem(obj));
                    int pesoItem = currentRoom.getItem(obj).getPeso();
                    currentRoom.eliminarItem(obj);                    
                    pesoMochila = pesoMochila + pesoItem;
                }
                else{
                    System.out.println("Si coges ese objeto superaras el peso maximo de tu mochila");
                }
            }
            else{
                System.out.println("Ese objeto no se puede recoger");
            }
        }

        else{
            System.out.println("Ese objeto no existe o no está en esta sala");
        }

    }

    public void items(){
        String itemsMochila = "Total en kg: " + pesoMochila + "\n";
        Collection <Item> objItem = mochila.values();
        for(Item objActual : objItem){
            itemsMochila = itemsMochila + "\n" + objActual.getInfoItem();
        }
        System.out.println(itemsMochila);
    }

    public void drop(Command command){
        String obj = command.getSecondWord();
        if(mochila.containsKey(obj)){
            currentRoom.addItem(mochila.get(obj).getId(), mochila.get(obj));
            pesoMochila = pesoMochila - mochila.get(obj).getPeso();
            mochila.remove(obj);
        }
        else{
            System.out.println("No llevas ese objeto en la mochila");
        }
    }

    /*public void objetoEspecial(Command command){
        if(command.getSecondWord().equals("cervecita")){
            if(mochila.containsKey(command.getSecondWord())){
                mochila.remove(command.getSecondWord());
                pesoMax = pesoMax * 1000;
                System.out.println("Acabas de ingerir escarcha pura para romper tus alveolos, tu peso maximo es multiplicado por mil, ahora puedes llevar " + pesoMax + " kg encima");
            }
            else{
                System.out.println("No llevas ese objeto en la mochila");
            }
        }
        else{
                System.out.println("Yo no me beberia eso, si es que se puede beber");
            }
    }*/
}
