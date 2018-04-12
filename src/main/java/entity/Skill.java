package entity;

import javax.persistence.*;

@Table(name = "skill")
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSkill")
    private long    id;

    @Column(name = "nameSkill")
    private String  nameSkill;

    @Column(name = "levelSkill")
    private String  levelSkill;

    @Transient
    private long    idDeveloper;
}
