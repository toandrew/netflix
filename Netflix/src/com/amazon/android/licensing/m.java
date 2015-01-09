package com.amazon.android.licensing;

import com.amazon.android.b.ResultFailureKiwiException_e;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;
import java.util.Map;

// Referenced classes of package com.amazon.android.licensing:
//			h, a, i

final class m implements h {

    private Map a;

    m() {
        this((byte) 0);
    }

    private m(byte byte0) {
        a = new a(this);
    }

    public final PromptContent a(KiwiException kiwiexception) {
        ResultFailureKiwiException_e e1 = (ResultFailureKiwiException_e) kiwiexception;
        PromptContent promptcontent = (PromptContent) a.get(e1.getReason());
        if (promptcontent != null)
            return promptcontent;
        else
            return com.amazon.android.licensing.AllPromptContents_i.e;
    }
}
