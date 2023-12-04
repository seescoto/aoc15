public class Ingredient implements Comparable<Ingredient> {

    public String name;
    public int capacity, durability, flavor, texture, calories;
    public int amount;

    public Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
        this.name = name;
        this.capacity = capacity;
        this.durability = durability;
        this.flavor = flavor;
        this.texture = texture;
        this.calories = calories;
    }

    public Ingredient(Ingredient other) {
        this.name = other.name;
        this.amount = other.amount;
        this.capacity = other.capacity;
        this.durability = other.durability;
        this.flavor = other.flavor;
        this.texture = other.texture;
        this.calories = other.calories;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is itself then return true
        if (o == this) {
            return true;
        }

        // Check if o is an instance of ingredient or not
        if (!(o instanceof Ingredient)) {
            return false;
        }

        // return true if all data members are the same
        Ingredient c = (Ingredient) o;
        return Integer.compare(this.capacity, c.capacity) == 0
                && Integer.compare(this.durability, c.durability) == 0
                && Integer.compare(this.flavor, c.flavor) == 0
                && Integer.compare(this.texture, c.texture) == 0
                && Integer.compare(this.calories, c.calories) == 0
                && Integer.compare(this.amount, c.amount) == 0
                && this.name.equals(c.name);
    }

    @Override
    public String toString() {
        return this.name + ":\n"
                + "\tamount: " + this.amount + " tsps\n"
                + "\tcapacity: " + this.capacity + "\n"
                + "\tdurability: " + this.durability + "\n"
                + "\tflavor: " + this.flavor + "\n"
                + "\ttexture: " + this.texture + "\n"
                + "\tcalories: " + this.calories + "\n";
    }

    public int compareTo(Ingredient i) {
        if (!i.name.equals(this.name))
            return this.name.compareTo(i.name);
        if (Integer.compare(this.amount, i.amount) != 0)
            return Integer.compare(this.amount, i.amount);
        if (Integer.compare(this.durability, i.durability) != 0)
            return Integer.compare(this.durability, i.durability);
        if (Integer.compare(this.flavor, i.flavor) != 0)
            return Integer.compare(this.flavor, i.flavor);
        if (Integer.compare(this.texture, i.texture) != 0)
            return Integer.compare(this.texture, i.texture);
        if (Integer.compare(this.calories, i.calories) != 0)
            return Integer.compare(this.calories, i.calories);

        // else are the same
        return 0;
    }

}
