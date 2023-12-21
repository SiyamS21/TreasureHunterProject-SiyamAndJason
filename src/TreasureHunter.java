/**
 * This class is responsible for controlling the Treasure Hunter game.<p>
 * It handles the display of the menu and the processing of the player's choices.<p>
 * It handles all of the display based on the messages it receives from the Town object.
 *
 */
import java.util.Scanner;

public class TreasureHunter
{
    //Instance variables
    private Town currentTown;
    private Hunter hunter;
    private static boolean hardMode;

    private static boolean easyMode;
    private static boolean hunted = true;
    private static boolean cheatMode = false;

    //Constructor
    /**
     * Constructs the Treasure Hunter game.
     */
    public TreasureHunter()
    {
        // these will be initialized in the play method
        currentTown = null;
        hunter = null;
        hardMode = false;
        easyMode = false;
    }

    // starts the game; this is the only public method
    public void play ()
    {
        welcomePlayer();
        enterTown();
        showMenu();
    }

    /**
     * Creates a hunter object at the beginning of the game and populates the class member variable with it.
     */
    private void welcomePlayer()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to TREASURE HUNTER!");
        System.out.println("Going hunting for the big treasure, eh?");
        System.out.print("What's your name, Hunter? ");
        String name = scanner.nextLine();

        System.out.print("Which mode would you like to play with? Easy (1) / Medium (2) / Hard (3): ");
        String mode = scanner.nextLine();
        if (mode.equals("1"))
        {
            easyMode = true;
            hunter = new Hunter(name, 20);
        }
        else if (mode.equals("3")) {
            hardMode = true;
            hunter = new Hunter(name, 10);
        } else if (mode.equals("PopUpDownLeftRightCheatCodeSmoke")) {
            cheatMode = true;
            hunter = new Hunter(name, 100);
        }
        else {
            hunter = new Hunter(name, 10);
        }
    }

    /**
     * Creates a new town and adds the Hunter to it.
     */
    private void enterTown()
    {
        double markdown = 0.25;
        double toughness = 0.4;
        if (hardMode)
        {
            // in hard mode, you get less money back when you sell items
            markdown = 0.5;

            // and the town is "tougher"
            toughness = 0.75;
        }

        if (easyMode) {
            markdown = 0.1;
            toughness = 0.1;
        }

        // note that we don't need to access the Shop object
        // outside of this method, so it isn't necessary to store it as an instance
        // variable; we can leave it as a local variable
        Shop shop = new Shop(markdown);

        // creating the new Town -- which we need to store as an instance
        // variable in this class, since we need to access the Town
        // object in other methods of this class
        currentTown = new Town(shop, toughness);

        // calling the hunterArrives method, which takes the Hunter
        // as a parameter; note this also could have been done in the
        // constructor for Town, but this illustrates another way to associate
        // an object with an object of a different class
        currentTown.hunterArrives(hunter);
    }

    /**
     * Displays the menu and receives the choice from the user.<p>
     * The choice is sent to the processChoice() method for parsing.<p>
     * This method will loop until the user chooses to exit.
     */
    private void showMenu()
    {
        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while (!(choice.equals("X") || choice.equals("x")))
        {
            System.out.println();
            System.out.println(currentTown.getLatestNews());
            System.out.println("***");
            System.out.println(hunter);
            System.out.println(currentTown);
            System.out.println("(B)uy something at the shop.");
            System.out.println("(S)ell something at the shop.");
            System.out.println("(M)ove on to a different town.");
            System.out.println("(L)ook for trouble!");
            System.out.println("(H)unt for treasure!");
            System.out.println("Give up the hunt and e(X)it.");
            System.out.println();
            System.out.print("What's your next move? ");
            choice = scanner.nextLine();
            choice = choice.toUpperCase();
            processChoice(choice);
        }
    }

    /**
     * Takes the choice received from the menu and calls the appropriate method to carry out the instructions.
     * @param choice The action to process.
     */
    private void processChoice(String choice)
    {
        if (choice.equals("B") || choice.equals("b") || choice.equals("S") || choice.equals("s"))
        {
            currentTown.enterShop(choice);
        }
        else if (choice.equals("M") || choice.equals("m"))
        {
            if (currentTown.leaveTown())
            {
                //This town is going away so print its news ahead of time.
                System.out.println(currentTown.getLatestNews());
                enterTown();
            }
        }
        else if (choice.equals("L") || choice.equals("l"))
        {
            currentTown.lookForTrouble();
        }
        else if (choice.equals("H") || choice.equals("h"))
        {
            System.out.println(hunter.huntForTreasure(currentTown));
            if (hunter.haveAllTreasures()) {
                System.out.println("Congratulations you've collected all 3 treasures in the game! You win!");
                System.exit(0);
            }

        }
        else if (choice.equals("X") || choice.equals("x"))
        {
            System.out.println("Fare thee well, " + hunter.getHunterName() + "!");
        }
        else
        {
            System.out.println("Yikes! That's an invalid option! Try again.");
        }
    }
    public static boolean isHunted (){
        return hunted;
    }

    public static void setHunted(boolean hunted) {
        TreasureHunter.hunted = hunted;
    }

    public static String getMode() {
        if (hardMode) {
            return "hard";
        }
        else if (easyMode) {
            return "easy";
        } else if (cheatMode) {
            return "cheat";
        } else {
            return "normal";
        }
    }
}
