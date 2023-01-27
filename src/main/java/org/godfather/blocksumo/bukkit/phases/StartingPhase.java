package org.godfather.blocksumo.bukkit.phases;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;
import org.godfather.blocksumo.api.server.runnables.utils.Countdown;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;
import org.godfather.blocksumo.api.utils.Utils;
import org.godfather.blocksumo.api.utils.messages.MessageType;

public class StartingPhase extends GamePhase {

    private Countdown countdown;

    public StartingPhase(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    protected void onLoad() {
        String timeName = countdown.getActualTime() == 1 ? "secondo." : "secondi.";

        countdown = Countdown.builder()
                .startFrom(30)
                .onStart(r -> alertPlayers())
                .onRepeat(r -> {
                    if (countdown.getActualTime() <= 5 || countdown.getActualTime() % 10 == 0) {
                        alertPlayers();
                    }
                })
                .onFinish(r -> {
                    parentGame.nextPhase();

                    Utils.sendMessageAll(MessageType.CHAT, "§aLa partita è iniziata!");
                    Utils.sendTitleAll(p -> "", p -> "", 0, 1, 0);
                })
                .start(bootstrap);
    }

    @Override
    protected void onUnload() {
        countdown.cancel();
        countdown = null;
    }

    @Override
    public Scoreboard getScoreboard() {
        return null;
    }

    @Override
    public boolean joinEnabled() {
        return true;
    }

    private void alertPlayers() {
        String timeName = countdown.getActualTime() == 1 ? "secondo." : "secondi.";

        Utils.sendMessageAll(MessageType.CHAT, "§eLa partita inizia in "
                + Utils.getFormattedTime(countdown.getActualTime(), ChatColor.YELLOW) + ChatColor.YELLOW + timeName);

        Utils.sendTitleAll(p -> Utils.getFormattedTime(countdown.getActualTime(), ChatColor.YELLOW), p -> "", 0, 25, 0);
        Utils.playSoundAll(Sound.WOOD_CLICK, 1, 1.6F);
    }
}
