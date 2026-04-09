package face_recognition_web.service;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.io.File;
import java.util.Base64;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_core.*;

public class FaceCaptureService {

    public void saveCapturedFaces(int id, List<String> base64Images) throws Exception {

        Loader.load(org.bytedeco.opencv.opencv_java.class);

        CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
        if (faceDetector.empty()) {
            throw new RuntimeException("Cascade file not found!");
        }

        File datasetDir = new File("dataset");
        if (!datasetDir.exists()) datasetDir.mkdirs();

        int count = 0;

        for (String base64Image : base64Images) {

            if (count >= 50) break;

            String base64Data = base64Image.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            Mat imgMat = new Mat(1, imageBytes.length, CV_8U);
            imgMat.data().put(imageBytes);

            Mat frame = imdecode(imgMat, IMREAD_COLOR);
            if (frame.empty()) continue;

            Mat gray = new Mat();
            cvtColor(frame, gray, COLOR_BGR2GRAY);

            equalizeHist(gray, gray);
            GaussianBlur(gray, gray, new Size(3,3), 0);

            RectVector faces = new RectVector();

            faceDetector.detectMultiScale(
                    gray,
                    faces,
                    1.2,
                    5,
                    0,
                    new Size(100,100),
                    new Size()
            );

            for (int i = 0; i < faces.size(); i++) {

                if (count >= 50) break;

                Rect face = faces.get(i);
                Mat faceCrop = new Mat(gray, face);

                Mat resizedFace = new Mat();
                resize(faceCrop, resizedFace, new Size(160,160));

                equalizeHist(resizedFace, resizedFace);

                count++;

                String filename = "dataset/user." + id + "." + count + ".jpg";
                imwrite(filename, resizedFace);
            }
        }

        if (count == 0) {
            throw new RuntimeException("No face detected!");
        }
    }
}