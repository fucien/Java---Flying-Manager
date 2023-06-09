/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ApiPayment;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.proteanit.sql.DbUtils;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class Payment extends javax.swing.JFrame {

    /**
     * Creates new form History
     */
    public Payment() {
        initComponents();
        DisplayPayment();
    }

    Connection Con = null;
    Statement St = null;
    ResultSet Rs = null;

    public static void DbPay(String user_id, String flight_id, String method) {
        Connection Con = null;
        Statement St = null;
        ResultSet Rs = null;
        try {

            Con = DriverManager.getConnection("jdbc:postgresql://localhost/Flytest", "ien", "7302");
            St = Con.createStatement();
            St.executeQuery("UPDATE bookings SET status = 'Paid',method = '" + method + "' WHERE user_id = '"
                    + user_id + "' and flight_id = '" + flight_id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void DisplayPayment() {
        try {

            Con = DriverManager.getConnection("jdbc:postgresql://localhost/Flytest", "ien", "7302");
            St = Con.createStatement();
            Rs = St.executeQuery("SELECT * FROM bookings");
            HistoryTbl.setModel(DbUtils.resultSetToTableModel(Rs));
            Rs = St.executeQuery("SELECT SUM(price) AS total_price FROM bookings WHERE status = 'Paid'");
            if (Rs.next()) {
                int total_price = Rs.getInt("total_price");
                ABlbl.setText(String.valueOf(total_price) + " VND");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        HistoryTbl = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        ABlbl = new javax.swing.JLabel();
        RsBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        HistoryTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(HistoryTbl);

        jLabel1.setText("Account Balance:");

        RsBtn.setText("Reset");
        RsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RsBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ABlbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RsBtn)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ABlbl)
                    .addComponent(RsBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void RsBtnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_RsBtnMouseClicked
        // TODO add your handling code here:
        DisplayPayment();
    }// GEN-LAST:event_RsBtnMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
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
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payment().setVisible(true);
            }
        });

        HttpServer server = HttpServer.create(new InetSocketAddress(3456), 0);
        System.out.println("Server started at port " + server.getAddress());
        server.createContext("/api/payment", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the request body
                System.out.println("Receiving a payment request");
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                try {
                    // Parse the request body as JSON
                    JSONObject requestBody = new JSONObject(body);

                    // Extract the user_id, flight_id, and method parameters from the JSON
                    String userId = requestBody.getString("user_id");
                    String flightId = requestBody.getString("flight_id");
                    String method = requestBody.getString("method");

                    // Process the received parameters
                    System.out.println("User ID: " + userId);
                    System.out.println("Flight ID: " + flightId);
                    System.out.println("Payment Method: " + method);

                    // Update the database
                    DbPay(userId, flightId, method);

                    // Return a response if needed
                    String responseText = "Payment received successfully";
                    exchange.sendResponseHeaders(200, responseText.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(responseText.getBytes());
                    output.flush();
                    JOptionPane.showMessageDialog(null,
                            "Payment received successfully, please press the reset button to update the table.");
                } catch (Exception e) {
                    exchange.sendResponseHeaders(400, -1); // 400 Bad Request
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
                // 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ABlbl;
    private javax.swing.JTable HistoryTbl;
    private javax.swing.JButton RsBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
