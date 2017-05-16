#!/usr/bin/groovy

def call(cluster, project, app){
    sh "rm -rf oc-build && mkdir -p oc-build/deployments"
    sh "cp target/*.jar oc-build/deployments/"
    openshift.withCluster(cluster){
        openshift.withProject(project){
            sh "oc start-build ${app}-container --from-dir=oc-build --follow"
        }
    }
}
