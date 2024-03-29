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
 * @author  Michael Kölling and David J. Barnes
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
        Item ropaSucia = new Item("ropaSucia", "Un monton de ropa sucia para lavar", 20, true);
        Item cervecita = new Item("cervecita", "Cervecita bien fresquita", 0, true);
        // create the rooms
        miHabitacion = new Room("Mi habitaci�n, la misma que tu madre te dice que recojas todos los dias");
        miBanio = new Room("Mi ba��, sin limpiar desde 1945");
        pasillo = new Room("El pasillo");
        habitacionPadres = new Room("La habitaci�n de mis padres, sigo sin comprender donde meten las cosas");
        banioPadres = new Room("El ba�o de mis padres, las estanterias est�n llenas de adornos, colonias y exceso de productos de higiene");
        hall = new Room("Hall, donde est� el telefono fijo, pero como est� de adorno, pues de poco me sirve");
        cocina = new Room("La cocina, de estilo americano con isla, muebles de caoba y encimeras de marmol");
        salon = new Room("El sal�n, hay una mesa mesa grande rodeada de silla y una tele del tama�o de la pared, si compramos otra mas grande nos tendremos que ir de casa");
        salaJuegos = new Room("Esta es la puerta de la habitaci�n que siempre est� cerrada, mejor me voy de aqu�...Antes de ver algo fuera de contexto...");
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
        miHabitacion.addItem("ropaSucia",ropaSucia);
        cocina.addItem("cervecita", cervecita);
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
        else if (commandWord.equals("drop")) {
            player.drop(command);
        }
        else if (commandWord.equals("items")) {
            player.items();
        }
        else if (commandWord.equals("beber")) {
            player.objetoEspecial(command);
        }

        return wantToQuit;
    }

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

}
