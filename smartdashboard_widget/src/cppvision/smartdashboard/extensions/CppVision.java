package cppvision.smartdashboard.extensions;

// Java
import java.io.*;

// FRC
import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.properties.BooleanProperty;
import edu.wpi.first.smartdashboard.properties.IntegerProperty;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpijavacv.*;

// OpenCV
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

// WPI
import edu.wpi.first.wpijavacv.WPIColor;
import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIImage;
import edu.wpi.first.wpijavacv.WPIPoint;

public class CppVision extends WPICameraExtension  {
    public static final String NAME = "CppVision";

    // desired target position
    public static int desiredX = 320;
    public static int desiredY = 240;
    public static int xTol = 15;
    public static int yTol = 15;
    
    // points for the overlays
    public static WPIPoint hA = new WPIPoint( 320, 0 );
    public static WPIPoint hB = new WPIPoint( 320, 480 );
    public static WPIPoint vA = new WPIPoint( 0, 240 );
    public static WPIPoint vB = new WPIPoint( 640, 240 );
    
    @Override
    public WPIImage processImage(WPIColorImage rawImage) {

        // default ovelay
        rawImage.drawLine(vA, vB, WPIColor.RED, 1);
        rawImage.drawLine(hA, hB, WPIColor.RED, 1);
    
        // run vision system
        if( Robot.getTable().getBoolean("runVision") ) {
            
            Robot.getTable().putBoolean("doneVision", false);
            
            // get image to OpenCV
            IplImage image = IplImage.createFrom(rawImage.getBufferedImage());
            
            // save image
            cvSaveImage( "image.png", image );
            
            // call OpenCV vision program
            try {
                String[] cmd = { "vision.exe" };
                Process p = Runtime.getRuntime().exec(cmd);
                p.waitFor();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            // Load OpenCV results
            boolean targetB = (0 == 0);
            int targetX = 0;
            int targetY = 0;
            
            try {
                // Open the file
                FileInputStream fstream = new FileInputStream("target.txt");
                // Get the object of DataInputStream
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                
                //Read File Line By Line
                //while ((strLine = br.readLine()) != null)   {
                    // Print the content on the console
                    //System.out.println (strLine);
                //}
                
                targetB = Integer.parseInt( br.readLine() ) == 1;
                targetX = Integer.parseInt( br.readLine() );
                targetY = Integer.parseInt( br.readLine() );
                
                //Close the input stream
                in.close();
            
            //Catch exception if any
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            
            // Place Results in NetworkTables
            Robot.getTable().putBoolean("targetB", targetB);
            Robot.getTable().putNumber("targetX", targetX);
            Robot.getTable().putNumber("targetY", targetY);
            
            // draw camera overlay
            // "aimed" overlay
            if( targetB == true ) {   
                
                // crosshair
                int crosshairSize = 10;
                WPIPoint l = new WPIPoint( targetX-crosshairSize, targetY );
                WPIPoint r = new WPIPoint( targetX+crosshairSize, targetY );
                WPIPoint t = new WPIPoint( targetX, targetY-crosshairSize );
                WPIPoint b = new WPIPoint( targetX, targetY+crosshairSize );
                
                rawImage.drawLine(l, r, WPIColor.GREEN, 1);
                rawImage.drawLine(t, b, WPIColor.GREEN, 1);
                
                // thicken overlay when aimed
                if( Math.abs(targetX - desiredX) <= xTol && Math.abs(targetY - desiredY) <= yTol) {
                    rawImage.drawLine(vA, vB, WPIColor.GREEN, 1);
                    rawImage.drawLine(hA, hB, WPIColor.GREEN, 1);
                }
            }
            
            // Set vision_done flag
            Robot.getTable().putBoolean("doneVision", true);
        
        // vision system not needed
        } else {
           // pass
        }
         
        return rawImage;
    }// end processImage
    
}// end CppVision

