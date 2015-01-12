// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.view.*;
import java.util.List;

public class SelectActionModeCallback
	implements android.view.ActionMode.Callback
{
	public static interface ActionHandler
	{

		public abstract boolean copy();

		public abstract boolean cut();

		public abstract String getSelectedText();

		public abstract boolean isSelectionEditable();

		public abstract void onDestroyActionMode();

		public abstract boolean paste();

		public abstract boolean selectAll();
	}


	private static final int ACTION_MODE_ATTRS[] = {
		0x101037e, 0x1010311, 0x1010312, 0x1010313
	};
	private static final int COPY_ATTR_INDEX = 2;
	private static final int CUT_ATTR_INDEX = 1;
	private static final int ID_COPY = 1;
	private static final int ID_CUT = 4;
	private static final int ID_PASTE = 5;
	private static final int ID_SEARCH = 3;
	private static final int ID_SELECTALL = 0;
	private static final int ID_SHARE = 2;
	private static final int PASTE_ATTR_INDEX = 3;
	private static final int SELECT_ALL_ATTR_INDEX = 0;
	private ActionHandler mActionHandler;
	private Context mContext;
	private boolean mEditable;
	private final boolean mIncognito;

	protected SelectActionModeCallback(Context context, ActionHandler actionhandler, boolean flag)
	{
		mContext = context;
		mActionHandler = actionhandler;
		mIncognito = flag;
	}

	private boolean canPaste()
	{
		return ((ClipboardManager)getContext().getSystemService("clipboard")).hasPrimaryClip();
	}

	private void createActionMenu(ActionMode actionmode, Menu menu)
	{
		TypedArray typedarray = getContext().obtainStyledAttributes(ACTION_MODE_ATTRS);
		menu.add(0, 0, 0, 0x104000d).setAlphabeticShortcut('a').setIcon(typedarray.getResourceId(0, 0)).setShowAsAction(6);
		if (mEditable)
			menu.add(0, 4, 0, 0x1040003).setIcon(typedarray.getResourceId(1, 0)).setAlphabeticShortcut('x').setShowAsAction(6);
		menu.add(0, 1, 0, 0x1040001).setIcon(typedarray.getResourceId(2, 0)).setAlphabeticShortcut('c').setShowAsAction(6);
		if (mEditable && canPaste())
			menu.add(0, 5, 0, 0x104000b).setIcon(typedarray.getResourceId(3, 0)).setAlphabeticShortcut('v').setShowAsAction(6);
		if (!mEditable)
		{
//			if (isShareHandlerAvailable())
//				menu.add(0, 2, 0, org.chromium.content.R.string.actionbar_share).setIcon(org.chromium.content.R.drawable.ic_menu_share_holo_light).setShowAsAction(6);
//			if (!mIncognito && isWebSearchAvailable())
//				menu.add(0, 3, 0, org.chromium.content.R.string.actionbar_web_search).setIcon(org.chromium.content.R.drawable.ic_menu_search_holo_light).setShowAsAction(6);
		}
		typedarray.recycle();
	}

	private boolean isShareHandlerAvailable()
	{
		Intent intent = new Intent("android.intent.action.SEND");
		intent.setType("text/plain");
		return getContext().getPackageManager().queryIntentActivities(intent, 0x10000).size() > 0;
	}

	private boolean isWebSearchAvailable()
	{
		Intent intent = new Intent("android.intent.action.WEB_SEARCH");
		intent.putExtra("new_search", true);
		return getContext().getPackageManager().queryIntentActivities(intent, 0x10000).size() > 0;
	}

	protected Context getContext()
	{
		return mContext;
	}

	public boolean onActionItemClicked(ActionMode actionmode, MenuItem menuitem)
	{
		String s = mActionHandler.getSelectedText();
		switch (menuitem.getItemId())
		{
		default:
			return false;

		case 0: // '\0'
			mActionHandler.selectAll();
			return true;

		case 4: // '\004'
			mActionHandler.cut();
			return true;

		case 1: // '\001'
			mActionHandler.copy();
			actionmode.finish();
			return true;

		case 5: // '\005'
			mActionHandler.paste();
			return true;

		case 2: // '\002'
			if (!TextUtils.isEmpty(s))
			{
				Intent intent1 = new Intent("android.intent.action.SEND");
				intent1.setType("text/plain");
				intent1.putExtra("android.intent.extra.TEXT", s);
				Intent intent;
				ActivityNotFoundException activitynotfoundexception;
//				try
//				{
//					Intent intent2 = Intent.createChooser(intent1, getContext().getString(org.chromium.content.R.string.actionbar_share));
//					intent2.setFlags(0x10000000);
//					getContext().startActivity(intent2);
//				}
//				catch (ActivityNotFoundException activitynotfoundexception1) { }
			}
			actionmode.finish();
			return true;

		case 3: // '\003'
			break;
		}
		if (!TextUtils.isEmpty(s))
		{
//			intent = new Intent("android.intent.action.WEB_SEARCH");
//			intent.putExtra("new_search", true);
//			intent.putExtra("query", s);
//			intent.putExtra("com.android.browser.application_id", getContext().getPackageName());
//			try
//			{
//				getContext().startActivity(intent);
//			}
			// Misplaced declaration of an exception variable
//			catch (ActivityNotFoundException activitynotfoundexception) { }
		}
		actionmode.finish();
		return true;
	}

	public boolean onCreateActionMode(ActionMode actionmode, Menu menu)
	{
		actionmode.setTitle(null);
		actionmode.setSubtitle(null);
		mEditable = mActionHandler.isSelectionEditable();
		createActionMenu(actionmode, menu);
		return true;
	}

	public void onDestroyActionMode(ActionMode actionmode)
	{
		mActionHandler.onDestroyActionMode();
	}

	public boolean onPrepareActionMode(ActionMode actionmode, Menu menu)
	{
		boolean flag = mActionHandler.isSelectionEditable();
		if (mEditable != flag)
		{
			mEditable = flag;
			menu.clear();
			createActionMenu(actionmode, menu);
			return true;
		} else
		{
			return false;
		}
	}

}
