SET ACTION=put
SET HOST=127.0.0.1
SET PORT=1414
SET CHANNEL=DEV.APP.SVRCONN
SET QMGR=QM1
REM SET APP_USER=
SET APP_PASSWORD=passw0rd
SET QUEUE_NAME=DEV.QUEUE.1
SET MESSAGEFILE=message-file-send-example.txt

ECHO.
ECHO java -jar mqseries-api-cli.jar -A %ACTION% -H %HOST% -P %PORT% -C %CHANNEL% -Q %QMGR% -p %APP_PASSWORD% -q %QUEUE_NAME% -f %MESSAGEFILE%
ECHO.

java -jar mqseries-api-cli.jar -A %ACTION% -H %HOST% -P %PORT% -C %CHANNEL% -Q %QMGR% -p %APP_PASSWORD% -q %QUEUE_NAME% -f %MESSAGEFILE%
