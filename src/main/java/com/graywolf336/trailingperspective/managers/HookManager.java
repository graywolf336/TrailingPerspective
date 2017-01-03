package com.graywolf336.trailingperspective.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.classes.Hook;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.hooks.DefaultHook;
import com.graywolf336.trailingperspective.hooks.EssentialsHook;
import com.graywolf336.trailingperspective.interfaces.IHookManager;

public class HookManager implements IHookManager {
    private TrailingPerspectiveMain pl;
    private List<Hook> hooks;

    public HookManager(TrailingPerspectiveMain plugin) {
        this.hooks = new ArrayList<Hook>();
        this.pl = plugin;
        this.loadHooks();
    }

    public List<Hook> getHooks() {
        return this.hooks;
    }

    public Hook getHook(String name) {
        return this.hooks.stream().filter(h -> h.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void addHook(Hook hook) {
        this.pl.debug(false, "Added " + hook.getName() + " Hook.");
        this.hooks.add(hook);
    }

    public boolean checkHooksToSeeIfPlayerIsOkayToBeTrailed(Player player) {
        //Check each hook, if any of them say this player can't be trailed, then return false if they can't
        //So, if the filter returns no values then all hooks gave the okay to be trailed
        return !this.hooks.stream().filter(h -> !h.isPlayerOkayToBeTrailed(player)).findAny().isPresent();
    }

    private void loadHooks() {
        this.addHook(new DefaultHook());

        if (this.pl.getServer().getPluginManager().isPluginEnabled("Essentials") && Settings.HOOKS_ESSENTIALS_ENABLED.asBoolean()) {
            EssentialsHook esh = new EssentialsHook(this.pl);
            this.pl.getServer().getPluginManager().registerEvents(esh, this.pl);
            this.addHook(esh);
        }
    }
}
