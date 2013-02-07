package team2809.smartdashboard.extensions;

// FRC
import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.properties.BooleanProperty;
import edu.wpi.first.smartdashboard.properties.IntegerProperty;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpijavacv.*;

// OpenCV
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

public class CppVision extends WPICameraExtension  {
    public static final String NAME = "CppVision";

    @Override
    public WPIImage processImage(WPIColorImage rawImage) {

        IplImage image = IplImage.createFrom(rawImage.getBufferedImage());
    
        if( Robot.getTable().getBoolean("take_image") ) {
            cvSaveImage( "../../image.png", image );
            
            // call OpenCV vision program
            try {
                String[] cmd = { "../../cppMain/vision.exe" };
                Process p = Runtime.getRuntime().exec(cmd);
                p.waitFor();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            // Load OpenCV results
            
            // Place Results in NetworkTables
            
            // Set vision_done flag
            Robot.getTable().putBoolean("vision_done", true);
        }
        
        return rawImage;
    }// end processImage
    
}// end CppVision

