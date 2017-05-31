#!/usr/bin/groovy

def call(project, app){
    loginOpenshift(project){
      sh "oc start-build ${app}-container --from-dir=oc-build --follow -n ${project}"
    }
}
