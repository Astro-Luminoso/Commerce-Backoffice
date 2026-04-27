package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.config.PasswordEncoder;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateManagerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.ManagerRoleUpdate;
import dev.nbcsparta.assignment.commerce_backoffice.dto.ManagerStatusUpdate;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateMyProfileRequest;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "managers")
public class Manager extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    private String statusReason;

    protected Manager() {
        super();
    }

    /**
     * 기본 매니저 생성자 입니다. Manager 엔티티를 생성할 때 필요한 모든 필드들을 매개변수로 받아 초기화하는 생성자입니다.
     *
     * @param name 관리자 이름
     * @param email 관리자 이메일, 고유성이 보장되어야 합니다.
     * @param password 관리자 비밀번호, 보낭을 위해 해싱 한 형태로 저장되어야 합니다.
     * @param phoneNumber 관리자 전화번호
     * @param role 관리자 직책
     * @param status 관리자 계정 상태 기본적으인 값 외에 값을 정의할수 있도록 생성자에 포함시켰습니다.
     */
    public Manager(String name, String email, String password, String phoneNumber,Role role, AccountStatus status) {
        super(name, email, phoneNumber, status);
        this.password = password;
        this.role = role;

    }

//    /**
//     * Manager 엔티티 생성자
//     * Manager 엔티티를 생성할 때 필요한 필드들을 매개변수로 받아 초기화하는 생성자입니다.
//     * Manager 계정 생성시 기본적으로 승인 대기중인 상태여야 하므로 자동으로 AccountStatus.PENDING으로 설정될 것입니다.
//     * 따라서 편의상 AccountStatus가 필수가 아닌 생성자를 따로 만들어서 Manager 생성을 하기위해 만든 생성자입니다.
//     *
//     * @param name 관리자 이름
//     * @param email 관리자 이메일, 고유성이 보장되어야 합니다.
//     * @param password 관리자 비밀번호, 보낭을 위해 해싱 한 형태로 저장되어야 합니다.
//     * @param phoneNumber 관리자 전화번호
//     * @param role 관리자 직책
//     */
//    public Manager(String name, String email, String password, String phoneNumber, Role role) {
//        this(name, email, password, phoneNumber, role, AccountStatus.PENDING);
//    }

    public static Manager to(CreateManagerRequest req, String encodedPassword) {
        return new Manager(
                req.name(),
                req.email(),
                encodedPassword,
                req.phoneNumber(),
                req.role(),
                AccountStatus.PENDING
        );
    }

    public long getId(){
        return this.id;
    }

    public Role getRole() {
        return role;
    }

    public String getStatusReason() {
        return this.statusReason;
    }

    public boolean isPasswordMatch(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public void updateStatus(ManagerStatusUpdate reqBody) {
        super.updateStatus(reqBody.status());
        this.statusReason = (reqBody.status() == AccountStatus.ACTIVE) ? null : reqBody.reason();
    }

    public void updateRole(ManagerRoleUpdate reqBody) {
        this.role = reqBody.role();
    }
}
