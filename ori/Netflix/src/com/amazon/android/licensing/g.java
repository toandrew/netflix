package com.amazon.android.licensing;

import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;

// Referenced classes of package com.amazon.android.licensing:
//			h, LicenseFailurePromptContentMapper

final class g implements h {

    private PromptContent a;
    private LicenseFailurePromptContentMapper b;

    g(LicenseFailurePromptContentMapper licensefailurepromptcontentmapper,
            PromptContent promptcontent) {
        b = licensefailurepromptcontentmapper;
        a = promptcontent;
    }

    public final PromptContent a(KiwiException kiwiexception) {
        return a;
    }
}
