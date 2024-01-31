package crm.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "crmfiles")
public class CrmFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    private String filename;

    @Column
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CrmFile() {
        this.status = 1;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "crm_id", referencedColumnName = "id")
    @JsonBackReference
    // @JsonIgnore
    private Crm crm;

    public Crm getCrm() {
        return crm;
    }

    public void setCrm(Crm crm) {
        this.crm = crm;
    }

}
