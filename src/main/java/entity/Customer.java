package entity;

import javax.persistence.*;

@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCustomer")
    private long    id;

    @Column(name = "firstName")
    private String  firstName;

    @Column(name = "lastName")
    private String  lastName;

    @Column(name = "phone")
    private String  phone;
}
