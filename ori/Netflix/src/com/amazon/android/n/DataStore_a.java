package com.amazon.android.n;

import com.amazon.android.framework.resource.b;

// Referenced classes of package com.amazon.android.n:
//			d

public final class DataStore_a implements b {

    public ExpirableValueDataStore_d a;
    private com.amazon.android.framework.resource.ResourceManager_a b;

    public DataStore_a() {
        a = new ExpirableValueDataStore_d();
    }

    public final Object a(String s) {
        return a.b(s);
    }

    public final void a(String s, Object obj) {
        a.a(s, obj);
    }

    public final boolean b(String s) {
        return a.a(s);
    }

    public final void onResourcesPopulated() {
        b.b(a);
    }
}
