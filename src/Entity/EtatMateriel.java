/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "etat_materiel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtatMateriel.findAll", query = "SELECT e FROM EtatMateriel e"),
    @NamedQuery(name = "EtatMateriel.findByIdEtatMateriel", query = "SELECT e FROM EtatMateriel e WHERE e.idEtatMateriel = :idEtatMateriel"),
    @NamedQuery(name = "EtatMateriel.findByEtatMateriel", query = "SELECT e FROM EtatMateriel e WHERE e.etatMateriel = :etatMateriel")})
public class EtatMateriel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_etat_materiel")
    @TableGenerator(name="sqlite_etat_materiel", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="etat_materiel",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_etat_materiel")
    private Integer idEtatMateriel;
    @Column(name = "etat_materiel")
    private String etatMateriel;
    @OneToMany(mappedBy = "idEtatMateriel")
    private Collection<Materiel> materielCollection;

    public EtatMateriel() {
    }

    public EtatMateriel(Integer idEtatMateriel) {
        this.idEtatMateriel = idEtatMateriel;
    }

    public EtatMateriel(Integer idEtatMateriel, String etatMateriel) {
        this.idEtatMateriel = idEtatMateriel;
        this.etatMateriel = etatMateriel;
    }

    public EtatMateriel(String etatMateriel) {
        this.etatMateriel = etatMateriel;
    }

    public Integer getIdEtatMateriel() {
        return idEtatMateriel;
    }

    public void setIdEtatMateriel(Integer idEtatMateriel) {
        this.idEtatMateriel = idEtatMateriel;
    }

    public String getEtatMateriel() {
        return etatMateriel;
    }

    public void setEtatMateriel(String etatMateriel) {
        this.etatMateriel = etatMateriel;
    }

    @XmlTransient
    public Collection<Materiel> getMaterielCollection() {
        return materielCollection;
    }

    public void setMaterielCollection(Collection<Materiel> materielCollection) {
        this.materielCollection = materielCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEtatMateriel != null ? idEtatMateriel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtatMateriel)) {
            return false;
        }
        EtatMateriel other = (EtatMateriel) object;
        if ((this.idEtatMateriel == null && other.idEtatMateriel != null) || (this.idEtatMateriel != null && !this.idEtatMateriel.equals(other.idEtatMateriel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getEtatMateriel();
    }
    
}
