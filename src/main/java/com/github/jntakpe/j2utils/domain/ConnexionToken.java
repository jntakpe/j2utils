package com.github.jntakpe.j2utils.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

/**
 * Token de connexion
 *
 * @author jntakpe
 */
@Entity
public class ConnexionToken extends GenericDomain {

    @Column(unique = true)
    private String series;

    private String token;

    @JsonIgnore
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate tokenTs;

    @Size(max = 39)
    private String ip;

    private String userAgent;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Utilisateur utilisateur;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getTokenTs() {
        return tokenTs;
    }

    public void setTokenTs(LocalDate tokenTs) {
        this.tokenTs = tokenTs;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnexionToken that = (ConnexionToken) o;

        if (series != null ? !series.equals(that.series) : that.series != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return series != null ? series.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ConnexionToken{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
