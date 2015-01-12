// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.text.Editable;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.*;

// Referenced classes of package org.chromium.content.browser.input:
//			ImeAdapter, InputMethodManagerWrapper

public class AdapterInputConnection extends BaseInputConnection
{

	private static final boolean DEBUG = false;
	public static final int INVALID_COMPOSITION = -1;
	public static final int INVALID_SELECTION = -1;
	private static final String TAG = "org.chromium.content.browser.input.AdapterInputConnection";
	private boolean mIgnoreTextInputStateUpdates;
	private final ImeAdapter mImeAdapter;
	private final View mInternalView;
	private int mLastUpdateCompositionEnd;
	private int mLastUpdateCompositionStart;
	private int mLastUpdateSelectionEnd;
	private int mLastUpdateSelectionStart;
	private int mNumNestedBatchEdits;
	private boolean mSingleLine;

	AdapterInputConnection(View view, ImeAdapter imeadapter, EditorInfo editorinfo)
	{
		super(view, true);
		mNumNestedBatchEdits = 0;
		mIgnoreTextInputStateUpdates = false;
		mLastUpdateSelectionStart = -1;
		mLastUpdateSelectionEnd = -1;
		mLastUpdateCompositionStart = -1;
		mLastUpdateCompositionEnd = -1;
		mInternalView = view;
		mImeAdapter = imeadapter;
		mImeAdapter.setInputConnection(this);
		mSingleLine = true;
		editorinfo.imeOptions = 0x12000000;
		editorinfo.inputType = 161;
		if (imeadapter.getTextInputType() != ImeAdapter.sTextInputTypeText) goto _L2; else goto _L1
_L1:
		editorinfo.imeOptions = 2 | editorinfo.imeOptions;
_L4:
		editorinfo.initialSelStart = imeadapter.getInitialSelectionStart();
		editorinfo.initialSelEnd = imeadapter.getInitialSelectionStart();
		return;
_L2:
		if (imeadapter.getTextInputType() == ImeAdapter.sTextInputTypeTextArea || imeadapter.getTextInputType() == ImeAdapter.sTextInputTypeContentEditable)
		{
			editorinfo.inputType = 0x2c000 | editorinfo.inputType;
			editorinfo.imeOptions = 1 | editorinfo.imeOptions;
			mSingleLine = false;
		} else
		if (imeadapter.getTextInputType() == ImeAdapter.sTextInputTypePassword)
		{
			editorinfo.inputType = 225;
			editorinfo.imeOptions = 2 | editorinfo.imeOptions;
		} else
		if (imeadapter.getTextInputType() == ImeAdapter.sTextInputTypeSearch)
			editorinfo.imeOptions = 3 | editorinfo.imeOptions;
		else
		if (imeadapter.getTextInputType() == ImeAdapter.sTextInputTypeUrl)
			editorinfo.imeOptions = 2 | editorinfo.imeOptions;
		else
		if (imeadapter.getTextInputType() == ImeAdapter.sTextInputTypeEmail)
		{
			editorinfo.inputType = 209;
			editorinfo.imeOptions = 2 | editorinfo.imeOptions;
		} else
		if (imeadapter.getTextInputType() == ImeAdapter.sTextInputTypeTel)
		{
			editorinfo.inputType = 3;
			editorinfo.imeOptions = 5 | editorinfo.imeOptions;
		} else
		if (imeadapter.getTextInputType() == ImeAdapter.sTextInputTypeNumber)
		{
			editorinfo.inputType = 2;
			editorinfo.imeOptions = 5 | editorinfo.imeOptions;
		}
		if (true) goto _L4; else goto _L3
_L3:
	}

	private InputMethodManagerWrapper getInputMethodManagerWrapper()
	{
		return mImeAdapter.getInputMethodManagerWrapper();
	}

	public boolean beginBatchEdit()
	{
		if (mNumNestedBatchEdits == 0)
			mImeAdapter.batchStateChanged(true);
		mNumNestedBatchEdits = 1 + mNumNestedBatchEdits;
		return false;
	}

	public boolean commitText(CharSequence charsequence, int i)
	{
		super.commitText(charsequence, i);
		ImeAdapter imeadapter = mImeAdapter;
		String s = charsequence.toString();
		boolean flag;
		if (charsequence.length() > 0)
			flag = true;
		else
			flag = false;
		return imeadapter.checkCompositionQueueAndCallNative(s, i, flag);
	}

	public boolean deleteSurroundingText(int i, int j)
	{
		if (!super.deleteSurroundingText(i, j))
			return false;
		else
			return mImeAdapter.deleteSurroundingText(i, j);
	}

	public boolean endBatchEdit()
	{
		if (mNumNestedBatchEdits != 0)
		{
			mNumNestedBatchEdits = -1 + mNumNestedBatchEdits;
			if (mNumNestedBatchEdits == 0)
			{
				mImeAdapter.batchStateChanged(false);
				return false;
			}
		}
		return false;
	}

	public boolean finishComposingText()
	{
		Editable editable = getEditable();
		if (getComposingSpanStart(editable) == getComposingSpanEnd(editable))
		{
			return true;
		} else
		{
			super.finishComposingText();
			mImeAdapter.finishComposingText();
			return true;
		}
	}

	public ExtractedText getExtractedText(ExtractedTextRequest extractedtextrequest, int i)
	{
		ExtractedText extractedtext = new ExtractedText();
		Editable editable = getEditable();
		extractedtext.text = editable.toString();
		extractedtext.partialEndOffset = editable.length();
		extractedtext.selectionStart = Selection.getSelectionStart(editable);
		extractedtext.selectionEnd = Selection.getSelectionEnd(editable);
		int j;
		if (mSingleLine)
			j = 1;
		else
			j = 0;
		extractedtext.flags = j;
		return extractedtext;
	}

	boolean isActive()
	{
		return getInputMethodManagerWrapper().isActive(mInternalView);
	}

	protected boolean isIgnoringTextInputStateUpdates()
	{
		return mIgnoreTextInputStateUpdates;
	}

	public boolean performContextMenuAction(int i)
	{
		switch (i)
		{
		default:
			return false;

		case 16908319: 
			return mImeAdapter.selectAll();

		case 16908320: 
			return mImeAdapter.cut();

		case 16908321: 
			return mImeAdapter.copy();

		case 16908322: 
			return mImeAdapter.paste();
		}
	}

	public boolean performEditorAction(int i)
	{
		if (i == 5)
		{
			restartInput();
			long l = System.currentTimeMillis();
			mImeAdapter.sendSyntheticKeyEvent(ImeAdapter.sEventTypeRawKeyDown, l, 61, 0);
		} else
		{
			mImeAdapter.sendKeyEventWithKeyCode(66, 22);
		}
		return true;
	}

	void restartInput()
	{
		getInputMethodManagerWrapper().restartInput(mInternalView);
		mIgnoreTextInputStateUpdates = false;
		mNumNestedBatchEdits = 0;
	}

	public boolean sendKeyEvent(KeyEvent keyevent)
	{
		if (keyevent.getAction() != 1) goto _L2; else goto _L1
_L1:
		if (keyevent.getKeyCode() != 67) goto _L4; else goto _L3
_L3:
		super.deleteSurroundingText(1, 0);
_L2:
		mImeAdapter.translateAndSendNativeEvents(keyevent);
		return true;
_L4:
		if (keyevent.getKeyCode() == 112)
		{
			super.deleteSurroundingText(0, 1);
		} else
		{
			int i = keyevent.getUnicodeChar();
			if (i != 0)
			{
				Editable editable = getEditable();
				int j = Selection.getSelectionStart(editable);
				int k = Selection.getSelectionEnd(editable);
				if (j > k)
				{
					int l = j;
					j = k;
					k = l;
				}
				editable.replace(j, k, Character.toString((char)i));
			}
		}
		if (true) goto _L2; else goto _L5
_L5:
	}

	public boolean setComposingRegion(int i, int j)
	{
		int k = Math.min(i, j);
		int l = Math.max(i, j);
		if (k < 0)
			k = 0;
		if (l < 0)
			l = 0;
		if (k == l)
			removeComposingSpans(getEditable());
		else
			super.setComposingRegion(k, l);
		return mImeAdapter.setComposingRegion(k, l);
	}

	public boolean setComposingText(CharSequence charsequence, int i)
	{
		super.setComposingText(charsequence, i);
		return mImeAdapter.checkCompositionQueueAndCallNative(charsequence.toString(), i, false);
	}

	public void setEditableText(String s, int i, int j, int k, int l)
	{
		String s1 = s.replace('\240', ' ');
		int i1 = Math.min(i, s1.length());
		int j1 = Math.min(j, s1.length());
		int k1 = Math.min(k, s1.length());
		int l1 = Math.min(l, s1.length());
		Editable editable = getEditable();
		if (!editable.toString().equals(s1))
			editable.replace(0, editable.length(), s1);
		int i2 = Selection.getSelectionStart(editable);
		int j2 = Selection.getSelectionEnd(editable);
		int k2 = getComposingSpanStart(editable);
		int l2 = getComposingSpanEnd(editable);
		if (i2 != i1 || j2 != j1 || k2 != k1 || l2 != l1)
		{
			Selection.setSelection(editable, i1, j1);
			if (k1 == l1)
				removeComposingSpans(editable);
			else
				super.setComposingRegion(k1, l1);
			if (!mIgnoreTextInputStateUpdates)
			{
				updateSelection(i1, j1, k1, l1);
				return;
			}
		}
	}

	public void setIgnoreTextInputStateUpdates(boolean flag)
	{
		mIgnoreTextInputStateUpdates = flag;
		if (flag)
		{
			return;
		} else
		{
			Editable editable = getEditable();
			updateSelection(Selection.getSelectionStart(editable), Selection.getSelectionEnd(editable), getComposingSpanStart(editable), getComposingSpanEnd(editable));
			return;
		}
	}

	public boolean setSelection(int i, int j)
	{
		if (i < 0 || j < 0)
		{
			return true;
		} else
		{
			super.setSelection(i, j);
			return mImeAdapter.setEditableSelectionOffsets(i, j);
		}
	}

	protected void updateSelection(int i, int j, int k, int l)
	{
		if (mLastUpdateSelectionStart == i && mLastUpdateSelectionEnd == j && mLastUpdateCompositionStart == k && mLastUpdateCompositionEnd == l)
		{
			return;
		} else
		{
			getInputMethodManagerWrapper().updateSelection(mInternalView, i, j, k, l);
			mLastUpdateSelectionStart = i;
			mLastUpdateSelectionEnd = j;
			mLastUpdateCompositionStart = k;
			mLastUpdateCompositionEnd = l;
			return;
		}
	}
}
