package com.amazon.android.j;

import com.amazon.android.o.f;

public enum ApplicationState_d implements f {
    CREATE, DESTROY, START, STOP;

    public final String toString() {
        return (new StringBuilder()).append("APPLICATION_").append(name())
                .toString();
    }
}
