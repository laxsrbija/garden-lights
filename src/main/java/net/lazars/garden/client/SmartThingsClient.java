package net.lazars.garden.client;

import net.lazars.garden.model.DeviceCommandRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SmartThingsClient {

  @POST("v1/devices/{deviceId}/commands")
  Call<Void> sendCommand(
      @Path("deviceId") String deviceId,
      @Body DeviceCommandRequest deviceCommandRequest,
      @Header("Authorization") String authHeader);
}
