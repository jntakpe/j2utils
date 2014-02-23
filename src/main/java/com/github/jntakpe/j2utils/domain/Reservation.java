package com.github.jntakpe.j2utils.domain;

import com.github.jntakpe.j2utils.validation.ReservationDone;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

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
    @Column(unique = true)
    private LocalDate jour;

    @NotNull(groups = ReservationDone.class)
    private LocalTime creneau;

    @NotNull(groups = ReservationDone.class)
    @Min(value = 1, groups = ReservationDone.class)
    @Max(value = 4, groups = ReservationDone.class)
    private Byte terrain;

    @ManyToOne
    @NotNull(groups = ReservationDone.class)
    private Utilisateur payeur;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
    private Set<Utilisateur> joueurs = new HashSet<>();

    public LocalDate getJour() {
        return jour;
    }

    public void setJour(LocalDate localDate) {
        this.jour = localDate;
    }

    public LocalTime getCreneau() {
        return creneau;
    }

    public void setCreneau(LocalTime creneau) {
        this.creneau = creneau;
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
