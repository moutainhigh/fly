CUR_VERSION="1.0.0.1"
BIN_PATH=$(cd "$(dirname "$0")"; pwd)
cd $BIN_PATH
cd ..
PID_FILE="./bin/pid"
if [ -f "$PID_FILE" ]; then
	PID=$(head -1 "$PID_FILE")
        echo "MC has run! : pid=$PID version=$CUR_VERSION"
else
        nohup java -Dmc.config="$(pwd)/conf/config.properties" -jar message-center-$CUR_VERSION.jar >/dev/null 2>&1 &
        echo $!>"$PID_FILE"
	PID=$(head -1 "$PID_FILE")	
        echo "MC is running : pid=$PID version=$CUR_VERSION"
	sleep 1
        tail -f ./log/mc.log
fi
