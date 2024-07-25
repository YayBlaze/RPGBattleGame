import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;
public class main {
    public static Random generator = new Random();
    public static int coins = 5;
    public static int games_won = 0;
    public static int winstreak = 0;
    public static int lvlcost = 15;

    public static void main(String[] args) {
        int gameCoins = 0;
        Character player = new Character();
        Character npc = new Character();
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to the Battle Arena!\n\nYou will choose a class and go head-to head with a random opponent.\nAfter each round, if you win, you will be healed for a random amount between 1 and 100.\nPress Enter to Continue");
        userInput.nextLine();
        System.out.println("During the battle, you can choose to attack or dodge. If you attack, you have a 75% chase to hit your opponent, and your opponent has a 75% chance to hit you back.\nIf you dodge you have a 50% chance to attack your opponent and they have a 25% chance to attack you.\nMake sense?");
        userInput.nextLine();
        System.out.println("You start with 5 coins. You play double or nothing, for each opponent you defeat, you double your coins.\nBut if you lose, you lose all of your coins.\nYou can spend 15 coins to level up your character, increasing your stats.\nUnderstand?");
        userInput.nextLine();
        try {
            System.out.println("Would you like to:\n1: Create new Character\n2: Load from files\n\nNote: creating new character will override any previously saved characters.");
            int choice = userInput.nextInt();
            if (choice == 1) player = create(player);
            else if (choice == 2) {
                System.out.println("Attempting to load from files...");
                player = load(player);
            }
            else {
                System.out.println("That isn't an option! I'll create a new character for you. If that isn't what you wanted to do, restart the program.");
                player = create(player);
            }
        }
        catch (InputMismatchException e) {
            System.out.println("That isn't an option! I'll create a new character for you. If that isn't what you wanted to do, restart the program.");
            player = create(player);
        }
        while (true) {
            player.hp = 100;
            try {
                System.out.println("\n\n\n\n\nYou have " + coins + " coins.\nYou have won "+games_won+" games, and have a win streak of "+winstreak+" wins!");
                System.out.println("Would you like to:\n1: Play\n2: Level Up Character\n3: View Stats");
                if (player.lvl > 1) System.out.println("4: View Shop");
                System.out.println("5: Save & Quit");
                int choice = userInput.nextInt();
                if (choice == 2 && coins >= lvlcost) {
                    if (player.lvl < 5) {
                        System.out.println("Are you sure you want to spend "+lvlcost+" coins to level up?\n1: Yes\n2: No");
                        choice = userInput.nextInt();
                        if (choice == 1) {
                            coins -= lvlcost;
                            player.lvl++;
                            player.setStats(player.typeint);
                            lvlcost = lvlcost *2;
                            System.out.println("Upgrade complete.");
                        }
                        continue;
                    }
                    else {
                        System.out.println("You can't upgrade past level 5.");
                        continue;
                    }
                } else if (choice == 2) {
                    System.out.println("You need "+lvlcost+" coins to do this!");
                    continue;
                } else if (choice == 1) ;
                else if (choice == 3) {
                    player.viewStats();
                    continue;
                }
                else if (choice == 4) {
                    if (player.lvl > 1) {
                        shop(player);
                    }
                    else {
                        System.out.println("That's not an option!");
                    }
                    continue;
                }
                else if (choice == 5) {
                    System.out.println("Attempting to save to files...");
                    save(player);
                    break;
                }
                else {
                    System.out.println("That's not an option!");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("That's not an option! Try to give me a number next time.");
                userInput.nextLine();
                continue;
            }
            try {
                System.out.println("How many coins would you like to bet?");
                gameCoins = userInput.nextInt();
                if (gameCoins > coins) {
                    System.out.println("You can't bet more coins that you have.");
                    gameCoins = 0;
                    continue;
                } else coins -= gameCoins;
            } catch (InputMismatchException e) {
                System.out.println("That's not an option! Try to give me a number next time.");
                userInput.nextLine();
                continue;
            }
            String[] npcnames = {"The Rock", "Evil Bob", "Mr. Madness", "He who needs no introduction", "The Joker", "Caption Marvel", "The Man The Myth The Legend", "Jeffery... Jeffery Bezos"};
            startbattle(npc, npcnames, player);
            int kills = 0;
            while (true) {
                System.out.println("Would you like to:\n1: Attack\n2: Dodge");
                if (player.inv[0] > 0) System.out.println("3: Consume Health Potion");
                int choice = userInput.nextInt();
                int rng;
                double dmg;
                if (choice == 1) {
                    rng = generator.nextInt(4);
                    if (rng != 3) {
                        dmg = player.attack(npc);
                        System.out.println("You hit " + npc.name + " for " + dmg + " damage!");
                    } else System.out.println("You miss your attack!");
                    rng = generator.nextInt(4);
                    if (rng !=3) {
                        dmg = npc.attack(player);
                        System.out.println(npc.name + " hit you for " + dmg + " damage!");
                    } else System.out.println(npc.name + " misses their attack!");
                } else if (choice == 2) {
                    rng = generator.nextInt(4);
                    if (rng == 0) {
                        dmg = npc.attack(player);
                        System.out.println(npc.name + " hits you for " + dmg + " damage!");
                    } else System.out.println(npc.name + " misses their attack!");
                    rng = generator.nextInt(4);
                    if (rng == 0 || rng == 1) {
                        dmg = player.attack(npc);
                        System.out.println("You hit " + npc.name + " for " + dmg + " damage!");
                    } else System.out.println("You miss your attack!");
                }
                else if (choice == 3) {
                    if (player.inv[0] >0) {
                        int heal = generator.nextInt(51 -25)+25;
                        player.hp +=heal;
                        if (player.hp > 100) player.hp =100;
                        player.inv[0]--;
                        System.out.println("You were healed for "+heal+" health!");
                    }
                }
                else System.out.println("That's not an option!");
                if (player.hp <= 0) {
                    System.out.println("YOU LOSE!");
                    gameCoins = 0;
                    winstreak = 0;
                    break;
                } else if (npc.hp <= 0) {
                    System.out.println("YOU WIN!\n\n1: Continue\n2: Main Menu");
                    kills++;
                    games_won++;
                    winstreak++;
                    choice = userInput.nextInt();
                    if (choice == 1) {
                        int heal = generator.nextInt(101);
                        System.out.println("You are healed for " + heal + " health!");
                        player.hp += heal;
                        if (player.hp > 100) player.hp = 100;
                        System.out.println("You now have " + player.hp + " health!");
                        startbattle(npc, npcnames, player);
                    } else if (choice == 2) {
                        float i = 2;
                        float gcoins = 0;
                        for (; kills > 0; kills--) {
                            gcoins = gameCoins * i;
                            gameCoins = Math.round(gcoins);
                            i+=0.5;
                        }
                        coins += gameCoins;
                        break;
                    }
                } else {
                    System.out.println(player.name + " now has " + player.hp + " health!\n" + npc.name + " has " + npc.hp + " health!");
                }
            }
            if (coins == 0) {
                System.out.println("You have 0 coins...\nThat means you lose...\nHope you play again!");
                userInput.nextLine();
                userInput.nextLine();
                break;
            }
        }

    }

    static void startbattle(Character npc, String[] npcnames, Character player) {
        Random generator = new Random();
        npc.name = npcnames[generator.nextInt(npcnames.length)];
        switch (player.lvl) {
            case 1:
                npc.lvl = 1;
                break;
            case 2:
                npc.lvl = generator.nextInt(4-1)+1;
                break;
            case 3:
                npc.lvl = generator.nextInt(5-3)+3;
                break;
            case 4:
                npc.lvl = generator.nextInt(6-4)+4;
                break;
            case 5:
                npc.lvl = generator.nextInt(7-5)+5;
        }

        npc.setStats(generator.nextInt(3));
        System.out.println("Players: " + player.name + " vs " + npc.name);
        System.out.println("Health: " + player.hp + " vs " + npc.hp);
        System.out.println("Level: " + player.lvl + " vs " + npc.lvl);
        System.out.println("Class: " + player.type + " vs " + npc.type);
    }

    static void shop(Character player) {
        Scanner userInput = new Scanner(System.in);
        int choice = 0;
        while (choice != 9) {
            System.out.println("Welcome to the shop! Here are the items for sale:\n\n1: Health Potion- 25-50hp - 3gp\n2: Wooden Breastplate - +1 Def - 5gp\n3: Iron Breastplate - +3 Def - 7gp\n4: Titanium Breastplate - +10 Def - 20gp\n\n5: Rapier - +1 Str - 2gp\n6: Long Sword - +2 Str - 5gp\n7: Great Sword - +5 Str - 10gp\n8: Katana - +10 Str = 20gp\n\n9: Exit Shop");
            choice = userInput.nextInt();
            switch (choice) {
                case 1:
                    if (coins >=3) {
                        player.inv[0]++;
                        coins -= 3;
                    }
                    else System.out.println("You don't have enough coins!");
                    break;
                case 2:
                    if (coins >= 5) {
                        player.inv[2] = 1;
                        coins-=5;
                    }
                    else System.out.println("You don't have enough coins!");
                    break;
                case 3:
                    if (coins >=7) {
                        player.inv[2] = 3;
                        coins -=7;
                    }
                    else System.out.println("You don't have enough coins!");
                    break;
                case 4:
                    if (coins >=20) {
                        player.inv[2] = 10;
                        coins-=20;
                    }
                    else System.out.println("You don't have enough coins!");
                    break;
                case 5:
                    if (coins >=2) {
                        player.inv[1] = 1;
                        coins-=2;
                    }
                    else System.out.println("You don't have enough coins!");
                    break;
                case 6:
                    if (coins >=5) {
                        player.inv[1] = 2;
                        coins-=5;
                    }
                    else System.out.println("You don't have enough coins!");
                    break;
                case 7:
                    if (coins >=10) {
                        player.inv[1] = 5;
                        coins -=10;
                    }
                    else System.out.println("You don't have enough coins!");
                    break;
                case 8:
                    if (coins >=20) {
                        player.inv[1] = 10;
                        coins -=20;
                    }
                    else System.out.println("You don't have enough coins!");
                    break;
            }
            if (choice != 9) System.out.println("You have "+coins+" coins!");
        }
        player.setStats(player.typeint);
    }

    static void save(Character player) {
        try {
            File myObj = new File("save.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("save.txt");
            myWriter.write(player.name+"\n");
            myWriter.write(player.str+"\n");
            myWriter.write(player.def+"\n");
            myWriter.write(player.hp+"\n");
            myWriter.write(player.lvl+"\n");
            myWriter.write(player.typeint+"\n");
            myWriter.write(player.inv[0]+"\n"+player.inv[1]+"\n"+player.inv[2]+"\n");
            myWriter.write(coins+"\n");
            myWriter.write(games_won+"\n");
            myWriter.write(winstreak+"\n");
            myWriter.write(lvlcost+"\n");
            myWriter.close();
            System.out.println("Successfully saved to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    static Character load(Character player) {
        try {
            File myObj = new File("save.txt");
            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                System.out.println(data);
//            }
            String data = myReader.nextLine();
            player.name = data;
            int stat = myReader.nextInt();
            player.str = stat;
            stat = myReader.nextInt();
            player.def = stat;
            stat = myReader.nextInt();
            player.hp = stat;
            stat = myReader.nextInt();
            player.lvl = stat;
            stat = myReader.nextInt();
            player.typeint = stat;
            stat = myReader.nextInt();
            player.inv[0] = stat;
            stat = myReader.nextInt();
            player.inv[1] = stat;
            stat = myReader.nextInt();
            player.inv[2] = stat;
            stat = myReader.nextInt();
            coins = stat;
            stat = myReader.nextInt();
            games_won = stat;
            stat = myReader.nextInt();
            winstreak = stat;
            stat = myReader.nextInt();
            lvlcost = stat;
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("No File Found, Creating new Character...");
            e.printStackTrace();
        }
        player.setStats(player.typeint);
        return player;
    }

    static Character create(Character player) {
        Scanner userInput = new Scanner(System.in);
        try {
            System.out.println("Choose a class\n1: Tank - High Defence, Low Strength\n2: Warrior - Balanced\n3: Beast - Low Defence, High Strength");
            int answer = userInput.nextInt() - 1;
            player.setStats(answer);
        } catch (InputMismatchException e) {
            System.out.println("That isn't a option :/ I'll set you to the Warrior.");
            player.setStats(1);
        }
        userInput.nextLine();
        System.out.println("Enter your character's name.");
        player.name = userInput.nextLine();
        return player;
    }
}