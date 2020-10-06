/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Image;
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
import javax.swing.JFileChooser;
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
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author santosh
 */
public class modteach extends javax.swing.JFrame {

    /**
     * Creates new form modteach
     */
    private String s;
    private File f;
    private ImageIcon img;
    private BufferedImage buff;
    private BufferedImage bu;
    private final DefaultTableModel model;
    public modteach() {
    initComponents();
    ButtonGroup bg=new ButtonGroup();
    bg.add(rduserid);
    bg.add(rdgender);
    bg.add(rdemail);
    bg.add(rdname);
    rdname.setSelected(true);
    String[] columnNames = {"first name","last name","gender","email","userid","password"};
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
                  // update.setEnabled(false);
                   //delete.setEnabled(false);
               }
             else{
                 fname.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
                 lname.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 1));
                 gender.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), 2));  
                 email.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 3));
                 userid.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 4));
                 password.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString());  
        try{           
        Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");     
        PreparedStatement ps = con.prepareStatement("select * from teacher where userid=?");
        ps.setString(1,jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());
        ResultSet rs = ps.executeQuery();
        rs.next();  
        ByteArrayInputStream bais = new ByteArrayInputStream(rs.getBytes("profilepic"));
        bu=ImageIO.read(bais);
        profilepic.setIcon(new ImageIcon(rs.getBytes("profilepic")));
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
          
            }}
    });
       
       
       
    super.addWindowFocusListener(new WindowAdapter() {
    public void windowGainedFocus(WindowEvent e) {
        fname.requestFocusInWindow();
    }
    });
    
    }
void insert()
    {
         try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("Select  * from teacher ");
            ResultSet rs=pst.executeQuery();
            int i=0;
            if(rs.next())
            {
                do{
                   
                    model.insertRow(i,new Object[] { rs.getString("fname"),rs.getString("lname"),rs.getString("gender"),rs.getString("email"),rs.getString("userid"),rs.getString("password")});
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        fileChooser = new javax.swing.JFileChooser();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        clearall = new javax.swing.JToggleButton();
        lname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        back = new javax.swing.JToggleButton();
        insert = new javax.swing.JToggleButton();
        fname = new javax.swing.JTextField();
        userid = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        delete = new javax.swing.JToggleButton();
        update = new javax.swing.JToggleButton();
        password = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        profilepic = new javax.swing.JLabel();
        browse = new javax.swing.JToggleButton();
        gender = new javax.swing.JComboBox<>();
        searchtext = new javax.swing.JTextField();
        search1 = new javax.swing.JToggleButton();
        jLabel14 = new javax.swing.JLabel();
        rdname = new javax.swing.JRadioButton();
        rduserid = new javax.swing.JRadioButton();
        rdemail = new javax.swing.JRadioButton();
        rdgender = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();

        fileChooser.setPreferredSize(new java.awt.Dimension(700, 500));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AUTO MARKING ATTENDANCE USING FACIAL RECOGNITION");

        jPanel2.setBackground(new java.awt.Color(0, 204, 153));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("User ID");

        clearall.setBackground(new java.awt.Color(255, 255, 255));
        clearall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clearall1.jpg"))); // NOI18N
        clearall.setBorder(null);
        clearall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearallActionPerformed(evt);
            }
        });

        lname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.lname}"), lname, org.jdesktop.beansbinding.BeanProperty.create("text"), "b");
        bindingGroup.addBinding(binding);

        lname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lnameKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("Last name");

        back.setBackground(new java.awt.Color(255, 255, 255));
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/aback.png"))); // NOI18N
        back.setToolTipText("");
        back.setBorder(null);
        back.setOpaque(true);
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fname}"), fname, org.jdesktop.beansbinding.BeanProperty.create("text"), "a");
        bindingGroup.addBinding(binding);

        fname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fnameKeyTyped(evt);
            }
        });

        userid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.userid}"), userid, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.email}"), email, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel11.setText("Gender");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setText("First name");

        delete.setBackground(new java.awt.Color(255, 255, 255));
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        delete.setBorder(null);
        delete.setOpaque(true);
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        update.setBackground(new java.awt.Color(255, 255, 255));
        update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        update.setBorder(null);
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        password.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.password}"), password, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Password");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setText("Email id");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("ProfilePic");

        browse.setBackground(new java.awt.Color(255, 255, 255));
        browse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/browse.png"))); // NOI18N
        browse.setBorder(null);
        browse.setOpaque(true);
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });

        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

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
        });

        search1.setBackground(new java.awt.Color(255, 255, 255));
        search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clearall.jpg"))); // NOI18N
        search1.setToolTipText("");
        search1.setBorder(null);
        search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search1ActionPerformed(evt);
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

        rduserid.setBackground(new java.awt.Color(255, 255, 255));
        rduserid.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rduserid.setText("Userid");
        rduserid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rduseridActionPerformed(evt);
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

        rdgender.setBackground(new java.awt.Color(255, 255, 255));
        rdgender.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdgender.setText("Gender");
        rdgender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdgenderActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("<html>&nbsp&nbsp;clear &nbsp;&nbsp;search<br> &nbsp;  field</html> ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Back");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("<html>&nbsp;Browse<br>&nbsp;image");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("   Add New");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("    Update");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Clear selection");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("         Delete");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchtext, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdname)
                                        .addGap(33, 33, 33)
                                        .addComponent(rduserid)
                                        .addGap(33, 33, 33)
                                        .addComponent(rdemail)
                                        .addGap(33, 33, 33)
                                        .addComponent(rdgender)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(67, 67, 67)
                                        .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)
                                        .addComponent(jLabel2)
                                        .addGap(48, 48, 48)
                                        .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(78, 78, 78)
                                                .addComponent(userid, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(78, 78, 78)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(email)
                                                    .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(back))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(70, 70, 70)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(93, 93, 93)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(10, 10, 10)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(browse))))
                                        .addGap(30, 30, 30)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(profilepic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(93, 93, 93)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(insert)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(update)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(87, 87, 87)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(delete)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(clearall)))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(search1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchtext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdname, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rduserid, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdemail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdgender, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fname)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(password, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(userid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(browse)
                                    .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6))))
                            .addComponent(profilepic, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(insert, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel15))))
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(delete)
                            .addComponent(clearall))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))))
                .addGap(0, 0, 0)
                .addComponent(jLabel7)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 204, 153));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Teacher Database");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(420, 420, 420)
                .addComponent(jLabel12)
                .addGap(420, 420, 420))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(20, 20, 20))
        );

        jPanel4.setBackground(new java.awt.Color(0, 204, 153));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Copyright@2018 Auto Attendance System");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(442, 442, 442)
                .addComponent(jLabel13)
                .addGap(390, 390, 390))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel13)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        bindingGroup.bind();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
        // TODO add your handling code here:
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.addChoosableFileFilter(imageFilter);
        int result = fileChooser.showSaveDialog(null);
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
                 Imgproc.resize(col, col, new Size(profilepic.getWidth(),profilepic.getHeight()));
                 MatOfByte me = new MatOfByte();
                 Highgui.imencode(".bmp", col, me);
                 Image i = ImageIO.read(new ByteArrayInputStream(me.toArray()));
                 bu = (BufferedImage) i;
                 profilepic.setIcon(new ImageIcon(bu));
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

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
      if(jTable1.getSelectionModel().isSelectionEmpty())
               {
                 JOptionPane.showMessageDialog(null, "please first select an entry form the table which you want to update");
               }        
               else 
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("update teacher set fname=?,lname=?,userid=?,password=?,email=?,profilepic=?,gender=? where userid=?;");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bu, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            pst.setString(1, fname.getText());
            pst.setString(2,lname.getText());
            pst.setString(3,userid.getText());
            pst.setString(4,password.getText());
            pst.setString(5,email.getText());
            pst.setBlob(6,is );
            pst.setString(7,gender.getSelectedItem().toString());
            pst.setString(8, jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());
            int rs=pst.executeUpdate();
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

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
 if(jTable1.getSelectionModel().isSelectionEmpty())
               {
                 JOptionPane.showMessageDialog(null, "please first select an entry form the table which you want to delete");
               }
             else try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("delete   from teacher where userid=?");
            pst.setString(1, (String) jTable1.getValueAt(jTable1.getSelectedRow(), 4));
            int rs=pst.executeUpdate();
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
            PreparedStatement pst = conn.prepareStatement("insert into teacher value(?,?,?,?,?,?,?)");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bu, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            pst.setString(1, fname.getText());
            pst.setString(2,lname.getText());
            pst.setString(3,userid.getText());
            pst.setString(4,password.getText());
            pst.setString(5,email.getText());
            pst.setBlob(6,is );
            pst.setString(7,gender.getSelectedItem().toString());
            int rs=pst.executeUpdate();
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

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
      admin a=new admin();
      a.setVisible(true);
      dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_backActionPerformed

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

    private void search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search1ActionPerformed
        // TODO add your handling code here:
        search1.setSelected(false);
        searchtext.setText("");
        
    }//GEN-LAST:event_search1ActionPerformed
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
                    pst = conn.prepareStatement("Select  * from teacher where fname like '%" + fn + "%' and lname like '%"+ln+"%'");
                }
                else
                {
                    fn=sh;
                    ln=sh;
                    pst = conn.prepareStatement("Select distinct * from teacher where fname like '%" + fn + "%' or lname like '%"+ln+"%'");
                }
                ResultSet rs=pst.executeQuery();
                int i=0;
                if(rs.next())
                {
                    do
                    {
                    model.insertRow(i,new Object[] { rs.getString("fname"),rs.getString("lname"),rs.getString("gender"),rs.getString("email"),rs.getString("userid"),rs.getString("password")});
                        i++;
                    }while(rs.next());
                }
            }
            else if(rduserid.isSelected())
             search("userid",sh);               
            else if(rdemail.isSelected())
            search("email",sh);                
            else if(rdgender.isSelected())
            search("gender",sh);                

        }catch(Exception ex){
            ex.printStackTrace();
        }
  
}
void search(String co,String sh)
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");
            PreparedStatement pst = conn.prepareStatement("Select * from teacher where "+co+" like '%" + sh + "%'");
            ResultSet rs=pst.executeQuery();
            int i=0;
            if(rs.next())
            {
                do
                {
                    model.insertRow(i,new Object[] { rs.getString("fname"),rs.getString("lname"),rs.getString("gender"),rs.getString("email"),rs.getString("userid"),rs.getString("password")});
                    i++;
                }while(rs.next());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void rdnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdnameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rdnameMouseClicked

    private void rdnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdnameActionPerformed
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_rdnameActionPerformed

    private void rduseridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rduseridActionPerformed
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_rduseridActionPerformed

    private void rdemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdemailActionPerformed
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_rdemailActionPerformed

    private void rdgenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdgenderActionPerformed
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_rdgenderActionPerformed

    private void searchtextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtextKeyReleased
        // TODO add your handling code here:
        typsearch();
    }//GEN-LAST:event_searchtextKeyReleased

    private void searchtextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchtextFocusGained
        // TODO add your handling code here:
       
          if (searchtext.getText().equals("Type here to Search")) {
            searchtext.setText("");
            searchtext.setForeground(Color.BLACK);
        }                             
       
    }//GEN-LAST:event_searchtextFocusGained

    private void searchtextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchtextFocusLost
    
      if (searchtext.getText().isEmpty()) {
            searchtext.setForeground(Color.GRAY);
            searchtext.setText("Type here to Search");
        }
    }//GEN-LAST:event_searchtextFocusLost

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
         

    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:3
        fname.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
        lname.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 1));
        gender.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), 2));  
        email.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 3));
        userid.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 4));
        password.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString()); 
    }//GEN-LAST:event_jTable1KeyReleased

    void clearall()
    {
    fname.setText("");
    lname.setText("");
    password.setText("");
    email.setText(""); 
    userid.setText("");
    gender.setSelectedIndex(0);
    jTable1.clearSelection();
    profilepic.setIcon(new ImageIcon());
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
            java.util.logging.Logger.getLogger(modteach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modteach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modteach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modteach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new modteach().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton back;
    private javax.swing.JToggleButton browse;
    private javax.swing.JToggleButton clearall;
    private javax.swing.JToggleButton delete;
    private javax.swing.JTextField email;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JTextField fname;
    private javax.swing.JComboBox<String> gender;
    private javax.swing.JToggleButton insert;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField password;
    private javax.swing.JLabel profilepic;
    private javax.swing.JRadioButton rdemail;
    private javax.swing.JRadioButton rdgender;
    private javax.swing.JRadioButton rdname;
    private javax.swing.JRadioButton rduserid;
    private javax.swing.JToggleButton search1;
    private javax.swing.JTextField searchtext;
    private javax.swing.JToggleButton update;
    private javax.swing.JTextField userid;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
