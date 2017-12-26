# NativeHotkey
[![](https://jitpack.io/v/electricman226/NativeHotkey.svg)](https://jitpack.io/#electricman226/NativeHotkey)

An easy-to-use, easy-to-implement library for global hotkeys in Java.

# Why?
I found it quite interesting that no one had created something similar, so I said *"what the hell"* and created it as a library, and released it.
This makes it easy to create a 'hotkey' system, that is working just as you would expect. (multiple keys, modifier keys, modifier protection, etc)

# How to use
When you want to start using hotkeys, all you have to do is call `NativeHotkey#init` wherever (maybe in main, or one of your inits), and that's everything taken care of for key handling.

## To register a hotkey:
```java
NativeHotkey.registerHotkey( new Hotkey()
{
    @Override
    public void onPress( NativeKeyEvent e )
    {
        System.out.println( "hi from c" );
    }
}, NativeKeyEvent.VC_C );
NativeHotkey.registerHotkey( new Hotkey()
{
    @Override
    public void onPress( NativeKeyEvent e )
    {
        System.out.println( "hi from c, with control" );
    }
}, NativeKeyEvent.VC_C, NativeKeyEvent.VC_CONTROL );
```

You can also create a class if you want, instead of an anonymous one. There are reasons you want to do this.


`RandomIntHotkey.java`:
```java
public class RandomIntHotkey extends Hotkey
{
    public final int max;
    private Random rand;

    public RandomIntHotkey( int max )
    {
        this.max = max;
        rand = new Random();
    }

    @Override
    public void onPress( NativeKeyEvent e )
    {
        System.out.println( String.format( "oh boy, you've got a %d", rand.nextInt( max ) ) );
    }
}
```


`SomewhereElseInYourInit.java`:
```java
NativeHotkey.registerHotkey( new RandomIntHotkey( 20 ), NativeKeyEvent.VC_D );
NativeHotkey.registerHotkey( new RandomIntHotkey( 100 ), NativeKeyEvent.VC_SPACE );
```

## Hotkey as String
If you need to display your hotkey as a string in a G/UI, you can use `NativeHotkey#keysToString` to get a string representation of a hotkey.

```java
public static void main(String[] args)
{
    System.out.println( keysToString( NativeKeyEvent.VC_K, NativeKeyEvent.VC_CONTROL ) ); // Ctrl + K
    System.out.println( keysToString( NativeKeyEvent.VC_SHIFT, NativeKeyEvent.VC_D, NativeKeyEvent.VC_CONTROL ) ); // Ctrl + Shift + D
    NativeHotkey.registerHotkey( new RandomIntHotkey( 20 ), NativeKeyEvent.VC_D, NativeKeyEvent.VC_SHIFT );
    NativeHotkey.registerHotkey( new RandomIntHotkey( 100 ), NativeKeyEvent.VC_SPACE );
    for ( Map.Entry< Integer[], Hotkey > hKeys : NativeHotkey.getHotkeys() )
        System.out.println( NativeHotkey.keysToString( hKeys.getKey() ) );
}
```
