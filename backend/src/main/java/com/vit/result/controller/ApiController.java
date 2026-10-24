package com.vit.result.controller;

import com.vit.result.model.*;
import com.vit.result.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;
    private final ResultRepository resultRepo;

    // ── Students ──
    @GetMapping("/students")
    public List<Student> getAllStudents() { return studentRepo.findAll(); }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) { return studentRepo.save(student); }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updated) {
        return studentRepo.findById(id).map(s -> {
            s.setName(updated.getName());
            s.setPrn(updated.getPrn());
            s.setDepartment(updated.getDepartment());
            s.setSemester(updated.getSemester());
            s.setDivision(updated.getDivision());
            s.setEmail(updated.getEmail());
            s.setPhone(updated.getPhone());
            return ResponseEntity.ok(studentRepo.save(s));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Subjects ──
    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() { return subjectRepo.findAll(); }

    @PostMapping("/subjects")
    public Subject createSubject(@RequestBody Subject subject) { return subjectRepo.save(subject); }

    // ── Results ──
    @GetMapping("/results")
    public List<Result> getAllResults() { return resultRepo.findAll(); }

    @GetMapping("/results/student/{studentId}")
    public List<Result> getResultsByStudent(@PathVariable Long studentId) {
        return resultRepo.findByStudentId(studentId);
    }

    @PostMapping("/results")
    public Result createResult(@RequestBody Result result) { return resultRepo.save(result); }

    // ── Search by PRN ──
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchByPrn(@RequestParam String prn) {
        return studentRepo.findByPrn(prn).map(student -> {
            List<Result> results = resultRepo.findByStudentPrn(prn);
            double sgpa = calculateSGPA(results);
            double percentage = calculatePercentage(results);
            boolean passed = results.stream().allMatch(Result::isPassed);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("student", student);
            response.put("results", results);
            response.put("sgpa", sgpa);
            response.put("percentage", percentage);
            response.put("passed", passed);
            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── Dashboard Stats ──
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalStudents", studentRepo.count());
        stats.put("totalSubjects", subjectRepo.count());
        stats.put("totalResults", resultRepo.count());
        return stats;
    }

    private double calculateSGPA(List<Result> results) {
        if (results.isEmpty()) return 0;
        double total = 0; int credits = 0;
        for (Result r : results) {
            int c = r.getSubject().getCredits();
            total += r.getGradePoints() * c;
            credits += c;
        }
        return credits > 0 ? Math.round((total / credits) * 100.0) / 100.0 : 0;
    }

    private double calculatePercentage(List<Result> results) {
        if (results.isEmpty()) return 0;
        int totalMarks = results.stream().mapToInt(Result::getTotalMarks).sum();
        return Math.round((totalMarks * 100.0 / (results.size() * 100)) * 100.0) / 100.0;
    }
}
