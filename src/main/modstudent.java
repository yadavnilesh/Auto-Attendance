/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGRA2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.equalizeHist;
import org.opencv.objdetect.CascadeClassifier;
import sun.java2d.SunGraphicsEnvironment;
import icon.*;
/**
 *
 * @author santosh
 */
public class modstudent extends javax.swing.JFrame {

    /**
     * Creates new form modteach
     */
  CascadeClassifier faceDetector = new CascadeClassifier(modstudent.class.getResource("haarcascade_frontalface_default.xml").getPath().substring(1));
 
    String s;
    File f;
    ImageIcon img;
    BufferedImage buff;
    BufferedImage bu,bm0;
    DefaultTableModel model;
    BufferedImage buf;
    Mat frame=new Mat();
    MatOfRect faceDetections = new MatOfRect();
    int totaldet;
    BufferedImage[] bm;
    JDialog dialog;
    private int result;

    public modstudent() {
    initComponents();
    profilepic1.setIcon(new ImageIcon(ex.class.getResource("profile.png").getPath()));
    ButtonGroup bg=new ButtonGroup();
    bg.add(rdrollno);
    bg.add(rdcourse);
    bg.add(rdyear);
    bg.add(rdemail);
    bg.add(rdname);
    rdname.setSelected(true);
     try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("Select  * from course ");
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                course.addItem(rs.getString("course"));
            }
            }catch(Exception ex){
                ex.printStackTrace();
            }
    String[] columnNames = {"first name","last name","roll no","course","year","email"};
        Object[][] data =
        {
           
        };
      model = new DefaultTableModel(data, columnNames)
    {
       @Override
      public boolean isCellEditable(int row,int column )
     {
        return false;
     }
      @Override
      public Class  getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        jTable1.setModel(model);
       insert();
       jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            if(jTable1.getSelectionModel().isSelectionEmpty())
               {
                 
               }
               else{
                 fname.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
                 lname.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 1));
                 rollno.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 2));
                 year.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), 4));  
                 email.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 5));
                 course.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), 3)); 
                 
        try{           
        Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");     
        PreparedStatement ps = con.prepareStatement("select * from simages where rollno=? and course=? and year=?");
        ps.setString(1,jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());
        ps.setString(2,jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
        ps.setString(3,jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());
        ResultSet rs = ps.executeQuery();
        rs.next();  
        ByteArrayInputStream bais = new ByteArrayInputStream(rs.getBytes("image"));        
        bu=ImageIO.read(bais);
        profilepic1.setIcon(new ImageIcon(rs.getBytes("image")));
        bais.reset();
        ByteArrayInputStream ba= new ByteArrayInputStream(rs.getBytes("eximage"));
        bm0=ImageIO.read(ba);
        profilepic.setIcon(new ImageIcon(rs.getBytes("eximage")));
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
            }
            }
    });
           
    super.addWindowFocusListener(new WindowAdapter() {
    public void windowGainedFocus(WindowEvent e) {
        fname.requestFocusInWindow();
    }
    });
    }
    
    static{ 
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // load opencv_java
    }
    void insert()
    {
         try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("Select  * from student ");
            ResultSet rs=pst.executeQuery();
            int i=0;
            if(rs.next())
            {
                do{
                   
                    model.insertRow(i,new Object[] { rs.getString("fname"),rs.getString("lname"),rs.getString("rollno"),rs.getString("course"),rs.getString("year"),rs.getString("email")});
                    i++;
                }while(rs.next());
            }
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        rnlabel = new javax.swing.JLabel();
        clearall = new javax.swing.JToggleButton();
        lname = new javax.swing.JTextField();
        lnlabel = new javax.swing.JLabel();
        jToggleButton4 = new javax.swing.JToggleButton();
        insert = new javax.swing.JToggleButton();
        fname = new javax.swing.JTextField();
        fname.requestFocusInWindow();
        rollno = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        yearlabel = new javax.swing.JLabel();
        fnlabel = new javax.swing.JLabel();
        delete = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        coulabel = new javax.swing.JLabel();
        emaillabel = new javax.swing.JLabel();
        splabel = new javax.swing.JLabel();
        profilepic = new javax.swing.JLabel();
        browse = new javax.swing.JToggleButton();
        course = new javax.swing.JComboBox<>();
        year = new javax.swing.JComboBox<>();
        profilepic1 = new javax.swing.JLabel();
        searchtext = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        rdname = new javax.swing.JRadioButton();
        rdrollno = new javax.swing.JRadioButton();
        rdcourse = new javax.swing.JRadioButton();
        rdemail = new javax.swing.JRadioButton();
        rdyear = new javax.swing.JRadioButton();
        search1 = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        update = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();

        fileChooser.setPreferredSize(new java.awt.Dimension(700, 500));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AUTO MARKING ATTENDANCE USING FACIAL RECOGNITION");

        jPanel2.setBackground(new java.awt.Color(0, 204, 153));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        rnlabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rnlabel.setText("Roll No");

        clearall.setBackground(new java.awt.Color(255, 255, 255));
        clearall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clearall1.jpg"))); // NOI18N
        clearall.setBorder(null);
        clearall.setOpaque(true);
        clearall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearallActionPerformed(evt);
            }
        });

        lname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lnameKeyTyped(evt);
            }
        });

        lnlabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lnlabel.setText("Last Name");

        jToggleButton4.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/aback.png"))); // NOI18N
        jToggleButton4.setToolTipText("");
        jToggleButton4.setBorder(null);
        jToggleButton4.setOpaque(true);
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        insert.setBackground(new java.awt.Color(255, 255, 255));
        insert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/create.png"))); // NOI18N
        insert.setBorder(null);
        insert.setOpaque(true);
        insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertActionPerformed(evt);
            }
        });

        fname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fnameKeyTyped(evt);
            }
        });

        rollno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        yearlabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        yearlabel.setText("Year");

        fnlabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        fnlabel.setText("First name");

        delete.setBackground(new java.awt.Color(255, 255, 255));
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        delete.setBorder(null);
        delete.setOpaque(true);
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        coulabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        coulabel.setText("Course");

        emaillabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        emaillabel.setText("Email id");

        splabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        splabel.setText("Student Photos");

        browse.setBackground(new java.awt.Color(255, 255, 255));
        browse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/browse.png"))); // NOI18N
        browse.setBorder(null);
        browse.setOpaque(true);
        browse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                browseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                browseMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                browseMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                browseMouseReleased(evt);
            }
        });
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });

        course.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                courseItemStateChanged(evt);
            }
        });

        year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Year", "Second Year", "Third Year", "Fourth Year" }));
        year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearActionPerformed(evt);
            }
        });

        searchtext.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        searchtext.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchtextFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchtextFocusLost(evt);
            }
        });
        searchtext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchtextKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchtextKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Search By");

        rdname.setBackground(new java.awt.Color(255, 255, 255));
        rdname.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdname.setText("Name");
        rdname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdnameMouseClicked(evt);
            }
        });
        rdname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdnameActionPerformed(evt);
            }
        });

        rdrollno.setBackground(new java.awt.Color(255, 255, 255));
        rdrollno.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdrollno.setText("Roll No");
        rdrollno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdrollnoActionPerformed(evt);
            }
        });

        rdcourse.setBackground(new java.awt.Color(255, 255, 255));
        rdcourse.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdcourse.setText("Course");
        rdcourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdcourseActionPerformed(evt);
            }
        });

        rdemail.setBackground(new java.awt.Color(255, 255, 255));
        rdemail.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdemail.setText("Email id");
        rdemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdemailActionPerformed(evt);
            }
        });

        rdyear.setBackground(new java.awt.Color(255, 255, 255));
        rdyear.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdyear.setText("Year");
        rdyear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdyearActionPerformed(evt);
            }
        });

        search1.setBackground(new java.awt.Color(255, 255, 255));
        search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clearall.jpg"))); // NOI18N
        search1.setToolTipText("");
        search1.setBorder(null);
        search1.setOpaque(true);
        search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search1ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 204, 153));
        jPanel4.setPreferredSize(new java.awt.Dimension(843, 60));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Copyright@2018 Auto Attendance System");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(355, 355, 355)
                .addComponent(jLabel13)
                .addContainerGap(356, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel13)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("<html>&nbsp&nbsp;clear &nbsp;&nbsp;search<br> &nbsp;  field</html> ");

        update.setBackground(new java.awt.Color(255, 255, 255));
        update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        update.setBorder(null);
        update.setOpaque(true);
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("   Add New");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("    Update");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("         Delete");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Clear selection");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("<html>&nbsp;Browse<br>&nbsp;image");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Back");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1043, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchtext, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdname)
                                .addGap(33, 33, 33)
                                .addComponent(rdrollno)
                                .addGap(33, 33, 33)
                                .addComponent(rdcourse)
                                .addGap(33, 33, 33)
                                .addComponent(rdemail)
                                .addGap(33, 33, 33)
                                .addComponent(rdyear)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(search1)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(emaillabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(yearlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(10, 10, 10)
                                                        .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(57, 57, 57)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(email)
                                                    .addComponent(year, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(rnlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(57, 57, 57)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(rollno, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel7)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(course, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(splabel)
                                                .addGap(220, 220, 220)))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(61, 61, 61)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(profilepic1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(profilepic, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(coulabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(fnlabel)
                                        .addGap(236, 236, 236)
                                        .addComponent(lnlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(delete)
                                .addComponent(clearall, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchtext, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdname, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdrollno, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdemail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdyear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1))))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(fnlabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lnlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fname))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(profilepic, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rollno, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rnlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(coulabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(course, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(splabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(emaillabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(yearlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(profilepic1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(9, 9, 9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(insert)
                                    .addComponent(update))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)))
                            .addComponent(delete))
                        .addGap(24, 24, 24)
                        .addComponent(clearall, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 204, 153));
        jPanel3.setPreferredSize(new java.awt.Dimension(769, 77));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Student Database");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(371, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap(390, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1043, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1017, 698));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
    if(jTable1.getSelectionModel().isSelectionEmpty())
               {
                 JOptionPane.showMessageDialog(null, "please first select an entry form the table which you want to delete");
               }        
               else
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("delete   from student where rollno=? and course=? and year=?");
            pst.setString(1, (String) jTable1.getValueAt(jTable1.getSelectedRow(), 2));
            pst.setString(2, (String) jTable1.getValueAt(jTable1.getSelectedRow(), 3));
            pst.setString(3, (String) jTable1.getValueAt(jTable1.getSelectedRow(), 4));
            int rs=pst.executeUpdate();
            pst = conn.prepareStatement("delete   from simages where rollno=? and course=? and year=?");
            pst.setString(1, (String) jTable1.getValueAt(jTable1.getSelectedRow(), 2));
            pst.setString(2, (String) jTable1.getValueAt(jTable1.getSelectedRow(), 3));
            pst.setString(3, (String) jTable1.getValueAt(jTable1.getSelectedRow(), 4));
            int rs1=pst.executeUpdate();
            if(rs1!=0)
            System.out.println("suc");
            if(rs!=0)
            {
               model.setRowCount(0);
               insert();
               clearall();
            }
            }catch(Exception ex){
                ex.printStackTrace();
            }        
    }//GEN-LAST:event_deleteActionPerformed

    private void fnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fnameKeyTyped
        // TODO add your handling code here:
        char i;
        i = evt.getKeyChar();
        if(!(Character.isLetter(i) ||(i==KeyEvent.VK_BACK_SPACE) || i==KeyEvent.VK_DELETE)){
            //getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_fnameKeyTyped

    private void insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertActionPerformed
        insert.setSelected(false);        
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("insert into student value(?,?,?,?,?,?)");
            pst.setString(2,course.getSelectedItem().toString());
            pst.setString(1, fname.getText());
            pst.setString(3,year.getSelectedItem().toString());
            pst.setString(4,rollno.getText());
            pst.setString(5,lname.getText());
            pst.setString(6,email.getText());
            int rs=pst.executeUpdate();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bu, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            os.reset();
            ImageIO.write(bm0, "jpg", os);
            InputStream is1 = new ByteArrayInputStream(os.toByteArray());
            pst = conn.prepareStatement("insert into simages(image,course,year,rollno,eximage) value(?,?,?,?,?)");
            pst.setBlob(1,is );
            pst.setBlob(5,is1);
            pst.setString(2,course.getSelectedItem().toString());
            pst.setString(3,year.getSelectedItem().toString());
            pst.setString(4,rollno.getText());
            int rs1 = pst.executeUpdate();
            if(rs1!=0)
            System.out.println("suc");
            if(rs!=0)
            {
               model.setRowCount(0);
               insert();
               clearall();
            }
                        }catch(Exception ex){
                ex.printStackTrace();
            }   
    }//GEN-LAST:event_insertActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
            // TODO add your handling code here:
            admin ad=new admin();
            ad.setVisible(true);
            dispose();
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void lnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lnameKeyTyped
        // TODO add your handling code here:
        char i;
        i = evt.getKeyChar();
        if(!(Character.isLetter(i) ||(i==KeyEvent.VK_BACK_SPACE) || i==KeyEvent.VK_DELETE)){
            //getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_lnameKeyTyped

    private void clearallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearallActionPerformed
        clearall.setSelected(false);
        clearall();
    }//GEN-LAST:event_clearallActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked
void detect()
{
    try
        {
             Image im = ImageIO.read(f);
             buf = (BufferedImage) im;     
        Mat col ;
        col = new Mat(buf.getHeight(), buf.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) buf.getRaster().getDataBuffer()).getData();
        col.put(0, 0, data);
        cvtColor(col, frame, COLOR_BGRA2GRAY);        
        if(faceDetector.empty())
            JOptionPane.showMessageDialog(null, "error loading");

        faceDetector.detectMultiScale(frame, faceDetections,1.1,15,0,new Size(50,50),new Size());
        totaldet=faceDetections.toArray().length;
        bm=new BufferedImage[totaldet];
        Mat m=new Mat();
        int k=0;
        if(totaldet>0)
        {
            for (Rect rect : faceDetections.toArray())
            {
                 rect.x += (int)(rect.height * 0.175);
                 rect.y += (int)(rect.width * 0.175);
                 rect.height -= (int)(rect.height * 0.275);
                 rect.width -= (int)(rect.width * 0.275);
                 m=new Mat(frame,rect);
                 Imgproc.resize(m, m, new Size(150,150));
                 equalizeHist(m, m);
                MatOfByte me = new MatOfByte();
                Highgui.imencode(".bmp", m, me);
                Image i = ImageIO.read(new ByteArrayInputStream(me.toArray()));
                bm[k] = (BufferedImage) i;
                k++;
            }
            for (Rect rect : faceDetections.toArray())
            {
                 rect.x += (int)(rect.height * 0.1);
                 rect.y += (int)(rect.width * 0.1);
                 rect.height -= (int)(rect.height * 0.1);
                 rect.width -= (int)(rect.width * 0.225);
                Core.rectangle(col, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                                        new Scalar(0, 0,255),5);
            }
            MatOfByte me = new MatOfByte();
            Imgproc.resize(col, m, new Size(profilepic1.getWidth(), profilepic1.getHeight()));
            Highgui.imencode(".bmp", m, me);
           Image i = ImageIO.read(new ByteArrayInputStream(me.toArray()));
           BufferedImage bu = (BufferedImage) i;
           profilepic1.setIcon(new ImageIcon(bu));
           bm0=bm[0];
           profilepic.setIcon(new ImageIcon(bm0));
        }
        else
            JOptionPane.showMessageDialog(null, "we didn't find any face in the provided image");
           //profilepic1.setIcon(new ImageIcon(modstudent.class.getResource("imageerror.jpg").getPath()));

        }
        catch(Exception ex)
        {
         ex.printStackTrace();;
        }
}
    private void yearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearActionPerformed

    void setyear()
    {
         try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("Select  * from course where course=?");
            pst.setString(1,course.getSelectedItem().toString());
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
               int x=Integer.parseInt(rs.getString("year"));
               if(x>=1)
               {
                   year.addItem("First Year");
               }
               if(x>=2)
                    year.addItem("Second Year");
               if(x>=3)
                    year.addItem("Third Year");
                   if(x>=4)
                    year.addItem("Fourth Year");
            }
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }
    private void courseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_courseItemStateChanged
        // TODO add your handling code here:
        year.removeAllItems();
        setyear();
       
    }//GEN-LAST:event_courseItemStateChanged
    void search(String co,String sh)
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("Select * from student where "+co+" like '%" + sh + "%'");
            ResultSet rs=pst.executeQuery();
            int i=0;
            if(rs.next())
            {
                do
                {
                    model.insertRow(i,new Object[] { rs.getString("fname"),rs.getString("lname"),rs.getString("rollno"),rs.getString("course"),rs.getString("year"),rs.getString("email")});
                    i++;
                }while(rs.next());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search1ActionPerformed
        // TODO add your handling code here:
        search1.setSelected(false);
        searchtext.setText("");
        model.setRowCount(0);
        clearall();
        insert();
    }//GEN-LAST:event_search1ActionPerformed

    private void rdrollnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdrollnoActionPerformed
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_rdrollnoActionPerformed

    private void rdcourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdcourseActionPerformed
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_rdcourseActionPerformed

    private void rdemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdemailActionPerformed
        // TODO add your handling code here:
       typsearch();
    }//GEN-LAST:event_rdemailActionPerformed

    private void rdyearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdyearActionPerformed
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_rdyearActionPerformed

    private void searchtextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtextKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_searchtextKeyTyped

    private void searchtextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtextKeyReleased
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_searchtextKeyReleased

    private void rdnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdnameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rdnameMouseClicked

    private void rdnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdnameActionPerformed
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_rdnameActionPerformed

    private void searchtextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchtextFocusLost
        // TODO add your handling code here:
       if (searchtext.getText().isEmpty()) {
            searchtext.setForeground(Color.GRAY);
            searchtext.setText("Type here to Search");
        }
    }//GEN-LAST:event_searchtextFocusLost

    private void searchtextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchtextFocusGained
        // TODO add your handling code here:
         if (searchtext.getText().equals("Type here to Search")) {
            searchtext.setText("");
            searchtext.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_searchtextFocusGained

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // TODO add your handling code here:
               if(jTable1.getSelectionModel().isSelectionEmpty())
               {
                 JOptionPane.showMessageDialog(null, "please first select an entry form the table which you want to update");
               }        
               else try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("update student set fname=?,lname=?,rollno=?,email=?,course=?,year=? where rollno=? and course=? and year=?;");
            pst.setString(1,fname.getText());
            pst.setString(2,lname.getText());
            pst.setString(3,rollno.getText());
            pst.setString(4,email.getText());
            pst.setString(5,course.getSelectedItem().toString());
            pst.setString(6,year.getSelectedItem().toString());
            pst.setString(7,jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());
            pst.setString(8,jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
            pst.setString(9,jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());
            int rs=pst.executeUpdate();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bu, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            os.reset();
            ImageIO.write(bm0, "jpg", os);
            InputStream is1 = new ByteArrayInputStream(os.toByteArray());
            pst = conn.prepareStatement("update simages set image=?,rollno=?,course=?,year=?,eximage=? where rollno=? and course=? and year=?");
            pst.setBlob(1,is);
            pst.setString(3,course.getSelectedItem().toString());
            pst.setString(4,year.getSelectedItem().toString());
            pst.setString(2,rollno.getText());
            pst.setBlob(5,is1);            
            pst.setString(6,jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());
            pst.setString(7,jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
            pst.setString(8,jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());            
            int rs1 = pst.executeUpdate();
            if(rs1!=0)
                System.out.println("");        
            if(rs!=0)
            {
               model.setRowCount(0);
               insert();
               clearall();
            }
            
            }catch(Exception ex){
                ex.printStackTrace();
            }
     

    }//GEN-LAST:event_updateActionPerformed

    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
        // TODO add your handling code here:
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.addChoosableFileFilter(imageFilter);
        result = fileChooser.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            s = path;
            f = new File(s);
            try
            {
                Image im = ImageIO.read(f);
                buff = (BufferedImage) im;
                Mat col ;
                col = new Mat(buff.getHeight(), buff.getWidth(), CvType.CV_8UC3);
                byte[] data = ((DataBufferByte) buff.getRaster().getDataBuffer()).getData();
                col.put(0, 0, data);
                Imgproc.resize(col, col, new Size(profilepic1.getWidth(),profilepic1.getHeight()));
                MatOfByte me = new MatOfByte();
                Highgui.imencode(".bmp", col, me);
                Image i = ImageIO.read(new ByteArrayInputStream(me.toArray()));
                bu = (BufferedImage) i;
                profilepic1.setIcon(new ImageIcon(bu));
                JOptionPane.showMessageDialog(null, "detecting face");
                detect();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }

        }
        else if(result == JFileChooser.CANCEL_OPTION){
            System.out.println("No Data");
        }
    }//GEN-LAST:event_browseActionPerformed

    private void browseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_browseMouseReleased

    private void browseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_browseMousePressed

    private void browseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_browseMouseExited

    private void browseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseMouseEntered
        // TODO add your handling code here:
        if(result == JFileChooser.APPROVE_OPTION){
        }
    }//GEN-LAST:event_browseMouseEntered
void typsearch()
{
        try{
            model.setRowCount(0);
            clearall();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            String sh=searchtext.getText().trim();
            PreparedStatement pst;
            if(rdname.isSelected())
            {
                int j=sh.indexOf(" ");
                String fn,ln;
                if(j!=-1)
                {
                    fn=sh.substring(0,j );
                    ln=sh.substring(j+1);
                    pst = conn.prepareStatement("Select  * from student where fname like '%" + fn + "%' and lname like '%"+ln+"%'");
                }
                else
                {
                    fn=sh;
                    ln=sh;
                    pst = conn.prepareStatement("Select distinct * from student where fname like '%" + fn + "%' or lname like '%"+ln+"%'");
                }
                ResultSet rs=pst.executeQuery();
                int i=0;
                if(rs.next())
                {
                    do
                    {
                        model.insertRow(i,new Object[] { rs.getString("fname"),rs.getString("lname"),rs.getString("rollno"),rs.getString("course"),rs.getString("year"),rs.getString("email")});
                        i++;
                    }while(rs.next());
                }
            }
            else if(rdrollno.isSelected())
             search("rollno",sh);
            else if(rdcourse.isSelected())
            search("course",sh);                
            else if(rdemail.isSelected())
            search("email",sh);                
            else if(rdyear.isSelected())
            search("year",sh);                

        }catch(Exception ex){
            ex.printStackTrace();
        }
  
}
 
    void clearall()
    {
    fname.setText("");
    lname.setText("");
    rollno.setText("");
    email.setText(""); 
    course.setSelectedIndex(0);
    year.removeAllItems();
    jTable1.clearSelection();
    profilepic1.setIcon(new ImageIcon(ex.class.getResource("profile.png").getPath()));
    profilepic.setIcon(new ImageIcon());
    setyear();
    }
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(modstudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modstudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modstudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modstudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new modstudent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton browse;
    private javax.swing.JToggleButton clearall;
    private javax.swing.JLabel coulabel;
    private javax.swing.JComboBox<String> course;
    private javax.swing.JToggleButton delete;
    private javax.swing.JTextField email;
    private javax.swing.JLabel emaillabel;
    public javax.swing.JFileChooser fileChooser;
    private javax.swing.JTextField fname;
    private javax.swing.JLabel fnlabel;
    private javax.swing.JToggleButton insert;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JTextField lname;
    private javax.swing.JLabel lnlabel;
    private javax.swing.JLabel profilepic;
    public javax.swing.JLabel profilepic1;
    private javax.swing.JRadioButton rdcourse;
    private javax.swing.JRadioButton rdemail;
    private javax.swing.JRadioButton rdname;
    private javax.swing.JRadioButton rdrollno;
    private javax.swing.JRadioButton rdyear;
    private javax.swing.JLabel rnlabel;
    private javax.swing.JTextField rollno;
    private javax.swing.JToggleButton search1;
    private javax.swing.JTextField searchtext;
    private javax.swing.JLabel splabel;
    private javax.swing.JToggleButton update;
    private javax.swing.JComboBox<String> year;
    private javax.swing.JLabel yearlabel;
    // End of variables declaration//GEN-END:variables
}

