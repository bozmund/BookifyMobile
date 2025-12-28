package hr.fipu.bookifymobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/book")
    Call<List<BookDto>> getBookByName(@Query("name") String name);
}
