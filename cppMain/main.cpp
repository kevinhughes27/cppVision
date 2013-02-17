#include <fstream>

#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>

using namespace cv;
using namespace std;

int main()
{
	// File IO test
	ofstream f;
	f.open ("target.txt");
	f << "1\n";
	f << "320\n";
	f << "240\n";
	f.close();
	
	// OpenCV test
	Mat image = imread("image.png");
	imshow("image", image);
	waitKey();
	
	return 0;
}