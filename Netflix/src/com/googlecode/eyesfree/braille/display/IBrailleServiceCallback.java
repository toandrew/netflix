// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.googlecode.eyesfree.braille.display;

import android.os.*;

// Referenced classes of package com.googlecode.eyesfree.braille.display:
//			BrailleDisplayProperties, BrailleInputEvent

public interface IBrailleServiceCallback
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements IBrailleServiceCallback
	{

		private static final String DESCRIPTOR = "com.googlecode.eyesfree.braille.display.IBrailleServiceCallback";
		static final int TRANSACTION_onDisplayConnected = 1;
		static final int TRANSACTION_onDisplayDisconnected = 2;
		static final int TRANSACTION_onInput = 3;

		public static IBrailleServiceCallback asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
			if (iinterface != null && (iinterface instanceof IBrailleServiceCallback))
				return (IBrailleServiceCallback)iinterface;
			else
				return new Proxy(ibinder);
		}

		public IBinder asBinder()
		{
			return this;
		}

		public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
			throws RemoteException
		{
			switch (i)
			{
			default:
				return super.onTransact(i, parcel, parcel1, j);

			case 1598968902: 
				parcel1.writeString("com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
				BrailleDisplayProperties brailledisplayproperties;
				if (parcel.readInt() != 0)
					brailledisplayproperties = (BrailleDisplayProperties)BrailleDisplayProperties.CREATOR.createFromParcel(parcel);
				else
					brailledisplayproperties = null;
				onDisplayConnected(brailledisplayproperties);
				parcel1.writeNoException();
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
				onDisplayDisconnected();
				parcel1.writeNoException();
				return true;

			case 3: // '\003'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
				break;
			}
			BrailleInputEvent brailleinputevent;
			if (parcel.readInt() != 0)
				brailleinputevent = (BrailleInputEvent)BrailleInputEvent.CREATOR.createFromParcel(parcel);
			else
				brailleinputevent = null;
			onInput(brailleinputevent);
			parcel1.writeNoException();
			return true;
		}

		public Stub()
		{
			attachInterface(this, "com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
		}
	}

	private static class Stub.Proxy
		implements IBrailleServiceCallback
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public String getInterfaceDescriptor()
		{
			return "com.googlecode.eyesfree.braille.display.IBrailleServiceCallback";
		}

		public void onDisplayConnected(BrailleDisplayProperties brailledisplayproperties)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
			if (brailledisplayproperties == null)
				break MISSING_BLOCK_LABEL_56;
			parcel.writeInt(1);
			brailledisplayproperties.writeToParcel(parcel, 0);
_L1:
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			parcel1.recycle();
			parcel.recycle();
			return;
			parcel.writeInt(0);
			  goto _L1
			Exception exception;
			exception;
			parcel1.recycle();
			parcel.recycle();
			throw exception;
		}

		public void onDisplayDisconnected()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			parcel1.recycle();
			parcel.recycle();
			return;
			Exception exception;
			exception;
			parcel1.recycle();
			parcel.recycle();
			throw exception;
		}

		public void onInput(BrailleInputEvent brailleinputevent)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.display.IBrailleServiceCallback");
			if (brailleinputevent == null)
				break MISSING_BLOCK_LABEL_56;
			parcel.writeInt(1);
			brailleinputevent.writeToParcel(parcel, 0);
_L1:
			mRemote.transact(3, parcel, parcel1, 0);
			parcel1.readException();
			parcel1.recycle();
			parcel.recycle();
			return;
			parcel.writeInt(0);
			  goto _L1
			Exception exception;
			exception;
			parcel1.recycle();
			parcel.recycle();
			throw exception;
		}

		Stub.Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract void onDisplayConnected(BrailleDisplayProperties brailledisplayproperties)
		throws RemoteException;

	public abstract void onDisplayDisconnected()
		throws RemoteException;

	public abstract void onInput(BrailleInputEvent brailleinputevent)
		throws RemoteException;
}
