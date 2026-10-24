package com.vit.result.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "results")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Result {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    private int internalMarks;
    private int externalMarks;

    @Transient public int getTotalMarks() { return internalMarks + externalMarks; }

    @Transient
    public String getGrade() {
        int t = getTotalMarks();
        if (t >= 90) return "O";
        if (t >= 80) return "A+";
        if (t >= 70) return "A";
        if (t >= 60) return "B+";
        if (t >= 50) return "B";
        if (t >= 40) return "C";
        return "F";
    }

    @Transient
    public double getGradePoints() {
        switch (getGrade()) {
            case "O": return 10; case "A+": return 9; case "A": return 8;
            case "B+": return 7; case "B": return 6; case "C": return 5;
            default: return 0;
        }
    }

    @Transient public boolean isPassed() { return getTotalMarks() >= 40 && externalMarks >= 24; }
}
