// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.chromium.content.browser.ContentViewCore;

public class SelectPopupDialog
{
	private class SelectPopupArrayAdapter extends ArrayAdapter
	{

		static final int POPUP_ITEM_TYPE_DISABLED = 1;
		static final int POPUP_ITEM_TYPE_ENABLED = 2;
		static final int POPUP_ITEM_TYPE_GROUP = 0;
		private boolean mAreAllItemsEnabled;
		private int mItemEnabled[];

		public boolean areAllItemsEnabled()
		{
			return mAreAllItemsEnabled;
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			if (i < 0 || i >= getCount())
				return null;
			View view1 = super.getView(i, null, viewgroup);
			if (mItemEnabled[i] != 2)
				if (mItemEnabled[i] == 0)
					((CheckedTextView)view1).setCheckMarkDrawable(null);
				else
					view1.setEnabled(false);
			return view1;
		}

		public boolean isEnabled(int i)
		{
			while (i < 0 || i >= getCount() || mItemEnabled[i] != 2) 
				return false;
			return true;
		}

		public SelectPopupArrayAdapter(String as[], int ai[], boolean flag)
		{
		    super(mContentViewCore.getContext(), 0x1090013);
//			this$0 = SelectPopupDialog.this;
//			android.content.Context context = mContentViewCore.getContext();
//			int i;
//			int ai1[];
//			int j;
//			int k;
//			if (flag)
//				i = 0x1090013;
//			else
//				i = 0x1090012;
//			super(context, i, as);
//			mItemEnabled = ai;
//			mAreAllItemsEnabled = true;
//			ai1 = mItemEnabled;
//			j = ai1.length;
//			k = 0;
//			do
//			{
//label0:
//				{
//					if (k < j)
//					{
//						if (ai1[k] == 2)
//							break label0;
//						mAreAllItemsEnabled = false;
//					}
//					return;
//				}
//				k++;
//			} while (true);
		}
	}


	private static SelectPopupDialog sShownDialog;
	private ContentViewCore mContentViewCore;
	private AlertDialog mListBoxPopup;

	private SelectPopupDialog(ContentViewCore contentviewcore, String as[], int ai[], boolean flag, int ai1[])
	{
		mListBoxPopup = null;
		mContentViewCore = contentviewcore;
		final ListView listView = new ListView(mContentViewCore.getContext());
		android.app.AlertDialog.Builder builder = (new android.app.AlertDialog.Builder(mContentViewCore.getContext())).setView(listView).setCancelable(true).setInverseBackgroundForced(true);
		if (flag)
		{
			builder.setPositiveButton(0x104000a, new android.content.DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialoginterface, int j)
				{
					mContentViewCore.selectPopupMenuItems(getSelectedIndices(listView));
				}

			});
			builder.setNegativeButton(0x1040000, new android.content.DialogInterface.OnClickListener() {

	
				public void onClick(DialogInterface dialoginterface, int j)
				{
					mContentViewCore.selectPopupMenuItems(null);
				}

			});
		}
		mListBoxPopup = builder.create();
		listView.setAdapter(new SelectPopupArrayAdapter(as, ai, flag));
		listView.setFocusableInTouchMode(true);
		if (flag)
		{
			listView.setChoiceMode(2);
			for (int i = 0; i < ai1.length; i++)
				listView.setItemChecked(ai1[i], true);

		} else
		{
			listView.setChoiceMode(1);
			listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

				public void onItemClick(AdapterView adapterview, View view, int j, long l)
				{
					mContentViewCore.selectPopupMenuItems(getSelectedIndices(listView));
					mListBoxPopup.dismiss();
				}

			
			});
			if (ai1.length > 0)
			{
				listView.setSelection(ai1[0]);
				listView.setItemChecked(ai1[0], true);
			}
		}
		mListBoxPopup.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialoginterface)
			{
				mContentViewCore.selectPopupMenuItems(null);
			}
		});
		mListBoxPopup.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
			public void onDismiss(DialogInterface dialoginterface)
			{
				mListBoxPopup = null;
				SelectPopupDialog.sShownDialog = null;
			}

		});
	}

	public static SelectPopupDialog getCurrent()
	{
		return sShownDialog;
	}

	private int[] getSelectedIndices(ListView listview)
	{
		SparseBooleanArray sparsebooleanarray = listview.getCheckedItemPositions();
		int i = 0;
		for (int j = 0; j < sparsebooleanarray.size(); j++)
			if (sparsebooleanarray.valueAt(j))
				i++;

		int ai[] = new int[i];
		int k = 0;
		int l = 0;
		for (; k < sparsebooleanarray.size(); k++)
			if (sparsebooleanarray.valueAt(k))
			{
				int i1 = l + 1;
				ai[l] = sparsebooleanarray.keyAt(k);
				l = i1;
			}

		return ai;
	}

	public static void hide(ContentViewCore contentviewcore)
	{
		if (sShownDialog != null && (contentviewcore == null || sShownDialog.mContentViewCore == contentviewcore))
		{
			if (contentviewcore != null)
				contentviewcore.selectPopupMenuItems(null);
			sShownDialog.mListBoxPopup.dismiss();
		}
	}

	public static void show(ContentViewCore contentviewcore, String as[], int ai[], boolean flag, int ai1[])
	{
		hide(null);
		sShownDialog = new SelectPopupDialog(contentviewcore, as, ai, flag, ai1);
		sShownDialog.mListBoxPopup.show();
	}





/*
	static AlertDialog access$202(SelectPopupDialog selectpopupdialog, AlertDialog alertdialog)
	{
		selectpopupdialog.mListBoxPopup = alertdialog;
		return alertdialog;
	}

*/


/*
	static SelectPopupDialog access$302(SelectPopupDialog selectpopupdialog)
	{
		sShownDialog = selectpopupdialog;
		return selectpopupdialog;
	}

*/
}
