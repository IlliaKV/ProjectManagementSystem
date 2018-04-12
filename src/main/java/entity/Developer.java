package entity;

import javax.persistence.*;

@Table(name = "developer")
@Entity
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDeveloper")
    private long    id;

    @Column(name = "firstName")
    private String  firstName;

    @Column(name = "lastName")
    private String  lastName;

    @Column(name = "sex")
    private int     sex;

    @Column(name = "age")
    private int     age;

    @Column(name = "salary")
    private double  salary;

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
