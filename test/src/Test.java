import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Core;
import org.opencv.highgui.Highgui;
import java.io.*;

class Test {

  static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

  public static void main(String[] args) {

    Mat img = Highgui.imread("image.png");
    //Highgui.imwrite("test.png", img);

    MatOfByte buf = new MatOfByte();
    Highgui.imencode(".png", img, buf);
    byte[] byteArray = buf.toArray();
    //System.out.println("OpenCV Mat data:\n" + buf.dump());

    try {
        String[] cmd = { "./cppvision" };
        Process p = Runtime.getRuntime().exec(cmd);

        OutputStream stdin = p.getOutputStream();
        BufferedOutputStream outstream = new BufferedOutputStream(stdin);

        outstream.write(byteArray);
        outstream.close();

        p.waitFor();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

  }

}
