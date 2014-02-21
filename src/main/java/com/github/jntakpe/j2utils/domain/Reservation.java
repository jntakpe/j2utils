package com.github.jntakpe.j2utils.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private LocalDate localDate;

    @NotNull
    @Min(1)
    @Max(4)
    private Byte terrain;

    @ManyToOne
    private Utilisateur payeur;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
    private Set<Utilisateur> joueurs = new HashSet<>();

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
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

        if (localDate != null ? !localDate.equals(that.localDate) : that.localDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return localDate != null ? localDate.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "terrain=" + terrain +
                ", localDate=" + localDate +
                ", joueurs=" + joueurs +
                '}';
    }
}
