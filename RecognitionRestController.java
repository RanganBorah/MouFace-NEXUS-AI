package face_recognition_web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import face_recognition_web.model.RecognitionRequest;
import face_recognition_web.service.FaceRecognitionService;

@RestController
public class RecognitionRestController {

    @PostMapping("/recognizeFace")
    public String recognizeFace(@RequestBody RecognitionRequest request) {
        return new FaceRecognitionService().recognizeBase64Image(request.getImage());
    }
}