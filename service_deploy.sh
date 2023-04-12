# sudo chmod a+x /home/admin/app/jnljs.jar

cd /home/ubuntu/app

if [ -f "/home/ubuntu/app/app.pid" ];
then
  cat /home/ubuntu/app/app.pid |sudo xargs  kill -9
  # 暂停15秒再重启,让内存回收下
  sleep 5
fi

sudo nohup java -jar -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms2048m -Xmx2048m -Xmn2048m -Xss256k -XX:SurvivorRatio=8 -XX:+UseG1GC -Dspring.profiles.active=prod /home/ubuntu/app/maxi.jar  >> /home/ubuntu/logs/appdef.log 2>&1   &

sleep 20
curl  127.0.0.1:8088/health
echo "service done"
