#!/usr/bin/groovy

def call(cluster, project, app){
    sh "rm -rf oc-build && mkdir -p oc-build/deployments"
    sh "cp target/*.jar oc-build/deployments/"
    openshift.withCluster(cluster){
      sh "oc start-build ${app}-container --from-dir=oc-build --follow -n ${project}"
    }
}
