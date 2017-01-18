package ro.igpr.tickets.domain;

import com.wordnik.swagger.annotations.ApiModel;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import ro.igpr.tickets.persistence.validator.cnp.Cnp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vlad4800@gmail.com on 16-Jan-17.
 */
@Entity
@Table(name = "tickets")
@DynamicUpdate
@ApiModel(value = "Ticket Item", // Brief synopsis of the model.
        description = "The list of tickets", // Detailed description of the class.
        parent = BaseEntity.class
)
public class TicketsEntity extends BaseEntity {

    private Integer ticketCountyId;
    private String ip;
    private String name;
    private Integer countyId;
    private String email;
    private String address;
    private String cnp;
    private String phone;
    private String description;

    private List<TicketMessagesEntity> messages;
    private List<TicketAttachmentsEntity> attachments;

    @NotNull
    @Basic
    @Column(name = "`ticket_county_id`", nullable = false, insertable = true, updatable = true)
    public Integer getTicketCountyId() {
        return ticketCountyId;
    }

    public void setTicketCountyId(Integer ticketCountyId) {
        this.ticketCountyId = ticketCountyId;
    }

    @NotNull
    @Basic
    @Column(name = "`ip`", nullable = false, insertable = true, updatable = true, length = 50)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
    @Column(name = "`email`", nullable = false, insertable = true, updatable = true, length = 200)
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
    @Column(name = "`cnp`", nullable = false, insertable = true, updatable = true, length = 13)
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

    @NotNull
    @Basic
    @Column(name = "`description`", nullable = false, insertable = true, updatable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "ticketId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<TicketMessagesEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<TicketMessagesEntity> messages) {
        this.messages = messages;
    }

    @OneToMany(mappedBy = "ticketId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<TicketAttachmentsEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TicketAttachmentsEntity> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketsEntity)) return false;
        if (!super.equals(o)) return false;

        TicketsEntity that = (TicketsEntity) o;

        if (getTicketCountyId() != null ? !getTicketCountyId().equals(that.getTicketCountyId()) : that.getTicketCountyId() != null)
            return false;
        if (getIp() != null ? !getIp().equals(that.getIp()) : that.getIp() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getCountyId() != null ? !getCountyId().equals(that.getCountyId()) : that.getCountyId() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (getCnp() != null ? !getCnp().equals(that.getCnp()) : that.getCnp() != null) return false;
        if (getPhone() != null ? !getPhone().equals(that.getPhone()) : that.getPhone() != null) return false;
        return getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTicketCountyId() != null ? getTicketCountyId().hashCode() : 0);
        result = 31 * result + (getIp() != null ? getIp().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCountyId() != null ? getCountyId().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getCnp() != null ? getCnp().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketsEntity{" +
                "ticketCountyId=" + ticketCountyId +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", countyId=" + countyId +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", cnp='" + cnp + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
