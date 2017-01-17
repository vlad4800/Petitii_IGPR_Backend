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
@Table(name = "ticket_attachments")
@DynamicUpdate
@ApiModel(value = "Ticket Attachment Item",
        description = "The list of ticket attachments",
        parent = BaseEntity.class
)
public class TicketAttachmentsEntity extends BaseEntity {

    @JsonIgnore
    private Integer ticketId;

    private String path;
    private String contentType;

    @Basic
    @Column(name = "`ticket_id`", nullable = false, insertable = true, updatable = true)
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Basic
    @Column(name = "`path`", nullable = true, insertable = true, updatable = true, length = 50)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "`content_type`", nullable = false, insertable = true, updatable = true, length = 255)
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketAttachmentsEntity)) return false;
        if (!super.equals(o)) return false;

        TicketAttachmentsEntity that = (TicketAttachmentsEntity) o;

        if (getTicketId() != null ? !getTicketId().equals(that.getTicketId()) : that.getTicketId() != null)
            return false;
        if (getPath() != null ? !getPath().equals(that.getPath()) : that.getPath() != null) return false;
        return getContentType() != null ? getContentType().equals(that.getContentType()) : that.getContentType() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTicketId() != null ? getTicketId().hashCode() : 0);
        result = 31 * result + (getPath() != null ? getPath().hashCode() : 0);
        result = 31 * result + (getContentType() != null ? getContentType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketAttachmentsEntity{" +
                "ticketId=" + ticketId +
                ", path='" + path + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
