cd /home/ubuntuseba/eclipse-workspace/SiabElasticApi
mvn clean
mvn package
sudo docker build -f Dockerfile -t docker-siab-elastic-api .
sudo docker images
sudo docker tag 5f4c861de832 localhost:5000/docker-siab-elastic-api
sudo docker push localhost:5000/docker-siab-elastic-api
cd /home/ubuntuseba/cord/helm-charts
helm install --name alper alperchart --debug 
kubectl get pods --all-namespaces -o wide|grep alper
kubectl logs $POD_NAME --follow
kubectl get services 
