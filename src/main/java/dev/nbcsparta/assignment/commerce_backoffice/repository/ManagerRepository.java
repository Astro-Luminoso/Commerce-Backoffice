package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ManagerStatistics;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    @Query("SELECT m FROM Manager m " +
            "WHERE (:name IS NULL OR m.name = :name) AND " +
            "(:email IS NULL OR m.email = :email) AND " +
            "(:role IS NULL OR m.role = :role) AND " +
            "(:status IS NULL OR m.status = :status)")
    Page<Manager> findAll(
            @Param("name") String name,
            @Param("email")String email,
            @Param("role")Role role,
            @Param("status")AccountStatus status,
            Pageable pageable);

    @Query("SELECT m FROM Manager m " +
            "WHERE (:name IS NULL OR m.name = :name) AND " +
            "(:email IS NULL OR m.email = :email) AND " +
            "(:role IS NULL OR m.role = :role) AND " +
            "(:status IS NULL OR m.status = :status)")
    Page<Manager> findAllByIsDeletedFalse(
            @Param("name") String name,
            @Param("email")String email,
            @Param("role")Role role,
            @Param("status")AccountStatus status,
            Pageable pageable
    );

    Optional<Manager> findByIdAndIsDeletedFalse(long id);

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ManagerStatistics(" +
            "COUNT(m)," +
            "SUM(CASE WHEN m.status = dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus.ACTIVE THEN 1 ELSE 0 END))" +
            "FROM Manager m")
    ManagerStatistics getStatistics();
}
