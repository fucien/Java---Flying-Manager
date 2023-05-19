/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package flightmanager;

import static flightmanager.BookingList.setKey;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import net.proteanit.sql.DbUtils;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class Pay extends javax.swing.JFrame {

    /**
     * Creates new form Pay
     */
    public Pay() {
        initComponents();
        DisplayFlight();
    }

    Connection Con = null;
    Statement St = null;
    ResultSet Rs = null;
    private String user_id = "";
    private String flight_id = "";
    private String method = "";

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id = flight_id;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFlight_id() {
        return flight_id;
    }

    public String getMethod() {
        return method;
    }

    private void DisplayFlight() {
        try {
            
            Con = DriverManager.getConnection("jdbc:postgresql://localhost/Flytest", "ien", "7302");
            St = Con.createStatement();
            System.out.println(Login.getUsername());
            Rs = St.executeQuery("SELECT * FROM bookings where user_id = '" + Login.getUsername()+ "' AND status = 'Pending'" );
            TicketsTbl.setModel(DbUtils.resultSetToTableModel(Rs));
            setFlight_id(TicketsTbl.getValueAt(0, 2).toString());
            if (TicketsTbl.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "You have no pending tickets");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMethod(PaymentsCbx.getSelectedItem().toString());
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TicketsTbl = new javax.swing.JTable();
        PayBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ABlbl = new javax.swing.JLabel();
        PaymentsCbx = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PaymentHistoryTbl = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jTabbedPane1.setBackground(new java.awt.Color(27, 46, 83));
        jTabbedPane1.setForeground(new java.awt.Color(27, 46, 83));

        jPanel1.setBackground(new java.awt.Color(27, 46, 83));
        jPanel1.setForeground(new java.awt.Color(255, 62, 62));

        TicketsTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        TicketsTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TicketsTblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TicketsTbl);

        PayBtn.setBackground(new java.awt.Color(255, 62, 62));
        PayBtn.setForeground(new java.awt.Color(255, 255, 255));
        PayBtn.setText("Pay");
        PayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PayBtnMouseClicked(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 62, 62));
        jLabel1.setText("Account Balance");

        PaymentsCbx.setBackground(new java.awt.Color(255, 62, 62));
        PaymentsCbx.setForeground(new java.awt.Color(255, 255, 255));
        PaymentsCbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CreditCard", "Visa", "Paypal", "Cash" }));
        PaymentsCbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentsCbxActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 62, 62));
        jLabel2.setText("Payment Method");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 62, 62));
        jLabel3.setText("Select Ticket");

        jButton9.setBackground(new java.awt.Color(255, 0, 0));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Back");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(PayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PaymentsCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(ABlbl)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9)
                .addGap(78, 78, 78)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ABlbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PaymentsCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PayBtn)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Thanh toán hóa đơn", jPanel1);

        PaymentHistoryTbl.setBackground(new java.awt.Color(27, 46, 83));
        PaymentHistoryTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(PaymentHistoryTbl);

        jButton11.setBackground(new java.awt.Color(255, 0, 0));
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Back");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton11)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Lịch sử giao dịch", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TicketsTblMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_BookingListTblMouseClicked
        int row = TicketsTbl.getSelectedRow();
        setUser_id(TicketsTbl.getValueAt(row, 1).toString());
        setFlight_id(TicketsTbl.getValueAt(row, 2).toString());
        System.out.println(getUser_id() + " " + getFlight_id() + " " + getMethod());
    }// GEN-LAST:event_BookingListTblMouseClicked

    private void PaymentsCbxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PaymentsCbxActionPerformed
        // TODO add your handling code here:
        setMethod(PaymentsCbx.getSelectedItem().toString());
    }// GEN-LAST:event_PaymentsCbxActionPerformed

    private void PayBtnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_PayBtnMouseClicked
        // TODO add your handling code here:
        try {
            // Set the endpoint URL
            String endpointUrl = "http://localhost:3456/api/payment";

            // Prepare the request body
            System.out.println(getUser_id() + " " + getFlight_id() + " " + getMethod());
            System.out.println("Sending a payment request");
            

            JSONObject requestBodyJson = new JSONObject();
            try {
                requestBodyJson.put("user_id", getUser_id());
                requestBodyJson.put("flight_id", getFlight_id());
                requestBodyJson.put("method", getMethod());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String requestBody = requestBodyJson.toString();
            // Create the URL object
            URL url = new URL(endpointUrl);

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");

            // Enable input and output streams
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Set the content type
            connection.setRequestProperty("Content-Type", "application/json");

            // Get the output stream of the connection
            OutputStream outputStream = connection.getOutputStream();

            // Write the request body to the output stream
            outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            outputStream.close();

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);
            String responseString = connection.getResponseMessage();
            System.out.println("Response message: " + responseString);
            JOptionPane.showMessageDialog(this, "Payment success.");

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new User().setVisible(true);
        this.dispose();

    }// GEN-LAST:event_PayBtnMouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton9ActionPerformed
        new User().setVisible(true);
        this.dispose();
    }// GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        new User().setVisible(true);
        this.dispose();
    }// GEN-LAST:event_jButton11ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Pay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pay().setVisible(true);
            }
        });
    }

    private void DisplayTicket() {
        try {
            Connection Con = DriverManager.getConnection("jdbc:postgresql://localhost/Flytest", "ien", "7302");
            Statement St = Con.createStatement();
            String Query = "SELECT * FROM bookings";
            ResultSet Rs = St.executeQuery(Query);
            TicketsTbl.setModel(DbUtils.resultSetToTableModel(Rs));
        } catch (Exception e) {
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ABlbl;
    private javax.swing.JButton PayBtn;
    private javax.swing.JTable PaymentHistoryTbl;
    private javax.swing.JComboBox<String> PaymentsCbx;
    private javax.swing.JTable TicketsTbl;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
