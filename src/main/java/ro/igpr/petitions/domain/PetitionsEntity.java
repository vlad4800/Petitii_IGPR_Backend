package ro.igpr.petitions.domain;

import com.wordnik.swagger.annotations.ApiModel;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * Created by vlad4800@gmail.com on 16-Jan-17.
 */
@Entity
@Table(name = "petitions")
@DynamicUpdate
@ApiModel(value = "Petittions Item", // Brief synopsis of the model.
        description = "The list of petitions", // Detailed description of the class.
        parent = BaseEntity.class
)
public class PetitionsEntity extends BaseEntity {

    private Integer petitionCountyId;
    private String ip;
    private String name;
    private Integer countyId;
    private String email;
    private String address;
    private Integer cnp;
    private String phone;

    private List<PetitionMessagesEntity> messages;

    @Basic
    @Column(name = "`petition_county_id`", nullable = false, insertable = true, updatable = true)
    public Integer getPetitionCountyId() {
        return petitionCountyId;
    }

    public void setPetitionCountyId(Integer petitionCountyId) {
        this.petitionCountyId = petitionCountyId;
    }

    @Basic
    @Column(name = "`ip`", nullable = false, insertable = true, updatable = true, length = 50)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "`county_id`", nullable = false, insertable = true, updatable = true)
    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    @Basic
    @Column(name = "`email`", nullable = false, insertable = true, updatable = true, length = 200)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "`address`", nullable = false, insertable = true, updatable = true, length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "`cnp`", nullable = false, insertable = true, updatable = true)
    public Integer getCnp() {
        return cnp;
    }

    public void setCnp(Integer cnp) {
        this.cnp = cnp;
    }

    @Basic
    @Column(name = "`phone`", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @OneToMany(mappedBy = "petitionId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<PetitionMessagesEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<PetitionMessagesEntity> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetitionsEntity)) return false;
        if (!super.equals(o)) return false;

        PetitionsEntity that = (PetitionsEntity) o;

        if (getPetitionCountyId() != null ? !getPetitionCountyId().equals(that.getPetitionCountyId()) : that.getPetitionCountyId() != null)
            return false;
        if (getIp() != null ? !getIp().equals(that.getIp()) : that.getIp() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getCountyId() != null ? !getCountyId().equals(that.getCountyId()) : that.getCountyId() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (getCnp() != null ? !getCnp().equals(that.getCnp()) : that.getCnp() != null) return false;
        return getPhone() != null ? getPhone().equals(that.getPhone()) : that.getPhone() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPetitionCountyId() != null ? getPetitionCountyId().hashCode() : 0);
        result = 31 * result + (getIp() != null ? getIp().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCountyId() != null ? getCountyId().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getCnp() != null ? getCnp().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PetitionsEntity{" +
                "petitionCountyId=" + petitionCountyId +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", countyId=" + countyId +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", cnp=" + cnp +
                ", phone='" + phone + '\'' +
                '}';
    }
}
