/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author ACER
 */
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

public class testfly {
  private static final String API_BASE_URL = "https://timetable.lookup.com";
  private static final String API_KEY = "9805577ad4msh99cd4f93e0a2950p1f3421jsnee8802bd6cf2";
  private static final String DB_URL = "jdbc:postgresql://localhost/flytest";
  private static final String DB_USER = "ien";
  private static final String DB_PASSWORD = "7302";

  private HttpClient httpClient;
  private Gson gson;

  public testfly() {
    this.httpClient = HttpClients.createDefault();
    this.gson = new Gson();
  }

  public void importFlights(String origin, String destination, String date) throws URISyntaxException, IOException, SQLException {
    URIBuilder builder = new URIBuilder(API_BASE_URL + "/flights");
    builder.addParameter("origin", origin);
    builder.addParameter("destination", destination);
    builder.addParameter("date", date);
    builder.addParameter("apikey", API_KEY);
    URI uri = builder.build();
    HttpGet request = new HttpGet(uri);
    HttpResponse response = httpClient.execute(request);
    String jsonResponse = EntityUtils.toString(response.getEntity());
    List<Flight> flights = gson.fromJson(jsonResponse, new TypeToken<ArrayList<Flight>>(){}.getType());
    
    // Connect to the database
    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    PreparedStatement stmt = conn.prepareStatement("INSERT INTO flights (airline, flight_number, origin_airport_code, destination_airport_code, departure_time, arrival_time, price) VALUES (?, ?, ?, ?, ?, ?, ?)");

    // Insert the flight data into the database
    for (Flight flight : flights) {
      stmt.setString(1, flight.getAirline());
      stmt.setString(2, flight.getFlightNumber());
      stmt.setString(3, flight.getOriginAirportCode());
      stmt.setString(4, flight.getDestinationAirportCode());
      stmt.setTimestamp(5, Timestamp.valueOf(flight.getDepartureTime()));
      stmt.setTimestamp(6, Timestamp.valueOf(flight.getArrivalTime()));
      stmt.setBigDecimal(7, BigDecimal.valueOf(flight.getPrice()));
      stmt.executeUpdate();
    }
    
    // Close the database connection
    stmt.close();
    conn.close();
  }

    public static void main(String[] args) throws URISyntaxException, IOException, SQLException {
        //FlightsApi flightsApi = new FlightsApi();
        //flightsApi.importFlights("SGN", "UIH", "2023-26-03");
    }
}
