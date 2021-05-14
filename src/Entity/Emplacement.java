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
@Table(name = "emplacement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emplacement.findAll", query = "SELECT e FROM Emplacement e"),
    @NamedQuery(name = "Emplacement.findByIdEmplacement", query = "SELECT e FROM Emplacement e WHERE e.idEmplacement = :idEmplacement"),
    @NamedQuery(name = "Emplacement.findByEmplacement", query = "SELECT e FROM Emplacement e WHERE e.emplacement = :emplacement")})
public class Emplacement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_emplacement")
    @TableGenerator(name="sqlite_emplacement", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="emplacement",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_emplacement")
    private Integer idEmplacement;
    @Column(name = "emplacement")
    private String emplacement;
    @OneToMany(mappedBy = "idEmplacement")
    private Collection<Materiel> materielCollection;

    public Emplacement() {
    }

    public Emplacement(Integer idEmplacement) {
        this.idEmplacement = idEmplacement;
    }

    public Emplacement(Integer idEmplacement, String emplacement) {
        this.idEmplacement = idEmplacement;
        this.emplacement = emplacement;
    }

    public Emplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public Integer getIdEmplacement() {
        return idEmplacement;
    }

    public void setIdEmplacement(Integer idEmplacement) {
        this.idEmplacement = idEmplacement;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
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
        hash += (idEmplacement != null ? idEmplacement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emplacement)) {
            return false;
        }
        Emplacement other = (Emplacement) object;
        if ((this.idEmplacement == null && other.idEmplacement != null) || (this.idEmplacement != null && !this.idEmplacement.equals(other.idEmplacement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getEmplacement();
    }
    
}
