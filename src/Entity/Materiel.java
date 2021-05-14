/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "materiel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materiel.findAll", query = "SELECT m FROM Materiel m"),
    @NamedQuery(name = "Materiel.findByIdMateriel", query = "SELECT m FROM Materiel m WHERE m.idMateriel = :idMateriel"),
    @NamedQuery(name = "Materiel.findByDesignation", query = "SELECT m FROM Materiel m WHERE m.designation = :designation"),
    @NamedQuery(name = "Materiel.findByReference", query = "SELECT m FROM Materiel m WHERE m.reference = :reference"),
    @NamedQuery(name = "Materiel.findByQuantite", query = "SELECT m FROM Materiel m WHERE m.quantite = :quantite"),
    @NamedQuery(name = "Materiel.findByDateAjout", query = "SELECT m FROM Materiel m WHERE m.dateAjout = :dateAjout")})
public class Materiel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_materiel")
    @TableGenerator(name="sqlite_materiel", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="materiel",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_materiel")
    private Integer idMateriel;
    @Column(name = "designation")
    private String designation;
    @Column(name = "reference")
    private String reference;
    @Column(name = "quantite")
    private Integer quantite;
    @Column(name = "date_ajout")
    private String dateAjout;
    @JoinColumn(name = "id_categorie_materiel", referencedColumnName = "id_categorie_materiel")
    @ManyToOne
    private CategorieMateriel idCategorieMateriel;
    @JoinColumn(name = "id_etat_materiel", referencedColumnName = "id_etat_materiel")
    @ManyToOne
    private EtatMateriel idEtatMateriel;
    @JoinColumn(name = "id_emplacement", referencedColumnName = "id_emplacement")
    @ManyToOne
    private Emplacement idEmplacement;

    public Materiel() {
    }

    public Materiel(Integer idMateriel) {
        this.idMateriel = idMateriel;
    }

    public Materiel(String designation, String reference, Integer quantite, String dateAjout, CategorieMateriel idCategorieMateriel, EtatMateriel idEtatMateriel, Emplacement idEmplacement) {
        this.designation = designation;
        this.reference = reference;
        this.quantite = quantite;
        this.dateAjout = dateAjout;
        this.idCategorieMateriel = idCategorieMateriel;
        this.idEtatMateriel = idEtatMateriel;
        this.idEmplacement = idEmplacement;
    }

   

    public Materiel(Integer idMateriel, String designation, String reference, Integer quantite, String dateAjout, CategorieMateriel idCategorieMateriel, EtatMateriel idEtatMateriel, Emplacement idEmplacement) {
        this.idMateriel = idMateriel;
        this.designation = designation;
        this.reference = reference;
        this.quantite = quantite;
        this.dateAjout = dateAjout;
        this.idCategorieMateriel = idCategorieMateriel;
        this.idEtatMateriel = idEtatMateriel;
        this.idEmplacement = idEmplacement;
    }
    public void setProperties(String designation, String reference, Integer quantite, String dateAjout, CategorieMateriel idCategorieMateriel, EtatMateriel idEtatMateriel, Emplacement idEmplacement){
        this.designation = designation;
        this.reference = reference;
        this.quantite = quantite;
        this.dateAjout = dateAjout;
        this.idCategorieMateriel = idCategorieMateriel;
        this.idEtatMateriel = idEtatMateriel;
        this.idEmplacement = idEmplacement;
    }

    public Integer getIdMateriel() {
        return idMateriel;
    }

    public void setIdMateriel(Integer idMateriel) {
        this.idMateriel = idMateriel;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDateAjout() {
        return LocalDate.parse(dateAjout);
    }

    public void setDateAjout(String dateAjout) {
        this.dateAjout = dateAjout;
    }

    public CategorieMateriel getIdCategorieMateriel() {
        return idCategorieMateriel;
    }

    public void setIdCategorieMateriel(CategorieMateriel idCategorieMateriel) {
        this.idCategorieMateriel = idCategorieMateriel;
    }

    public EtatMateriel getIdEtatMateriel() {
        return idEtatMateriel;
    }

    public void setIdEtatMateriel(EtatMateriel idEtatMateriel) {
        this.idEtatMateriel = idEtatMateriel;
    }

    public Emplacement getIdEmplacement() {
        return idEmplacement;
    }

    public void setIdEmplacement(Emplacement idEmplacement) {
        this.idEmplacement = idEmplacement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMateriel != null ? idMateriel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materiel)) {
            return false;
        }
        Materiel other = (Materiel) object;
        if ((this.idMateriel == null && other.idMateriel != null) || (this.idMateriel != null && !this.idMateriel.equals(other.idMateriel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Materiel[ idMateriel=" + idMateriel + " ]";
    }
    
}
