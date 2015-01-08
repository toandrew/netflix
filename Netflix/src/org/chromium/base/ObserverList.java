// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import java.util.*;

public class ObserverList
	implements Iterable
{
	private class ObserverListIterator
		implements Iterator
	{

		private int mIndex;
		private boolean mIsExhausted;
		private final int mListEndMarker;
		final ObserverList this$0;

		private void compactListIfNeeded()
		{
			if (!mIsExhausted)
			{
				mIsExhausted = true;
				decrementIterationDepthAndCompactIfNeeded();
			}
		}

		public boolean hasNext()
		{
			int i;
			for (i = mIndex; i < mListEndMarker && getObserverAt(i) == null; i++);
			if (i < mListEndMarker)
			{
				return true;
			} else
			{
				compactListIfNeeded();
				return false;
			}
		}

		public Object next()
		{
			for (; mIndex < mListEndMarker && getObserverAt(mIndex) == null; mIndex = 1 + mIndex);
			if (mIndex < mListEndMarker)
			{
				ObserverList observerlist = ObserverList.this;
				int i = mIndex;
				mIndex = i + 1;
				return observerlist.getObserverAt(i);
			} else
			{
				compactListIfNeeded();
				throw new NoSuchElementException();
			}
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		private ObserverListIterator()
		{
			this$0 = ObserverList.this;
			super();
			mIndex = 0;
			mIsExhausted = false;
			incrementIterationDepth();
			mListEndMarker = getSize();
		}

	}


	static final boolean $assertionsDisabled;
	private int mIterationDepth;
	public final List mObservers = new ArrayList();

	public ObserverList()
	{
		mIterationDepth = 0;
	}

	private void compact()
	{
		if (!$assertionsDisabled && mIterationDepth != 0)
			throw new AssertionError();
		Iterator iterator1 = mObservers.iterator();
		do
		{
			if (!iterator1.hasNext())
				break;
			if (iterator1.next() == null)
				iterator1.remove();
		} while (true);
	}

	private void decrementIterationDepthAndCompactIfNeeded()
	{
		mIterationDepth = -1 + mIterationDepth;
		if (!$assertionsDisabled && mIterationDepth < 0)
			throw new AssertionError();
		if (mIterationDepth == 0)
			compact();
	}

	private Object getObserverAt(int i)
	{
		return mObservers.get(i);
	}

	private int getSize()
	{
		return mObservers.size();
	}

	private void incrementIterationDepth()
	{
		mIterationDepth = 1 + mIterationDepth;
	}

	public void addObserver(Object obj)
	{
		if (obj == null || mObservers.contains(obj))
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
		} else
		{
			mObservers.add(obj);
		}
	}

	public void clear()
	{
		if (mIterationDepth == 0)
		{
			mObservers.clear();
		} else
		{
			int i = mObservers.size();
			int j = 0;
			while (j < i) 
			{
				mObservers.set(j, null);
				j++;
			}
		}
	}

	public boolean hasObserver(Object obj)
	{
		return mObservers.contains(obj);
	}

	public Iterator iterator()
	{
		return new ObserverListIterator();
	}

	public void removeObserver(Object obj)
	{
		int i = mObservers.indexOf(obj);
		if (i == -1)
			return;
		if (mIterationDepth == 0)
		{
			mObservers.remove(obj);
			return;
		} else
		{
			mObservers.set(i, null);
			return;
		}
	}

	static 
	{
		boolean flag;
		if (!org/chromium/base/ObserverList.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}




}
