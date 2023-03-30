import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

        


public class testfly {

    private static final String API_HOST = "https://aviation-reference-data.p.rapidapi.com/icaoType/B738";
    private static final String API_KEY = "9805577ad4msh99cd4f93e0a2950p1f3421jsnee8802bd6cf2";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Flytest";
    private static final String DB_USER = "ien";
    private static final String DB_PASSWORD = "7302";

    public static void main(String[] args) throws Exception
     {
         try {
           for (int i = 0; i < 7; i++) {
                // Set departure date in query parameters
//                String date = calendar.getTime();
                URL url = new URL ("https://timetable-lookup.p.rapidapi.com/TimeTable/SGN/DAD/20230331/");
                //URL url = new URL(API_HOST + "/timetables/standard/" + urlParameters);

                // Set up connection and headers

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-RapidAPI-Host", "timetable-lookup.p.rapidapi.com");
                connection.setRequestProperty("X-RapidAPI-Key", "9805577ad4msh99cd4f93e0a2950p1f3421jsnee8802bd6cf2");
                
                
                // Read response from API
                BufferedReader asd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder responseBuilder = new StringBuilder();
                while ((inputLine = asd.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                asd.close();
                String response = responseBuilder.toString();
                System.out.println(response);
           }
         }
         catch (IOException sn){
             sn.printStackTrace();
         }
        // try {
        //     // Connect to database 
        //     Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        //     // Set up date format and calendar
        //     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //     Calendar calendar = Calendar.getInstance();
        //     calendar.setTime(new Date());

        //     // Retrieve flight schedules for the next 7 days
        //     for (int i = 0; i < 7; i++) {
        //         // Set departure date in query parameters
        //         String date = dateFormat.format(calendar.getTime());
        //         String urlParameters = "orginPlace=SGN-sky&destinationPlace=DAD-sky&outboundDate=" + date;
        //         URL url = new URL(API_HOST + "/timetables/standard/" + urlParameters);

        //         // Set up connection and headers
        //         URLConnection connection = url.openConnection();
        //         connection.setRequestProperty("x-rapidapi-host", "aviation-reference-data.p.rapidapi.com");
        //         connection.setRequestProperty("x-rapidapi-key", API_KEY);

        //         // Read response from API
        //         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //         String inputLine;
        //         StringBuilder responseBuilder = new StringBuilder();
        //         while ((inputLine = in.readLine()) != null) {
        //             responseBuilder.append(inputLine);
        //         }
        //         in.close();
        //         String response = responseBuilder.toString();

        //         // Parse response JSON and insert into database
        //         JSONObject jsonObject = new JSONObject(response);
        //         JSONArray schedules = jsonObject.getJSONArray("ScheduleResource");
        //         for (int j = 0; j < schedules.length(); j++) {
        //             JSONObject schedule = schedules.getJSONObject(j);
        //             String flightId = schedule.getString("FlightNumber");
        //             String airline = schedule.getJSONObject("MarketingCarrier").getString("AirlineID");
        //             String departure = schedule.getJSONObject("Departure").getString("AirportCode");
        //             String arrival = schedule.getJSONObject("Arrival").getString("AirportCode");
        //             String departureDate = schedule.getJSONObject("Departure").getString("DateTime");
        //             String arrivalDate = schedule.getJSONObject("Arrival").getString("DateTime");
        //             int availableSeats = schedule.getInt("TotalScheduledServiceUnits");
        //             BigDecimal price = new BigDecimal(Math.random() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP);

        //             // Insert flight information into database
        //             PreparedStatement ps = conn.prepareStatement(
        //                 "INSERT INTO Flights (flight_id, airline, departure, arrival, departure_date, arrival_date, available_seats, price) " +
        //                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        //             ps.setString(1, flightId);
        //             ps.setString(2, airline);
        //             ps.setString(3, departure);
        //             ps.setString(4, arrival);
        //             ps.setString(5, departureDate);
        //             ps.setString(6, arrivalDate);
        //             ps.setInt(7, availableSeats);
        //             ps.setBigDecimal(8, price);
        //             ps.executeUpdate();
                    
        //             System.out.println("Inserted flight " + flightId + " from " + departure + " to " + arrival + " on " + departureDate);
        //         }
        //     }
        // }

//         catch (SQLException e) {
//           e.printStackTrace();
//         }
//         catch (IOException sn){
//             sn.printStackTrace();
//         }
      }
}