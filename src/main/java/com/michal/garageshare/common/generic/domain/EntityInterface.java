package com.michal.garageshare.common.generic.domain;

import java.io.Serializable;

public interface EntityInterface<ID extends Serializable> extends Serializable {
    ID getId();

    void setId(ID id);
}
