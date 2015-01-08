// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.common;

import android.text.TextUtils;
import android.util.Log;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class CommandLine
{
	private static class JavaCommandLine extends CommandLine
	{

		static final boolean $assertionsDisabled;
		private ArrayList mArgs;
		private int mArgsBegin;
		private HashMap mSwitches;

		private void appendSwitchesInternal(String as[], int i)
		{
			boolean flag = true;
			int j = as.length;
			int k = 0;
			while (k < j) 
			{
				String s = as[k];
				if (i > 0)
				{
					i--;
				} else
				{
					if (s.equals("--"))
						flag = false;
					if (flag && s.startsWith("--"))
					{
						String as1[] = s.split("=", 2);
						String s1;
						if (as1.length > 1)
							s1 = as1[1];
						else
							s1 = null;
						appendSwitchWithValue(as1[0].substring("--".length()), s1);
					} else
					{
						mArgs.add(s);
					}
				}
				k++;
			}
		}

		private String[] getCommandLineArguments()
		{
			return (String[])mArgs.toArray(new String[mArgs.size()]);
		}

		public void appendSwitch(String s)
		{
			appendSwitchWithValue(s, null);
		}

		public void appendSwitchWithValue(String s, String s1)
		{
			HashMap hashmap = mSwitches;
			String s2;
			String s3;
			ArrayList arraylist;
			int i;
			if (s1 == null)
				s2 = "";
			else
				s2 = s1;
			hashmap.put(s, s2);
			s3 = (new StringBuilder()).append("--").append(s).toString();
			if (s1 != null && !s1.isEmpty())
				s3 = (new StringBuilder()).append(s3).append("=").append(s1).toString();
			arraylist = mArgs;
			i = mArgsBegin;
			mArgsBegin = i + 1;
			arraylist.add(i, s3);
		}

		public void appendSwitchesAndArguments(String as[])
		{
			appendSwitchesInternal(as, 0);
		}

		public String getSwitchValue(String s)
		{
			String s1 = (String)mSwitches.get(s);
			if (s1 == null || s1.isEmpty())
				s1 = null;
			return s1;
		}

		public boolean hasSwitch(String s)
		{
			return mSwitches.containsKey(s);
		}

		static 
		{
			boolean flag;
			if (!org/chromium/content/common/CommandLine.desiredAssertionStatus())
				flag = true;
			else
				flag = false;
			$assertionsDisabled = flag;
		}


		JavaCommandLine(String as[])
		{
			mSwitches = new HashMap();
			mArgs = new ArrayList();
			mArgsBegin = 1;
			if (as == null || as.length == 0 || as[0] == null)
			{
				mArgs.add("");
			} else
			{
				mArgs.add(as[0]);
				appendSwitchesInternal(as, 1);
			}
			if (!$assertionsDisabled && mArgs.size() <= 0)
				throw new AssertionError();
			else
				return;
		}
	}

	private static class NativeCommandLine extends CommandLine
	{

		public void appendSwitch(String s)
		{
			CommandLine.nativeAppendSwitch(s);
		}

		public void appendSwitchWithValue(String s, String s1)
		{
			CommandLine.nativeAppendSwitchWithValue(s, s1);
		}

		public void appendSwitchesAndArguments(String as[])
		{
			CommandLine.nativeAppendSwitchesAndArguments(as);
		}

		public String getSwitchValue(String s)
		{
			return CommandLine.nativeGetSwitchValue(s);
		}

		public boolean hasSwitch(String s)
		{
			return CommandLine.nativeHasSwitch(s);
		}

		public boolean isNativeImplementation()
		{
			return true;
		}

		private NativeCommandLine()
		{
		}

	}


	static final boolean $assertionsDisabled = false;
	public static final String ACCESSIBILITY_DEBUG_BRAILLE_SERVICE = "debug-braille-service";
	public static final String ACCESSIBILITY_JAVASCRIPT_URL = "accessibility-js-url";
	public static final String ADD_OFFICIAL_COMMAND_LINE = "add-official-command-line";
	public static final String DUMP_RENDER_TREE = "dump-render-tree";
	public static final String ENABLE_INSTANT_EXTENDED_API = "enable-instant-extended-api";
	public static final String ENABLE_SPEECH_RECOGNITION = "enable-speech-recognition";
	public static final String ENABLE_TEST_INTENTS = "enable-test-intents";
	public static final String ENABLE_TOP_CONTROLS_POSITION_CALCULATION = "enable-top-controls-position-calculation";
	public static final String LOG_FPS = "log-fps";
	public static final String NETWORK_COUNTRY_ISO = "network-country-iso";
	private static final String SWITCH_PREFIX = "--";
	private static final String SWITCH_TERMINATOR = "--";
	private static final String SWITCH_VALUE_SEPARATOR = "=";
	public static final String TABLET_UI = "tablet-ui";
	private static final String TAG = "CommandLine";
	public static final String TOP_CONTROLS_HEIGHT = "top-controls-height";
	public static final String TOP_CONTROLS_HIDE_THRESHOLD = "top-controls-hide-threshold";
	public static final String TOP_CONTROLS_SHOW_THRESHOLD = "top-controls-show-threshold";
	public static final String USE_MOBILE_UA = "use-mobile-user-agent";
	public static final String WAIT_FOR_JAVA_DEBUGGER = "wait-for-java-debugger";
	private static final AtomicReference sCommandLine = new AtomicReference();

	private CommandLine()
	{
	}


	public static void enableNativeProxy()
	{
		sCommandLine.set(new NativeCommandLine());
	}

	public static CommandLine getInstance()
	{
		CommandLine commandline = (CommandLine)sCommandLine.get();
		if (!$assertionsDisabled && commandline == null)
			throw new AssertionError();
		else
			return commandline;
	}

	public static String[] getJavaSwitchesOrNull()
	{
		CommandLine commandline = (CommandLine)sCommandLine.get();
		if (commandline != null)
		{
			if (!$assertionsDisabled && commandline.isNativeImplementation())
				throw new AssertionError();
			else
				return ((JavaCommandLine)commandline).getCommandLineArguments();
		} else
		{
			return null;
		}
	}

	public static void init(String as[])
	{
		setInstance(new JavaCommandLine(as));
	}

	public static void initFromFile(String s)
	{
		char ac[] = readUtf8FileFully(s, 8192);
		String as[];
		if (ac == null)
			as = null;
		else
			as = tokenizeQuotedAruments(ac);
		init(as);
	}

	public static boolean isInitialized()
	{
		return sCommandLine.get() != null;
	}

	private static native void nativeAppendSwitch(String s);

	private static native void nativeAppendSwitchWithValue(String s, String s1);

	private static native void nativeAppendSwitchesAndArguments(String as[]);

	private static native String nativeGetSwitchValue(String s);

	private static native boolean nativeHasSwitch(String s);

	private static native void nativeReset();

	private static char[] readUtf8FileFully(String s, int i)
	{
		InputStreamReader inputstreamreader;
		File file;
		int j;
		inputstreamreader = null;
		file = new File(s);
		long l = file.length();
		if (l == 0L)
			return null;
		if (l > (long)i)
		{
			Log.w("CommandLine", (new StringBuilder()).append("File ").append(s).append(" length ").append(l).append(" exceeds limit ").append(i).toString());
			return null;
		}
		j = (int)l;
		char ac[];
		InputStreamReader inputstreamreader2;
		ac = new char[j];
		inputstreamreader2 = new InputStreamReader(new FileInputStream(file), "UTF-8");
		int k;
		k = inputstreamreader2.read(ac);
		if (!$assertionsDisabled && inputstreamreader2.ready())
			throw new AssertionError();
		  goto _L1
		FileNotFoundException filenotfoundexception1;
		filenotfoundexception1;
		InputStreamReader inputstreamreader1 = inputstreamreader2;
_L7:
		IOException ioexception5;
		char ac1[];
		if (inputstreamreader1 != null)
			try
			{
				inputstreamreader1.close();
			}
			catch (IOException ioexception)
			{
				Log.e("CommandLine", "Unable to close file reader.", ioexception);
			}
		return null;
_L1:
		if (k >= ac.length)
			break MISSING_BLOCK_LABEL_181;
		ac1 = Arrays.copyOfRange(ac, 0, k);
		ac = ac1;
		if (inputstreamreader2 != null)
			try
			{
				inputstreamreader2.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ioexception5)
			{
				Log.e("CommandLine", "Unable to close file reader.", ioexception5);
			}
		return ac;
		IOException ioexception2;
		ioexception2;
_L5:
		if (inputstreamreader != null)
			try
			{
				inputstreamreader.close();
			}
			catch (IOException ioexception3)
			{
				Log.e("CommandLine", "Unable to close file reader.", ioexception3);
			}
		return null;
		Exception exception;
		exception;
_L3:
		if (inputstreamreader != null)
			try
			{
				inputstreamreader.close();
			}
			catch (IOException ioexception1)
			{
				Log.e("CommandLine", "Unable to close file reader.", ioexception1);
			}
		throw exception;
		exception;
		inputstreamreader = inputstreamreader2;
		if (true) goto _L3; else goto _L2
_L2:
		IOException ioexception4;
		ioexception4;
		inputstreamreader = inputstreamreader2;
		if (true) goto _L5; else goto _L4
_L4:
		FileNotFoundException filenotfoundexception;
		filenotfoundexception;
		inputstreamreader1 = null;
		if (true) goto _L7; else goto _L6
_L6:
	}

	public static void reset()
	{
		setInstance(null);
	}

	private static void setInstance(CommandLine commandline)
	{
		CommandLine commandline1 = (CommandLine)sCommandLine.getAndSet(commandline);
		if (commandline1 != null && commandline1.isNativeImplementation())
			nativeReset();
	}

	public static String[] tokenizeQuotedAruments(char ac[])
	{
		ArrayList arraylist = new ArrayList();
		StringBuilder stringbuilder = null;
		int i = 0;
		int j = ac.length;
		int k = 0;
		while (k < j) 
		{
			char c = ac[k];
			if (i == 0 && (c == '\'' || c == '"') || c == i)
			{
				if (stringbuilder != null && stringbuilder.length() > 0 && stringbuilder.charAt(-1 + stringbuilder.length()) == '\\')
					stringbuilder.setCharAt(-1 + stringbuilder.length(), c);
				else
				if (i == 0)
					i = c;
				else
					i = 0;
			} else
			if (i == 0 && Character.isWhitespace(c))
			{
				if (stringbuilder != null)
				{
					arraylist.add(stringbuilder.toString());
					stringbuilder = null;
				}
			} else
			{
				if (stringbuilder == null)
					stringbuilder = new StringBuilder();
				stringbuilder.append(c);
			}
			k++;
		}
		if (stringbuilder != null)
		{
			if (i != 0)
				Log.w("CommandLine", (new StringBuilder()).append("Unterminated quoted string: ").append(stringbuilder).toString());
			arraylist.add(stringbuilder.toString());
		}
		return (String[])arraylist.toArray(new String[arraylist.size()]);
	}

	public abstract void appendSwitch(String s);

	public abstract void appendSwitchWithValue(String s, String s1);

	public abstract void appendSwitchesAndArguments(String as[]);

	public abstract String getSwitchValue(String s);

	public String getSwitchValue(String s, String s1)
	{
		String s2 = getSwitchValue(s);
		if (TextUtils.isEmpty(s2))
			return s1;
		else
			return s2;
	}

	public abstract boolean hasSwitch(String s);

	public boolean isNativeImplementation()
	{
		return false;
	}

	static 
	{
		boolean flag;
		if (!org/chromium/content/common/CommandLine.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}





}
