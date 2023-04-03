package com.carbazaar.carservice.entity.helper;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.Objects;

public class EntityListener {

    @PrePersist
    private void beforeInsert(AbstractJpaEntity abstractJpaEntity) {
        if(Objects.isNull(abstractJpaEntity.getCreatedAt())) {
            abstractJpaEntity.setCreatedAt(new Date());
        }

        if(Objects.isNull(abstractJpaEntity.getUpdatedAt())) {
            abstractJpaEntity.setUpdatedAt(new Date());
        }

    }

    @PreUpdate
    private void beforeUpdate(AbstractJpaEntity abstractJpaEntity) {
        if(Objects.isNull(abstractJpaEntity.getCreatedAt())) {
            abstractJpaEntity.setCreatedAt(new Date());
        }

        abstractJpaEntity.setUpdatedAt(new Date());
    }

}
