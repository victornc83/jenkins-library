#!/usr/bin/groovy

def call(app){
    sh "rm -rf oc-build && mkdir -p oc-build/deployments"
    sh "cp target/*.jar oc-build/deployments/"
    sh "oc start-build ${app}-container --from-dir=oc-build --follow"
}
