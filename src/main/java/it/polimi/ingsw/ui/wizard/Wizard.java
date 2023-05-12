package it.polimi.ingsw.ui.wizard;

import it.polimi.ingsw.launcher.parameters.AppLaunchTarget;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.launcher.parameters.ExhaustiveLaunchConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.commons.lang.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import static it.polimi.ingsw.launcher.argparser.CLIDestinations.*;

public class Wizard {

    public static ExhaustiveLaunchConfiguration launchWizardAndAcquireParams(@NotNull Namespace ns) {
        // client target
        AppLaunchTarget target = ns.get(TARGET);

        // ports and host
        String serverHost = ns.get(SERVER_IP);
        int serverTcpPort = 0, serverRmiPort = 0;

        try {
            serverTcpPort = ns.get(SERVER_TCP_PORT);
            serverRmiPort = ns.get(SERVER_RMI_PORT);
        } catch (NullPointerException npe) {
            // no ports given
        }

        // client-specific exhaustive config
        ClientUiMode modePreselection = ns.get(CLIENT_MODE);
        ClientProtocol protocolPreselection = ns.get(CLIENT_PROTOCOL);

        throw new NotImplementedException();
    }

}
