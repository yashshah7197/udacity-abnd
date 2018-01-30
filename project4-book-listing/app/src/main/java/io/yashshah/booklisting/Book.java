package io.yashshah.booklisting;

/**
 * Created by yashshah on 04/01/17.
 */

/**
 * Class for a single Book object
 */
public class Book {

    // Title of the book
    private String mTitle;

    // Authors of the book
    private String mAuthors;

    // Categories of the book
    private String mCategories;

    // The number of pages in the book
    private int mPageCount;

    /**
     * @param title      is a String containing title of the book
     * @param authors    is a String containing the names of the authors of the book
     * @param categories is a String containing the names of categories of this book
     * @param pageCount  is an int containing the number of pages in the book
     */
    public Book(String title, String authors, String categories, int pageCount) {
        this.mTitle = title;
        this.mAuthors = authors;
        this.mCategories = categories;
        this.mPageCount = pageCount;
    }

    // Get the title of the book
    public String getTitle() {
        return mTitle;
    }

    // Get the authors of the book
    public String getAuthors() {
        return mAuthors;
    }

    // Get the categories of the book
    public String getCategories() {
        return mCategories;
    }

    // Get the page count of the book
    public int getPageCount() {
        return mPageCount;
    }
}
