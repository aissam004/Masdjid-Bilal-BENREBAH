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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "personne")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personne.findAll", query = "SELECT p FROM Personne p"),
    @NamedQuery(name = "Personne.findByIdPersonne", query = "SELECT p FROM Personne p WHERE p.idPersonne = :idPersonne"),
    @NamedQuery(name = "Personne.findByNom", query = "SELECT p FROM Personne p WHERE p.nom = :nom"),
    @NamedQuery(name = "Personne.findByPrenom", query = "SELECT p FROM Personne p WHERE p.prenom = :prenom"),
    @NamedQuery(name = "Personne.findByDateNaissance", query = "SELECT p FROM Personne p WHERE p.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "Personne.findByTelephone", query = "SELECT p FROM Personne p WHERE p.telephone = :telephone"),
    @NamedQuery(name = "Personne.findByDateEngagement", query = "SELECT p FROM Personne p WHERE p.dateEngagement = :dateEngagement")})
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_personne")
    @TableGenerator(name="sqlite_personne", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="personne",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_personne")
    private Integer idPersonne;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "date_naissance")
    private String dateNaissance;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "date_engagement")
    private String dateEngagement;
    @JoinColumn(name = "id_type_personne", referencedColumnName = "id_type_personne")
    @ManyToOne
    private TypePersonne idTypePersonne;
    @OneToMany(mappedBy = "idPersonne")
    private Collection<Cotisation> cotisationCollection;
    @OneToMany(mappedBy = "idPersonne")
    private Collection<Operation> operationCollection;
    @OneToMany(mappedBy = "idPersonne")
    private Collection<Compte> compteCollection;

    public Personne() {
    }

    public Personne(Integer idPersonne) {
        this.idPersonne = idPersonne;
    }

    public Personne(String nom, String prenom, String dateNaissance, String telephone, String dateEngagement, TypePersonne idTypePersonne) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.dateEngagement = dateEngagement;
        this.idTypePersonne = idTypePersonne;
    }

    public Personne(Integer idPersonne,String nom, String prenom, String dateNaissance, String telephone, String dateEngagement, TypePersonne idTypePersonne) {
        this.idPersonne = idPersonne;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.dateEngagement = dateEngagement;
        this.idTypePersonne = idTypePersonne;
    }

   
    
    public Integer getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Integer idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDateEngagement() {
        return dateEngagement;
    }

    public void setDateEngagement(String dateEngagement) {
        this.dateEngagement = dateEngagement;
    }

    public TypePersonne getIdTypePersonne() {
        return idTypePersonne;
    }

    public void setIdTypePersonne(TypePersonne idTypePersonne) {
        this.idTypePersonne = idTypePersonne;
    }

    @XmlTransient
    public Collection<Cotisation> getCotisationCollection() {
        return cotisationCollection;
    }

    public void setCotisationCollection(Collection<Cotisation> cotisationCollection) {
        this.cotisationCollection = cotisationCollection;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
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
        hash += (idPersonne != null ? idPersonne.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.idPersonne == null && other.idPersonne != null) || (this.idPersonne != null && !this.idPersonne.equals(other.idPersonne))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Personne[ idPersonne=" + idPersonne + " ]";
    }
    
}
