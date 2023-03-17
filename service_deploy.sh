# sudo chmod a+x /home/admin/app/jnljs.jar

if [ -f "/home/ecs-user/github/maxi-backend/app.pid" ];
then
  cat /home/ecs-user/github/maxi-backend/app.pid |sudo xargs  kill -9
  # 暂停15秒再重启,让内存回收下
  sleep 5
fi

sudo nohup java -jar -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms2048m -Xmx2048m -Xmn2048m -Xss256k -XX:SurvivorRatio=8 -XX:+UseG1GC -Dspring.profiles.active=prod /home/ecs-user/app/maxi.jar  >> /home/ecs-user/logs/appdef.log 2>&1   &

sleep 10
curl  127.0.0.1:8088/health
echo "service done"
