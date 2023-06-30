# IS23-AM02

<p style="text-align: left;">
  <a href="https://github.com/cioccarellia/IS23-AM02/actions/workflows/build.yaml"><img src="https://github.com/cioccarellia/IS23-AM02/actions/workflows/build.yaml/badge.svg" alt="Build CI" /></a>
  <a href="https://github.com/cioccarellia/IS23-AM02/actions/workflows/tests.yaml"><img src="https://github.com/cioccarellia/IS23-AM02/actions/workflows/tests.yaml/badge.svg" alt="Tests CI" /></a>
</p>

## Developers

- ### 10766393    Alberto Cantele ([@CntAlberto](https://github.com/CntAlberto))<br>alberto.cantele@mail.polimi.it
- ### 10713858    Andrea Cioccarelli ([@cioccarellia](https://github.com/cioccarellia))<br>andrea.cioccarelli@mail.polimi.it
- ### 10792907    Giulia Bortone ([@GiuliaBortone](https://github.com/GiuliaBortone))<br>giulia.bortone@mail.polimi.it
- ### 10790205    Marco Carminati ([@MarcoCarminati](https://github.com/MarcoCarminati))<br>marco9.carminati@mail.polimi.it

## Progress status

| Functionality  | State |
|:---------------|:-----:|
| Basic rules    |  🟢   |
| Complete rules |  🟢   |
| Socket         |  🟢   |
| RMI            |  🟢   |
| GUI            |  🟢   |
| CLI            |  🟢   |
| Multiple games |  🔴   |
| Persistence    |  🟢   |
| Resilience     |  🟡   |
| Chat           |  🟢   |

## Compilation

### Requirements

The project uses as the intended language level `19 preview` ⚠️. <br>
To compile and execute the project, you'll need a jdk with 19 preview features support.
We recommend using any version of openjdk-19.

### Compiling
Run the maven `clean` and then `package` command to generate a jar file for the project. 

```
mvn clean -f pom.xml
mvn package -f pom.xml
```

The output jar will be located in `artifacts/shelfie.jar`.

### Executing

Once the jar file has been compiled, you can run it (assuming your `java` uses a jdk 19 with preview-features enabled) using one of the launch modes described below.
A script to generate command line arguments is present (`gen_args_nix.sh`) to expedite this process.

#### Wizard

The wizard is invoked when no/not enough parameters are given (as program arguments), and will ask through a CLI interface to provide them until an exhaustive configuration is reached, and the corresponding module is started.

```
java --enable-preview -jar myshelfie-all.jar
```

#### Server

Template for starting the server. The server address is the IP of the machine executing it.

| Parameter | Value    | Description                                 |
|-----------|----------|---------------------------------------------|
| Target    | `SERVER` | Specifies that the server should be started |
| TCP port  | `12000`  | Port used for TCP connections               |
| RMI port  | `13000`  | Port used for RMI connections               |

```
java --enable-preview -jar myshelfie-all.jar --target SERVER --server-tcp-port 12000 --server-rmi-port 13000
```

#### Client

Template for starting the client.

| Parameter       | Value                       | Description                                                                |
|-----------------|-----------------------------|----------------------------------------------------------------------------|
| Target          | `CLIENT`                    | Specifies that the server should be started                                |
| Server address  | `127.0.0.1` / `192.168.1.X` | Address of the server hosting the game. Can be the loopback or local (LAN) |
| Client mode     | `CLI`/`GUI`                 | Whether starting the client in CLI or GUI mode                             |
| Client proto    | `TCP`/`RMI`                 | Specifies which communication protocol is used for this client             |
| Server TCP port | `12000`                     | Port used for TCP connections                                              |
| Server RMI port | `13000`                     | Port used for RMI connections                                              |

```
java --enable-preview -jar myshelfie-all.jar --target CLIENT --server-address 127.0.0.1 --client-mode CLI --client-protocol RMI --server-tcp-port 12000 --server-rmi-port 13000
```
