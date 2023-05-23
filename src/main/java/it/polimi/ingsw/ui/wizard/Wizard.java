package it.polimi.ingsw.ui.wizard;

import it.polimi.ingsw.launcher.parameters.*;
import it.polimi.ingsw.ui.cli.Console;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static it.polimi.ingsw.launcher.argparser.CLIDestinations.*;

public class Wizard {

    /**
     * Lunches the wizard and acquires the parameters from the user
     *
     * @param ns the default values for the parameters
     * @return the configuration created with the parameters given by the player
     */
    public static ExhaustiveLaunchConfiguration launchWizardAndAcquireParams(@NotNull Namespace ns) {
        // client target
        AppLaunchTarget target = ns.get(TARGET);

        // ports and host
        String serverHost = ns.get(SERVER_IP);
        int serverTcpPort = 0, serverRmiPort = 0;

        // client-specific exhaustive config
        ClientUiMode modePreselection = ns.get(CLIENT_MODE);
        ClientProtocol protocolPreselection = ns.get(CLIENT_PROTOCOL);


        switch (target = selectAppLaunchTarget(target)) {
            case SERVER -> {
                serverTcpPort = selectTcpPort(serverTcpPort);
                serverRmiPort = selectRmiPort(serverRmiPort);

                return new ExhaustiveLaunchConfiguration(target, "127.0.0.1", serverTcpPort, serverRmiPort, List.of());
            }
            case CLIENT -> {
                modePreselection = selectGameMode(modePreselection);
                protocolPreselection = selectProtocol(protocolPreselection);

                serverHost = selectServerHost(serverHost);

                switch (protocolPreselection) {
                    case RMI -> serverRmiPort = selectTcpPort(serverRmiPort);
                    case TCP -> serverTcpPort = selectRmiPort(serverTcpPort);
                }

                return new ExhaustiveLaunchConfiguration(target, serverHost, serverTcpPort, serverRmiPort, List.of(
                        new ClientExhaustiveConfiguration(modePreselection, protocolPreselection)
                ));

            }
            default -> throw new IllegalStateException("Unexpected value.");
        }
    }


    /**
     * @param defaultValue the default value for {@link AppLaunchTarget}, if the player doesn't choose any
     * @return the target selected by the player
     */
    private static AppLaunchTarget selectAppLaunchTarget(AppLaunchTarget defaultValue) {
        if (defaultValue != null) {
            return defaultValue;
        }

        while (true) {
            System.out.println("Select app launch target: [SERVER, CLIENT]: ");

            String modeValueFromUser = Console.in();

            if (modeValueFromUser.equalsIgnoreCase("s") || modeValueFromUser.toLowerCase().contains("server") || modeValueFromUser.equalsIgnoreCase("1")) {
                return AppLaunchTarget.SERVER;
            } else if (modeValueFromUser.equalsIgnoreCase("c") || modeValueFromUser.toLowerCase().contains("client") || modeValueFromUser.equalsIgnoreCase("2")) {
                return AppLaunchTarget.CLIENT;
            }
        }

    }

    /**
     * @param defaultMode the default value for UI mode, if the player doesn't choose any
     * @return the UI mode selected by the player (client)
     */
    private static ClientUiMode selectGameMode(ClientUiMode defaultMode) {
        if (defaultMode != null) {
            return defaultMode;
        }

        while (true) {
            System.out.println("Select client UI [CLI, GUI]: ");

            String modeValueFromUser = Console.in();

            if (modeValueFromUser.equalsIgnoreCase("gui") || modeValueFromUser.equalsIgnoreCase("1")) {
                return ClientUiMode.GUI;
            } else if (modeValueFromUser.equalsIgnoreCase("cli") || modeValueFromUser.equalsIgnoreCase("2")) {
                return ClientUiMode.CLI;
            }
        }
    }

    /**
     * @param defaultProtocol the default value for protocol, if the player doesn't choose any
     * @return the protocol selected by the player (client)
     */
    private static ClientProtocol selectProtocol(ClientProtocol defaultProtocol) {
        if (defaultProtocol != null) {
            return defaultProtocol;
        }

        while (true) {
            System.out.println("Select protocol [TCP, RMI]: ");

            String protocolValueFromUser = Console.in();

            if (protocolValueFromUser.equalsIgnoreCase("tcp") || protocolValueFromUser.equalsIgnoreCase("1")) {
                return ClientProtocol.TCP;
            } else if (protocolValueFromUser.equalsIgnoreCase("rmi") || protocolValueFromUser.equalsIgnoreCase("2")) {
                return ClientProtocol.RMI;
            }
        }
    }

    /**
     * @param defaultTcpPort the default value for TCP port, if the player doesn't choose any
     * @return the TCP port selected by the player
     */
    private static int selectTcpPort(int defaultTcpPort) {
        if (defaultTcpPort != 0) {
            return defaultTcpPort;
        }

        while (true) {
            System.out.println("Select TCP port [1024, 65535]: ");

            String tcpPortValueFromUser = Console.in();

            if (isPortValid(tcpPortValueFromUser))
                return Integer.parseInt(tcpPortValueFromUser);
        }
    }

    /**
     * @param defaultRmiPort the default value for RMI port, if the player doesn't choose any
     * @return the RMI port selected by the player
     */
    private static int selectRmiPort(int defaultRmiPort) {
        if (defaultRmiPort != 0) {
            return defaultRmiPort;
        }

        while (true) {
            System.out.println("Select RMI port [1024, 65535]: ");

            String rmiPortValueFromUser = Console.in();

            if (isPortValid(rmiPortValueFromUser))
                return Integer.parseInt(rmiPortValueFromUser);

        }
    }

    /**
     * @param defaultServerHost the default value for server IP address, if the player doesn't choose any
     * @return the server IP address selected by the player
     */
    private static String selectServerHost(String defaultServerHost) {
        if (defaultServerHost != null) {
            return defaultServerHost;
        }

        while (true) {
            System.out.println("Select server IP address: ");

            String serverValueFromUser = Console.in();

            if (!serverValueFromUser.isBlank()) {
                return serverValueFromUser;
            }
        }
    }

    /**
     * @param portValueFromUser the port selected by the user, both for RMI and TCP
     * @return if the port value is between the correct range (from 1024 to 65535)
     */
    private static boolean isPortValid(String portValueFromUser) {
        if (portValueFromUser.isBlank()) {
            return false;
        }

        int serverPort;

        try {
            serverPort = Integer.parseInt(portValueFromUser);
        } catch (NumberFormatException e) {
            return false;
        }

        // verify ports are not too high
        if (serverPort < 1024) {
            // verify ports are not too low
            return false;
        } else return serverPort <= 65535;
    }
}
