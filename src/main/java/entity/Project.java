package entity;

import javax.persistence.*;

@Table(name = "project")
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProject")
    private long    id;

    @Column(name = "nameProject")
    private String  nameProject;

    @Transient
    private long    idCustomer;

    @Transient
    private long    idCompany;

    @Column(name = "cost")
    private double  cost;
}
