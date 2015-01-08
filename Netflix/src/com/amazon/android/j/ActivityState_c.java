package com.amazon.android.j;

import com.amazon.android.o.f;

public enum ActivityState_c implements f {
    CREATE_a, DESTROY_b, RESUME_c, PAUSE_d, START_e, STOP_f;

    public final String toString() {
        return (new StringBuilder()).append("ACTIVITY_").append(name())
                .toString();
    }
}
