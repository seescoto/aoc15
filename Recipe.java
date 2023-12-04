import java.util.ArrayList;
import java.util.Collections;

public class Recipe {

    private ArrayList<Ingredient> ingredients;
    public int score;

    // idk make a recipe with different ingredients, all the ingredients amounts
    // must add up to 100

    public Recipe(ArrayList<Ingredient> ingredients) {
        if (!this.setIngredients(ingredients)) {
            System.out.println("Error setting ingredients... please make sure all amounts add to 100.");

        }
        this.score = this.getScore();
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public boolean setIngredients(ArrayList<Ingredient> ingredients) {
        // all amounts must sum to 100
        int totalAmount = 0;
        Collections.sort(ingredients); // so comparing works later
        for (Ingredient i : ingredients) {
            totalAmount += i.amount;
        }
        if (totalAmount == 100) {
            this.ingredients = ingredients;
            return true;
        }

        return false;
    }

    public int getScore() {
        // return the total 'score' of the recipe - ingredients
        // aka
        // score = [product over ingredient feature] of
        // -> -> -> -> ([sum over ingredient in recipe] of
        // -> -> -> -> -> -> amount * feature Score)
        // DOESNT INCLUDE CALORIES

        // bad form to iterate over class attributes in java, will do the long way
        // public int capacity, durability, flavor, texture, calories;

        int scores[] = { 0, 0, 0, 0 }; // capacity, durability, flavor, texture
        int totalScore = 1;

        for (Ingredient i : this.ingredients) {
            // capacity = 0, durability = 1
            // flavor = 2, texture = 3
            scores[0] += i.amount * i.capacity;
            scores[1] += i.amount * i.durability;
            scores[2] += i.amount * i.flavor;
            scores[3] += i.amount * i.texture;
        }

        // IF ANY SCORES ARE NEGATIVE, MAKE IT ZERO!
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] < 0) {
                scores[i] = 0;
            }
            totalScore *= scores[i];
        }

        return totalScore;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is itself then return true
        if (o == this) {
            return true;
        }

        // Check if o is an instance of ingredient or not
        if (!(o instanceof Recipe)) {
            return false;
        }

        Recipe c = (Recipe) o;

        // return true if all data members are the same
        if (c.ingredients.size() != this.ingredients.size())
            return false;
        for (int i = 0; i < this.ingredients.size(); i++) {
            if (this.ingredients.get(i) != c.ingredients.get(i))
                return false;
        }
        // must be true
        return true;
    }

    @Override
    public String toString() {
        String ret = "Recipe:\n";
        for (Ingredient i : this.ingredients) {
            ret += "\t" + i + "\n";
        }
        return ret;

    }

}
