package com.amazon.android.licensing;

import com.amazon.android.b.CommandServiceKiwiException_a;
import com.amazon.android.b.UnhandledKiwiException_b;
import com.amazon.android.b.AuthTokenVerFailureKiwiException_d;
import com.amazon.android.b.ResultFailureKiwiException_e;
import com.amazon.android.b.CommandServiceBindFailureKiwiException_f;
import com.amazon.android.b.CommandServiceNotInstalledKiwiException_g;
import com.amazon.android.b.MalformedResponseKiwiException_h;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;
import com.amazon.android.h.SignedTokenVerFailureKiwiException_c;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.amazon.android.licensing:
//			i, m, j, g, 
//			h

public class LicenseFailurePromptContentMapper {

    private final Map mappings = new HashMap();

    public LicenseFailurePromptContentMapper() {
        register(
                com.amazon.android.b.CommandServiceNotInstalledKiwiException_g.class,
                com.amazon.android.licensing.AllPromptContents_i.a);
        register(
                com.amazon.android.b.CommandServiceBindFailureKiwiException_f.class,
                com.amazon.android.licensing.AllPromptContents_i.a);
        register(com.amazon.android.b.CommandServiceKiwiException_a.class,
                com.amazon.android.licensing.AllPromptContents_i.b);
        register(com.amazon.android.b.AuthTokenVerFailureKiwiException_d.class,
                com.amazon.android.licensing.AllPromptContents_i.c);
        register(com.amazon.android.b.ResultFailureKiwiException_e.class,
                new m());
        register(com.amazon.android.b.MalformedResponseKiwiException_h.class,
                com.amazon.android.licensing.AllPromptContents_i.e);
        register(com.amazon.android.b.UnhandledKiwiException_b.class,
                com.amazon.android.licensing.AllPromptContents_i.e);
        register(com.amazon.android.s.LicenseVerFailureKiwiException_a.class,
                new j());
        register(com.amazon.android.h.CertFailedToLoadKiwiException_a.class,
                com.amazon.android.licensing.AllPromptContents_i.e);
        register(
                com.amazon.android.h.SignedTokenParseFailureKiwiException_b.class,
                com.amazon.android.licensing.AllPromptContents_i.e);
        register(
                com.amazon.android.h.SignedTokenVerFailureKiwiException_c.class,
                com.amazon.android.licensing.AllPromptContents_i.e);
    }

    private void register(Class class1, PromptContent promptcontent) {
        register(
                class1,
                ((com.amazon.android.licensing.h) (new com.amazon.android.licensing.g(
                        this, promptcontent))));
    }

    private void register(Class class1, com.amazon.android.licensing.h h1) {
        com.amazon.android.d.a.b(mappings.containsKey(class1),
                (new StringBuilder()).append("mapping exists for type: ")
                        .append(class1).toString());
        mappings.put(class1, h1);
    }

    public PromptContent map(KiwiException kiwiexception) {
        com.amazon.android.licensing.h h1 = (com.amazon.android.licensing.h) mappings
                .get(kiwiexception.getClass());
        if (h1 == null)
            return null;
        else
            return h1.a(kiwiexception);
    }
}
