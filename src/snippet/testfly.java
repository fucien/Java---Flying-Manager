package snippet;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.*;
import java.util.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.xpath.*;
import org.xml.sax.*;


//...

public class testfly {

    public static Date formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = null;
        try {
            date = inputFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputString = outputFormat.format(date);
        try {
            date = outputFormat.parse(outputString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static final String API_HOST = "https://aviation-reference-data.p.rapidapi.com/icaoType/B738";
    private static final String API_KEY = "9805577ad4msh99cd4f93e0a2950p1f3421jsnee8802bd6cf2";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Flytest";
    private static final String DB_USER = "ien";
    private static final String DB_PASSWORD = "7302";

    public static class Flight {
        private String flightId;
        private String airline;
        private String departure;
        private String arrival;
        private Date departureDate;
        private Date arrivalDate;
        private int availableSeats;
        private BigDecimal price;

        public Flight(String flightId, String airline, String departure, String arrival, Date departureDate,
                Date arrivalDate, int availableSeats, BigDecimal price) {
            this.flightId = flightId;
            this.airline = airline;
            this.departure = departure;
            this.arrival = arrival;
            this.departureDate = departureDate;
            this.arrivalDate = arrivalDate;
            this.availableSeats = availableSeats;
            this.price = price;
        }

        public Flight() {
            this.flightId = "";
            this.airline = "";
            this.departure = "";
            this.arrival = "";
            this.departureDate = null;
            this.arrivalDate = null;
            this.availableSeats = 0;
            this.price = null;
        }

        public String getFlightId() {
            return flightId;
        }

        public void setFlightId(String flightId) {
            this.flightId = flightId;
        }

        public String getAirline() {
            return airline;
        }

        public void setAirline(String airline) {
            this.airline = airline;
        }

        public String getDeparture() {
            return departure;
        }

        public void setDeparture(String departure) {
            this.departure = departure;
        }

        public String getArrival() {
            return arrival;
        }

        public void setArrival(String arrival) {
            this.arrival = arrival;
        }

        public Date getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(Date departureDate) {
            this.departureDate = departureDate;
        }

        public Date getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(Date arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public int getAvailableSeats() {
            return availableSeats;
        }

        public void setAvailableSeats(int availableSeats) {
            this.availableSeats = availableSeats;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

    }

    private static Date parseDate(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.parse(dateString);
    }

    public static void main(String[] args) throws Exception {

     
        // String xmlString = new String(Files.readAllBytes(Paths.get(filePath)));
        List<Flight> flights = new ArrayList<>();
        try {
            
            URL url = new URL("https://timetable-lookup.p.rapidapi.com/TimeTable/UIH/SGN/20230406/");
            // URL url = new URL(API_HOST + "/timetables/standard/" + urlParameters);

            // Set up connection and headers

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-RapidAPI-Host",
                    "timetable-lookup.p.rapidapi.com");
            connection.setRequestProperty("X-RapidAPI-Key",
                    "9805577ad4msh99cd4f93e0a2950p1f3421jsnee8802bd6cf2");

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
            //khuc nay dung api nen de do 




            InputSource inputResponse = new InputSource(new StringReader(response));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputResponse);
            
            
//            File xmlFile = new File("src/test.xml");            
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(xmlFile);
            
            
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            NodeList flightNodes = (NodeList) xpath.evaluate("//FlightDetails", doc, XPathConstants.NODESET);

            for (int i = 0; i < flightNodes.getLength(); i++) {
                Node flightNode = flightNodes.item(i);
                Flight flight = new Flight();
                flight.setAirline(
                        xpath.evaluate("./FlightLegDetails/MarketingAirline/@Code", flightNode));
                flight.setDeparture(
                        xpath.evaluate("./FlightLegDetails/DepartureAirport/@LocationCode", flightNode));
                flight.setArrival(
                        xpath.evaluate("./FlightLegDetails/ArrivalAirport/@LocationCode", flightNode));
                flight.setFlightId(xpath.evaluate("./FlightLegDetails/@FLSUUID", flightNode));

                String departureDateStr = xpath.evaluate("./FlightLegDetails/@DepartureDateTime", flightNode);
                String arrivalDateStr = xpath.evaluate("./FlightLegDetails/@ArrivalDateTime", flightNode);

                

                flight.setDepartureDate(formatDate(departureDateStr));
                flight.setArrivalDate(formatDate(arrivalDateStr));
                flights.add(flight);
                System.out.println("Flight ID: " + flight.flightId);
                System.out.println("Airline: " + flight.airline);
                System.out.println("Departure: " + flight.departure);
                System.out.println("Arrival: " + flight.arrival);
                System.out.println("Departure Date: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(flight.departureDate));
                System.out.println("Arrival Date: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(flight.arrivalDate));
//                System.out.println("Departure Date: " + new SimpleDateFormat("yyyyMMdd").format(flight.departureDate));
//                System.out.println("Arrival Date: " + new SimpleDateFormat("yyyyMMdd").format(flight.arrivalDate));
                System.out.println("Available Seats: " + flight.availableSeats);
                System.out.println("Price: " + flight.price);
                System.out.println("--------------------------");


                    // // Connect to database
    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

    // // Set up date format and calendar
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    PreparedStatement ps = conn.prepareStatement(
        "INSERT INTO public.flights (flight_id, airline, departure, arrival, departure_date,arrival_date, available_seats, price) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, flight.flightId);
        ps.setString(2, flight.airline);
        ps.setString(3, flight.departure);
        ps.setString(4, flight.arrival);
        Date date = formatDate(departureDateStr);
        Timestamp timestamp = new Timestamp(date.getTime());
        ps.setTimestamp(5, timestamp);
        Date date1 = formatDate(arrivalDateStr);
        Timestamp timestamp1 = new Timestamp(date1.getTime());
        ps.setTimestamp(6, timestamp1);
        ps.setInt(7, 80);
        ps.setInt(8, 80000000);
        ps.executeUpdate();
    }
            // for (Flight flight : flights) {

            // }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Do whatever you need to do with the extracted information
    }
    // try {

    // Calendar calendar = Calendar.getInstance();
    // calendar.setTime(new Date());

    // // Retrieve flight schedules for the next 7 days
    // for (int i = 0; i < 7; i++) {
    // // Set departure date in query parameters
    // String date = dateFormat.format(calendar.getTime());
    // String urlParameters =
    // "orginPlace=SGN-sky&destinationPlace=DAD-sky&outboundDate=" + date;
    // URL url = new URL(API_HOST + "/timetables/standard/" + urlParameters);

    // // Set up connection and headers
    // URLConnection connection = url.openConnection();
    // connection.setRequestProperty("x-rapidapi-host",
    // "aviation-reference-data.p.rapidapi.com");
    // connection.setRequestProperty("x-rapidapi-key", API_KEY);

    // // Read response from API
    // BufferedReader in = new BufferedReader(new
    // InputStreamReader(connection.getInputStream()));
    // String inputLine;
    // StringBuilder responseBuilder = new StringBuilder();
    // while ((inputLine = in.readLine()) != null) {
    // responseBuilder.append(inputLine);
    // }
    // in.close();
    // String response = responseBuilder.toString();

    // // Parse response JSON and insert into database
    // JSONObject jsonObject = new JSONObject(response);
    // JSONArray schedules = jsonObject.getJSONArray("ScheduleResource");
    // for (int j = 0; j < schedules.length(); j++) {
    // JSONObject schedule = schedules.getJSONObject(j);
    // String flightId = schedule.getString("FlightNumber");
    // String airline =
    // schedule.getJSONObject("MarketingCarrier").getString("AirlineID");
    // String departure =
    // schedule.getJSONObject("Departure").getString("AirportCode");
    // String arrival = schedule.getJSONObject("Arrival").getString("AirportCode");
    // String departureDate =
    // schedule.getJSONObject("Departure").getString("DateTime");
    // String arrivalDate = schedule.getJSONObject("Arrival").getString("DateTime");
    // int availableSeats = schedule.getInt("TotalScheduledServiceUnits");
    // BigDecimal price = new BigDecimal(Math.random() * 1000).setScale(2,
    // BigDecimal.ROUND_HALF_UP);

    // // Insert flight information into database

    // System.out.println("Inserted flight " + flightId + " from " + departure + "
    // to " + arrival + " on " + departureDate);
    // }
    // }
    // }

    // catch (SQLException e) {
    // e.printStackTrace();
    // }
    // catch (IOException sn){
    // sn.printStackTrace();
    // }
}