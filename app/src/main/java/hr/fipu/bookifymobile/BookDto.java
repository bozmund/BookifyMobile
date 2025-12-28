package hr.fipu.bookifymobile;

import com.google.gson.annotations.SerializedName;

public class BookDto {
    @SerializedName(value = "id", alternate = {"Id"})
    public int id;

    @SerializedName(value = "bookId", alternate = {"BookId"})
    public String bookId;

    @SerializedName(value = "bestBookId", alternate = {"BestBookId"})
    public String bestBookId;

    @SerializedName(value = "workId", alternate = {"WorkId"})
    public String workId;

    @SerializedName(value = "booksCount", alternate = {"BooksCount"})
    public int booksCount;

    @SerializedName(value = "isbn", alternate = {"Isbn", "ISBN"})
    public String isbn;

    @SerializedName(value = "isbn13", alternate = {"Isbn13", "ISBN13"})
    public String isbn13;

    @SerializedName(value = "authors", alternate = {"Authors"})
    public String authors;

    @SerializedName(value = "originalPublicationYear", alternate = {"OriginalPublicationYear"})
    public Float originalPublicationYear;

    @SerializedName(value = "originalTitle", alternate = {"OriginalTitle"})
    public String originalTitle;

    @SerializedName(value = "title", alternate = {"Title"})
    public String title;

    @SerializedName(value = "languageCode", alternate = {"LanguageCode"})
    public String languageCode;

    @SerializedName(value = "averageRating", alternate = {"AverageRating"})
    public double averageRating;

    @SerializedName(value = "ratingsCount", alternate = {"RatingsCount"})
    public int ratingsCount;

    @SerializedName(value = "workRatingsCount", alternate = {"WorkRatingsCount"})
    public int workRatingsCount;

    @SerializedName(value = "workTextReviewsCount", alternate = {"WorkTextReviewsCount"})
    public int workTextReviewsCount;

    @SerializedName(value = "ratings1", alternate = {"Ratings1"})
    public int ratings1;

    @SerializedName(value = "ratings2", alternate = {"Ratings2"})
    public int ratings2;

    @SerializedName(value = "ratings3", alternate = {"Ratings3"})
    public int ratings3;

    @SerializedName(value = "ratings4", alternate = {"Ratings4"})
    public int ratings4;

    @SerializedName(value = "ratings5", alternate = {"Ratings5"})
    public int ratings5;

    @SerializedName(value = "imageUrl", alternate = {"ImageUrl"})
    public String imageUrl;

    @SerializedName(value = "smallImageUrl", alternate = {"SmallImageUrl"})
    public String smallImageUrl;
}
