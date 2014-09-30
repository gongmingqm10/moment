package org.gongming.moment.api;

import java.io.Serializable;

public abstract class AbsApi<T> implements Serializable {
    public abstract ApiCallResponse<T> call();
}
