package entity;

import javax.persistence.*;

@Table(name = "company")
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompany")
    private long id;

    @Column(name = "nameCompany")
    private String nameCompany;

    @Column(name = "phone")
    private String phone;
}
