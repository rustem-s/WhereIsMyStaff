package kz.pio.whereismystuff.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * User entity
 * @version 20140614
 * @author Rustem S
 */
@Entity
@Table(name = "users")
public class User {
    private Long id;
    private String username;
    private String psw;
    private String email;
    private byte[] picture;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USERNAME")
    @Size(min=1)
    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    @Column(name = "PSW")
    @Size(min=1)
    public String getPsw() { return psw; }

    public void setPsw(String psw) { this.psw = psw; }

    @Column(name = "EMAIL")
    @Size(min=1)
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Basic(fetch=FetchType.LAZY)
    @Lob @Column(name = "PICTURE")
    public byte[] getPicture() { return picture; }

    public void setPicture(byte[] picture) { this.picture = picture; }
}
