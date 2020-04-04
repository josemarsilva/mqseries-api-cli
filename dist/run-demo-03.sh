#/bin/bash

ACTION=put
HOST=127.0.0.1
PORT=1414
CHANNEL=DEV.APP.SVRCONN
QMGR=QM1
APP_PASSWORD=passw0rd
QUEUE_NAME=DEV.QUEUE.1
MESSAGEFILE=message-file-send-example.txt

echo ""
echo "java -jar /home/ubuntu/githome/bitbucket.server.local/mqseries-api-cli/dist/mqseries-api-cli.jar -A ${ACTION} -H ${HOST} -P ${PORT} -C ${CHANNEL} -Q ${QMGR} -p ${APP_PASSWORD} -q ${QUEUE_NAME} -f ${MESSAGEFILE}"
echo ""

java -jar /home/ubuntu/githome/bitbucket.server.local/mqseries-api-cli/dist/mqseries-api-cli.jar -A ${ACTION} -H ${HOST} -P ${PORT} -C ${CHANNEL} -Q ${QMGR} -p ${APP_PASSWORD} -q ${QUEUE_NAME} -f ${MESSAGEFILE}
