package face_recognition_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaceController {

    @GetMapping("/")
    public String home() {
        return "home";   
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "register";   
    }

    @GetMapping("/capture")
    public String capturePage() {
        return "capture";  
    }

    @GetMapping("/recognition")
    public String recognitionPage() {
        return "recognition";
    }
}