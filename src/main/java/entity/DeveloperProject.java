package entity;

import javax.persistence.*;

@Table(name = "developer_project")
@Entity
public class DeveloperProject {
    @Transient
    private long idDeveloper;

    @Transient
    private long idProject;
}
