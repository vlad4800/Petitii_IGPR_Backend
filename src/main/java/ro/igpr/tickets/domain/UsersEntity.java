package ro.igpr.tickets.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.strategicgains.restexpress.plugin.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.annotations.ApiModel;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import ro.igpr.tickets.persistence.types.Status;
import ro.igpr.tickets.persistence.validator.cnp.Cnp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
@DynamicUpdate
@ApiModel(value = "User Item", // Brief synopsis of the model.
        description = "The list of users", // Detailed description of the class.
        parent = BaseEntity.class
)
@JsonIgnoreProperties(value = {"tickets"}, allowGetters = true)
public class UsersEntity extends BaseEntity {

    @ApiModelProperty(required = true)
    private String deviceId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(required = true)
    private String password;
    @ApiModelProperty(required = true)
    private String name;
    @ApiModelProperty(required = true)
    private Integer countyId;
    @ApiModelProperty(required = true)
    private String email;
    @ApiModelProperty(required = true)
    private String address;
    @ApiModelProperty(required = true)
    private String cnp;
    @ApiModelProperty(required = true)
    private String phone;
    @ApiModelProperty(required = false)
    private Status status;

    @ApiModelProperty(hidden = true)
    private List<TicketsEntity> tickets;

    @Basic
    @Column(name = "`password`", nullable = true, insertable = true, updatable = true, length = 61)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Basic
    @Column(name = "`device_id`", nullable = false, insertable = true, updatable = true, length = 100)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @NotNull
    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Basic
    @Column(name = "`county_id`", nullable = false, insertable = true, updatable = true)
    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    @NotNull
    @Email
    @Basic
    @Column(name = "`email`", nullable = false, unique = true, insertable = true, updatable = true, length = 200)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Basic
    @Column(name = "`address`", nullable = false, insertable = true, updatable = true, length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Cnp
    @Basic
    @Column(name = "`cnp`", nullable = false, unique = true, insertable = true, updatable = true, length = 13)
    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @NotNull
    @Basic
    @Column(name = "`phone`", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    @Basic
    @Column(name = "`status`", nullable = false, insertable = true, updatable = true, length = 50)
    public Status getStatus() {
        return status != null ? status : Status.inactive;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<TicketsEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketsEntity> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersEntity)) return false;
        if (!super.equals(o)) return false;

        UsersEntity that = (UsersEntity) o;

        if (getDeviceId() != null ? !getDeviceId().equals(that.getDeviceId()) : that.getDeviceId() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
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
        result = 31 * result + (getDeviceId() != null ? getDeviceId().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
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
        return "UsersEntity{" +
                "deviceId='" + deviceId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", countyId=" + countyId +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", cnp='" + cnp + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
