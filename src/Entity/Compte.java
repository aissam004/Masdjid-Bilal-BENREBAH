/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "compte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compte.findAll", query = "SELECT c FROM Compte c"),
    @NamedQuery(name = "Compte.findByIdCompte", query = "SELECT c FROM Compte c WHERE c.idCompte = :idCompte"),
    @NamedQuery(name = "Compte.findByNomCompte", query = "SELECT c FROM Compte c WHERE c.nomCompte = :nomCompte"),
    @NamedQuery(name = "Compte.findByDateCreation", query = "SELECT c FROM Compte c WHERE c.dateCreation = :dateCreation")})
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_compte")
    @TableGenerator(name="sqlite_compte", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="compte",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_compte")
    private Integer idCompte;
    @Column(name = "nom_compte")
    private String nomCompte;
    @Column(name = "date_creation")
    private String dateCreation;
    @JoinColumn(name = "id_compte_bancaire", referencedColumnName = "id_compte_bancaire")
    @ManyToOne
    private CompteBancaire idCompteBancaire;
    @JoinColumn(name = "id_personne", referencedColumnName = "id_personne")
    @ManyToOne
    private Personne idPersonne;

    public Compte() {
    }

    public Compte(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public Integer getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public String getNomCompte() {
        return nomCompte;
    }

    public void setNomCompte(String nomCompte) {
        this.nomCompte = nomCompte;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public CompteBancaire getIdCompteBancaire() {
        return idCompteBancaire;
    }

    public void setIdCompteBancaire(CompteBancaire idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
    }

    public Personne getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Personne idPersonne) {
        this.idPersonne = idPersonne;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompte != null ? idCompte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compte)) {
            return false;
        }
        Compte other = (Compte) object;
        if ((this.idCompte == null && other.idCompte != null) || (this.idCompte != null && !this.idCompte.equals(other.idCompte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Compte[ idCompte=" + idCompte + " ]";
    }
    
}
