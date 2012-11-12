/*
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * Copyright 2011, 2012 Peter Güttinger
 * 
 */

package ch.njol.skript.effects;

import org.bukkit.block.Block;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.StructureType;

/**
 * @author Peter Güttinger
 */
public class EffTree extends Effect {
	private static final long serialVersionUID = -7165734014003826172L;
	
	static {
		Skript.registerEffect(EffTree.class,
				"(grow|create|generate) tree [of type %structuretype%] %blocks%",
				"(grow|create|generate) %structuretype% [tree] %blocks%");
	}
	
	private Expression<Block> blocks;
	private Expression<StructureType> type;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(final Expression<?>[] vars, final int matchedPattern, final int isDelayed, final ParseResult parser) {
		type = (Expression<StructureType>) vars[0];
		blocks = (Expression<Block>) vars[1];
		return true;
	}
	
	@Override
	public void execute(final Event e) {
		final StructureType type = this.type.getSingle(e);
		if (type == null)
			return;
		for (final Block b : blocks.getArray(e)) {
			type.grow(b);
		}
	}
	
	@Override
	public String toString(final Event e, final boolean debug) {
		return "grow tree of type " + type.toString(e, debug) + " " + blocks.toString(e, debug);
	}
	
}