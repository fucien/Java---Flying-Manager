/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package flightmanager;

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
import java.io.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.xpath.*;
import org.xml.sax.*;
import src.flightmanager.*;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author trantri
 */

public class BookingList extends javax.swing.JFrame {
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

    /**
     * Creates new form BookingList
     */
    public BookingList() throws IOException {
        initComponents();
        List<Flight> flights = new ArrayList<>();
        try {
            URL url = new Booking().getURL();

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

            InputSource inputResponse = new InputSource(new StringReader(response));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputResponse);
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
                

                String departureDateStr = xpath.evaluate("./FlightLegDetails/@DepartureDateTime", flightNode);
                String tempTime = departureDateStr.substring(11, 13) + departureDateStr.substring(14, 16);
                System.out.println(tempTime);
                String tempID = xpath.evaluate("./FlightLegDetails/@FLSUUID", flightNode) + tempTime;
                System.out.println(tempID);
                flight.setFlightId(tempID);
                String arrivalDateStr = xpath.evaluate("./FlightLegDetails/@ArrivalDateTime", flightNode);
                flight.setDepartureDate(formatDate(departureDateStr));
                flight.setArrivalDate(formatDate(arrivalDateStr));
                flights.add(flight);
                Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Flytest", "ien", "7302");

                // // Set up date format and calendar
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO public.flights (flight_id, airline, departure, arrival, departure_date,arrival_date, available_seats, price) "
                                +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                                +
                                "ON CONFLICT (flight_id) DO NOTHING");
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
                Object[] newRow = {flight.flightId, flight.airline, flight.departure, flight.arrival, flight.departureDate,
                        flight.arrivalDate, 80, 80000000};
                DefaultTableModel model = (DefaultTableModel) BookingListTbl.getModel();
                model.addRow(newRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // khuc nay dung api nen de do

        
        // try {
        //     Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Flytest", "ien", "7302");
        //     Statement St = conn.createStatement();
        //     ResultSet Rs = St.executeQuery("SELECT * FROM Flights");
        //     BookingListTbl.setModel(DbUtils.resultSetToTableModel(Rs));
        // } catch (Exception e) {
        // }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BookingListTbl = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        BookBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(800, 300));

        jLabel1.setText("ID");

        jLabel2.setText("Airlines");

        jLabel3.setText("Start");

        jLabel4.setText("Arrive");

        jLabel5.setText("Boarding date and time");

        jLabel6.setText("Landing date and time");

        jLabel7.setText("Seats");

        jLabel8.setText("Price");

        BookingListTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fly ID", "Airline", "Departure", "Destination", "Departure Time", "Arrival Time", "Available seats", "Price"
            }
        ));
        BookingListTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BookingListTblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BookingListTbl);

        jLabel9.setText("Booking List");

        BookBtn.setText("Book");
        BookBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BookBtnMouseClicked(evt);
            }
        });
        BookBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)))
                        .addGap(18, 18, 18)
                        .addComponent(BookBtn)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel9)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BookBtn))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private static int Key = 0;

    public static int getKey() {
        return Key;
    }

    private void BookingListTblMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_BookingListTblMouseClicked
        int row = BookingListTbl.getSelectedRow();
        Key = Integer.parseInt(BookingListTbl.getValueAt(row, 0).toString());
    }// GEN-LAST:event_BookingListTblMouseClicked

    private void BookBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BookBtnActionPerformed

    }// GEN-LAST:event_BookBtnActionPerformed

    private void BookBtnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_BookBtnMouseClicked
        Payment pay = new Payment();
        pay.setVisible(true);
        this.dispose();
    }// GEN-LAST:event_BookBtnMouseClicked

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookingList.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookingList.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookingList.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookingList.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

        /* Create and display the form */

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new BookingList().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(BookingList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BookBtn;
    private javax.swing.JTable BookingListTbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
