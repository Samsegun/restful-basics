package com.samsegun.restful.rest;

import com.samsegun.restful.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    @PostConstruct
    private void loadData() {
        students = new ArrayList<>();

        students.add(new Student("will", "smith"));
        students.add(new Student("john", "cena"));
        students.add(new Student("wade", "wilson"));
    }

    @GetMapping("/")
    public String home() {
        return "welcome to students api";
    }

    //get all students
    @GetMapping("/students")
    public List<Student> students() {
        return students;
    }

    //get a student
    @GetMapping("/students/{studentId}")
    public Student student(@PathVariable int studentId) {

        if((studentId >= students.size()) || studentId < 0) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }

        return students.get(studentId);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
