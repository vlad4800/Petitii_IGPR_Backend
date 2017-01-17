package ro.igpr.petitions.domain;

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
@Table(name = "petition_attachments")
@DynamicUpdate
@ApiModel(value = "Petition Attachment Item",
        description = "The list of petition attachments",
        parent = BaseEntity.class
)
public class PetitionAttachmentsEntity extends BaseEntity {

    @JsonIgnore
    private Integer petitionId;

    private String path;
    private String contentType;

    @Basic
    @Column(name = "`petition_id`", nullable = false, insertable = true, updatable = true)
    public Integer getPetitionId() {
        return petitionId;
    }

    public void setPetitionId(Integer petitionId) {
        this.petitionId = petitionId;
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
        if (!(o instanceof PetitionAttachmentsEntity)) return false;
        if (!super.equals(o)) return false;

        PetitionAttachmentsEntity that = (PetitionAttachmentsEntity) o;

        if (getPetitionId() != null ? !getPetitionId().equals(that.getPetitionId()) : that.getPetitionId() != null)
            return false;
        if (getPath() != null ? !getPath().equals(that.getPath()) : that.getPath() != null) return false;
        return getContentType() != null ? getContentType().equals(that.getContentType()) : that.getContentType() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPetitionId() != null ? getPetitionId().hashCode() : 0);
        result = 31 * result + (getPath() != null ? getPath().hashCode() : 0);
        result = 31 * result + (getContentType() != null ? getContentType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PetitionAttachmentsEntity{" +
                "petitionId=" + petitionId +
                ", path='" + path + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
