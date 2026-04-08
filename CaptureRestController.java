package face_recognition_web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import face_recognition_web.model.BatchCaptureRequest;
import face_recognition_web.service.DBHelperSave;
import face_recognition_web.service.FaceCaptureService;
import face_recognition_web.service.FaceTrainingService;

@RestController
public class CaptureRestController {

    @PostMapping("/saveCaptureBatch")
    public String saveCaptureBatch(@RequestBody BatchCaptureRequest request) {
        try {
            int id = DBHelperSave.insertUser(
                    request.getName(),
                    request.getAge(),
                    request.getDept(),
                    request.getInfo()
            );

            new FaceCaptureService().saveCapturedFaces(id, request.getImages());
            new FaceTrainingService().train();

            return "Success: 50 images captured and trained!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}