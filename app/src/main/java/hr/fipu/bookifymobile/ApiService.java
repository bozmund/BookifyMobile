package hr.fipu.bookifymobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/Book")
    Call<List<BookDto>> getBookByName(@Query("name") String name);

    @POST("/api/Book/books")
    Call<List<BookDto>> getBooksByIds(@Body List<Integer> bookIds);

    @POST("/shortSummary/{bookId}")
    Call<String> interpretBookWithAI(@Path("bookId") int bookId);
}
