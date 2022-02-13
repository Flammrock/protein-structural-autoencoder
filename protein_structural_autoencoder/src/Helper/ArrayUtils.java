package Helper;

import java.util.Arrays;
import java.util.Comparator;

public final class ArrayUtils {
	
	public static boolean ReverseOrder = false;
	
    public static <T extends Comparable<T>> Integer[] argsort(final T[] a) {
        return argsort(a, true);
    }

    public static <T extends Comparable<T>> Integer[] argsort(final T[] a, final boolean ascending) {
        Integer[] indexes = new Integer[a.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(final Integer i1, final Integer i2) {
                return (ascending ? 1 : -1) * a[i1].compareTo(a[i2]);
            }
        });
        return indexes;
    }

    private ArrayUtils() {
    }
}