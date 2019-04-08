package com.example.rajeevjha.stackoverflow.network;

import com.example.rajeevjha.stackoverflow.models.QuestionList;
import com.example.rajeevjha.stackoverflow.models.TagList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//GetRemoteDataInterface.java defines various endpoints of our API

public interface GetRemoteDataInterface {


    // Api endpoint for tags
    @GET("/tags")
    Call<TagList> getAllTags(@Query("order") String order,
                             @Query("sort") String sort,
                             @Query("site") String site
    );

    // API endpoint for Questions
    @GET("/questions")
    Call<QuestionList> getQuestions(@Query("order") String order,
                                    @Query("sort") String sort,
                                    @Query("tagged") String tag,
                                    @Query("site") String site
    );

}
