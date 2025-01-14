# ChatMenuAPI - EverNifeCore's Version
An API for making menus inside Minecraft's chat.
This API treats Minecraft's chat like a 2D grid, allowing you to position elements freely in chat.

### Fork

This is a fork of a fork of a fork... from the original ChatMenuAPI by timtomtim7.\
It has some small changes and optimizations on the code.

### EverNifeCore

This fork is meant to be embedded on the **[EverNifeCore](https://github.com/EverNife/EverNifeCore)** plugin!\
This fork has many changes to fit the needs of the EverNifeCore plugin.

---

## Contents
* [ChatMenuAPI](#chatmenuapi)
  - [Usage](#usage)
    + [Setup](#setup)
    + [ChatMenu](#chatmenu)
    + [Element](#element)
    + [States](#states)
    + [Displaying](#displaying)

---

## Usage

#### Requirements
- JDK 8 or above.
- EverNifeCore

#### Setup
> Replace `VERSION` with a specific version.

```groovy
repositories {
    maven {
        url 'https://maven.petrus.dev/public'
    }
}

dependencies {
     implementation 'br.com.finalcraft:ChatMenuAPI:VERSION'
}
```
### ChatMenu
To create a menu, just create a new instance of `ChatMenu`:
```Java
ChatMenu menu = new ChatMenu();
```
If you are not using this API just for chat formatting, it is recommended that you make the menu a pausing menu:
```Java
ChatMenu menu = new ChatMenu().pauseChat();
```
When this menu is sent to a player, it will automatically pause outgoing chat to that player so that the menu will not be interrupted.

**Warning:** If you make a menu pause chat, you need to add a way to close the menu!

### Element
Elements are the building blocks of menus. They are used to represent everything in a menu.
There are a few elements provided by default, you can view them by [clicking here](../master/src/me/tom/sparse/spigot/chat/menu/element).

Basic `TextElement`:
```Java
menu.add(new TextElement("Hello, world!", 10, 10));
```

Basic close button:
```Java
menu.add(new ButtonElement(x, y, ChatColor.RED+"[Close]", (p) -> {menu.close(p); return false;}));
```

Instead of manually creating a close button, you can also just pass the arguments you would use for a close button directly into the `pauseChat` method.
```Java
ChatMenu menu = new ChatMenu().pauseChat(x, y, ChatColor.RED+"[Close]");
```

All of the default elements require and X and Y in their constructor, 
these coordinates should be greater than or equal to 0 and less than 320 on the X axis and 20 on the Y axis.
The default Minecraft chat is 320 pixels wide and 20 lines tall.

### States
Most interactive elements have one or more `State` objects.

`State`s are used to store information about an `Element`, such as the current number in an `IncrementalElement`.

Every state can have a change callback to detect when it changes:
```Java
IncrementalElement incr = ...;
incr.value.setChangeCallback((s) -> {
	System.out.println("IncrementalElement changed! "+s.getPrevious()+" -> "+s.getCurrent());
});
```

### Displaying
Once you've created your menu and added all the elements you want, now would probably be a good time to display it.
You can display a menu using `ChatMenu#openFor(Player player)`:
```Java
Player p = ...;
menu.openFor(p);
```