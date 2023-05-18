package it.polimi.ingsw.ui.wizard;

import it.polimi.ingsw.launcher.parameters.*;
import it.polimi.ingsw.ui.cli.Console;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static it.polimi.ingsw.launcher.argparser.CLIDestinations.*;

public class Wizard {

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
