/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_COLOR;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.helper.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_core.CvType;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_face;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imdecode;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import org.opencv.core.Core;
//import org.bytedeco.javacpp.opencv_core.Mat;
//import org.bytedeco.javacpp.opencv_core.MatVector;
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


/**
 *
 * @author niraj
 */
public class UpdateStudentDetails extends javax.swing.JFrame {

    /**
     * Creates new form UpdateStudentDetails
     *
     *
     */
    MatVector images;
    File f;
    Mat labels;
     ResultSet rs;
     String currentname;
     FaceRecognizer faceRecognizer ;
     MatOfRect faceDetections = new MatOfRect();
    String[] name;
 CascadeClassifier faceDetector = new CascadeClassifier(livecamera.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
     BufferedImage buf;
    org.opencv.core.Mat  frame=new org.opencv.core.Mat();
    org.opencv.core.Mat col;
    private int totaldet;
    private BufferedImage[] bm;
    public UpdateStudentDetails() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, (dim.height-40)/2-this.getSize().height/2);
        try{           
        Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facedb?" + "user=root&password=root");     
        Statement ps = con.createStatement(
                           ResultSet.TYPE_SCROLL_INSENSITIVE,
                           ResultSet.CONCUR_UPDATABLE);
       String sql="select * from studentinfo order by name asc";
       rs = ps.executeQuery(sql);

       rs.next();

       currentname=new String(rs.getString(1));
       stuname.setText(rs.getString(1));
       stuimg.setIcon(new ImageIcon(rs.getBytes(2)));        
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
        try {
           
             int total=getrow();
             name=new String[total];
             int cur=rs.getRow();
            images=new MatVector(total);
            labels = new Mat(total, 1, CV_32SC1);
            IntBuffer labelsBuf = labels.createBuffer();
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
              Mat m=imdecode(new Mat(new BytePointer(rs.getBytes(2)),false),CV_LOAD_IMAGE_GRAYSCALE);
              name[i]=rs.getString(1);
              //int label = i;
             images.put(i, m);
            labelsBuf.put(i, i);
            i++;
            }
            //faceRecognizer=EigenFaceRecognizer.create();

           // faceRecognizer=FisherFaceRecognizer.create();
            faceRecognizer=LBPHFaceRecognizer.create();
            faceRecognizer.train(images, labels);
           // JOptionPane.showMessageDialog(null, "success");
           rs.absolute(cur);
        }catch (SQLException ex) {
            Logger.getLogger(UpdateStudentDetails.class.getName()).log(Level.SEVERE, null, ex);
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
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        stuimg = new javax.swing.JLabel();
        stuname = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMinimumSize(new java.awt.Dimension(700, 500));
        fileChooser.setPreferredSize(new java.awt.Dimension(900, 600));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton12.setText("<<");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("<");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("delete");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        stuname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stunameActionPerformed(evt);
            }
        });

        jButton15.setText(">");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText(">>");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("update");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 778, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
        );

        jTextField2.setEditable(false);
        jTextField2.setEnabled(false);

        jButton10.setText("choose image");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton2.setText("load data");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Detect and recognize Face");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButton13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(stuimg, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(stuname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(stuimg, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(stuname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton13)
                            .addComponent(jButton15)
                            .addComponent(jButton12)
                            .addComponent(jButton16))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton17)
                            .addComponent(jButton14))
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(0, 146, Short.MAX_VALUE)))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
         try {
             // TODO add your handling code here:
             if(rs.isFirst())
             {
                 
             }
             else if(rs.previous())
             {
                currentname=new String(rs.getString(1));
                 stuname.setText(rs.getString(1));
                 stuimg.setIcon(new ImageIcon(rs.getBytes(2)));
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
         try {
             // TODO add your handling code here:
             if(rs.first())
             {
                currentname=new String(rs.getString(1));
                 stuname.setText(rs.getString(1));
                 stuimg.setIcon(new ImageIcon(rs.getBytes(2)));
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
         try {
             // TODO add your handling code here:
             if(rs.isLast())
             {
                rs.deleteRow();
                jButton12.doClick();
             }
             else 
             {
               rs.deleteRow();
             jButton16.doClick();
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
         try {
             // TODO add your handling code here:
             if(rs.last())
             {
                 currentname=new String(rs.getString(1));
                 stuname.setText(rs.getString(1));
                 stuimg.setIcon(new ImageIcon(rs.getBytes(2)));
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
         try {
             // TODO add your handling code here:
             if(rs.isLast())
             {
                 
             }
             else if(rs.next())
             {
                 currentname=new String(rs.getString(1));
                 stuname.setText(rs.getString(1));
                 stuimg.setIcon(new ImageIcon(rs.getBytes(2)));
             }

         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
         try{           
        rs.updateString(1, stuname.getText());
        rs.updateRow();
        stuname.setText(rs.getString(1));
        stuimg.setIcon(new ImageIcon(rs.getBytes(2)));
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void stunameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stunameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stunameActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        // JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png","bmp");
        FileFilter imageFilter = new FileNameExtensionFilter(
            "Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.addChoosableFileFilter(imageFilter);
        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
             // label.setIcon(ResizeImage(path));
             String s = path;
              f = new File(s);
              jTextField2.setText(s);
            try{
                Image img=ImageIO.read(f);
                BufferedImage bu = (BufferedImage) img;
                Graphics g=jPanel1.getGraphics();
                g.drawImage(bu, 0, 0, jPanel1.getWidth(), jPanel1.getHeight(), 0, 0, bu.getWidth(), bu.getHeight(), null);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }

        }
        else if(result == JFileChooser.CANCEL_OPTION){
            System.out.println("No Data");
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    int getrow()
    {
        int rownumber=0;
        
        try {
            int cur=rs.getRow();
            rs.beforeFirst();
            while(rs.next())
            {
                rownumber++;
            }

    rs.absolute(cur);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rownumber;    

    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
           
             int total=getrow();
             name=new String[total];
             int cur=rs.getRow();
            images=new MatVector(total);
            labels = new Mat(total, 1, CV_32SC1);
            IntBuffer labelsBuf = labels.createBuffer();
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
              Mat m=imdecode(new Mat(new BytePointer(rs.getBytes(2)),false),CV_LOAD_IMAGE_GRAYSCALE);
              name[i]=rs.getString(1);
              //int label = i;
             images.put(i, m);
            labelsBuf.put(i, i);
            i++;
            }
            //faceRecognizer=EigenFaceRecognizer.create();

           // faceRecognizer=FisherFaceRecognizer.create();
            faceRecognizer=LBPHFaceRecognizer.create();
            faceRecognizer.train(images, labels);
            JOptionPane.showMessageDialog(null, "success");
           rs.absolute(cur);
        }catch (SQLException ex) {
            Logger.getLogger(UpdateStudentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
          
     
    }//GEN-LAST:event_jButton2ActionPerformed
void rec(int k)
{
        Loader.load(opencv_core.class);
        IntPointer label = new IntPointer(1);
        DoublePointer confidence = new DoublePointer(1);
// BytePointer b = new BytePointer(((DataBufferByte) bm[0].getRaster().getDataBuffer()).getData());
// Mat m =imread(b, CV_LOAD_IMAGE_GRAYSCALE);
//       Mat m=imread()
//   byte[] data = ((DataBufferByte) bm[0].getRaster().getDataBuffer()).getData();
//int DataBufferByte = 0;
//ByteBuffer.wrap(data);
//BytePointer b=new BytePointer(ByteBuffer.wrap(data));
// Mat m=imdecode(new Mat(b,false),CV_LOAD_IMAGE_GRAYSCALE);

//Mat m = new Mat(bm[0].getHeight(),bm[0].getWidth(),bm[0].getType(),new BytePointer(ByteBuffer.wrap((DataBufferByte)bm[0].getRaster().getDataBuffer()).getData()));
 //BytePointer bp=new BytePointer(ByteBuffer.wrap(image.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData));
//new Image(new IplImage(m),name,itype);
       Mat m=imread("C:\\Users\\niraj\\Pictures\\nil.jpg", CV_LOAD_IMAGE_GRAYSCALE);
        faceRecognizer.predict(m, label, confidence);
        int predictedLabel = label.get(0);
        double con=confidence.get(0);
        JOptionPane.showMessageDialog(null, Integer.toString(predictedLabel)+"  "+name[predictedLabel]+" "+con);       
        Rect[] rect = faceDetections.toArray();
        Core.putText (
         col,name[predictedLabel],new Point(rect[k].x+rect[k].width/4,rect[k].y),Core.FONT_HERSHEY_SIMPLEX ,3,new Scalar(255, 0, 0),4);
        MatOfByte me = new MatOfByte();
            Image i;
            BufferedImage bu;
                org.opencv.core.Mat mo=new org.opencv.core.Mat();
            Imgproc.resize(col, mo, new Size(jLabel1.getWidth(), jLabel1.getHeight()));
            Highgui.imencode(".bmp", mo, me);
        try {
            i = ImageIO.read(new ByteArrayInputStream(me.toArray()));
             bu = (BufferedImage) i;
            jLabel1.setIcon(new ImageIcon(bu));
        } catch (IOException ex) {
            Logger.getLogger(UpdateStudentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        try
        {
            Image im = ImageIO.read(f);
            buf = (BufferedImage) im;
            col = new org.opencv.core.Mat(buf.getHeight(), buf.getWidth(),org.opencv.core.CvType.CV_8UC3);
            byte[] data1 = ((DataBufferByte) buf.getRaster().getDataBuffer()).getData();
            col.put(0, 0, data1);
            
            cvtColor(col, frame, COLOR_BGRA2GRAY);        

            if(faceDetector.empty())
            JOptionPane.showMessageDialog(null, "error loading");
            faceDetector.detectMultiScale(frame, faceDetections,1.1,5,0,new Size(50,50),new Size());
            totaldet=faceDetections.toArray().length;
            bm=new BufferedImage[totaldet];
            org.opencv.core.Mat m=new org.opencv.core.Mat();
            int k=0;
            for (Rect rect : faceDetections.toArray())
            {
                rect.x += (int)(rect.height * 0.1);
                rect.y += (int)(rect.width * 0.1);
                rect.height -= (int)(rect.height * 0.1);
                rect.width -= (int)(rect.width * 0.225);
                m=new org.opencv.core.Mat(frame,rect);
                Imgproc.resize(m, m, new Size(200,200));
                equalizeHist(m, m);
                MatOfByte me = new MatOfByte();
                Highgui.imencode(".jpg", m, me);
                Image i = ImageIO.read(new ByteArrayInputStream(me.toArray()));
                bm[k] = (BufferedImage) i;
                Highgui.imwrite("C:\\Users\\niraj\\Pictures\\nil.jpg", m);
               rec(k);
               k++;
            }
            for (Rect rect : faceDetections.toArray())
            {
                rect.x += (int)(rect.height * 0.1);
                rect.y += (int)(rect.width *+ 0.1);
                rect.height -= (int)(rect.height * 0.1);
                rect.width -= (int)(rect.width * 0.225);
                Core.rectangle(col, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                new Scalar(0, 0,255),5);
            }
            MatOfByte me = new MatOfByte();
            Image i;
            BufferedImage bu;
            Imgproc.resize(col, m, new Size(jLabel1.getWidth(), jLabel1.getHeight()));
            Highgui.imencode(".bmp", m, me);
            i = ImageIO.read(new ByteArrayInputStream(me.toArray()));
            bu = (BufferedImage) i;
            jLabel1.setIcon(new ImageIcon(bu));
        }
        catch(Exception ex)
        {
          ex.printStackTrace();;
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateStudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateStudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateStudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateStudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateStudentDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel stuimg;
    private javax.swing.JTextField stuname;
    // End of variables declaration//GEN-END:variables
}
