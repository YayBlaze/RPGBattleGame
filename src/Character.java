import java.util.Scanner;

public class Character {
    public String name;
    public int str;
    public int def;
    public int hp = 100;
    public int lvl = 1;
    public String type = "";
    public int typeint = 0;
    public int[] inv = {0, 0, 0};


    public double takeDamage(double damage) {
        double dmg = damage - this.def;
        if (dmg < 0) dmg = 0;
        this.hp -= dmg;
        return dmg;
    }

    public double attack(Character target) {
        double damage = this.str * 2;
        return target.takeDamage(damage);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void setStats(int type) {
        this.hp = 100;
        this.typeint = type;
        switch (type) {
            case 0 -> {
                this.def = (this.lvl/2) * 15 + this.inv[2];
                this.str = (this.lvl/2) * 10 + this.inv[1];
                this.type = "Tank";
            }
            case 1 -> {
                this.def = (this.lvl/2) * 10 + this.inv[2];
                this.str = (this.lvl/2) * 15 + this.inv[1];
                this.type = "Warrior";
            }
            case 2 -> {
                this.def = (this.lvl/2) * 5 + this.inv[2];
                this.str = (this.lvl/2) * 20 + this.inv[1];
                this.type = "Beast";
            }
            default -> {
                System.out.println("That isn't an option! You can be a warrior I guess.");
                this.def = (this.lvl/2) * 10 + this.inv[2];
                this.str = (this.lvl/2) * 15 + this.inv[1];
            }
        }
    }
    public void viewStats() {
        Scanner userInput = new Scanner(System.in);
        System.out.println(this.name+"'s Stats:\n\nStrength: "+this.str+"\nDefence: "+this.def+"\nHealth: "+this.hp+"\nLevel: "+this.lvl+"\nHealth Potions: "+this.inv[0]);
        String item = "None";
        switch (this.inv[1]) {
            case 0:
                item = "None";
                break;
            case 1:
                item = "Rapier";
                break;
            case 2:
                item = "Long Sword";
                break;
            case 5:
                item = "Great Sword";
                break;
            case 10:
                item = "Katana";
                break;
            default:
                item = "Error";
        }
        System.out.println("Weapon: "+item);
        switch (this.inv[2]) {
            case 0:
                item = "None";
                break;
            case 1:
                item = "Wooden Breastplate";
                break;
            case 3:
                item = "Iron Breastplate";
                break;
            case 10:
                item = "Titanium Breastplate";
                break;
            default:
                item = "Error";
        }
        System.out.println("Armor: "+item);
        System.out.println("\n1: Back to Menu");
        userInput.nextLine();
    }
}

