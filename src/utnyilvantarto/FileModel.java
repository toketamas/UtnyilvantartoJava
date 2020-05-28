package utnyilvantarto;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileModel {


    ObservableList<String> list = new ObservableList<String>() {
        @Override
        public void addListener(ListChangeListener<? super String> listener) {

        }

        @Override
        public void removeListener(ListChangeListener<? super String> listener) {

        }

        @Override
        public boolean addAll(String... elements) {
            return false;
        }

        @Override
        public boolean setAll(String... elements) {
            return false;
        }

        @Override
        public boolean setAll(Collection<? extends String> col) {
            return false;
        }

        @Override
        public boolean removeAll(String... elements) {
            return false;
        }

        @Override
        public boolean retainAll(String... elements) {
            return false;
        }

        @Override
        public void remove(int from, int to) {

        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public String set(int index, String element) {
            return null;
        }

        @Override
        public void add(int index, String element) {

        }

        @Override
        public String remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @Override
        public ListIterator<String> listIterator(int index) {
            return null;
        }

        @Override
        public List<String> subList(int fromIndex, int toIndex) {
            return null;
        }

        @Override
        public void addListener(InvalidationListener listener) {

        }

        @Override
        public void removeListener(InvalidationListener listener) {

        }
    };

    public ObservableList readFile(String path) {

        File file = new File(path);
        try {
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                list.add(read.next());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        // list.sort(Comparator.naturalOrder());
        return list;
    }
}


