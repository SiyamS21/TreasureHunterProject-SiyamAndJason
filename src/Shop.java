/**
 * The Shop class controls the cost of the items in the Treasure Hunt game.<p>
 * The Shop class also acts as a go between for the Hunter's buyItem() method.<p>
 */
import java.util.Scanner;

public class Shop
{
    // constants
    private static final int WATER_COST = 2;
    private static final int ROPE_COST = 4;
    private static final int MACHETE_COST = 6;
    private static final int HORSE_COST = 12;
    private static final int BOAT_COST = 20;


    // instance variables
    private double markdown;
    private Hunter customer;

    //Constructor
    public Shop(double markdown)
    {
        this.markdown = markdown;
        customer = null;
    }

    /** method for entering the shop
     * @param hunter  the Hunter entering the shop
     * @param buyOrSell  String that determines if hunter is "B"uying or "S"elling
     */
    public void enter(Hunter hunter, String buyOrSell)
    {
        customer = hunter;

        Scanner scanner = new Scanner(System.in);
        if (buyOrSell.equals("B") || buyOrSell.equals("b"))
        {
            System.out.println("Welcome to the shop! We have the finest wares in town.");
            System.out.println("Currently we have the following items:");
            System.out.println(inventory());
            System.out.print("What're you lookin' to buy? \n");
            String item = scanner.nextLine();
            item = item.toLowerCase();
            String item2 = item.toUpperCase();
            int cost = checkMarketPrice(item, true);
            int cost2 = checkMarketPrice(item2, true);
            if (cost == 0 || cost2 == 0)
            {
                System.out.println("We ain't got none of those.");
            }
            else
            {
                System.out.print("It'll cost you " + cost + " gold. Buy it (y/n)? ");
                String option = scanner.nextLine();

                if (option.equals("y") || option.equals("Y"))
                {
                    buyItem(item);
                }
            }
        }
        else
        {
            System.out.println("What're you lookin' to sell? ");
            System.out.print("You currently have the following items: \n" + customer.getInventory());
            String item = scanner.nextLine();
            String item1 = item.toLowerCase();
            String item2 = item.toUpperCase();
            int cost = checkMarketPrice(item1, false);
            int cost2 = checkMarketPrice(item2, false);
            if (cost == 0 || cost2 == 0)
            {
                System.out.println("We don't want none of those.");
            }
            else
            {
                System.out.print("It'll get you " + cost + " gold. Sell it (y/n)? ");
                String option = scanner.nextLine();

                if (option.equals("y") || option.equals("Y"))
                {
                    sellItem(item);
                }
            }
        }
    }

    /** A method that returns a string showing the items available in the shop (all shops sell the same items)
     *
     * @return  the string representing the shop's items available for purchase and their prices
     */
    public String inventory()
    {
        String str = "";
        if (TreasureHunter.getMode().equals("cheat")) {
             str = "\n************\nWater: " + 1 + " gold\n************\n";
            str += "Rope: " + 1 + " gold\n************\n";
            str += "Machete: " + 1 + " gold\n************\n";
            str += "Horse: " + 1 + " gold\n************\n";
            str += "Boat: " + 1 + " gold\n************\n";
        } else {
             str = "\n************\nWater: " + WATER_COST + " gold\n************\n";
            str += "Rope: " + ROPE_COST + " gold\n************\n";
            str += "Machete: " + MACHETE_COST + " gold\n************\n";
            str += "Horse: " + HORSE_COST + " gold\n************\n";
            str += "Boat: " + BOAT_COST + " gold\n************\n";
        }
        return str;
    }

    /**
     * A method that lets the customer (a Hunter) buy an item.
     * @param item The item being bought.
     */
    public void buyItem(String item)
    {
        int costOfItem = checkMarketPrice(item, true);
        String item1 = item.toLowerCase();
        String item2 = item.toUpperCase();
        if (customer.buyItem(item1, costOfItem) || customer.buyItem(item2, costOfItem))
        {
            System.out.println("Ye' got yerself a " + item + ". Come again soon.");
        }
        else
        {
            System.out.println("Hmm, either you don't have enough gold or you've already got one of those!");
        }
    }

    /**
     * A pathway method that lets the Hunter sell an item.
     * @param item The item being sold.
     */
    public void sellItem(String item)
    {
        String item1 = item.toUpperCase();
        String item2 = item1.toLowerCase();
        int buyBackPrice = checkMarketPrice(item, false);
        int buyBackPrice2 = checkMarketPrice(item1, false);
        int buyBackPrice3 = checkMarketPrice(item2, false);
        if (customer.sellItem(item, buyBackPrice) || (customer.sellItem(item1, buyBackPrice2) || (customer.sellItem(item2, buyBackPrice3)))) {

            System.out.println("Pleasure doin' business with you.");
        }
        else
        {
            System.out.println("Stop stringin' me along!");
        }
    }

    /**
     * Determines and returns the cost of buying or selling an item.
     * @param item The item in question.
     * @param isBuying Whether the item is being bought or sold.
     * @return The cost of buying or selling the item based on the isBuying parameter.
     */
    public int checkMarketPrice(String item, boolean isBuying)
    {
        if (isBuying)
        {
            return getCostOfItem(item);
        }
        else
        {
            return getBuyBackCost(item);
        }
    }

    /**
     * Checks the item entered against the costs listed in the static variables.
     *
     * @param item The item being checked for cost.
     * @return The cost of the item or 0 if the item is not found.
     */
    public int getCostOfItem(String item)
    {
        if (item.equals("Water") || item.equals("water") || item.equals("WATER"))
        {
            return WATER_COST;
        }
        else if (item.equals("Rope") || item.equals("rope") || item.equals("ROPE"))
        {
            return ROPE_COST;
        }
        else if (item.equals("Machete") || item.equals("machete") || item.equals("MACHETE"))
        {
            return MACHETE_COST;
        }
        else if (item.equals("Horse")|| item.equals("horse") || item.equals("HORSE"))
        {
            return HORSE_COST;
        }
        else if (item.equals("Boat") || item.equals("boat") || item.equals("BOAT"))
        {
            return BOAT_COST;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Checks the cost of an item and applies the markdown.
     *
     * @param item The item being sold.
     * @return The sell price of the item.
     */
    public int getBuyBackCost(String item)
    {
        int cost = (int)(getCostOfItem(item) * markdown);
        return cost;
    }
}