package hr.fipu.bookifymobile;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Stores "Read" and "To Read" books in SharedPreferences (as JSON).
 * Ensures a book can exist in only one list at a time.
 */
public final class BookShelfStore {

    private static final String PREFS_NAME = "bookify_bookshelf";
    private static final String KEY_READ = "read_books";
    private static final String KEY_TO_READ = "to_read_books";

    private static final Gson gson = new Gson();
    private static final Type LIST_TYPE = new TypeToken<List<BookDto>>() {}.getType();

    private BookShelfStore() {}

    public static List<BookDto> getReadBooks(Context context) {
        return new ArrayList<>(loadList(context, KEY_READ));
    }

    public static List<BookDto> getToReadBooks(Context context) {
        return new ArrayList<>(loadList(context, KEY_TO_READ));
    }

    public static void addToRead(Context context, BookDto book) {
        // Remove from the other list first (mutual exclusion)
        List<BookDto> toRead = loadList(context, KEY_TO_READ);
        removeByKey(toRead, getBookKey(book));
        saveList(context, KEY_TO_READ, toRead);

        List<BookDto> read = loadList(context, KEY_READ);
        // Avoid duplicates
        removeByKey(read, getBookKey(book));
        read.add(0, book);
        saveList(context, KEY_READ, read);
    }

    public static void addToToRead(Context context, BookDto book) {
        // Remove from the other list first (mutual exclusion)
        List<BookDto> read = loadList(context, KEY_READ);
        removeByKey(read, getBookKey(book));
        saveList(context, KEY_READ, read);

        List<BookDto> toRead = loadList(context, KEY_TO_READ);
        // Avoid duplicates
        removeByKey(toRead, getBookKey(book));
        toRead.add(0, book);
        saveList(context, KEY_TO_READ, toRead);
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static List<BookDto> loadList(Context context, String key) {
        String json = prefs(context).getString(key, null);
        if (json == null || json.trim().isEmpty()) return new ArrayList<>();
        try {
            List<BookDto> list = gson.fromJson(json, LIST_TYPE);
            return list != null ? list : new ArrayList<>();
        } catch (Exception ignored) {
            return new ArrayList<>();
        }
    }

    private static void saveList(Context context, String key, List<BookDto> list) {
        prefs(context).edit().putString(key, gson.toJson(list)).apply();
    }

    private static String getBookKey(BookDto book) {
        if (book == null) return "";
        if (book.bookId != null && !book.bookId.trim().isEmpty()) return book.bookId;
        return String.valueOf(book.id);
    }

    private static void removeByKey(List<BookDto> list, String key) {
        if (list == null || key == null) return;
        Iterator<BookDto> it = list.iterator();
        while (it.hasNext()) {
            BookDto b = it.next();
            if (key.equals(getBookKey(b))) it.remove();
        }
    }
}