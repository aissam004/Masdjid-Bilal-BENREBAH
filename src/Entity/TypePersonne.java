/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "typePersonne")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypePersonne.findAll", query = "SELECT t FROM TypePersonne t"),
    @NamedQuery(name = "TypePersonne.findByIdTypePersonne", query = "SELECT t FROM TypePersonne t WHERE t.idTypePersonne = :idTypePersonne"),
    @NamedQuery(name = "TypePersonne.findByTypePersonne", query = "SELECT t FROM TypePersonne t WHERE t.typePersonne = :typePersonne")})
public class TypePersonne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_typePersonne")
    @TableGenerator(name="sqlite_typePersonne", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="type_personne",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_type_personne")
    private Integer idTypePersonne;
    @Column(name = "type_personne")
    private String typePersonne;
    @OneToMany(mappedBy = "idTypePersonne")
    private Collection<Personne> personneCollection;

    public TypePersonne() {
    }

    public TypePersonne(Integer idTypePersonne) {
        this.idTypePersonne = idTypePersonne;
         personneCollection= new ArrayList<Personne>();
    }

    public TypePersonne(String type_personne) {
        this.typePersonne = type_personne;
         personneCollection= new ArrayList<Personne>();
    }
    public TypePersonne(Integer idTypePersonne,String type_personne) {
          this.idTypePersonne = idTypePersonne;
        this.typePersonne = type_personne;
       personneCollection= new ArrayList<Personne>();
    }

    public Integer getIdTypePersonne() {
        return idTypePersonne;
    }

    public void setIdTypePersonne(Integer idTypePersonne) {
        this.idTypePersonne = idTypePersonne;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    @XmlTransient
    public Collection<Personne> getPersonneCollection() {
        return personneCollection;
    }

    public void setPersonneCollection(Collection<Personne> personneCollection) {
        this.personneCollection = personneCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypePersonne != null ? idTypePersonne.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypePersonne)) {
            return false;
        }
        TypePersonne other = (TypePersonne) object;
        if ((this.idTypePersonne == null && other.idTypePersonne != null) || (this.idTypePersonne != null && !this.idTypePersonne.equals(other.idTypePersonne))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getTypePersonne();
    }
    
}
