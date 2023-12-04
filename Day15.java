import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day15 {

    /*
     * --- Day 15: Science for Hungry People ---
     * 
     * Today, you set out on the task of perfecting your milk-dunking cookie recipe.
     * All you have to do is find the right balance of ingredients.
     * 
     * Your recipe leaves room for exactly 100 teaspoons of ingredients. You make a
     * list of the remaining ingredients you could use to finish the recipe (your
     * puzzle input) and their properties per teaspoon:
     * 
     * capacity (how well it helps the cookie absorb milk)
     * durability (how well it keeps the cookie intact when full of milk)
     * flavor (how tasty it makes the cookie)
     * texture (how it improves the feel of the cookie)
     * calories (how many calories it adds to the cookie)
     * You can only measure ingredients in whole-teaspoon amounts accurately, and
     * you have to be accurate so you can reproduce your results in the future. The
     * total score of a cookie can be found by adding up each of the properties
     * (negative totals become 0) and then multiplying together everything except
     * calories.
     * 
     * For instance, suppose you have these two ingredients:
     * 
     * Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
     * Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
     * Then, choosing to use 44 teaspoons of butterscotch and 56 teaspoons of
     * cinnamon (because the amounts of each ingredient must add up to 100) would
     * result in a cookie with the following properties:
     * 
     * A capacity of 44*-1 + 56*2 = 68
     * A durability of 44*-2 + 56*3 = 80
     * A flavor of 44*6 + 56*-2 = 152
     * A texture of 44*3 + 56*-1 = 76
     * Multiplying these together (68 * 80 * 152 * 76, ignoring calories for now)
     * results in a total score of 62842880, which happens to be the best score
     * possible given these ingredients. If any properties had produced a negative
     * total, it would have instead become zero, causing the whole score to multiply
     * to zero.
     * 
     * Given the ingredients in your kitchen and their properties, what is the total
     * score of the highest-scoring cookie you can make?
     */

    public static void main(String args[]) {
        prob1();
    }

    public static void prob1() {
        ArrayList<Ingredient> ingredients = getIngredients("test.txt");
        Set<ArrayList<Ingredient>> listOfRecipes = new HashSet<>();
        ArrayList<Ingredient> recipe = new ArrayList<>();
        createRecipe(listOfRecipes, recipe, ingredients);
        // ArrayList<Recipe> recipes = new ArrayList<>();

        System.out.println(listOfRecipes.size());
        int maxScore = Integer.MIN_VALUE;
        for (ArrayList<Ingredient> r : listOfRecipes) {
            Recipe rec = new Recipe(r);
            // recipes.add(rec);
            if (maxScore < rec.score) {
                System.out.println(rec);
                System.out.println(rec.score);
                maxScore = rec.score;
            }

            // wrong total score is printing out, debug later
        }

        System.out.println(maxScore);

        // get permutations of different ingredients and amounts of them, get their
        // value
        // Permutation<Ingredient> perms = new Permutation<>(ingredients);

    }

    public static ArrayList<Ingredient> getIngredients(String fileName) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        File file = new File(fileName);
        try {
            Scanner input = new Scanner(file);
            String line;
            while (input.hasNextLine()) {
                line = input.nextLine();
                ingredients.add(makeIngredient(line));
            }
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    public static Ingredient makeIngredient(String str) {
        // given a string in this form
        // NAME: capacity N1, durability N2, flavor N3, texture N4, calories N5
        // make a new Ingredient with those features

        str = str.replace(",", "");
        String arr[] = str.split(" ");
        String name = arr[0].substring(0, arr[0].length() - 1); // take off the colon
        int cap, dur, flav, text, cals;

        cap = Integer.valueOf(arr[2]);
        dur = Integer.valueOf(arr[4]);
        flav = Integer.valueOf(arr[6]);
        text = Integer.valueOf(arr[8]);
        cals = Integer.valueOf(arr[10]);

        return new Ingredient(name, cap, dur, flav, text, cals);

    }

    // create all possible recipes - all combos of ingredients

    public static void createRecipe(Set<ArrayList<Ingredient>> list,
            ArrayList<Ingredient> ingredients, ArrayList<Ingredient> toAdd) {

        ArrayList<Ingredient> ingredientsCopy = cloneIngList(ingredients);
        ArrayList<Ingredient> toAddCopy = cloneIngList(toAdd);

        int totalAmount = getAmount(ingredients);

        // if have a full recipe - all ingredients in list, add to list of recipes
        if (toAdd.size() == 0) {
            list.add(ingredients);
        } else if (toAdd.size() == 1) {
            // if only one more ingredient to add, then make that amount (100 - totalAmount)
            // so it makes the full recipe be amount 100
            Ingredient i = toAddCopy.remove(0);
            i.amount = (100 - totalAmount);
            ingredientsCopy.add(i);
            createRecipe(list, ingredientsCopy, toAddCopy);
        } else {
            for (int j = 0; j < 100 - totalAmount + 1; j++) {
                for (Ingredient i : toAdd) {
                    // find ingredient in toAdd and add it to recipe, then recurse
                    toAddCopy.remove(i);
                    i.amount = j;
                    ingredientsCopy.add(i);
                    createRecipe(list, ingredientsCopy, toAddCopy);
                    // re-add ingredient to toAdd and loop again
                    toAddCopy.add(i);
                    ingredientsCopy.remove(i);
                }
            }

        }

    }

    public static int getAmount(ArrayList<Ingredient> arr) {
        int total = 0;
        for (Ingredient i : arr) {
            total += i.amount;
        }

        return total;
    }

    public static ArrayList<Ingredient> cloneIngList(ArrayList<Ingredient> og) {
        ArrayList<Ingredient> newList = new ArrayList<>();
        for (Ingredient i : og) {
            Ingredient newI = new Ingredient(i);
            newList.add(newI);
        }
        return newList;
    }

}
