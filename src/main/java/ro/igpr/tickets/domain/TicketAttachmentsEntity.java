package ro.igpr.tickets.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.strategicgains.restexpress.plugin.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.annotations.ApiModel;
import org.hibernate.annotations.DynamicUpdate;
import ro.igpr.tickets.util.AttachmentUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
@JsonIgnoreProperties(value = {"url"}, allowGetters = true, allowSetters = false)
public class TicketAttachmentsEntity extends BaseEntity {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long ticketId;
    @ApiModelProperty(required = false)
    private String url;
    @ApiModelProperty(hidden = true)
    private String fileName;
    @ApiModelProperty(required = true)
    private String originalFileName;
    @ApiModelProperty(required = true)
    private String contentType;

    public TicketAttachmentsEntity() {
    }

    public TicketAttachmentsEntity(Long ticketId, String fileName, String originalFileName, String contentType) {
        this.ticketId = ticketId;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
    }

    @NotNull
    @Basic
    @Column(name = "`ticket_id`", nullable = false, insertable = true, updatable = true)
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Transient
    @JsonGetter("url")
    public String getUrl() {
        return url != null ? url : AttachmentUtil.getUrlFromFileName(this.getFileName());
    }

    public void setUrl(String url) {
        this.url = url != null ? url : AttachmentUtil.getUrlFromFileName(this.getFileName());
    }

    @NotNull
    @Basic
    @Column(name = "`file_name`", nullable = false, insertable = true, updatable = true)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @NotNull
    @Basic
    @Column(name = "`original_file_name`", nullable = true, insertable = true, updatable = true, length = 2083)
    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @NotNull
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
        if (getFileName() != null ? !getFileName().equals(that.getFileName()) : that.getFileName() != null)
            return false;
        if (getOriginalFileName() != null ? !getOriginalFileName().equals(that.getOriginalFileName()) : that.getOriginalFileName() != null)
            return false;
        return getContentType() != null ? getContentType().equals(that.getContentType()) : that.getContentType() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTicketId() != null ? getTicketId().hashCode() : 0);
        result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
        result = 31 * result + (getOriginalFileName() != null ? getOriginalFileName().hashCode() : 0);
        result = 31 * result + (getContentType() != null ? getContentType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketAttachmentsEntity{" +
                "ticketId=" + ticketId +
                ", fileName='" + fileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
