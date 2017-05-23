#!/usr/bin/groovy

def call(body){

  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  def name = config.name
  def project = config.project
  def service = config.service
  def hostname = config.hostname ?: ''

  loginOpenshift(config.project){
    def noexist = sh(script:"oc get route ${name} -n ${project}",returnStatus: true)
    if(noexist){
      if (hostname == ''){
        sh "oc expose --name=${name} svc ${service} -n ${project}"
      } else {
        sh "oc expose --name=${name} svc ${service} --hostname=${hostname} -n ${project}"
      }
    } else {
      sh "oc patch route/${name} -p '{\"spec\": {\"to\": {\"name\": \"${service}\" }}}' -n ${project}"
      if(hostname != ''){
        sh "oc patch route/${name} -p '{\"spec\": {\"host\": \"${hostname}\" }}' -n ${project}"
      }
    }
  }
}
