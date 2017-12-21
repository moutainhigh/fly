BIN_PATH=$(cd "$(dirname "$0")"; pwd)
cd $BIN_PATH
PID_FILE="./pid"
if [ -f "$PID_FILE" ]; then
        PID=$(head -1 "$PID_FILE")
	rm -f "$PID_FILE"
	kill -9 "$PID"
fi
