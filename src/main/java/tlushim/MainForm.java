/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlushim;

import java.util.concurrent.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author gchaim
 */
public class MainForm extends javax.swing.JFrame {
    // instance variables - replace the example below with your own
    private static final String SITE = "https://www.tlushim.co.il/main.php?op=start";
    private final DefaultTableModel dtm;
    private static final String HEADER[] = new String[] { "סך הכל","יציא", "כניסה", "יום","תאריך" };

    private String data;
    
    // Creates new form MainForm
    public MainForm() {
        this.dtm = new DefaultTableModel(0, 0);
        initComponents();
    }
    
    public static String [][] to2dim (String source, String outerdelim, String innerdelim) {
        // outerdelim may be a group of characters
        String [] sOuter = source.split ("[" + outerdelim + "]");
        int size = sOuter.length;
        // one dimension of the array has to be known on declaration:
        String [][] result = new String [size][];
        int count = 0;
        for (String line : sOuter)
        {
            result [count] = line.split (innerdelim);
            ++count;
        }
        return result;
    }
    
    void getResult(String result) {
        dtm.setColumnIdentifiers(HEADER);
        tbMonth.setModel(dtm);
        dtm.setRowCount(0);
        String[][] rows = to2dim(result, "\n", ",");
        double mHours = 0;
        double lHours = 0;
        double tkn , totalDouble ;
        if(!rows[0][0].equals("error") && !rows[0][0].isEmpty()) {
            String[] strHeader=rows[0][0].split(" ");
            lblName.setText("נוכחות של "+strHeader[1]+" "+strHeader[0]);
            lblDate.setText("נכון לתאריך "+strHeader[7]);

            for (int i = 2; i < rows.length-3; i++) {
                if (rows[i].length>60){
                    if (rows[i][rows[i].length - 3].equals("רגיל") || rows[i][rows[i].length - 3].contains("חוה")) {
                        
                        dtm.addRow(new Object[] { rows[i][6],rows[i][5], rows[i][4], rows[i][3] , rows[i][2] });
                        
                        tkn = Double.parseDouble(rows[i][rows[i].length - 2]);
                        if (rows[i][rows[i].length - 3].equals("חוה\"מ 1"))
                            tkn=8.5;

                        if (!rows[i][rows[i].length - 4].isEmpty() && !rows[2][1].contains("אי השלמת תקן")) {
                            totalDouble = Double.parseDouble(rows[i][rows[i].length - 4]);
                            if (totalDouble < tkn)
                                lHours += tkn - totalDouble;
                            if(totalDouble > tkn)
                                mHours+= totalDouble-tkn;
                        }else if(!rows[i][rows[i].length - 5].isEmpty()){
                            totalDouble = Double.parseDouble(rows[i][rows[i].length - 5]);
                            if (totalDouble < tkn)
                                lHours += tkn - totalDouble;
                            if(totalDouble > tkn)
                                mHours+= totalDouble-tkn;
                        }
                    }else if (rows[i][7].equals("חופשה") ||
                            rows[i][7].contains("מילואים") ||
                            rows[i][7].contains("חול בתפקיד") ||
                            rows[i][7].contains("אבל") ||
                            rows[i][7].contains("מצב בטחוני")||
                            rows[i][7].contains("מחלה")) {
                        dtm.addRow(new Object[] { "",rows[i][7],"" , rows[i][3] , rows[i][2] });
                    }else if(rows[i][rows[i].length - 3].contains("העדרות")||
                            rows[i][rows[i].length - 3].contains("חג")){
                        dtm.addRow(new Object[] { "","", rows[i][rows[i].length - 3], rows[i][3] , rows[i][2] });
                    }else if(rows[i][rows[i].length - 3].contains("שישי")){
                        dtm.addRow(new Object[] { rows[i][6],rows[i][5], rows[i][4], rows[i][3] , rows[i][2] });
                        if (!rows[i][rows[i].length - 4].isEmpty() && !rows[2][1].contains("אי השלמת תקן")) {
                            totalDouble = Double.parseDouble(rows[i][rows[i].length - 4]);
                            mHours+= totalDouble;
                        }else if(!rows[i][rows[i].length - 5].isEmpty()){
                            totalDouble = Double.parseDouble(rows[i][rows[i].length - 5]);
                            mHours+= totalDouble;
                        }
                    }
                }
            }

            moreHours.setText("שעות נוספות: " + sFromD(mHours));
            lessHours.setText("שעות חסרות: " + sFromD(lHours));

            double sum = mHours - lHours;
            if (sum < 0) {
                totalHours.setText("חסר " + sFromD(Math.abs(sum)) +" שעות");
            } else {
                totalHours.setText("יתרה " + sFromD(sum) +" שעות");
            }
        }
    }
    
     static String sFromD(Double time){
        int h =time.intValue();
        int m = (int) (time * 100 % 100);
        int minute = Math.abs((int)(0.6*m));
        if(minute<10){
            return h+":0" + minute;
        }
        return h+":" + minute;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        pfPass = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        totalPanel = new javax.swing.JPanel();
        moreHours = new javax.swing.JLabel();
        lessHours = new javax.swing.JLabel();
        totalHours = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        spMonth = new javax.swing.JScrollPane();
        tbMonth = new javax.swing.JTable();
        pStatus = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("נוכחות");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(500, 500));

        pLogin.setBackground(new java.awt.Color(255, 255, 255));
        pLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "כניסה ל- tlushim.co.il", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        pLogin.setMaximumSize(new java.awt.Dimension(50, 500));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setLabelFor(tfUser);
        jLabel1.setText("שם משתמש");

        tfUser.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tfUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfUser.setText("326988425");
        tfUser.setNextFocusableComponent(pfPass);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setLabelFor(pfPass);
        jLabel2.setText("סיסמה");

        pfPass.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        pfPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pfPass.setText("Sdrm1415");
        pfPass.setNextFocusableComponent(btnLogin);

        btnLogin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLogin.setText("קבל נתונים");
        btnLogin.setToolTipText("");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        totalPanel.setBackground(new java.awt.Color(255, 255, 255));
        totalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "תוצאות", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N

        moreHours.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        moreHours.setForeground(new java.awt.Color(51, 51, 255));
        moreHours.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        moreHours.setText("שעות נוספות: ");

        lessHours.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lessHours.setForeground(new java.awt.Color(255, 102, 51));
        lessHours.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lessHours.setText("שעות חסרות: ");

        totalHours.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalHours.setForeground(new java.awt.Color(0, 204, 0));
        totalHours.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalHours.setText("יתרה שעות");

        lblName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblName.setText("נוכחות של ");

        lblDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDate.setText("נכון לתאריך ");

        javax.swing.GroupLayout totalPanelLayout = new javax.swing.GroupLayout(totalPanel);
        totalPanel.setLayout(totalPanelLayout);
        totalPanelLayout.setHorizontalGroup(
            totalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(totalHours, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lessHours, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(moreHours, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        totalPanelLayout.setVerticalGroup(
            totalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDate)
                .addGap(27, 27, 27)
                .addComponent(moreHours)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lessHours)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(totalHours)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout pLoginLayout = new javax.swing.GroupLayout(pLogin);
        pLogin.setLayout(pLoginLayout);
        pLoginLayout.setHorizontalGroup(
            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pLoginLayout.createSequentialGroup()
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfUser, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(pfPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pLoginLayout.setVerticalGroup(
            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        spMonth.setBackground(new java.awt.Color(255, 255, 255));

        tbMonth.setAutoCreateRowSorter(true);
        tbMonth.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tbMonth.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "סך הכל", "יציאה", "כניסה", "יום", "תאריך"
            }
        ));
        tbMonth.setAlignmentX(0.0F);
        tbMonth.setAlignmentY(0.0F);
        tbMonth.setMaximumSize(new java.awt.Dimension(500, 480));
        tbMonth.setMinimumSize(new java.awt.Dimension(300, 500));
        tbMonth.setNextFocusableComponent(btnLogin);
        tbMonth.setOpaque(false);
        tbMonth.setRowHeight(25);
        tbMonth.setShowGrid(true);
        tbMonth.setShowVerticalLines(false);
        spMonth.setViewportView(tbMonth);

        pStatus.setBackground(new java.awt.Color(255, 255, 255));
        pStatus.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "סטטוס", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        pStatus.setAlignmentX(0.0F);
        pStatus.setAlignmentY(0.0F);

        lblStatus.setBackground(new java.awt.Color(255, 255, 255));
        lblStatus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout pStatusLayout = new javax.swing.GroupLayout(pStatus);
        pStatus.setLayout(pStatusLayout);
        pStatusLayout.setHorizontalGroup(
            pStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pStatusLayout.setVerticalGroup(
            pStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addComponent(pLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        jProgressBar1.setIndeterminate(true);
        HtmlParser parse = new HtmlParser(tfUser.getText(),pfPass.getText(),SITE);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        // Runnable, return void, submit and run the task async
        executor.submit(() -> {
            System.out.println("Start parser Task");
            String data1 = parse.getData();
            if (data1.equals("error") || data1.isEmpty()) {
                lblStatus.setText("אין אינטרנט, או שם משתמש וסיסמה לא נכונים!");
            } else {
                getResult(data1);
                lblStatus.setText("נתונים עודכנו בהצלחה!");
            }
            jProgressBar1.setIndeterminate(false);
        });
        
        //String data = parse.getData();
        
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lessHours;
    private javax.swing.JLabel moreHours;
    private javax.swing.JPanel pLogin;
    private javax.swing.JPanel pStatus;
    private javax.swing.JPasswordField pfPass;
    private javax.swing.JScrollPane spMonth;
    private javax.swing.JTable tbMonth;
    private javax.swing.JTextField tfUser;
    private javax.swing.JLabel totalHours;
    private javax.swing.JPanel totalPanel;
    // End of variables declaration//GEN-END:variables
}
