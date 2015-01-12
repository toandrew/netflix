// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.os.Handler;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

// Referenced classes of package org.chromium.content.browser.input:
//			AdapterInputConnection, InputMethodManagerWrapper, InputDialogContainer

public class ImeAdapter
{
	public static class AdapterInputConnectionFactory
	{

		public AdapterInputConnection get(View view, ImeAdapter imeadapter, EditorInfo editorinfo)
		{
			return new AdapterInputConnection(view, imeadapter, editorinfo);
		}

		public AdapterInputConnectionFactory()
		{
		}
	}

	private class DelayedDismissInput
		implements Runnable
	{

		private final int mNativeImeAdapter;

		public void run()
		{
			attach(mNativeImeAdapter, ImeAdapter.sTextInputTypeNone, -1, -1);
			dismissInput(true);
		}

		DelayedDismissInput(int i)
		{
			mNativeImeAdapter = i;
		}
	}

	public static interface ImeAdapterDelegate
	{

		public abstract View getAttachedView();

		public abstract ResultReceiver getNewShowKeyboardReceiver();

		public abstract void onDismissInput();

		public abstract void onImeEvent(boolean flag);

		public abstract void onSetFieldValue();
	}


	private static final int COMPOSITION_KEY_CODE = 229;
	private static final int INPUT_DISMISS_DELAY = 150;
	static int sEventTypeChar;
	static int sEventTypeKeyUp;
	static int sEventTypeRawKeyDown;
	static int sModifierAlt;
	static int sModifierCapsLockOn;
	static int sModifierCtrl;
	static int sModifierNumLockOn;
	static int sModifierShift;
	static int sTextInputTypeContentEditable;
	static int sTextInputTypeEmail;
	static int sTextInputTypeNone;
	static int sTextInputTypeNumber;
	static int sTextInputTypePassword;
	static int sTextInputTypeSearch;
	static int sTextInputTypeTel;
	static int sTextInputTypeText;
	static int sTextInputTypeTextArea;
	static int sTextInputTypeUrl;
	static int sTextInputTypeWeek;
	private DelayedDismissInput mDismissInput;
	private final Handler mHandler = new Handler();
	private int mInitialSelectionEnd;
	private int mInitialSelectionStart;
	private AdapterInputConnection mInputConnection;
	private InputMethodManagerWrapper mInputMethodManagerWrapper;
	boolean mIsShowWithoutHideOutstanding;
	private int mNativeImeAdapterAndroid;
	private int mTextInputType;
	private final ImeAdapterDelegate mViewEmbedder;

	public ImeAdapter(InputMethodManagerWrapper inputmethodmanagerwrapper, ImeAdapterDelegate imeadapterdelegate)
	{
		mDismissInput = null;
		mIsShowWithoutHideOutstanding = false;
		mInputMethodManagerWrapper = inputmethodmanagerwrapper;
		mViewEmbedder = imeadapterdelegate;
	}

	private void cancelComposition()
	{
		if (mInputConnection != null)
			mInputConnection.restartInput();
	}

	private void dismissInput(boolean flag)
	{
		hideKeyboard(flag);
		mViewEmbedder.onDismissInput();
	}

	private static int getModifiers(int i)
	{
		int j = i & 1;
		int k = 0;
		if (j != 0)
			k = 0 | sModifierShift;
		if ((i & 2) != 0)
			k |= sModifierAlt;
		if ((i & 0x1000) != 0)
			k |= sModifierCtrl;
		if ((0x100000 & i) != 0)
			k |= sModifierCapsLockOn;
		if ((0x200000 & i) != 0)
			k |= sModifierNumLockOn;
		return k;
	}

	public static int getTextInputTypeNone()
	{
		return sTextInputTypeNone;
	}

	private boolean hasInputType()
	{
		return mTextInputType != sTextInputTypeNone;
	}

	private void hideKeyboard(boolean flag)
	{
		mIsShowWithoutHideOutstanding = false;
		View view = mViewEmbedder.getAttachedView();
		if (mInputMethodManagerWrapper.isActive(view))
		{
			InputMethodManagerWrapper inputmethodmanagerwrapper = mInputMethodManagerWrapper;
			android.os.IBinder ibinder = view.getWindowToken();
			ResultReceiver resultreceiver;
			if (flag)
				resultreceiver = mViewEmbedder.getNewShowKeyboardReceiver();
			else
				resultreceiver = null;
			inputmethodmanagerwrapper.hideSoftInputFromWindow(ibinder, 0, resultreceiver);
		}
	}

	private static void initializeTextInputTypes(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
			int i2, int j2, int k2, int l2, int i3, int j3, int k3, 
			int l3)
	{
		sTextInputTypeNone = i;
		sTextInputTypeText = j;
		sTextInputTypeTextArea = k;
		sTextInputTypePassword = l;
		sTextInputTypeSearch = i1;
		sTextInputTypeUrl = j1;
		sTextInputTypeEmail = k1;
		sTextInputTypeTel = l1;
		sTextInputTypeNumber = i2;
		sTextInputTypeWeek = k3;
		sTextInputTypeContentEditable = l3;
	}

	private static void initializeWebInputEvents(int i, int j, int k, int l, int i1, int j1, int k1, int l1)
	{
		sEventTypeRawKeyDown = i;
		sEventTypeKeyUp = j;
		sEventTypeChar = k;
		sModifierShift = l;
		sModifierAlt = i1;
		sModifierCtrl = j1;
		sModifierCapsLockOn = k1;
		sModifierNumLockOn = l1;
	}

	private boolean isFor(int i, int j)
	{
		return mNativeImeAdapterAndroid == i && mTextInputType == j;
	}

	static boolean isTextInputType(int i)
	{
		return i != sTextInputTypeNone && !InputDialogContainer.isDialogInputType(i);
	}

	private native void nativeAttachImeAdapter(int i);

	private native void nativeCommitText(int i, String s);

	private native void nativeCopy(int i);

	private native void nativeCut(int i);

	private native void nativeDeleteSurroundingText(int i, int j, int k);

	private native void nativeFinishComposingText(int i);

	private native void nativeImeBatchStateChanged(int i, boolean flag);

	private native void nativePaste(int i);

	private native void nativeResetImeAdapter(int i);

	private native void nativeSelectAll(int i);

	private native boolean nativeSendKeyEvent(int i, KeyEvent keyevent, int j, int k, long l, int i1, 
			boolean flag, int j1);

	private native boolean nativeSendSyntheticKeyEvent(int i, int j, long l, int k, int i1);

	private native void nativeSetComposingRegion(int i, int j, int k);

	private native void nativeSetComposingText(int i, String s, int j);

	private native void nativeSetEditableSelectionOffsets(int i, int j, int k);

	private native void nativeUnselect(int i);

	private int shouldSendKeyEventWithKeyCode(String s)
	{
		if (s.length() == 1)
		{
			if (s.equals("\n"))
				return 66;
			if (s.equals("\t"))
				return 61;
		}
		return 229;
	}

	private void showKeyboard()
	{
		mIsShowWithoutHideOutstanding = true;
		mInputMethodManagerWrapper.showSoftInput(mViewEmbedder.getAttachedView(), 0, mViewEmbedder.getNewShowKeyboardReceiver());
	}

	public void attach(int i)
	{
		if (mNativeImeAdapterAndroid != 0)
			nativeResetImeAdapter(mNativeImeAdapterAndroid);
		mNativeImeAdapterAndroid = i;
		if (i != 0)
			nativeAttachImeAdapter(mNativeImeAdapterAndroid);
	}

	public void attach(int i, int j, int k, int l)
	{
		if (mNativeImeAdapterAndroid != 0)
			nativeResetImeAdapter(mNativeImeAdapterAndroid);
		mNativeImeAdapterAndroid = i;
		mTextInputType = j;
		mInitialSelectionStart = k;
		mInitialSelectionEnd = l;
		if (i != 0)
			nativeAttachImeAdapter(mNativeImeAdapterAndroid);
	}

	public void attachAndShowIfNeeded(int i, int j, int k, int l, boolean flag)
	{
//		mHandler.removeCallbacks(mDismissInput);
//		if (mTextInputType != sTextInputTypeNone || flag) goto _L2; else goto _L1
//_L1:
//		return;
//_L2:
//		if (isFor(i, j))
//			continue; /* Loop/switch isn't completed */
//		if (j == sTextInputTypeNone)
//		{
//			mDismissInput = new DelayedDismissInput(i);
//			mHandler.postDelayed(mDismissInput, 150L);
//			return;
//		}
//		mTextInputType;
//		attach(i, j, k, l);
//		mInputMethodManagerWrapper.restartInput(mViewEmbedder.getAttachedView());
//		if (!flag) goto _L1; else goto _L3
//_L3:
//		showKeyboard();
//		return;
//		if (!hasInputType() || !flag) goto _L1; else goto _L4
//_L4:
//		showKeyboard();
//		return;
	}

	void batchStateChanged(boolean flag)
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return;
		} else
		{
			nativeImeBatchStateChanged(mNativeImeAdapterAndroid, flag);
			return;
		}
	}

	boolean checkCompositionQueueAndCallNative(String s, int i, boolean flag)
	{
		if (mNativeImeAdapterAndroid == 0)
			return false;
		boolean flag1 = s.isEmpty();
		mViewEmbedder.onImeEvent(flag1);
		int j = shouldSendKeyEventWithKeyCode(s);
		long l = System.currentTimeMillis();
		if (j != 229)
		{
			sendKeyEventWithKeyCode(j, 6);
		} else
		{
			nativeSendSyntheticKeyEvent(mNativeImeAdapterAndroid, sEventTypeRawKeyDown, l, j, 0);
			if (flag)
				nativeCommitText(mNativeImeAdapterAndroid, s);
			else
				nativeSetComposingText(mNativeImeAdapterAndroid, s, i);
			nativeSendSyntheticKeyEvent(mNativeImeAdapterAndroid, sEventTypeKeyUp, l, j, 0);
		}
		return true;
	}

	void commitText()
	{
		cancelComposition();
		if (mNativeImeAdapterAndroid != 0)
			nativeCommitText(mNativeImeAdapterAndroid, "");
	}

	public boolean copy()
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativeCopy(mNativeImeAdapterAndroid);
			return true;
		}
	}

	public boolean cut()
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativeCut(mNativeImeAdapterAndroid);
			return true;
		}
	}

	boolean deleteSurroundingText(int i, int j)
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativeDeleteSurroundingText(mNativeImeAdapterAndroid, i, j);
			return true;
		}
	}

	void detach()
	{
		mNativeImeAdapterAndroid = 0;
		mTextInputType = 0;
	}

	public boolean dispatchKeyEvent(KeyEvent keyevent)
	{
		return translateAndSendNativeEvents(keyevent);
	}

	void finishComposingText()
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return;
		} else
		{
			nativeFinishComposingText(mNativeImeAdapterAndroid);
			return;
		}
	}

	int getInitialSelectionEnd()
	{
		return mInitialSelectionEnd;
	}

	int getInitialSelectionStart()
	{
		return mInitialSelectionStart;
	}

	InputMethodManagerWrapper getInputMethodManagerWrapper()
	{
		return mInputMethodManagerWrapper;
	}

	int getTextInputType()
	{
		return mTextInputType;
	}

	public boolean hasTextInputType()
	{
		return isTextInputType(mTextInputType);
	}

	public boolean isActive()
	{
		return mInputConnection != null && mInputConnection.isActive();
	}

	public boolean isNativeImeAdapterAttached()
	{
		return mNativeImeAdapterAndroid != 0;
	}

	public boolean paste()
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativePaste(mNativeImeAdapterAndroid);
			return true;
		}
	}

	public boolean selectAll()
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativeSelectAll(mNativeImeAdapterAndroid);
			return true;
		}
	}

	void sendKeyEventWithKeyCode(int i, int j)
	{
		long l = System.currentTimeMillis();
		translateAndSendNativeEvents(new KeyEvent(l, l, 0, i, 0, 0, -1, 0, j));
		translateAndSendNativeEvents(new KeyEvent(System.currentTimeMillis(), l, 1, i, 0, 0, -1, 0, j));
	}

	boolean sendSyntheticKeyEvent(int i, long l, int j, int k)
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativeSendSyntheticKeyEvent(mNativeImeAdapterAndroid, i, l, j, k);
			return true;
		}
	}

	boolean setComposingRegion(int i, int j)
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativeSetComposingRegion(mNativeImeAdapterAndroid, i, j);
			return true;
		}
	}

	protected boolean setEditableSelectionOffsets(int i, int j)
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativeSetEditableSelectionOffsets(mNativeImeAdapterAndroid, i, j);
			return true;
		}
	}

	void setInputConnection(AdapterInputConnection adapterinputconnection)
	{
		mInputConnection = adapterinputconnection;
	}

	public void setInputMethodManagerWrapper(InputMethodManagerWrapper inputmethodmanagerwrapper)
	{
		mInputMethodManagerWrapper = inputmethodmanagerwrapper;
	}

	boolean translateAndSendNativeEvents(KeyEvent keyevent)
	{
		int i;
		if (mNativeImeAdapterAndroid != 0)
			if ((i = keyevent.getAction()) == 0 || i == 1)
			{
				mViewEmbedder.onImeEvent(false);
				return nativeSendKeyEvent(mNativeImeAdapterAndroid, keyevent, keyevent.getAction(), getModifiers(keyevent.getMetaState()), keyevent.getEventTime(), keyevent.getKeyCode(), keyevent.isSystem(), keyevent.getUnicodeChar());
			}
		return false;
	}

	public boolean unselect()
	{
		if (mNativeImeAdapterAndroid == 0)
		{
			return false;
		} else
		{
			nativeUnselect(mNativeImeAdapterAndroid);
			return true;
		}
	}

}
