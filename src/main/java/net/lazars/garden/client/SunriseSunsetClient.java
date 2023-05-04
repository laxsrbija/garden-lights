package net.lazars.garden.client;

import net.lazars.garden.model.SunriseSunsetResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SunriseSunsetClient {

  @GET("json")
  Call<SunriseSunsetResponse> getSunsetData(
      @Query("lat") String latitude,
      @Query("lng") String longitude,
      @Query("date") String date,
      @Query("formatted") int formatted);
}
