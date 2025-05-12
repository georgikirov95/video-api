# 🎥 Video API

A Spring Boot REST API for uploading videos, extracting audio using **FFmpeg**, and downloading the extracted audio. Designed to be simple, modular, and easy to integrate into other systems.

---

## 📦 Features

- Upload video files
- Extract audio (MP3) from uploaded videos
- Download extracted audio
- Structured API responses with unified error handling

---

## 🚀 Quick Start

### 🔧 Prerequisites

- Java 17+
- Maven 3.6+
- FFmpeg installed and accessible from the system's PATH

```bash
ffmpeg -version
```

### 🛠️ Installation

```bash
git clone https://github.com/georgikirov95/video-api.git
cd video-api
mvn clean install
```

### ▶️ Run the app

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

---

## 📂 API Endpoints

### 1. Upload Video

```http
POST /videos
```

**Request:**
- Multipart form-data with a field named `video`.

**Response:**
```json
{
  "message": "Video uploaded successfully",
  "status": "success",
  "data": {
    "id": "uuid",
    ...
  }
}
```

---

### 2. Extract Audio from Video

```http
POST /videos/{videoId}/extract-audio
```

**Path Variable:**
- `videoId`: UUID of the uploaded video.

**Response:**
```json
{
  "message": "Audio extracted successfully",
  "status": "success",
  "data": {
    "id": "uuid",
    ...
  }
}
```

---

### 3. Download Extracted Audio

```http
GET /videos/{videoId}/download-audio
```

**Path Variable:**
- `videoId`: UUID of the processed video.

**Response:**
- Content-Type: `application/octet-stream`
- Triggers audio file download

---

## ⚠️ Error Handling

All errors return a structured `ApiResponse`:

```json
{
  "message": "Description of the error",
  "status": "error",
  "data": null
}
```

### Possible Exceptions:
- `StorageException` – File handling or persistence issues
- `FfmpegException` – Audio extraction failures
- `VideoNotFoundException` – Video not found by UUID
- `IllegalArgumentException` – Bad input

---

## 🧰 Tech Stack

- Java 17
- Spring Boot
- FFmpeg
- Maven

---

## 📁 Project Structure

```
video-api/
│
├── videos/
│   ├── VideoController.java
│   ├── VideoService.java
│   ├── ApiResponse.java
│   ├── ffmpeg/
│   │   └── FfmpegService.java
│
├── global/
│   └── GlobalExceptionHandler.java
│
├── storage/
│   └── StorageService.java
```