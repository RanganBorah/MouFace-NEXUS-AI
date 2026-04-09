package face_recognition_web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;

public class ImageSaveService {

    public static void saveBase64Images(List<String> base64Images, String name) throws Exception {
        String folderPath = "dataset";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (int i = 0; i < base64Images.size(); i++) {
            String base64Image = base64Images.get(i);
            String base64Data = base64Image.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            String fileName = folderPath + "/" + name + "_" + (i + 1) + ".png";

            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(imageBytes);
            }
        }
    }
}