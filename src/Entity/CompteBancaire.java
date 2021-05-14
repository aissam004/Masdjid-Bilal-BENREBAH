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
@Table(name = "compte_bancaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT c FROM CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.findByIdCompteBancaire", query = "SELECT c FROM CompteBancaire c WHERE c.idCompteBancaire = :idCompteBancaire"),
    @NamedQuery(name = "CompteBancaire.findByNomBanque", query = "SELECT c FROM CompteBancaire c WHERE c.nomBanque = :nomBanque"),
    @NamedQuery(name = "CompteBancaire.findByNumeroCompte", query = "SELECT c FROM CompteBancaire c WHERE c.numeroCompte = :numeroCompte"),
    @NamedQuery(name = "CompteBancaire.findByDateCreationCompteBancaire", query = "SELECT c FROM CompteBancaire c WHERE c.dateCreationCompteBancaire = :dateCreationCompteBancaire")})
public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_compte_bancaire")
    @TableGenerator(name="sqlite_compte_bancaire", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="compte_bancaire",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_compte_bancaire")
    private Integer idCompteBancaire;
    @Column(name = "nom_banque")
    private String nomBanque;
    @Column(name = "numero_compte")
    private Integer numeroCompte;
    @Column(name = "date_creation_compte_bancaire")
    private String dateCreationCompteBancaire;
    @OneToMany(mappedBy = "idCompteBancaire")
    private Collection<Compte> compteCollection;

    public CompteBancaire() {
    }

    public CompteBancaire(Integer idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
    }

    public Integer getIdCompteBancaire() {
        return idCompteBancaire;
    }

    public void setIdCompteBancaire(Integer idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
    }

    public String getNomBanque() {
        return nomBanque;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    public Integer getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(Integer numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getDateCreationCompteBancaire() {
        return dateCreationCompteBancaire;
    }

    public void setDateCreationCompteBancaire(String dateCreationCompteBancaire) {
        this.dateCreationCompteBancaire = dateCreationCompteBancaire;
    }

    @XmlTransient
    public Collection<Compte> getCompteCollection() {
        return compteCollection;
    }

    public void setCompteCollection(Collection<Compte> compteCollection) {
        this.compteCollection = compteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompteBancaire != null ? idCompteBancaire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if ((this.idCompteBancaire == null && other.idCompteBancaire != null) || (this.idCompteBancaire != null && !this.idCompteBancaire.equals(other.idCompteBancaire))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CompteBancaire[ idCompteBancaire=" + idCompteBancaire + " ]";
    }
    
}
