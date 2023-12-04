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
        prob2();
    }

    public static void prob1() {
        ArrayList<Ingredient> ingredients = getIngredients("day15_input.txt");
        // Set<ArrayList<Ingredient>> listOfRecipes = new HashSet<>();
        // dont store recipes, too much memory - just store scores
        Set<Integer> recipeScores = new HashSet<>();
        ArrayList<Ingredient> recipe = new ArrayList<>();
        createRecipe(recipeScores, recipe, ingredients);

        int maxScore = Integer.MIN_VALUE;
        for (Integer i : recipeScores) {
            if (i > maxScore) {
                maxScore = i;
            }
        }

        System.out.println(maxScore);

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

    // create all possible recipes - all combos of ingredient amounts

    public static void createRecipe(Set<Integer> list,
            ArrayList<Ingredient> ingredients, ArrayList<Ingredient> toAdd) {

        ArrayList<Ingredient> ingredientsCopy = new ArrayList<>(ingredients);
        ArrayList<Ingredient> toAddCopy = new ArrayList<>(toAdd);
        int totalAmount = getAmount(ingredients);

        // if have a full recipe - all ingredients in list, add to list of recipes
        if (toAdd.size() == 0) {
            // list.add(ingredients);
            list.add(getScore(ingredients));
        } else if (toAdd.size() == 1) {
            // if only one more ingredient to add, then make that amount (100 - totalAmount)
            // so it makes the full recipe be amount 100
            Ingredient i = toAddCopy.remove(0);
            i.amount = (100 - totalAmount);
            ingredientsCopy.add(i);
            createRecipe(list, ingredientsCopy, toAddCopy);
        } else {
            // permute like normal

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

    public static int getScore(ArrayList<Ingredient> arr) {
        int scores[] = { 0, 0, 0, 0 }; // capacity, durability, flavor, texture
        int totalScore = 1;

        for (Ingredient i : arr) {
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

    /*
     * --- Part Two ---
     * 
     * Your cookie recipe becomes wildly popular! Someone asks if you can make
     * another recipe that has exactly 500 calories per cookie (so they can use it
     * as a meal replacement). Keep the rest of your award-winning process the same
     * (100 teaspoons, same ingredients, same scoring system).
     * 
     * For example, given the ingredients above, if you had instead selected 40
     * teaspoons of butterscotch and 60 teaspoons of cinnamon (which still adds to
     * 100), the total calorie count would be 40*8 + 60*3 = 500. The total score
     * would go down, though: only 57600000, the best you can do in such trying
     * circumstances.
     * 
     * Given the ingredients in your kitchen and their properties, what is the total
     * score of the highest-scoring cookie you can make with a calorie total of 500?
     * 
     */

    public static int getCalories(ArrayList<Ingredient> arr) {
        int cals = 0;
        for (Ingredient i : arr) {
            cals += i.amount * i.calories;
        }

        return cals;
    }

    // only adds score if calories = 500
    public static void createMealReplacementRecipe(Set<Integer> list,
            ArrayList<Ingredient> ingredients, ArrayList<Ingredient> toAdd) {

        ArrayList<Ingredient> ingredientsCopy = new ArrayList<>(ingredients);
        ArrayList<Ingredient> toAddCopy = new ArrayList<>(toAdd);
        int totalAmount = getAmount(ingredients);

        // if have a full recipe - all ingredients in list, add to list of recipes
        if (toAdd.size() == 0) {
            if (getCalories(ingredients) == 500)
                list.add(getScore(ingredients));
        } else if (toAdd.size() == 1) {
            // if only one more ingredient to add, then make that amount (100 - totalAmount)
            // so it makes the full recipe be amount 100
            Ingredient i = toAddCopy.remove(0);
            i.amount = (100 - totalAmount);
            ingredientsCopy.add(i);
            createMealReplacementRecipe(list, ingredientsCopy, toAddCopy);
        } else {
            // permute like normal

            for (int j = 0; j < 100 - totalAmount + 1; j++) {
                for (Ingredient i : toAdd) {
                    // find ingredient in toAdd and add it to recipe, then recurse
                    toAddCopy.remove(i);
                    i.amount = j;
                    ingredientsCopy.add(i);
                    createMealReplacementRecipe(list, ingredientsCopy, toAddCopy);
                    // re-add ingredient to toAdd and loop again
                    toAddCopy.add(i);
                    ingredientsCopy.remove(i);
                }
            }

        }

    }

    public static void prob2() {
        ArrayList<Ingredient> ingredients = getIngredients("day15_input.txt");
        Set<Integer> recipeScores = new HashSet<>();
        ArrayList<Ingredient> recipe = new ArrayList<>();
        createMealReplacementRecipe(recipeScores, recipe, ingredients);

        int maxScore = Integer.MIN_VALUE;
        for (Integer i : recipeScores) {

            if (i > maxScore) {
                maxScore = i;
            }
        }

        System.out.println(maxScore);
    }
}
