/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicRegionalniCentar;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ducati
 */
@Entity
@Table(name = "zahtev")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zahtev.findAll", query = "SELECT z FROM Zahtev z"),
    @NamedQuery(name = "Zahtev.findByIdzahtev", query = "SELECT z FROM Zahtev z WHERE z.idzahtev = :idzahtev"),
    @NamedQuery(name = "Zahtev.findByJmbg", query = "SELECT z FROM Zahtev z WHERE z.jmbg = :jmbg"),
    @NamedQuery(name = "Zahtev.findByIme", query = "SELECT z FROM Zahtev z WHERE z.ime = :ime"),
    @NamedQuery(name = "Zahtev.findByPrezime", query = "SELECT z FROM Zahtev z WHERE z.prezime = :prezime"),
    @NamedQuery(name = "Zahtev.findByImemajke", query = "SELECT z FROM Zahtev z WHERE z.imemajke = :imemajke"),
    @NamedQuery(name = "Zahtev.findByPrezimemajke", query = "SELECT z FROM Zahtev z WHERE z.prezimemajke = :prezimemajke"),
    @NamedQuery(name = "Zahtev.findByImeoca", query = "SELECT z FROM Zahtev z WHERE z.imeoca = :imeoca"),
    @NamedQuery(name = "Zahtev.findByPrezimeoca", query = "SELECT z FROM Zahtev z WHERE z.prezimeoca = :prezimeoca"),
    @NamedQuery(name = "Zahtev.findByPol", query = "SELECT z FROM Zahtev z WHERE z.pol = :pol"),
    @NamedQuery(name = "Zahtev.findByDatumrodjenja", query = "SELECT z FROM Zahtev z WHERE z.datumrodjenja = :datumrodjenja"),
    @NamedQuery(name = "Zahtev.findByNacionalnost", query = "SELECT z FROM Zahtev z WHERE z.nacionalnost = :nacionalnost"),
    @NamedQuery(name = "Zahtev.findByProfesija", query = "SELECT z FROM Zahtev z WHERE z.profesija = :profesija"),
    @NamedQuery(name = "Zahtev.findByBracnostanje", query = "SELECT z FROM Zahtev z WHERE z.bracnostanje = :bracnostanje"),
    @NamedQuery(name = "Zahtev.findByOpstinaprebivaliste", query = "SELECT z FROM Zahtev z WHERE z.opstinaprebivaliste = :opstinaprebivaliste"),
    @NamedQuery(name = "Zahtev.findByUlicaprebivalista", query = "SELECT z FROM Zahtev z WHERE z.ulicaprebivalista = :ulicaprebivalista"),
    @NamedQuery(name = "Zahtev.findByBrojulice", query = "SELECT z FROM Zahtev z WHERE z.brojulice = :brojulice"),
    @NamedQuery(name = "Zahtev.findByStanje", query = "SELECT z FROM Zahtev z WHERE z.stanje = :stanje")})
public class Zahtev implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idzahtev")
    private Integer idzahtev;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "JMBG")
    private String jmbg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Ime")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Prezime")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Imemajke")
    private String imemajke;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Prezimemajke")
    private String prezimemajke;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Imeoca")
    private String imeoca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Prezimeoca")
    private String prezimeoca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Pol")
    private String pol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Datumrodjenja")
    private String datumrodjenja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nacionalnost")
    private String nacionalnost;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Profesija")
    private String profesija;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Bracnostanje")
    private String bracnostanje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Opstinaprebivaliste")
    private String opstinaprebivaliste;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Ulicaprebivalista")
    private String ulicaprebivalista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Brojulice")
    private String brojulice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Stanje")
    private String stanje;

    public Zahtev() {
    }

    public Zahtev(Integer idzahtev) {
        this.idzahtev = idzahtev;
    }

    public Zahtev(String jmbg, String ime, String prezime, String imemajke, String prezimemajke, String imeoca, String prezimeoca, String pol, String datumrodjenja, String nacionalnost, String profesija, String bracnostanje, String opstinaprebivaliste, String ulicaprebivalista, String brojulice) {
        this.idzahtev = 0;
        this.jmbg = jmbg;
        this.ime = ime;
        this.prezime = prezime;
        this.imemajke = imemajke;
        this.prezimemajke = prezimemajke;
        this.imeoca = imeoca;
        this.prezimeoca = prezimeoca;
        this.pol = pol;
        this.datumrodjenja = datumrodjenja;
        this.nacionalnost = nacionalnost;
        this.profesija = profesija;
        this.bracnostanje = bracnostanje;
        this.opstinaprebivaliste = opstinaprebivaliste;
        this.ulicaprebivalista = ulicaprebivalista;
        this.brojulice = brojulice;
        this.stanje = "Kreiran";
    }

    public Integer getIdzahtev() {
        return idzahtev;
    }

    public void setIdzahtev(Integer idzahtev) {
        this.idzahtev = idzahtev;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getImemajke() {
        return imemajke;
    }

    public void setImemajke(String imemajke) {
        this.imemajke = imemajke;
    }

    public String getPrezimemajke() {
        return prezimemajke;
    }

    public void setPrezimemajke(String prezimemajke) {
        this.prezimemajke = prezimemajke;
    }

    public String getImeoca() {
        return imeoca;
    }

    public void setImeoca(String imeoca) {
        this.imeoca = imeoca;
    }

    public String getPrezimeoca() {
        return prezimeoca;
    }

    public void setPrezimeoca(String prezimeoca) {
        this.prezimeoca = prezimeoca;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getDatumrodjenja() {
        return datumrodjenja;
    }

    public void setDatumrodjenja(String datumrodjenja) {
        this.datumrodjenja = datumrodjenja;
    }

    public String getNacionalnost() {
        return nacionalnost;
    }

    public void setNacionalnost(String nacionalnost) {
        this.nacionalnost = nacionalnost;
    }

    public String getProfesija() {
        return profesija;
    }

    public void setProfesija(String profesija) {
        this.profesija = profesija;
    }

    public String getBracnostanje() {
        return bracnostanje;
    }

    public void setBracnostanje(String bracnostanje) {
        this.bracnostanje = bracnostanje;
    }

    public String getOpstinaprebivaliste() {
        return opstinaprebivaliste;
    }

    public void setOpstinaprebivaliste(String opstinaprebivaliste) {
        this.opstinaprebivaliste = opstinaprebivaliste;
    }

    public String getUlicaprebivalista() {
        return ulicaprebivalista;
    }

    public void setUlicaprebivalista(String ulicaprebivalista) {
        this.ulicaprebivalista = ulicaprebivalista;
    }

    public String getBrojulice() {
        return brojulice;
    }

    public void setBrojulice(String brojulice) {
        this.brojulice = brojulice;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idzahtev != null ? idzahtev.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zahtev)) {
            return false;
        }
        Zahtev other = (Zahtev) object;
        if ((this.idzahtev == null && other.idzahtev != null) || (this.idzahtev != null && !this.idzahtev.equals(other.idzahtev))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basicRegionalniCentar.Zahtev[ idzahtev=" + idzahtev + " ]";
    }
    
}
