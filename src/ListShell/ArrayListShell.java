package ListShell;

import java.util.ArrayList;

public class ArrayListShell<T> extends ArrayList<T> {

    public boolean contains(Comparable<T> comparison) {
        for (T elem : this) {
            if (comparison.compare(elem))
                return true;
        }

        return false;
    }

    public T get(Comparable<T> comparison)
    {
        for (T elem : this) {
            if (comparison.compare(elem))
                return elem;
        }

        return null;
    }

}
