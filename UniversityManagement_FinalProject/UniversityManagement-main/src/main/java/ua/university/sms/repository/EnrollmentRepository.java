package ua.university.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.university.sms.model.entity.Enrollment;
import ua.university.sms.model.entity.Student;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);

    @Query("SELECT DISTINCT e.student FROM Enrollment e WHERE e.paid = false")
    List<Student> findStudentsWithUnpaidCourses();

    @Query("SELECT AVG(e.grade) FROM Enrollment e WHERE e.course.id = :courseId AND e.grade IS NOT NULL")
    Double getAverageGradeByCourse(@Param("courseId") Long courseId);
}
