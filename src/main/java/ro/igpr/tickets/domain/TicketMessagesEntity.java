package ro.igpr.tickets.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by vlad4800@gmail.com on 16-Jan-17.
 */
@Entity
@Table(name = "ticket_messages")
@DynamicUpdate
@ApiModel(value = "Ticket Message Item",
        description = "The list of ticket messages",
        parent = BaseEntity.class
)
public class TicketMessagesEntity extends BaseEntity {

    @JsonIgnore
    private Integer ticketId;

    private String name;
    private String subject;
    private String message;

    @Basic
    @Column(name = "`ticket_id`", nullable = false, insertable = true, updatable = true)
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Basic
    @Column(name = "`name`", nullable = true, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "`subject`", nullable = false, insertable = true, updatable = true, length = 255)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "`message`", nullable = false, insertable = true, updatable = true)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketMessagesEntity)) return false;
        if (!super.equals(o)) return false;

        TicketMessagesEntity that = (TicketMessagesEntity) o;

        if (getTicketId() != null ? !getTicketId().equals(that.getTicketId()) : that.getTicketId() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getSubject() != null ? !getSubject().equals(that.getSubject()) : that.getSubject() != null) return false;
        return getMessage() != null ? getMessage().equals(that.getMessage()) : that.getMessage() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTicketId() != null ? getTicketId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketMessagesEntity{" +
                "ticketId=" + ticketId +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
