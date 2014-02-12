package com.github.jntakpe.j2utils.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Utilisateur de l'application
 *
 * @author jntakpe
 */

@Entity
public class Utilisateur extends GenericDomain {

    @NotNull
    @Size(max = 50)
    @Column(unique = true)
    private String login;

    @JsonIgnore
    @Size(max = 100)
    private String password;

    @Email
    @Size(max = 100)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur")
    private Set<ConnexionToken> connexionTokens;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "utilisateur_role",
            joinColumns = {@JoinColumn(name = "utilisateur")}, inverseJoinColumns = {@JoinColumn(name = "role")})
    private Set<Role> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ConnexionToken> getConnexionTokens() {
        return connexionTokens;
    }

    public void setConnexionTokens(Set<ConnexionToken> connexionTokens) {
        this.connexionTokens = connexionTokens;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur that = (Utilisateur) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "login='" + login + '\'' +
                '}';
    }
}
