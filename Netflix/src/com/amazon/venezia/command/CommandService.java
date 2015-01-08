// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			Command, ResultCallback

public interface CommandService
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements CommandService
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.CommandService";
		static final int TRANSACTION_execute = 1;

		public static CommandService asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.CommandService");
			if (iinterface != null && (iinterface instanceof CommandService))
				return (CommandService)iinterface;
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
				parcel1.writeString("com.amazon.venezia.command.CommandService");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.venezia.command.CommandService");
				execute(Command.Stub.asInterface(parcel.readStrongBinder()), ResultCallback.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.CommandService");
		}
	}

	private static class Stub.Proxy
		implements CommandService
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public void execute(Command command, ResultCallback resultcallback)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("com.amazon.venezia.command.CommandService");
			if (command == null) goto _L2; else goto _L1
_L1:
			IBinder ibinder = command.asBinder();
_L5:
			parcel.writeStrongBinder(ibinder);
			if (resultcallback == null) goto _L4; else goto _L3
_L3:
			IBinder ibinder1 = resultcallback.asBinder();
_L6:
			parcel.writeStrongBinder(ibinder1);
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			parcel1.recycle();
			parcel.recycle();
			return;
_L2:
			ibinder = null;
			  goto _L5
_L4:
			ibinder1 = null;
			  goto _L6
			Exception exception;
			exception;
			parcel1.recycle();
			parcel.recycle();
			throw exception;
			  goto _L5
		}

		public String getInterfaceDescriptor()
		{
			return "com.amazon.venezia.command.CommandService";
		}

		Stub.Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract void execute(Command command, ResultCallback resultcallback)
		throws RemoteException;
}
