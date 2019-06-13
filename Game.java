import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();

        player = new Player(createRooms());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room miHabitacion, miBanio, pasillo, habitacionPadres, banioPadres, hall, cocina, salon, salaJuegos;
        Item movil = new Item("movil", "AiFon de ultima generacion", 1, true);
        Item libro = new Item("libro", "Libro de programacion basica en java", 500, false);
        // create the rooms
        miHabitacion = new Room("Mi habitación, la misma que tu madre te dice que recojas todos los dias");
        miBanio = new Room("Mi bañó, sin limpiar desde 1945");
        pasillo = new Room("El pasillo");
        habitacionPadres = new Room("La habitación de mis padres, sigo sin comprender donde meten las cosas");
        banioPadres = new Room("El baño de mis padres, las estanterias están llenas de adornos, colonias y exceso de productos de higiene");
        hall = new Room("Hall, donde está el telefono fijo, pero como está de adorno, pues de poco me sirve");
        cocina = new Room("La cocina, de estilo americano con isla, muebles de caoba y encimeras de marmol");
        salon = new Room("El salón, hay una mesa mesa grande rodeada de silla y una tele del tamaño de la pared, si compramos otra mas grande nos tendremos que ir de casa");
        salaJuegos = new Room("Esta es la puerta de la habitación que siempre está cerrada, mejor me voy de aquí...Antes de ver algo fuera de contexto...");
        // initialise room exits n e s o se nw
        miHabitacion.setExit("west", miBanio);
        miHabitacion.setExit("south", pasillo);
        miBanio.setExit("east", miHabitacion);
        pasillo.setExit("north", miHabitacion);
        pasillo.setExit("south", habitacionPadres);
        pasillo.setExit("east", salaJuegos);
        pasillo.setExit("west", hall);
        pasillo.setExit("northWest", salon);
        habitacionPadres.setExit("north", pasillo);
        habitacionPadres.setExit("west", banioPadres);
        banioPadres.setExit("east", habitacionPadres);
        hall.setExit("east", pasillo);
        hall.setExit("north", salon);
        hall.setExit("south", cocina);
        cocina.setExit("north", hall);
        salon.setExit("south", hall);
        salon.setExit("southEast", pasillo);
        salaJuegos.setExit("west", pasillo);

        salon.addItem("movil", movil);
        salon.addItem("libro", libro);

        return miHabitacion;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        player.look();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            player.goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            player.look();
        }
        else if (commandWord.equals("eat")) {
            player.eat();
        }
        else if (commandWord.equals("back")) {
            player.back();
        }
        else if (commandWord.equals("take")) {
            player.take(command);
        }
        /*else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("items")) {
            items();
        }*/

        return wantToQuit;
    }

    /*private void look() {   
    System.out.println(currentRoom.getLongDescription());
    }**/

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the house.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());

    }

    /*private void items(){
        String itemsMochila = "";
        Collection <Item> objItem = mochila.values();
        for(Item objActual : objItem){
            itemsMochila = itemsMochila + "\n" + objActual.getInfoItem();
        }
        System.out.println(itemsMochila);
    }*/

    /*private void drop(Command command){
        String obj = command.getSecondWord();
        if(mochila.containsKey(obj)){
            player.currentRoom.addItem(mochila.get(obj).getId(), mochila.get(obj));
            pesoMochila = pesoMochila - mochila.get(obj).getPeso();
            mochila.remove(obj);
        }
        else{
            System.out.println("No llevas ese objeto en la mochila");
        }
    }*/

    /*/** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.

    private void goRoom(Command command) 
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
    printLocationInfo();
    }
    }**/

    /*private void take(Command command){
        String obj = command.getSecondWord();
        if(player.currentRoom.getItem(obj) != null){
            if(player.currentRoom.getItem(obj).getSePuedeCoger()){
                if((pesoMochila + player.currentRoom.getItem(obj).getPeso()) <= pesoMax){
                    mochila.put(obj, player.currentRoom.getItem(obj));
                    int pesoItem = player.currentRoom.getItem(obj).getPeso();
                    player.currentRoom.eliminarItem(obj);                    
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

    }*/

    /*private void back() 
    {

    if (!prevRooms.empty()) {
    currentRoom = prevRooms.pop();    
    printLocationInfo();
    }

    else{
    System.out.println("No hay habitacion anterior");
    }
    }*/

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /*private void printLocationInfo(){
    System.out.println(currentRoom.getLongDescription());

    System.out.println();
    }*/
}
