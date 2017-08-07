package me.tom.sparse.spigot.chat.menu.element;

import me.tom.sparse.spigot.chat.menu.ChatMenuAPI;
import me.tom.sparse.spigot.chat.menu.IElementContainer;
import me.tom.sparse.spigot.chat.util.State;
import me.tom.sparse.spigot.chat.util.Text;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A number with a subtract button on the left and a add button on the right.
 */
public class IncrementalElement extends Element
{
	public final State<Integer> value;
	protected int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
	
	/**
	 * Construct a new {@code IncrementalElement} with a minimum of {@link Integer#MIN_VALUE} and a maximum of {@link Integer#MAX_VALUE}
	 *
	 * @param x     the x coordinate
	 * @param y     the y coordinate
	 * @param value the starting value
	 */
	public IncrementalElement(int x, int y, int value)
	{
		super(x, y);
		this.value = new State<>(value, this::filter);
	}
	
	/**
	 * Constructs a new {@code IncrementalElement}
	 *
	 * @param x     the x coordinate
	 * @param y     the y coordinate
	 * @param min   the minimum value
	 * @param max   the maximum value
	 * @param value the starting value
	 */
	public IncrementalElement(int x, int y, int min, int max, int value)
	{
		super(x, y);
		this.min = min;
		this.max = max;
		this.value = new State<>(value, this::filter);
	}
	
	private int filter(int v)
	{
		return Math.min(Math.max(v, min), max);
	}
	
	/**
	 * @return the minimum value
	 */
	public int getMin()
	{
		return min;
	}
	
	/**
	 * @param min the new minimum value
	 */
	public void setMin(int min)
	{
		this.min = min;
		value.set(value.current());
	}
	
	/**
	 * @return the maximum value
	 */
	public int getMax()
	{
		return max;
	}
	
	/**
	 * @param max the new maximum value
	 */
	public void setMax(int max)
	{
		this.max = max;
	}
	
	/**
	 * @param value the new value
	 */
	public void setValue(int value)
	{
		this.value.set(value);
	}
	
	/**
	 * @return the current value
	 */
	public int getValue()
	{
		return value.current();
	}
	
	public int getWidth()
	{
		return ChatMenuAPI.getWidth("[-] " + value.current() + " [+]");
	}
	
	public int getHeight()
	{
		return 1;
	}
	
	public boolean isEnabled()
	{
		return true;
	}
	
	public List<Text> render(IElementContainer context)
	{
		String baseCommand = context.getCommand(this);
		
		List<BaseComponent> components = new ArrayList<>();
		TextComponent decrement = new TextComponent("[-]");
		int current = value.current();
		if(current - 1 >= min)
		{
			decrement.setColor(ChatColor.RED);
			decrement.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, baseCommand + (current - 1)));
		}else
		{
			decrement.setColor(ChatColor.DARK_GRAY);
		}
		
		TextComponent increment = new TextComponent("[+]");
		if(current + 1 <= max)
		{
			increment.setColor(ChatColor.GREEN);
			increment.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, baseCommand + (current + 1)));
		}else
		{
			increment.setColor(ChatColor.DARK_GRAY);
		}
		
		TextComponent number = new TextComponent(" " + current + " ");
		
		components.add(decrement);
		components.add(number);
		components.add(increment);
		
		return Collections.singletonList(new Text(components));
	}
	
	public void edit(IElementContainer container, String[] args)
	{
		value.set(Integer.parseInt(args[0]));
	}
	
	public List<State<?>> getStates()
	{
		return Collections.singletonList(value);
	}
}