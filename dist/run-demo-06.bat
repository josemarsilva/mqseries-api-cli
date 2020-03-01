@ECHO OFF
REM ***************************************************************************
REM filename   : run-demo-06
REM description: Run Demonstration of GET of triggered queue
REM souce-code : https://github.com/josemarsilva/mqseries-api-cli 
REM obs        :
REM ***************************************************************************

SET ACTION=get
SET HOST=127.0.0.1
SET PORT=1414
SET CHANNEL=DEV.APP.SVRCONN
SET QMGR=QM1
SET APP_PASSWORD=passw0rd
SET QUEUE_NAME=TRG.QUEUE.1

ECHO.
ECHO java -jar mqseries-api-cli.jar -A %ACTION% -H %HOST% -P %PORT% -C %CHANNEL% -Q %QMGR% -p %APP_PASSWORD% -q %QUEUE_NAME%
ECHO.

java -jar mqseries-api-cli.jar -A %ACTION% -H %HOST% -P %PORT% -C %CHANNEL% -Q %QMGR% -p %APP_PASSWORD% -q %QUEUE_NAME%
