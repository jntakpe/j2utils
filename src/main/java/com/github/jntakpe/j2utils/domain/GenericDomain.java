package com.github.jntakpe.j2utils.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Ajouter la clé technique et la colonne de versionning à une entité
 *
 * @author jntakpe
 */
@MappedSuperclass
public abstract class GenericDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Short version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }
}
