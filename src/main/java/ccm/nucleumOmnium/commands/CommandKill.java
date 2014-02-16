/*
 * Copyright (c) 2014 CCM modding crew.
 * View members of the CCM modding crew on https://github.com/orgs/CCM-Modding/members
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleumOmnium.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;

public class CommandKill extends net.minecraft.command.CommandKill
{
    @Override
    public String getCommandUsage(final ICommandSender sender)
    {
        return "commands.kill.fix.usage";
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if (args.length == 1)
        {
            final EntityPlayerMP playerDead = getPlayer(sender, args[0]);
            playerDead.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
            playerDead.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.kill.success"));
        }
        else
        {
            EntityPlayerMP playerDead = getCommandSenderAsPlayer(sender);
            if (args.length == 0)
            {
                playerDead.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
                playerDead.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.kill.success"));
            }
            else
            {
                playerDead.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions(getCommandUsage(sender)));
            }
        }
    }
}