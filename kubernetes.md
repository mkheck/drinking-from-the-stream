# Deploying to Kubernetes

## Build Images

> Note: you can use our prebuilt images at `paulczar/scst-source`, `paulczar/scst-processor` and `paulczar/scst-sink`.

```bash
$ mvn compile com.google.cloud.tools:jib-maven-plugin:1.0.2:dockerBuild \
  -f scst-processor/pom.xml \
  -Dimage=paulczar/scst-processor:0.0.1-SNAPSHOT

$ mvn compile com.google.cloud.tools:jib-maven-plugin:1.0.2:dockerBuild \
  -f scst-sink/pom.xml \
  -Dimage=paulczar/scst-sink:0.0.1-SNAPSHOT

$ mvn compile com.google.cloud.tools:jib-maven-plugin:1.0.2:dockerBuild \
  -f scst-source/pom.xml \
  -Dimage=paulczar/scst-source:0.0.1-SNAPSHOT

```

## Push images to Docker registry

```bash
docker push paulczar/scst-source:0.0.1-SNAPSHOT
docker push paulczar/scst-processor:0.0.1-SNAPSHOT
docker push paulczar/scst-sink:0.0.1-SNAPSHOT
```

## Run using Helm

```bash
helm dependency update deploy/helm/scst

helm install --name coffee --namespace coffee \
    deploy/helm/scst
```

Once its running you can view the logs of the microservices like so:

```
kubectl --namespace coffee logs deployment/coffee-scst-source | tail
kubectl --namespace coffee logs deployment/coffee-scst-processor | tail
kubectl --namespace coffee logs deployment/coffee-scst-sink | tail
```
