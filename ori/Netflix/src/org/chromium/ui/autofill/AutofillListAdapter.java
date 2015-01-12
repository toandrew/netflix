// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui.autofill;

import android.content.Context;
import android.text.TextUtils;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

// Referenced classes of package org.chromium.ui.autofill:
//			AutofillSuggestion

public class AutofillListAdapter extends ArrayAdapter
{

	public AutofillListAdapter(Context context, int resource) {
        super(context, resource);
        // TODO Auto-generated constructor stub
    }

    private Context mContext;

//	AutofillListAdapter(Context context, ArrayList arraylist)
//	{
//		//super(context, org.chromium.ui.R.layout.autofill_text, arraylist);
//		mContext = context;
//	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
	    return null;
//		View view1 = view;
//		if (view == null)
//			view1 = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(org.chromium.ui.R.layout.autofill_text, null);
//		((TextView)view1.findViewById(org.chromium.ui.R.id.autofill_label)).setText(((AutofillSuggestion)getItem(i)).mLabel);
//		TextView textview = (TextView)view1.findViewById(org.chromium.ui.R.id.autofill_sublabel);
//		String s = ((AutofillSuggestion)getItem(i)).mSublabel;
//		if (TextUtils.isEmpty(s))
//		{
//			textview.setVisibility(8);
//			return view1;
//		} else
//		{
//			textview.setText(s);
//			textview.setVisibility(0);
//			return view1;
//		}
	}
}
