package com.vit.result.config;

import com.vit.result.model.*;
import com.vit.result.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;
    private final ResultRepository resultRepo;

    @Override
    public void run(String... args) {
        if (studentRepo.count() > 0) return;

        Student s1 = studentRepo.save(Student.builder().prn("VIT2024001").name("Aditya Wagh").department("Computer Engineering").semester(5).division("A").email("aditya@vit.edu").phone("9876543210").build());
        Student s2 = studentRepo.save(Student.builder().prn("VIT2024002").name("Priya Sharma").department("Computer Engineering").semester(5).division("A").email("priya@vit.edu").phone("9876543211").build());
        Student s3 = studentRepo.save(Student.builder().prn("VIT2024003").name("Rahul Deshmukh").department("Computer Engineering").semester(5).division("B").email("rahul@vit.edu").phone("9876543212").build());

        Subject sub1 = subjectRepo.save(Subject.builder().code("WT301").name("Web Technology").credits(4).semester(5).department("Computer Engineering").build());
        Subject sub2 = subjectRepo.save(Subject.builder().code("CN302").name("Computer Networks").credits(4).semester(5).department("Computer Engineering").build());
        Subject sub3 = subjectRepo.save(Subject.builder().code("DBMS303").name("Database Management").credits(3).semester(5).department("Computer Engineering").build());
        Subject sub4 = subjectRepo.save(Subject.builder().code("OS304").name("Operating Systems").credits(3).semester(5).department("Computer Engineering").build());
        Subject sub5 = subjectRepo.save(Subject.builder().code("SE305").name("Software Engineering").credits(3).semester(5).department("Computer Engineering").build());

        resultRepo.save(Result.builder().student(s1).subject(sub1).internalMarks(36).externalMarks(52).build());
        resultRepo.save(Result.builder().student(s1).subject(sub2).internalMarks(32).externalMarks(48).build());
        resultRepo.save(Result.builder().student(s1).subject(sub3).internalMarks(38).externalMarks(55).build());
        resultRepo.save(Result.builder().student(s1).subject(sub4).internalMarks(30).externalMarks(45).build());
        resultRepo.save(Result.builder().student(s1).subject(sub5).internalMarks(35).externalMarks(50).build());

        resultRepo.save(Result.builder().student(s2).subject(sub1).internalMarks(38).externalMarks(56).build());
        resultRepo.save(Result.builder().student(s2).subject(sub2).internalMarks(35).externalMarks(50).build());
        resultRepo.save(Result.builder().student(s2).subject(sub3).internalMarks(36).externalMarks(52).build());
        resultRepo.save(Result.builder().student(s2).subject(sub4).internalMarks(33).externalMarks(48).build());
        resultRepo.save(Result.builder().student(s2).subject(sub5).internalMarks(37).externalMarks(54).build());

        resultRepo.save(Result.builder().student(s3).subject(sub1).internalMarks(28).externalMarks(35).build());
        resultRepo.save(Result.builder().student(s3).subject(sub2).internalMarks(25).externalMarks(30).build());
        resultRepo.save(Result.builder().student(s3).subject(sub3).internalMarks(30).externalMarks(40).build());
        resultRepo.save(Result.builder().student(s3).subject(sub4).internalMarks(20).externalMarks(22).build());
        resultRepo.save(Result.builder().student(s3).subject(sub5).internalMarks(32).externalMarks(42).build());

        System.out.println("✅ Sample data seeded!");
    }
}
