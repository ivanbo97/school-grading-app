package com.ivanboyukliev.schoolgradingapp.domain;

import com.ivanboyukliev.schoolgradingapp.security.roles.SchoolSystemUserRole;
import lombok.*;

import javax.persistence.*;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ENTITY_SCHOOL_CREDENTIALS_COLUMN_NAME_USER_CREDENTIALS_ID;
import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ENTITY_SCHOOL_CREDENTIALS_TABLE_NAME;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = ENTITY_SCHOOL_CREDENTIALS_TABLE_NAME)
public class SchoolSystemCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_SCHOOL_CREDENTIALS_COLUMN_NAME_USER_CREDENTIALS_ID)
    private Long id;

    private String username;

    private String password;

    @OneToOne(mappedBy = "credentials", fetch = FetchType.EAGER)
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private SchoolSystemUserRole userRole;

    @Override
    public String toString() {
        return "SchoolUserCredentials{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
