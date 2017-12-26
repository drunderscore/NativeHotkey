package me.james.nativehotkey;

import org.jnativehook.keyboard.*;

public abstract class Hotkey
{
    public abstract void onPress( NativeKeyEvent e );
}
