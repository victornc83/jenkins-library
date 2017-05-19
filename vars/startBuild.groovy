#!/usr/bin/groovy

def call(project, app){
    loginOpenshift(project){
      sh "rm -rf oc-build && mkdir -p oc-build/deployments"
      sh "cp target/*.jar oc-build/deployments/"
      sh "oc start-build ${app}-container --from-dir=oc-build --follow -n ${project}"
    }
}
