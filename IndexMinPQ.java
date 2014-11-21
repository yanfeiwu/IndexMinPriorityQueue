private class MinPQ<E extends Comparable<E>> {
        private int[] k;
        private int[] idx;
        private E[] e;
        private int size;
        
        @SuppressWarnings("unchecked")
        public MinPQ(int capacity) {
            k = new int[capacity];
            idx = new int[capacity];
            e = (E[]) new Comparable[capacity];
            for (int i = 0; i < capacity; i++) {
                k[i] = -1;
                idx[i] = -1;
            }
            size = 0;
        }
        
        private void swap(int i, int j) {
            int tmp = k[i];
            k[i] = k[j];
            k[j] = tmp;
            idx[k[i]] = i;
            idx[k[j]] = j;
        }
        
        private void sink(int i) {
            while (i * 2 + 1 < size) {
                int next = i * 2 + 1;
                if (next + 1 < size && e[k[next]].compareTo(e[k[next+1]]) > 0) next++;
                if (e[k[i]].compareTo(e[k[next]]) < 0) break;
                swap(i, next);
                i = next;
            }
        }
        
        private void swim(int i) {
            while (i > 0 && e[k[i]].compareTo(e[k[(i-1)/2]]) < 0) {
                swap(i, (i-1)/2);
                i = (i-1)/2;
            }
        }
        
        public boolean containsKey(int key) {
            return idx[key] != -1;
        }
        
        public E getElement(int key) {
            if (!containsKey(key)) throw new NoSuchElementException("Element doesn't exist");
            return e[key];
        }
        
        public E getMinElement() {
            if (size < 1) throw new NoSuchElementException("Retrieving from empty queue");
            return e[k[0]];
        }
        
        public int retrieveMinKey(){
            if (size < 1) throw new NoSuchElementException("Retrieving from empty queue.");
            int ret = k[0];
            size--;
            swap(0, size);
            idx[ret] = -1;
            e[ret] = null;
            sink(0);
            
            return ret;
        }
        
        public void insert(int key, E weight) {
            if (key < 0 || key >= k.length) throw new IndexOutOfBoundsException();
            if (containsKey(key)) throw new IllegalArgumentException("Index inserted already exists.");
            k[size] = key;
            idx[key] = size;
            e[key] = weight;
            swim(size);
            size++;
        }
        
        public void decreaseElement(int key) {
            swim(idx[key]);
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
    }