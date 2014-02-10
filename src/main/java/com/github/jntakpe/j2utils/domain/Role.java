package com.github.jntakpe.j2utils.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Rôle d'un utilisateur
 *
 * @author jntakpe
 */
@Entity
public class Role extends GenericDomain {

    @NotNull
    @Size(max = 50)
    @Column(unique = true)
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (nom != null ? !nom.equals(role.nom) : role.nom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nom != null ? nom.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Role{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
