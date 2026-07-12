package ua.university.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.university.sms.model.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByStatus(String status);
    List<Student> findByStudyYear(Integer studyYear);
    List<Student> findByFullNameContainingIgnoreCase(String fullName);
    List<Student> findByEmailContainingIgnoreCase(String email);
    List<Student> findTop10ByOrderByGpaDesc();
}
