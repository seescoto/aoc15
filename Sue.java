import java.util.Arrays;

public class Sue {
    public int id;
    public int children, cats, samoyeds, pomeranians, akitas, vizslas;
    public int goldfish, trees, cars, perfumes;

    public Sue(String line) {
        // all instance vars EXCEPT id are optional
        // line is in the form
        // Sue NUM: FEATURE1: COUNT1, FEATURE2: COUNT2 ..... FEATURE10: COUNT 10
        // features can be up to 10 if all are defined, but prob wont be
        line = line.replace(":", "").replace(",", "");
        String words[] = line.split(" ");
        this.id = Integer.valueOf(words[1]); // remove colon after
        // set all values as -1 first
        this.children = this.cats = this.samoyeds = this.pomeranians = this.akitas = -1;
        this.vizslas = this.goldfish = this.trees = this.cars = this.perfumes = -1;

        // replace values if they exist - skip first two strings in array
        this.setValues(Arrays.copyOfRange(words, 0, words.length));

    }

    public Sue(int id) {
        // if making each feature manually, set all to -1 and replace manually
        this.id = id;
        this.children = this.cats = this.samoyeds = this.pomeranians = this.akitas = -1;
        this.vizslas = this.goldfish = this.trees = this.cars = this.perfumes = -1;

    }

    private void setValues(String words[]) {
        // given words in pairs, set values
        String feature;
        int count;
        for (int i = 0; i < words.length; i += 2) {
            feature = words[i];
            // get int value of count, remove comma and newline if it's in it
            count = Integer.valueOf(words[i + 1].replace(",", "").replace("\n", ""));
            switch (feature) {
                case "children":
                    this.children = count;
                    break;
                case "cats":
                    this.cats = count;
                    break;
                case "samoyeds":
                    this.samoyeds = count;
                    break;
                case "pomeranians":
                    this.pomeranians = count;
                    break;
                case "akitas":
                    this.akitas = count;
                    break;
                case "vizslas":
                    this.vizslas = count;
                    break;
                case "goldfish":
                    this.goldfish = count;
                    break;
                case "trees":
                    this.trees = count;
                    break;
                case "cars":
                    this.cars = count;
                    break;
                case "perfumes":
                    this.perfumes = count;
                    break;
            }
        }
    }

    public boolean matches(Sue s) {
        // returns true if this sue has (for all features) the same value as the other
        // sue OR if
        // the feature is undefined in one of them (-1)
        boolean child, cat, sam, pom, aki, viz, gold, tree, car, perf, tot;

        child = (this.children == s.children || this.children == -1 || s.children == -1);
        cat = (this.cats == s.cats || this.cats == -1 || s.cats == -1);
        sam = (this.samoyeds == s.samoyeds || this.samoyeds == -1 || s.samoyeds == -1);
        pom = (this.pomeranians == s.pomeranians || this.pomeranians == -1 || s.pomeranians == -1);
        aki = (this.akitas == s.akitas || this.akitas == -1 || s.akitas == -1);
        viz = (this.vizslas == s.vizslas || this.vizslas == -1 || s.vizslas == -1);
        gold = (this.goldfish == s.goldfish || this.goldfish == -1 || s.goldfish == -1);
        tree = (this.trees == s.trees || this.trees == -1 || s.trees == -1);
        car = (this.cars == s.cars || this.cars == -1 || s.cars == -1);
        perf = (this.perfumes == s.perfumes || this.perfumes == -1 || s.perfumes == -1);

        tot = (child && cat && sam && pom && aki && viz);
        tot = (tot && gold && tree && car && perf);
        return tot;
    }

    public boolean improvedMatches(Sue s) {
        // returns true if this sue has (for all features) the same value as the other
        // sue OR if
        // the feature is undefined in one of them (-1)
        // EXCEPT cats/trees - tests if new s has more than this (the gifter)
        // EXCEPT pomeranians/goldfish - tests if new s has fewer than this
        boolean child, cat, sam, pom, aki, viz, gold, tree, car, perf, tot;

        child = (this.children == s.children || this.children == -1 || s.children == -1);
        cat = (this.cats < s.cats || s.cats == -1);
        sam = (this.samoyeds == s.samoyeds || this.samoyeds == -1 || s.samoyeds == -1);
        pom = (this.pomeranians > s.pomeranians || s.pomeranians == -1);
        aki = (this.akitas == s.akitas || this.akitas == -1 || s.akitas == -1);
        viz = (this.vizslas == s.vizslas || this.vizslas == -1 || s.vizslas == -1);
        gold = (this.goldfish > s.goldfish || s.goldfish == -1);
        tree = (this.trees < s.trees || s.trees == -1);
        car = (this.cars == s.cars || this.cars == -1 || s.cars == -1);
        perf = (this.perfumes == s.perfumes || this.perfumes == -1 || s.perfumes == -1);

        tot = (child && cat && sam && pom && aki && viz);
        tot = (tot && gold && tree && car && perf);
        return tot;
    }
}
// public int children, cats, samoyeds, pomeranians, akitas, vizslas;
// public int goldfish, trees, cars, perfumes;
