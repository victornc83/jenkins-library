#!/usr/bin/groovy

def call(project, body){
  withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'openshift', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
      sh "oc login --insecure-skip-tls-verify=true -u $env.USERNAME -p $env.PASSWORD $OPENSHIFT"
  }
  sh "oc new-project ${project} || echo 'Project exists'"
  sh "oc project ${project}"
  body()
}
