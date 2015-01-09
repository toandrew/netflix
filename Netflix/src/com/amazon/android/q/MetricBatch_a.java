package com.amazon.android.q;

import java.io.Serializable;
import java.util.*;

public final class MetricBatch_a implements Serializable, Iterable {

    private static final long serialVersionUID = 1L;
    final List a = new ArrayList();

    public MetricBatch_a() {
    }

    public final boolean a() {
        return a.isEmpty();
    }

    public final int b() {
        return a.size();
    }

    public final Iterator iterator() {
        return a.iterator();
    }

    public final String toString() {
        return (new StringBuilder()).append("MetricBatch: [").append(a)
                .append("]").toString();
    }
}
