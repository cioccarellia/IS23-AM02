package it.polimi.ingsw.model.game.session;

import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class for managing and updating player sessions.
 * Provides independence between accessing a session with the player's username and game number.
 */
public class SessionManager {

    private final Map<String, PlayerSession> map = new HashMap<>();
    private final GameMode mode;

    public SessionManager(GameMode _mode) {
        mode = _mode;
    }

    /**
     * Returns the amount of sessions
     */
    public int size() {
        return map.size();
    }

    /**
     * Returns the maximum amount of sessions that can be added
     */
    public int maxSize() {
        return mode.maxPlayerAmount();
    }

    /**
     * Returns a collection containing the sessions
     */
    public Collection<PlayerSession> values() {
        return map.values();
    }

    /**
     * Inserts a new session
     */
    public void put(@NotNull PlayerSession session) {
        String username = session.getUsername();

        if (map.containsKey(username)) {
            throw new IllegalStateException("Impossible to add a player: username %s already present in game".formatted(username));
        }

        assert Objects.equals(session.getUsername(), username);
        assert map.values().stream().noneMatch(it -> it.getPlayerNumber() == session.getPlayerNumber());
        assert map.values().stream().noneMatch(it -> Objects.equals(it.getUsername(), username));

        map.put(username, session);
    }

    public boolean isPresent(String username) {
        return map.containsKey(username);
    }

    public boolean isPresent(PlayerNumber number) {
        return map.values().stream().anyMatch(it -> it.getPlayerNumber() == number);
    }


    public PlayerSession getByUsername(String username) {
        assert map.containsKey(username);
        return map.get(username);
    }


    public PlayerSession getByNumber(PlayerNumber number) {
        Optional<PlayerSession> matchingPlayerSession = map.values()
                .stream()
                .filter(it -> it.getPlayerNumber() == number)
                .findAny();

        if (matchingPlayerSession.isPresent()) {
            return map.get(matchingPlayerSession.get().getUsername());
        } else {
            throw new IllegalStateException("Given number does not match to any session");
        }
    }


    public Map<PlayerNumber, PlayerSession> getNumberMap() {
        return map.values().stream().collect(
                Collectors.toMap(
                        PlayerSession::getPlayerNumber,
                        Function.identity()
                )
        );
    }

    public List<PlayerSession> playerSessions() {
        return map.values().stream().toList();
    }

}
