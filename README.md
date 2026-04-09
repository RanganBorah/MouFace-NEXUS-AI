# MouFace NEXUS AI – Face Recognition Web Application

## Overview

MouFace NEXUS AI is a full-stack real-time face recognition web application built using Java Spring Boot, OpenCV, and MySQL. The system allows users to register their face, automatically capture training images, train a machine learning model, and perform live face recognition through a browser.

This project is designed as a portfolio project showcasing AI, computer vision, and full-stack development skills.

---

## Features

* Face registration with user details
* Automatic capture of 50 face samples
* Face detection using Haar Cascade
* Face recognition using LBPH algorithm
* Real-time recognition via webcam
* MySQL database integration
* Web-based user interface (HTML, CSS, JavaScript)
* REST APIs using Spring Boot
* Public sharing using ngrok

---

## Tech Stack

### Backend

* Java
* Spring Boot
* REST APIs

### Frontend

* HTML, CSS, JavaScript

### Computer Vision

* OpenCV (JavaCV / Bytedeco)

### Database

* MySQL

### Tools

* Git and GitHub
* ngrok (for public deployment)

---

## Project Structure

```
face_recognition_web/
│── controller/
│   ├── FaceController.java
│   ├── CaptureRestController.java
│   └── RecognitionRestController.java
│
│── model/
│   ├── CaptureRequest.java
│   ├── BatchCaptureRequest.java
│   └── RecognitionRequest.java
│
│── service/
│   ├── FaceCaptureService.java
│   ├── FaceTrainingService.java
│   ├── FaceRecognitionService.java
│   ├── DBHelperSave.java
│   ├── DBHelperFetch.java
│   └── ImageSaveService.java
│
│── templates/
│   ├── home.html
│   ├── register.html
│   ├── capture.html
│   └── recognition.html
│
│── dataset/
│── trainer.yml
│── application.properties
```

---

## How It Works

### 1. Registration

* User enters details such as name, age, and department
* User is redirected to the capture page

### 2. Face Capture

* The system captures 50 images using the webcam
* Images are converted to Base64 and sent to the backend
* Backend detects faces and stores processed images

### 3. Training

* The LBPH model is trained using stored images
* The trained model is saved as trainer.yml

### 4. Recognition

* Webcam feed is analyzed in real time
* Faces are detected and compared with the trained model
* User details are fetched from the database if a match is found

---

## API Endpoints

### Save Capture Batch

```
POST /saveCaptureBatch
```

* Saves user details and captured images
* Triggers model training

### Recognize Face

```
POST /recognizeFace
```

* Accepts a Base64 image
* Returns a JSON response with recognition result

Controller reference: fileciteturn0file0

---

## Recognition Logic

* Haar Cascade is used for face detection
* LBPHFaceRecognizer is used for prediction
* Confidence threshold is set to 55

Core implementation: fileciteturn0file1

---

## Setup Instructions

### 1. Clone the Repository

```
git clone https://github.com/your-username/your-repo.git
cd your-repo
```

### 2. Install Requirements

* Java JDK 17 or higher
* Maven
* MySQL

### 3. Configure Database

Create database:

```
CREATE DATABASE face_db;
```

Create table:

```
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    department VARCHAR(100),
    info TEXT
);
```

Update database credentials in code:

```
username: root
password: your_password
```

### 4. Run the Application

```
mvn spring-boot:run
```

Open in browser:

```
http://localhost:8080
```

---

## Public Deployment using ngrok

1. Install ngrok
2. Run the Spring Boot application
3. Start ngrok:

```
ngrok http 8080
```

4. Share the generated public URL

---

## Important Notes

* Ensure haarcascade_frontalface_alt.xml is present in the root directory
* Ensure dataset folder exists or will be created automatically
* trainer.yml is generated after training
* Camera permissions must be enabled in the browser

---

## UI Pages

* Home Page: fileciteturn0file3
* Capture Page: fileciteturn0file2
* Recognition Page: fileciteturn0file4

---

## Future Improvements

* Integration of deep learning models such as FaceNet or CNN
* Cloud deployment (AWS, Render, or similar platforms)
* Authentication system using JWT
* Multi-face recognition support
* Mobile application integration

---

## Author

Rangan Pratik Borah
B.Tech Electronics and Communication Engineering Student

Email: [ranganpratikborah2006@gmail.com](mailto:ranganpratikborah2006@gmail.com)
Phone: +91 8134055562

---

## License

This project is licensed under the MIT License.

---

## Support

If you find this project useful, consider starring the repository and sharing it.

---

Built as a portfolio project focusing on Artificial Intelligence, Computer Vision, and Full Stack Development.
