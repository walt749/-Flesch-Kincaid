package vandy.mooc.functional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * A generic unsynchronized array class implemented via a single
 * contiguous buffer.
 */
@SuppressWarnings({"unchecked", "JavadocBlankLines", "JavadocDeclaration"})
public class Array<E>
        implements Iterable<E> {
    /**
     * Default initial capacity.
     */
    static final int DEFAULT_CAPACITY = 10;

    /**
     * Shared empty array instance used for empty instances.
     */
    static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * The array buffer that stores all the array elements.  The
     * capacity is the length of this array buffer.
     */
    Object[] mElementData;
    int added = 0;

    /**
     * The size of the {@link Array} (the number of elements it
     * contains).  This field also indicates the next "open" slot in
     * the array, i.e., where a call to add() will place the new
     * element: mElementData[mSize] = element.
     */
    int mSize;

    /*
     * The following methods can use classic Java features (i.e., Java
     * 7 or before), such as loops, if/else statements, exceptions,
     * etc.
     */

    /**
     * Sets element data to an immutable static zero-sized array,
     * which is used later by the {@code ensureCapacityInternal()}
     * method to construct an empty array with an initial capacity of
     * {@code DEFAULT_CAPACITY} "on demand", i.e., when it's actually
     * necessary.
     */
    public Array() {
        mElementData = EMPTY_ELEMENTDATA;
    }

    /**
     * Constructs an empty {@link Array} with the specified initial
     * capacity.
     *
     * @param initialCapacity The initial capacity of the {@link
     *                        Array}
     * @throws IllegalArgumentException If the specified initial
     *                                  capacity is negative
     */
    public Array(int initialCapacity) {
        // TODO -- you fill in here.
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        this.mElementData = new Object[initialCapacity];
        this.mSize = initialCapacity;


    }

    /**
     * Constructs an {@link Array} containing the elements of the
     * specified collection in the order they are returned by the
     * {@link Collection}'s iterator.
     *
     * @param c The {@link Collection} whose elements will be placed
     *          into this array
     * @throws NullPointerException If the specified {@link
     *                              Collection} is null
     */
    public Array(Collection<? extends E> c) {
        // TODO -- you fill in here.
        if (c == null) {
            throw new NullPointerException("Input collection cannot be null.");
        }

        this.mElementData = c.toArray();
    }

    /**
     * @return <tt>true</tt> If this {@link Array} contains no elements
     */
    public boolean isEmpty() {
        // TODO -- you fill in here (replace 'return false' with
        // proper code).
        return mSize == 0 || added == 0;
    }

    /**
     * @return The number of elements in this {@link Array}
     */
    public int size() {
        // TODO -- you fill in here (replace 'return 0' with proper code).
        return mSize;
    }

    /**
     * Returns the index of the first occurrence of the specified
     * element in this {@link Array}, or -1 if this {@link Array} does
     * not contain the element.
     *
     * @param o Element to search for
     * @return The index of the first occurrence of the specified
     *         element in this {@link Array}, or -1 if this {@link
     *         Array} does not contain the element
     */
    public int indexOf(Object o) {
        // TODO -- you fill in here (replace 'return -1' with proper
        // code).
        if( o == null){
            for(int i = 0; i < mElementData.length; i++){

                if(mElementData[i] == null){
                    return i;
                }
            }
        } else {
            for (int i = 0; i < mElementData.length ; i++) {
                if(o.equals(mElementData[i])){
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * Appends all the elements in the specified {@link Collection}
     * to the end of this {@link Array}, in the order that they are
     * returned by the specified {@link Collection}'s {@link
     * Iterator}.  The behavior of this operation is undefined if the
     * specified {@link Collection} is modified while the operation is
     * in progress.  This implies that the behavior of this call is
     * undefined if the specified {@link Collection} is this {@link
     * Array}, and this {@link Array} is nonempty.
     *
     * @param c {@link Collection} containing elements to be added to
     *          this {@link Array}
     * @return <tt>true</tt> If this {@link Array} changed as a result
     *                       of the call
     * @throws {@link NullPointerException} If the specified {@link
     *                                      Collection} is null
     */
    public boolean addAll(Collection<? extends E> c) {
        // TODO -- you fill in here (replace 'return false' with
        // proper code).  Try to avoid using Java loops, but use
        // System.arraycopy() instead and also call
        // ensureCapacityInternal() to simplify this code.

        // Copy elements from the collection to the end of mElementData
        ensureCapacityInternal(mSize + c.size());

        int i = mSize;
        for (E element : c) {
            mElementData[i] = element;
            i++;
        }

        mSize += c.size();

        // Return true if elements were added
        return !c.isEmpty();
    }

    /**
     * Appends all the elements in the specified {@link Array} to the
     * end of this {@link Array}, in the order they are returned by
     * the specified {@link Collection}'s {@link Iterator}.  The
     * behavior of this operation is undefined if the specified {@link
     * Collection} is modified while the operation is in progress.
     * This implies that the behavior of this call is undefined if the
     * specified {@link Collection} is this {@link Array}, and this
     * {@link Array} is nonempty.
     *
     * @param array An {@link Array} containing elements to added to
     *              this {@link Array}
     * @return <tt>true</tt> if this {@link Array} changed as a result
     *                       of the call
     * @throws {@link NullPointerException} if the specified {@link
     *                                      Array} is null
     */
    public boolean addAll(Array<E> array) {
        // TODO -- you fill in here (replace 'return false' with
        // proper code).  Try to avoid using Java loops, but use
        // System.arraycopy() instead and also call
        // ensureCapacityInternal() to simplify this code.
        // Ensure the array is large enough to hold the new elements
        ensureCapacityInternal(mElementData.length + array.size());

        // Convert the specified array to an array and copy to the end of mElementData
        System.arraycopy(array.mElementData, 0, mElementData, mElementData.length, array.size());

        return true;
    }

    /**
     * Removes the element at the specified position in this {@link
     * Array}.  Shifts any subsequent elements to the left (subtracts
     * one from their indices).
     *
     * @param index The index of the element to be removed
     * @return The element that was removed from the {@link Array}
     */
    public E remove(int index) {
        // TODO -- you fill in here (replace 'return null' with proper
        // code).  Try to avoid using Java loops, but use
        // System.arraycopy() instead and also call rangeCheck() to
        // simplify this code.
        rangeCheck(index);  // Check if the index is within valid bounds

        E removedElement = (E) mElementData[index];

        // Calculate the number of elements to be moved (length after the index)
        int numElementsToMove = mElementData.length - index - 1;

        if (numElementsToMove > 0) {
            // Shift elements to the left to remove the element at the specified index
            System.arraycopy(mElementData, index + 1, mElementData, index, numElementsToMove);
        }

        // Set the last element to null and update the size
        mElementData[mElementData.length - 1] = null;
        mElementData = Arrays.copyOf(mElementData, mElementData.length - 1);

        return removedElement;

    }

    /**
     * Checks if the given index is in range (i.e., index is
     * non-negative and is not equal to or larger than the size of the
     * {@link Array}) and throws the {@link IndexOutOfBoundsException}
     * if it's not.
     *
     * Normally should be declared as 'private', but for unit test
     * access, has been declared 'public'.
     *
     * @param index The index of the element to check
     * @throws {@link IndexOutOfBoundsException} If {@code index} is
     *                                           out of bounds
     */
    public void rangeCheck(int index) throws IndexOutOfBoundsException {
        // TODO -- you fill in here.
        if (index < 0 || index >= mElementData.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mElementData.length);
        }
    }

    /**
     * Returns the element at the specified position in this array.
     * The {@link IndexOutOfBoundsException} is thrown if {@code
     * index} is out of range.
     *
     * @param index The index of the element to return
     * @return The element at the specified position in this {@link
     *         Array}
     */
    public E get(int index) {
        // TODO -- you fill in here (replace 'return null' with proper
        // code).  Make sure to use the rangeCheck() method here.

        rangeCheck(index);
        return (E) mElementData[index];
    }

    /**
     * Replaces the element at the specified position in this list
     * with the specified element.  {@link IndexOutOfBoundsException}
     * is thrown if {@code index} is out of range.
     *
     * @param index The index of the element to replace
     * @param element The element to be stored at the specified
     *                position
     * @return The element previously at the specified position
     */
    public E set(int index, E element) {
        // TODO -- you fill in here (replace 'return null' with proper
        // code).
        rangeCheck(index);

        E previousElement = (E) mElementData[index];

        mElementData[index] = element;
        return previousElement;

    }

    /**
     * Appends the specified element to the end of this {@link Array}.
     *
     * @param element The element to append to this {@link Array}
     * @return {@code true}
     */
    public boolean add(E element) {
        // TODO -- you fill in here (replace 'return false' with
        // proper code).  Call ensureCapacityInternal() to simplify
        // this code.
        ensureCapacityInternal(mSize + 1);
        mElementData[mSize++] = element;
        added++;
        return true;
    }

    /**
     * Ensure this {@link Array} is large enough to hold {@code
     * minCapacity} elements.  The {@link Array} will be expanded if
     * necessary.
     *
     * Normally should be declared as 'private', but for unit test
     * access, it has been declared 'protected'.
     *
     * @param minCapacity The minimum capacity needed for this {@link
     *                    Array}
     */
    protected void ensureCapacityInternal(int minCapacity) {
        // TODO -- you fill in here.  Try to avoid using Java loops,
        // but use System.arraycopy() or Arrays.copyOf() instead.
        int currentCapacity = mElementData.length;
        if (minCapacity > currentCapacity) {
            int newCapacity = Math.max(currentCapacity + 1, minCapacity);
            mElementData = Arrays.copyOf(mElementData, newCapacity);
        }
    }

    /**
     * @return An {@link Iterator} over the elements in this {@link
     *         Array} in proper sequence
     */
    public Iterator<E> iterator() {
        // TODO -- you fill in here replacing this statement with your
        // solution.
        return new ArrayIterator();
    }

    /**
     * This class implements an {@link Iterator} that traverses over
     * the elements in an {@link Array} in proper sequence.
     */
    @SuppressWarnings("JavadocDeclaration")
    public class ArrayIterator implements Iterator<E> {
        /**
         * Current position in the {@link Array} (defaults to 0).
         */
        // TODO - you fill in here.
        private int currentIndex;
        // private E[] array;

        /**
         * Index of last element returned; -1 if no such element.
         */
        // TODO - you fill in here.
        private int lastIndexReturned;


        public int getLastIndexReturned() {
            return lastIndexReturned;
        }

        public int getCurrentIndex() {
            return currentIndex - 1;
        }

        /**
         public ArrayIterator(E[] array){
         this.array = array;
         this.currentIndex = 0;
         this.lastIndexReturned = -1;

         }
         **/

        /**
         * @return True if the iteration has more elements that
         *         haven't been iterated through yet, else false
         */
        @Override
        public boolean hasNext() {
            // TODO - you fill in here (replace 'return false' with
            // proper code).
            return currentIndex < mElementData.length;
        }

        /**
         * @return The next element in the iteration
         * @throws {@link NoSuchElementException} if there's no next
         *         element
         */
        @Override
        public E next() {
            // TODO - you fill in here (replace 'return null' with
            // proper code).
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Object element = mElementData[currentIndex];
            lastIndexReturned = currentIndex;
            currentIndex++;
            return (E) element;
        }

        /**
         * Removes from the underlying {@link Array} the last element
         * returned by this {@link Iterator}. This method can be
         * called only once per call to {@code next()}.
         *
         * @throws IllegalStateException if no last element was
         *                               returned by the iterator
         */
        @Override
        public void remove() {
            // TODO - you fill in here
            if (lastIndexReturned == -1) {
                throw new IllegalStateException("No element to remove");
            }
            for(int i = lastIndexReturned; i < mElementData.length - 1; i++){
                mElementData[i] = mElementData[i + 1];
            }
            mElementData[mElementData.length - 1] = null;
            currentIndex--;
            lastIndexReturned = -1;

        }
    }

    /*
     * The following methods can apply modern Java features (i.e.,
     * Java 8 and beyond), such as functional interfaces, lambda
     * expressions, and method references.
     */

    /**
     * Replaces each element of this {@link Array} with the result of
     * applying the {@code operator} to that element.  Errors or
     * runtime exceptions thrown by the {@code operator} are relayed
     * to the caller.
     *
     * @param operator The {@link UnaryOperator} to apply to transform
     *                 each element
     */
    public void replaceAll(UnaryOperator<E> operator) {
        // TODO - you fill in here (this implementation can use a Java
        //  index-based for loop).
        for (int i = 0; i < mSize; i++) {
            @SuppressWarnings("unchecked")
            E transformedElement = operator.apply((E) mElementData[i]);
            mElementData[i] = transformedElement;
        }
    }

    /**
     * Performs the given {@code action} for each element of the
     * {@link Array} until all elements have been processed or the
     * {@code action} throws an exception.  Unless otherwise specified
     * by the implementing class, the {@code action} is performed in
     * the order of iteration (if an iteration order is specified).
     * Exceptions thrown by the {@code action} are relayed to the
     * caller.
     *
     * @param action The {@link Consumer} action to perform for each
     *               element
     */
    public void forEach(Consumer<? super E> action) {
        // TODO - You fill in here using a for-each loop or
        // the Iterator.forEachRemaining() method.
        for (int i = 0; i < mSize; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) mElementData[i];
            action.accept(element);
        }

    }

    /**
     * @return A {@link List} view of the elements in this {@link Array}
     */
    public List<E> asList() {
        // TODO - You fill in here, replacing 'return null'
        // with a solution using a for-each loop or (better
        // yet) the Iterator.forEachRemaining() method and
        // a method reference.
        List<E> list = new ArrayList<>(mSize);

        for (int i = 0; i < mSize; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) mElementData[i];
            list.add(element);
        }

        return list;
    }

    /*
     * The following method implementations are provided for you.
     */

    /**
     * @return A reference to the underlying buffer containing all
     *         the elements in this {@link Array} object in proper
     *         sequence
     */
    public Object[] uncheckedToArray() {
        return mElementData;
    }

    /**
     * Returns an {@link Object} array containing all the elements in
     * this {@link Array} in proper sequence (from first to last
     * element).
     *
     * The returned {@link Array} will be "safe" in that no references
     * to it are maintained by this {@link Array} (in other words,
     * this method must allocate a new {@link Array}).  The caller is
     * thus free to modify the returned {@link Array}.
     *
     * This method acts as a bridge between array-based and
     * collection-based APIs.
     *
     * @return An {@link Object} array containing all the elements in
     *         this {@link Array} object in proper sequence
     */
    public Object[] toArray() {
        return Arrays.copyOf(mElementData, mSize);
    }

    /**
     * Returns a generic built-in array containing all the elements in
     * the specified {@code array} parameter in proper sequence (from
     * first to last element). The runtime type of the returned
     * built-in array is that of the specified {@code array}
     * parameter.  If the existing {@link Array} fits in the specified
     * {@code array}, it is returned.  Otherwise, a new build-in array
     * is allocated with the runtime type of the specified {@code
     * array} and the size of this {@link Array}.
     *
     * @param array The array into which the elements of the list are
     *              stored, if it is big enough; otherwise, a new array
     *              of the same runtime type is created for this purpose
     * @return An array containing the elements of this {@link Array}
     * @throws {@link ArrayStoreException} If the runtime type of the
     *         specified {@code array} is not a supertype of the
     *         runtime type of every element in this {@link Array}
     * @throws {@link NullPointerException} If the specified {@code
     *                                      array} is null
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] array) {
        // If the given array's length is less than the size of this
        // Array, create and return a new array of the same runtime
        // type as the given array but with the size of this Array.
        if (array.length < mSize) {
            // Make a new array of array's runtime type, but with this
            // Array's contents.
            return (T[]) Arrays.copyOf(mElementData,
                    mSize,
                    array.getClass());
        } else {
            // Copy the elements from this Array into the given array
            // starting from the beginning of both sources.
            System.arraycopy(mElementData, 0,
                    array, 0,
                    mSize);

            // If the given array's length is larger than this Array's
            // size, set the element at the position of the array's
            // size to null.
            if (array.length > mSize) {
                array[mSize] = null;
            }

            // Return the populated array.
            return array;
        }
    }
}
