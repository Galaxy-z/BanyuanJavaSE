package com.banyuan.exercise.collection;

public class MyHashMap {
    private Entry[] entryArr = new Entry[5];

    private int size;

    public void put(Object key, Object value) {
        int hashcode = key.hashCode();
        int index = hashcode % entryArr.length;
        if (entryArr[index] == null) {
            entryArr[index] = new Entry(key, value);
            size++;
        } else {
            Entry current = entryArr[index];
            while (true) {
                if (key.equals(current.getKey())) {
                    current.setValue(value);
                    return;
                }
                if (current.getNext() == null) {
                    current.setNext(new Entry(key, value));
                    size++;
                    return;
                }
                current = current.getNext();
            }
        }

    }

    public Object get(Object key) {
        int index = key.hashCode() % entryArr.length;

        Entry current = entryArr[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    public static void main(String[] args) {

    }

    class Entry {
        private Object key;
        private Object value;
        private Entry next;

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
        }

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}