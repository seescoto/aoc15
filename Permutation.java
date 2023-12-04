import java.util.ArrayList;

public class Permutation<T> {
    // given an original set of objects of type t, return all possible permutations
    // of the objects (in an array format)

    ArrayList<T> original;
    ArrayList<ArrayList<T>> permutations = new ArrayList<>();

    public Permutation(ArrayList<T> list) {
        this.original = list;
        this.permute();
    }

    public void permute() {
        ArrayList<T> original = new ArrayList<>(this.original);
        ArrayList<T> currentPerm = new ArrayList<>(), toPermute = new ArrayList<>(original);

        permute(currentPerm, toPermute);
    }

    public void permute(ArrayList<T> currentPerm, ArrayList<T> toPermute) {
        ArrayList<T> currCopy = new ArrayList<>(currentPerm);
        ArrayList<T> toPermCopy = new ArrayList<>(toPermute);

        if (toPermCopy.size() == 0) {
            // if current permutation is a complete permutation, containing all items of
            // type T that the original did, then toPermute (the chars to add to the
            // permutation) will have length 0 and we can just add curr to permutations and
            // return
            this.permutations.add(currCopy);
        } else {
            // if there are still more items of type T to add to curr, then try adding an
            // item in & recursing on that, then undoing it and repeating
            for (int i = 0; i < toPermute.size(); i++) {
                T item = toPermute.get(i);
                currCopy.add(item);
                toPermCopy.remove(item);
                permute(currCopy, toPermCopy);
                currCopy.remove(item);
                toPermCopy.add(item);
            }
        }
    }

}