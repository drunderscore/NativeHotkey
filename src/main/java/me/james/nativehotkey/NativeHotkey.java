package me.james.nativehotkey;

import java.awt.event.*;
import java.util.*;
import java.util.logging.*;
import org.jnativehook.*;
import org.jnativehook.keyboard.*;

public class NativeHotkey
{
    private static HashMap< Integer[], Hotkey > hotkeys = new HashMap<>();
    private static boolean keys[] = new boolean[70000];

    public static boolean init()
    {
        Logger logger = Logger.getLogger( GlobalScreen.class.getPackage().getName() );
        logger.setLevel( Level.WARNING );

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers( false );
        try
        {
            GlobalScreen.registerNativeHook();
        } catch ( NativeHookException e )
        {
            System.err.println( "Failed to register native hook. (NativeHotkey)" );
            e.printStackTrace();
            return false;
        }
        GlobalScreen.addNativeKeyListener( new NativeKeyListener()
        {
            @Override
            public void nativeKeyTyped( NativeKeyEvent e )
            {

            }

            @Override
            public void nativeKeyPressed( NativeKeyEvent e )
            {
                keys[e.getKeyCode()] = true;
                for ( Map.Entry< Integer[], Hotkey > hotkeySet : hotkeys.entrySet() )
                    if ( allKeysPressed( Arrays.stream( hotkeySet.getKey() ).mapToInt( Integer::intValue ).toArray() ) && Arrays.stream( hotkeySet.getKey() ).anyMatch( val -> val == e.getKeyCode() ) )
                        hotkeySet.getValue().onPress( e );
            }

            @Override
            public void nativeKeyReleased( NativeKeyEvent e )
            {
                keys[e.getKeyCode()] = false;
            }
        } );
        return true;
    }

    private static boolean allKeysPressed( int[] pressed )
    {
        for ( int k : pressed )
            if ( !keys[k] )
                return false;
        if ( Arrays.stream( pressed ).allMatch( key -> key != NativeKeyEvent.VC_CONTROL ) && keys[NativeKeyEvent.VC_CONTROL] )
            return false;
        if ( Arrays.stream( pressed ).allMatch( key -> key != NativeKeyEvent.VC_SHIFT ) && keys[NativeKeyEvent.VC_SHIFT] )
            return false;
        if ( Arrays.stream( pressed ).allMatch( key -> key != NativeKeyEvent.VC_ALT ) && keys[NativeKeyEvent.VC_ALT] )
            return false;
        return true;
    }

    public static String keysToString( Integer... keysStr )
    {
        StringBuilder str = new StringBuilder();
        if ( Arrays.stream( keysStr ).mapToInt( Integer::intValue ).anyMatch( vc -> vc == NativeKeyEvent.VC_CONTROL ) )
            str.append( "Ctrl + " );
        if ( Arrays.stream( keysStr ).mapToInt( Integer::intValue ).anyMatch( vc -> vc == NativeKeyEvent.VC_SHIFT ) )
            str.append( "Shift + " );
        if ( Arrays.stream( keysStr ).mapToInt( Integer::intValue ).anyMatch( vc -> vc == NativeKeyEvent.VC_ALT ) )
            str.append( "Alt + " );
        for ( int k : keysStr )
        {
            if ( k == NativeKeyEvent.VC_CONTROL || k == NativeKeyEvent.VC_SHIFT || k == NativeKeyEvent.VC_ALT )
                continue;
            str.append( KeyEvent.getKeyText( toSwingKey( k ) ) ).append( " + " );
        }
        str = new StringBuilder( str.subSequence( 0, str.length() - 3 ) );
        return str.toString();
    }

    public static void registerHotkey( Hotkey hot, Integer... keys )
    {
        NativeHotkey.hotkeys.put( keys, hot );
    }

    public static boolean isKeyDown( int virtualCode )
    {
        return keys[virtualCode];
    }

    public static Map< Integer[], Hotkey > getHotkeys()
    {
        return Collections.unmodifiableMap( hotkeys );
    }

    private static int toSwingKey( int key )
    {
        switch ( key )
        {
            case NativeKeyEvent.VC_ESCAPE:
                return KeyEvent.VK_ESCAPE;

            // Begin Function Keys
            case NativeKeyEvent.VC_F1:
                return KeyEvent.VK_F1;

            case NativeKeyEvent.VC_F2:
                return KeyEvent.VK_F2;

            case NativeKeyEvent.VC_F3:
                return KeyEvent.VK_F3;

            case NativeKeyEvent.VC_F4:
                return KeyEvent.VK_F4;

            case NativeKeyEvent.VC_F5:
                return KeyEvent.VK_F5;

            case NativeKeyEvent.VC_F6:
                return KeyEvent.VK_F6;

            case NativeKeyEvent.VC_F7:
                return KeyEvent.VK_F7;

            case NativeKeyEvent.VC_F8:
                return KeyEvent.VK_F8;

            case NativeKeyEvent.VC_F9:
                return KeyEvent.VK_F9;

            case NativeKeyEvent.VC_F10:
                return KeyEvent.VK_F10;

            case NativeKeyEvent.VC_F11:
                return KeyEvent.VK_F11;

            case NativeKeyEvent.VC_F12:
                return KeyEvent.VK_F12;

            case NativeKeyEvent.VC_F13:
                return KeyEvent.VK_F13;

            case NativeKeyEvent.VC_F14:
                return KeyEvent.VK_F14;

            case NativeKeyEvent.VC_F15:
                return KeyEvent.VK_F15;

            case NativeKeyEvent.VC_F16:
                return KeyEvent.VK_F16;

            case NativeKeyEvent.VC_F17:
                return KeyEvent.VK_F17;

            case NativeKeyEvent.VC_F18:
                return KeyEvent.VK_F18;

            case NativeKeyEvent.VC_F19:
                return KeyEvent.VK_F19;
            case NativeKeyEvent.VC_F20:
                return KeyEvent.VK_F20;

            case NativeKeyEvent.VC_F21:
                return KeyEvent.VK_F21;

            case NativeKeyEvent.VC_F22:
                return KeyEvent.VK_F22;

            case NativeKeyEvent.VC_F23:
                return KeyEvent.VK_F23;

            case NativeKeyEvent.VC_F24:
                return KeyEvent.VK_F24;
            // End Function Keys

            // Begin Alphanumeric Zone
            case NativeKeyEvent.VC_BACKQUOTE:
                return KeyEvent.VK_BACK_QUOTE;

            case NativeKeyEvent.VC_1:
                return KeyEvent.VK_1;

            case NativeKeyEvent.VC_2:
                return KeyEvent.VK_2;

            case NativeKeyEvent.VC_3:
                return KeyEvent.VK_3;

            case NativeKeyEvent.VC_4:
                return KeyEvent.VK_4;

            case NativeKeyEvent.VC_5:
                return KeyEvent.VK_5;

            case NativeKeyEvent.VC_6:
                return KeyEvent.VK_6;

            case NativeKeyEvent.VC_7:
                return KeyEvent.VK_7;

            case NativeKeyEvent.VC_8:
                return KeyEvent.VK_8;

            case NativeKeyEvent.VC_9:
                return KeyEvent.VK_9;

            case NativeKeyEvent.VC_0:
                return KeyEvent.VK_0;

            case NativeKeyEvent.VC_MINUS:
                return KeyEvent.VK_MINUS;

            case NativeKeyEvent.VC_EQUALS:
                return KeyEvent.VK_EQUALS;

            case NativeKeyEvent.VC_BACKSPACE:
                return KeyEvent.VK_BACK_SPACE;

            case NativeKeyEvent.VC_TAB:
                return KeyEvent.VK_TAB;

            case NativeKeyEvent.VC_CAPS_LOCK:
                return KeyEvent.VK_CAPS_LOCK;

            case NativeKeyEvent.VC_A:
                return KeyEvent.VK_A;

            case NativeKeyEvent.VC_B:
                return KeyEvent.VK_B;

            case NativeKeyEvent.VC_C:
                return KeyEvent.VK_C;

            case NativeKeyEvent.VC_D:
                return KeyEvent.VK_D;

            case NativeKeyEvent.VC_E:
                return KeyEvent.VK_E;

            case NativeKeyEvent.VC_F:
                return KeyEvent.VK_F;

            case NativeKeyEvent.VC_G:
                return KeyEvent.VK_G;

            case NativeKeyEvent.VC_H:
                return KeyEvent.VK_H;

            case NativeKeyEvent.VC_I:
                return KeyEvent.VK_I;

            case NativeKeyEvent.VC_J:
                return KeyEvent.VK_J;

            case NativeKeyEvent.VC_K:
                return KeyEvent.VK_K;

            case NativeKeyEvent.VC_L:
                return KeyEvent.VK_L;

            case NativeKeyEvent.VC_M:
                return KeyEvent.VK_M;

            case NativeKeyEvent.VC_N:
                return KeyEvent.VK_N;

            case NativeKeyEvent.VC_O:
                return KeyEvent.VK_O;

            case NativeKeyEvent.VC_P:
                return KeyEvent.VK_P;

            case NativeKeyEvent.VC_Q:
                return KeyEvent.VK_Q;

            case NativeKeyEvent.VC_R:
                return KeyEvent.VK_R;

            case NativeKeyEvent.VC_S:
                return KeyEvent.VK_S;

            case NativeKeyEvent.VC_T:
                return KeyEvent.VK_T;

            case NativeKeyEvent.VC_U:
                return KeyEvent.VK_U;

            case NativeKeyEvent.VC_V:
                return KeyEvent.VK_V;

            case NativeKeyEvent.VC_W:
                return KeyEvent.VK_W;

            case NativeKeyEvent.VC_X:
                return KeyEvent.VK_X;

            case NativeKeyEvent.VC_Y:
                return KeyEvent.VK_Y;

            case NativeKeyEvent.VC_Z:
                return KeyEvent.VK_Z;

            case NativeKeyEvent.VC_OPEN_BRACKET:
                return KeyEvent.VK_OPEN_BRACKET;

            case NativeKeyEvent.VC_CLOSE_BRACKET:
                return KeyEvent.VK_CLOSE_BRACKET;

            case NativeKeyEvent.VC_BACK_SLASH:
                return KeyEvent.VK_BACK_SLASH;

            case NativeKeyEvent.VC_SEMICOLON:
                return KeyEvent.VK_SEMICOLON;

            case NativeKeyEvent.VC_QUOTE:
                return KeyEvent.VK_QUOTE;

            case NativeKeyEvent.VC_ENTER:
                return KeyEvent.VK_ENTER;

            case NativeKeyEvent.VC_COMMA:
                return KeyEvent.VK_COMMA;

            case NativeKeyEvent.VC_PERIOD:
                return KeyEvent.VK_PERIOD;

            case NativeKeyEvent.VC_SLASH:
                return KeyEvent.VK_SLASH;

            case NativeKeyEvent.VC_SPACE:
                return KeyEvent.VK_SPACE;
            // End Alphanumeric Zone

            case NativeKeyEvent.VC_PRINTSCREEN:
                return KeyEvent.VK_PRINTSCREEN;

            case NativeKeyEvent.VC_SCROLL_LOCK:
                return KeyEvent.VK_SCROLL_LOCK;

            case NativeKeyEvent.VC_PAUSE:
                return KeyEvent.VK_PAUSE;

            // Begin Edit Key Zone
            case NativeKeyEvent.VC_INSERT:
                return KeyEvent.VK_INSERT;

            case NativeKeyEvent.VC_DELETE:
                return KeyEvent.VK_DELETE;

            case NativeKeyEvent.VC_HOME:
                return KeyEvent.VK_HOME;

            case NativeKeyEvent.VC_END:
                return KeyEvent.VK_END;

            case NativeKeyEvent.VC_PAGE_UP:
                return KeyEvent.VK_PAGE_UP;

            case NativeKeyEvent.VC_PAGE_DOWN:
                return KeyEvent.VK_PAGE_DOWN;
            // End Edit Key Zone

            // Begin Cursor Key Zone
            case NativeKeyEvent.VC_UP:
                return KeyEvent.VK_UP;
            case NativeKeyEvent.VC_LEFT:
                return KeyEvent.VK_LEFT;
            case NativeKeyEvent.VC_CLEAR:
                return KeyEvent.VK_CLEAR;
            case NativeKeyEvent.VC_RIGHT:
                return KeyEvent.VK_RIGHT;
            case NativeKeyEvent.VC_DOWN:
                return KeyEvent.VK_DOWN;
            // End Cursor Key Zone

            // Begin Numeric Zone
            case NativeKeyEvent.VC_NUM_LOCK:
                return KeyEvent.VK_NUM_LOCK;

            case NativeKeyEvent.VC_SEPARATOR:
                return KeyEvent.VK_SEPARATOR;
            // End Numeric Zone

            // Begin Modifier and Control Keys
            case NativeKeyEvent.VC_SHIFT:
                return KeyEvent.VK_SHIFT;

            case NativeKeyEvent.VC_CONTROL:
                return KeyEvent.VK_CONTROL;

            case NativeKeyEvent.VC_ALT:
                return KeyEvent.VK_ALT;

            case NativeKeyEvent.VC_META:
                return KeyEvent.VK_META;

            case NativeKeyEvent.VC_CONTEXT_MENU:
                return KeyEvent.VK_CONTEXT_MENU;
            // End Modifier and Control Keys

			/* Begin Media Control Keys
            case NativeKeyEvent.VC_POWER:
			case NativeKeyEvent.VC_SLEEP:
			case NativeKeyEvent.VC_WAKE:
			case NativeKeyEvent.VC_MEDIA_PLAY:
			case NativeKeyEvent.VC_MEDIA_STOP:
			case NativeKeyEvent.VC_MEDIA_PREVIOUS:
			case NativeKeyEvent.VC_MEDIA_NEXT:
			case NativeKeyEvent.VC_MEDIA_SELECT:
			case NativeKeyEvent.VC_MEDIA_EJECT:
			case NativeKeyEvent.VC_VOLUME_MUTE:
			case NativeKeyEvent.VC_VOLUME_UP:
			case NativeKeyEvent.VC_VOLUME_DOWN:
			case NativeKeyEvent.VC_APP_MAIL:
			case NativeKeyEvent.VC_APP_CALCULATOR:
			case NativeKeyEvent.VC_APP_MUSIC:
			case NativeKeyEvent.VC_APP_PICTURES:
			case NativeKeyEvent.VC_BROWSER_SEARCH:
			case NativeKeyEvent.VC_BROWSER_HOME:
			case NativeKeyEvent.VC_BROWSER_BACK:
			case NativeKeyEvent.VC_BROWSER_FORWARD:
			case NativeKeyEvent.VC_BROWSER_STOP:
			case NativeKeyEvent.VC_BROWSER_REFRESH:
			case NativeKeyEvent.VC_BROWSER_FAVORITES:
			// End Media Control Keys */

            // Begin Japanese Language Keys
            case NativeKeyEvent.VC_KATAKANA:
                return KeyEvent.VK_KATAKANA;

            case NativeKeyEvent.VC_UNDERSCORE:
                return KeyEvent.VK_UNDERSCORE;

            //case VC_FURIGANA:
            case NativeKeyEvent.VC_KANJI:
                return KeyEvent.VK_KANJI;

            case NativeKeyEvent.VC_HIRAGANA:
                return KeyEvent.VK_HIRAGANA;

            //case VC_YEN:
            // End Japanese Language Keys

            // Begin Sun keyboards
            case NativeKeyEvent.VC_SUN_HELP:
                return KeyEvent.VK_HELP;

            case NativeKeyEvent.VC_SUN_STOP:
                return KeyEvent.VK_STOP;

            //case VC_SUN_FRONT:
            //case VC_SUN_OPEN:
            case NativeKeyEvent.VC_SUN_PROPS:
                return KeyEvent.VK_PROPS;

            case NativeKeyEvent.VC_SUN_FIND:
                return KeyEvent.VK_FIND;

            case NativeKeyEvent.VC_SUN_AGAIN:
                return KeyEvent.VK_AGAIN;

            //case NativeKeyEvent.VC_SUN_INSERT:
            case NativeKeyEvent.VC_SUN_COPY:
                return KeyEvent.VK_COPY;

            case NativeKeyEvent.VC_SUN_CUT:
                return KeyEvent.VK_CUT;
            // End Sun keyboards
        }
        return KeyEvent.VK_UNDEFINED;
    }
}
