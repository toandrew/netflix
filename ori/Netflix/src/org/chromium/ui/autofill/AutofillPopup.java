// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui.autofill;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import org.chromium.ui.ViewAndroidDelegate;

// Referenced classes of package org.chromium.ui.autofill:
//			AutofillSuggestion, AutofillListAdapter

public class AutofillPopup extends ListPopupWindow
	implements android.widget.AdapterView.OnItemClickListener
{
	public static interface AutofillPopupDelegate
	{

		public abstract void requestHide();

		public abstract void suggestionSelected(int i);
	}


	private static final int ITEM_ID_AUTOCOMPLETE_ENTRY = 0;
	private static final int ITEM_ID_DATA_LIST_ENTRY = -6;
	private static final int ITEM_ID_PASSWORD_ENTRY = -2;
	private static final int TEXT_PADDING_DP = 30;
	private float mAnchorHeight;
	private View mAnchorView;
	private float mAnchorWidth;
	private float mAnchorX;
	private float mAnchorY;
	private final AutofillPopupDelegate mAutofillCallback;
	private final Context mContext;
	private Paint mLabelViewPaint;
	private android.view.View.OnLayoutChangeListener mLayoutChangeListener;
	private Paint mSublabelViewPaint;
	private final ViewAndroidDelegate mViewAndroidDelegate;

	public AutofillPopup(Context context, ViewAndroidDelegate viewandroiddelegate, AutofillPopupDelegate autofillpopupdelegate)
	{
		super(context, null, 0, org.chromium.ui.R.style.AutofillPopupWindow);
		mContext = context;
		mViewAndroidDelegate = viewandroiddelegate;
		mAutofillCallback = autofillpopupdelegate;
		setOnItemClickListener(this);
		mAnchorView = mViewAndroidDelegate.acquireAnchorView();
		mAnchorView.setId(org.chromium.ui.R.id.autofill_popup_window);
		mAnchorView.setTag(this);
		mViewAndroidDelegate.setAnchorViewPosition(mAnchorView, mAnchorX, mAnchorY, mAnchorWidth, mAnchorHeight);
		mLayoutChangeListener = new android.view.View.OnLayoutChangeListener() {

			final AutofillPopup this$0;

			public void onLayoutChange(View view, int i, int j, int k, int l, int i1, int j1, 
					int k1, int l1)
			{
				if (view == mAnchorView)
					show();
			}
		};
		mAnchorView.addOnLayoutChangeListener(mLayoutChangeListener);
		setAnchorView(mAnchorView);
	}

	private float getDesiredWidth(ArrayList arraylist)
	{
		if (mLabelViewPaint == null || mSublabelViewPaint == null)
		{
			View view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(org.chromium.ui.R.layout.autofill_text, null);
			mLabelViewPaint = ((TextView)view.findViewById(org.chromium.ui.R.id.autofill_label)).getPaint();
			mSublabelViewPaint = ((TextView)view.findViewById(org.chromium.ui.R.id.autofill_sublabel)).getPaint();
		}
		float f = 0.0F;
		Rect rect = new Rect();
		for (int i = 0; i < arraylist.size(); i++)
		{
			rect.setEmpty();
			String s = ((AutofillSuggestion)arraylist.get(i)).mLabel;
			if (!TextUtils.isEmpty(s))
				mLabelViewPaint.getTextBounds(s, 0, s.length(), rect);
			float f1 = rect.width();
			rect.setEmpty();
			String s1 = ((AutofillSuggestion)arraylist.get(i)).mSublabel;
			if (!TextUtils.isEmpty(s1))
				mSublabelViewPaint.getTextBounds(s1, 0, s1.length(), rect);
			f = Math.max(f, Math.max(f1, rect.width()));
		}

		return 30F + f / mContext.getResources().getDisplayMetrics().density;
	}

	public void dismiss()
	{
		mAutofillCallback.requestHide();
	}

	public void hide()
	{
		super.dismiss();
		mAnchorView.removeOnLayoutChangeListener(mLayoutChangeListener);
		mAnchorView.setTag(null);
		mViewAndroidDelegate.releaseAnchorView(mAnchorView);
	}

	public void onItemClick(AdapterView adapterview, View view, int i, long l)
	{
		mAutofillCallback.suggestionSelected(i);
	}

	public void setAnchorRect(float f, float f1, float f2, float f3)
	{
		mAnchorWidth = f2;
		mAnchorHeight = f3;
		mAnchorX = f;
		mAnchorY = f1;
		if (mAnchorView != null)
			mViewAndroidDelegate.setAnchorViewPosition(mAnchorView, mAnchorX, mAnchorY, mAnchorWidth, mAnchorHeight);
	}

	public void show()
	{
		setInputMethodMode(1);
		super.show();
	}

	public void show(AutofillSuggestion aautofillsuggestion[])
	{
		ArrayList arraylist = new ArrayList();
		for (int i = 0; i < aautofillsuggestion.length; i++)
		{
			int j = aautofillsuggestion[i].mUniqueId;
			if (j > 0 || j == 0 || j == -2 || j == -6)
				arraylist.add(aautofillsuggestion[i]);
		}

		setAdapter(new AutofillListAdapter(mContext, arraylist));
		mAnchorWidth = Math.max(getDesiredWidth(arraylist), mAnchorWidth);
		mViewAndroidDelegate.setAnchorViewPosition(mAnchorView, mAnchorX, mAnchorY, mAnchorWidth, mAnchorHeight);
	}

}
