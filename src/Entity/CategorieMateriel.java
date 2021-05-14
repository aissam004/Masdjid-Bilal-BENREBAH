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
@Table(name = "categorie_materiel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategorieMateriel.findAll", query = "SELECT c FROM CategorieMateriel c"),
    @NamedQuery(name = "CategorieMateriel.findByIdCategorieMateriel", query = "SELECT c FROM CategorieMateriel c WHERE c.idCategorieMateriel = :idCategorieMateriel"),
    @NamedQuery(name = "CategorieMateriel.findByCategorieMateriel", query = "SELECT c FROM CategorieMateriel c WHERE c.categorieMateriel = :categorieMateriel")})
public class CategorieMateriel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_categorie_materiel")
    @TableGenerator(name="sqlite_categorie_materiel", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="categorie_materiel",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_categorie_materiel")
    private Integer idCategorieMateriel;
    @Column(name = "categorie_materiel")
    private String categorieMateriel;
    @OneToMany(mappedBy = "idCategorieMateriel")
    private Collection<Materiel> materielCollection;

    public CategorieMateriel() {
    }

    public CategorieMateriel(Integer idCategorieMateriel) {
        this.idCategorieMateriel = idCategorieMateriel;
    }

    public CategorieMateriel(Integer idCategorieMateriel, String categorieMateriel) {
        this.idCategorieMateriel = idCategorieMateriel;
        this.categorieMateriel = categorieMateriel;
    }

    public CategorieMateriel(String categorieMateriel) {
        this.categorieMateriel = categorieMateriel;
    }
    
    public Integer getIdCategorieMateriel() {
        return idCategorieMateriel;
    }

    public void setIdCategorieMateriel(Integer idCategorieMateriel) {
        this.idCategorieMateriel = idCategorieMateriel;
    }

    public String getCategorieMateriel() {
        return categorieMateriel;
    }

    public void setCategorieMateriel(String categorieMateriel) {
        this.categorieMateriel = categorieMateriel;
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
        hash += (idCategorieMateriel != null ? idCategorieMateriel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategorieMateriel)) {
            return false;
        }
        CategorieMateriel other = (CategorieMateriel) object;
        if ((this.idCategorieMateriel == null && other.idCategorieMateriel != null) || (this.idCategorieMateriel != null && !this.idCategorieMateriel.equals(other.idCategorieMateriel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCategorieMateriel();
    }
    
}
