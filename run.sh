# Util to generate launch args
clear
echo "Generating command line options to start MyShelfie"

osx_user="cioccarellia"
osx_jdk_version="19.0.2"

# Specify a jdk-19 version of java installed on your system (with experimental flags enabled)
jbin="/Users/${osx_user}/Library/Java/JavaVirtualMachines/openjdk-${osx_jdk_version}/Contents/Home/bin/java"

# Experimental flags
params="--enable-preview"

# Jar file path
jarfile="deliveries/jars/myshelfie-all.jar"

# Program arguments
progargs_dfu=""
progargs_srv="--target SERVER --server-tcp-port 12000 --server-rmi-port 13000"
progargs_client="--target CLIENT --server-address 127.0.0.1 --client-mode CLI --client-protocol RMI --server-tcp-port 12000 --server-rmi-port 13000"

# Runs
echo "------------------------ for default (wizard)"
echo $jbin $params -jar $jarfile $progargs
echo "\n------------------------ for server"
echo $jbin $params -jar $jarfile $progargs_srv
echo "\n------------------------ for client"
echo $jbin $params -jar $jarfile $progargs_client
echo "\n"