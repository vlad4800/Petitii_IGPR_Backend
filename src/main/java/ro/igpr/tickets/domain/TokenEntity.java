/*
 * Copyright (c) 2015. Nephos6 Inc.
 */

package ro.igpr.tickets.domain;

import com.strategicgains.restexpress.plugin.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.annotations.ApiModel;
import org.restexpress.plugin.hyperexpress.Linkable;
import ro.igpr.tickets.persistence.types.TokenType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Vlad on 12-Dec-14.
 */
@Entity
@Table(name = "tokens")
@ApiModel(value = "Token Item", // Brief synopsis of the model.
        description = "Model that defines a course item that will be returned back to the user.", // Detailed description of the class.
        parent = BaseEntity.class
)
public final class TokenEntity extends BaseEntity implements Linkable {

    @ApiModelProperty(required = true)
    private String entityId;
    @ApiModelProperty(required = true)
    private TokenType type;
    @ApiModelProperty(required = true)
    private String value;
    @ApiModelProperty(required = true)
    private Date expiryDate;

    @Basic
    @Column(name = "`entity_id`", nullable = false, insertable = true, updatable = true)
    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "`type`", nullable = false, insertable = true, updatable = true, length = 8)
    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "`value`", nullable = false, insertable = true, updatable = true, length = 32)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "`expiry_date`", nullable = true, insertable = true, updatable = true)
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {

        if (!super.equals(o)) return false;

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenEntity that = (TokenEntity) o;

        if (entityId != null ? !entityId.equals(that.entityId) : that.entityId != null) return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 31 * result + (entityId != null ? entityId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "entityId='" + entityId + '\'' +
                ", type=" + type +
                ", value='" + value + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
