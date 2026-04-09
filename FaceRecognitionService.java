package face_recognition_web.service;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.io.File;
import java.util.Base64;

import static org.bytedeco.opencv.global.opencv_core.CV_8U;
import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imdecode;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.equalizeHist;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

public class FaceRecognitionService {

    public String recognizeBase64Image(String base64Image) {
        try {
            Loader.load(org.bytedeco.opencv.opencv_java.class);

            CascadeClassifier detector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
            if (detector.empty()) {
                return "{\"status\":\"error\",\"message\":\"Cascade file not found\"}";
            }

            LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();

            File trainerFile = new File("trainer.yml");
            if (!trainerFile.exists()) {
                return "{\"status\":\"error\",\"message\":\"Model not trained\"}";
            }

            recognizer.read("trainer.yml");

            String base64Data = base64Image.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            Mat imgMat = new Mat(1, imageBytes.length, CV_8U);
            imgMat.data().put(imageBytes);

            Mat frame = imdecode(imgMat, IMREAD_COLOR);
            if (frame.empty()) {
                return "{\"status\":\"error\",\"message\":\"Image decode failed\"}";
            }

            Mat gray = new Mat();
            cvtColor(frame, gray, COLOR_BGR2GRAY);
            equalizeHist(gray, gray);
            GaussianBlur(gray, gray, new Size(3, 3), 0);

            RectVector faces = new RectVector();
            detector.detectMultiScale(
                    gray,
                    faces,
                    1.2,
                    5,
                    0,
                    new Size(100, 100),
                    new Size()
            );

            if (faces.size() == 0) {
                return "{\"status\":\"unknown\",\"message\":\"No face detected\"}";
            }

            Rect face = faces.get(0);
            Mat faceROI = new Mat(gray, face);

            Mat resizedFace = new Mat();
            resize(faceROI, resizedFace, new Size(160, 160));
            equalizeHist(resizedFace, resizedFace);

            IntPointer label = new IntPointer(1);
            DoublePointer confidence = new DoublePointer(1);

            recognizer.predict(resizedFace, label, confidence);

            int id = label.get(0);
            double conf = confidence.get(0);

            double THRESHOLD = 55;

            if (conf < THRESHOLD) {
                String userData = DBHelperFetch.getUserInfo(id);

                String name = extract(userData, "Name:");
                String age = extract(userData, "Age:");
                String dept = extract(userData, "Dept:");
                String info = extract(userData, "Info:");

                return "{"
                        + "\"status\":\"matched\","
                        + "\"name\":\"" + escape(name) + "\","
                        + "\"age\":\"" + escape(age) + "\","
                        + "\"dept\":\"" + escape(dept) + "\","
                        + "\"info\":\"" + escape(info) + "\","
                        + "\"confidence\":" + conf
                        + "}";
            }

            return "{\"status\":\"unknown\",\"message\":\"Face not recognized\",\"confidence\":" + conf + "}";

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"error\",\"message\":\"" + escape(e.getMessage()) + "\"}";
        }
    }

    private String extract(String text, String key) {
        try {
            int start = text.indexOf(key);
            if (start == -1) return "";
            start += key.length();
            int end = text.indexOf("\n", start);
            if (end == -1) end = text.length();
            return text.substring(start, end).trim();
        } catch (Exception e) {
            return "";
        }
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}