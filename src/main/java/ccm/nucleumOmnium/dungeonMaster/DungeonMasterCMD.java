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

package ccm.nucleumOmnium.dungeonMaster;

import com.google.common.base.Joiner;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import java.util.List;

public class DungeonMasterCMD extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "dungeonmaster";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "Use this command to modify the loot in dungeons.";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length == 0 || args[0].equalsIgnoreCase("help"))
        {
            helpText(sender);
            return;
        }
        else if (args[0].equalsIgnoreCase("list"))
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromText(Joiner.on(", ").join(DungeonMaster.getChestList())));
        }
        else if (args[0].equalsIgnoreCase("edit"))
        {
            if (!DungeonMaster.getChestList().contains(args[1]))
            {
                throw new WrongUsageException(args[1] + " isn't on the list.");
            }
            ChestGenHooks chestGenHooks = ChestGenHooks.getInfo(args[1]);

            if (args[2].equalsIgnoreCase("min"))
            {
                if (args[3].equalsIgnoreCase("get"))
                {
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("Min spawn chance of " + args[1] + ": " + chestGenHooks.getMin()));
                }
                else if (args[3].equalsIgnoreCase("set"))
                {
                    chestGenHooks.setMin(parseIntWithMin(sender, args[4], 0));
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("Min spawn chance set to " + args[1] + ": " + chestGenHooks.getMin()));
                }
            }
            else if (args[2].equalsIgnoreCase("max"))
            {
                if (args[3].equalsIgnoreCase("get"))
                {
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("Max spawn chance of " + args[1] + ": " + chestGenHooks.getMax()));
                }
                else if (args[3].equalsIgnoreCase("set"))
                {
                    chestGenHooks.setMax(parseIntWithMin(sender, args[4], 0));
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("Max spawn chance set to " + args[1] + ": " + chestGenHooks.getMax()));
                }
            }
            else if (args[2].equalsIgnoreCase("items"))
            {
                if (args.length == 3 || args[3].equalsIgnoreCase("get"))
                {
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("Items in " + args[1]).setColor(EnumChatFormatting.AQUA));
                    int i = 0;
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("[Index]  Name - " + EnumChatFormatting.AQUA + "Weight" + EnumChatFormatting.WHITE + " - " + EnumChatFormatting.GREEN + "Min" + EnumChatFormatting.WHITE + " - " + EnumChatFormatting.RED + " Max"));
                    for (WeightedRandomChestContent content : DungeonMaster.getContents(chestGenHooks))
                    {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText("[" + i++ + "]  " + getTextFromWeightedRandomChestContent(content)));
                    }
                }
                else if (args[3].equalsIgnoreCase("remove"))
                {
                    int i = parseIntBounded(sender, args[4], 0, DungeonMaster.getContents(chestGenHooks).size());
                    WeightedRandomChestContent content = DungeonMaster.getContents(chestGenHooks).remove(i);
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("Removed item with index " + i + ": " + content.theItemId.getUnlocalizedName()));
                }
                else if (args[3].equalsIgnoreCase("add"))
                {
                    try
                    {
                        EntityPlayerMP player = getCommandSenderAsPlayer(sender);
                        ItemStack itemStack = player.getHeldItem();
                        if (itemStack == null) throw new WrongUsageException("You must be holding the item you want to add.");

                        int weight = parseIntWithMin(sender, args[4], 0);

                        int min = parseIntWithMin(sender, args[5], 0);
                        int max = parseIntWithMin(sender, args[6], min);

                        WeightedRandomChestContent content = new WeightedRandomChestContent(itemStack, min, max, weight);
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText("Added: " + getTextFromWeightedRandomChestContent(content)));

                        chestGenHooks.addItem(content);
                    }
                    catch (NumberInvalidException e)
                    {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText("Proper syntax: ... add <item weight> <min stack> <max stack>").setColor(EnumChatFormatting.RED));
                    }
                }
            }
        }
        else helpText(sender);

        DungeonMaster.saveLoot();
    }

    public static String getTextFromWeightedRandomChestContent(WeightedRandomChestContent content)
    {
        return content.theItemId.getUnlocalizedName() + " - " + EnumChatFormatting.AQUA + content.itemWeight + EnumChatFormatting.WHITE + " - " + EnumChatFormatting.GREEN + content.theMinimumChanceToGenerateItem + EnumChatFormatting.WHITE + " - " + EnumChatFormatting.RED + content.theMaximumChanceToGenerateItem;
    }

    private void helpText(ICommandSender sender)
    {
        sender.sendChatToPlayer(ChatMessageComponent.createFromText("/dungeonmaster list|help|edit <dungeon type> min|max|items").setColor(EnumChatFormatting.AQUA));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        if (args.length == 1) return getListOfStringsMatchingLastWord(args, "list", "help", "edit");
        if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("min") || args[0].equalsIgnoreCase("max")) return getListOfStringsMatchingLastWord(args, "get", "set");
            if (args[0].equalsIgnoreCase("edit")) return getListOfStringsFromIterableMatchingLastWord(args, DungeonMaster.getChestList());
        }
        if (args.length == 3)
        {
            if (args[0].equalsIgnoreCase("edit")) return getListOfStringsMatchingLastWord(args, "min", "max", "items");
            if (args[2].equalsIgnoreCase("min") || args[2].equalsIgnoreCase("max")) return getListOfStringsMatchingLastWord(args, "get", "set");
        }
        if (args.length == 4)
        {
            if (args[2].equalsIgnoreCase("items")) return getListOfStringsMatchingLastWord(args, "get", "add", "remove");
        }
        return null;
    }
}
