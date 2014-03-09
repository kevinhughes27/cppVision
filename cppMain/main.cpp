#include <fstream>
#include <iostream>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>

using namespace cv;
using namespace std;

int main(int argc, char *argv[])
{
	if(argc < 2)
	{
		// load image from stdin
		vector<uchar> buf;
		char c;
		while (cin.get(c)) {
			buf.push_back( (uchar)c );
    }

		Mat image = imdecode(Mat(buf), 1);
		imshow("image", image);
		waitKey();
	}
	else
	{
		// load test image
		Mat image = imread(argv[1]);
		imshow("image", image);
		waitKey();
	}

	/*
		Perform image processing here and then write the results to "target.txt".

		Note - you'll probably want to remove the waitKey line above after you are
		sure that the image is being passed correctly.
	*/

	// File IO test
	ofstream f;
	f.open ("target.txt");
	f << "1\n";
	f << "320\n";
	f << "240\n";
	f.close();

	return 0;
}
