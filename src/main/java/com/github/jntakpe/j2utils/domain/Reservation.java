package com.github.jntakpe.j2utils.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant une réservation
 *
 * @author jntakpe
 */
@Entity
public class Reservation extends GenericDomain {

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate jour;

    @Min(1)
    @Max(4)
    private Byte terrain;

    @ManyToOne
    private Utilisateur payeur;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
    private Set<Utilisateur> joueurs = new HashSet<>();

    @PrePersist
    public void populateJour() {
        this.setJour(new LocalDate());
    }

    public LocalDate getJour() {
        return jour;
    }

    public void setJour(LocalDate localDate) {
        this.jour = localDate;
    }

    public Byte getTerrain() {
        return terrain;
    }

    public void setTerrain(Byte terrain) {
        this.terrain = terrain;
    }

    public Set<Utilisateur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Set<Utilisateur> joueurs) {
        this.joueurs = joueurs;
    }

    public Utilisateur getPayeur() {
        return payeur;
    }

    public void setPayeur(Utilisateur payeur) {
        this.payeur = payeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (jour != null ? !jour.equals(that.jour) : that.jour != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return jour != null ? jour.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "terrain=" + terrain +
                ", localDate=" + jour +
                ", joueurs=" + joueurs +
                '}';
    }
}
